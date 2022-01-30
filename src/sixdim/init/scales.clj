(ns sixdim.init.scales
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms] 
    [sixdim.score.scales.maj_sixth_dim 
      :as maj_sixth_dim]
    [sixdim.score.scales.min_sixth_dim 
      :as min_sixth_dim]
    [sixdim.score.scales.dom_sixth_dim 
      :as dom_sixth_dim]
    [sixdim.score.scales.dom_in_scale
      :as dom_in_scale]
    )
  (:gen-class))
; load-in custom scales 

; Barry Harris Cmaj6-dim: Amin7 with B diminshed
; white piano keys + G sharp
(swap! atoms/scales maj_sixth_dim/add_scale_C)

; G dominant in Cmaj (white piano keys + F sharp)
(swap! atoms/scales dom_in_scale/add_scale_G) 
