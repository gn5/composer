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

  () selection menu: shift/copy  
  () scale menu   

  (/) dec/inc scores buffer index 

  (h/y) add/del last bar 
  (`/^) undo/redo active score

  (c/d) dec/inc active view bar
  (l/u) dec/inc active score

  loop dec/inc start/end:
     - (z) (x) (<) (>)

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

  "scale"
  "
  scale operations:
  () change scale
  () to closest note scale semitone down
  () to closest note scale semitone up 
  () to closest note scale tone down 
  () to closest note scale tone up 
  "
  })



