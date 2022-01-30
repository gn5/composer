(ns sixdim.score.scales.min_sixth_dim
  (:use overtone.core)
  (:gen-class))

(defn add_scale_C [scales]
  (conj scales
    {:id "Cm6"
     :id_long "Cmin6 sixth-dim" ; =A half dim
     :downbeats ["A" "C" "Eb" "G"]
     :upbeats ["B" "D" "F"]
     :scale_chromatics ["Ab"]
     :other_chromatics ["Db" "E" "Gb" "Bb"]}))
