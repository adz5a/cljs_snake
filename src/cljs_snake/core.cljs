(ns cljs_snake.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(def default-state {:mode "pending"})

(defn valid? [{:keys [mode]}]
  (let [;; mode can be "pending" or "active" depending on
        ;; whether the viewer has focus or not.
        mode? (#{"pending" "active"} mode)]
    (and mode?)))

(assert (valid? default-state) "At least make the default state valid...")

(defonce app-state (atom default-state
                     :validator valid?))

(defn slide
  "this component is a slide, it takes destructered
  keyword arguments"
  [& {:keys [title]
      :or {title "no title"}}]
  [:article {:className "pa3 pa5-ns"}
   [:h1 {:className "f3 f1-m f-headline-l"} title]])

(defn mode-indicator 
  "Component showing the current presentation mode"
  []
  [:span {:className "absolute top-0 right-0"} (:mode @app-state)])

(defn viewer 
  "this component is used a container for the presented slides"
  [& slides]
  (vector :section {:className "bg-black vh-100 white"
                    :tabIndex "0"
                    :onFocus #(swap! app-state assoc :mode "active")
                    :onBlur #(swap! app-state assoc :mode "pending")
                    :onKeyDown #(println "pressed" (.-key %))}
          [mode-indicator]
          (first slides)
          (second slides)))

(reagent/render-component [viewer
                           [slide :title "First Slide"]
                           (vector slide :title "Second slide")]
                          (. js/document (getElementById "app")))

(.addEventListener js/document "onkeypress" #(println %))

(defn on-js-reload []
  ;; optionally touch your app' state to force rerendering depending on
  ;; your application
  ;; (swap! state update-in [:__figwheel_counter] inc)

  (println "yolo reloaded")
  )
