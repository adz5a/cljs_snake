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
         :article {:className "pa3 pa5-ns vh-100 white"}
         [:h1 {:className "f-headline-l"} title]
         content))

(defn viewer
  "this component is used a container for the presented slides"
  [app-state & slides]
  ;; does a side effect on mounting
  (swap! app-state assoc :slide-count (inc (count slides)))
  (println "render here")

  ;; needs to have the same signatures, names can differ
  ;; though
  (fn [app-state & slides]
    (apply vector :section {:className "overflow-hidden"
                            :style {:transform (translation app-state)
                                    :transition "transform 0.8s"}}
           (conj slides [slide {:title "Summary"}
                         [:ul (map (fn [[_ opts & __]]
                                     [:li {:key (:title opts)} (:title opts)]) 
                                   slides)]]))))
