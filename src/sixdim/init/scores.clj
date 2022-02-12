(ns sixdim.init.scores
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms] 
    [sixdim.score.score :as score]
    )
  (:gen-class))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; init note scores/voices 
(swap! atoms/score1
  (fn [a] (into [] (repeat @atoms/n_bars score/init_bar))))
(swap! atoms/score2
  (fn [a] (into [] (repeat @atoms/n_bars score/init_bar))))
(swap! atoms/score3
  (fn [a] (into [] (repeat @atoms/n_bars score/init_bar))))
(swap! atoms/score4
  (fn [a] (into [] (repeat @atoms/n_bars score/init_bar))))
(swap! atoms/score5
  (fn [a] (into [] (repeat @atoms/n_bars score/init_bar))))
(swap! atoms/score6
  (fn [a] (into [] (repeat @atoms/n_bars score/init_bar))))
(swap! atoms/score7
  (fn [a] (into [] (repeat @atoms/n_bars score/init_bar))))
(swap! atoms/score8
  (fn [a] (into [] (repeat @atoms/n_bars score/init_bar))))

; init active_score from watcher of active_scores_n
(reset! atoms/active_scores_n [1])
(reset! atoms/active_ccs_n [1])

; todo 
; init midi cc scores 1 to 8
; (swap! cc1 (fn [_] (into [] (repeat @n_bars score/init_cc_bar)))) 
; til cc8 
