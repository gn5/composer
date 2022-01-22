(ns sixdim.gui.horizontal1
  (:use overtone.core)
  (:require 
    [membrane.ui :as ui]
    [sixdim.gui.core :as guicore]
    [sixdim.atoms :as atoms]
    [sixdim.state_defs :as state_defs])
  (:gen-class))

(defn horizontal1 [bar_bpm_ loop_start_bar_
                   loop_end_bar_ active_score_
                   location_ key_press_
                   to_midi_
                   selection_bar_start_
                   selection_bar_end_
                   selection_eight_start_
                   selection_eight_end_
                   log1_ active_view_bar_
                   bar_view_horizontal_
                   bar_view_vertical_ menu_] 
  (ui/vertical-layout 

  (ui/on :key-press (fn [akey]
                [[
                  (cond 
                    (string? akey)
                    (reset! atoms/key_press akey)
                    (keyword? akey) 
                    "ignore")
                  ]])
    (ui/label ""))

   (ui/on :mouse-down (fn [[mouse-x mouse-y]]
                     (swap! atoms/to_midi1 (fn [a] (not a)))
                     nil)
       (ui/button (str "to midi1: " (str to_midi_)) nil to_midi_))

   (guicore/ll (str "bar bpm: " bar_bpm_))

   (guicore/ll (str "bar:" (location_ "bar") 
                    " q:" (location_ "quarter") 
                    " e:" (location_ "eight")))

   (guicore/ll (str "n bars: " (count active_score_)))

   (guicore/ll (str "loop: [" loop_start_bar_ 
                            " - " 
                            loop_end_bar_ "]")) 

   (guicore/ll (str "selection: [" selection_bar_start_ 
                                 " . " 
                                 selection_eight_start_  
                                 " - " selection_bar_end_ 
                                 " . " selection_eight_end_ "]"))

   (guicore/ll (str "active_view_bar: " active_view_bar_))
   (guicore/ll (str "keypress: " key_press_))
   (guicore/ll (str "menu: " menu_) )
   (guicore/ll (str "action log: " log1_))
   ))

