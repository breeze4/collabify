(ns collabify.core
  (:require
    [compojure.core :refer :all]
    [compojure.route :as route]
    [ring.middleware.file :refer [wrap-file]]
    [ring.middleware.not-modified :refer [wrap-not-modified]]
    [ring.util.response :refer [file-response resource-response redirect]]
    ))

(defroutes app-routes
           (GET "/" [] (file-response "index.html" {:root "resources/public"}))
           (route/not-found "<h1>Page not found</h1>"))

(def app
  (-> app-routes
      (wrap-file "public")))