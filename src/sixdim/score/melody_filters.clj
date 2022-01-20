(ns sixdim.score.melody_filters
  (:use overtone.core)
  (:require
    [sixdim.score.score :as score]
    [sixdim.score.scales :as scales]
    ;not used: [sixdim.score.score_nav :as nav]
    )
  (:gen-class))

(defn filter_accept_all
 "must return string passed, not_passed or to_rerun_next_beats" 
  [score bar_n beat_key beat_n scales]
  "passed")


(defn filter_accept_bh [score bar_n beat_key beat_n scales]
 "must return string passed, not_passed or to_rerun_next_beats
   accept Barry Harris scales downbeats/upbeats" 
  (let [beat (score/get_score_beat score bar_n beat_key beat_n)]
    (let [note_octave (beat "pitch")
          scale_id (beat "scale_id")
          generate (beat "generate")]
      (let [note_str (name (:pitch-class (note-info note_octave)))
            scale (first (filter #(= scale_id (:id %)) scales))]
        (cond (and (= beat_key "quarter")
                   (scales/in_scale_group note_str scale "downbeats"))
              "passed"
              (and (= beat_key "eight")
                   (scales/in_scale_group note_str scale "upbeats"))
              "passed"
              (and (= beat_key "eight")
                   (scales/in_scale_group note_str scale "scale_chromatics"))
              "passed"
              :else "not_passed")))))

; (filter_accept_bh tscore 1 "quarter" 1 @scales)
; (filter_accept_bh tscore 1 "eight" 1 @scales)

