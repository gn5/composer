(ns sixdim.score.melody_filters
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

(defn filter_accept_all
 "must return string passed, not_passed or to_rerun_next_beats" 
  [score bar_n beat_key beat_n]
  "passed")


(defn filter_accept_bh [score bar_n beat_key beat_n]
 "must return string passed, not_passed or to_rerun_next_beats
   accept Barry Harris scales downbeats/upbeats" 
  (let [beat (get_score_beat score bar_n beat_key beat_n)]
    (let [note_octave (beat "pitch")
          scale_id (beat "scale_id")
          generate (beat "generate")]
      (let [note_str (name (:pitch-class (note-info note_octave)))
            scale (first (filter #(= scale_id (:id %)) @scales))]
        (cond (and (= beat_key "quarter")
                   (in_scale_group note_str scale "downbeats"))
              "passed"
              (and (= beat_key "eight")
                   (in_scale_group note_str scale "upbeats"))
              "passed"
              (and (= beat_key "eight")
                   (in_scale_group note_str scale "scale_chromatics"))
              "passed"
              :else "not_passed")))))

; (filter_accept_bh tscore 1 "quarter" 1)
; (filter_accept_bh tscore 1 "eight" 1)

