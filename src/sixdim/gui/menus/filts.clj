(ns sixdim.gui.menus.filts
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms]
    ; [sixdim.score.swaps :as ss]
    [sixdim.score.melody_filters :as mfilts]
    [sixdim.gui.menus.switch :as menu_switch]
    )
  (:gen-class))
; choose filter function

(def key_menu {
    "t" {:log1 "reset menu to base"
         :action #(menu_switch/to_menu "base")}

    "n" {:log1 "filter_accept_all"
         :action #(do 
        (reset! atoms/active_filter
                mfilts/filter_accept_all)
        (menu_switch/to_menu "base"))}

    "e" {:log1 "filter_accept_bh"
         :action #(do 
        (reset! atoms/active_filter 
                mfilts/filter_accept_bh)
        (menu_switch/to_menu "base"))}

      })

