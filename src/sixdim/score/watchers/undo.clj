(ns sixdim.score.watchers.undo
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms] 
    [sixdim.common_fns :as common_fns]
    [sixdim.score.undo :as undo]
    ; [sixdim.state_defs :as state_defs] 
    )
  (:gen-class))

(add-watch atoms/score1_undo :score1_undo_n_update
  (fn [key atom old-state new-state]
    (reset! atoms/n_score1_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})
    (reset! atoms/n_score_active_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})))

(add-watch atoms/score2_undo :score2_undo_n_update
  (fn [key atom old-state new-state]
    (reset! atoms/n_score2_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})
    (reset! atoms/n_score_active_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})))

(add-watch atoms/score3_undo :score3_undo_n_update
  (fn [key atom old-state new-state]
    (reset! atoms/n_score3_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})
    (reset! atoms/n_score_active_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})))

(add-watch atoms/score4_undo :score4_undo_n_update
  (fn [key atom old-state new-state]
    (reset! atoms/n_score4_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})
    (reset! atoms/n_score_active_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})))

(add-watch atoms/score5_undo :score5_undo_n_update
  (fn [key atom old-state new-state]
    (reset! atoms/n_score5_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})
    (reset! atoms/n_score_active_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})))

(add-watch atoms/score6_undo :score6_undo_n_update
  (fn [key atom old-state new-state]
    (reset! atoms/n_score6_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})
    (reset! atoms/n_score_active_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})))

(add-watch atoms/score7_undo :score7_undo_n_update
  (fn [key atom old-state new-state]
    (reset! atoms/n_score7_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})
    (reset! atoms/n_score_active_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})))

(add-watch atoms/score8_undo :score8_undo_n_update
  (fn [key atom old-state new-state]
    (reset! atoms/n_score8_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})
    (reset! atoms/n_score_active_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})))



(add-watch atoms/cc1_undo :cc1_undo_n_update
  (fn [key atom old-state new-state]
    (reset! atoms/n_cc1_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})
    (reset! atoms/n_cc_active_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})))

(add-watch atoms/cc2_undo :cc2_undo_n_update
  (fn [key atom old-state new-state]
    (reset! atoms/n_cc2_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})
    (reset! atoms/n_cc_active_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})))

(add-watch atoms/cc3_undo :cc3_undo_n_update
  (fn [key atom old-state new-state]
    (reset! atoms/n_cc3_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})
    (reset! atoms/n_cc_active_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})))

(add-watch atoms/cc4_undo :cc4_undo_n_update
  (fn [key atom old-state new-state]
    (reset! atoms/n_cc4_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})
    (reset! atoms/n_cc_active_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})))

(add-watch atoms/cc5_undo :cc5_undo_n_update
  (fn [key atom old-state new-state]
    (reset! atoms/n_cc5_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})
    (reset! atoms/n_cc_active_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})))

(add-watch atoms/cc6_undo :cc6_undo_n_update
  (fn [key atom old-state new-state]
    (reset! atoms/n_cc6_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})
    (reset! atoms/n_cc_active_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})))

(add-watch atoms/cc7_undo :cc7_undo_n_update
  (fn [key atom old-state new-state]
    (reset! atoms/n_cc7_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})
    (reset! atoms/n_cc_active_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})))

(add-watch atoms/cc8_undo :cc8_undo_n_update
  (fn [key atom old-state new-state]
    (reset! atoms/n_cc8_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})
    (reset! atoms/n_cc_active_undo 
            {:back (count (:back new-state))
             :forw (count (:forw new-state))})))
