(ns sixdim.time.loop
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms] 
    [sixdim.common_fns :as common_fns])
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
              

(defn unmute_midi_scores [scores_vec] 
  (mapv #(reset! (common_fns/int_to_midi %) true) scores_vec))
(defn mute_midi_scores [scores_vec] 
  (mapv #(reset! (common_fns/int_to_midi %) false) scores_vec))
(defn unmute_midicc_scores [scores_vec] 
  (mapv #(reset! (common_fns/int_to_midicc %) true) scores_vec))
(defn mute_midicc_scores [scores_vec] 
  (mapv #(reset! (common_fns/int_to_midicc %) false) scores_vec))

; (:scores @atoms/auto_play)
; (common_fns/int_to_midi (str 1))
; (reset! (common_fns/int_to_midi 1) true)
; (reset! (common_fns/int_to_midi 1) false)
; (tloop/unmute_midi_scores [1 2])
; (tloop/mute_midi_scores [1 2])
; (tloop/unmute_midi_scores (:scores @atoms/auto_play))
; (tloop/mute_midi_scores (:scores @atoms/auto_play))

(defn auto_player
  [beat]
  (cond 
  (= 1 (:requested @atoms/auto_play))
    (do 
      (swap! atoms/auto_play assoc 
        :start_loop @atoms/loop_start_bar
        :end_loop @atoms/loop_end_bar
        :state "first_silent"
        :requested 0)
      (reset! atoms/loop_end_bar @atoms/loop_start_bar)
      (mute_midi_scores (:scores @atoms/auto_play))
      (mute_midicc_scores (:ccs @atoms/auto_play)))
  (= "first_silent" (:state @atoms/auto_play))
    (do 
      (reset! atoms/loop_end_bar (:end_loop @atoms/auto_play))
      (swap! atoms/auto_play assoc :state "first_to_last")
      (unmute_midi_scores (:scores @atoms/auto_play))
      ; (reset! (common_fns/int_to_midi 1) true)
      (unmute_midicc_scores (:ccs @atoms/auto_play)))
  (= "first_to_last" (:state @atoms/auto_play))
    (if (= (@atoms/location "bar") (:end_loop @atoms/auto_play))
    (do 
      (swap! atoms/auto_play assoc :state "inactive")
      (mute_midi_scores (:scores @atoms/auto_play))
      ; (reset! (common_fns/int_to_midi 1) false)
      (mute_midicc_scores (:ccs @atoms/auto_play))))
  ))

      ; (swap! atoms/auto_play assoc :start_loop @atoms/loop_start_bar)
      ; (swap! atoms/auto_play assoc :end_loop @atoms/loop_end_bar)
      ; (swap! atoms/auto_play assoc :state "first_silent")
      ; (swap! atoms/auto_play assoc :requested 0)

; @atoms/auto_play
(swap! atoms/auto_play assoc :requested 1)

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

(defn trigger_auto_player
  [beat metro]
    (apply-at (metro beat) #'auto_player [beat]))

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

  (trigger_auto_player (+ bar_beat 0.96) metro)
  (let [next_bar_beat (+ bar_beat 1)]
    (apply-at (metro next_bar_beat) #'play_loop [next_bar_beat metro (inc val)])))

; (play_loop (bar_metronome) bar_metronome 0)
