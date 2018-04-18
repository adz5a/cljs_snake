(ns cljs_snake.slides.canvas
  (:require [reagent.core :as r :refer [atom]]))

(defn fill-style! [color ctx]
  (set! (.-fillStyle ctx) color)
  ctx)

(defn rect! [[x y] [width height] ctx]
  (.fillRect ctx x y width height)
  ctx)

(defn clear!
  ([ctx]
   (let [c (.-canvas ctx)]
     (clear! ctx '(0 0) [(.-width c) (.-height c)])))
  ([[x y] [w h] ctx]
   (.clearRect ctx x y w h)))

(defn line! [[x1 y1] [x2 y2] ctx]
  (doto ctx
    (.beginPath)
    (.moveTo x1 y1)
    (.lineTo x2 y2)
    (.stroke)))

(def Math js/Math)

(defn rotate [[x y] a]
  (let [cos (.cos Math a)
        sin (.sin Math a)]
    [(- (* cos x) (* sin y))
     (+ (* sin x) (* cos y))]))

(def PI (.-PI Math))
(def PI|2 (/ PI 2))

(def width 400)
(def height 400)

(defn pow [x n]
  (.pow js/Math x n))

;; perlin's polynom 6x5 -15x4 + 10x3
(defn perlin [x]
  (* (+ 10
        (* 6 (pow x 2))
        (* -15 x)) 
     (pow x 3)))

(defn path! [points ctx]
  {:pre [(seq? points)]}
  (.beginPath ctx)
  (let [current (first points)
        points (rest points)]
    (when-not (nil? current)
      (.moveTo ctx (first current) (second current))
      (loop [current (first points)
             points (rest points)]

        (if (nil? current)
          ; if no new point, just stroke
          (.stroke ctx)
          
          ; else draw line towards new point and recur
          (do
            (.lineTo ctx (first current) (second current))
            (recur (first points)
                   (rest points))))))))

(defn r [x x1 x2]
  (/ (- x x1)
     (- x2 x1)))

(defn interpol [inter-fn start end x]
  (let [[x1 y1] start
        [x2 y2] end
        px (-> (r x x1 x2)
               (perlin))]
    (+
     (* px y2)
     (* (- 1 px) y1))))

;; to allow for fiddle in the repl
(defonce !canvas (atom {:!ref nil
                        :ctx nil}))

(defonce canvas-component 
  (r/create-class {:component-did-mount (fn []
                                          (println "mounting canvas")
                                          (let [ctx (.getContext (:!ref @!canvas) "2d")]
                                            (swap! !canvas assoc :ctx ctx)
                                            (.transform ctx 1 0 0 -1 0 height)))
                   :reagent-render (fn []
                                     [:canvas {:width width
                                               :height height
                                               :style {:border "1px solid white"
                                                       :backgroundColor "grey"}
                                               :ref (partial swap! !canvas assoc :!ref)}])}))

(def canvas [{:title "Canvas example"}
             [:article
              [canvas-component]]])
