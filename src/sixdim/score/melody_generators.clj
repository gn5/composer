(ns sixdim.score.melody_generators
  (:use overtone.core)
  (:require
    [sixdim.score.score :as score]
    [sixdim.score.score_nav :as nav]
    [sixdim.score.scales :as scales])
  (:gen-class))

(def seconds [{:sign + :n 1} {:sign + :n 2}
              {:sign - :n 1} {:sign - :n 2}])

(def seconds_down [{:sign - :n 1} {:sign - :n 2}])

(def seconds_up [{:sign + :n 1} {:sign + :n 2}])

(defn gen_note_from_intervals
  [intervals_map score bar_n beat_key beat_n]
  "- generate possible scalar note: semitone or tone up or down
   - from 1 score, generate vector of n (length intervals) scores
     where each score has new note at beat_n
     - new note is previous eigth note +- n semitones"
  (let [previous_eigth
        ((nav/nav_eight score bar_n beat_key beat_n - 1) "pitch")]
          (map #(score/replace_score_note score bar_n beat_key beat_n; replace current note
                  (score/new_note (scales/shift_note previous_eigth ; with previous note
                              (:sign %)      ;  + or -
                              (:n %)) "eight"))        ;    n semitones
            intervals_map)))

(def gen_note_from_intervals_seconds 
  (partial gen_note_from_intervals seconds))

(def gen_note_from_intervals_seconds_down 
  (partial gen_note_from_intervals seconds_down))

(def gen_note_from_intervals_seconds_up 
  (partial gen_note_from_intervals seconds_up))

