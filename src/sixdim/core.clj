(ns sixdim.core
  (:use overtone.core)
  (:require 
    [clojure.java.io :as io]
    [membrane.java2d :as java2d]
    [sixdim.atoms :as atoms] 
    [sixdim.state_defs :as state_defs] 
    [sixdim.common_fns :as common_fns]
    [sixdim.midi.receivers :as midi_receivers]
    [sixdim.midi.play :as midi_play]
    [sixdim.midi.play_watchers :as midi_play_watchers]
    [sixdim.time.loop :as tloop]
    [sixdim.score.score_nav :as nav]
    [sixdim.score.melody :as melody]
    [sixdim.score.score :as score]
    [sixdim.score.undo :as undo]
    [sixdim.score.swaps.core :as score_swaps]
    [sixdim.score.scales.swaps :as scales_swaps]
    [sixdim.score.scales.watchers :as scales_watchers]
    [sixdim.score.swaps.undo :as score_swaps_undo]
    [sixdim.score.watchers.core :as score_watchers]
    [sixdim.score.watchers.undo :as score_undo_watchers]
    [sixdim.score.scales :as scales]
    [sixdim.score.scales.diminished :as scales_dim]
    [sixdim.score.melody_filters :as mfilters]
    [sixdim.score.melody_generators :as mgens]
    [sixdim.print.core :as print_core]
    [sixdim.print.score :as print_score]
    [sixdim.gui.windows :as windows]
    [sixdim.gui.key_press :as key_press]
    [sixdim.gui.help_window :as help_window]
    [sixdim.gui.menus.logs :as menu_logs]
    [sixdim.save :as save]
    )
  (:gen-class))

; (print midi_out_virtualport)
; (overtone.midi/midi-note midi_out_virtualport 66 127 500 0)

(load-file "src/sixdim/init/scales.clj")
(load-file "src/sixdim/init/scores.clj")
(load-file "src/sixdim/init/metronome.clj")
(load-file "src/sixdim/init/save.clj")
(load-file "src/sixdim/init/gui.clj")

; load default (latest) save
; (save/load_default_atoms @atoms/atoms_to_save state_defs/save_root_dir)

; save
; (save/save_atoms @atoms/atoms_to_save state_defs/save_root_dir)




