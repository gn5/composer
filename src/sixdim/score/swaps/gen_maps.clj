(ns sixdim.score.swaps.gen_maps
  (:use overtone.core)
  (:require 
    ; [sixdim.state_defs :as state_defs]
    [sixdim.atoms :as atoms] 
    [sixdim.score.swaps.gen_maps.eight :as gen_maps_eight]
    [sixdim.score.swaps.gen_maps.triplet :as gen_maps_triplet]
    [sixdim.score.swaps.gen_maps.sixteen :as gen_maps_sixteen]
    )
  (:gen-class))

(defn reset_eight_gen_maps_with_active_gen_filt []
  (reset! atoms/gen_maps
    (gen_maps_eight/fill_eight_gen_maps_with_active_gen_filt)))

(defn reset_triplet_gen_maps_with_active_gen_filt []
  (reset! atoms/gen_maps
    (gen_maps_triplet/fill_triplet_gen_maps_with_active_gen_filt)))

(defn reset_sixteen_gen_maps_with_active_gen_filt []
  (reset! atoms/gen_maps
    (gen_maps_sixteen/fill_sixteen_gen_maps_with_active_gen_filt)))



(defn first_eight_gen_maps []
  (reset! atoms/gen_maps
    (list (first 
    (gen_maps_eight/fill_eight_gen_maps_with_active_gen_filt)))))

(defn first_triplet_gen_maps []
  (reset! atoms/gen_maps
    (list (first 
    (gen_maps_triplet/fill_triplet_gen_maps_with_active_gen_filt)))))

(defn first_sixteen_gen_maps []
  (reset! atoms/gen_maps
    (list (first 
    (gen_maps_sixteen/fill_sixteen_gen_maps_with_active_gen_filt)))))



(defn ev2_eight_gen_maps []
  (reset! atoms/gen_maps
    (map first (partition-all 2
      (gen_maps_eight/fill_eight_gen_maps_with_active_gen_filt)))))

(defn ev3_eight_gen_maps []
  (reset! atoms/gen_maps
    (map first (partition-all 3
      (gen_maps_eight/fill_eight_gen_maps_with_active_gen_filt)))))

(defn ev4_eight_gen_maps []
  (reset! atoms/gen_maps
    (map first (partition-all 4
      (gen_maps_eight/fill_eight_gen_maps_with_active_gen_filt)))))

(defn ev2_m1_eight_gen_maps []
  (reset! atoms/gen_maps
    (map first (partition-all 2 (drop 1 
      (gen_maps_eight/fill_eight_gen_maps_with_active_gen_filt))))))

(defn ev3_m1_eight_gen_maps []
  (reset! atoms/gen_maps
    (map first (partition-all 3 (drop 1
      (gen_maps_eight/fill_eight_gen_maps_with_active_gen_filt))))))

(defn ev4_m1_eight_gen_maps []
  (reset! atoms/gen_maps
    (map first (partition-all 4 (drop 1
      (gen_maps_eight/fill_eight_gen_maps_with_active_gen_filt))))))

(defn complement_eight_gen_maps []
  (let [current_gm @atoms/gen_maps 
        full_gm (gen_maps_eight/fill_eight_gen_maps_with_active_gen_filt)] 
    (reset! atoms/gen_maps
      (clojure.set/difference full_gm current_gm))))

; (defn aa []
    ; (map first (partition-all 3 
      ; (gen_maps_eight/fill_eight_gen_maps_with_active_gen_filt))))

; (clojure.set/difference (vec (gen_maps_eight/fill_eight_gen_maps_with_active_gen_filt)) (vec (aa)))
; (count (aa))
; (first (aa))


(defn ev2_triplet_gen_maps []
  (reset! atoms/gen_maps
    (map first (partition-all 2
      (gen_maps_triplet/fill_triplet_gen_maps_with_active_gen_filt)))))

(defn ev3_triplet_gen_maps []
  (reset! atoms/gen_maps
    (map first (partition-all 3
      (gen_maps_triplet/fill_triplet_gen_maps_with_active_gen_filt)))))

(defn ev4_triplet_gen_maps []
  (reset! atoms/gen_maps
    (map first (partition-all 4
      (gen_maps_triplet/fill_triplet_gen_maps_with_active_gen_filt)))))

(defn ev2_m1_triplet_gen_maps []
  (reset! atoms/gen_maps
    (map first (partition-all 2 (drop 1 
      (gen_maps_triplet/fill_triplet_gen_maps_with_active_gen_filt))))))

(defn ev3_m1_triplet_gen_maps []
  (reset! atoms/gen_maps
    (map first (partition-all 3 (drop 1
      (gen_maps_triplet/fill_triplet_gen_maps_with_active_gen_filt))))))

(defn ev4_m1_triplet_gen_maps []
  (reset! atoms/gen_maps
    (map first (partition-all 4 (drop 1
      (gen_maps_triplet/fill_triplet_gen_maps_with_active_gen_filt))))))

(defn complement_triplet_gen_maps []
  (let [current_gm @atoms/gen_maps 
        full_gm (gen_maps_triplet/fill_triplet_gen_maps_with_active_gen_filt)] 
    (reset! atoms/gen_maps
      (clojure.set/difference full_gm current_gm))))




(defn ev2_sixteen_gen_maps []
  (reset! atoms/gen_maps
    (map first (partition-all 2
      (gen_maps_sixteen/fill_sixteen_gen_maps_with_active_gen_filt)))))

(defn ev3_sixteen_gen_maps []
  (reset! atoms/gen_maps
    (map first (partition-all 3
      (gen_maps_sixteen/fill_sixteen_gen_maps_with_active_gen_filt)))))

(defn ev4_sixteen_gen_maps []
  (reset! atoms/gen_maps
    (map first (partition-all 4
      (gen_maps_sixteen/fill_sixteen_gen_maps_with_active_gen_filt)))))

(defn ev2_m1_sixteen_gen_maps []
  (reset! atoms/gen_maps
    (map first (partition-all 2 (drop 1 
      (gen_maps_sixteen/fill_sixteen_gen_maps_with_active_gen_filt))))))

(defn ev3_m1_sixteen_gen_maps []
  (reset! atoms/gen_maps
    (map first (partition-all 3 (drop 1
      (gen_maps_sixteen/fill_sixteen_gen_maps_with_active_gen_filt))))))

(defn ev4_m1_sixteen_gen_maps []
  (reset! atoms/gen_maps
    (map first (partition-all 4 (drop 1
      (gen_maps_sixteen/fill_sixteen_gen_maps_with_active_gen_filt))))))

(defn complement_sixteen_gen_maps []
  (let [current_gm @atoms/gen_maps 
        full_gm (gen_maps_sixteen/fill_sixteen_gen_maps_with_active_gen_filt)] 
    (reset! atoms/gen_maps
      (clojure.set/difference full_gm current_gm))))







