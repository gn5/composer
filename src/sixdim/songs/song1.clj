(ns sixdim.songs.song1
  (:use overtone.core)
  (:require
   [sixdim.atoms :as atoms]
   [sixdim.score.patterns.note_functions :as notef]
   [sixdim.score.patterns.pattern_functions :as patternf]
   [sixdim.score.swaps.chord_prog :as prog]
   )
  (:gen-class))

(reset! atoms/active_scores_n [1]) ; set number of score to edit

(prog/add_bars_until_n 7); set score length to 10 bars
(prog/set_scale_of_bar 1 "C14") ; change scale of bar 1
(prog/set_scale_of_bar 2 "C24") ; change scale of bar 2
(prog/set_scale_of_bar 3 "C34")
(prog/set_scale_of_bar 4 "C44")
(prog/set_scale_of_bar 5 "C54")
(prog/set_scale_of_bar 6 "C64")
(prog/set_scale_of_bar 7 "C74")
(reset! atoms/active_view_bar 2) ; GUI-look at bar 1 

; (prog/fbars 1 "s" [1 1 1 1] [7 8 12 16] [(patternsf/init)])

(prog/set_loop 1 2)

(prog/fbars 1 "st" [1 1 1 1] [2 8 12 16]
            [(patternsf/scale12 "C14" "D13")])

(prog/fbars 1 "st" [1 1 1 1] [7 8 12 16]
       [(patternsf/play false)])

(prog/fbars 1 "st" [1 1 1 1] [5 8 12 16]
       [(patternsf/play true)])

(prog/fbars 1 "st" [1 1 1 1] [7 8 12 16]
       ; octave inversion_n arp_dir:
       [(patternsf/quarter_arp_fom_inv 3 1 +)
        (patternsf/quarter_arp_fom_inv 4 1 -)])