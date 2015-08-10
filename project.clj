(defproject collabify "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [ring "1.4.0"]
                 [compojure "1.4.0"]
                 [com.novemberain/monger "3.0.0-rc2"]
                 [clj-http "2.0.0"]
                 [environ "1.0.0"]
                 [org.clojure/data.codec "0.1.0"]
                 [org.clojure/data.json "0.2.6"]
                 [clj-time "0.10.0"]
                 [cheshire "5.5.0"]
                 [ring/ring-json "0.4.0"]
                 [hiccup "1.0.5"]]
  :plugins [[lein-ring "0.9.6"]
            [lein-environ "1.0.0"]]
  :ring {:handler collabify.core/app})
