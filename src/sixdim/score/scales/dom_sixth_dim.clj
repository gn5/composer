(ns sixdim.score.scales.dom_sixth_dim
  (:use overtone.core)
  (:gen-class))

(defn add_scale_C [scales]
  (conj scales
    {:id "CD6"
     :id_long "C dominant sixth-dim"
     :downbeats ["Bb" "C" "Eb" "G"]
     :upbeats ["B" "D" "F"]
     :scale_chromatics ["Ab"]
     :other_chromatics ["Db" "E" "Gb" "B"]}))
