(ns sixdim.gui.horizontal1
  (:use overtone.core)
  (:require 
    [sixdim.common_fns :as common_fns]
    [membrane.ui :as ui]
    [sixdim.gui.core :as guicore]
    [sixdim.atoms :as atoms]
    [sixdim.state_defs :as state_defs])
  (:gen-class))

(defn horizontal1 [
               [bar_bpm_ 
               active_view_bar_
               active_score_
               active_scores_
               active_scores_n_
               active_cc_
               active_ccs_
               active_ccs_n_

               active_generator_
               active_filter_
               active_scale_ ;(atom "CM6"))
               index_scores_buffer_ ;(atom 1))
               n_scores_buffer_ ;(atom 1))
               n_score_active_undo_ ;(atom {:back 0 :forw 0}))
               n_cc_active_undo_ ;(atom {:back 0 :forw 0}))

               loop_start_bar_
               loop_end_bar_
               location_
               key_press_
               selection_bar_start_
               selection_bar_end_
               selection_eight_start_
               selection_eight_end_
               selection_triplet_start_
               selection_triplet_end_
               selection_sixteen_start_
               selection_sixteen_end_

               log1_
               bar_view_horizontal_
               bar_view_vertical_
               menu_

               score1_
               score2_
               score3_
               score4_
               score5_
               score6_
               score7_
               score8_
               cc1_
               cc2_
               cc3_
               cc4_
               cc5_
               cc6_
               cc7_
               cc8_

               to_midi1_
               to_midi2_
               to_midi3_
               to_midi4_
               to_midi5_
               to_midi6_
               to_midi7_
               to_midi8_
               to_midi_cc1_
               to_midi_cc2_
               to_midi_cc3_
               to_midi_cc4_
               to_midi_cc5_
               to_midi_cc6_
               to_midi_cc7_
               to_midi_cc8_

               midi_channel1_
               midi_channel2_
               midi_channel3_
               midi_channel4_
               midi_channel5_
               midi_channel6_
               midi_channel7_
               midi_channel8_
               midi_channel_cc1_
               midi_channel_cc2_
               midi_channel_cc3_
               midi_channel_cc4_
               midi_channel_cc5_
               midi_channel_cc6_
               midi_channel_cc7_
               midi_channel_cc8_

               text_hoz1_1_
               text_hoz1_2_
               text_hoz1_3_
               text_hoz1_4_]
                   ] 
  ; (let [active_score_ (first active_scores_)
        ; active_cc_ (first active_ccs_)]
  (ui/vertical-layout 
    (guicore/ll "--------- --------- --------- --------- --------- ----------- ")

  (ui/on :key-press (fn [akey]
                [[
                  (cond
                    (string? akey)
                    (reset! atoms/key_press akey)
                    (keyword? akey)
                    "ignore")
                  ]])
    (ui/label ""))
   (guicore/ll (str "bar:" (location_ "bar")
                    " q:" (location_ "quarter")
                    " e:" (location_ "eight")
               " loop: [" loop_start_bar_
                            " - "
                            loop_end_bar_ "]"))

   (guicore/ll (str "[active scores] bar current/max: " 
                active_scores_n_ " "
                active_view_bar_ "/"
                (count active_score_)))
   (guicore/ll (str "active CCs n: " active_ccs_n_))

   (guicore/ll (str "selection bar: [" selection_bar_start_
                                 " . " selection_bar_end_ "]"))
   (guicore/ll (str "        eight: ["   selection_eight_start_
                                 " . " selection_eight_end_ "]"))
   (guicore/ll (str "      triplet: [" selection_triplet_start_
                                 " . " selection_triplet_end_ "]"))
   (guicore/ll (str "      sixteen: [" selection_sixteen_start_
                                 " . " selection_sixteen_end_ "]"))

   (guicore/ll (str "key press: " key_press_))
   (guicore/ll (str "menu: " menu_) )
   (guicore/ll (str "" log1_)) ; action log

   (guicore/ll (str "gen: " 
                    (common_fns/fn_name active_generator_)))
   (guicore/ll (str "filt: " 
                    (common_fns/fn_name active_filter_)))

               ; active_scale_ ;(atom "CM6"))
   (guicore/ll (str "scale: " active_scale_))
               ; index_scores_buffer_ ;(atom 1))
               ; n_scores_buffer_ ;(atom 1))
   (guicore/ll (str "scores buffer i/n: "
                    index_scores_buffer_ "/" n_scores_buffer_))

               ; n_score_active_undo_ ;(atom {:back 0 :forw 0}))
   (guicore/ll (str "n undo/redo active score: "
                    (:back n_score_active_undo_) "-" 
                    (:forw n_score_active_undo_)))
               ; n_cc_active_undo_ ;(atom {:back 0 :forw 0}))
   (guicore/ll (str "n undo/redo active cc: "
                    (:back n_cc_active_undo_) "-" 
                    (:forw n_cc_active_undo_)))

   (guicore/ll (str "mute/unmute midi send:"))
   (ui/horizontal-layout 
   (ui/on :mouse-down (fn [[mouse-x mouse-y]]
                     (swap! atoms/to_midi1 (fn [a] (not a)))
                     nil)
       (ui/button
         (str "1:" (str to_midi1_)) nil to_midi1_))
   (ui/on :mouse-down (fn [[mouse-x mouse-y]]
                     (swap! atoms/to_midi2 (fn [a] (not a)))
                     nil)
       (ui/button
         (str "2:" (str to_midi2_)) nil to_midi2_))
   (ui/on :mouse-down (fn [[mouse-x mouse-y]]
                     (swap! atoms/to_midi3 (fn [a] (not a)))
                     nil)
       (ui/button
         (str "3:" (str to_midi3_)) nil to_midi3_))
   (ui/on :mouse-down (fn [[mouse-x mouse-y]]
                     (swap! atoms/to_midi4 (fn [a] (not a)))
                     nil)
       (ui/button
         (str "4:" (str to_midi4_)) nil to_midi4_)))
   (ui/horizontal-layout 
   (ui/on :mouse-down (fn [[mouse-x mouse-y]]
                     (swap! atoms/to_midi5 (fn [a] (not a)))
                     nil)
       (ui/button
         (str "5:" (str to_midi5_)) nil to_midi5_))
   (ui/on :mouse-down (fn [[mouse-x mouse-y]]
                     (swap! atoms/to_midi6 (fn [a] (not a)))
                     nil)
       (ui/button
         (str "6:" (str to_midi6_)) nil to_midi6_))
   (ui/on :mouse-down (fn [[mouse-x mouse-y]]
                     (swap! atoms/to_midi7 (fn [a] (not a)))
                     nil)
       (ui/button
         (str "7:" (str to_midi7_)) nil to_midi7_))
   (ui/on :mouse-down (fn [[mouse-x mouse-y]]
                     (swap! atoms/to_midi8 (fn [a] (not a)))
                     nil)
       (ui/button
         (str "8:" (str to_midi8_)) nil to_midi8_)))

   (guicore/ll (str "mute/unmute midi cc send:"))
   (ui/horizontal-layout 
   (ui/on :mouse-down (fn [[mouse-x mouse-y]]
                     (swap! atoms/to_midi_cc1 (fn [a] (not a)))
                     nil)
       (ui/button
         (str "1:" (str to_midi_cc1_)) nil to_midi_cc1_))
   (ui/on :mouse-down (fn [[mouse-x mouse-y]]
                     (swap! atoms/to_midi_cc2 (fn [a] (not a)))
                     nil)
       (ui/button
         (str "2:" (str to_midi_cc2_)) nil to_midi_cc2_))
   (ui/on :mouse-down (fn [[mouse-x mouse-y]]
                     (swap! atoms/to_midi_cc3 (fn [a] (not a)))
                     nil)
       (ui/button
         (str "3:" (str to_midi_cc3_)) nil to_midi_cc3_))
   (ui/on :mouse-down (fn [[mouse-x mouse-y]]
                     (swap! atoms/to_midi_cc4 (fn [a] (not a)))
                     nil)
       (ui/button
         (str "4:" (str to_midi_cc4_)) nil to_midi_cc4_)))
   (ui/horizontal-layout 
   (ui/on :mouse-down (fn [[mouse-x mouse-y]]
                     (swap! atoms/to_midi_cc5 (fn [a] (not a)))
                     nil)
       (ui/button
         (str "5:" (str to_midi_cc5_)) nil to_midi_cc5_))
   (ui/on :mouse-down (fn [[mouse-x mouse-y]]
                     (swap! atoms/to_midi_cc6 (fn [a] (not a)))
                     nil)
       (ui/button
         (str "6:" (str to_midi_cc6_)) nil to_midi_cc6_))
   (ui/on :mouse-down (fn [[mouse-x mouse-y]]
                     (swap! atoms/to_midi_cc7 (fn [a] (not a)))
                     nil)
       (ui/button
         (str "7:" (str to_midi_cc7_)) nil to_midi_cc7_))
   (ui/on :mouse-down (fn [[mouse-x mouse-y]]
                     (swap! atoms/to_midi_cc8 (fn [a] (not a)))
                     nil)
       (ui/button
         (str "8:" (str to_midi_cc8_)) nil to_midi_cc8_)))

   (guicore/ll (str "bar BPM: " bar_bpm_))
   ))

