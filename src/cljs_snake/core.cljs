(ns cljs_snake.core
    (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(defonce app-state (atom [0 0]))

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
   [square @app-state]])

(reagent/render-component [universe]
                          (. js/document (getElementById "app")))

(defn on-tick []
  (println "yolo"))

(defn start-game! [on-tick]
  (js/setInterval on-tick 1000))

(defonce game-tick (start-game! on-tick))

(defn stop-game! []
  (js/clearInterval game-tick))

(defn restart-game! [on-tick]
  (set! game-tick (start-game! on-tick)))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
