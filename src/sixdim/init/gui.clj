(ns sixdim.init.gui
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms] 
    [sixdim.state_defs :as state_defs] 
    [membrane.java2d :as java2d]
    [sixdim.gui.windows :as windows]
    [sixdim.gui.menus.logs :as menu_logs]
    )
  (:gen-class))

; init menu log: key presses options
(reset! atoms/text_hoz3_1 (menu_logs/logs "base"))

; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; ; start GUI window
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








