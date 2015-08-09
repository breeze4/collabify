(ns collabify.core
  (:require
    [compojure.core :refer :all]
    [compojure.route :as route]
    [ring.middleware.file :refer [wrap-file]]
    [ring.middleware.params :refer [wrap-params]]
    [ring.middleware.json :refer [wrap-json-response]]
    [ring.util.response :refer [file-response redirect]]
    [collabify.spotify :refer [login login-success]]
    ))

(defroutes app-routes
           (GET "/" [] (file-response "index.html" {:root "resources/public"}))
           (GET "/login" [] login)
           (GET "/loginSuccess" [] login-success)
           (route/not-found "<h1>Page not found</h1>"))

(def app
  (-> app-routes
      (wrap-params)
      (wrap-json-response)
      (wrap-file "public")))