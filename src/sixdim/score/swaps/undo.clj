(ns sixdim.score.swaps.undo
  (:use overtone.core)
  (:require 
    [sixdim.state_defs :as state_defs]
    [sixdim.atoms :as atoms] 
    [sixdim.common_fns :as common_fns]
    [sixdim.score.score :as score]
    [sixdim.score.melody :as melody]
    [sixdim.score.undo :as undo]
    )
  (:gen-class))

(defn undo_active_score [score_n]
  (do 
  ;put current score in redo buffer
  (swap! (common_fns/int_to_undo_atom score_n)
         undo/add_score_to_redo_buffer
         (common_fns/int_to_score score_n)
         state_defs/max_redo)
  ;get last :back and put in current active score
  (reset! (common_fns/int_to_score_atom score_n)
          (first (take-last 1 (:back (common_fns/int_to_undo score_n)))))
  ;remove last :back from undo buffer
  (swap! (common_fns/int_to_undo_atom score_n)
         undo/remove_last_undo_score)
  ;reset active_score for GUI sync
  (reset! atoms/active_score (common_fns/int_to_score score_n))))

; (common_fns/int_to_score_atom 1)
; (common_fns/int_to_score 1)
; atoms/score1_undo
; (print (print_score/print_score_sel_bars_notes @atoms/score1 1 2 5))
; (print (print_score/print_score_sel_bars_notes @atoms/score2 1 2 5))
; (print (print_score/print_score_sel_bars_notes @atoms/active_score 1 2 5))
; (print (print_score/print_score_sel_bars_notes (first (take-last 1 (:back (common_fns/int_to_undo 1)))) 1 2 5))
; atoms/active_score
; atoms/scores_buffer

; (:back (common_fns/int_to_undo 1))
; (first (take-last 1 (:back (common_fns/int_to_undo 1))))
; (subvec (:back (common_fns/int_to_undo 1)) (- (count (common_fns/int_to_undo 1)) 2))

(defn redo_active_score [score_n]
  (do 
  ;put current score in undo buffer
  (swap! (common_fns/int_to_undo_atom score_n)
         undo/add_score_to_undo_buffer
         (common_fns/int_to_score score_n)
         state_defs/max_redo)
  ;get last :forw and put in current active score
  (reset! (common_fns/int_to_score_atom score_n)
          (first (take-last 1 (:forw (common_fns/int_to_undo score_n)))))
  ;remove last :forw from undo buffer
  (swap! (common_fns/int_to_undo_atom score_n)
         undo/remove_last_redo_score)
  ;reset active_score for GUI sync
  (reset! atoms/active_score (common_fns/int_to_score score_n))))
