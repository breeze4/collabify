(ns collabify.spotify
  (:require
    [clj-http.client :as http]
    [environ.core :refer [env]]
    [ring.util.response :refer [redirect]]
    [ring.middleware.cookies :refer [wrap-cookies]]
    [ring.util.codec :refer [form-encode]])
  (import java.util.UUID))

(def client-id (env :spotify-client-id))
(def client-secret (env :spotify-secret))

(def redirect-url "http://localhost:3000/loginSuccess")
(def scopes "")

(def code-key "code")
(def state-key "state")
(def state-cookie-key :spotify_auth_state)
(def spotify-auth-url-base "https://accounts.spotify.com/authorize?")

(def stored-state (atom {}))

(defn- gen-state []
  (UUID/randomUUID))

(defn login [request]
  (let [state (gen-state)
        cookie {state-cookie-key state}
        auth-params (form-encode {:response_type code-key
                                  :client_id     client-id
                                  :scope         scopes
                                  :redirect_uri  redirect-url
                                  :state         state})
        auth-url (str spotify-auth-url-base auth-params)]
    (do
      (swap! stored-state assoc :state-cookie-key state)
      (redirect auth-url))))

(defn login-success [request]
  (prn request))
  ;(let [query (:params (:querystring request))
  ;      code (query code-key)
  ;      returned-state (query state-key)
  ;      state-matches (= (state-cookie-key @stored-state) returned-state)]
  ;  ))
;;