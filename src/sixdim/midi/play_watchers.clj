(ns sixdim.midi.play_watchers
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms] 
    [sixdim.midi.receivers :as midi_receivers]
    [sixdim.midi.play :as midi_play]
    [sixdim.midi.play_watchers :as midi_play_watchers])
  (:gen-class))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; set up midi watcher on atom true/false
;   for on/off midi send of voice 1 (score1)

; - add watcher if ui button activated (= new-state true)
; - remove watcher if ui button de-activated (= new-state false)
(add-watch atoms/to_midi1 :to_midi_watcher1
  (fn [key atom old-state new-state]
    (cond
      new-state
      (add-watch atoms/location :player1_location
        (fn [key atom old_location new_location]
          (midi_play/midi_play_location
            new_location 
            @atoms/score1 
            midi_receivers/midi_out_virtualport
            @atoms/midi_channel1)))
      :else
      (remove-watch atoms/location :player1_location))))

(add-watch atoms/to_midi2 :to_midi_watcher2
  (fn [key atom old-state new-state]
    (cond
      new-state
      (add-watch atoms/location :player2_location
        (fn [key atom old_location new_location]
          (midi_play/midi_play_location
            new_location 
            @atoms/score2
            midi_receivers/midi_out_virtualport
            @atoms/midi_channel2)))
      :else
      (remove-watch atoms/location :player2_location))))
