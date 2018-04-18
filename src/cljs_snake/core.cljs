(ns cljs_snake.core
  (:require [reagent.core :as reagent :refer [atom]]
            [cljs.spec.alpha :as s]
            [cljs_snake.slides.core :refer [viewer
                                            slide]]
            [cljs_snake.slides.components :refer [slides]]))

(enable-console-print!)

(s/def ::current-slide (s/and integer? (partial < 0)))
(s/def ::slide-count (s/and integer? (partial <= 0)))
(s/def ::state (s/and (s/keys :req-un [::current-slide
                                       ::slide-count])
                      (fn [{:keys [current-slide slide-count]}]
                        (<= current-slide slide-count))))

(defn validate-state [state]
  (if (s/valid? ::state state)
    true
    (do
      (s/explain ::state state)
      false)))

(def default-state {:current-slide 1
                    :slide-count (count slides)
                    :slides slides})


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

(defn navigation []
  (let [current (-> @app-state
                    (:current-slide)
                    (dec))
        get-title #(-> (nth (:slides @app-state) 
                            %
                            [slide {:title nil}])
                       (second)
                       (:title))

        prev-label (if-let [title (get-title (dec current))]
                     [:button {:className "button"
                               :onClick previous-slide!} "Previous"]

                     [:button {:className "button"
                               :style {:visibility "hidden"}}])
        
        next-label (if-let [title (get-title current)]
                     [:button {:className "button"
                               :onClick next-slide!} (str "Next: " title)]                 
                     [:button {:className "button"
                               :style {:visibility "hidden"}}])]

    (println current)
    [:div {:className "fixed bottom-0 flex justify-between w-100 pa3"
          :style {}}
     prev-label
     next-label]))


(reagent/render-component [:div {:className "vh-100 overflow-hidden"}
                           (apply vector viewer app-state
                                   (map #(apply vector slide %)
                                        slides))
                           
                              [pagination]
                              [navigation]]
                          (. js/document (getElementById "app")))

(defn restart! []
  (reset! app-state default-state))

(defn on-js-reload []
  ;; optionally touch your app' state to force rerendering depending on
  ;; your application
  ;; (swap! state update-in [:__figwheel_counter] inc)

  (println "reloaded"))
