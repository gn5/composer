(ns sixdim.midi.play
  (:use overtone.core)
  (:require [sixdim.midi.receivers 
             :refer [midi-out-virtualport]]
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
             )
  (:gen-class))

(defonce to_midi (atom false))

(defn midi_play_location [location score midi_port midi_channel]
  (let [current_note (get_score_beat
                       score 
                       (location "bar")
                       (location "current_subdiv")
                       (location (location "current_subdiv"))
                       )]
    (cond
      (= "quarter" (location "current_subdiv")) 
      (overtone.midi/midi-note midi_port 
                             (:midi-note (note-info (current_note "pitch"))) 
                             (current_note "vol") 
                             (current_note "duration") 
                             midi_channel)
      (= "eight" (location "current_subdiv")) 
      (overtone.midi/midi-note midi_port 
                             (:midi-note (note-info (current_note "pitch"))) 
                             (current_note "vol") 
                             (current_note "duration") 
                             midi_channel)
      :else nil
      )))
