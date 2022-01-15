(ns sixdim.score.melody_generators
  (:use overtone.core)
  (:require [sixdim.score.score :refer
              [default_note_volume    
               default_midi_channel
               calc_bpm_based_note_duration
               new_note 
               empty_note
               empty_bar
               n_bars
               score
               add_bars_at_score_end
               add_bars_at_score_start
               get_score_bar
               get_bars_1_to_n
               get_bars_n_to_end
               add_bars_at_score_index
               add_bars_to_score
               remove_score_bars
               replace_score_bars
               replace_score_note
               replace_bar_note]]
            [sixdim.score.score_nav :refer
              [get_score_beat 
               get_next_eight
               nav_eight]]
            [sixdim.score.scales :refer [
               scales ;atom
               shift_note_nooctave ;f
               get_new_note_octave ;f
               shift_note ;f
               add_scale_Amin7sixthdim ;f
               in_scale_group ;f
                ]])
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
        ((nav_eight score bar_n beat_key beat_n - 1) "pitch")]
          (map #(replace_score_note score bar_n beat_key beat_n; replace current note
                  (new_note (shift_note previous_eigth ; with previous note
                              (:sign %)      ;  + or -
                              (:n %)) "eight"))        ;    n semitones
            intervals_map)))

(def gen_note_from_intervals_seconds 
  (partial gen_note_from_intervals seconds))

(def gen_note_from_intervals_seconds_down 
  (partial gen_note_from_intervals seconds_down))

(def gen_note_from_intervals_seconds_up 
  (partial gen_note_from_intervals seconds_up))

