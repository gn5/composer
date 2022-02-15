(ns sixdim.score.score
  (:use overtone.core)
  (:require 
    [sixdim.state_defs :as state_defs] 
    [sixdim.common_fns :as common_fns]
    )
  (:gen-class))

(defn calc_bpm_based_note_duration
  [bar_bpm mult_factor]
  (* 1000.0 ;1000 ms per second
     (/ 60 ;60 s per minute
        (* bar_bpm mult_factor)))) ; n durations per minute

(defn new_note 
  "- pitch: string in format A4 (or nil if no note)
   - volume: int in (midi) range 1-127
   - generate: string locked or edit"
  ([pitch volume duration_ms]
    {"pitch" pitch
    "vol" volume
    "duration" duration_ms
    "play" true ;true or false
    "delay" (calc_bpm_based_note_duration state_defs/bar_bpm 16)
    "scale_id" "CM6" ;"A min7 sixth-dim"
    "generate" "locked"})
  ([pitch beat_key]
    (new_note 
      pitch 
      state_defs/default_note_volume
      (cond (= beat_key "quarter") 
            (calc_bpm_based_note_duration state_defs/bar_bpm 16)
            (= beat_key "eight") 
            (calc_bpm_based_note_duration state_defs/bar_bpm 16)
            (= beat_key "triplet") 
            (calc_bpm_based_note_duration state_defs/bar_bpm 32)
            (= beat_key "sixteen") 
            (calc_bpm_based_note_duration state_defs/bar_bpm 32))))
  ([pitch]
    (new_note pitch "quarter")))

; (new_note "A4")
; (new_note "B4" "sixteen")

(defn replace_note_scale [current_note scale_id]
  (assoc current_note "scale_id" scale_id))

(defn replace_note_delay [current_note duration_ms]
  (assoc current_note "delay" duration_ms))

(defn replace_note_play [current_note true_false]
  (assoc current_note "play" true_false))

(defn replace_note_vol [current_note vol]
  (assoc current_note "vol" vol))

(defn replace_note_duration [current_note duration]
  (assoc current_note "duration" duration))

(defn replace_note_generate [current_note generate]
  (assoc current_note "generate" generate))

(def empty_note
  {"quarter" (new_note nil state_defs/default_note_volume (calc_bpm_based_note_duration state_defs/bar_bpm 16))
  "eight" (new_note nil state_defs/default_note_volume (calc_bpm_based_note_duration state_defs/bar_bpm 16))
  "triplet" (new_note nil state_defs/default_note_volume (calc_bpm_based_note_duration state_defs/bar_bpm 32))
  "sixteen" (new_note nil state_defs/default_note_volume (calc_bpm_based_note_duration state_defs/bar_bpm 32))})

(def empty_bar
  {"quarter" (reduce conj [] (repeat 4 (get empty_note "quarter")))
         "eight" (reduce conj [] (repeat 4 (get empty_note "eight")))
         "triplet" (reduce conj [] (repeat 8 (get empty_note "triplet")))
         "sixteen" (reduce conj [] (repeat 8 (get empty_note "sixteen")))})

(def init_bar
  {"quarter" (reduce conj [] (repeat 4 (new_note "A4" "quarter")))
         "eight" (reduce conj [] (repeat 4 (new_note "B4" "eight")))
         "triplet" (reduce conj [] (repeat 8 (new_note "C4" "triplet")))
         "sixteen" (reduce conj [] (repeat 8 (new_note "D4" "sixteen")))})

(defn add_bars_at_score_end [score bars] 
  "append bar (add at end of current score)"
  (if (vector? bars)
    (into [] (concat score bars))
    (into [] (concat score [bars]))))
; (add_bars_at_score_end ["1" "2" "2"] ["3"])

(defn add_bars_at_score_start [score bars] 
  "prepend bar (add at begining of current score)" 
  (if (vector? bars)
      (into bars score)
      (into [bars] score)))

(defn get_score_bar [score bar_n]
  (nth score (- bar_n 1)))

