(ns sixdim.gui.menus.gens
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms]
    ; [sixdim.score.swaps :as ss]
    [sixdim.score.melody_generators :as mgens]
    [sixdim.gui.menus.switch :as menu_switch]
    )
  (:gen-class))
; choose generator function

(def key_menu {
    "t" {:log1 "reset menu to base"
         :action #(menu_switch/to_menu "base")}

    "c" {:log1 "gen_shift_down_scale1"
         :action #(do 
        (reset! atoms/active_generator 
                mgens/gen_shift_down_scale1)
        (menu_switch/to_menu "base"))}

    "d" {:log1 "gen_shift_up_scale1"
         :action #(do 
        (reset! atoms/active_generator 
                mgens/gen_shift_up_scale1)
        (menu_switch/to_menu "base"))}

    "n" {:log1 "gen_note_from_intervals_seconds"
         :action #(do 
        (reset! atoms/active_generator 
                mgens/gen_note_from_intervals_seconds)
        (menu_switch/to_menu "base"))}

    "e" {:log1 "gen_note_from_intervals_seconds_down"
         :action #(do 
        (reset! atoms/active_generator 
                mgens/gen_note_from_intervals_seconds_down)
        (menu_switch/to_menu "base"))}

    "i" {:log1 "gen_note_from_intervals_seconds_up"
         :action #(do 
        (reset! atoms/active_generator 
                mgens/gen_note_from_intervals_seconds_up)
        (menu_switch/to_menu "base"))}

    "," {:log1 "gen_note_from_intervals_34_57_down"
         :action #(do 
        (reset! atoms/active_generator 
                mgens/gen_note_from_intervals_34_57_down)
        (menu_switch/to_menu "base"))}

    "-" {:log1 "gen_note_from_intervals_34_57_up"
         :action #(do 
        (reset! atoms/active_generator 
                mgens/gen_note_from_intervals_34_57_up)
        (menu_switch/to_menu "base"))}


    "l" {:log1 "change notes scale gen_note_from_scale"
         :action #(do 
        (reset! atoms/active_generator 
                mgens/gen_note_from_scale)
        (menu_switch/to_menu "base"))}

    "u" {:log1 "gen_closest_scale_note_up_and_down"
         :action #(do 
        (reset! atoms/active_generator 
                mgens/gen_closest_scale_note_up_and_down)
        (menu_switch/to_menu "base"))}

    "y" {:log1 "gen_closest_scale_note_up_first"
         :action #(do 
        (reset! atoms/active_generator 
                mgens/gen_closest_scale_note_up_first)
        (menu_switch/to_menu "base"))}

    "h" {:log1 "gen_closest_scale_note_down_first"
         :action #(do 
        (reset! atoms/active_generator 
                mgens/gen_closest_scale_note_down_first)
        (menu_switch/to_menu "base"))}

    "enter" {:log1 "gen_note_play_true"
             :action #(do 
             (reset! atoms/active_generator 
                     mgens/gen_note_play_true)
             (menu_switch/to_menu "base"))}

    "backspace" {:log1 "gen_note_play_false"
                 :action #(do 
                (reset! atoms/active_generator 
                        mgens/gen_note_play_false)
                (menu_switch/to_menu "base"))}

      })




