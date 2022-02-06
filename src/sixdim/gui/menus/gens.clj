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

    "l" {:log1 "gen_note_from_scale"
         :action #(do 
        (reset! atoms/active_generator 
                mgens/gen_note_from_scale)
        (menu_switch/to_menu "base"))}
      })
