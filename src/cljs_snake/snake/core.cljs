(def default-state {:dir nil
                    :running false
                    :over false
                    :pos [0 0]})

; can only have 4 states :
; - waiting to be started: has running to false and no current moving dir
; - paused: has running to false but a current dir
; - running: has running to true and a current dir
; - over: has over to true

(def move {:right (partial map + [1 0])
           :left (partial map + [-1 0])
           :down (partial map + [0 1])
           :up (partial map + [0 -1])})

(def key-dir-map {"ArrowUp" :up
                  "ArrowDown" :down
                  "ArrowLeft" :left
                  "ArrowRight" :right})

(defn waiting?
 "Returns value true if in waiting state"
 [state]
  (let [{:keys [running dir]} state]
    (and (nil? dir)
         (false? running))))

(defn paused? [state]
  (let [{:keys [running dir]} state]
    (and dir
         (false? running))))

(defn pause! [state]
  (assoc state :running false))

(defn run-game! [state]
  (assoc state :running true))

(defn set-dir! [state dir]
  (assoc state :dir dir))

(defn running? [state]
  (let [{:keys [running dir]} state]
    (and dir
         (true? running))))

(defn over? [state]
  (let [{:keys [over]} state]
    (true? over)))

;; will not "redef" on reload
;; needs to be done from the repl
(defonce state (atom default-state))

(def SIZE 20)
(def U-SIZE 30)


(defn square [[x y]]
  [:rect {:width SIZE :height SIZE :stroke "red" :fill "none"
          :x (* SIZE x)
          :y (* SIZE y)}])

(defn universe []
  [:svg {:style {:border "1px solid"}
         :width (* SIZE U-SIZE)
         :height (* SIZE U-SIZE)}
   [square (:pos @state)]])

(defn message-box [yolo]
  [:section (cond
        (waiting? @state) [:div
                           [:input {:onClick #(swap! state run-game!)
                                    :type "button"
                                    :value "Click Here to start"}]]

        (running? @state) [:span (str "... running ..." (:dir @state))]
        (paused? @state) [:span "Game is paused, press any key to move"]

        (over? @state) [:span "GAME OVER!"])])

(defn on-key-input [event]
  ;; handles keyboard input to update the game
  (let [k (.-key event)
        space? (= " " k)]
    (cond
      (and (running? @state)
           (contains? key-dir-map k)) (do
                                      (swap! state set-dir! (key-dir-map k))
                                      (swap! state update :pos (-> @state
                                                                   (:dir)
                                                                   (move)))))))

; (reagent/render-component [:div {:tabIndex 1
;                                  :onFocus #(println "focus")
;                                  :onBlur #(println "blur")
;                                   :onKeyDown on-key-input}

;                            [universe]
;                            [message-box]]
;                           (. js/document (getElementById "app")))




(defn up-game []
  (let [{:keys [pos dir]} @state]
    (when dir
      (swap! state update
             :pos (move dir)))))

