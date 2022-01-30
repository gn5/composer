(ns sixdim.gui.horizontal3
  (:require [sixdim.gui.core :as guicore]
            [membrane.ui :as ui])
  (:gen-class))

(defn horizontal3 [
                   [text_hoz3_1_
                   text_hoz3_2_
                   text_hoz3_3_
                   text_hoz3_4_]
                   ]
 (ui/vertical-layout 
    (guicore/ll "--------- --------- --------- --------- --------- ----------- ")

  (guicore/ll (str text_hoz3_1_)) ;menu
  ; (guicore/ll (str text_hoz3_2_))
  ; (guicore/ll (str text_hoz3_3_))
  ; (guicore/ll (str text_hoz3_4_))
    ))