(defn get_bars_1_to_n [score index] ;index not included
  (into [] (take (dec index) score)))

(defn get_bars_n_to_end [score index] ;including index
  (as-> score v
    (count v)
    (- v index)
    (+ v 1)
    (take-last v score)
    (into [] v)))
; (take-last (+ 1 (- (count current_score) index)) current_score)]

(defn add_bars_at_score_index 
  [score bars index] 
  (let [first_bars (get_bars_1_to_n score index) ; index not included
        last_bars (get_bars_n_to_end score index)]
    (if (vector? bars)
      (reduce into [first_bars bars last_bars])
      (reduce into [first_bars [bars] last_bars]))))

(defn add_bars_to_score [score bars index] 
  "index (bar number) starts at 1, not 0
   use with
     (swap! score add_bars_to_score bars index)"
  (if (<= index 1)
    (add_bars_at_score_start score bars)
    (if (> index (count score))
      (add_bars_at_score_end score bars)
      (add_bars_at_score_index score bars index))))

; (add_bars_to_score ["1" "2" "2"] ["3"] 1)
; (add_bars_to_score ["1" "2" "2"] ["3"] 2)
; (add_bars_to_score ["1" "2" "2"] ["3"] 3)
; (add_bars_to_score ["1" "2" "2"] ["3"] 4)
; (add_bars_to_score [3 3 3] (remove_score_bars [[1 1 1] [2 2 2]] 2 1) 2)

(defn remove_score_bars [score index n_bars]
  "- remove n_bars from score starting at index
   - index (bar number) starts at 1, not 0
   - use with
     (swap! score remove_bars_from_score index n_bars)"
  (if (> (- (+ index n_bars) 1) (count score))
    (let [pre_bars (subvec score 0 (- index 1))
          post_bars (subvec score (count score))]
      (into [] (concat pre_bars post_bars)))
    (let [pre_bars (subvec score 0 (- index 1))
          post_bars (subvec score (- (+ index n_bars) 1))]
      (into [] (concat pre_bars post_bars)))))

; (remove_score_bars [[1 1 1] [2 2 2]] 2 1)
; (add_bars_to_score [3 3 3] (remove_score_bars [[1 1 1] [2 2 2]] 2 1) 1)

(defn replace_bar_note 
  [bar beat_key beat_n new_note]
  (update bar ; update bar
          beat_key ; at key beat_key (e.g. "quarter")
          ; function that take beat_key vector as first arg
          ;   and return new beat_key vector replacecement
          ;     using (assoc vec index replacement)
          #(assoc %1 (- %2 1) %3) 
          beat_n      ;%2 (%1 is the vector at beat_key)
          new_note))  ;%3

; (replace_bar_note empty_bar "eight" 4 (new_note "A4"))

(defn replace_score_bars [score bars index]
  "- replace (count bars) bars from score starting at index 
   - index (bar number) starts at 1, not 0
   - use with
     (swap! score replace_bars_in_score bars index)"
    (-> score
      (remove_score_bars index (if (vector? bars) 
                                   (count bars)
                                   1))
      (add_bars_to_score bars index)))

; (pprint (replace_score_bars [empty_bar empty_bar] 
  ; {"quarter" (reduce conj [] (repeat 4 (new_note "C8" "quarter")))
         ; "eight" (reduce conj [] (repeat 4 (new_note "C4" "eight")))
         ; "triplet" (reduce conj [] (repeat 8 (new_note "C3" "triplet")))
         ; "sixteen" (reduce conj [] (repeat 8 (new_note "C2" "sixteen")))}
 ; 2))

(defn replace_score_note 
  [score bar_n beat_key beat_n new_note]
  (as-> score v
  (get_score_bar v bar_n)
  (replace_bar_note v beat_key beat_n new_note)
  (replace_score_bars score v bar_n)))

