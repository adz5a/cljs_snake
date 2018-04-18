(ns cljs_snake.slides.macros
  (:require [cljs_snake.codemirror :refer [code-mirror-input code-mirror]])
  (:require-macros [cljs_snake.utils :as u]))

(def macro-example [{:title "Macros"}
                    [:article 
                     [:p "Programmatic access to the compiler using functions."]
                     [:p "They allow to define new constructs, extending it."]
                     [:p "They can be used to combine forms in useful ways, or to combine them"]
                     [:section
                      [:h4 "Example 1: When"]
                      [code-mirror-input (str '(when (pos? a)
                                                 (println "is positive")))]
                      [:span "will produce"]
                      [code-mirror-input (str '(if (pos? a)
                                                 (do (println "is positive"))))]
                      [:h4 "Example 2: Threading macros"]
                      [code-mirror-input (str '(-> {} (assoc :a 1) (assoc :b 4)))]
                      [:span "will produce"]
                      [code-mirror-input (str '(assoc (assoc {} :a 1) :b 4))]
                      
                      [:h4 "Macro example"]
                      [code-mirror-input (str '(defmacro infix [infixed]
                                                 (let [[left op right] infixed]
                                                   (list op left right))))]
                      [:ul
                       [:li "macroexpand-1"]
                       [:li "macroexpand"]
                       [:li "Syntax quote, unquote"
                        [code-mirror-input (str '(`(+ 1 (inc 2))))]]]]
                     [:p "In Clojurescript they need to be declared at compilation time"]
                     [:a {:href "https://clojure.org/reference/macros"} "Macros"]]])

(u/infix (1 + 2))
