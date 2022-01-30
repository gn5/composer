(ns sixdim.score.scales.dom_in_scale
  (:use overtone.core)
  (:gen-class))

(defn add_scale_G [scales]
  (conj scales
    {:id "GdC" ; G dom in Cmaj scale
     :id_long "G dom in Cmaj" 
     :downbeats ["G" "B" "D" "F"]
     :upbeats ["A" "C" "E"]
     :scale_chromatics ["Gb"]
     :other_chromatics ["Db" "Eb" "Ab" "Bb"]}))

