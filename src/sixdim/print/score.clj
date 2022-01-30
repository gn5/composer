(ns sixdim.print.score
  ; (:use overtone.core)
  (:require
    [sixdim.print.core :as print_core]
    [sixdim.score.score_nav :as nav]
    ; [sixdim.state_defs :as state_defs]
    )
  (:gen-class))

(defn bar_eigth_to_vec 
"return vector of 8 eighth notes of a bar from score"
  [score bar_n] [
          (nav/get_score_beat score bar_n "quarter" 1)
          (nav/get_score_beat score bar_n "eight" 1)
          (nav/get_score_beat score bar_n "quarter" 2)
          (nav/get_score_beat score bar_n "eight" 2)
          (nav/get_score_beat score bar_n "quarter" 3)
          (nav/get_score_beat score bar_n "eight" 3)
          (nav/get_score_beat score bar_n "quarter" 4)
          (nav/get_score_beat score bar_n "eight" 4)])

(defn bar_triplet_to_vec 
"return vector of 8 triplet notes of a bar from score"
  [score bar_n] [
          (nav/get_score_beat score bar_n "triplet" 1)
          (nav/get_score_beat score bar_n "triplet" 2)
          (nav/get_score_beat score bar_n "triplet" 3)
          (nav/get_score_beat score bar_n "triplet" 4)
          (nav/get_score_beat score bar_n "triplet" 5)
          (nav/get_score_beat score bar_n "triplet" 6)
          (nav/get_score_beat score bar_n "triplet" 7)
          (nav/get_score_beat score bar_n "triplet" 8)])

(defn bar_sixteen_to_vec 
"return vector of 8 sixteen notes of a bar from score"
  [score bar_n] [
          (nav/get_score_beat score bar_n "sixteen" 1)
          (nav/get_score_beat score bar_n "sixteen" 2)
          (nav/get_score_beat score bar_n "sixteen" 3)
          (nav/get_score_beat score bar_n "sixteen" 4)
          (nav/get_score_beat score bar_n "sixteen" 5)
          (nav/get_score_beat score bar_n "sixteen" 6)
          (nav/get_score_beat score bar_n "sixteen" 7)
          (nav/get_score_beat score bar_n "sixteen" 8)])

(defn vec_notes_subset_key
"extract key from vector of notes"
  [note_key vec_notes] 
  (map #(% note_key) vec_notes))

(def vec_notes_subset_pitch 
  (partial vec_notes_subset_key "pitch"))
(def vec_notes_subset_vol
  (partial vec_notes_subset_key "vol"))
(def vec_notes_subset_duration
  (partial vec_notes_subset_key "duration"))
(def vec_notes_subset_scale
  (partial vec_notes_subset_key "scale_id"))
(def vec_notes_subset_generate
  (partial vec_notes_subset_key "generate"))

(defn print_bar_notes
"print all bar notes with fixed notes length"
  [score bar_n str_length]
  (let [
        vec_notes_eight (bar_eigth_to_vec score bar_n)
        vec_notes_triplet (bar_triplet_to_vec score bar_n)
        vec_notes_sixteen (bar_sixteen_to_vec score bar_n)
        ]
    (print_core/vec_strings_newline
      [(str (print_core/str_fixed_length (str bar_n) str_length) 
            "(bar_n) :")
       (str "eight " (print_core/vec_str_to_fixed_length
         (vec_notes_subset_pitch vec_notes_eight) str_length))
       (str "tripl " (print_core/vec_str_to_fixed_length
         (vec_notes_subset_pitch vec_notes_triplet) str_length))
       (str "sixte " (print_core/vec_str_to_fixed_length
         (vec_notes_subset_pitch vec_notes_sixteen) str_length))]
      )))

(defn print_bar_scales
"print all bar scales with fixed notes length"
  [score bar_n str_length]
  (let [
        vec_notes_eight (bar_eigth_to_vec score bar_n)
        vec_notes_triplet (bar_triplet_to_vec score bar_n)
        vec_notes_sixteen (bar_sixteen_to_vec score bar_n)
        ]
    (print_core/vec_strings_newline
      [(str (print_core/str_fixed_length (str bar_n) str_length) 
            "(bar_n) :")
       (str "eight " (print_core/vec_str_to_fixed_length
         (vec_notes_subset_scale vec_notes_eight) str_length))
       (str "tripl " (print_core/vec_str_to_fixed_length
         (vec_notes_subset_scale vec_notes_triplet) str_length))
       (str "sixte " (print_core/vec_str_to_fixed_length
         (vec_notes_subset_scale vec_notes_sixteen) str_length))]
      )))
; (print_score/print_bar_notes @atoms/score1 2 5)
; (print_score/print_bar_notes @atoms/active_score 2 5)

(defn print_score_sel_bars_notes
"print all bar notes with fixed notes length"
  [score from_bar_n to_bar_n str_length]
  (as-> (range from_bar_n (+ 1 to_bar_n)) v
        (map #(print_bar_notes score % str_length) v)
        (reduce #(str %1 "\n" %2) v)))

(defn print_score_sel_bars_scales
"print all bar scales with fixed notes length"
  [score from_bar_n to_bar_n str_length]
  (as-> (range from_bar_n (+ 1 to_bar_n)) v
        (map #(print_bar_scales score % str_length) v)
        (reduce #(str %1 "\n" %2) v)))

; (print (print_score/print_score_sel_bars_notes @atoms/score1 1 3 5))
; (print (print_score/print_score_sel_bars_notes 
         ; @atoms/active_score 1 2 5))
