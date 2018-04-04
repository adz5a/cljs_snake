(ns cljs_snake.codemirror
  (:require [codemirror.api :as cm]
            ;; should use https://github.com/clojure/tools.reader
            ;; instead
            [cljs.reader :as reader]
            [cljs.js :as cljs :refer [eval empty-state]]
            ;; will read arbitrary code
            [cljs.tools.reader :refer [read-string]]
            [reagent.core :as r :refer [atom]]))

(def compiler-state (empty-state))

(def yoolo "swag")

(defn- create-instance [default-value on-change dom-node]
  (let [instance (cm dom-node 
                     (clj->js {:mode "text/x-clojure"
                               :theme "cobalt"
                               :value default-value}))]
    
    (.on instance "changes" #(on-change (.getValue instance)))))

;; example of recurive component with the map-form
(defn code-mirror-input [default-value on-change]
  (let [!ref (atom nil)]
    (fn [default-value on-change] (r/create-class 
             {:component-did-mount (fn []
                                     (create-instance default-value on-change @!ref))
              :reagent-render (fn [default-value on-change]
                                [:span {:ref (partial reset! !ref)}])}))))

(defn code-mirror [default-value]
  (let [current (atom {:eval "type code to see what happens"
                       :js []})]
    
    (fn []
      [:div
       [code-mirror-input default-value (fn [src]
                                         (let [raw-forms (str "(" src ")")
                                               read-forms (read-string raw-forms)
                                               count-forms (count read-forms)]
                                           ; (println "read-forms: " read-forms)
                                           
                                           (doall (for [form read-forms]
                                                    (eval compiler-state 
                                                          form {:eval js/eval}
                                                          (fn [res]
                                                            (let [compiled-js (get-in res [:value :source])
                                                                  eval-result (js/eval compiled-js)]
                                                              (println "compiled js: " compiled-js)
                                                              (println "evaled" eval-result)
                                                              (swap! current assoc :eval eval-result :js []))))))) 


                                          )]
       [:span (:eval @current)]
       [:div (map #(vector :pre %) (:js @current))]])))
