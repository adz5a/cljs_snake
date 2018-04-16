(ns cljs_snake.slides.special-forms)

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
                              [code-mirror-input (str '(def var-sym "value")) println]]
                             [:li {:className "w-60"}
                              [:span "declaring local vars"]
                              [code-mirror-input (str '(let [local-var "value"
                                                       other {:key val}])) println]]
                             [:li {:className "w-60"}
                              [:span "if loops"]
                              [code-mirror-input (str '(if test-form
                                                         then-form
                                                         else-form)) println]]
                             [:li {:className "w-60"}
                              [:span "function declaration"]
                              [code-mirror-input (str '(fn [arg] 
                                                         body)) println]
                              [code-mirror-input (str '(defn fn-sym-name [a & args]
                                                         body))]]
                             [:li {:className "w-60"}
                              [:span ". dot special form (interop)"]
                              [code-mirror-input (str '(.log js/console "hello")) println]]
                             [:li {:className "w-60"}
                              [:span "set! special form"]
                              [code-mirror-input (str '(set! (.-innerHTML el) "hello")) println]]
                             [:li {:className "w-60"}
                              [:span "set! special form"]
                              [code-mirror-input (str '(set! (.-innerHTML el) "hello")) println]]]]])
