(ns clu.server.sounds
  (:require [overtone.live :as o]))

(def dispatch
  {[1 1] (fn [x y]
           {:data {:x x
                   :y y}})})

(defn handle-message
  [{:keys [x y]}]
  (when-let [handler-fn (get dispatch [x y])]
    (handler-fn x y)))

(o/definst bell
  [freq 440]
  (* (o/env-gen (o/perc 0.05 1) :action o/FREE)
     (o/lf-tri freq)))

(comment
  (bell 320)
  )

