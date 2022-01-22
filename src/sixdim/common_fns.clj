(ns sixdim.common_fns
  (:gen-class))

(defn int_to_atom 
  [ns_name_prefix int_]
  (eval (symbol (str ns_name_prefix int_))))

; (common_fns/int_to_atom "atoms/score" 1)

(def int_to_score (partial int_to_atom "atoms/score"))
(def int_to_cc (partial int_to_atom "atoms/cc"))

; (reset! (common_fns/int_to_score 1) ["a" "b"])
; (pprint @(common_fns/int_to_score 1))
