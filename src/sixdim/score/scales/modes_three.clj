(ns sixdim.score.scales.modes_three
  (:use overtone.core)
  (:require
   [sixdim.score.scales :as scales])
  (:gen-class))


(def modes3_scale_map
  ; the seven modes of the major scale as 7 'scales'
  ; 3 notes in :downbeats (i.e. 3-notes 'triad'-chord)
  ; 4 remaining notes in :upbeats
  {"13" ; first digit is starting note of mode (1 to 7, from C to B in C major)
        ; second digit is '3', meaning triad-'chord' notes in :downbeats
   {:id "13"
    :id_long "1st 3-notes mode in Cx major"
    :downbeats [0 4 7] ; do mi sol
    :upbeats [2 5 9 11] ; re fa la si
    :scale_chromatics []
    :other_chromatics [1 3 6 8 10]}

   "23"
   {:id "23"
    :id_long "2nd 3-notes mode in Cx major"
    :downbeats [2 5 9] ;  re fa la
    :upbeats [0 4 7 11] ; do mi sol si
    :scale_chromatics []  
    :other_chromatics [1 3 6 8 10]}

   "33"
   {:id "33"
    :id_long "3rd 3-notes mode in Cx major"
    :downbeats [4 7 11]
    :upbeats [0 2 5 9]
    :scale_chromatics []
    :other_chromatics [1 3 6 8 10]}

   "43"
   {:id "43"
    :id_long "4th 3-notes mode in Cx major"
    :downbeats [0 5 9]
    :upbeats [2 4 7 11]
    :scale_chromatics []
    :other_chromatics [1 3 6 8 10]}

   "53"
   {:id "53"
    :id_long "5th 3-notes mode in Cx major"
    :downbeats [5 7 11]
    :upbeats [0 2 4 9]
    :scale_chromatics []
    :other_chromatics [1 3 6 8 10]}

   "63"
   {:id "63"
    :id_long "6th 3-notes mode in Cx major"
    :downbeats [0 4 9]
    :upbeats [2 5 7 11]
    :scale_chromatics []
    :other_chromatics [1 3 6 8 10]}

   "73"
   {:id "73"
    :id_long "7th 3-notes mode in Cx major"
    :downbeats [2 5 11]
    :upbeats [0 4 7 9]
    :scale_chromatics []
    :other_chromatics [1 3 6 8 10]}})

; (scales/shift_note_nooctave "Db4" - 3)
; (scales/shift_note_nooctave "C4" - 3)

(defn id_to [r v]
  (str (scales/flat_to_case r) v))

; (id_to "Db" "M6")

(defn id_long_to [r v]
  (clojure.string/replace v "Cx" r))

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

(defn n_to_modes3_scale [n scale_id]
  (let [root (scales/c_inc_to n)]
    (as-> (modes3_scale_map scale_id) v
      (assoc v :id (id_to root (:id v)))
      (assoc v :id_long (id_long_to root (:id_long v)))
      (assoc v :downbeats (downbeats_to root (:downbeats v)))
      (assoc v :upbeats (upbeats_to root (:upbeats v)))
      (assoc v :scale_chromatics (scale_chromatics_to root (:scale_chromatics v)))
      (assoc v :other_chromatics (other_chromatics_to root (:other_chromatics v))))))

; (n_to_modes3_scale 1 "m5")
; (scales_dim/n_to_modes3_scale 0 "m5")

; (mapv #(map (fn [a b] (n_to_modes3_scale a b)) (range 12) %1) 
     ; (keys modes3_scale_map))

(defn add_modes3_scales [scales]
  (flatten
   (conj scales
         (mapv #(map (fn [a] (n_to_modes3_scale a %1)) (range 12))
               (keys modes3_scale_map)))))

; (pprint (scales_dim/add_modes3_scales []))
