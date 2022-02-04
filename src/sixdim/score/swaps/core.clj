(ns sixdim.score.swaps.core
  (:use overtone.core)
  (:require 
    [sixdim.state_defs :as state_defs]
    [sixdim.atoms :as atoms] 
    [sixdim.common_fns :as common_fns]
    [sixdim.score.score :as score]
    [sixdim.score.undo :as undo]
    [sixdim.score.melody :as melody]
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

(defn add_one_score_at_score_end []
  (swap_active_score
    #(score/add_bars_at_score_end % score/init_bar)))

(defn del_one_score_at_score_end []
  (swap_active_score
    #(score/remove_score_bars % (count %) 1)))
; (defn remove_score_bars [score index n_bars]
 
; (def mapping_selection_start_bar_eights
; (def mapping_selection_end_bar_eights
; (def all_bar_eigths

(defn add_bar_to_gen_map [gen_map bar_n]
  (assoc gen_map :bar bar_n))

(defn add_bar_to_gen_maps [gen_maps bar_n]
  (map #(add_bar_to_gen_map % bar_n) gen_maps))

(defn add_bars_to_gen_maps [gen_maps bars]
  (reduce into []
  (map #(add_bar_to_gen_maps gen_maps %) bars)))

; (add_bars_to_gen_maps score/all_bar_eights [2 3])

(defn add_gen_to_gen_maps [gen gen_maps]
    (map (fn [gen_map] (assoc gen_map :g gen)) 
         gen_maps))

(defn add_filt_to_gen_maps [filt gen_maps]
    (map (fn [gen_map] (assoc gen_map :f filt)) 
         gen_maps))

(defn fill_eight_gen_maps_with_active_gen_filt []
"fill a gen_map in selection bar/eight boundary 
   with current active generator and filter"
  (let [;eight gen maps
        all_bar_maps score/all_bar_eights
        get_start_bar_maps score/mapping_selection_start_bar_eights 
        get_end_bar_maps score/mapping_selection_end_bar_eights 
        ;active generator and filter
        active_generator @atoms/active_generator
        active_filter @atoms/active_filter
        ; bounds from active selection
        start_bar @atoms/selection_bar_start
        end_bar @atoms/selection_bar_end
        start_eight @atoms/selection_eight_start
        end_eight @atoms/selection_eight_end
        bars_inside_range (range (+ 1 start_bar) end_bar)]    

  (reset! atoms/gen_maps
  (distinct 
  (reduce into [] [

    (as-> start_bar v
      (add_bars_to_gen_maps
        (get_start_bar_maps (str start_eight)) [v])
      (add_gen_to_gen_maps active_generator v)
      (add_filt_to_gen_maps active_filter v)
      (vec v))

    (as-> bars_inside_range v
      (add_bars_to_gen_maps all_bar_maps v)
      (add_gen_to_gen_maps active_generator v)
      (add_filt_to_gen_maps active_filter v)
      (vec v))

    (as-> end_bar v
      (add_bars_to_gen_maps
        (get_end_bar_maps (str end_eight)) [v])
      (add_gen_to_gen_maps active_generator v)
      (add_filt_to_gen_maps active_filter v)
      (vec v))
    ])))))

(defn reset_eight_gen_maps_with_active_gen_filt []
  (reset! atoms/gen_maps
    (fill_eight_gen_maps_with_active_gen_filt)))

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
