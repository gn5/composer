(ns sixdim.time.loop
  (:use overtone.core)
  (:require [sixdim.atoms :as atoms]) 
  (:gen-class))

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
  (swap! atoms/location inc_location_at_subdiv "quarter" 4 @atoms/loop_start_bar @atoms/loop_end_bar "new_bar"))
  ; (println "bar trigger.. beat = " beat))
(defn quarter_player
  [beat]
  (swap! atoms/location inc_location_at_subdiv "quarter" 4 @atoms/loop_start_bar @atoms/loop_end_bar "in_bar"))
  ; (println "quarter trigger.. beat = " beat))
(defn eighth_player
  [beat]
  (swap! atoms/location inc_location_at_subdiv "eight" 4 @atoms/loop_start_bar @atoms/loop_end_bar "in_bar"))
  ; (println "eigthh trigger.. beat = " beat))
(defn triplet_player
  [beat]
  (swap! atoms/location inc_location_at_subdiv "triplet" 8 @atoms/loop_start_bar @atoms/loop_end_bar "in_bar"))
  ; (println "triplet trigger.. beat = " beat))
(defn sixteenth_player
  [beat]
  (swap! atoms/location inc_location_at_subdiv "sixteen" 8 @atoms/loop_start_bar @atoms/loop_end_bar "in_bar"))
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
