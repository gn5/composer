(ns sixdim.gui.horizontal2
  (:require [sixdim.gui.core :as guicore]
            [membrane.ui :as ui])
  (:gen-class))

(defn horizontal2 [_]
 (ui/vertical-layout 
    (guicore/ll "--------- --------- --------- --------- --------- --------- ")))

