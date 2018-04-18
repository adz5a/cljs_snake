(ns cljs_snake.slides.special-forms)

(def var-example
  "(def symbol value)")

(def local-var
  "(let [sym1 val1
      sym2 val2])")

(def if-example 
  "(if test-form
     then-form
     else-form)")

(def fn-example
  "(fn [arg]
  *body*)")

(def def-fn-example
  "(def fn-name (fn [args]
                *body*))")

(def loop-recur
  "(loop [var1 val1]
  *body*
  (recur form1)))")

(defn item [& children]
  (apply vector :li {:className "w-60 flex pa2"} children))

(defn code [code-str]
  [:pre {:className "dib ml2 mt0 mb0"} code-str])

(def syntax-special-forms [{:title "Syntax - Special forms"}
                           [:article
                            [:ul {:className "lh-copy list"}
                             [item 
                              [:span "declaring a var"]
                              [code var-example]]
                             [item 
                              [:span "declaring local vars"]
                              [code local-var]]
                             [item
                              [:span "if loops"]
                              [code if-example]]
                             [item
                              [:span "function declaration"]
                              [code fn-example]]
                             [item
                              [:span "declaring a function with a name"]
                              [code def-fn-example]]
                             [item
                              [:span "loop / recur"]
                              [code loop-recur]]
                             [item
                              [:span ". dot special form (interop)"]
                              [code (str '(.log js/console "hello"))]]
                             [item
                              [:span "set! special form"]
                              [code (str '(set! (.-innerHTML el) "hello"))]]]]])
