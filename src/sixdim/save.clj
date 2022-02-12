(ns sixdim.save
  (:use overtone.core)
  (:require 
    [sixdim.atoms :as atoms]
    [clojure.java.io :as io]
    )
  (:gen-class))

(defn mkdir [path_]
  (let [io_file_path_ (io/file path_)]
    (if (not (.exists io_file_path_))
      (.mkdir io_file_path_)
      (str path_ " already exists"))))
; (mkdir "./saves/111") 

(defn last_of_path [path_]
  (last (clojure.string/split path_ #"/")))

(defn filt_out_default [paths_]
  (vec (filter #(not (= "default" %)) paths_)))

(defn list_saves [root_dir_]
  (mapv #(clojure.string/replace 
          (str %)
          #"\\" 
          "/"
          ) (.listFiles (io/file root_dir_))))

(defn default_save_path [root_dir_]
  (str root_dir_ "default"))

(defn last_save_dir [paths_]
  (as-> paths_ v 
    (mapv #(last_of_path %) v)
    (filt_out_default v)
    (mapv #(Integer/parseInt  %) v)
    (sort v)
    (last v)
    (str v)))

(defn next_save_dir [paths_]
  (as-> paths_ v 
    (mapv #(last_of_path %) v)
    (filt_out_default v)
    (mapv #(Integer/parseInt  %) v)
    (sort v)
    (last v)
    (inc v)
    (str v)))

(defn last_save_path [root_dir_ paths_]
  (as-> paths_ v
    (last_save_dir paths_) 
    (str root_dir_ v)))

(defn next_save_path [root_dir_ paths_]
  (as-> paths_ v
    (next_save_dir paths_) 
    (str root_dir_ v)))

(defn save_atom [atom_name_ atom_ save_path_dir_] 
  (let [save_path_ (str save_path_dir_ "/" atom_name_ ".edn")] 
    (spit save_path_ (pr-str (deref atom_)))
    save_path_))

(defn load_atom [atom_name_ atom_ save_path_dir_] 
  (let [save_path_ (str save_path_dir_ "/" atom_name_ ".edn")] 
    (reset! atom_ (read-string (slurp save_path_))) 
    save_path_))

(defn save_atoms [atoms_vec_ root_dir_]
  (let [default_save_path_ (default_save_path root_dir_) 
        last_save_path_ (last_save_path root_dir_ (list_saves root_dir_)) 
        next_save_path_ (next_save_path root_dir_ (list_saves root_dir_))]
    (mkdir next_save_path_)
    (mapv 
      (fn [name_atom]
        (save_atom (first name_atom) (last name_atom) next_save_path_))
      atoms_vec_)
    (mapv 
      (fn [name_atom]
        (save_atom (first name_atom) (last name_atom) default_save_path_))
      atoms_vec_)
    ))

(defn load_default_atoms [atoms_vec_ root_dir_]
  (let [default_save_path_ (default_save_path root_dir_)]
    (mapv 
      (fn [name_atom]
        (load_atom (first name_atom) (last name_atom) default_save_path_))
      atoms_vec_)))

; (filt_out_default ["1" "2" "default"])
; (last_of_path "saves/ar")
; (list_saves "saves/")
; (last_save_dir (list_saves "saves/"))
; (default_save_path "saves/") 
; (last_save_path "saves/" (list_saves "saves/")) 
; (next_save_path "saves/" (list_saves "saves/")) 
; (mkdir (next_save_path "saves/" (list_saves "saves/"))) 
; (last_save_path "saves/" (list_saves "saves/")) 
; (save_atom "score1" atoms/score1 "saves/5")

; (save/load_default_atoms @atoms/atoms_to_save state_defs/save_root_dir)




