(ns cljs_snake.core
    (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(def default-state {:dir nil
                    :pos [0 0]})

(defonce state (atom default-state))

(def SIZE 20)
(def U-SIZE 30)

(defn square [[x y]]
  [:rect {:width SIZE :height SIZE :stroke "red" :fill "none"
          :x (* SIZE x)
          :y (* SIZE y)}])

(defn universe []
  [:svg {:style {:border "1px solid"}
         :width (* SIZE U-SIZE)
         :height (* SIZE U-SIZE)}
   [square (:pos @state)]])

(reagent/render-component [:div {:onKeyDown #(println (.-key %))
                                 :contentEditable true}
                           [universe]]
                          (. js/document (getElementById "app")))

(defn on-tick []
  (println "yolo"))

(defn start-game! [on-tick]
  (js/setInterval on-tick 1000))

(defonce game-tick (start-game! on-tick))

(defn stop-game! []
  (js/clearInterval game-tick))

(defn restart-game! [on-tick]
  (stop-game!)
  (set! game-tick (start-game! on-tick)))


(def move {:right (partial map + [1 0])
           :left (partial map + [-1 0])
           :down (partial map + [0 1])
           :up (partial map + [0 -1])})

(def key-dir-map {"ArrowUp" :up
                  "ArrowDown" :down
                  "ArrowLeft" :left
                  "ArrowRight" :right})
(defn up-game []
  (let [{:keys [pos dir]} @state]
    (when dir
      (swap! state update
             :pos (move dir)))))

(defn on-js-reload []
  ;; optionally touch your app' state to force rerendering depending on
  ;; your application
  ;; (swap! state update-in [:__figwheel_counter] inc)

  (println "yolo reloaded")
)
