(ns collabify.core
  (:require
    [compojure.core :refer :all]
    [compojure.route :as route]
    [ring.middleware.file :refer [wrap-file]]
    [ring.middleware.params :refer [wrap-params]]
    [ring.middleware.json :refer [wrap-json-response]]
    [ring.util.response :refer [file-response redirect]]
    [collabify.spotify :refer [login login-success]]
    [collabify.templates :as templates]
    ))

(def user-state (atom {}))

(defroutes app-routes
           ;(GET "/" [] (file-response "index.html" {:root "resources/public"}))
           (GET "/" [] templates/index)
           (GET "/playlists" [] templates/playlists)
           (GET "/about" [] templates/about)
           (GET "/login" [] login)
           (GET "/loginSuccess" [] login-success)
           (route/not-found "<h1>Page not found</h1>"))

(defn user-state-middleware [f state]
  (fn [request]
    (f (assoc request :app-state state))))

(def app
  (-> app-routes
      (wrap-params)
      (wrap-json-response)
      (wrap-file "public")
      ))