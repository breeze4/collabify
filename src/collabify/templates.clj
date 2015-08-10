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

(defn- add-active-class-if-route [href current-route]
  (if (= href current-route)
    {:class "active"}
    {}))

(defn- active-route? [request]
  ())

(defn- active-li [current-route href text]
  (if (= current-route href)
    [:li {:class "active"} [:a {:href href} text]]
    [:li [:a {:href href} text]]
    ))

(defn- navbar [request]
  (let [active-route ((:compojure/route request) 1)]
    [:nav {:class "flex-nav navbar navbar-default"}
     [:div {:class "container-fluid"}
      [:div {:class "navbar-header"}
       [:button {:type          "button"
                 :class         "navbar-toggle collapsed"
                 :data-toggle   "collapse" :data-target "#navbar"
                 :aria-expanded "false" :aria-controls "navbar"}
        [:span {:class "sr-only"} "Toggle Navigation"]
        [:span {:class "icon-bar"}]
        [:span {:class "icon-bar"}]
        [:span {:class "icon-bar"}]]
       [:a {:class "navbar-brand" :href "#"} "Collabify"]]
      [:div {:id "navbar" :class "navbar-collapse collapse"}
       [:ul {:class "nav navbar-nav"}
        (active-li active-route "/" "Home")
        (active-li active-route "/playlists" "Playlists")
        (active-li active-route "/about" "About")
        ]]
      ]]))

(defn- template-core [request content]
  (let [user-id (logged-in? request)]
    (html [:head
           [:meta {:http-equiv "Content-type" :content "text/html; charset=utf-8"}]
           [:title "Collabify"]
           [:link {:rel "stylesheet" :href "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"}]
           [:link {:rel "stylesheet" :href "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css"}]
           ;[:link {:rel "stylesheet" :href "/css/app.css"}]
           ;[:script {:src "https://code.jquery.com/jquery-2.1.4.min.js"}]
           ;[:script {:src "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"}]
           ]
          [:body {:class "container flex"}
           [:div {:id "container" :class "flex-container"}
            (navbar request)
            [:main {:class "flex-content"}
             (content request)]
            ]])))

(defn about [request]
  (template-core request (fn [request] [:p "about!"])))

(defn playlists [request]
  (template-core request (fn [request] [:p "playlists!"])))

(defn index [request]
  (template-core request (fn [request] [:p "welcome to the main event"])))

;;