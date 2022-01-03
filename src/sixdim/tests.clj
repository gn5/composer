; (load-file "src/sixdim/tests.clj")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(pprint (vector? @score))

(pprint "count before (swap! score add_bars_to_score ")
(pprint (count @score))
(swap! score add_bars_to_score empty_bar (count @score))
(pprint "count after (swap! score add_bars_to_score ")
(pprint (count @score))

(pprint (vector? @score))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(pprint "count before (swap! score remove_bars_from_score ")
(pprint (count @score))
; (swap! score remove_bars_from_score index n_bars)
(swap! score remove_bars_from_score 5 1)
(pprint "count after (swap! score remove_bars_from_score ")
(pprint (count @score))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

score 
[{:quarter [1 2 3 4] :eight [1 2 3 4]}
 {:quarter [_ _ _ _] :eight [_ _ _ _]}
 {:quarter [1 2 3 4] :eight [1 2 3 4]}]

gen_maps [
  {:bar 1 :k "quarter" :n 1 :g gen_sc :f filt_sc_db}
  {:bar 1 :k "eight" :n 1 :g gen_sc :f filt_sc_ub}
  {:bar 1 :k "quarter" :n 2 :g gen_sc :f filt_sc_db}
  {:bar 1 :k "eight" :n 2 :g gen_sc :f filt_sc_ub}
  {:bar 1 :k "quarter" :n 3 :g gen_sc :f filt_sc_db}
  {:bar 1 :k "eight" :n 3 :g gen_sc :f filt_sc_ub}
  {:bar 1 :k "quarter" :n 4 :g gen_sc :f filt_sc_db}
  {:bar 1 :k "eight" :n 4 :g gen_sc :f filt_sc_ub}]

