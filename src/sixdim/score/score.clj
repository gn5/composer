(ns sixdim.score.score
  (:use overtone.core)
  (:require [sixdim.state_defs :as state_defs]) 
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
    "scale_id" "Ams" ;"A min7 sixth-dim"
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

(def empty_note
  {"quarter" (new_note nil default_note_volume (calc_bpm_based_note_duration state_defs/bar_bpm 16))
  "eight" (new_note nil default_note_volume (calc_bpm_based_note_duration state_defs/bar_bpm 16))
  "triplet" (new_note nil default_note_volume (calc_bpm_based_note_duration state_defs/bar_bpm 32))
  "sixteen" (new_note nil default_note_volume (calc_bpm_based_note_duration state_defs/bar_bpm 32))})

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

