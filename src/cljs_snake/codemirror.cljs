(ns cljs_snake.codemirror
  (:require ;; should use https://github.com/clojure/tools.reader
            ;; instead
            [cljsjs.common :refer [CodeMirror]]
            [cljs.js :as cljs :refer [eval empty-state analyze-str compile-str]]
            ;; will read arbitrary code
            [cljs.tools.reader :refer [read-string]]
            [reagent.core :as r :refer [atom]]))

(defn- create-instance [default-value on-change dom-node]
  (let [instance (CodeMirror dom-node 
                             (clj->js {:mode "text/x-clojure"
                                       :theme "cobalt"
                                       :value default-value}))]

    (.on instance "changes" #(on-change (.getValue instance)))))

(defn noop [& args]
  nil)

;; example of recurive component with the map-form
(defn code-mirror-input [default-value on-change]
  (let [!ref (atom nil)
        on-change (if (fn? on-change)
                    on-change
                    noop)]
    (fn [default-value _]
      (r/create-class 
        {:component-did-mount (fn []
                                (create-instance default-value on-change @!ref))
         :reagent-render (fn [default-value on-change]
                           [:span {:ref (partial reset! !ref)}])}))))

(defn code-mirror []
  (let [current (atom {:eval nil
                       :js []
                       :input (str '(+ 1 2))})

        compiler-state (empty-state)

        on-input (partial swap! current assoc :input)

        compile-code (fn [code]
                       (let [raw-forms (str "(" code ")")
                             read-forms (read-string raw-forms)]

                         ; (println read-forms)
                         (swap! current assoc :js [])
                         (compile-str compiler-state 
                                      code
                                      "user"
                                      {:eval js/eval}
                                      (fn [res]
                                        (println res)
                                        (let [compiled-js (get-in res [:value])
                                              eval-result (js/eval compiled-js)]
                                          ; (println "compiled js: " compiled-js)
                                          (swap! current assoc :eval eval-result)
                                          (swap! current update :js conj compiled-js))))))]

    (fn inner-code-mirror[]
      [:div
       [:button {:onClick #(compile-code (:input @current))} "Run code"]
       [code-mirror-input (:input @current) on-input]
       [:section
        [:h4 "Result"]
        [:span {:className "mt2 mb2 dib"} (:eval @current)]]
       [:section
        [:h4 "Compiled JS code"]
        [:div (apply str (:js @current))]]])))
