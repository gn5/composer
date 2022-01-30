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

; (common_fns/int_to_score 1)
