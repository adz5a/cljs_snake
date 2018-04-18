(ns cljs_snake.slides.core)

(defn- translation
  "calculate a vertical translation based on the
  height of the window to show the slides"
  [app-state]
  (str "translateY(" (-> (:current-slide @app-state)
                         (dec)
                         (* -100)) "vh)"))

(defn slide
  "this component is a slide, it takes destructered
  keyword arguments"
  [{:keys [title]
    :or {title "no title"}}
   & content]
  (apply vector
         :article {:className "pa3 pa5-ns vh-100"}
         [:h1 {:className "f-headline-l"} title]
         content))

(defn make-summary [slides]
  [slide {:title "Summary"}
   [:section 
    [:ul (map (fn [[_ opts & __]]
                [:li {:key (:title opts)} (:title opts)]) 
              slides)]
    [:img {:src "https://raw.githubusercontent.com/cljs/logo/master/cljs.png"
           :style {:width "3em"
                   :height "auto"}}]
    [:img {:src "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/Clojure_logo.svg/1000px-Clojure_logo.svg.png"
           :style {:width "3em"
                   :height "auto"}}]]])

(defn viewer
  "this component is used a container for the presented slides"
  [app-state & slides]
  (let [slide-count (inc (count slides))

        summary (make-summary slides)

        final-slides (apply vector summary slides)
        slide-count (count final-slides)]

    ; (println "there is " slide-count  "slides")
    ;; side effect to notify everyone
    (when-not (= (:slide-count @app-state) slide-count)
      (swap! app-state assoc 
             :slide-count slide-count
             :slides slides))

    (apply vector
           :section {:className "overflow-hidden"
                     :style {:transform (translation app-state)
                             :transition "transform 0.8s"}}
           final-slides)))
