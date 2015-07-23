(ns clu.gui.core
  (:require [om.core :as om]
            [om.dom :as dom]
            [clu.gui.ws :as ws])
  (:require-macros [om-utils.core :refer [defcomponent]]))

(def app-state (atom {}))

(defcomponent cell
  [click-handler]
  (render
   (dom/button
    #js {:className "button"
         :onClick   click-handler})))

(defcomponent app
  [websocket]
  (render
   (apply
    dom/div
    (for [y (range 8)]
      (apply
       dom/div
       (for [x (range 8)]
         (om/build cell data {:opts {:click-handler (fn [_]
                                                      (ws/send-to-websocket websocket {:x x
                                                                                       :y y}))}})))))))

(defn main
  []
  (let [ws-atom (atom nil)]
    (do
      (enable-console-print!)
      (ws/initialize-websocket ws-atom)
      (om/root
       app
       app-state
       {:target (. js/document (getElementById "app-container"))
        :opts   {:websocket ws-atom}}))))

(main)
