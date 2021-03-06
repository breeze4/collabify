(ns collabify.core
  (:require
    [compojure.core :refer :all]
    [compojure.route :as route]
    [ring.middleware.file :refer [wrap-file]]
    [ring.middleware.params :refer [wrap-params]]
    [ring.middleware.json :refer [wrap-json-response]]
    [ring.util.response :refer [file-response redirect]]
    [collabify.spotify :refer [login login-success get-playlists]]
    [collabify.templates :as templates]
    ))

(def user-state (atom {}))

(defroutes app-routes
           (GET "/" [] (file-response "index.html" {:root "public"}))
           (GET "/loginSuccess" [] (file-response "loginSuccess.html" {:root "public"}))
           (GET "/collabify" [] (file-response "collabify.html" {:root "public"}))
           (context "/playlist/:user-id" [user-id]
                    (GET "/" [user-id] get-playlists))
           (GET "/login" [] login)
           (GET "/loginCallback" [] login-success)
           (route/not-found "<h1>Page not found</h1>"))

(defn logging-middleware [handler]
  (fn [request]
    (do
      (prn request)
      (handler request)
      )))

(def app
  (-> app-routes
      (logging-middleware)
      (wrap-params)
      (wrap-json-response)
      (wrap-file "public")
      ))