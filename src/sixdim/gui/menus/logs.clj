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
  (u) gen_closest_scale_note
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
  scale operations:
  (n) selection to maj6 sixth-dim
  (i) selection to maj5 sixth-dim
  (-) selection to dom sixth-dim
  (_) selection to dom in scale
  ...(?) to closest note scale semitone down
  ...(!) to closest note scale semitone up 
  ...(/) to closest note scale tone down 
  ...(\\) to closest note scale tone up 
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



