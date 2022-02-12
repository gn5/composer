(ns sixdim.gui.menus.base
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms]
    [sixdim.state_defs :as state_defs] 
    [sixdim.score.swaps.core :as ss]
    [sixdim.score.swaps.undo :as ss_undo]
    [sixdim.gui.menus.switch :as menu_switch]
    [sixdim.time.loop :as tloop]
    [sixdim.save :as save]
    )
  (:gen-class))

(def key_menu {
    "a" {:log1 "menu choose generator"
         :action #(menu_switch/to_menu "gens")}
    "r" {:log1 "menu choose filter"
         :action #(menu_switch/to_menu "filts")}
    "w" {:log1 "scales menu"
         :action #(menu_switch/to_menu "scales")}

    "l" {:log1 "decrement_active_score_n"
         :action #(ss/decrement_active_score_n)}
    "u" {:log1 "increment_active_score_n"
         :action #(ss/increment_active_score_n)}

    "y" {:log1 "del_one_score_at_score_end"
         :action #(ss/del_one_score_at_score_end)}
                      ; (reset! atoms/active_scores_n 
                             ; @atoms/active_scores_n))}
    "h" {:log1 "add_one_score_at_score_end"
         :action #(ss/add_one_score_at_score_end)}
                      ; (reset! atoms/active_scores_n 
                             ; @atoms/active_scores_n))}

    "f" {:log1  "reset_eight_gen_maps_with_active_gen_filt"
    :action #(ss/reset_eight_gen_maps_with_active_gen_filt)}
    "p" {:log1  "reset_fill_score_with_active_gen_map"
    :action #(ss/reset_fill_score_with_active_gen_map)}

    "0" {:log1  "previous buffer score"
    :action #(ss/score_from_previous_buffer)}
    "4" {:log1  "next buffer score"
    :action #(ss/score_from_next_buffer)}

    "c" {:log1       "decrement_active_view_bar"
         :action #(ss/decrement_active_view_bar)}
    "d" {:log1       "increment_active_view_bar"
         :action #(ss/increment_active_view_bar 
                    (count @atoms/active_score))}

    "z" {:log1       "decrement_loop_start_bar"
         :action #(ss/decrement_loop_start_bar)}
    "x" {:log1       "increment_loop_start_bar"
         :action #(ss/increment_loop_start_bar 
                     @atoms/loop_end_bar)}

    "<" {:log1       "decrement_loop_end_bar"
         :action #(ss/decrement_loop_end_bar
                    @atoms/loop_start_bar)}
    ">" {:log1       "increment_loop_end_bar"
         :action #(ss/increment_loop_end_bar 
                    (count @atoms/active_score))}

    "n" {:log1       "decrement_selection_start_bar"
         :action #(ss/decrement_selection_start_bar)}
    "e" {:log1       "increment_selection_start_bar"
         :action #(ss/increment_selection_start_bar)}
    "i" {:log1       "decrement_selection_end_bar"
         :action #(ss/decrement_selection_end_bar)}
    "o" {:log1       "increment_selection_end_bar"
         :action #(ss/increment_selection_end_bar)}

    "." {:log1       "decrement_selection_start_eight"
         :action #(ss/decrement_selection_start_eight)}
    "," {:log1       "increment_selection_start_eight"
         :action #(ss/increment_selection_start_eight)}
    "-" {:log1       "decrement_selection_end_eight"
         :action #(ss/decrement_selection_end_eight)}
    "_" {:log1       "increment_selection_end_eight"
         :action #(ss/increment_selection_end_eight)}

    "`" {:log1 "undo active score"
         :action #(ss_undo/undo_active_score
                    (first @atoms/active_scores_n))}
    "^" {:log1 "redo active score"
         :action #(ss_undo/redo_active_score
                    (first @atoms/active_scores_n))}

    "escape" {:log1 "auto_play loop once"
              :action #(swap! atoms/auto_play assoc :requested 1 :silent_pre_bar 0)}
    "enter" {:log1 "rec and auto_mute_or_unmute"
             :action #(tloop/auto_mute_or_unmute)}
    "backspace" {:log1 "auto_play loop once w/ silent pre-bar"
                 :action #(swap! atoms/auto_play assoc :requested 1 :silent_pre_bar 1)}

    "q" {:log1   "save_atoms to default dir"
         :action #(save/save_atoms @atoms/atoms_to_save state_defs/save_root_dir)}

    "\"" {:log1   "load saved default dir atoms"
         :action #(save/load_default_atoms @atoms/atoms_to_save state_defs/save_root_dir)}

      })

    ; 1 to play a silent pre bar  
; (first @atoms/active_scores_n)

; (swap_active_score [swap_function]
; (reset_active_score [reset_function]

; (fill_gen_maps_all_active_gen_filt []
; (fill_eight_gen_maps_with_active_gen_filt) []

