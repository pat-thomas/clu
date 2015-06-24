(ns clu.server.core
  (:require [clojure.tools.logging :as log]
            [org.httpkit.server :as http]
            [clojure.data.json :as json]))

(defn handler
  [request]
  (http/with-channel request channel
    (http/on-close channel (fn [status]
                             (log/infof "Channel closed: %s" status)))
    (http/on-receive channel (fn [data] ;; echo it back
                               (let [parsed-data (json/read-str data :key-fn keyword)]
                                 (do
                                   (log/infof "received data: %s" parsed-data)
                                   (http/send! channel (json/write-str parsed-data))))))))

(comment
  (http/run-server handler {:port 9090})
  )

