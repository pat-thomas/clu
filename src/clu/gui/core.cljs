(ns clu.gui.core
  (:require [om.core :as om])
  (:require-macros [om-utils.core :refer [defcomponent]]))

(defn main
  []
  (do
    (enable-console-print!)))
