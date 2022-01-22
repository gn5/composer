(ns sixdim.print.core
  ; (:use overtone.core)
  ; (:require 
    ; [membrane.ui :as ui]
    ; [sixdim.state_defs :as state_defs])
  (:gen-class))

(defn str_fixed_length
"auto add right number of spaces at end of string 
 to get to fixed input length"
 [str_to_augment str_length]
 (let [n_spaces_to_add (- str_length (count str_to_augment))]
   (str str_to_augment 
        (reduce str (repeat n_spaces_to_add " ")))))

; (str_fixed_length "abc" 5)

(defn vec_str_to_fixed_length 
"paste all vector strings with fixed string length
 by adding spaces at end of strings if necessary"
 [vec_strs str_length]
 (reduce str 
         (map #(str_fixed_length % str_length) vec_strs)))

(defn vec_strings_newline
"take vector of strings and paste with \n line separators"
 [vec_strs]
  (reduce #(str %1 "\n" %2) vec_strs)) 
