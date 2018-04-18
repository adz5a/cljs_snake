(ns cljs_snake.slides.components
  (:require [clojure.string :as string :refer [join]]
            [reagent.core :as r :refer [atom]]
            [cljs_snake.codemirror :refer [code-mirror-input code-mirror]]
            [cljs.pprint :refer [pprint]]
            [cljs_snake.slides.special-forms :refer [syntax-special-forms]]
            [cljs_snake.slides.macros :refer [macro-example]]
            [cljs_snake.slides.canvas :refer [canvas]]))

;; this uses metadata to declare privacy of the var
(def ^{:private true} presentation [{:title "Clojure, Clojurescript"}
                                    [:article 
                                     [:h3 "Introduction"]
                                     [:ul
                                      [:li "Functional programming"]
                                      [:li "Lisp"]
                                      [:li "Designed to be hosted"]
                                      [:li "Designed for concurrency"]
                                      [:li "Dynamic polymorphism"]
                                      [:li "Clear approach to state and identity"]]
                                     
                                     [:h3 "Rationale / why ?"]
                                     [:ul 
                                      [:li "No innovation in Common Lisp, Scheme since standardization"]
                                      [:li "Almost no syntax"]
                                      [:li "Can use all the ecosystem of the hosting platform (JVM, JS)"]
                                      [:li "Data structures modeled as immutable objects"]]
                                     [:a {:href "https://clojure.org/about/rationale"} "Rationale"]]])

(defn code [code-str]
  [:pre {:className "dib ml2 mt0 mb0"} code-str])

(def syntax-literals [{:title "Syntax - Basics"}
             [:article
              [:h3 "Literals"]
              [:ul {:className "lh-copy"}
               [:li "Nil: maps to null:" [code "nil"]]
               [:li "String: uses double quotes, are multiline by default" [code "\"a string\""]]
               [:li "Numbers: js literals" [code (join "  " '(1 3.14 1.1e26 "..."))]]
               [:li "In Java: chars, ratio, bytes, ..."]
               [:li "Symbols: "
                [:ul
                 [:li "Used as identifiers, but are also data, begin with non numeric char, can contain numbers and * + ! - _ ' ? :" [code "a-symbol a?*-!"]]
                 [:li "Special symbols:" [code "true false"]]]]
               [:li "Keywords: " [code (join "  " '(:keyword :1a?*!))]]]]
             
             [:article
              [:h3 "Data structures"]
              [:ul {:className "lh-copy"}
               [:li "Vector: " [code (join "  " '([]  [1 nil] (vector "string")))]]
               [:li "Maps: " [code (join "  " '({} {:yolo "swag"} {"string" nil} (hash-map key value)))]]
               [:li "Sets: " [code (join "  " '(#{} {"a " :keyword} (set coll)))]]
               [:li "Lists: " [code (join "  " '(() (symbol "a" 1)))]]]]
             
             [:article
              [:h3 "Evaluation"]
              [:p "Literals are evaluated to themselves, data structures evaluate their members before returning a value."]
              [:p "Lists are evaluated as follow"]
              [:ul
               [:li "If first element is a symbol, resolve it"]
               [:li "Evaluate in order remaining element"]
               [:li "If symbol is resolved to a function, call it with the remaining values as argument"]]]])


                             

(def syntax-in-a-nutshell [{:title "Evaluation"}
                           [:article  {:className "lh-copy"}
                            [:p
                             "Clojure programs are composed of expressions. 
                             Every form not handled specially by a special form or macro is considered by the compiler to be an expression,
                             which is evaluated to yield a value."]
                            [:p
                             "There are no declarations or statements, although sometimes expressions may be evaluated for their side-effects and their values ignored.
                             In all cases, evaluation is the same - a single object is considered by the compiler, evaluated, and its result returned.
                             If an expression needs to be compiled, it will be. There is no separate compilation step, nor any need to worry that a function you have defined is being interpreted. Clojure has no interpreter."]
                            [:p 
                             "When evaluated, symbol are resolved"]
                            [:p 
                             "Macros are functions that manipulate forms, allowing for syntactic abstraction. 
                             If the operator of a call is a symbol that names a global var that is a macro function,
                             that macro function is called and is passed the unevaluated operand forms. 
                             The return value of the macro is then evaluated in its place."]
                            [:a {:href "https://clojure.org/reference/evaluation"} "Source"]]])


(def async [{:title "core.async"}
            [:article
             [:h4 "Before"]
             [:ul
              [:li "Atoms: container holding immutable value, updated atomically"]
              [:li "Refs: container holding immutable value, updated in a dosync block"]
              [:li "Both can be used to pass data around in multiple threads (JVM), or as a
                   single source of truth (JS, cf Redux pattern)"]]
             [:h4 "With core.async"]
             [:ul
              [:li "A new primitive: channels"]
              [:li "New macros to deal with async control flow"]
              [:li "Inspired by Go (channels)& C# (async)"]
              [:li "Present in ClojureScript since first release"]]
             
             [:a {:href "http://clojure.com/blog/2013/06/28/clojure-core-async-channels.html"}
              "Blog post announcement"]]])


(def try-it-out [{:title "Try it out"}
                    [:article [code-mirror]]])


(def references [{:title "Références"}
                 [:ul {:className "lh-copy"}
                  [:li 
                   [:a {:href  "http://swannodette.github.io/2015/07/29/clojurescript-17"} "ClojureScript Next (D.Nolen)"]]]])


(def slides [presentation
             syntax-literals
             syntax-special-forms
             macro-example
             syntax-in-a-nutshell
             async
             try-it-out
             references])
