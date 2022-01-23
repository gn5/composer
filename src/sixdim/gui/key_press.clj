(ns sixdim.gui.key_press
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms]
    [sixdim.score.swaps :as score_swaps]
    )
  (:gen-class))
            ;not used: [sixdim.gui.core :as guicore]
            ;not used: [membrane.ui :as ui]

(add-watch atoms/key_press :key_press_watcher
  (fn [key atom old-state new-state]
    (cond 

      (= "t" new-state)
      (do 
      (reset! atoms/log1 "(t) swap! menu to base")
      (reset! atoms/menu "base"))

      (= "m" new-state)
      (do 
      ; todo replace score1 with active bar
      ; (swap! atoms/score1 score/add_bars_at_score_end init_bar)
      (reset! atoms/log1 "(m) swap! score add_bars_at_score_end init_bar"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; selection

      (= "n" new-state)
      (do 
      ; (swap! atoms/selection_bar_start dec)
      (reset! atoms/log1 "(n) selection_bar_start dec"))
      (= "e" new-state)
      (do 
      ; (swap! atoms/selection_bar_start inc)
      (reset! atoms/log1 "(e) selection_bar_start inc"))

      (= "i" new-state)
      (do 
      ; (swap! atoms/selection_bar_end dec)
      (reset! atoms/log1 "(i) selection_bar_end dec"))
      (= "o" new-state)
      (do 
      ; (swap! atoms/selection_bar_end inc)
      (reset! atoms/log1 "(o) selection_bar_end inc"))


      ))) 
        
