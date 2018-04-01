(ns cljs_snake.codemirror
  (:require [codemirror.api :as cm]
            ;; should use https://github.com/clojure/tools.reader
            ;; instead
            [cljs.reader :refer [read-string]]
            [reagent.core :as r]))

(defn- create-instance [default-value on-change dom-node]
  (let [instance (cm dom-node 
                     (clj->js {:mode "text/x-clojure"
                               :theme "cobalt"
                               :value default-value}))]
    
    (.on instance "changes" #(on-change (.getValue instance)))))

(def code-mirror-input (r/create-class 
                          {:reagent-render (fn [default-value on-change]
                                             [:span {:ref (partial create-instance default-value on-change)}])}))

(defn code-mirror [default-value]
  (let [current (atom nil)]
    
    (fn []
      [:div
       [code-mirror-input default-value println]
       [:span @current]])))
