(ns sixdim.gui.windows
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
             [sixdim.global :refer [key_press ;atom 
                                     log1
                                    menu
                                    selection_bar_start
                                    selection_bar_end
                                    selection_eight_start
                                    selection_eight_end 
                                    active_view_bar
                                    bar_view_horizontal
                                    bar_view_vertical
                                    ]]
             [sixdim.gui.core :refer [
                                    default_color
                                    default_bg_color
                                    default_font
                                    default_font_size
                                    ll
                                    sv
                                    ]]
             [sixdim.gui.horizontal1 :refer [
                                    horizontal1 ;f
                                      ]]
             [sixdim.gui.horizontal2 :refer [
                                    horizontal2 ;f
                                      ]]
             [sixdim.gui.horizontal3 :refer [
                                    horizontal3 ;f
                                      ]]
            )
  (:gen-class))

(defn main_window [bar_bpm_ loop_start_bar_
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
  [(ui/filled-rectangle default_bg_color 2000 2000)
   (ui/with-color default_color
     (horizontal-layout
       (ui/padding 5 5
         (horizontal1
           bar_bpm_ loop_start_bar_
                loop_end_bar_ score_
                location_ key_press_
                to_midi_
                selection_bar_start_
                selection_bar_end_
                selection_eight_start_
                selection_eight_end_
                log1_ active_view_bar_
                bar_view_horizontal_
                bar_view_vertical_ menu_))
       (ui/padding 5 5
         (horizontal2 nil))
       (ui/padding 5 5
         (horizontal3 nil))))])
