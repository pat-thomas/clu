(ns clu.server.sounds
  (:require [clojure.core.async :as async :refer [chan go timeout <! >! <!! >!!]]
            [overtone.live      :as o]))

(def msg-queue (chan 32))

(def dispatch (atom {}))

(defn listen!
  []
  (do
    (async/go-loop []
      (let [{:keys [x y] :as msg} (<! msg-queue)]
        (when-let [handler-fn (-> dispatch deref (get [x y]))]
          (handler-fn x y)))
      (recur))
    :ok))

(defn handle-message
  [{:keys [x y] :as params}]
  (go
    (>! msg-queue {:x x
                   :y y})))

(defmacro register
  [x y & handler-fn]
  `(swap! dispatch assoc [~x ~y] (fn [~'x ~'y]
                                   ~@handler-fn)))

(register
 1 1
 {:data {:x x
         :y y}})

(o/definst bell
  [freq 440]
  (* (o/env-gen (o/perc 0.05 1) :action o/FREE)
     (o/lf-tri freq)))

(comment
  (bell 320)
  )

