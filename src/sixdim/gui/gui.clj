(ns sixdim.gui.gui
  (:use overtone.core)
  (:require 
             [membrane.ui :as ui
                            :refer [
                                    vertical-layout
                                    horizontal-layout
                                    button
                                    label
                                    spacer
                                    on]]
            [sixdim.time.loop :refer [
                                    location
                                    bar_bpm
                                    bar_metronome
                                    loop_start_bar
                                    loop_end_bar
                                    play_loop]]
             [sixdim.midi.play :refer [
                                    to_midi ;a
                                    midi_play_location ;
                                      ]]
            )
  (:gen-class))

; (def bar_bpm (atom 10)) ; bpm (n beats per minute) of bar (=4 quarter notes)
; (def bar_metronome (metronome @bar_bpm)) ; start metronome at bar bpm speed
; (def loop_start_bar (atom 1)) ; first bar of play loop 
; (def loop_end_bar (atom 2)) ; last bar of play loop 
; init location to last quarter and bar to 0
; (def location ; current beat location (within loop start-end) used to trigger midi player(s)
  ; (atom {"bar" 0     ; bar number
         ; "quarter" 4 ; downbeats (quarter notes)
         ; "eight" 0   ; upbeats (eighth notes in-between quarter notes)
         ; "triplet" 0 ; triplets upbeats in-between quarter notes)
         ; "sixteen" 0 ; sixteenth upbeats in-between eighth notes
         ; "current_subdiv" "quarter" ; latest updated bar sub-division/beat
         ; }))

; (defonce playing (atom ""))

(defonce key_press (atom "k"))


(defn main_gui [bar_bpm_ loop_start_bar_
                    loop_end_bar_
                location_ key_press_
                to_midi_]
  (ui/padding 5
  (vertical-layout
   (spacer 0 5)
   (on :key-press (fn [akey]
                [[
                  (cond 
                    (string? akey)
                    (reset! key_press akey)
                    (keyword? akey) 
                    "ignore")
                  ]])
    (ui/label ""))
   (spacer 0 5)
   (on :mouse-down (fn [[mouse-x mouse-y]]
                     (swap! to_midi (fn [a] (not a)))
                     nil)
       (button (str "to midi: " (str to_midi_)) nil to_midi_))
   (spacer 0 5)
   (spacer 0 5)
   (label (str "bar bpm: " bar_bpm_) (ui/font nil 19))
   (spacer 0 5)
   (label (str "bar:" (location_ "bar") 
               " q:" (location_ "quarter") 
               " e:" (location_ "eight")) (ui/font nil 19))
   (spacer 0 5)
   (label (str "loop: [" loop_start_bar_ " - " loop_end_bar_ "]") (ui/font nil 19))
   (spacer 0 5)
   (label (str "keypress: " key_press_) (ui/font nil 19))
   (spacer 0 5)
   )))
