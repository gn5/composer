(ns sixdim.gui.horizontal2
  (:require 
    [sixdim.print.score :as print_score]
    [sixdim.gui.core :as guicore]
    [membrane.ui :as ui])
  (:gen-class))

(defn horizontal2 [
                   [
                   active_score_
                   active_view_bar_
                   text_hoz2_1_
                   text_hoz2_2_
                   text_hoz2_3_
                   text_hoz2_4_]
                   ]
 (ui/vertical-layout 
    (guicore/ll "--------- --------- --------- --------- --------- --------- ")

 (guicore/ll (str "active_view_bar: " active_view_bar_))
    
 (guicore/ll
  (print_score/print_score_sel_bars_notes 
    active_score_ 
    active_view_bar_
    active_view_bar_ 5))
 (guicore/ll
  (print_score/print_score_sel_bars_scales 
    active_score_ 
    active_view_bar_
    active_view_bar_ 5))
 (guicore/ll
  (print_score/print_score_sel_bars_vols
    active_score_
    active_view_bar_
    active_view_bar_ 5))
 (guicore/ll
  (print_score/print_score_sel_bars_durations
    active_score_
    active_view_bar_
    active_view_bar_ 7))

  (guicore/ll (str text_hoz2_1_))
  ; (guicore/ll (str text_hoz2_2_))
  ; (guicore/ll (str text_hoz2_3_))
  ; (guicore/ll (str text_hoz2_4_))
    ))

