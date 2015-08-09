(ns collabify.token
  (:require
    [environ.core :refer [env]]
    [monger.core :as mg]
    [monger.collection :as mc]
    [clj-time.local :as l]
    [monger.joda-time]
    [monger.json])
  (:import (org.bson.types ObjectId)))

(def db-url (env :database-url))
(def db-port (env :database-port))

(defn- conn []
  (let [conn (mg/connect {:host db-url :port db-port})
        db (mg/get-db conn "collabify")]
    db))

(defn save-token [token-body]
  (let [db (conn)
        collection "tokens"
        access-token (:access_token token-body)
        refresh-token (:refresh_token token-body)
        token-type (:token_type token-body)
        expires-in (.plusSeconds (l/local-now) (:expires_in token-body))
        doc {:_id (ObjectId.)
             :access_token access-token
             :refresh_token refresh-token
             :token_type token-type
             :expires_in expires-in}]
    (mc/insert-and-return db collection doc)))


;;