; (replace_score_note [empty_bar empty_bar] 2 "sixteen" 8 (new_note "C2" "sixteen"))
; (replace_bar_note empty_bar "sixteen" 8 (new_note "C2" "sixteen"))
(def all_bar_bars
  [{:k "quarter" :n 1}
   {:k "quarter" :n 2}
   {:k "quarter" :n 3}
   {:k "quarter" :n 4}])

(def all_bar_eights
  [{:k "quarter" :n 1} {:k "eight" :n 1}
   {:k "quarter" :n 2} {:k "eight" :n 2}
   {:k "quarter" :n 3} {:k "eight" :n 3}
   {:k "quarter" :n 4} {:k "eight" :n 4}])

(def all_bar_triplets
  [{:k "quarter" :n 1} {:k "triplet" :n 1} {:k "triplet" :n 2}
   {:k "quarter" :n 2} {:k "triplet" :n 3} {:k "triplet" :n 4}
   {:k "quarter" :n 3} {:k "triplet" :n 5} {:k "triplet" :n 6}
   {:k "quarter" :n 4} {:k "triplet" :n 7} {:k "triplet" :n 8}])

(def all_bar_sixteens
  [{:k "quarter" :n 1} {:k "sixteen" :n 1} 
   {:k "eight"   :n 1} {:k "sixteen" :n 2}
   {:k "quarter" :n 2} {:k "sixteen" :n 3}
   {:k "eight"   :n 2} {:k "sixteen" :n 4}
   {:k "quarter" :n 3} {:k "sixteen" :n 5}
   {:k "eight"   :n 3} {:k "sixteen" :n 6}
   {:k "quarter" :n 4} {:k "sixteen" :n 7}
   {:k "eight"   :n 4} {:k "sixteen" :n 8}])

(def mapping_selection_start_bar_eights {
  "1" [{:k "quarter" :n 1} {:k "eight" :n 1}
       {:k "quarter" :n 2} {:k "eight" :n 2}
       {:k "quarter" :n 3} {:k "eight" :n 3}
       {:k "quarter" :n 4} {:k "eight" :n 4}]
  "2" [                    {:k "eight" :n 1}
       {:k "quarter" :n 2} {:k "eight" :n 2}
       {:k "quarter" :n 3} {:k "eight" :n 3}
       {:k "quarter" :n 4} {:k "eight" :n 4}]
  "3" [{:k "quarter" :n 2} {:k "eight" :n 2}
       {:k "quarter" :n 3} {:k "eight" :n 3}
       {:k "quarter" :n 4} {:k "eight" :n 4}]
  "4" [{:k "eight" :n 2}
       {:k "quarter" :n 3} {:k "eight" :n 3}
       {:k "quarter" :n 4} {:k "eight" :n 4}]
  "5" [{:k "quarter" :n 3} {:k "eight" :n 3}
       {:k "quarter" :n 4} {:k "eight" :n 4}]
  "6" [{:k "eight" :n 3}
       {:k "quarter" :n 4} {:k "eight" :n 4}]
  "7" [{:k "quarter" :n 4} {:k "eight" :n 4}]
  "8" [{:k "eight" :n 4}]})

(def mapping_selection_start_bar_triplets {
  "1" all_bar_triplets
  "2"  (vec (take-last 11 all_bar_triplets))
  "3"  (vec (take-last 10 all_bar_triplets))
  "4"  (vec (take-last 9 all_bar_triplets))
  "5"  (vec (take-last 8 all_bar_triplets))
  "6"  (vec (take-last 7 all_bar_triplets))
  "7"  (vec (take-last 6 all_bar_triplets))
  "8"  (vec (take-last 5 all_bar_triplets))
  "9"  (vec (take-last 4 all_bar_triplets))
  "10" (vec (take-last 3 all_bar_triplets))
  "11" (vec (take-last 2 all_bar_triplets))
  "12" (vec (take-last 1 all_bar_triplets))})

