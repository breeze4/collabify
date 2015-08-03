(defproject collabify "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [ring "1.4.0"]
                 [hiccup "1.0.5"]
                 [compojure "1.4.0"]
                 [com.novemberain/monger "3.0.0-rc2"]]
  :plugins [[lein-ring "0.9.6"]]
  :ring {:handler collabify.core/app})
