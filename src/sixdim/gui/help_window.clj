(ns sixdim.gui.help_window
  (:use overtone.core)
  (:require 
    [membrane.ui :as ui]
    [sixdim.gui.core :as guicore])
  (:gen-class))

(defn help_window []
  (guicore/ll 
    (str "bar: asdas" 
                    "  q: fdsfsd" 
                    "  e: dfsd" 
  )))
