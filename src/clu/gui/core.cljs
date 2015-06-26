(ns clu.gui.core
  (:require [om.core :as om]
            [om.dom :as dom]
            [clu.gui.ws :as ws])
  (:require-macros [om-utils.core :refer [defcomponent]]))

(def app-state (atom {}))

(defcomponent app
  [websocket]
  (render
   (dom/button
    #js {:onClick (fn [e]
                    (ws/send-to-websocket websocket {:msg "foof"}))}
    "click here")))

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
