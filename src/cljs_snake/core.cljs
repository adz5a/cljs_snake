(ns cljs_snake.core
    (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(reagent/render-component [:div "hello world"]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app' state to force rerendering depending on
  ;; your application
  ;; (swap! state update-in [:__figwheel_counter] inc)

  (println "yolo reloaded")
)
