(ns collabify.core
  (:require
    [compojure.core :refer :all]
    [compojure.route :as route]
    [ring.middleware.file :refer [wrap-file]]
    [ring.middleware.resource :refer [wrap-resource]]
    [ring.middleware.content-type :refer [wrap-content-type]]
    [ring.middleware.not-modified :refer [wrap-not-modified]]
    [ring.util.response :refer [file-response resource-response redirect]]
    ))

(defroutes app-routes
           (GET "/" [] (file-response "index.html" {:root "resources/public"}))
           ;(GET "/js/react.js" [] (file-response "react.js" {:root "resources/public/js"}))
           ;(GET "/js" [] (resource-response "/*" {:root "resources/public/js"}))
           ;(GET "/js/*" [] (resource-response "app.js" {:root "resources/public/js"}))
           ;(route/resources "/" {:root "resources/public"})
           (route/not-found "<h1>Page not found</h1>"))

(def app
  (-> app-routes
      (wrap-file "public")
      ;(wrap-content-type)
      (wrap-not-modified)))