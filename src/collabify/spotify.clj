(ns collabify.spotify
  (:require
    [clj-http.client :as http]
    [environ.core :refer [env]]
    [ring.util.response :refer [response redirect header status]]
    [ring.middleware.cookies :refer [wrap-cookies]]
    [ring.util.codec :refer [form-encode]]
    [clojure.data.codec.base64 :as b64]
    [clojure.data.json :as json]
    [collabify.token :refer [save-token]])
  (import java.util.UUID))

(def client-id (env :spotify-client-id))
(def client-secret (env :spotify-secret))

(defn string-to-base64-string [original]
  (String. (b64/encode (.getBytes original "UTF-8")) "UTF-8"))

(def auth-header-value (str "Basic "
                            (string-to-base64-string
                              (str client-id ":" client-secret))))

(def redirect-url "http://localhost:3000/loginCallback")
(def loggedin-url "http://localhost:3000/loginSuccess?")
(def state-mismatch-url "/state_mismatch")
(def scopes "")

(def code-key "code")
(def state-key "state")
(def state-cookie-key "spotify_auth_state")
(def spotify-auth-url-base "https://accounts.spotify.com/authorize?")
(def spotify-token-url "https://accounts.spotify.com/api/token")
(def spotify-user-detail-url "https://api.spotify.com/v1/me")

(def grant-authorization-code "authorization_code")
(def grant-refresh-token "refresh_token")

(def stored-state (atom {}))

(defn- gen-state []
  (UUID/randomUUID))

(defn login [request]
  (let [state (.toString (gen-state))
        cookie (str state-cookie-key "=" state)
        auth-params (form-encode {:response_type code-key
                                  :client_id     client-id
                                  :scope         scopes
                                  :redirect_uri  redirect-url
                                  :state         state})
        auth-url (str spotify-auth-url-base auth-params)]
    (do
      (swap! stored-state assoc :state state)
      (header (redirect auth-url) "Set-Cookie" cookie))))

(defn- build-token-request [grant-type code]
  (let [body {:grant_type   "authorization_code"
              :code         code
              :redirect_uri redirect-url}]
    {:form-params  body
     :headers      {"Authorization" auth-header-value}
     :content-type :x-www-form-urlencoded
     :accept       :json}))

(defn- build-user-details-request
  "TODO: refresh token if necessary"
  [access-token refresh-token]
  (let [
        auth-bearer (str "Bearer " access-token)
        auth-header {"Authorization" auth-bearer}]
    {:headers auth-header
     :accept  :json}))

(defn- request-user-details [opts]
  (let [url spotify-user-detail-url
        response (http/get url opts)
        body (json/read-str (:body response) :key-fn keyword)]
    body))

(defn- handle-token-response [response]
  (let [token-body (json/read-str (:body response) :key-fn keyword)
        request-details (build-user-details-request (:access_token token-body) (:refresh_token token-body))
        user-details (request-user-details request-details)
        user-id (user-details :id)]
    (save-token user-id token-body)))

(defn- request-token [grant-type code]
  (let [url spotify-token-url
        opts (build-token-request grant-type code)
        token-response (http/post url opts)
        stored-token (handle-token-response token-response)]
    stored-token))

(defn- redirect-to-index [token]
  (let [params (form-encode {:user_id      (token :_id)
                             :access_token (token :access_token)
                             })
        url (str loggedin-url params)]
    (redirect url)))

(defn login-success [request]
  (let [query (:params request)
        code (query code-key)
        returned-state (query state-key)
        state-matches (= (:state @stored-state) returned-state)]
    (if (not state-matches)
      (redirect state-mismatch-url)
      (let [token (request-token grant-authorization-code code)
            user-id (token :_id)]
        (redirect-to-index token)))))

(defn refresh-token
  "Spotify tokens expire after 1 hour. Server stores the refresh_token
  and if the current user token has expired, it will request a new one"
  [])
;;