(ns sixdim.score.melody
  (:use overtone.core)
  (:gen-class))

(defn apply_generator_on_score_filters 
  "run generator on one score and re-attach :filters 
   to all generated scores"
  [score_filters bar_n beat_key beat_n generator]
  (map 
    #(hash-map :score % 
               :filters (:filters score_filters)
               :scales (:scales score_filters))
    (generator (:score score_filters) bar_n beat_key beat_n)
  ))

; (count (melody/apply_generator_on_score_filters 
  ; {:score @atoms/score1 :filters [] :scales @atoms/scales} 
  ; 2 "quarter" 1
  ; mgens/gen_note_from_intervals_seconds_down))

; (:scales (nth (melody/apply_generator_on_score_filters
  ; {:score @atoms/score1 :filters [] :scales @atoms/scales}
  ; 2 "quarter" 1
  ; mgens/gen_note_from_intervals_seconds_down) 0))

(defn apply_generator_on_scores_filters
  [vec_of_scores_filters bar_n beat_key beat_n generator]
  (reduce into [] ; flatten vec of vec 
    (map ; apply the score generator on each score
      #(apply_generator_on_score_filters 
          % bar_n beat_key beat_n generator)
      vec_of_scores_filters)))

; (apply_generator_on_scores_filters tinput 1 "eight" 4 gen_note_from_intervals_seconds)

(defn add_filter_to_score_filters
  [score_filters bar_n beat_key beat_n filter_]
  (update score_filters ; update hash-map
          :filters ; at key
          #(conj %1 {:bar bar_n :k beat_key :n beat_n :f filter_})))

; (add_filter_to_score_filters (nth tinput 0)
                             ; 1 "eight" 4 add_bars_to_score)

(defn add_filter_to_scores_filters
  [vec_of_scores_filters bar_n beat_key beat_n filter_]
  (map
    #(add_filter_to_score_filters % bar_n beat_key beat_n filter_)  
    vec_of_scores_filters))

; (add_filter_to_scores_filters tinput
                             ; 1 "eight" 4 filter_accept_all)

(defn filters_reducer [acc current_filter]
  (cond (= [] (:score acc)) ; if returns empty score
    (reduced {})       
    :else
    (let [score (:score acc)
          scales (:scales acc)
          bar_n (:bar current_filter)
          beat_key (:k current_filter)
          beat_n (:n current_filter)
          filter_func (:f current_filter)]
      (let [res_func (filter_func score bar_n beat_key beat_n scales)]
        (cond
          (= res_func "passed") 
            acc
          (= res_func "not_passed") 
            (update acc :score (fn [a] []))
          (= res_func "to_rerun_next_beats") 
            (update acc :filters conj current_filter)
          :else 
            (update acc :score (fn [a] []))
            ; (update acc :filters conj "filters_reducer error")
          )))))

(defn run_filters_on_score_filters
  [score_filters]
  (reduce filters_reducer
          {:score (:score score_filters) 
           :filters []
           :scales (:scales score_filters)}
          (:filters score_filters)))

; (run_filters_on_score_filters (nth tinput 0))

(defn run_filters_on_scores_filters
  [scores_filters]
  (map run_filters_on_score_filters scores_filters))

(defn gen_and_filters [acc_scores_filters gen_beat]
  (let [bar_n (:bar gen_beat)
        beat_key (:k gen_beat)
        beat_n (:n gen_beat)
        beat_gen (:g gen_beat)
        beat_filt (:f gen_beat)] 
    (as-> acc_scores_filters v
      (apply_generator_on_scores_filters v bar_n beat_key beat_n beat_gen)
      (add_filter_to_scores_filters v bar_n beat_key beat_n beat_filt)
      (run_filters_on_scores_filters v)
      (filter #(not= {} %) v)
      (filter #(not= [] (:score %)) v)
      )))
   

; (in_scale_group "A" (first @scales) "downbeats")
; (in_scale_group "A" (first @scales) "upbeats")

(defn gen_melody [score gen_maps scales]
  ""
  ; (flip all gen_maps beats meta to edit)
  (reduce gen_and_filters
          ; init list of objects {:score ... :filters [... ...]}
          [{:score score :filters [] :scales scales}]
          gen_maps))

; (print_score/print_bar_notes @atoms/score1 1 5)
; (pprint @atoms/gen_maps)
; @atoms/scales
; (melody/gen_melody @atoms/score1 @atoms/gen_maps @atoms/scales)
