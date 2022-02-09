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

; (defonce active_scale (atom "GDi"))
    "n" {:log1 "M6 --- C maj 6 dim  --- Amin7"
         :action #(do (reset! atoms/active_scale "M6")
                      (menu_switch/to_menu "scale_root"))}

    "e" {:log1 "m6 --- C min 6 dim  --- Amin7b5"
         :action #(do (reset! atoms/active_scale "m6")
                      (menu_switch/to_menu "scale_root"))}

    "i" {:log1 "S6 --- C maj s6 dim --- Cdom7"
         :action #(do (reset! atoms/active_scale "S6")
                      (menu_switch/to_menu "scale_root"))}

    "o" {:log1 "s6 --- C min s6 dim --- Cmin7"
         :action #(do (reset! atoms/active_scale "s6")
                      (menu_switch/to_menu "scale_root"))}

    "l" {:log1 "M5 --- C maj b5 6 dim --- Amin dim7"
         :action #(do (reset! atoms/active_scale "M5")
                      (menu_switch/to_menu "scale_root"))}

    "u" {:log1 "m5 --- C min b5 6 dim --- Adim7 - Cdim7"
         :action #(do (reset! atoms/active_scale "m5")
                      (menu_switch/to_menu "scale_root"))}

    "y" {:log1 "S5 --- C maj b5 s6 dim --- Cdom7b5"
         :action #(do (reset! atoms/active_scale "S5")
                      (menu_switch/to_menu "scale_root"))}

    "h" {:log1 "s5 --- C min b5 s6 dim --- Cmin7b5"
         :action #(do (reset! atoms/active_scale "s5")
                      (menu_switch/to_menu "scale_root"))}
      })

(def key_root_menu {
    "t" {:log1 "reset menu to base"
         :action #(menu_switch/to_menu "base")}

    "l" {:log1 "C"
         :action #(do (swap! atoms/active_scale 
                             (fn [scale_] (str "C" scale_)))
                      (menu_switch/to_menu "base"))}
    "u" {:log1 "d"
         :action #(do (swap! atoms/active_scale 
                             (fn [scale_] (str "d" scale_)))
                      (menu_switch/to_menu "base"))}
    "y" {:log1 "D"
         :action #(do (swap! atoms/active_scale 
                             (fn [scale_] (str "D" scale_)))
                      (menu_switch/to_menu "base"))}
    "h" {:log1 "e"
         :action #(do (swap! atoms/active_scale 
                             (fn [scale_] (str "e" scale_)))
                      (menu_switch/to_menu "base"))}
    "n" {:log1 "E"
         :action #(do (swap! atoms/active_scale 
                             (fn [scale_] (str "E" scale_)))
                      (menu_switch/to_menu "base"))}
    "e" {:log1 "F"
         :action #(do (swap! atoms/active_scale 
                             (fn [scale_] (str "F" scale_)))
                      (menu_switch/to_menu "base"))}
    "i" {:log1 "g"
         :action #(do (swap! atoms/active_scale 
                             (fn [scale_] (str "g" scale_)))
                      (menu_switch/to_menu "base"))}
    "o" {:log1 "G"
         :action #(do (swap! atoms/active_scale 
                             (fn [scale_] (str "G" scale_)))
                      (menu_switch/to_menu "base"))}
    "." {:log1 "a"
         :action #(do (swap! atoms/active_scale 
                             (fn [scale_] (str "a" scale_)))
                      (menu_switch/to_menu "base"))}
    "," {:log1 "A"
         :action #(do (swap! atoms/active_scale 
                             (fn [scale_] (str "A" scale_)))
                      (menu_switch/to_menu "base"))}
    "-" {:log1 "b"
         :action #(do (swap! atoms/active_scale 
                             (fn [scale_] (str "b" scale_)))
                      (menu_switch/to_menu "base"))}
    "_" {:log1 "B"
         :action #(do (swap! atoms/active_scale 
                             (fn [scale_] (str "B" scale_)))
                      (menu_switch/to_menu "base"))}
      })

  ; (l) c 
  ; (u) d c#
  ; (y) D
  ; (h) e D#
  ; (n) E 
  ; (e) F
  ; (i) g F#
  ; (o) G 
  ; (.) a G#
  ; (,) A
  ; (-) b A#
  ; (_) B









