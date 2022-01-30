(ns sixdim.gui.menus.switch
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms]
    [sixdim.gui.menus.logs :as menu_logs]
    )
  (:gen-class))

(defn to_menu [menu_str]
  (do
  (reset! atoms/menu menu_str)
  (reset! atoms/text_hoz3_1 (menu_logs/logs menu_str))
 ))
