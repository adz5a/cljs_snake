(ns cljs_snake.core
  (:require [reagent.core :as reagent :refer [atom]]
            [cljs.spec.alpha :as s]))

(enable-console-print!)

(s/def ::mode #{"pending" "active"})
(s/def ::current-slide integer?)
(s/def ::slide-count integer?)
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
                    :current-slide 0
                    :slide-count 0})


(assert (validate-state default-state))

(defonce app-state (atom default-state
                     :validator validate-state))

(defn next-slide! []
  (swap! app-state update :current-slide inc))

(defn previous-slide! []
  (swap! app-state update :current-slide dec))

(defn slide
  "this component is a slide, it takes destructered
  keyword arguments"
  [& {:keys [title]
      :or {title "no title"}}]
  [:article {:className "pa3 pa5-ns vh-100 white"}
   [:h1 {:className "f3 f1-m f-headline-l"} title]])

(defn pagination []
  (let [{:keys [current-slide slide-count]} @app-state]
    [:span {:className "pa2 dib fixed top-0 left-0"} (str current-slide "/" slide-count)]))

(defn mode-indicator 
  "Component showing the current presentation mode"
  []
  [:span {:className "fixed top-0 right-0"} (:mode @app-state)])

(defn translation [] (str "translateY(" (-> (:current-slide @app-state)
                                           (dec)
                                           (* -100)) "vh)"))

(defn viewer 
  "this component is used a container for the presented slides"
  [& slides]
  [:div {:className "bg-black white vh-100 overflow-hidden"}
   (vector :section {:className "overflow-hidden"
                     :style {:transform (translation)
                             :transition "transform 0.8s"}
                     :tabIndex "0"
                     :onFocus #(swap! app-state assoc :mode "active")
                     :onBlur #(swap! app-state assoc :mode "pending")
                     :onKeyDown #(let [k (.-key %)]
                                   (cond
                                     (= "ArrowDown" k) (next-slide!)
                                     (= "ArrowUp" k) (previous-slide!)))}
           (first slides)
           (second slides))
   [mode-indicator]
   [pagination]])

(reagent/render-component [viewer
                           [slide :title "First Slide"]
                           (vector slide :title "Second slide")]
                          (. js/document (getElementById "app")))

(defn restart! []
  (reset! app-state default-state))

(defn on-js-reload []
  ;; optionally touch your app' state to force rerendering depending on
  ;; your application
  ;; (swap! state update-in [:__figwheel_counter] inc)

  (println "reloaded")
  )
