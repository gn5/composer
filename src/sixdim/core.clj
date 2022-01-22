(ns sixdim.core
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms] 
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
    [sixdim.print.core :as print_core]
    [sixdim.print.score :as print_score]
    [membrane.java2d :as java2d]
    [sixdim.gui.windows :as windows]
    )
  (:gen-class))

; (print midi_out_virtualport)
; (overtone.midi/midi-note midi_out_virtualport 66 127 500 0)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; load-in custom scales 
(swap! atoms/scales scales/add_scale_Amin7sixthdim) ;Ams" "A min7 sixth-dim"

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; init note scores/voices 
(swap! atoms/score1
  (fn [a] (into [] (repeat @atoms/n_bars score/init_bar))))
(swap! atoms/score2
  (fn [a] (into [] (repeat @atoms/n_bars score/init_bar))))
(swap! atoms/score3
  (fn [a] (into [] (repeat @atoms/n_bars score/init_bar))))
(swap! atoms/score4
  (fn [a] (into [] (repeat @atoms/n_bars score/init_bar))))
(swap! atoms/score5
  (fn [a] (into [] (repeat @atoms/n_bars score/init_bar))))
(swap! atoms/score6
  (fn [a] (into [] (repeat @atoms/n_bars score/init_bar))))
(swap! atoms/score7
  (fn [a] (into [] (repeat @atoms/n_bars score/init_bar))))
(swap! atoms/score8
  (fn [a] (into [] (repeat @atoms/n_bars score/init_bar))))


; todo 
; init midi cc scores 1 to 8
; (swap! cc1 (fn [_] (into [] (repeat @n_bars score/init_cc_bar)))) 
; til cc8 

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; start metronome loop

; start metronome at bar bpm speed
(def bar_metronome (metronome state_defs/bar_bpm))
(tloop/play_loop (bar_metronome) bar_metronome 0)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; gen melody
;
(reset! atoms/gen_maps
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


(melody/gen_melody @atoms/score1 @atoms/gen_maps @atoms/scales)

(count (melody/gen_melody @atoms/score1
                          @atoms/gen_maps
                          @atoms/scales))

(reset! atoms/score1 (:score (first (melody/gen_melody
                               @atoms/score1
                               @atoms/gen_maps
                               @atoms/scales))))

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
          (play/midi_play_location
            new_location 
            @atoms/score1 
            midi/midi_out_virtualport
            @atoms/midi_channel1)))
      :else
      (remove-watch atoms/location :player1_location))))
;
; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; ; start GUI window
;
(java2d/run #(windows/main_window
               state_defs/bar_bpm 
               @atoms/loop_start_bar
               @atoms/loop_end_bar 
               @atoms/score1 ; todo @atoms/active_score
               @atoms/location @atoms/key_press
               @atoms/to_midi1 ; todo active?
               @atoms/selection_bar_start
               @atoms/selection_bar_end
               @atoms/selection_eight_start
               @atoms/selection_eight_end
               @atoms/log1 @atoms/active_view_bar
               @atoms/bar_view_horizontal
               @atoms/bar_view_vertical
               @atoms/menu
               )
            {:window-title "Composer"
             :window-start-width 850
             :window-start-height 850})
;
;
;
