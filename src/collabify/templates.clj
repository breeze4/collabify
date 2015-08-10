(ns collabify.templates
  (:require
    [hiccup.core :refer :all]
    [clojure.data.json :as json]))

(defn- login [user-id]
  (if (nil? user-id)
    [:p "Hello there, please login!"
     [:a {:href "/login"} "Login"]]
    [:p (str "Welcome! " user-id)]))

(defn- logged-in? [request]
  (let [user-id ((:params request) "user_id")]
    (if (nil? user-id)
      nil
      user-id))
  )

(defn index [request]
  (let [user-id (logged-in? request)]
    (html [:head
           [:meta {:http-equiv "Content-type" :content "text/html; charset=utf-8"}]
           [:title "Collabify"]]
          [:body
           [:div {:id "container"}
            (login user-id)
            ]])))

;;