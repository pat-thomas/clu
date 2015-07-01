(ns clu.server.core
  (:require [clojure.tools.logging :as log]
            [org.httpkit.server :as http]
            [org.httpkit.timer :as timer]
            [clojure.data.json :as json]
            [clu.server.sounds :as sounds]
            [clu.server.scheduled :as scheduled]))

(defn send-msg
  [channel {:keys [msg data] :as params}]
  (http/send! channel (json/write-str {:msg  msg
                                       :data data})))

(defn handler
  [request]
  (http/with-channel request channel
    (http/on-close channel (fn [status]
                             (log/infof "Channel closed: %s" status)))
    (http/on-receive channel (fn [data] ;; echo it back
                               (let [parsed-data (json/read-str data :key-fn keyword)]
                                 (do
                                   (log/infof "received data: %s" parsed-data)
                                   (http/send! channel (json/write-str parsed-data))))))
    (loop [id 0]
      (when (< id 10)
        (do
          (timer/schedule-task (* id 200)
                               (send-msg channel {:msg  (format "message from server: %s" id)
                                                  :data {}}))
          (recur (inc id)))))
    (timer/schedule-task 10000 (http/close channel))))

(defn main
  []
  (http/run-server #'handler {:port 9090}))

(comment
  (main)
  )

