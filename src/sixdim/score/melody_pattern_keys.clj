(ns sixdim.score.melody_pattern_keys
  (:use overtone.core)
  (:require
   [sixdim.score.score :as score]
   [sixdim.score.score_nav :as nav]
   [sixdim.score.scales :as scales])
  (:gen-class))

;; ch1d3: first chord note of downbeats (when ordered in first inversion) 
;;        in octave 3
;; u1dq: up-1 from previous downbeat quarter-note
;; d1dq: down-1 from previous downbeat quarter-note
;; 0:    set note to init note C0 and play:false
;; 1:    keep note as is

(defn set_play_false
  [score bar_n beat_key beat_n extra_gen_args]
  (let [current_note
        (nav/get_score_beat score bar_n beat_key beat_n)] 
      (list (score/replace_score_note
             score bar_n beat_key beat_n
             (assoc current_note "play" false)))))

(defn keep_same_note
  [score bar_n beat_key beat_n extra_gen_args]
  (let [current_note
        (nav/get_score_beat score bar_n beat_key beat_n)
        current_pattern_key
        (nav/get_score_beat
         (:patterns extra_gen_args)
         (- bar_n (:patterns_delta extra_gen_args))
         beat_key beat_n)
        scale (scales/get_scale (current_note "scale_id")
                                (:scales extra_gen_args))]
    (let [new_pitch
          (current_note "pitch")]
      ;(println (str "downbeats scale: " (:downbeats scale)))
      ;(println (str "new_pitch: " new_pitch))
      ;(println (str "current_note: " current_note))
      ;(println (str "new_note: " (assoc current_note "pitch" new_pitch)))
      (list (score/replace_score_note
             score bar_n beat_key beat_n
             (assoc current_note "pitch" new_pitch))))))

(defn ch1d3
  "todo c1 first note of :downbeats in first inversion"
  [score bar_n beat_key beat_n extra_gen_args]
  (let [current_note 
          (nav/get_score_beat score bar_n beat_key beat_n) 
        current_pattern_key 
          (nav/get_score_beat 
            (:patterns extra_gen_args) 
            (- bar_n (:patterns_delta extra_gen_args))
            beat_key beat_n)
        scale (scales/get_scale (current_note "scale_id")
                                (:scales extra_gen_args))
        ]
    ;(println (str "scale: " scale))
    ;(println (str "bar_n: " bar_n))
    ;(println (str "beat_key: " beat_key))
    ;(println (str "beat_n: " beat_n)) 
    ;(println (str "current_pattern_key: " current_pattern_key))
    ;(println (str "patterns: " (:patterns extra_gen_args)))
    ;(println (str "patterns_delta: " (:patterns_delta extra_gen_args)))
    (let [new_pitch
            (scales/get_nth_note_from_nth_inversion_octave_n 
             1 1 (:downbeats scale) 3)] 
        ;(println (str "downbeats scale: " (:downbeats scale)))
        ;(println (str "new_pitch: " new_pitch))
        ;(println (str "current_note: " current_note))
        ;(println (str "new_note: " (assoc current_note "pitch" new_pitch)))
        (list (score/replace_score_note
               score bar_n beat_key beat_n
               (assoc current_note 
                      "pitch" new_pitch
                      "play" true))))))

(def melody_key_functions_map
  "map all patterns key codes to function for generating note"
  {
   ;"0" default_note_play_false
   ;0 default_note_play_false
   1 keep_same_note
   "1" keep_same_note
   "ch1d3" ch1d3
   "play_0" set_play_false
   ;todo "ch1d4" ch1d4
   ;todo "u1dq" u1dq
   ;todo "d1dq" d1dq
   })