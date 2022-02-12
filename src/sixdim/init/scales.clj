(ns sixdim.init.scales
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms] 
    [sixdim.score.scales.diminished :as scales_dim]
    )
  (:gen-class))
; load-in custom scales 

(swap! atoms/scales scales_dim/add_diminished_scales)
