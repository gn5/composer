(ns sixdim.gui.keypress
  (:use overtone.core)
  (:require 
            [sixdim.score.score :as score])
            ;not used: [sixdim.gui.core :as guicore]
            ;not used: [membrane.ui :as ui]
  (:gen-class))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(add-watch key_press :key_press_watcher
  (fn [key atom old-state new-state]
    (cond 

      (= "t" new-state)
      (do 
      (reset! log1 "(t) swap! menu to base")
      (reset! menu "base"))

      (= "m" new-state)
      (do 
      ; todo replace score1 with active bar
      (swap! score1 score/add_bars_at_score_end init_bar)
      (reset! log1 "(m) swap! score add_bars_at_score_end init_bar"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; selection

      (= "n" new-state)
      (do 
      (swap! selection_bar_start dec)
      (reset! log1 "(n) selection_bar_start dec"))
      (= "e" new-state)
      (do 
      (swap! selection_bar_start inc)
      (reset! log1 "(e) selection_bar_start inc"))

      (= "i" new-state)
      (do 
      (swap! selection_bar_end dec)
      (reset! log1 "(i) selection_bar_end dec"))
      (= "o" new-state)
      (do 
      (swap! selection_bar_end inc)
      (reset! log1 "(o) selection_bar_end inc"))


      ))) 
        
