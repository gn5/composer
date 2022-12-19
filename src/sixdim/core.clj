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
   [sixdim.score.nav.quarter :as nav_quarter]
   [sixdim.score.nav.eight :as nav_eight]
   [sixdim.score.nav.triplet :as nav_triplet]
   [sixdim.score.nav.sixteen :as nav_sixteen]
   [sixdim.score.score_nav :as nav]
   [sixdim.score.melody :as melody]
   [sixdim.score.score :as score]
   [sixdim.score.undo :as undo]
   [sixdim.score.swaps.core :as score_swaps]
   [sixdim.score.swaps.gen_maps.core :as gen_maps_core]
   [sixdim.score.swaps.gen_maps.eight :as gen_maps_eight]
   [sixdim.score.swaps.gen_maps.triplet :as gen_maps_triplet]
   [sixdim.score.swaps.gen_maps.sixteen :as gen_maps_sixteen]
   [sixdim.score.scales.swaps :as scales_swaps]
   [sixdim.score.scales.watchers :as scales_watchers]
   [sixdim.score.swaps.undo :as score_swaps_undo]
   [sixdim.score.swaps.gen_maps :as swaps_gen_maps]
   [sixdim.score.watchers.core :as score_watchers]
   [sixdim.score.watchers.undo :as score_undo_watchers]
   [sixdim.score.scales :as scales]
   [sixdim.score.scales.diminished :as scales_dim]
   [sixdim.score.scales.modes_four :as scales_modes_four]
   [sixdim.score.scales.modes_three :as scales_modes_three]
   [sixdim.score.melody_filters :as mfilters]
   ;[sixdim.score.patterns.pattern-functions :as mpatterns]
   ;[sixdim.score.patterns.melody-pattern-keys :as melody_pattern_keys]
   [sixdim.score.melody_generators :as mgens]
    [sixdim.score.patterns.note_functions :as nf]
    [sixdim.score.patterns.pattern_functions :as pf]
   [sixdim.print.core :as print_core]
   [sixdim.print.score :as print_score]
   [sixdim.gui.windows :as windows]
   [sixdim.gui.key_press :as key_press]
   [sixdim.gui.help_window :as help_window]
   [sixdim.gui.menus.logs :as menu_logs]
   [sixdim.save :as save]
   [sixdim.score.swaps.chord_prog :as prog]
   )
  (:gen-class))

(println (str "midi_out_virtualport: " midi_receivers/midi_out_virtualport))
; (overtone.midi/midi-note midi_receivers/midi_out_virtualport 66 127 500 0)

(load-file "src/sixdim/init/scales.clj")
;
;(println (str "log: core init - n scales:" (count @atoms/scales)))
;(println (str "log: core init - all scales:" (vec (map #(:id %) @atoms/scales))))
(load-file "src/sixdim/init/scores.clj")

(load-file "src/sixdim/init/metronome.clj")

(load-file "src/sixdim/init/save.clj")
(load-file "src/sixdim/init/gui.clj")

;(println @atoms/active_score)

;(println @atoms/score1)

; (defn ttt []
  ; (overtone.midi/midi-note midi_receivers/midi_out_virtualport 66 127 500 0))
; (apply-by (+ 10000 (now)) #'ttt [])

; (apply-at (+ 1000 (now)) #'overtone.midi/midi-note [midi_receivers/midi_out_virtualport 66 127 500 0])

; (print (first @atoms/scales))

; (print (scales/get_scale "CM5" @atoms/scales))



