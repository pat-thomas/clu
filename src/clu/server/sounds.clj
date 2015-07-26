(ns clu.server.sounds
  (:require [clojure.core.async :as async :refer [chan go timeout <! >! <!! >!!]]
            [overtone.live      :as o]))

(def msg-queue (chan 32))

(defn basic-handler
  [x y]
  (println "hey hey hey")
  {:data {:x x
          :y y}})

(def dispatch
  {[1 1] #'basic-handler})

(defn listen!
  []
  (do
    (async/go-loop []
      (let [{:keys [x y] :as msg} (<! msg-queue)]
        (when-let [handler-fn (get dispatch [x y])]
          (handler-fn x y)))
      (recur))
    :ok))

(defn handle-message
  [{:keys [x y] :as params}]
  (def params params)
  (go
    (>! msg-queue {:x x
                   :y y})))

(o/definst bell
  [freq 440]
  (* (o/env-gen (o/perc 0.05 1) :action o/FREE)
     (o/lf-tri freq)))

(comment
  (bell 320)
  )

