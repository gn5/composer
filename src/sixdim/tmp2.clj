(in-ns 'sixdim.core)
    
(print @atoms/n_bars)
(print @atoms/active_cc)

(defn ch1d
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
                                (:scales extra_gen_args))]
    ;(println (str "bar_n: " bar_n))
    ;(println (str "beat_key: " beat_key))
    ;(println (str "beat_n: " beat_n)) 
    ;(println (str "current_pattern_key: " current_pattern_key))
    ;(println (str "patterns: " (:patterns extra_gen_args)))
    ;(println (str "patterns_delta: " (:patterns_delta extra_gen_args)))
    ;  (let [new_pitch
    ;        (scales/down_scale_note_n (current_note "pitch") scale 1)]
    (list (score/replace_score_note
           score bar_n beat_key beat_n
           (assoc current_note "pitch" "C1")))))
                ; (assoc current_note "pitch" new_pitch)))))))