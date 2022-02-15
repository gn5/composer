(ns sixdim.midi.play
  (:use overtone.core)
  (:require [sixdim.score.score_nav :as nav])
            ; [overtone.at-at :refer [now]])
  (:gen-class))

(defn midi_play_location [location score midi_port midi_channel]
  (let [current_note (nav/get_score_beat
                       score 
                       (location "bar")
                       (location "current_subdiv")
                       (location (location "current_subdiv"))
                       )]
    (if (current_note "play")
    (cond
; (apply-at (+ 1000 (now)) #'overtone.midi/midi-note [midi_receivers/midi_out_virtualport 66 127 500 0])
      (= "quarter" (location "current_subdiv")) 
      (apply-at (+ (current_note "delay") (now)) 
        #'overtone.midi/midi-note 
            [midi_port 
             (:midi-note (note-info (current_note "pitch"))) 
             (current_note "vol") 
             (current_note "duration") 
             midi_channel])

      (= "eight" (location "current_subdiv")) 
      (apply-at (+ (current_note "delay") (now)) 
        #'overtone.midi/midi-note 
            [midi_port 
             (:midi-note (note-info (current_note "pitch"))) 
             (current_note "vol") 
             (current_note "duration") 
             midi_channel])

      ; (= "triplet" (location "current_subdiv")) 
      ; (apply-at (+ (current_note "delay") (now)) 
      ;   #'overtone.midi/midi-note 
      ;       [midi_port 
      ;        (:midi-note (note-info (current_note "pitch"))) 
      ;        (current_note "vol") 
      ;        (current_note "duration") 
      ;        midi_channel])
      ;
      ; (= "sixteen" (location "current_subdiv")) 
      ; (apply-at (+ (current_note "delay") (now)) 
      ;   #'overtone.midi/midi-note 
      ;       [midi_port 
      ;        (:midi-note (note-info (current_note "pitch"))) 
      ;        (current_note "vol") 
      ;        (current_note "duration") 
      ;        midi_channel])

      :else nil
      ))))
