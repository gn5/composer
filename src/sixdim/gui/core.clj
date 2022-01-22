(ns sixdim.gui.core
  (:use overtone.core)
  (:require 
    [membrane.ui :as ui]
    [sixdim.state_defs :as state_defs])
  (:gen-class))

(def selection_eight_convert_map {
  "1" {:bar_key "quarter" :key_n 1}
  "2" {:bar_key "eight"   :key_n 1}
  "3" {:bar_key "quarter" :key_n 2}
  "4" {:bar_key "eight"   :key_n 2}
  "5" {:bar_key "quarter" :key_n 3}
  "6" {:bar_key "eight"   :key_n 3}
  "7" {:bar_key "quarter" :key_n 4}
  "8" {:bar_key "eight"   :key_n 4}}) 

(defn convert_eight_sel [sel_1_8]
  (selection_eight_convert_map (str sel_1_8)))
; (convert_eight_sel 1)

(defn ll [str_] 
  (ui/padding 0 3
    (ui/label str_ (ui/font state_defs/default_font 
                            state_defs/default_font_size))))

(defn sv [_] (ui/spacer 0 _))

