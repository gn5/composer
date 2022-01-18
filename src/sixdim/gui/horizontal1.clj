(ns sixdim.gui.horizontal1
  (:use overtone.core)
  (:require 
             [membrane.ui :as ui
                            :refer [
                                    vertical-layout
                                    horizontal-layout
                                    button
                                    label
                                    spacer
                                    on]]
            [sixdim.time.loop :refer [
                                    location
                                    bar_bpm
                                    bar_metronome
                                    loop_start_bar
                                    loop_end_bar
                                    play_loop]]
             [sixdim.midi.play :refer [
                                    to_midi ;a
                                    midi_play_location ;
                                      ]]
             [sixdim.global :refer [
             key_press
             log1
             menu
             selection_bar_start
             selection_bar_end
             active_view_bar
             bar_view_horizontal
             bar_view_vertical
             selection_eight_start
             selection_eight_end
                                    ]]
             [sixdim.gui.core :refer [
                                    default_color
                                    default_bg_color
                                    default_font
                                    default_font_size
                                    ll
                                    sv
                                    ]]
            )
  (:gen-class))

(defn horizontal1 [bar_bpm_ loop_start_bar_
                loop_end_bar_ score_
                location_ key_press_
                to_midi_
                selection_bar_start_
                selection_bar_end_
                selection_eight_start_
                selection_eight_end_
                log1_ active_view_bar_
                bar_view_horizontal_
                bar_view_vertical_ menu_] 
  (vertical-layout 
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
       (button (str "to midi: " (str to_midi_)) nil to_midi_))
   (ll (str "bar bpm: " bar_bpm_))
   (ll (str "bar:" (location_ "bar") 
               " q:" (location_ "quarter") 
               " e:" (location_ "eight")))
   (ll (str "n bars: " (count score_)))
   (ll (str "loop: [" loop_start_bar_ " - " loop_end_bar_ "]")) 
   (ll (str "selection: [" selection_bar_start_ " . " selection_eight_start_  " - " selection_bar_end_ " . " selection_eight_end_  "]"))
   (ll (str "active_view_bar: " active_view_bar_))
   (ll (str "keypress: " key_press_))
   (ll (str "menu: " menu_) )
   (ll (str "action log: " log1_))
   ))

