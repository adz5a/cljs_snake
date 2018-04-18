(ns cljs_snake.utils)

(defmacro infix [infixed]
  (let [[left op right] infixed]
    (list op left right)))
