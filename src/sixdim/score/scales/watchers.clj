(ns sixdim.score.scales.watchers
  (:use overtone.core)
  (:require 
    ; [sixdim.state_defs :as state_defs]
    [sixdim.atoms :as atoms] 
    [sixdim.score.scales :as scales]
    [sixdim.common_fns :as common_fns]
    ; [sixdim.score.score :as score]
    ; [sixdim.score.melody :as melody]
    ; [sixdim.score.undo :as undo]
    )
  (:gen-class))

(add-watch atoms/active_scale :active_scale_watcher
  (fn [key atom old-state new-state]
    (cond (= 3 (count new-state))
          (reset! atoms/text_hoz2_1
            (str "scale: " 
 "id         " (println-str (:id (scales/get_scale new-state @atoms/scales)))
 "id_long    " (println-str (:id_long (scales/get_scale new-state @atoms/scales)))
 "downbeats  " (println-str (:downbeats (scales/get_scale new-state @atoms/scales)))
 "upbeats    " (println-str (:upbeats (scales/get_scale new-state @atoms/scales)))
 "scale_chr  " (println-str (:scale_chromatics (scales/get_scale new-state @atoms/scales)))
 "other_chr  " (println-str (:other_chromatics (scales/get_scale new-state @atoms/scales)))
              ))
          :else 
          (reset! atoms/text_hoz2_1 "---"))))
