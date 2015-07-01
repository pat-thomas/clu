(ns clu.server.sounds
  (:require [overtone.live :as o]))

(defn handle-message
  [{:keys [msg data]}])

(o/definst bell
  [freq 440]
  (* (o/env-gen (o/perc 0.05 1) :action o/FREE)
     (o/lf-tri freq)))

(comment
  (bell 320)
  )

