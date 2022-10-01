(ns sixdim.score.patterns.pattern_functions
  (:use overtone.core)
  (:require
   [sixdim.score.patterns.note_functions :as nf])
  (:gen-class))

; [nf/ch_inv [inversion_n octave 1]]
; from chord ordered in inversion_n in octave
; get first note

; [nf/nav_scale ["quarter" "-" 1 "downbeats" + 1]]
; from note of 1st previous quarter,
; get next closest downbeats up

(defn scale12
  [scale_id1 scale_id2]
  {"quarter" (concat (repeat 2 [nf/set_scale [scale_id1]])
                     (repeat 2 [nf/set_scale [scale_id2]]))
   "eight"   (concat (repeat 2 [nf/set_scale [scale_id1]])
                     (repeat 2 [nf/set_scale [scale_id2]]))
   "triplet" (concat (repeat 4 [nf/set_scale [scale_id1]])
                     (repeat 4 [nf/set_scale [scale_id2]]))
   "sixteen" (concat (repeat 4 [nf/set_scale [scale_id1]])
                     (repeat 4 [nf/set_scale [scale_id2]]))})

(defn play 
  [true_false]
  {"quarter" (vec (repeat 4 [nf/play [true_false]]))
   "eight"   (vec (repeat 4 [nf/play [true_false]]))
   "triplet" (vec (repeat 8 [nf/play [true_false]]))
   "sixteen" (vec (repeat 8 [nf/play [true_false]]))})

(defn init []
  {"quarter" (vec (repeat 4 [nf/init []]))
   "eight"   (vec (repeat 4 [nf/init []]))
   "triplet" (vec (repeat 8 [nf/init []]))
   "sixteen" (vec (repeat 8 [nf/init []]))})

(defn quarter_arp_fom_inv
  "chord arpeggio up from 1st note of downbeats"
  [octave inversion_n arp_dir]
  {"quarter" [[nf/ch_inv_downbeats [inversion_n octave 1]] 
              [nf/nav_scale ["quarter" - 1 "downbeats" arp_dir 1]] 
              [nf/nav_scale ["quarter" - 1 "downbeats" arp_dir 1]] 
              [nf/nav_scale ["quarter" - 1 "downbeats" arp_dir 1]]]
   "eight"   (vec (repeat 4 [nf/play [false]]))
   "triplet" (vec (repeat 8 [nf/play [false]]))
   "sixteen" (vec (repeat 8 [nf/play [false]]))})