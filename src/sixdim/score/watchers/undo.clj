(ns sixdim.score.watchers.undo
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms] 
    [sixdim.common_fns :as common_fns]
    [sixdim.score.undo :as undo]
    ; [sixdim.state_defs :as state_defs] 
    )
  (:gen-class))


; (defn redo_score_from_buffer [score undo_redo_hash]
  ; )
; (defonce score1_undo (atom {:back [] :forw []}))
; (add-watch atoms/score1 :score1_undo_update
  ; (fn [key atom old-state new-state]
    ; (swap! atoms/score1_undo #(undo/add_undo_score % old-state))))

