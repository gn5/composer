(ns sixdim.score.score_nav
  (:use overtone.core)
  (:gen-class))

; (defmacro fn-name [f] `(-> ~f var meta :name str))
; (fn-name +) ; (fn-name -)

(def tbar1 {"quarter" ["b1q1" "b1q2" "b1q3" "b1q4"] 
           "eight"   ["b1e1" "b1e2" "b1e3" "b1e4"]})
(def tbar2 {"quarter" ["b2q1" "b2q2" "b2q3" "b2q4"] 
           "eight"   ["b2e1" "b2e2" "b2e3" "b2e4"]}) 
(def tbar3 {"quarter" ["b3q1" "b3q2" "b3q3" "b3q4"] 
           "eight"   ["b3e1" "b3e2" "b3e3" "b3e4"]}) 
(def tscore [tbar1 tbar2 tbar3])

(defn get_score_beat [score bar_n beat_key beat_n]
  "get specific beat in score
   e.g. (get_score_beat score 1 \"quarter\" 1)" 
  (as-> score v            ;in score
    (nth v (- bar_n 1))    ;get bar number (e.g. 1)
    (v beat_key)           ;get beat key (e.g. "quarter") 
    (nth v (- beat_n 1)))) ;get beat number (e.g. 1)

; (get_score_beat tscore 2 "quarter" 3)
; (get_score_beat tscore 1 "eight" 1)

(def next_eight_map 
  "- get previous/next eigth note from key direction beat_number
   - space delimited to form map key
   - bar 1 means get beat from previous/next bar, not current"
  {
   "quarter + 1" {:bar 0  :beat_key "eight"   :beat_n 1}
   "quarter + 2" {:bar 0  :beat_key "eight"   :beat_n 2}
   "quarter + 3" {:bar 0  :beat_key "eight"   :beat_n 3}
   "quarter + 4" {:bar 0  :beat_key "eight"   :beat_n 4}
   "eight + 1"   {:bar 0  :beat_key "quarter" :beat_n 2}
   "eight + 2"   {:bar 0  :beat_key "quarter" :beat_n 3}
   "eight + 3"   {:bar 0  :beat_key "quarter" :beat_n 4}
   "eight + 4"   {:bar 1  :beat_key "quarter" :beat_n 1}
   "quarter - 1" {:bar 1  :beat_key "eight"   :beat_n 4}
   "quarter - 2" {:bar 0  :beat_key "eight"   :beat_n 1}
   "quarter - 3" {:bar 0  :beat_key "eight"   :beat_n 2}
   "quarter - 4" {:bar 0  :beat_key "eight"   :beat_n 3}
   "eight - 1"   {:bar 0  :beat_key "quarter" :beat_n 1}
   "eight - 2"   {:bar 0  :beat_key "quarter" :beat_n 2}
   "eight - 3"   {:bar 0  :beat_key "quarter" :beat_n 3}
   "eight - 4"   {:bar 0  :beat_key "quarter" :beat_n 4}
   })

(defn get_next_eight_map 
  [score bar_n beat_key beat_n direction]
  "get previous or next eight beat map in score
   direction must be + or - (for next or previous eight)"
  (if (= direction +)
    (next_eight_map (str beat_key " + "  beat_n))
    (next_eight_map (str beat_key " - "  beat_n))))

; next_eight     (next_eight (str beat_key " " (fn-name +) " "  beat_n))]

(defn get_next_eight 
  [score bar_n beat_key beat_n direction]
  "get previous or next eight beat in score
   direction must be function + or - (for next or previous eight)"
  (let [next_map (get_next_eight_map score bar_n beat_key beat_n direction)]
      (get_score_beat score
                    ; substract or add a bar if necessary, if crossing
                    (direction bar_n (:bar next_map))
                    (:beat_key next_map)
                    (:beat_n next_map))))

; (get_score_beat tscore 2 "quarter" 1)
; (get_next_eight tscore 2 "quarter" 1 +)
; (get_next_eight tscore 2 "quarter" 1 -)

(defn nav_eight
  [score bar_n beat_key beat_n direction iter_n] 
  (if (= iter_n 0)
    (get_score_beat score bar_n beat_key beat_n)
    (let [next_map (get_next_eight_map score bar_n beat_key beat_n direction)]
      (nav_eight score
                    ; substract or add a bar if necessary, if crossing
                    (direction bar_n (:bar next_map))
                    (:beat_key next_map)
                    (:beat_n next_map)
                    direction
                    (- iter_n 1)))))

; (nav_eight tscore 2 "quarter" 1 - 9)

