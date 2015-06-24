(ns clu.gui.core
  (:require [om.core :as om]
            [om.dom :as dom])
  (:require-macros [om-utils.core :refer [defcomponent]]))

(defn socket-callback
  [{:strs [msg data]}]
  (println "msg:" msg)
  (println "data:" data))

(defn initialize-websocket
  [ws-atom]
  (reset! ws-atom (js/WebSocket. "ws://localhost:9090"))
  (doall
   (map #(aset @ws-atom (first %) (second %))
        [["onopen" (fn [] (println "OPEN"))]
         ["onclose" (fn [] (println "CLOSE"))]
         ["onerror" (fn [e]
                      (println "ERROR:" e))]
         ["onmessage" (fn [msg]
                        (let [data (.-data msg)
                              d    (js->clj (.parse (.-JSON js/window) data))]
                          (socket-callback d)))]])))

(defn to-json
  [m]
  (.stringify (.-JSON js/window) (clj->js m)))

(defn send-to-websocket
  [ws-atom msg]
  (.send @ws-atom (to-json msg)))

(def app-state (atom {}))

(defcomponent app
  [websocket]
  (render
   (dom/button
    #js {:onClick (fn [e]
                    (send-to-websocket websocket {:msg "foof"}))}
    "click here")))

(defn main
  []
  (let [websocket (atom nil)]
    (do
      (enable-console-print!)
      (initialize-websocket websocket)
      (om/root
       app
       app-state
       {:target (. js/document (getElementById "app-container"))
        :opts   {:websocket websocket}}))))

(main)
