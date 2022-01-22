(ns sixdim.gui.horizontal2
  (:require [sixdim.gui.core :as guicore]
            [membrane.ui :as ui])
  (:gen-class))

(defn horizontal2 [
                   [text_hoz2_1_
                   text_hoz2_2_
                   text_hoz2_3_
                   text_hoz2_4_]
                   ]
 (ui/vertical-layout 
    (guicore/ll "--------- --------- --------- --------- --------- --------- ")
    
  (guicore/ll (str text_hoz2_1_))
  (guicore/ll (str text_hoz2_2_))
  (guicore/ll (str text_hoz2_3_))
  (guicore/ll (str text_hoz2_4_))
    ))

