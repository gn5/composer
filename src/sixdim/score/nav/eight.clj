(ns sixdim.score.nav.eight
  (:use overtone.core)
  (:gen-class))

(defn get_score_beat [score bar_n beat_key beat_n]
  "get specific beat in score
   e.g. (get_score_beat score 1 \"quarter\" 1)" 
  (as-> score v            ;in score
    (nth v (- bar_n 1))    ;get bar number (e.g. 1)
    (v beat_key)           ;get beat key (e.g. "quarter") 
    (nth v (- beat_n 1)))) ;get beat number (e.g. 1)

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

