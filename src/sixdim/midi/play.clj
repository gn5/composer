(ns sixdim.midi.play
  (:use overtone.core)
  (:require [sixdim.score.score_nav :as nav])
  (:gen-class))

(defn midi_play_location [location score midi_port midi_channel]
  (let [current_note (nav/get_score_beat
                       score 
                       (location "bar")
                       (location "current_subdiv")
                       (location (location "current_subdiv"))
                       )]
    (cond
      (= "quarter" (location "current_subdiv")) 
      (overtone.midi/midi-note midi_port 
                             (:midi-note (note-info (current_note "pitch"))) 
                             (current_note "vol") 
                             (current_note "duration") 
                             midi_channel)
      (= "eight" (location "current_subdiv")) 
      (overtone.midi/midi-note midi_port 
                             (:midi-note (note-info (current_note "pitch"))) 
                             (current_note "vol") 
                             (current_note "duration") 
                             midi_channel)
      :else nil
      )))
