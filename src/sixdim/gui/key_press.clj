(ns sixdim.gui.key_press
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms]
    [sixdim.score.swaps.core :as ss]
    [sixdim.gui.menus.base :as menu_base]
    [sixdim.gui.menus.gens :as menu_gens]
    [sixdim.gui.menus.filts :as menu_filts]
    )
  (:gen-class))

(def menu_map {
    "base"  menu_base/key_menu 
    "gens"  menu_gens/key_menu 
    "filts" menu_filts/key_menu 
      })

; redirection of current menu to key_map of that menu
(defn reset_atoms_log1 [str_]
  (reset! atoms/log1 str_))

(defn act_on_key [atoms_menu_str old_key new_key]
  (let [menu_map_ (menu_map atoms_menu_str)]
    (cond (contains? menu_map_ new_key)
          (let [key_map (menu_map_ new_key)] 
            (let [log1_str (:log1 key_map)
                  atoms_action_fn (:action key_map)]
              (do 
                (reset_atoms_log1 
                  (str "(" new_key ") " log1_str))
                (atoms_action_fn))))
          :else
          (reset_atoms_log1
            (str "(" new_key ")" 
                 " not in menu " atoms_menu_str)))))

(add-watch atoms/key_press :key_press_watcher
  (fn [key atom old-state new-state]
    (act_on_key @atoms/menu old-state new-state)))

