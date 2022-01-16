(ns sixdim.core
  (:use overtone.core)
  (:require [sixdim.midi.receivers 
             :refer [midi-out-virtualport]]
            [sixdim.time.loop :refer [
                                    location
                                    bar_bpm
                                    bar_metronome
                                    loop_start_bar
                                    loop_end_bar
                                    play_loop]]
             [sixdim.score.score :refer [
                                    default_note_volume
                                    default_midi_channel
                                    calc_bpm_based_note_duration
                                    new_note 
                                    empty_note
                                    empty_bar
                                    init_bar
                                    test_bar
                                    n_bars
                                    score
                                    add_bars_at_score_end
                                    add_bars_at_score_start
                                    get_score_bar
                                    get_bars_1_to_n
                                    get_bars_n_to_end
                                    add_bars_at_score_index
                                    add_bars_to_score
                                    remove_score_bars
                                    replace_score_bars
                                    replace_score_note
                                    replace_bar_note]]
             [sixdim.score.score_nav :refer [
                                    get_score_beat 
                                    get_next_eight
                                    nav_eight]]
             [sixdim.score.scales :refer [
                                    scales ;atom
                                    shift_note_nooctave ;f
                                    get_new_note_octave ;f
                                    shift_note ;f
                                    add_scale_Amin7sixthdim ;f
                                    in_scale_group ;f
                                      ]]
             [sixdim.score.melody :refer [
                                    gen_maps ;atom
                                    gen_melody ;f
                                      ]]
             [sixdim.score.melody_generators :refer [
                                    seconds ;map
                                    seconds_up ;map
                                    seconds_down ;map
                                    gen_note_from_intervals ;f
                                    gen_note_from_intervals_seconds ;f
                                    gen_note_from_intervals_seconds_down ;f
                                    gen_note_from_intervals_seconds_up ;f
                                      ]]
             [sixdim.score.melody_filters :refer [
                                    filter_accept_all ;f
                                    filter_accept_bh ;f
                                      ]]
             [sixdim.midi.play :refer [
                                    to_midi ;a
                                    midi_play_location ;
                                      ]]
             [membrane.java2d :as java2d]
             [sixdim.gui.gui :refer [key_press ;a
                                     main_gui ;gui f
                                    ]]
             ) ;:verbose)  
  (:gen-class))

; (load-file "src/sixdim/core.clj")

; (print midi-out-virtualport)
; (overtone.midi/midi-note midi-out-virtualport 66 127 500 0)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; start loop
(play_loop (bar_metronome) bar_metronome 0)

(add-watch location :watcher_location
           (fn [key atom old-state new-state]
             (println "location update:" new-state)))

(remove-watch location :watcher_location)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; play to midi


; (add-watch location :player_location
           ; (fn [key atom old-state new-state]
             ; (midi_play_location new-state @score midi-out-virtualport default_midi_channel)))

; (remove-watch location :player_location)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; gen melody

(defn print_2 [a] [
          ((get_score_beat a 2 "quarter" 1) "pitch")
          ((get_score_beat a 2 "eight" 1) "pitch")
          ((get_score_beat a 2 "quarter" 2) "pitch")
          ((get_score_beat a 2 "eight" 2) "pitch")
          ((get_score_beat a 2 "quarter" 3) "pitch")
          ((get_score_beat a 2 "eight" 3) "pitch")
          ((get_score_beat a 2 "quarter" 4) "pitch")
          ((get_score_beat a 2 "eight" 4) "pitch")])

(count (gen_melody @score @gen_maps))
(print_2 (:score (nth (gen_melody @score @gen_maps) 0)))

(reset! score (:score (first (gen_melody @score @gen_maps))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; set up midi watcher for voice 1

(add-watch to_midi :to_midi_watcher
           (fn [key atom old-state new-state]
             (cond new-state
                   (add-watch location :player_location
                     (fn [key atom old-state new-state]
                       (midi_play_location
                           new-state @score
                           midi-out-virtualport default_midi_channel)))
                   :else
                   (remove-watch location :player_location))))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; start GUI


(java2d/run #(main_gui @bar_bpm @loop_start_bar
                       @loop_end_bar
                       @location @key_press
                       @to_midi)
            {:window-title "composer"
             :window-start-width 500
             :window-start-height 800})

