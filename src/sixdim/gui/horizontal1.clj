(ns sixdim.gui.horizontal1
  (:use overtone.core)
  (:require 
    [membrane.ui :as ui]
    [sixdim.gui.core :as guicore]
    [membrane.sixdim.state_defs :as state_defs])
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

  (on :key-press (fn [akey]
                [[
                  (cond 
                    (string? akey)
                    (reset! key_press akey)
                    (keyword? akey) 
                    "ignore")
                  ]])
    (ui/label ""))

   (on :mouse-down (fn [[mouse-x mouse-y]]
                     (swap! to_midi (fn [a] (not a)))
                     nil)
       (ui/button (str "to midi: " (str to_midi_)) nil to_midi_))

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

