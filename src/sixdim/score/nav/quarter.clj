(ns sixdim.score.nav.quarter
  (:use overtone.core)
  (:gen-class))

(defn get_score_beat [score bar_n beat_key beat_n]
  "get specific beat in score
   e.g. (get_score_beat score 1 \"quarter\" 1)"
  (as-> score v            ;in score
    (nth v (- bar_n 1))    ;get bar number (e.g. 1)
    (v beat_key)           ;get beat key (e.g. "quarter") 
    (nth v (- beat_n 1)))) ;get beat number (e.g. 1)

(def next_quarter_map
  "- get previous/next quarter note from key direction beat_number
   - space delimited to form map key
   - bar 1 means get beat from previous/next bar, not current"
  {"quarter + 1" {:bar 0  :beat_key "quarter"   :beat_n 2}
   "quarter + 2" {:bar 0  :beat_key "quarter"   :beat_n 3}
   "quarter + 3" {:bar 0  :beat_key "quarter"   :beat_n 4}
   "quarter + 4" {:bar 1  :beat_key "quarter"   :beat_n 1}

   "quarter - 1" {:bar 1  :beat_key "quarter"   :beat_n 4}
   "quarter - 2" {:bar 0  :beat_key "quarter"   :beat_n 1}
   "quarter - 3" {:bar 0  :beat_key "quarter"   :beat_n 2}
   "quarter - 4" {:bar 0  :beat_key "quarter"   :beat_n 3}
   
   "eight + 1" {:bar 0  :beat_key "quarter"   :beat_n 2}
   "eight + 2" {:bar 0  :beat_key "quarter"   :beat_n 3}
   "eight + 3" {:bar 0  :beat_key "quarter"   :beat_n 4}
   "eight + 4" {:bar 1  :beat_key "quarter"   :beat_n 1}

   "eight - 1" {:bar 0  :beat_key "quarter"   :beat_n 1}
   "eight - 2" {:bar 0  :beat_key "quarter"   :beat_n 2}
   "eight - 3" {:bar 0  :beat_key "quarter"   :beat_n 3}
   "eight - 4" {:bar 0  :beat_key "quarter"   :beat_n 4}

   "sixteen + 1"   {:bar 0  :beat_key "quarter" :beat_n 2}
   "sixteen + 2"   {:bar 0  :beat_key "quarter" :beat_n 2}
   "sixteen + 3"   {:bar 0  :beat_key "quarter" :beat_n 3}
   "sixteen + 4"   {:bar 0  :beat_key "quarter" :beat_n 3}
   "sixteen + 5"   {:bar 0  :beat_key "quarter" :beat_n 4}
   "sixteen + 6"   {:bar 0  :beat_key "quarter" :beat_n 4}
   "sixteen + 7"   {:bar 1  :beat_key "quarter" :beat_n 1}
   "sixteen + 8"   {:bar 1  :beat_key "quarter" :beat_n 1}

   "sixteen - 1"   {:bar 0  :beat_key "quarter" :beat_n 1}
   "sixteen - 2"   {:bar 0  :beat_key "quarter" :beat_n 1}
   "sixteen - 3"   {:bar 0  :beat_key "quarter" :beat_n 2}
   "sixteen - 4"   {:bar 0  :beat_key "quarter" :beat_n 2}
   "sixteen - 5"   {:bar 0  :beat_key "quarter" :beat_n 3}
   "sixteen - 6"   {:bar 0  :beat_key "quarter" :beat_n 3}
   "sixteen - 7"   {:bar 0  :beat_key "quarter" :beat_n 4}
   "sixteen - 8"   {:bar 0  :beat_key "quarter" :beat_n 4}

   "triplet + 1"   {:bar 0  :beat_key "quarter" :beat_n 2}
   "triplet + 2"   {:bar 0  :beat_key "quarter" :beat_n 2}
   "triplet + 3"   {:bar 0  :beat_key "quarter" :beat_n 3}
   "triplet + 4"   {:bar 0  :beat_key "quarter" :beat_n 3}
   "triplet + 5"   {:bar 0  :beat_key "quarter" :beat_n 4}
   "triplet + 6"   {:bar 0  :beat_key "quarter" :beat_n 4}
   "triplet + 7"   {:bar 1  :beat_key "quarter" :beat_n 1}
   "triplet + 8"   {:bar 1  :beat_key "quarter" :beat_n 1}

   "triplet - 1"   {:bar 0  :beat_key "quarter" :beat_n 1}
   "triplet - 2"   {:bar 0  :beat_key "quarter" :beat_n 1}
   "triplet - 3"   {:bar 0  :beat_key "quarter" :beat_n 2}
   "triplet - 4"   {:bar 0  :beat_key "quarter" :beat_n 2}
   "triplet - 5"   {:bar 0  :beat_key "quarter" :beat_n 3}
   "triplet - 6"   {:bar 0  :beat_key "quarter" :beat_n 3}
   "triplet - 7"   {:bar 0  :beat_key "quarter" :beat_n 4}
   "triplet - 8"   {:bar 0  :beat_key "quarter" :beat_n 4}})

(defn get_next_quarter_map
  [score bar_n beat_key beat_n direction]
  "get previous or next eight beat map in score
   direction must be + or - (for next or previous eight)"
  (if (= direction +)
    (next_quarter_map (str beat_key " + "  beat_n))
    (next_quarter_map (str beat_key " - "  beat_n))))

(defn get_next_quarter
  [score bar_n beat_key beat_n direction]
  "get previous or next eight beat in score
   direction must be function + or - (for next or previous eight)"
  (let [next_map (get_next_quarter_map score bar_n beat_key beat_n direction)]
    (get_score_beat score
                    ; substract or add a bar if necessary, if crossing
                    (direction bar_n (:bar next_map))
                    (:beat_key next_map)
                    (:beat_n next_map))))
