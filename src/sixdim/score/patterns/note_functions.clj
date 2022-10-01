(ns sixdim.score.patterns.note_functions
  (:use overtone.core)
  (:require
   [sixdim.score.score :as score]
   [sixdim.score.score_nav :as nav]
   [sixdim.score.scales :as scales])
  (:gen-class))

; [nf/ch_inv [inversion_n octave 1]]
; from chord ordered in inversion_n in octave
; get first note

; [nf/nav_scale ["quarter" "-" 1 "downbeats" up 1]]
; from note of 1st previous quarter,
; get next closest downbeats up

(defn set_scale
  [score bar_n beat_key beat_n extra_gen_args scale_id]
  (let [current_note
        (nav/get_score_beat score bar_n beat_key beat_n)]
    (list (score/replace_score_note
           score bar_n beat_key beat_n
           (assoc current_note "scale_id" scale_id)))))

(defn play
  "set current note to play true or false"
  [score bar_n beat_key beat_n extra_gen_args true_false]
  (let [current_note
        (nav/get_score_beat score bar_n beat_key beat_n)]
    (list (score/replace_score_note
           score bar_n beat_key beat_n
           (assoc current_note "play" true_false)))))

(defn init
  "default init note: C0 and play false"
  [score bar_n beat_key beat_n extra_gen_args]
  (let [current_note
        (nav/get_score_beat score bar_n beat_key beat_n)
        new_C0_play0
          (assoc (score/new_note "C0" "sixteen") "play" false)]
    (list (score/replace_score_note
           score bar_n beat_key beat_n new_C0_play0))))

(defn noalter
  "leave note as is"
  [score bar_n beat_key beat_n extra_gen_args]
    (list score))

; [nf/ch_inv [inversion_n octave 1]]
; from chord ordered in inversion_n in octave
; get first note
(defn ch_inv_downbeats
  "todo c1 first note of :downbeats in first inversion"
  [score bar_n beat_key beat_n extra_gen_args 
   nth_inversion octave nth_note]
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
           (scales/get_nth_note_from_nth_inversion_octave_n  nth_note nth_inversion (:downbeats scale) octave)]
       ;(println (str "ch_inv_downbeats: "))
       ;(println (str "current_note: " current_note))
       ;(println (str "current_scale: " scale))
       ;(println (str "new_pitch: " new_pitch))
       (list (score/replace_score_note
              score bar_n beat_key beat_n 
              (assoc current_note 
                     "pitch" new_pitch
                     "play" true
                     ))))))

; e.g. [nf/nav_scale [quarter "-" 1 "downbeats" up 1]]
(defn nav_scale
  "from note of 1st previous quarter,
   get next closest downbeats up"
  [score bar_n beat_key beat_n extra_gen_args
   nav_beat nav_dir nav_n scale_div scale_dir scale_n]
  ; (println (str "nav_scale: "))
  (let [from_note
        (nav/nav score bar_n beat_key beat_n nav_beat nav_dir nav_n)
        current_note
        (nav/get_score_beat score bar_n beat_key beat_n)
        current_pattern_key
        (nav/get_score_beat
         (:patterns extra_gen_args)
         (- bar_n (:patterns_delta extra_gen_args))
         beat_key beat_n)
        scale (scales/get_scale (current_note "scale_id")
                                (:scales extra_gen_args))]
    ;(println (str "from_note: " from_note))
    (let [new_pitch
          (scales/scale_nav (from_note "pitch") scale
                            scale_div scale_dir scale_n)]

      ;(println (str "current_note: " current_note))
      ;(println (str "current_scale: " scale))
      ;(println (str "new_pitch: " new_pitch))
      (list (score/replace_score_note
             score bar_n beat_key beat_n
             (assoc from_note
                    "pitch" new_pitch
                    "play" true))))))
