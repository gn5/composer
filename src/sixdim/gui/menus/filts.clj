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

    "i" {:log1 "filter_accept_only_downbeats"
         :action #(do 
        (reset! atoms/active_filter 
                mfilts/filter_accept_only_downbeats)
        (menu_switch/to_menu "base"))}

    "o" {:log1 "filter_accept_downbeats_on_4"
         :action #(do 
        (reset! atoms/active_filter 
                mfilts/filter_accept_downbeats_on_4)
        (menu_switch/to_menu "base"))}

    "h" {:log1 "filter_accept_downbeats_on_48_up316"
         :action #(do 
        (reset! atoms/active_filter 
                mfilts/filter_accept_downbeats_on_48_up316)
        (menu_switch/to_menu "base"))}

      })

