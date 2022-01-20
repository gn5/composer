(ns sixdim.core
  (:use overtone.core)
  (:require 
    [sixdim.global :as global] 
    [sixdim.state_defs :as state_defs] 
    [sixdim.midi.receivers :as midi]
    [sixdim.midi.play :as play]
    [sixdim.time.loop :as tloop]
    [sixdim.score.score_nav :as nav]
    [sixdim.score.melody :as melody]
    [sixdim.score.score :as score]
    [sixdim.score.scales :as scales]
    [sixdim.score.melody_filters :as mfilters]
    [sixdim.score.melody_generators :as mgens]
    [membrane.java2d :as java2d]
    [sixdim.gui.windows :as windows])
  (:gen-class))

; (print midi_out_virtualport)
; (overtone.midi/midi-note midi_out_virtualport 66 127 500 0)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; load app state defs / constants
(load-file "src/sixdim/state_defs.clj")
; load app state atoms
(load-file "src/sixdim/atoms.clj")

; load-in custom scales 
(swap! scales add_scale_Amin7sixthdim) ;Ams" "A min7 sixth-dim"

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; init note scores/voices 
(reset! score1 #(into [] (repeat @n_bars score/init_bar))) 
(reset! score2 #(into [] (repeat @n_bars score/init_bar))) 
(reset! score3 #(into [] (repeat @n_bars score/init_bar))) 
(reset! score4 #(into [] (repeat @n_bars score/init_bar))) 
(reset! score5 #(into [] (repeat @n_bars score/init_bar))) 
(reset! score6 #(into [] (repeat @n_bars score/init_bar))) 
(reset! score7 #(into [] (repeat @n_bars score/init_bar))) 
(reset! score8 #(into [] (repeat @n_bars score/init_bar))) 

; todo 
; init midi cc scores 1 to 8
; (reset! cc1 #(into [] (repeat @n_bars score/init_cc_bar))) 
; (reset! cc2 #(into [] (repeat @n_bars score/init_cc_bar))) 
; (reset! cc3 #(into [] (repeat @n_bars score/init_cc_bar))) 
; (reset! cc4 #(into [] (repeat @n_bars score/init_cc_bar))) 
; (reset! cc5 #(into [] (repeat @n_bars score/init_cc_bar))) 
; (reset! cc6 #(into [] (repeat @n_bars score/init_cc_bar))) 
; (reset! cc7 #(into [] (repeat @n_bars score/init_cc_bar))) 
; (reset! cc8 #(into [] (repeat @n_bars score/init_cc_bar))) 

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; start metronome loop

; start metronome at bar bpm speed
(def bar_metronome (metronome state_defs/bar_bpm))
(tloop/play_loop (bar_metronome) bar_metronome 0)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; gen melody

(reset! gen_maps 
 [{:bar 2 :k "quarter" :n 1 
   :g mgens/gen_note_from_intervals_seconds_up
   :f mfilters/filter_accept_bh}
  {:bar 2 :k "eight" :n 1 
   :g mgens/gen_note_from_intervals_seconds_up
   :f mfilters/filter_accept_bh}
  {:bar 2 :k "quarter" :n 2 
   :g mgens/gen_note_from_intervals_seconds_up
   :f mfilters/filter_accept_bh}
  {:bar 2 :k "eight" :n 2 
   :g mgens/gen_note_from_intervals_seconds_up 
   :f mfilters/filter_accept_bh}
  {:bar 2 :k "quarter" :n 3 
   :g mgens/gen_note_from_intervals_seconds_down 
   :f mfilters/filter_accept_bh}
  {:bar 2 :k "eight" :n 3 
   :g mgens/gen_note_from_intervals_seconds_down 
   :f mfilters/filter_accept_bh}
  {:bar 2 :k "quarter" :n 4 
   :g mgens/gen_note_from_intervals_seconds_down 
   :f mfilters/filter_accept_bh}
  {:bar 2 :k "eight" :n 4 
   :g mgens/gen_note_from_intervals_seconds_down 
   :f mfilters/filter_accept_bh}])


(defn print_2 [a] [
          ((nav/get_score_beat a 2 "quarter" 1) "pitch")
          ((nav/get_score_beat a 2 "eight" 1) "pitch")
          ((nav/get_score_beat a 2 "quarter" 2) "pitch")
          ((nav/get_score_beat a 2 "eight" 2) "pitch")
          ((nav/get_score_beat a 2 "quarter" 3) "pitch")
          ((nav/get_score_beat a 2 "eight" 3) "pitch")
          ((nav/get_score_beat a 2 "quarter" 4) "pitch")
          ((nav/get_score_beat a 2 "eight" 4) "pitch")])

(count (melody/gen_melody @score1 
                          @gen_maps
                          @scales))
(print_2 (:score (nth (melody/gen_melody 
                        @score1
                        @gen_maps
                        @scales) 0)))

(reset! score1 (:score (first (melody/gen_melody 
                               @score1 
                               @gen_maps
                               @scales))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; set up midi watcher on atom true/false
;   for on/off midi send of voice 1

; - add watcher if ui button activated (= new-state true)
; - remove watcher if ui button de-activated (= new-state false)
(add-watch to_midi1 :to_midi_watcher1
  (fn [key atom old-state new-state]
    (cond 
      new-state
      (add-watch location :player1_location
        (fn [key atom old_location new_location]
          (play/midi_play_location
            new_location @score1 midi/midi_out_virtualport
            @midi_channel1)))
      :else
      (remove-watch location :player1_location))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; start GUI window

(java2d/run #(windows/main_window 
               @bar_bpm @loop_start_bar
               @loop_end_bar @active_score
               @location @key_press
               @to_midi
               @selection_bar_start
               @selection_bar_end
               @selection_eight_start
               @selection_eight_end
               @log1 @active_view_bar
               @bar_view_horizontal
               @bar_view_vertical
               @menu
               )
            {:window-title "Composer"
             :window-start-width 850 
             :window-start-height 850})



