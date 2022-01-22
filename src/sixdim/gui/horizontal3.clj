(ns sixdim.gui.horizontal3
  (:require [sixdim.gui.core :as guicore]
            [membrane.ui :as ui])
  (:gen-class))

(defn horizontal3 [_]
 (ui/vertical-layout 
    (guicore/ll "--------- --------- --------- --------- --------- --------- ")))

