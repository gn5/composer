(ns sixdim.gui.menus.logs
  (:use overtone.core)
  (:gen-class))

(def logs {

  "base"
  "
  base menu:
  (a) choose generator
  (r) choose filter
  (f) selection/gen/filt to gen_map
  (p) fill score with gen_map

  (w) scales menu   
  () selection menu: shift/copy  

  (0/4) dec/inc scores buffer index 

  (h/y) add/del last bar 
  (`/^) undo/redo active score

  (c/d) dec/inc active view bar
  (l/u) dec/inc active score

  loop dec/inc start/end:
     - (z) (x) (<) (>)

  (6) auto_play loop once w/ silent pre-bar

  selection dec/inc start/end:
     - (n) (e) (i) (o): bar n
     - (.) (,) (-) (_): eighth n
     - (#) (~) ($) (@): triplet n
     - (?) (!) (/) (\\): sixteen n
  "

  "gens"
  "
  choose generator:
  (n) gen_note_from_intervals_seconds
  (e) gen_note_from_intervals_seconds_down
  (i) gen_note_from_intervals_seconds_up

  (l) change scale (gen_note_from_scale)
        - use filter_accept_all
  (u) gen_closest_scale_note_up_and_down
        - use filter_accept_all
  (y) gen_closest_scale_note_up_first
        - use filter_accept_all
  (h) gen_closest_scale_note_down_first
        - use filter_accept_all
  "

  "filts"
  "
  choose filter:
  (n) filter_accept_all
  (e) filter_accept_bh
  "

  "selection"
  "
  selection operations:
  (f) selection/gen/filt to gen_map
  () same start/end
  () end to next bar
  () start to previous bar
  "

  "scales"
  "
  scales:
  (n) M6 --- C maj 6 dim  --- Amin7
  (e) m6 --- C min 6 dim  --- Amin7b5
  (i) S6 --- C maj s6 dim --- Cdom7
  (o) s6 --- C min s6 dim --- Cmin7
  (l) M5 --- C maj b5 6 dim --- Amin dim7
  (u) m5 --- C min b5 6 dim --- Adim7 - Cdim7
  (y) S5 --- C maj b5 s6 dim --- Cdom7b5
  (h) s5 --- C min b5 s6 dim --- Cmin7b
  "

  "scale_root"
  "
  scale operations:
  (l) C 
  (u) d C#
  (y) D
  (h) e D#
  (n) E 
  (e) F
  (i) g F#
  (o) G 
  (.) a G#
  (,) A
  (-) b A#
  (_) B
  "
  })



