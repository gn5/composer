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

  (y) del last bar 
  (h) add 1 bar at end score

  (c) decrement active view bar
  (d) increment active view bar

  (l) decrement active score
  (u) increment active score

  selection dec/inc start/end:
     - (n) (e) (i) (o): bar n
     - (.) (,) (-) (_): eighth n
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

  })
