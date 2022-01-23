(ns sixdim.gui.windows
  (:require
    [membrane.ui :as ui]
    [sixdim.state_defs :as state_defs]
    [sixdim.gui.help_window :as help_window]
    [sixdim.gui.horizontal1 :as hoz1]
    [sixdim.gui.horizontal2 :as hoz2]
    [sixdim.gui.horizontal3 :as hoz3])
  (:gen-class))

(defn main_window [
               [bar_bpm 
               active_view_bar
               active_score
               active_scores
               active_scores_n
               active_cc
               active_ccs
               active_ccs_n

               loop_start_bar
               loop_end_bar
               location
               key_press
               selection_bar_start
               selection_bar_end
               selection_eight_start
               selection_eight_end
               log1
               bar_view_horizontal
               bar_view_vertical
               menu

               score1
               score2
               score3
               score4
               score5
               score6
               score7
               score8
               cc1
               cc2
               cc3
               cc4
               cc5
               cc6
               cc7
               cc8

               to_midi1
               to_midi2
               to_midi3
               to_midi4
               to_midi5
               to_midi6
               to_midi7
               to_midi8
               to_midi_cc1
               to_midi_cc2
               to_midi_cc3
               to_midi_cc4
               to_midi_cc5
               to_midi_cc6
               to_midi_cc7
               to_midi_cc8

               midi_channel1
               midi_channel2
               midi_channel3
               midi_channel4
               midi_channel5
               midi_channel6
               midi_channel7
               midi_channel8
               midi_channel_cc1
               midi_channel_cc2
               midi_channel_cc3
               midi_channel_cc4
               midi_channel_cc5
               midi_channel_cc6
               midi_channel_cc7
               midi_channel_cc8

               text_hoz1_1
               text_hoz1_2
               text_hoz1_3
               text_hoz1_4
               text_hoz2_1
               text_hoz2_2
               text_hoz2_3
               text_hoz2_4
               text_hoz3_1
               text_hoz3_2
               text_hoz3_3
               text_hoz3_4]
                ]
  [(ui/filled-rectangle state_defs/default_bg_color 2000 2000)
   (ui/with-color state_defs/default_color
     (ui/horizontal-layout
       (ui/padding 5 5
         (hoz1/horizontal1
               [bar_bpm 
               active_view_bar
               active_score
               active_scores
               active_scores_n
               active_cc
               active_ccs
               active_ccs_n

               loop_start_bar
               loop_end_bar
               location
               key_press
               selection_bar_start
               selection_bar_end
               selection_eight_start
               selection_eight_end
               log1
               bar_view_horizontal
               bar_view_vertical
               menu

               score1
               score2
               score3
               score4
               score5
               score6
               score7
               score8
               cc1
               cc2
               cc3
               cc4
               cc5
               cc6
               cc7
               cc8

               to_midi1
               to_midi2
               to_midi3
               to_midi4
               to_midi5
               to_midi6
               to_midi7
               to_midi8
               to_midi_cc1
               to_midi_cc2
               to_midi_cc3
               to_midi_cc4
               to_midi_cc5
               to_midi_cc6
               to_midi_cc7
               to_midi_cc8

               midi_channel1
               midi_channel2
               midi_channel3
               midi_channel4
               midi_channel5
               midi_channel6
               midi_channel7
               midi_channel8
               midi_channel_cc1
               midi_channel_cc2
               midi_channel_cc3
               midi_channel_cc4
               midi_channel_cc5
               midi_channel_cc6
               midi_channel_cc7
               midi_channel_cc8

               text_hoz1_1
               text_hoz1_2
               text_hoz1_3
               text_hoz1_4]
                ))
       (ui/padding 5 5
         (hoz2/horizontal2 
               [text_hoz2_1
               text_hoz2_2
               text_hoz2_3
               text_hoz2_4]
                           ))
       (ui/padding 5 5
         (hoz3/horizontal3
               [text_hoz3_1
               text_hoz3_2
               text_hoz3_3
               text_hoz3_4]
                           ))))])

(defn main_help_window []
  [(ui/filled-rectangle state_defs/default_bg_color 2000 2000)
   (ui/with-color state_defs/default_color
     (ui/horizontal-layout
       (ui/padding 5 5
         (help_window/help_window))))])
