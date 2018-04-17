(ns cljs_snake.slides.components
  (:require [clojure.string :as string :refer [join]]
            [reagent.core :as r :refer [atom]]
            [cljs_snake.codemirror :refer [code-mirror
                                           code-mirror-input]]
            [cljs.pprint :refer [pprint]]
            [cljs_snake.slides.special-forms :refer [syntax-special-forms]]
            [cljs_snake.slides.canvas :refer [canvas]]))

;; this uses metadata to declare privacy of the var
(def ^{:private true} presentation [{:title "Clojure, Lisp, wat ?"}])

(def syntax-literals [{:title "Syntax - Basics"}
             [:article
              [:h3 "Literals"]
              [:ul {:className "lh-copy"}
               [:li "Null: only nil is available, maps to null. No undefined."]
               [:li "String: uses double quotes, are multiline by default" (str '"a string")]
               [:li "Numbers: js literals" (join "  " '(1 3.14 1.1e26 "..."))]
               [:li "Boolean: " (join '"  " '(true false)) "maps to JS boolean values"]
               [:li "In Java: chars, ratio, bytes, ..."]
               [:li "Symbols: used as identifiers, but are also data, begin with non numeric char, can contain numbers and * + ! - _ ' ? : aSymbol a?*-! is also a valid symbol"]
               [:li "Keywords: " (join "  " '(:keyword :1a?*!))]]]
             
             [:article
              [:h3 "Data structures"]
              [:ul {:className "lh-copy"}
               [:li "Vector: " (join "  " '([]  [1 nil] (vector "string")))]
               [:li "Maps: " (join "  " '({} {:yolo "swag"} {"string" nil} (hash-map key value)))]
               [:li "Sets: " (join "  " '(#{} {"a " :keyword} (set coll)))]
               [:li "Lists: " (join "  " '(() (symbol "a" 1)))]]]
             
             [:article
              [:h3 "Function calls"]
              [:ul
               [:li "A function call is a list which the first element is a symbol and others the arguments"]
               [:li (join " " '((fun-identifier arg1 arg2)))]
               [:li "All data structures literals have their own function constructors"
                [:ul
                 [:li (join " " '((vector el1 el1)))]
                 [:li (join " " '(hash-map key1 val1))]
                 [:li (join " " '(set coll))]
                 [:li (join " " '(list el1 el2))]]]]]
             ])


                             

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


(def macro-example [{:title "Macros"}
                    [:article 
                     [:p "TODO"]
                     [:pre]]])

(def try-it-out [{:title "Try it out"}
                    [:article [code-mirror (str '(+ 1 2)) ]]])


(def references [{:title "Références"}
                 [:ul {:className "lh-copy"}
                  [:li 
                   [:a {:href  "http://swannodette.github.io/2015/07/29/clojurescript-17"} "ClojureScript Next (D.Nolen)"]]]])


(def slides [presentation
             syntax-literals
             syntax-special-forms
             syntax-in-a-nutshell
             macro-example
             try-it-out
             canvas
             references])
