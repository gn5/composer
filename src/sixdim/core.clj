(ns sixdim.core
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms] 
    [sixdim.state_defs :as state_defs] 
    [sixdim.common_fns :as common_fns]
    [sixdim.midi.receivers :as midi_receivers]
    [sixdim.midi.play :as midi_play]
    [sixdim.midi.play_watchers :as midi_play_watchers]
    [sixdim.time.loop :as tloop]
    [sixdim.score.score_nav :as nav]
    [sixdim.score.melody :as melody]
    [sixdim.score.score :as score]
    [sixdim.score.undo :as undo]
    [sixdim.score.swaps.core :as score_swaps]
    [sixdim.score.scales.swaps :as scales_swaps]
    [sixdim.score.scales.watchers :as scales_watchers]
    [sixdim.score.swaps.undo :as score_swaps_undo]
    [sixdim.score.watchers.core :as score_watchers]
    [sixdim.score.watchers.undo :as score_undo_watchers]
    [sixdim.score.scales :as scales]
    [sixdim.score.scales.diminished :as scales_dim]
    [sixdim.score.melody_filters :as mfilters]
    [sixdim.score.melody_generators :as mgens]
    [sixdim.print.core :as print_core]
    [sixdim.print.score :as print_score]
    [membrane.java2d :as java2d]
    [sixdim.gui.windows :as windows]
    [sixdim.gui.key_press :as key_press]
    [sixdim.gui.help_window :as help_window]
    [sixdim.gui.menus.logs :as menu_logs]
    ; [sixdim.init.scales :as init_scales]
    )
  (:gen-class))

; (print midi_out_virtualport)
; (overtone.midi/midi-note midi_out_virtualport 66 127 500 0)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; load-in custom scales 
(load-file "src/sixdim/init/scales.clj")

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


; (melody/gen_melody @atoms/score1 @atoms/gen_maps @atoms/scales)

; (count (melody/gen_melody @atoms/score1
                          ; @atoms/gen_maps
                          ; @atoms/scales))

; (reset! atoms/score1 (:score (first (melody/gen_melody
                               ; @atoms/score1
                               ; @atoms/gen_maps
                               ; @atoms/scales))))

; init active_score from watcher of active_scores_n
(reset! atoms/active_scores_n [1])
(reset! atoms/active_ccs_n [1])
; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; ; start GUI window

; init menu log: key presses options
(reset! atoms/text_hoz3_1 (menu_logs/logs "base"))
;
(java2d/run #(windows/main_window
               [state_defs/bar_bpm 
               @atoms/active_view_bar
               @atoms/active_score
               @atoms/active_scores
               @atoms/active_scores_n
               @atoms/active_cc
               @atoms/active_ccs
               @atoms/active_ccs_n
               ; @atoms/to_midi_auto_play

               @atoms/active_generator
               @atoms/active_filter
               @atoms/active_scale ;(atom "CM6"))
               @atoms/index_scores_buffer ;(atom 1))
               @atoms/n_scores_buffer ;(atom 1))
               @atoms/n_score_active_undo ;(atom {:back 0 :forw 0}))
               @atoms/n_cc_active_undo ;(atom {:back 0 :forw 0}))

               @atoms/loop_start_bar
               @atoms/loop_end_bar
               @atoms/location
               @atoms/key_press
               @atoms/selection_bar_start
               @atoms/selection_bar_end
               @atoms/selection_eight_start
               @atoms/selection_eight_end
               @atoms/selection_triplet_start
               @atoms/selection_triplet_end
               @atoms/selection_sixteen_start
               @atoms/selection_sixteen_end

               @atoms/log1
               @atoms/bar_view_horizontal
               @atoms/bar_view_vertical
               @atoms/menu

               @atoms/score1
               @atoms/score2
               @atoms/score3
               @atoms/score4
               @atoms/score5
               @atoms/score6
               @atoms/score7
               @atoms/score8
               @atoms/cc1
               @atoms/cc2
               @atoms/cc3
               @atoms/cc4
               @atoms/cc5
               @atoms/cc6
               @atoms/cc7
               @atoms/cc8

               @atoms/to_midi1
               @atoms/to_midi2
               @atoms/to_midi3
               @atoms/to_midi4
               @atoms/to_midi5
               @atoms/to_midi6
               @atoms/to_midi7
               @atoms/to_midi8
               @atoms/to_midi_cc1
               @atoms/to_midi_cc2
               @atoms/to_midi_cc3
               @atoms/to_midi_cc4
               @atoms/to_midi_cc5
               @atoms/to_midi_cc6
               @atoms/to_midi_cc7
               @atoms/to_midi_cc8

               @atoms/midi_channel1
               @atoms/midi_channel2
               @atoms/midi_channel3
               @atoms/midi_channel4
               @atoms/midi_channel5
               @atoms/midi_channel6
               @atoms/midi_channel7
               @atoms/midi_channel8
               @atoms/midi_channel_cc1
               @atoms/midi_channel_cc2
               @atoms/midi_channel_cc3
               @atoms/midi_channel_cc4
               @atoms/midi_channel_cc5
               @atoms/midi_channel_cc6
               @atoms/midi_channel_cc7
               @atoms/midi_channel_cc8

               @atoms/text_hoz1_1
               @atoms/text_hoz1_2
               @atoms/text_hoz1_3
               @atoms/text_hoz1_4
               @atoms/text_hoz2_1
               @atoms/text_hoz2_2
               @atoms/text_hoz2_3
               @atoms/text_hoz2_4
               @atoms/text_hoz3_1
               @atoms/text_hoz3_2
               @atoms/text_hoz3_3
               @atoms/text_hoz3_4]
               )
            {:window-title "Composer"
             :window-start-width 1260
             :window-start-height 850})

; (java2d/run #(windows/main_help_window)
            ; {:window-title "Commands"
             ; :window-start-width 850
             ; :window-start-height 850})








