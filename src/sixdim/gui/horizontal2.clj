(ns sixdim.gui.horizontal2
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

(defn horizontal2 [_]
 (vertical-layout 
    (ll "--------- --------- --------- --------- --------- --------- ")))

