(ns sixdim.global
  (:use overtone.core)
  (:gen-class))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; global atoms 

;; gui atoms
(defonce key_press (atom "t"))
(defonce log1 (atom "init"))
(defonce menu (atom ""))
(defonce selection_bar_start (atom 1))
(defonce selection_bar_end (atom 2))
(defonce active_view_bar (atom 1))
(defonce bar_view_horizontal (atom ""))
(defonce bar_view_vertical (atom ""))
(defonce selection_eight_start (atom 1))
(defonce selection_eight_end (atom 8))

;;score atoms
(defonce n_bars (atom 4)) ; init number of bars in whole score
(def score (atom [])) ; init partition score
(def score2 (atom []))
(def score3 (atom []))
(def score4 (atom []))
(def score5 (atom []))
(def score6 (atom []))
(def score7 (atom []))
(def score8 (atom []))

