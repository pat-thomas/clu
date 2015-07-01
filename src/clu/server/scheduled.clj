(ns clu.server.scheduled
  (:require [clojurewerkz.quartzite.scheduler :as qs]
            [clojurewerkz.quartzite.jobs :as jobs]
            [clojurewerkz.quartzite.triggers :as triggers]
            [clojure.tools.logging :as log]
            [clojurewerkz.quartzite.schedule.simple :as schedule]))

(jobs/defjob UserNotification
  [ctx]
  (let [msg "testing"]
    (println "testing")
    (log/info "testing")))

(defn init!
  []
  (let [s       (-> (qs/initialize) qs/start)
        job     (jobs/build
                 (jobs/of-type UserNotification)
                 (jobs/with-identity (jobs/key "jobs.user-notification.1")))
        trigger (triggers/build
                 (triggers/with-identity (triggers/key "jobs.user-notification.1"))
                 (triggers/start-now)
                 (triggers/with-schedule
                   (schedule/schedule
                    (schedule/with-repeat-count 10)
                    (schedule/with-interval-in-milliseconds 200))))]
    (qs/schedule s job trigger)))
