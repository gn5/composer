(ns sixdim.common_fns
  (:require 
    [sixdim.atoms :as atoms] 
    )
  (:gen-class))

(defn fn_name
  [f]
  (first (re-find #"(?<=\$)([^@]+)(?=@)" (str f))))

(def int_to_score_map {
   "1" atoms/score1
   "2" atoms/score2
   "3" atoms/score3
   "4" atoms/score4
   "5" atoms/score5
   "6" atoms/score6
   "7" atoms/score7
   "8" atoms/score8})

(defn int_to_score [score_n]
  (deref (int_to_score_map (str score_n))))

(defn int_to_score_atom [score_n]
  (int_to_score_map (str score_n)))

(def int_to_cc_map {
   "1" atoms/cc1
   "2" atoms/cc2
   "3" atoms/cc3
   "4" atoms/cc4
   "5" atoms/cc5
   "6" atoms/cc6
   "7" atoms/cc7
   "8" atoms/cc8})

(defn int_to_cc [cc_n]
  (deref (int_to_cc_map (str cc_n))))

(defn int_to_cc_atom [cc_n]
  (int_to_cc_map (str cc_n)))

(def int_to_undo_map {
   "1" atoms/score1_undo
   "2" atoms/score2_undo
   "3" atoms/score3_undo
   "4" atoms/score4_undo
   "5" atoms/score5_undo
   "6" atoms/score6_undo
   "7" atoms/score7_undo
   "8" atoms/score8_undo})

(defn int_to_undo [score_n]
  (deref (int_to_undo_map (str score_n))))

(defn int_to_undo_atom [score_n]
  (int_to_undo_map (str score_n)))

(def int_to_ccundo_map {
   "1" atoms/cc1_undo
   "2" atoms/cc2_undo
   "3" atoms/cc3_undo
   "4" atoms/cc4_undo
   "5" atoms/cc5_undo
   "6" atoms/cc6_undo
   "7" atoms/cc7_undo
   "8" atoms/cc8_undo})

(defn int_to_ccundo [score_n]
  (deref (int_to_ccundo_map (str score_n))))

(defn int_to_ccundo_atom [score_n]
  (int_to_ccundo_map (str score_n)))



(def int_to_n_undo_map {
   "1" atoms/n_score1_undo
   "2" atoms/n_score2_undo
   "3" atoms/n_score3_undo
   "4" atoms/n_score4_undo
   "5" atoms/n_score5_undo
   "6" atoms/n_score6_undo
   "7" atoms/n_score7_undo
   "8" atoms/n_score8_undo})

(defn int_to_n_undo_score [score_n]
  (deref (int_to_n_undo_map (str score_n))))

(def int_to_n_ccundo_map {
   "1" atoms/n_cc1_undo
   "2" atoms/n_cc2_undo
   "3" atoms/n_cc3_undo
   "4" atoms/n_cc4_undo
   "5" atoms/n_cc5_undo
   "6" atoms/n_cc6_undo
   "7" atoms/n_cc7_undo
   "8" atoms/n_cc8_undo})

(defn int_to_n_ccundo [score_n]
  (deref (int_to_n_ccundo_map (str score_n))))

