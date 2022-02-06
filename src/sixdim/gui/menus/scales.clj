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

    "n" {:log1 "selection to M6 (maj6 sixth-dim)"
         :action #(menu_switch/to_menu "scale_M6")}
    "e" {:log1 "selection to m6 (minj6 sixth-dim)"
         :action #(menu_switch/to_menu "scale_m6")}
    "i" {:log1 "selection to D6 (dom6 sixth-dim)"
         :action #(menu_switch/to_menu "scale_D6")}
    "o" {:log1 "selection to dom (dom in maj scale)"
         :action #(menu_switch/to_menu "scale_Di")}
    ;
    ; "e" {:log1 "filter_accept_bh"
    ;      :action #(do 
    ;     (reset! atoms/active_filter 
    ;             mfilts/filter_accept_bh)
    ;     (menu_switch/to_menu "base"))}

      })

