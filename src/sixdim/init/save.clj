(ns sixdim.init.saves
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms] 
    [sixdim.save :as save]
    [sixdim.state_defs :as state_defs] 
    )
  (:gen-class))
; load-in custom scales 

; create minimum save dirs
(save/mkdir state_defs/save_root_dir)
(save/mkdir (save/default_save_path state_defs/save_root_dir))
(save/mkdir (str state_defs/save_root_dir "/1"))

; load default (latest) save
; (save/load_default_atoms @atoms/atoms_to_save state_defs/save_root_dir)

; save
; (save/save_atoms @atoms/atoms_to_save state_defs/save_root_dir)
