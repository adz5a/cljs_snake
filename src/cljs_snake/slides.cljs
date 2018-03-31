(ns cljs_snake.slides)

(defn translation [app-state] 
  (str "translateY(" (-> (:current-slide @app-state)
                         (dec)
                         (* -100)) "vh)"))

(defn viewer 
  "this component is used a container for the presented slides"
  [app-state & slides]

  (apply vector :section {:className "overflow-hidden"
                    :style {:transform (translation app-state)
                            :transition "transform 0.8s"}}
          slides))

(defn slide
  "this component is a slide, it takes destructered
  keyword arguments"
  [{:keys [title]
    :or {title "no title"}}
   & content]
  (apply vector 
         :article {:className "pa3 pa5-ns vh-100 white"}
         [:h1 {:className "f-headline-l"} title]
         content))

(def summary [slide {:title "5 @ 7 Clojure"}
               [:ul {:className "f1"}
                [:li "Clojure, Lisp, wat ?"]
                [:li "Syntaxe"]
                [:li "Statements vs Expressions"]
                [:li "Références"]]])

(def presentation [slide {:title "Clojure, Lisp, wat ?"}])

(def references [slide {:title "Références"}
                 [:ul 
                  [:li 
                   [:a {:href  "http://swannodette.github.io/2015/07/29/clojurescript-17"} "ClojureScript Next (D.Nolen)"]]]])

(def slides [summary
             presentation
             references])
