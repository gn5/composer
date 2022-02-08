(ns sixdim.score.scales.min_sixth_dim
  (:use overtone.core)
  (:require
    [sixdim.score.scales :as scales])
  (:gen-class))

(defn add_scale_C [scales]
  (conj scales
    {:id "Cm6"
     :id_long "Cmin6 sixth-dim" ; =A half dim
     :downbeats ["A" "C" "Eb" "G"]
     :upbeats ["B" "D" "F"]
     :scale_chromatics ["Ab"]
     :other_chromatics ["Db" "E" "Gb" "Bb"]}))

    {:id "Cm6"
     :id_long "Cmin6 sixth-dim" ; =A half dim
     :downbeats ["A" "C" "Eb" "G"]
     :upbeats ["B" "D" "F"]
     :scale_chromatics ["Ab"]
     :other_chromatics ["Db" "E" "Gb" "Bb"]}))

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

(def flat_to_case {
  "C" "C" "Cb" "B" "C#" "d" 
  "D" "D" "Db" "d" "D#" "e" 
  "E" "E" "Eb" "e" "E#" "F" 
  "F" "F" "Fb" "f" "F#" "g" 
  "G" "G" "Gb" "g" "G#" "a" 
  "A" "A" "Ab" "a" "A#" "b" 
  "B" "B" "Bb" "b" "B#" "C" })

(def c_inc_to (partial scales/shift_note_nooctave "C4" +))
(c_inc_to 0)
(c_inc_to 1)
; (scales/c_inc_to 4)

; (scales/shift_note_nooctave "Db4" - 3)
(scales/shift_note_nooctave "C4" - 3)

(defn id_to [r v] 
  (str (flat_to_case r) v)) 

(id_to "Db" "M6")

(defn id_long_to [r v]
  (clojure.string/replace  
    (clojure.string/replace v "Cx" r) 
    "Ax" (scales/shift_note_nooctave (str r "4") - 3))) 

(id_long_to "Db" "Cxm5 --- Cx min b5 6 dim --- Axdim7 - Cxdim7")
    
(defn downbeats_to [r v]
  (mapv #(scales/shift_note_nooctave (str r "4") + %) v)) 

(downbeats_to "Db" [0 3 6 10])
(downbeats_to "C" [0 3 6 10])

(defn upbeats_to [r v]
  (mapv #(scales/shift_note_nooctave (str r "4") + %) v)) 

(defn scale_chromatics_to [r v]
  (mapv #(scales/shift_note_nooctave (str r "4") + %) v)) 

(defn other_chromatics_to [r v]
  (mapv #(scales/shift_note_nooctave (str r "4") + %) v)) 

(defn n_to_diminished_scale [n scale_id] 
  (let [root (c_inc_to n)]
  (as-> (diminished_scale_map scale_id) v
    (assoc v :id (id_to root (:id v)))
    (assoc v :id_long (id_long_to root (:id_long v)))
    (assoc v :downbeats (downbeats_to root (:downbeats v)))
    (assoc v :upbeats (upbeats_to root (:upbeats v)))
    (assoc v :scale_chromatics (scale_chromatics_to root (:scale_chromatics v)))
    (assoc v :other_chromatics (other_chromatics_to root (:other_chromatics v))))))

(n_to_diminished_scale 1 "m5")
(n_to_diminished_scale 0 "m5")







