(ns sixdim.score.swaps.gen_maps.core
  (:use overtone.core)
  ; (:require 
    ; [sixdim.state_defs :as state_defs]
    ; [sixdim.atoms :as atoms] 
    ; [sixdim.common_fns :as common_fns]
    ; )
  (:gen-class))

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

(defn add_scale_to_gen_maps [scale_id gen_maps]
    (map (fn [gen_map] (assoc gen_map :scale_id scale_id)) 
         gen_maps))
