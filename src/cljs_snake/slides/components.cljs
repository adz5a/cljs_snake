(ns cljs_snake.slides.components
  (:require [clojure.string :as string :refer [join]]
            [cljs.pprint :refer [pprint]]))

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

(def nl "
  ")
(def syntax-special-forms [{:title "Syntax - Special forms"}
                           [:article
                            [:h3 "Binding data to symbols"]
                            [:ul {:className "lh-copy"}
                             [:li (str "declaring a var in the current namespace:  " '(def sym value))]
                             [:li (str "declaring a local variable  " '(let sym value))]
                             [:li (join "\n" '(a b c))]]]])

(def references [{:title "Références"}
                 [:ul {:className "lh-copy"}
                  [:li 
                   [:a {:href  "http://swannodette.github.io/2015/07/29/clojurescript-17"} "ClojureScript Next (D.Nolen)"]]]])

(def slides [presentation
             syntax-literals
             syntax-special-forms
             references])
