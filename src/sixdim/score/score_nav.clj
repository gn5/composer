(ns sixdim.score.score_nav
  (:use overtone.core)
  (:require
    [sixdim.score.nav.quarter :as nav_quarter]
    [sixdim.score.nav.eight :as nav_eight] 
    [sixdim.score.nav.triplet :as nav_triplet]
    [sixdim.score.nav.sixteen :as nav_sixteen]
   )
  (:gen-class))

; (defmacro fn-name [f] `(-> ~f var meta :name str))
; (fn-name +) ; (fn-name -)

(defn get_score_beat [score bar_n beat_key beat_n]
  "get specific beat in score
   e.g. (get_score_beat score 1 \"quarter\" 1)" 
  (as-> score v            ;in score
    (nth v (- bar_n 1))    ;get bar number (e.g. 1)
    (v beat_key)           ;get beat key (e.g. "quarter") 
    (nth v (- beat_n 1)))) ;get beat number (e.g. 1)

(defn nav_quarter
  [score bar_n beat_key beat_n direction iter_n]
  (if (= iter_n 0)
    (get_score_beat score bar_n beat_key beat_n)
    (let
     [next_map
      (nav_quarter/get_next_quarter_map score bar_n beat_key beat_n direction)]
      (nav_quarter score
                    ; substract or add a bar if necessary, if crossing
                 (direction bar_n (:bar next_map))
                 (:beat_key next_map)
                 (:beat_n next_map)
                 direction
                 (- iter_n 1)))))

(defn nav_eight
  [score bar_n beat_key beat_n direction iter_n] 
  (if (= iter_n 0)
    (get_score_beat score bar_n beat_key beat_n)
    (let 
      [next_map 
       (nav_eight/get_next_eight_map score bar_n beat_key beat_n direction)]
      (nav_eight score
                    ; substract or add a bar if necessary, if crossing
                    (direction bar_n (:bar next_map))
                    (:beat_key next_map)
                    (:beat_n next_map)
                    direction
                    (- iter_n 1)))))

; (nav_eight tscore 2 "quarter" 1 - 9)

(defn nav_triplet
  [score bar_n beat_key beat_n direction iter_n] 
  (if (= iter_n 0)
    (get_score_beat score bar_n beat_key beat_n)
    (let 
      [next_map 
       (nav_triplet/get_next_triplet_map score bar_n beat_key beat_n direction)]
      (nav_triplet score
                    ; substract or add a bar if necessary, if crossing
                    (direction bar_n (:bar next_map))
                    (:beat_key next_map)
                    (:beat_n next_map)
                    direction
                    (- iter_n 1)))))

(defn nav_sixteen
  [score bar_n beat_key beat_n direction iter_n] 
  (if (= iter_n 0)
    (get_score_beat score bar_n beat_key beat_n)
    (let 
      [next_map 
       (nav_sixteen/get_next_sixteen_map score bar_n beat_key beat_n direction)]
      (nav_sixteen score
                    ; substract or add a bar if necessary, if crossing
                    (direction bar_n (:bar next_map))
                    (:beat_key next_map)
                    (:beat_n next_map)
                    direction
                    (- iter_n 1)))))

(defn nav
  [score bar_n beat_key beat_n nav_beat_key direction iter_n]
  (cond
    (= nav_beat_key "quarter")
    (nav_quarter score bar_n beat_key beat_n direction iter_n)
    (= nav_beat_key "triplet")
    (nav_triplet score bar_n beat_key beat_n direction iter_n)
    (= nav_beat_key "eight")
    (nav_eight score bar_n beat_key beat_n direction iter_n)
    (= nav_beat_key "sixsteen")
    (nav_sixteen score bar_n beat_key beat_n direction iter_n)
    :else "error"))