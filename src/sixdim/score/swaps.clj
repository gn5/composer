(ns sixdim.score.swaps
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms] 
    [sixdim.common_fns :as common_fns]
    )
  (:gen-class))

; (defn swap_active_score[swap_function]
; (defn reset_active_score [reset_function]
; (defn increment_active_score_n []
; (defn decrement_active_score_n []
; (defn decrement_selection_start_bar []
; (defn increment_selection_start_bar []
; (defn decrement_selection_end_bar []
; (defn increment_selection_end_bar []

(defn swap_active_score
"swap! active score"
  [swap_function]
  (let [int_active_score 
          (first @atoms/active_scores_n)]
    (let [score_to_swap
          (common_fns/int_to_score int_active_score)]
      (swap! score_to_swap swap_function))))

; (swap_active_score [1 2] #(score/add_bars_at_score_end % ["test"]))

(defn reset_active_score
"swap! active score"
  [reset_function]
  (let [int_active_score 
          (first @atoms/active_scores_n)]
    (let [score_to_reset
          (common_fns/int_to_score int_active_score)]
      (reset! score_to_reset reset_function))))


(defn decrement_active_score_n []
  (swap! atoms/active_scores_n 
         (fn [a] (cond 
                   (>= (first a) 2) ;min score is n 1  
                   (update-in a [0] dec)
                   :else ; score can't be below 1
                   a))))
; (decrement_active_score_n)

(defn increment_active_score_n []
  (swap! atoms/active_scores_n 
         (fn [a] (cond 
                   (<= (first a) 7) ;max score is n 8  
                   (update-in a [0] inc)
                   :else ; score can't be below 1
                   a))))
; (increment_active_score_n)

(defn decrement_selection_start_bar []
      (swap! atoms/selection_bar_start dec))
(defn increment_selection_start_bar []
      (swap! atoms/selection_bar_start inc))

(defn decrement_selection_end_bar []
      (swap! atoms/selection_bar_end dec))
(defn increment_selection_end_bar []
      (swap! atoms/selection_bar_start inc))

