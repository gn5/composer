(ns sixdim.score.scales.maj_sixth_dim
  (:use overtone.core)
  (:gen-class))

(defn add_scale_C [scales]
  (conj scales
    {:id "CM6"
     :id_long "Cmaj6 sixth-dim" ; =Amin7
     :downbeats ["A" "C" "E" "G"]
     :upbeats ["B" "D" "F"]
     :scale_chromatics ["Ab"]
     :other_chromatics ["Db" "Eb" "Gb" "Bb"]}))
