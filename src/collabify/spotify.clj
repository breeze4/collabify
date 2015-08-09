(ns collabify.spotify
  (:require
    [clj-http.client :as http]
    [environ.core :refer [env]]
    [ring.util.response :refer [redirect]]
    [ring.middleware.cookies :refer [wrap-cookies]]
    [ring.util.codec :refer [form-encode]]
    [clojure.data.codec.base64 :as b64]
    [clojure.data.json :as json])
  (import java.util.UUID))

(def client-id (env :spotify-client-id))
(def client-secret (env :spotify-secret))

(def redirect-url "http://localhost:3000/loginSuccess")
(def state-mismatch-url "/state_mismatch")
(def scopes "")

(def code-key "code")
(def state-key "state")
(def state-cookie-key :spotify_auth_state)
(def spotify-auth-url-base "https://accounts.spotify.com/authorize?")
(def spotify-token-url "https://accounts.spotify.com/api/token")

(def stored-state (atom {}))

(defn- gen-state []
  (UUID/randomUUID))

(defn string-to-base64-string [original]
  (String. (b64/encode (.getBytes original "UTF-8")) "UTF-8"))

(defn login [request]
  (let [state (.toString (gen-state))
        cookie {state-cookie-key state}
        auth-params (form-encode {:response_type code-key
                                  :client_id     client-id
                                  :scope         scopes
                                  :redirect_uri  redirect-url
                                  :state         state})
        auth-url (str spotify-auth-url-base auth-params)]
    (do
      (swap! stored-state assoc :state state)
      (redirect auth-url))))                                ;; add header to set cookie

(defn- build-token-request [code]
  (let [body {:grant_type   "authorization_code"
              :code         code
              :redirect_uri redirect-url}
        auth-header (str "Basic " (string-to-base64-string (str client-id ":" client-secret)))]
    {:form-params body
     :headers {"Authorization" auth-header}
     :content-type :x-www-form-urlencoded
     :accept :json}))

(defn- handle-token-response [response]
  (let [body (json/read-str (:body response) :key-fn keyword)]
    (prn body)))

(defn- request-token [code]
  (let [url spotify-token-url
        opts (build-token-request code)
        response (http/post url opts)]
    (handle-token-response response)))

(defn login-success [request]
  (let [query (:params request)
        code (query code-key)
        returned-state (query state-key)
        state-matches (= (:state @stored-state) returned-state)]
    (if (not state-matches)
      (redirect state-mismatch-url)
      (request-token code))))
;;