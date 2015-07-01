(ns clu.server.sketch-runner
  (:require [quil.core :as quil]))

(defn setup
  []
  (quil/smooth)
  (quil/frame-rate 1)
  (quil/background 200))

(defn draw
  []
  (quil/stroke (quil/random 255))
  (quil/stroke-weight (quil/random 10))
  (quil/fill (quil/random 255))
  (let [diam (quil/random 100)
        x    (quil/random (quil/width))
        y    (quil/random (quil/height))]
    (quil/ellipse x y diam diam)))

(quil/defsketch main-sketch
  :title "cool"
  :setup setup
  :draw draw
  :size [400 400])
