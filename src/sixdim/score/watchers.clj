(ns sixdim.score.watchers
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms] 
    [sixdim.common_fns :as common_fns]
    ; [sixdim.state_defs :as state_defs] 
    )
  (:gen-class))

; (defonce active_view_bar (atom 1))
; (defonce active_scores_n (atom [1]))
; (defonce active_scores (atom []))
; (defonce active_score (atom []))
; (defonce active_ccs_n (atom [1]))
; (defonce active_ccs (atom []))
; (defonce active_cc (atom []))
; (defonce n_bars (atom 4)) ; init number of bars in whole score

(add-watch atoms/active_scores_n :active_scores_n_watcher
  (fn [key atom old-state new-state]
    (let [int_active_score (first new-state)]
      (reset! atoms/active_score 
              (common_fns/int_to_score int_active_score)))))

; run watcher once to init atoms/active_score
(reset! atoms/active_scores_n [1])

(add-watch atoms/active_ccs_n :active_ccs_n_watcher
  (fn [key atom old-state new-state]
    (let [int_active_score (first new-state)]
      (reset! atoms/active_cc
              (common_fns/int_to_score int_active_score)))))

; run watcher once to init atoms/active_cc
(reset! atoms/active_ccs_n [1])

(add-watch atoms/active_score :active_score_n_bars_watcher
  (fn [key atom old-state new-state]
    (reset! atoms/n_bars (count new-state))))

; run watcher to init atoms/active_score

