(ns sixdim.score.swaps.core
  (:use overtone.core)
  (:require 
    [sixdim.state_defs :as state_defs]
    [sixdim.atoms :as atoms] 
    [sixdim.common_fns :as common_fns]
    [sixdim.score.score :as score]
    [sixdim.score.undo :as undo]
    [sixdim.score.melody :as melody]
    ; [sixdim.score.swaps.gen_maps :as swaps_gen_maps]
    )
  (:gen-class))

(defn swap_active_score
"swap! active score"
  [swap_function]
  (let [int_active_score 
          (first @atoms/active_scores_n)]
    (let [score_atom_to_swap
          (common_fns/int_to_score_atom int_active_score)]
      (swap! score_atom_to_swap swap_function))))

(defn reset_active_score
"swap! active score"
  [reset_function]
  (let [int_active_score 
          (first @atoms/active_scores_n)]
    (let [score_atom_to_reset
          (common_fns/int_to_score_atom int_active_score)]
      (reset! score_atom_to_reset reset_function))))

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
                   (< (first a) 8) ;max score is n 8  
                   (update-in a [0] inc)
                   :else ; score can't be below 1
                   a))))
; (increment_active_score_n)
(defn decrement_active_view_bar []
  (swap! atoms/active_view_bar
         (fn [a] (cond 
                   (>= a 2) ;min score is n 1  
                   (dec a)
                   :else ; score can't be below 1
                   a))))

(defn increment_active_view_bar [max_bars]
  (swap! atoms/active_view_bar
         (fn [a] (cond 
                       ;inf max bars in score  
                   (< a max_bars) 
                   (inc a)
                   :else ; score can't be below 1
                   a))))

(defn decrement_loop_start_bar []
  (swap! atoms/loop_start_bar
         (fn [a] (cond 
                   (>= a 2)
                   (dec a)
                   :else
                   a))))

(defn increment_loop_start_bar [loop_end_bar]
  (swap! atoms/loop_start_bar
         (fn [a] (cond 
                   (< a loop_end_bar) 
                   (inc a)
                   :else
                   a))))

(defn decrement_loop_end_bar [loop_start_bar]
  (swap! atoms/loop_end_bar
         (fn [a] (cond 
                   (> a loop_start_bar)
                   (dec a)
                   :else
                   a))))

(defn increment_loop_end_bar [max_bars]
  (swap! atoms/loop_end_bar
         (fn [a] (cond 
                   (< a max_bars) 
                   (inc a)
                   :else
                   a))))

(defn decrement_selection_start_bar []
      (swap! atoms/selection_bar_start dec))
(defn increment_selection_start_bar []
      (swap! atoms/selection_bar_start inc))

(defn decrement_selection_end_bar []
      (swap! atoms/selection_bar_end dec))
(defn increment_selection_end_bar []
      (swap! atoms/selection_bar_end inc))

; (defonce selection_eight_start (atom 1))
; (defonce selection_eight_end (atom 8))

(defn decrement_selection_start_eight []
      (swap! atoms/selection_eight_start dec))
(defn increment_selection_start_eight []
      (swap! atoms/selection_eight_start inc))

(defn decrement_selection_end_eight []
      (swap! atoms/selection_eight_end dec))
(defn increment_selection_end_eight []
      (swap! atoms/selection_eight_end inc))

