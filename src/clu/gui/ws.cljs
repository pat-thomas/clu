(ns clu.gui.ws)

(defn to-json
  [m]
  (.stringify (.-JSON js/window) (clj->js m)))

(defn socket-callback
  [data]
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
                        (socket-callback (js->clj (.parse (.-JSON js/window) (.-data msg)))))]])))

(defn send-to-websocket
  [ws-atom msg]
  (.send @ws-atom (to-json msg)))
