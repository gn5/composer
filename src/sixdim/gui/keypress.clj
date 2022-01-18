(ns sixdim.gui.keypress
  (:use overtone.core)
  (:require 
            [sixdim.time.loop :refer [
                                    location
                                    bar_bpm
                                    bar_metronome
                                    loop_start_bar
                                    loop_end_bar
                                    play_loop]]
             [sixdim.score.score :refer [
                                    default_note_volume
                                    default_midi_channel
                                    calc_bpm_based_note_duration
                                    new_note 
                                    empty_note
                                    empty_bar
                                    init_bar
                                    test_bar
                                    n_bars
                                    score
                                    add_bars_at_score_end
                                    add_bars_at_score_start
                                    get_score_bar
                                    get_bars_1_to_n
                                    get_bars_n_to_end
                                    add_bars_at_score_index
                                    add_bars_to_score
                                    remove_score_bars
                                    replace_score_bars
                                    replace_score_note
                                    replace_bar_note]]
             [sixdim.score.score_nav :refer [
                                    get_score_beat 
                                    get_next_eight
                                    nav_eight]]
             [sixdim.score.scales :refer [
                                    scales ;atom
                                    shift_note_nooctave ;f
                                    get_new_note_octave ;f
                                    shift_note ;f
                                    add_scale_Amin7sixthdim ;f
                                    in_scale_group ;f
                                      ]]
             [sixdim.score.melody :refer [
                                    gen_maps ;atom
                                    gen_melody ;f
                                      ]]
             [sixdim.score.melody_generators :refer [
                                    seconds ;map
                                    seconds_up ;map
                                    seconds_down ;map
                                    gen_note_from_intervals ;f
                                    gen_note_from_intervals_seconds ;f
                                    gen_note_from_intervals_seconds_down ;f
                                    gen_note_from_intervals_seconds_up ;f
                                      ]]
             [sixdim.score.melody_filters :refer [
                                    filter_accept_all ;f
                                    filter_accept_bh ;f
                                      ]]
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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(add-watch key_press :key_press_watcher
  (fn [key atom old-state new-state]
    (cond 

      (= "t" new-state)
      (do 
      (reset! log1 "(t) swap! menu to base")
      (reset! menu "base"))

      (= "m" new-state)
      (do 
      (swap! score add_bars_at_score_end init_bar)
      (reset! log1 "(m) swap! score add_bars_at_score_end init_bar"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; selection

      (= "n" new-state)
      (do 
      (swap! selection_bar_start dec)
      (reset! log1 "(n) selection_bar_start dec"))
      (= "e" new-state)
      (do 
      (swap! selection_bar_start inc)
      (reset! log1 "(e) selection_bar_start inc"))

      (= "i" new-state)
      (do 
      (swap! selection_bar_end dec)
      (reset! log1 "(i) selection_bar_end dec"))
      (= "o" new-state)
      (do 
      (swap! selection_bar_end inc)
      (reset! log1 "(o) selection_bar_end inc"))


      ))) 
        
