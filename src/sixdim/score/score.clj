(ns sixdim.score.score
  (:use overtone.core)
  (:require [sixdim.time.loop :refer [bar_bpm]])
  (:gen-class))

(def default_note_volume 127)
(def default_midi_channel 0)
(defn calc_bpm_based_note_duration
  [bar_bpm mult_factor]
  (* 1000.0 ;1000 ms per second
     (/ 60 ;60 s per minute
        (* bar_bpm mult_factor)))) ; n durations per minute
(defn new_note 
  [pitch volume duration_ms]
  {"pitch" pitch
   "vol" volume
   "duration" duration_ms
   "scale_id" "A min7 sixth-dim"})
    ; {:class "sixth-dim" 
     ; :subclass "min7" 
     ; :root "A"

(def empty_note
  {"quarter" (new_note nil default_note_volume (calc_bpm_based_note_duration @bar_bpm 16))
  "eight" (new_note nil default_note_volume (calc_bpm_based_note_duration @bar_bpm 16))
  "triplet" (new_note nil default_note_volume (calc_bpm_based_note_duration @bar_bpm 32))
  "sixteen" (new_note nil default_note_volume (calc_bpm_based_note_duration @bar_bpm 32))})
;
(def empty_bar
  {"quarter" (reduce conj [] (repeat 4 (get empty_note "quarter")))
         "eight" (reduce conj [] (repeat 4 (get empty_note "eight")))
         "triplet" (reduce conj [] (repeat 8 (get empty_note "triplet")))
         "sixteen" (reduce conj [] (repeat 8 (get empty_note "sixteen")))})

(def n_bars (atom 4)) ; init number of bars in whole score
(def score (atom (into [] (repeat @n_bars empty_bar)))) ; init partition score

(defn add_bars_at_score_end [score bars] 
  "append bar (add at end of current score)"
  (as-> bars v
    (vector? v)
    (if v 
      (concat score v)
      (concat score [v]))))
  ; (if (vector? bars)
    ; (swap! score concat bars))
    ; (swap! score concat [bars])))

(defn add_bars_at_score_start [score bars] 
  "prepend bar (add at begining of current score)" 
  (as-> bars v
    (vector? v)
    (if v 
      (into v score)
      (into [v] score))))

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

(defn remove_bars_from_score [score index n_bars]
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
          
(defn replace_bars_in_score [score bars index]
  "-  replace (count bars) bars from score starting at index 
   - index (bar number) starts at 1, not 0
   - use with
     (swap! score replace_bars_in_score bars index)"
    (-> score
      (remove_bars_from_score index (count bars))
      (add_bars_to_score bars index)))
