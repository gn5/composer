(ns sixdim.score.undo
  (:use overtone.core)
  (:require
    ; [sixdim.state_defs :as state_defs]
    [sixdim.common_fns :as common_fns]
    )
  (:gen-class))

(defn add_scores_to_buffer [scores_buffer scores n_max]
  (cond (> (count scores) n_max)
        (take n_max (shuffle scores))
        :else 
        scores))

; (swap! atoms/scores_buffer undo/add_scores_to_buffer  
       ; (map #(:score %) (melody/gen_melody
                          ; score_to_swap_
                          ; gen_maps_
                          ; scales_)) state_defs/max_scores_buffer)

; (swap! atoms/scores_buffer undo/add_scores_to_buffer 
       ; (map #(:score %) [{:score [1 2] :a "b"} {:score [3 4] :a "b"}])
                        ; state_defs/max_scores_buffer)
; (pprint @atoms/scores_buffer)




(defn add_score_to_undo_buffer [undo_buffer score n_max]
"add (conj) score to be replaced score into buffer for undos
 (for swap! on undo_buffer atom)"
   (as-> 
     ;first add score to end of undo buffer
     {:back (conj (:back undo_buffer) score) 
      :forw (:forw undo_buffer)} v
     ;remove first element of buffer if reached max length
     ; apply anonymous function to new undo_buffer
     (#(cond (> (count (:back %)) n_max)
             {:back (subvec (:back %) 1)
              :forw (:forw %)} 
             :else %) 
      v)))
; (add_score_to_undo_buffer {:back [1 2 3]} 4 4)
; (add_score_to_undo_buffer {:back [1 2 3]} 4 3)

(defn remove_last_undo_score [undo_buffer]
     {:back (pop (:back undo_buffer)) 
      :forw (:forw undo_buffer)})

(defn remove_last_redo_score [redo_buffer]
     {:forw (pop (:forw redo_buffer)) 
      :back (:back redo_buffer)})

(defn add_score_to_redo_buffer [redo_buffer score n_max]
   (as-> 
     ;first add score to end of redo buffer
     {:forw (conj (:forw redo_buffer) score) 
      :back (:back redo_buffer)} v
     ;remove first element of buffer if reached max length
     ; apply anonymous function to new redo_buffer
     (#(cond (> (count (:forw %)) n_max)
             {:forw (subvec (:forw %) 1)
              :back (:back %)} 
             :else %) 
      v)))
