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

(defn undo_score_from_buffer [score_n]
  (do 
  ;put current score in redo buffer
  (swap! (common_fns/int_to_undo_atom score_n) 
         undo/add_score_to_redo_buffer
         (common_fns/int_to_score score_n) 
         state_defs/max_redo)
  ;get last :back and put in score
  (reset! (common_fns/int_to_score_atom score_n) 
          (pop (:back (common_fns/int_to_undo score_n))))))
