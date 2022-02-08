(ns sixdim.gui.menus.scales
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms]
    [sixdim.score.scales.swaps :as scales_swaps]
    [sixdim.gui.menus.switch :as menu_switch]
    )
  (:gen-class))
; choose filter function

(def key_menu {
    "t" {:log1 "reset menu to base"
         :action #(menu_switch/to_menu "base")}


    "n" {:log1 "M6 --- C maj 6 dim  --- Amin7"
         :action #(menu_switch/to_menu "scale_M6")}

    "e" {:log1 "m6 --- C min 6 dim  --- Amin7b5"
         :action #(menu_switch/to_menu "scale_m6")}

    "i" {:log1 "S6 --- C maj s6 dim --- Cdom7"
         :action #(menu_switch/to_menu "scale_S6")}

    "o" {:log1 "s6 --- C min s6 dim --- Cmin7"
         :action #(menu_switch/to_menu "scale_s6")}



    "l" {:log1 "M5 --- C maj b5 6 dim --- Amin dim7"
         :action #(menu_switch/to_menu "scale_M5")}

    "u" {:log1 "m5 --- C min b5 6 dim --- Adim7 - Cdim7"
         :action #(menu_switch/to_menu "scale_m5")}

    "y" {:log1 "S5 --- C maj b5 s6 dim --- Cdom7b5"
         :action #(menu_switch/to_menu "scale_S5")}

    "h" {:log1 "s5 --- C min b5 s6 dim --- Cmin7b5"
         :action #(menu_switch/to_menu "scale_s5")}
      })

