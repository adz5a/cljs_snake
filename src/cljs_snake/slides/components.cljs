(ns cljs_snake.slides.components)

;; this uses metadata to declare privacy of the var
(def ^{:private true} presentation [{:title "Clojure, Lisp, wat ?"}])

(def syntax [{:title "Syntax"}
             [:article
              [:h3 "Literals"]
              [:ul 
               [:li "String" (str '"a string")]
               [:li "Numbers" (str '(1))]]]])

(def references [{:title "Références"}
                 [:ul 
                  [:li 
                   [:a {:href  "http://swannodette.github.io/2015/07/29/clojurescript-17"} "ClojureScript Next (D.Nolen)"]]]])

(def slides [presentation
             syntax])