(def mapping_selection_start_bar_sixteens {
  "1" all_bar_sixteens
  "2"  (vec (take-last 15 all_bar_sixteens))
  "3"  (vec (take-last 14 all_bar_sixteens))
  "4"  (vec (take-last 13 all_bar_sixteens))
  "5"  (vec (take-last 12 all_bar_sixteens))
  "6"  (vec (take-last 11 all_bar_sixteens))
  "7"  (vec (take-last 10 all_bar_sixteens))
  "8"  (vec (take-last 9  all_bar_sixteens))
  "9"  (vec (take-last 8  all_bar_sixteens))
  "10" (vec (take-last 7  all_bar_sixteens))
  "11" (vec (take-last 6  all_bar_sixteens))
  "12" (vec (take-last 5  all_bar_sixteens))
  "13" (vec (take-last 4  all_bar_sixteens))
  "14" (vec (take-last 3  all_bar_sixteens))
  "15" (vec (take-last 2  all_bar_sixteens))
  "16" (vec (take-last 1  all_bar_sixteens))})

(def mapping_selection_end_bar_eights {
  "1" [{:k "quarter" :n 1}]
  "2" [{:k "quarter" :n 1} {:k "eight" :n 1}]
  "3" [{:k "quarter" :n 1} {:k "eight" :n 1}
       {:k "quarter" :n 3}]
  "4" [{:k "quarter" :n 1} {:k "eight" :n 1}
       {:k "quarter" :n 2} {:k "eight" :n 2}]
  "5" [{:k "quarter" :n 1} {:k "eight" :n 1}
       {:k "quarter" :n 2} {:k "eight" :n 2}
       {:k "quarter" :n 3}]
  "6" [{:k "quarter" :n 1} {:k "eight" :n 1}
       {:k "quarter" :n 2} {:k "eight" :n 2}
       {:k "quarter" :n 3} {:k "eight" :n 3}]
  "7" [{:k "quarter" :n 1} {:k "eight" :n 1}
       {:k "quarter" :n 2} {:k "eight" :n 2}
       {:k "quarter" :n 3} {:k "eight" :n 3}
       {:k "quarter" :n 4}]
  "8" [{:k "quarter" :n 1} {:k "eight" :n 1}
       {:k "quarter" :n 2} {:k "eight" :n 2}
       {:k "quarter" :n 3} {:k "eight" :n 3}
       {:k "quarter" :n 4} {:k "eight" :n 4}]})

(def mapping_selection_end_bar_triplets {
  "1"  (vec (take 1 all_bar_triplets))
  "2"  (vec (take 2 all_bar_triplets))
  "3"  (vec (take 3 all_bar_triplets))
  "4"  (vec (take 4 all_bar_triplets))
  "5"  (vec (take 5 all_bar_triplets))
  "6"  (vec (take 6 all_bar_triplets))
  "7"  (vec (take 7 all_bar_triplets))
  "8"  (vec (take 8 all_bar_triplets))
  "9"  (vec (take 9 all_bar_triplets))
  "10" (vec (take 10 all_bar_triplets))
  "11" (vec (take 11 all_bar_triplets))
  "12" (vec (take 12 all_bar_triplets))})

(def mapping_selection_end_bar_sixteens {
  "1"  (vec (take 1  all_bar_sixteens))
  "2"  (vec (take 2  all_bar_sixteens))
  "3"  (vec (take 3  all_bar_sixteens))
  "4"  (vec (take 4  all_bar_sixteens))
  "5"  (vec (take 5  all_bar_sixteens))
  "6"  (vec (take 6  all_bar_sixteens))
  "7"  (vec (take 7  all_bar_sixteens))
  "8"  (vec (take 8  all_bar_sixteens))
  "9"  (vec (take 9  all_bar_sixteens))
  "10" (vec (take 10 all_bar_sixteens))
  "11" (vec (take 11 all_bar_sixteens))
  "12" (vec (take 12 all_bar_sixteens))
  "13" (vec (take 13 all_bar_sixteens))
  "14" (vec (take 14 all_bar_sixteens))
  "15" (vec (take 15 all_bar_sixteens))
  "16" (vec (take 16 all_bar_sixteens))})