; (defn remove_score_bars [score index n_bars]
 
; (def mapping_selection_start_bar_eights
; (def mapping_selection_end_bar_eights
; (def all_bar_eigths


; (reset! atoms/selection_bar_start 1)
; (reset! atoms/selection_bar_end 4)
; (reset! atoms/selection_eight_start 8)
; (reset! atoms/selection_eight_end 1)
; (reset! atoms/active_generator
;         mgens/gen_note_from_intervals_seconds_down)
; (reset! atoms/active_generator
;         mfilters/filter_accept_bh)
; (score_swaps/fill_eight_gen_maps_with_active_gen_filt)
; (pprint (score_swaps/fill_eight_gen_maps_with_active_gen_filt))

; gen_melody [score gen_maps scales]
(defn reset_fill_score_with_active_gen_map []
  (let [gen_maps_ @atoms/gen_maps
        scales_ @atoms/scales
        int_active_score 
          (first @atoms/active_scores_n)]
    (let [
          score_to_swap_
          (common_fns/int_to_score int_active_score)
          score_atom_to_swap_
          (common_fns/int_to_score_atom int_active_score)
          undo_atom_to_swap_
          (common_fns/int_to_undo_atom int_active_score)]
      (do         
      ;add scores to scores buffer
      (swap! atoms/scores_buffer
             undo/add_scores_to_buffer  
             (vec (map #(:score %)
                       (melody/gen_melody 
                         score_to_swap_ gen_maps_ scales_)))
             state_defs/max_scores_buffer)
      ;add previous score to undo buffer
      (swap! undo_atom_to_swap_ 
             undo/add_score_to_undo_buffer
             score_to_swap_ ;(first @atoms/scores_buffer) 
             state_defs/max_redo)
      ;add first found score in current active score
      (reset! score_atom_to_swap_ (first @atoms/scores_buffer))
      ;reset active_score for GUI sync
      (reset! atoms/active_score (first @atoms/scores_buffer))
      ))))

; (reset! atoms/selection_bar_start 1)
; (reset! atoms/selection_bar_end 4)
; (reset! atoms/selection_eight_start 8)
; (reset! atoms/selection_eight_end 1)
; (reset! atoms/active_generator
;         mgens/gen_note_from_intervals_seconds_down)
; (reset! atoms/active_generator
;         mfilters/filter_accept_bh)
; (reset_eight_gen_maps_with_active_gen_filt)
; (score_swaps/reset_fill_score_with_active_gen_map)


; (print_score/print_bar_notes @atoms/score1 1 5)
; (pprint @atoms/gen_maps)
; @atoms/scales
; (melody/gen_melody @atoms/score1 @atoms/gen_maps @atoms/scales)


; (defonce scores_buffer (atom []))
; active index being checked out (range 1 to count)
; (defonce index_scores_buffer (atom 1))
; (defonce n_scores_buffer (atom 1))

(defn score_from_next_buffer []
  (let [int_active_score (first @atoms/active_scores_n) 
        new_buffer_index (inc @atoms/index_scores_buffer)]
    (let [
          score_to_swap_
          (common_fns/int_to_score int_active_score)
          score_atom_to_swap_
          (common_fns/int_to_score_atom int_active_score)
          undo_atom_to_swap_
          (common_fns/int_to_undo_atom int_active_score)]
    (if (> new_buffer_index (count @atoms/scores_buffer)) 
      nil
      (do        
      (let [new_score
           (nth @atoms/scores_buffer (- new_buffer_index 1))]

        (swap! atoms/index_scores_buffer inc)
        
        (swap! undo_atom_to_swap_ 
               undo/add_score_to_undo_buffer
               score_to_swap_ ;(first @atoms/scores_buffer) 
               state_defs/max_redo)

        (reset! score_atom_to_swap_ new_score)

        (reset! atoms/active_score new_score)))))))

(defn score_from_previous_buffer []
  (let [int_active_score (first @atoms/active_scores_n) 
        new_buffer_index (dec @atoms/index_scores_buffer)]
    (let [
          score_to_swap_
          (common_fns/int_to_score int_active_score)
          score_atom_to_swap_
          (common_fns/int_to_score_atom int_active_score)
          undo_atom_to_swap_
          (common_fns/int_to_undo_atom int_active_score)]
    (if (< new_buffer_index 1) 
      nil
      (do        
      (let [new_score
           (nth @atoms/scores_buffer (- new_buffer_index 1))]

        (swap! atoms/index_scores_buffer dec)
        
        (swap! undo_atom_to_swap_ 
               undo/add_score_to_undo_buffer
               score_to_swap_ ;(first @atoms/scores_buffer) 
               state_defs/max_redo)

        (reset! score_atom_to_swap_ new_score)

        (reset! atoms/active_score new_score)))))))

; (score_from_previous_buffer)

(defn add_one_score_at_score_end [] 
  (let [int_active_score (first @atoms/active_scores_n) 
        new_buffer_index (dec @atoms/index_scores_buffer)]
    (let [
          score_to_swap_
          (common_fns/int_to_score int_active_score)
          score_atom_to_swap_
          (common_fns/int_to_score_atom int_active_score)
          undo_atom_to_swap_
          (common_fns/int_to_undo_atom int_active_score)]
  (do
    (swap! undo_atom_to_swap_ 
           undo/add_score_to_undo_buffer
           score_to_swap_ ;(first @atoms/scores_buffer) 
           state_defs/max_redo)
    (swap_active_score
      #(score/add_bars_at_score_end % score/init_bar)) 
    (reset! atoms/active_scores_n 
            @atoms/active_scores_n)))))

(defn del_one_score_at_score_end []
  (let [int_active_score (first @atoms/active_scores_n) 
        new_buffer_index (dec @atoms/index_scores_buffer)]
    (let [
          score_to_swap_
          (common_fns/int_to_score int_active_score)
          score_atom_to_swap_
          (common_fns/int_to_score_atom int_active_score)
          undo_atom_to_swap_
          (common_fns/int_to_undo_atom int_active_score)]
  (do
    (swap! undo_atom_to_swap_ 
           undo/add_score_to_undo_buffer
           score_to_swap_ ;(first @atoms/scores_buffer) 
           state_defs/max_redo)
    (swap_active_score
      #(score/remove_score_bars % (count %) 1))
    (reset! atoms/active_scores_n 
            @atoms/active_scores_n)))))


