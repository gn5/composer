(ns sixdim.gui.windows
  (:require
    [membrane.ui :as ui]
    [membrane.sixdim.state_defs :as state_defs]
    [sixdim.gui.horizontal1 :as hoz1]
    [sixdim.gui.horizontal2 :as hoz2]
    [sixdim.gui.horizontal3 :as hoz3])
  (:gen-class))

(defn main_window [bar_bpm_ loop_start_bar_
                loop_end_bar_ active_score_
                location_ key_press_
                to_midi_
                selection_bar_start_
                selection_bar_end_
                selection_eight_start_
                selection_eight_end_
                log1_ active_view_bar_
                bar_view_horizontal_
                bar_view_vertical_ menu_]
  [(ui/filled-rectangle state_defs/default_bg_color 2000 2000)
   (ui/with-color state_defs/default_color
     (ui/horizontal-layout
       (ui/padding 5 5
         (hoz1/horizontal1
           bar_bpm_ loop_start_bar_
                loop_end_bar_ active_score_
                location_ key_press_
                to_midi_
                selection_bar_start_
                selection_bar_end_
                selection_eight_start_
                selection_eight_end_
                log1_ active_view_bar_
                bar_view_horizontal_
                bar_view_vertical_ menu_))
       (ui/padding 5 5
         (hoz2/horizontal2 nil))
       (ui/padding 5 5
         (hoz3/horizontal3 nil))))])
