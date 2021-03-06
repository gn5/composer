(ns sixdim.score.scales.diminished
  (:use overtone.core)
  (:require
    [sixdim.score.scales :as scales])
  (:gen-class))

(def diminished_scale_map
; barry harris style scales
; downbeats are diminished7th
 {
 "M6"
    {:id "M6"
     :id_long "CxM6 --- Cx maj 6 dim  --- Axmin7"
     :downbeats [0 4 7 9]
     :upbeats [2 5 11]
     :scale_chromatics [8]
     :other_chromatics [1 3 6 10]}

 "m6"
    {:id "m6"
     :id_long "Cxm6 --- Cx min 6 dim  --- Axmin7b5"
     :downbeats [0 3 7 9]
     :upbeats [2 5 11]
     :scale_chromatics [8]
     :other_chromatics [1 4 6 10]}

 "S6"
    {:id "S6"
     :id_long "CxS6 --- Cx maj s6 dim --- Cxdom7"
     :downbeats [0 4 7 10]
     :upbeats [2 5 11]
     :scale_chromatics [8]
     :other_chromatics [1 3 6 9]}

 "s6"
    {:id "s6"
     :id_long "Cxs6 --- Cx min s6 dim --- Cxmin7"
     :downbeats [0 3 7 10]
     :upbeats [2 5 11]
     :scale_chromatics [8]
     :other_chromatics [1 4 6 9]}

 "M5"
    {:id "M5"
     :id_long "CxM5 --- Cx maj b5 6 dim --- Axmin dim7"
     :downbeats [0 4 6 9]
     :upbeats [2 5 11]
     :scale_chromatics [8]
     :other_chromatics [1 3 7 10]}

 "m5"
    {:id "m5"
     :id_long "Cxm5 --- Cx min b5 6 dim --- Axdim7 - Cxdim7"
     :downbeats [0 3 6 9]
     :upbeats [2 5 11]
     :scale_chromatics [8]
     :other_chromatics [1 4 7 10]}

 "S5"
    {:id "S5"
     :id_long "CxS5 --- Cx maj b5 s6 dim --- Cxdom7b5"
     :downbeats [0 4 6 10]
     :upbeats [2 5 11]
     :scale_chromatics [8]
     :other_chromatics [1 3 7 9]}

 "s5"
    {:id "s5"
     :id_long "Cxs5 --- Cx min b5 s6 dim --- Cxmin7b5"
     :downbeats [0 3 6 10]
     :upbeats [2 5 11]
     :scale_chromatics [8]
     :other_chromatics [1 4 7 9]}
 })

; (scales/shift_note_nooctave "Db4" - 3)
; (scales/shift_note_nooctave "C4" - 3)

(defn id_to [r v] 
  (str (scales/flat_to_case r) v)) 

; (id_to "Db" "M6")

(defn id_long_to [r v]
  (clojure.string/replace  
    (clojure.string/replace v "Cx" r) 
    "Ax" (scales/shift_note_nooctave (str r "4") - 3))) 

; (id_long_to "Db" "Cxm5 --- Cx min b5 6 dim --- Axdim7 - Cxdim7")
    
(defn downbeats_to [r v]
  (mapv #(scales/shift_note_nooctave (str r "4") + %) v)) 

; (downbeats_to "Db" [0 3 6 10])
; (downbeats_to "C" [0 3 6 10])

(defn upbeats_to [r v]
  (mapv #(scales/shift_note_nooctave (str r "4") + %) v)) 

(defn scale_chromatics_to [r v]
  (mapv #(scales/shift_note_nooctave (str r "4") + %) v)) 

(defn other_chromatics_to [r v]
  (mapv #(scales/shift_note_nooctave (str r "4") + %) v)) 

(defn n_to_diminished_scale [n scale_id] 
  (let [root (scales/c_inc_to n)]
  (as-> (diminished_scale_map scale_id) v
    (assoc v :id (id_to root (:id v)))
    (assoc v :id_long (id_long_to root (:id_long v)))
    (assoc v :downbeats (downbeats_to root (:downbeats v)))
    (assoc v :upbeats (upbeats_to root (:upbeats v)))
    (assoc v :scale_chromatics (scale_chromatics_to root (:scale_chromatics v)))
    (assoc v :other_chromatics (other_chromatics_to root (:other_chromatics v))))))

; (n_to_diminished_scale 1 "m5")
; (scales_dim/n_to_diminished_scale 0 "m5")

; (mapv #(map (fn [a b] (n_to_diminished_scale a b)) (range 12) %1) 
     ; (keys diminished_scale_map))

(defn add_diminished_scales [scales]
  (flatten
    (conj scales 
      (mapv #(map (fn [a] (n_to_diminished_scale a %1)) (range 12)) 
        (keys diminished_scale_map)))))

; (pprint (scales_dim/add_diminished_scales []))
