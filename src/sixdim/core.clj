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
                                    n_bars
                                    score
                                    add_bars_at_score_end
                                    add_bars_at_score_start
                                    get_bars_1_to_n
                                    get_bars_n_to_end
                                    add_bars_at_score_index
                                    add_bars_to_score
                                    remove_bars_from_score
                                    replace_bars_in_score]]
             [sixdim.score.score_nav :refer [
                                    get_score_beat 
                                    get_next_eight]]
             [sixdim.score.scales :refer [
                                    shift_note_nooctave
                                    get_new_note_octave
                                    shift_note
                                    scales
                                    add_scale_Amin7sixthdim
                                      ]]) ;:verbose)  
  (:gen-class))
; (load-file "src/sixdim/core.clj")

; (print midi-out-virtualport)
; (overtone.midi/midi-note midi-out-virtualport 66 127 500 0)

; (def legato (atom 0.90)) ; as percentage
; (def eighth_swing (atom 0.5)) ; placement of eighth upbeats (triplet feel is at 0.6)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; start loop
; (add-watch location :watcher_location
           ; (fn [key atom old-state new-state]
             ; (println "location update:" new-state)))

; (play_loop (bar_metronome) bar_metronome 0)
