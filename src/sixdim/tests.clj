; (load-file "src/sixdim/tests.clj")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; (pprint (vector? @score))

; (pprint "count before (swap! score add_bars_to_score ")
; (pprint (count @score))
; (swap! score add_bars_to_score empty_bar (count @score))
; (pprint "count after (swap! score add_bars_to_score ")
; (pprint (count @score))

; (pprint (vector? @score))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; (pprint "count before (swap! score remove_bars_from_score ")
; (pprint (count @score))
; (swap! score remove_bars_from_score index n_bars)
; (swap! score remove_bars_from_score 5 1)
; (pprint "count after (swap! score remove_bars_from_score ")
; (pprint (count @score))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; (shift_note "A4" + 1)
; (shift_note "A4" - 12)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; (def n_bars (atom 4)) ; init number of bars in whole score
; (def score (atom (into [] (repeat @n_bars empty_bar)))) ; init partition score


(defn gen_eighth_scalar_note
  [score bar_n beat_key beat_n]
  "generate possible scalar note: semitone or tone up or down"
  (let [previous_eighth (nav_eight score bar_n beat_key beat_n - 1)
        intervals ["+ 1"])
  )

(defn filt_sc_db [vec_to_filt score bar beat_key beat_n]
  "filter generated notes for scalar movements
   i.e. in scale tone or semitone up or down
   for 'db' quarter downbeat, note must be in 'downbeats' group
   for 'ub' quarter upbeat, note must be in 'upbeats' group"
  )

  )

