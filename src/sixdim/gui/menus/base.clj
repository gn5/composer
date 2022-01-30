(ns sixdim.gui.menus.base
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms]
    [sixdim.score.swaps :as ss]
    [sixdim.gui.menus.switch :as menu_switch]
    )
  (:gen-class))

(def key_menu {
    "a" {:log1 "menu choose generator"
         :action #(menu_switch/to_menu "gens")}
    "r" {:log1 "menu choose filter"
         :action #(menu_switch/to_menu "filts")}

    "l" {:log1 "decrement_active_score_n"
         :action #(ss/decrement_active_score_n)}
    "u" {:log1 "increment_active_score_n"
         :action #(ss/increment_active_score_n)}

    "y" {:log1 "del_one_score_at_score_end"
         :action #(do (ss/del_one_score_at_score_end)
                      (reset! atoms/active_scores_n 
                             @atoms/active_scores_n))}
    "h" {:log1 "add_one_score_at_score_end"
         :action #(do (ss/add_one_score_at_score_end)
                      (reset! atoms/active_scores_n 
                             @atoms/active_scores_n))}

    "f" {:log1  "reset_eight_gen_maps_with_active_gen_filt"
    :action #(ss/reset_eight_gen_maps_with_active_gen_filt)}
    "p" {:log1  "reset_fill_score_with_active_gen_map"
    :action #(ss/reset_fill_score_with_active_gen_map)}

    "c" {:log1       "decrement_active_view_bar"
         :action #(ss/decrement_active_view_bar)}
    "d" {:log1       "increment_active_view_bar"
         :action #(ss/increment_active_view_bar 
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
      })

; (swap_active_score [swap_function]
; (reset_active_score [reset_function]

; (fill_gen_maps_all_active_gen_filt []
; (fill_eight_gen_maps_with_active_gen_filt) []

