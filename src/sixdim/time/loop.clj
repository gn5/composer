(ns sixdim.time.loop
  (:use overtone.core)
  (:gen-class))

(def bar_bpm (atom 15)) ; bpm (n beats per minute) of bar (=4 quarter notes)
(def bar_metronome (metronome @bar_bpm)) ; start metronome at bar bpm speed
(def loop_start_bar (atom 1)) ; first bar of play loop 
(def loop_end_bar (atom 2)) ; last bar of play loop 
; init location to last quarter and bar to 0
(def location ; current beat location (within loop start-end) used to trigger midi player(s)
  (atom {"bar" 0     ; bar number
         "quarter" 4 ; downbeats (quarter notes)
         "eight" 0   ; upbeats (eighth notes in-between quarter notes)
         "triplet" 0 ; triplets upbeats in-between quarter notes)
         "sixteen" 0 ; sixteenth upbeats in-between eighth notes
         "current_subdiv" "quarter" ; latest updated bar sub-division/beat
         }))

(defn inc_subdiv [current_beat start_beat end_beat]
  (if (or (>= current_beat end_beat) (< current_beat start_beat))
    start_beat
    (inc current_beat)))

(defn inc_location_at_subdiv [location subdiv subdiv_max loop_start_bar loop_end_bar option]
    (if (= option "new_bar") ; this is a quarter note by design (bar change only on first bar quarter)
    (assoc location 
           subdiv (inc_subdiv (get location subdiv) 1 subdiv_max)
           "bar" (inc_subdiv (get location "bar") loop_start_bar loop_end_bar)
           "current_subdiv" subdiv)
    (assoc location 
           subdiv (inc_subdiv (get location subdiv) 1 subdiv_max)
           "current_subdiv" subdiv)))

(defn bar_player
  [beat]
  (swap! location inc_location_at_subdiv "quarter" 4 @loop_start_bar @loop_end_bar "new_bar"))
  ; (println "bar trigger.. beat = " beat))
(defn quarter_player
  [beat]
  (swap! location inc_location_at_subdiv "quarter" 4 @loop_start_bar @loop_end_bar "in_bar"))
  ; (println "quarter trigger.. beat = " beat))
(defn eighth_player
  [beat]
  (swap! location inc_location_at_subdiv "eight" 4 @loop_start_bar @loop_end_bar "in_bar"))
  ; (println "eigthh trigger.. beat = " beat))
(defn triplet_player
  [beat]
  (swap! location inc_location_at_subdiv "triplet" 8 @loop_start_bar @loop_end_bar "in_bar"))
  ; (println "triplet trigger.. beat = " beat))
(defn sixteenth_player
  [beat]
  (swap! location inc_location_at_subdiv "sixteen" 8 @loop_start_bar @loop_end_bar "in_bar"))
  ; (println "sixteenth trigger.. beat = " beat))
              
(defn trigger_bar_note
  [beat metro]
    (apply-at (metro beat) #'bar_player [beat]))
(defn trigger_quarter_note
  [beat metro]
    (apply-at (metro beat) #'quarter_player [beat]))
(defn trigger_eighth_note
  [beat metro]
    (apply-at (metro beat) #'eighth_player [beat]))
(defn trigger_triplet_note
  [beat metro]
    (apply-at (metro beat) #'triplet_player [beat]))
(defn trigger_sixteenth_note
  [beat metro]
    (apply-at (metro beat) #'sixteenth_player [beat]))

(defn play_loop
  [bar_beat metro val]
  ; (println "looping at bar bpm; bar_beat =" bar_beat " val =" val)

  (trigger_bar_note bar_beat metro)
  (trigger_quarter_note (+ bar_beat 0.25) metro)
  (trigger_quarter_note (+ bar_beat 0.5) metro)
  (trigger_quarter_note (+ bar_beat 0.75) metro)

  (trigger_eighth_note (+ bar_beat 0.125) metro)
  (trigger_eighth_note (+ bar_beat 0.375) metro)
  (trigger_eighth_note (+ bar_beat 0.625) metro)
  (trigger_eighth_note (+ bar_beat 0.875) metro)

  (trigger_triplet_note (+ bar_beat 0.08333333) metro)
  (trigger_triplet_note (+ bar_beat 0.16666666) metro)
  (trigger_triplet_note (+ bar_beat 0.33333333) metro)
  (trigger_triplet_note (+ bar_beat 0.4166666) metro)
  (trigger_triplet_note (+ bar_beat 0.58333333) metro)
  (trigger_triplet_note (+ bar_beat 0.6666666) metro)
  (trigger_triplet_note (+ bar_beat 0.8333333) metro)
  (trigger_triplet_note (+ bar_beat 0.9166666) metro)

  (trigger_sixteenth_note (+ bar_beat 0.0625) metro)
  (trigger_sixteenth_note (+ bar_beat 0.1875) metro)
  (trigger_sixteenth_note (+ bar_beat 0.3125) metro)
  (trigger_sixteenth_note (+ bar_beat 0.4375) metro)
  (trigger_sixteenth_note (+ bar_beat 0.5625) metro)
  (trigger_sixteenth_note (+ bar_beat 0.6875) metro)
  (trigger_sixteenth_note (+ bar_beat 0.8125) metro)
  (trigger_sixteenth_note (+ bar_beat 0.9375) metro)
  (let [next_bar_beat (+ bar_beat 1)]
    (apply-at (metro next_bar_beat) #'play_loop [next_bar_beat metro (inc val)])))

; (play_loop (bar_metronome) bar_metronome 0)
