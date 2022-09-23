(ns sixdim.score.melody_pattern_keys
  (:use overtone.core)
  (:require
   [sixdim.score.score :as score]
   [sixdim.score.score_nav :as nav]
   [sixdim.score.scales :as scales])
  (:gen-class))

;; ch1d: c1 first note of :downbeats in first inversion
;; n1dq: next :downbeat from previous note on quarter-notes vector
;; n2dq: next-next :downbeat from previous note on quarter-notes vector
;; n1d8: next :downbeat from previous note on eights-notes vector
;; 0:    set note to init note C0 and play:false
;; 1:    keep note as is
(defn ch1d
  "todo c1 first note of :downbeats in first inversion"
  [score bar_n beat_key beat_n extra_gen_args]
  (let [current_note (nav/get_score_beat score bar_n beat_key beat_n)
        current_pattern_key (nav/get_score_beat (:patterns extra_gen_args) 
                                            (:patterns_delta extra_gen_args)
                                            beat_key beat_n)]
    ;(let [scale (scales/get_scale (current_note "scale_id")
    ;                              (:scales extra_gen_args))]
    ;  (let [new_pitch
    ;        (scales/down_scale_note_n (current_note "pitch") scale 1)]
        (list (score/replace_score_note
               score bar_n beat_key beat_n
               (assoc current_note "pitch" "C1")))))
                ; (assoc current_note "pitch" new_pitch)))))))

(def melody_key_functions_map
  "map all patterns key codes to function for generating note"
  {"ch1d" ch1d})