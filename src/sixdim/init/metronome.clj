(ns sixdim.init.metronome
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms] 
    [sixdim.state_defs :as state_defs] 
    [sixdim.time.loop :as tloop]
    )
  (:gen-class))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; start metronome loop

; start metronome at bar bpm speed
(def bar_metronome (metronome state_defs/bar_bpm))
(tloop/play_loop (bar_metronome) bar_metronome 0)
