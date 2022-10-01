
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