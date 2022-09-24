(ns sixdim.score.swaps.chord_prog.clj
  (:use overtone.core)
  (:require
   ; [sixdim.state_defs :as state_defs]
   [sixdim.atoms :as atoms]
   [sixdim.score.melody_generators :as mgens]
   [sixdim.score.melody_filters :as mfilts]
   [sixdim.score.swaps.gen_maps :as swaps_gen_maps]
   [sixdim.score.swaps.core :as ss]
   ; [sixdim.score.swaps.undo :as ss_undo]
   ; [sixdim.common_fns :as common_fns]
   ; [sixdim.score.score :as score]
   ; [sixdim.score.undo :as undo]
   ; [sixdim.score.melody :as melody]
    ; [sixdim.score.swaps.gen_maps :as swaps_gen_maps]
   [sixdim.score.swaps.core :as score_swaps])
  (:gen-class))

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

; (set_selection [2 2 2 2] [3 3 3 3])

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
  [bar_n scale_id]
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

;;; start example usage
(reset! atoms/active_scores_n [1]) ; set number of score to edit

(add_bars_until_n 7); set score length to 10 bars
(set_scale_of_bar 1 "C14") ; change scale of bar 1
(set_scale_of_bar 2 "C24") ; change scale of bar 2
(set_scale_of_bar 3 "C34")
(set_scale_of_bar 4 "C44")
(set_scale_of_bar 5 "C54")
(set_scale_of_bar 6 "C64")
(set_scale_of_bar 7 "C74")
(reset! atoms/active_view_bar 2) ; GUI-look at bar 1 
(reset! atoms/active_view_bar 1) ; GUI-look at bar 2
;;;; end example usage

(def fbar_test_pattern_0
  "full bar (fbar) test pattern"
  {"quarter" ["ch1d" "ch1d" "ch1d" "ch1d"]
   "eight"   ["ch1d" "ch1d" "ch1d" "ch1d"]
   "triplet" ["ch1d" "ch1d" "ch1d" "ch1d" "ch1d" "ch1d" "ch1d" "ch1d"]
   "sixteen" ["ch1d" "ch1d" "ch1d" "ch1d" "ch1d" "ch1d" "ch1d" "ch1d"]})

;; ch1d3: first chord note of downbeats (when ordered in first inversion) 
;;        in octave 3
;; u1dq: up-1 from previous downbeat quarter-note
;; d1dq: down-1 from previous downbeat quarter-note
(def fbar_pattern_quarter_arp_up_inv1
  "chord arpeggio from 1st note of downbeats"
  {"quarter" ["ch1d3" "u1dq" "u1dq" "u1dq"]
   "eight"   [0 0 0 0]
   "triplet" [0 0 0 0 0 0 0 0]
   "sixteen" [0 0 0 0 0 0 0 0]})

(def fbar_pattern_quarter_arp_down_inv1
  "chord arpeggio from 1st note of downbeats"
  {"quarter" ["ch1d4" "d1dq" "d1dq" "d1dq"]
   "eight"   [0 0 0 0]
   "triplet" [0 0 0 0 0 0 0 0]
   "sixteen" [0 0 0 0 0 0 0 0]})

(defn apply_fbars_patterns
  "apply full bar notes list of patterns 
  into score from bar_start to bar_end
   Args: 
     patterns: list of patterns to transform into score"
  [sel_starts sel_ends patterns]
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
  (swaps_gen_maps/reset_sixteen_gen_maps_with_active_gen_filt)
  ; apply (on all bar sixteen)
  (ss/reset_fill_score_with_active_gen_map)
  ; set gen_map atom to all triplet notes
  (swaps_gen_maps/reset_triplet_gen_maps_with_active_gen_filt)
  ; re-apply (for bar triplets)
  ; reset active_score for GUI sync is done in ss/reset_fill...
  (ss/reset_fill_score_with_active_gen_map)
  ; set active view bar in GUI
  (reset! atoms/active_view_bar (nth sel_starts 0))
  "apply_fbars_patterns")

(apply_fbars_patterns
 [0 0 0 0] [7 8 8 8]
 [{"quarter" ["play_0" "play_0" "play_0" "play_0"]
   "eight"   ["play_0" "play_0" "play_0" "play_0"]
   "triplet" ["play_0" "play_0" "play_0" "play_0" 
              "play_0" "play_0" "play_0" "play_0"]
   "sixteen" ["play_0" "play_0" "play_0" "play_0" 
              "play_0" "play_0" "play_0" "play_0"]}])

(apply_fbars_patterns 
 [1 0 0 0] [7 8 8 8]
 [{"quarter" ["ch1d3" "ch1d3" "ch1d3" "ch1d3"]
   "eight"   [1 1 1 1]
   "triplet" [1 1 1 1 1 1 1 1]
   "sixteen" [1 1 1 1 1 1 1 1]}])

(println (map #(:bar %) @atoms/gen_maps))
(println (map #(:n %) @atoms/gen_maps))