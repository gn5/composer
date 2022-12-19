(ns sixdim.score.scales.modes_four
  (:use overtone.core)
  (:require
   [sixdim.score.scales :as scales])
  (:gen-class))


(def modes4_scale_map
  ; the seven modes of the major scale as 7 'scales'
  ; 4 notes in :downbeats (i.e. 4-notes '7th'-chord)
  ; 3 remaining notes in :upbeats
  {"14" ; first digit is starting note of mode (1 to 7, from C to B in C major)
        ; second digit is '4', meaning 7th-'chord' notes in :downbeats
   {:id "14"
    :id_long "1st 4-notes mode in Cx major"
    :downbeats [0 4 7 11] ; do mi sol si
    :upbeats [2 5 9] ;      re fa la
    :scale_chromatics [] ; no semitone between B and C
    :other_chromatics [1 3 6 8 10]}

   "24"
   {:id "24"
    :id_long "2nd 4-notes mode in Cx major"
    :downbeats [0 2 5 9] ; do re fa la
    :upbeats [4 7 11] ;       mi sol si
    :scale_chromatics [1] ; semitone between do and re 
    :other_chromatics [3 6 8 10]}

   "34"
   {:id "34"
    :id_long "3rd 4-notes mode in Cx major"
    :downbeats [2 4 7 11] 
    :upbeats [0 5 9]    
    :scale_chromatics [3]
    :other_chromatics [1 6 8 10]}

   "44"
   {:id "44"
    :id_long "4th 4-notes mode in Cx major"
    :downbeats [0 4 5 9]
    :upbeats [2 7 11]   
    :scale_chromatics [] 
    :other_chromatics [1 3 6 8 10]}
   
   "54"
   {:id "54"
    :id_long "5th 4-notes mode in Cx major"
    :downbeats [2 5 7 11]
    :upbeats [0 4 9]     
    :scale_chromatics [6] 
    :other_chromatics [1 3 8 10]}
   
   "64"
   {:id "64"
    :id_long "6th 4-notes mode in Cx major"
    :downbeats [0 4 7 9]
    :upbeats [2 5 11]
    :scale_chromatics [8]
    :other_chromatics [1 3 6 10]}
   
   "74"
   {:id "74"
    :id_long "7th 4-notes mode in Cx major"
    :downbeats [2 5 9 11]
    :upbeats [0 4 7]     
    :scale_chromatics [10]
    :other_chromatics [1 3 6 8]}
   })

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

(defn n_to_modes4_scale [n scale_id]
  (let [root (scales/c_inc_to n)]
    (as-> (modes4_scale_map scale_id) v
      (assoc v :id (id_to root (:id v)))
      (assoc v :id_long (id_long_to root (:id_long v)))
      (assoc v :downbeats (downbeats_to root (:downbeats v)))
      (assoc v :upbeats (upbeats_to root (:upbeats v)))
      (assoc v :scale_chromatics (scale_chromatics_to root (:scale_chromatics v)))
      (assoc v :other_chromatics (other_chromatics_to root (:other_chromatics v))))))

; (n_to_modes4_scale 1 "m5")
; (scales_dim/n_to_modes4_scale 0 "m5")

; (mapv #(map (fn [a b] (n_to_modes4_scale a b)) (range 12) %1) 
     ; (keys modes4_scale_map))

(defn add_modes4_scales [scales]
  (flatten
   (conj scales
         (mapv #(map (fn [a] (n_to_modes4_scale a %1)) (range 12))
               (keys modes4_scale_map)))))

; (print (add_modes4_scales []))
