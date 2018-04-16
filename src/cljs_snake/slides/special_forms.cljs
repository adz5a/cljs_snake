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

(def syntax-special-forms [{:title "Syntax - Special forms"}
                           [:article
                            [:h3 "Binding data to symbols"]
                            [:ul {:className "lh-copy"}
                             [:li {:className "w-60"}
                              [:span "declaring a var"]
                              [:pre var-example]]
                             [:li {:className "w-60"}
                              [:span "declaring local vars"]
                              [:pre local-var]]
                             [:li {:className "w-60"}
                              [:span "if loops"]
                              [:pre (str '(if test-form
                                                         then-form
                                                         else-form)) println]]
                             [:li {:className "w-60"}
                              [:span "function declaration"]
                              [:pre (str '(fn [arg] 
                                                         body)) println]
                              [:pre (str '(defn fn-sym-name [a & args]
                                                         body))]]
                             [:li {:className "w-60"}
                              [:span ". dot special form (interop)"]
                              [:pre (str '(.log js/console "hello")) println]]
                             [:li {:className "w-60"}
                              [:span "set! special form"]
                              [:pre (str '(set! (.-innerHTML el) "hello")) println]]
                             [:li {:className "w-60"}
                              [:span "set! special form"]
                              [:pre (str '(set! (.-innerHTML el) "hello")) println]]]]])
