(ns sixdim.init.scales
  (:use overtone.core)
  (:require
   [sixdim.atoms :as atoms]
   [sixdim.score.scales.diminished :as scales_dim]
   [sixdim.score.scales.modes_four :as scales_modes_four]
   [sixdim.score.scales.modes_three :as scales_modes_three])
  (:gen-class))

; load-in custom scales 
(reset! atoms/scales [])
(swap! atoms/scales scales_dim/add_diminished_scales)
(swap! atoms/scales scales_modes_four/add_modes4_scales)
(swap! atoms/scales scales_modes_three/add_modes3_scales)

; (print (map #(:id %)  @atoms/scales))
(count @atoms/scales)