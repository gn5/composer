(ns sixdim.gui.menus.scales.Di
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms]
    [sixdim.score.scales.swaps :as scale_swaps]
    [sixdim.gui.menus.switch :as menu_switch]
    )
  (:gen-class))
; choose filter function

(def key_menu {
    "t" {:log1 "reset menu to base"
         :action #(menu_switch/to_menu "base")}

    "i" {:log1 "(i) G dominant in Cmaj"
         :action #(do 
            (scale_swaps/selection_to_scale "GDi") ;G dom in Cmaj scale
            (menu_switch/to_menu "base"))}



      })
