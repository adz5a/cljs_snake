(ns cljs_snake.core
  (:require [reagent.core :as reagent :refer [atom]]
            [cljs.spec.alpha :as s]
            [cljs_snake.slides.core :refer [viewer
                                            slide]]
            [cljs_snake.slides.components :refer [slides]]))

(enable-console-print!)

(s/def ::mode #{"pending" "active"})
(s/def ::current-slide (s/and integer? (partial < 0)))
(s/def ::slide-count (s/and integer? (partial <= 0)))
(s/def ::state (s/and (s/keys :req-un [::mode ::current-slide ::slide-count])
                      (fn [{:keys [current-slide slide-count]}]
                        (<= current-slide slide-count))))

(defn validate-state [state]
  (if (s/valid? ::state state)
    true
    (do
      (s/explain ::state state)
      false)))

(def default-state {:mode "pending"
                    :current-slide 1
                    :slide-count (count slides)})


(assert (validate-state default-state))

(defonce app-state (atom default-state
                     :validator validate-state))

(defn next-slide! []
  (swap! app-state update :current-slide inc))

(defn previous-slide! []
  (swap! app-state update :current-slide dec))


(defn pagination []
  (let [{:keys [current-slide slide-count]} @app-state]
    [:span {:className "pa2 dib fixed top-0 left-0"} (str current-slide "/" slide-count)]))

(defn mode-indicator 
  "Component showing the current presentation mode"
  []
  [:span {:className "fixed top-0 right-0"} (:mode @app-state)])


(reagent/render-component [:div {:className "bg-black white vh-100 overflow-hidden"
                                 :tabIndex "0"
                                 :onFocus #(swap! app-state assoc :mode "active")
                                 :onBlur #(swap! app-state assoc :mode "pending")
                                 :onKeyDown #(let [k (.-key %)]
                                               (cond
                                                 (= "ArrowDown" k) (next-slide!)
                                                 (= "ArrowUp" k) (previous-slide!)))}
                           (apply vector viewer app-state
                                   (map #(apply vector slide %)
                                        slides))
                           [mode-indicator]
                              [pagination]]
                          (. js/document (getElementById "app")))

(defn restart! []
  (reset! app-state default-state))

(defn on-js-reload []
  ;; optionally touch your app' state to force rerendering depending on
  ;; your application
  ;; (swap! state update-in [:__figwheel_counter] inc)

  (println "reloaded"))
