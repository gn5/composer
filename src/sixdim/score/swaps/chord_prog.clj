(ns sixdim.score.swaps.chord_prog
  (:use overtone.core)
  (:require
   ; [sixdim.state_defs :as state_defs]
   [sixdim.atoms :as atoms]
   [sixdim.score.melody_generators :as mgens]
   [sixdim.score.melody_filters :as mfilts]
   [sixdim.score.swaps.gen_maps :as swaps_gen_maps]
   [sixdim.score.swaps.core :as ss]
   ;[sixdim.score.patterns.pattern_functions :as patternsf]
   
   ; [sixdim.score.swaps.undo :as ss_undo]
   ; [sixdim.common_fns :as common_fns]
   ; [sixdim.score.score :as score]
   ; [sixdim.score.undo :as undo]
   ; [sixdim.score.melody :as melody]
    ; [sixdim.score.swaps.gen_maps :as swaps_gen_maps]
   [sixdim.score.swaps.core :as score_swaps])
  (:gen-class))

(defn in?
  "true if coll contains elm"
  [coll elm]
  (some #(= elm %) coll))

(defn split_chars [word]
  (vec (eduction (partition-all 1) (map #(apply str %)) word)))

(defn set_loop [start_bar end_bar]
  (reset! atoms/loop_start_bar start_bar)
  (reset! atoms/loop_end_bar end_bar))


(defn set_selection_on_full_bar
  "set active selection atoms to cover all notes of a single bar
   Args: 
     bar_n (int>0): bar number on which to focus selection"
  [bar_n]
  (reset! atoms/selection_bar_start bar_n)
  (reset! atoms/selection_bar_end bar_n)
  (reset! atoms/selection_eight_start 1)
  (reset! atoms/selection_eight_end 8)
  (reset! atoms/selection_triplet_start 1)
  (reset! atoms/selection_triplet_end 8)
  (reset! atoms/selection_sixteen_start 1)
  (reset! atoms/selection_sixteen_end 8))

(defn set_selection
  "set active selection atoms to cover notes"
  [sel_starts sel_ends]
  (let
   [bar_start (nth sel_starts 0)
    eight_start (nth sel_starts 1)
    triplet_start (nth sel_starts 2)
    sixteen_start (nth sel_starts 3)
    bar_end (nth sel_ends 0)
    eight_end (nth sel_ends 1)
    triplet_end (nth sel_ends 2)
    sixteen_end (nth sel_ends 3)]
    (reset! atoms/selection_bar_start     bar_start)
    (reset! atoms/selection_bar_end       bar_end)
    (reset! atoms/selection_eight_start   eight_start)
    (reset! atoms/selection_eight_end     eight_end)
    (reset! atoms/selection_triplet_start triplet_start)
    (reset! atoms/selection_triplet_end   triplet_end)
    (reset! atoms/selection_sixteen_start sixteen_start)
    (reset! atoms/selection_sixteen_end   sixteen_end)))

(defn add_bars_until_n
  "add empty bars to score until n bars reached
   Args: n_bars_max (int>0) number of bars to get to"
  [n_bars_max]
  (let [length_active_score (count @atoms/active_score)]
    ; if bar count not reached
    (if (< length_active_score n_bars_max)
      ;; add missing bars
      (dotimes [i (- n_bars_max length_active_score)]
        (ss/add_one_score_at_score_end)))))

(defn set_scale_of_bar
  "Set the scale of a full single bar (on active score)
   Args: 
     bar_n (int>0): bar number on which to apply scale change
     scale_id (str): ':id' of scale to attach on bar notes
  "
  [score_n bar_n scale_id]
  ; set score to modify
  (reset! atoms/active_scores_n [score_n])
  ; set selection on target bar (cover all bar notes)
  (set_selection_on_full_bar bar_n)
  ; set scale atom 
  (reset! atoms/active_scale scale_id)
  ; set active view bar in GUI
  (reset! atoms/active_view_bar bar_n)
  ; set generator atom to change scale
  (reset! atoms/active_generator mgens/gen_note_from_scale)
  ; set compatible filter: accept all generator changes
  (reset! atoms/active_filter mfilts/filter_accept_all)
  ; set gen_map atom to all sixteen notes
  (swaps_gen_maps/reset_sixteen_gen_maps_with_active_gen_filt)
  ; apply (on all bar sixteen)
  (ss/reset_fill_score_with_active_gen_map)
  ; set gen_map atom to all triplet notes
  (swaps_gen_maps/reset_triplet_gen_maps_with_active_gen_filt)
  ; re-apply (for bar triplets)
  ; reset active_score for GUI sync is done in ss/reset_fill...
  (ss/reset_fill_score_with_active_gen_map))

(defn fbars
  [score_n gen_maps_orders sel_starts sel_ends patterns]
  ; set score to modify
  (reset! atoms/active_scores_n [score_n])
  ; set selection on target bar (cover all bar notes)
  (set_selection sel_starts sel_ends)
  ; set patterns atom 
  (let [cycled_patterns
        (vec (take
              (+ (- (nth sel_ends 0) (nth sel_starts 0)) 1) (cycle patterns)))]
    (reset! atoms/active_patterns cycled_patterns))
  ; set indexes offset between first bar of score to edit 
  ; and first bar of patterns
  (reset! atoms/active_patterns_delta (- (nth sel_starts 0) 1))
  ; set generator atom to change pattern
  (reset! atoms/active_generator mgens/gen_note_from_pattern)
  ; set compatible filter: accept all generator changes
  (reset! atoms/active_filter mfilts/filter_accept_all)
  ; set gen_map atom to all sixteen notes
  ; apply (on all bar sixteen)
  (cond (in? (split_chars gen_maps_orders) "s")
        (do
          (println "apply on sixteen")
          (swaps_gen_maps/reset_sixteen_gen_maps_with_active_gen_filt)
          (ss/reset_fill_score_with_active_gen_map)))
  (cond (in? (split_chars gen_maps_orders) "t")
        (do
          (println "apply on triplets")
          (swaps_gen_maps/reset_triplet_gen_maps_with_active_gen_filt)
          (ss/reset_fill_score_with_active_gen_map)))
  ; reset active_score for GUI sync is done in ss/reset_fill...
  ; set active view bar in GUI
  (reset! atoms/active_view_bar (nth sel_starts 0))
  "fbars")
