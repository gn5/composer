(ns counter
  (:require [membrane.java2d :as java2d]
            [membrane.ui :as ui
             :refer [horizontal-layout
                     button
                     label
                     spacer
                     on]]))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; (add-watch location :watcher_location
           ; (fn [key atom old-state new-state]
             ; (println "location update:" new-state)))
; (remove-watch location :watcher_location)

; (load-file "src/sixdim/tests.clj")
(def app-state (atom false))

(defn checkbox [checked?]
  ; (ui/rounded-rectangle 200 100 10)
  ; (ui/with-color [0 0 0 (/ 4 5.0)]
    ; (ui/rectangle 100 50))
  ; (ui/with-color [1 0 0]
     ; (ui/label "Hello"))
  
  (ui/bordered [0 0]
  (ui/with-color [1 0.1 0.4]
    (ui/rectangle 10 40)))
  ; (ui/with-color [0 1 0]
     ; (ui/label "Hello"))
  )

(java2d/run #(checkbox @app-state) 
            {
             :window-start-width 500
             :window-start-height 800})
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

; (def tscore [tbar tbar])
; [list of [score [list of filters]]
; (def tinput [
   ; {:score tscore 
   ; :filters [
             ; {:bar 1 :k "quarter" :n 2 
          ; :g gen_note_from_intervals_seconds 
          ; :f filter_accept_all}
             ; {:bar 1 :k "quarter" :n 2 
          ; :g gen_note_from_intervals_seconds 
          ; :f filter_accept_all}]}
   ; {:score tscore 
   ; :filters [
          ; {:bar 1 :k "quarter" :n 2 
       ; :g gen_note_from_intervals_seconds 
       ; :f filter_accept_all}
          ; {:bar 1 :k "quarter" :n 2 
       ; :g gen_note_from_intervals_seconds 
       ; :f filter_accept_all}]}])



; (defn print_2 [a] [
          ; ((get_score_beat a 2 "quarter" 1) "pitch")
          ; ((get_score_beat a 2 "eight" 1) "pitch")
          ; ((get_score_beat a 2 "quarter" 2) "pitch")
          ; ((get_score_beat a 2 "eight" 2) "pitch")
          ; ((get_score_beat a 2 "quarter" 3) "pitch")
          ; ((get_score_beat a 2 "eight" 3) "pitch")
          ; ((get_score_beat a 2 "quarter" 4) "pitch")
          ; ((get_score_beat a 2 "eight" 4) "pitch")])

; (count (gen_melody tscore @gen_maps))
; (print_2 (:score (nth (gen_melody tscore @gen_maps) 0)))
