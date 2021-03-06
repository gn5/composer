(ns sixdim.gui.menus.logs
  (:use overtone.core)
  (:gen-class))

(def logs {

  "base"
  "
  base menu:
  (a) choose generator
  (r) choose filter
  (w) choose scale   
  (f) choose gen_map
  (p) fill score with gen_map

  () selection menu: shift/copy  

  (0/4) dec/inc scores buffer index 

  (h/y) add/del last bar 
  (`/^) undo/redo active score

  (c/d) dec/inc active view bar
  (l/u) dec/inc active score

  loop dec/inc start/end:
     - (z) (x) (<) (>)

  (enter) rec and auto_mute_or_unmute
  (escape) auto_play loop once
  (backspace) auto_play loop once w/ silent pre-bar

  (q) save_atoms to default dir
  (\") load saved default dir atoms

  selection dec/inc start/end:
     - (n) (e) (i) (o): bar n
     - (.) (,) (-) (_): eighth n
     - (#) (~) ($) (@): triplet n
     - (?) (!) (/) (\\): sixteen n
  "

  "gens"
  "
  choose generator:
  (n) gen_note_from_intervals_seconds_down_eight
  (e) gen_note_from_intervals_seconds_up_eight
  (i) gen_note_from_intervals_34_57_down_eight
  (o) gen_note_from_intervals_34_57_up_eight
  (l) gen_note_from_intervals_seconds_down_triplet
  (u) gen_note_from_intervals_seconds_up_triplet
  (y) gen_note_from_intervals_34_57_down_triplet
  (h) gen_note_from_intervals_34_57_up_triplet
  (enter) gen_note_from_intervals_seconds_down_sixteen
  (backspace) gen_note_from_intervals_seconds_up_sixteen
  ( ) gen_note_from_intervals_34_57_down_sixteen
  (tab) gen_note_from_intervals_34_57_up_sixteen
  (s) gen_note_play_true
  (w) gen_note_play_false
  (f) gen_shift_down_scale1
  (p) gen_shift_up_scale1
  (z) change notes scale gen_note_from_scale
  (x) gen_closest_scale_note_up_and_down
  (c) gen_closest_scale_note_down_first
  (d) gen_closest_scale_note_up_first
  "

  "gen_maps"
  "
  fill gen_maps:
  (n) all eight
  (.) first eight
  (l) all triplet 
  (#) first triplet 
  (enter) all sixteen
  (?) first sixteen

  (a) complement eight 
  (r) complement triplet
  (s) complement sixteen

  (e) every 2 eight
  (,)   every 2 eight + 1
  (u) every 2 triplet
  (~)   every 2 triplet + 1
  (backspace) every 2 sixteen
  (!)   every 2 sixteen + 1

  (i) every 3 eight
  (-)   every 3 eight + 1
  (y) every 3 triplet
  ($)   every 3 triplet + 1
  ( ) every 3 sixteen
  (/)   every 3 sixteen + 1

  (o) every 4 eight
  (_)   every 4 eight + 1
  (h) every 4 triplet
  (@)   every 4 triplet + 1
  (tab) every  4 sixteen
  (\\)   every 4 sixteen + 1
  "

  "filts"
  "
  choose filter:
  (n) ok all notes
  (e) ok BH 4:d 8:u/s      3/16:d/u/s
  (i) ok only downbeats
  (o) ok downbeats on 4  8/3/16:d/u/s
  (h) ok downbeats on 4/8  3/16:u/s
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



