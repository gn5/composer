(ns sixdim.score.scales
  (:use overtone.core)
  (:require [sixdim.score.score :refer
              [default_note_volume    
               default_midi_channel
               calc_bpm_based_note_duration
               new_note 
               empty_note
               empty_bar
               n_bars
               score
               add_bars_at_score_end
               add_bars_at_score_start
               get_score_bar
               get_bars_1_to_n
               get_bars_n_to_end
               add_bars_at_score_index
               add_bars_to_score
               remove_score_bars
               replace_score_bars
               replace_score_note
               replace_bar_note]]
            [sixdim.score.score_nav :refer
             [get_score_beat 
              get_next_eight
              nav_eight]])
  (:gen-class))

(defn shift_note_nooctave [note direction n_semitones]
  "- increase or decrease note by n_semitones
   - direction must be '+' or '-' function
   - note must be in format with octave, e.g. A4 or Ab4
   - e.g. (shift_note \"A4\" + 1)
   "
  (as-> note v
    (note-info v) 
    (:pitch-class v) 
    (v NOTES) 
    (direction v n_semitones) 
    (mod v 12)
    (REVERSE-NOTES v)
    (name v)))

(defn get_new_note_octave [note direction n_semitones]
  "- get new octave number of note after increase or decrease
       by n_semitones
   - direction must be '+' or '-' function
   - note must be in format with octave, e.g. A4 or Ab4
   - e.g. (get_new_note_octave \"A4\" + 1)
   "
  (as-> note v
    (note-info v)
    (:pitch-class v) ; :A 
    (v NOTES) ; 9
    (direction v n_semitones) ; 12 
    (/ v 12) ; divided by 12 because 12 notes by octave
    (cond (>= v 0)  
          ; going to higher octave
          (+ (:octave (note-info note)) (int v))
          :else 
          ; or to lower octave
          (- (:octave (note-info note)) (int (+ 1 (* v -1))))))) 

(defn shift_note [note direction n_semitones]
  (str 
    (shift_note_nooctave note direction n_semitones)
    (str (get_new_note_octave note direction n_semitones))))

(def scales (atom []))

(defn add_scale_Amin7sixthdim [scales]
  (conj scales
    {:id "A min7 sixth-dim" 
     :downbeats ["A" "C" "E" "G"]
     :upbeats ["B" "D" "F"]
     :scale_chromatics ["a"]
     :other_chromatics ["d" "e" "g" "b"]}))

(swap! scales add_scale_Amin7sixthdim)

; (def gen_maps (
  ; "ordered vector of beats to fill/create
   ; using generator and filter functions"
  ; atom [
  ; {:bar 2 :k "quarter" :n 1 :g gen_sc :f filt_sc_db}
  ; {:bar 2 :k "eight" :n 1 :g gen_sc :f filt_sc_ub}
  ; {:bar 2 :k "quarter" :n 2 :g gen_sc :f filt_sc_db}
  ; {:bar 2 :k "eight" :n 2 :g gen_sc :f filt_sc_ub}
  ; {:bar 2 :k "quarter" :n 3 :g gen_sc :f filt_sc_db}
  ; {:bar 2 :k "eight" :n 3 :g gen_sc :f filt_sc_ub}
  ; {:bar 2 :k "quarter" :n 4 :g gen_sc :f filt_sc_db}
  ; {:bar 2 :k "eight" :n 4 :g gen_sc :f filt_sc_ub}]))
; (pprint @gen_maps)
(def tbar
  {"quarter" (reduce conj [] (repeat 4 (new_note "A4" "quarter")))
   "eight"   (reduce conj [] (repeat 4 (new_note "A3" "eight"  )))})

(def seconds [{:sign + :n 1} {:sign + :n 2}
              {:sign - :n 1} {:sign - :n 2}])

(defn gen_note_from_intervals
  ([score bar_n beat_key beat_n intervals_map]
  "- generate possible scalar note: semitone or tone up or down
   - from 1 score, generate vector of n (length intervals) scores
     where each score has new note at beat_n
     - new note is previous eigth note +- n semitones"
  (cond ; check if unique score or vec of scores 
    (contains? (first score) "quarter"); check if unique score
      (let [previous_eigth
          ((nav_eight score bar_n beat_key beat_n - 1) "pitch")]
    (reduce into []
            (map #(replace_score_note score bar_n beat_key beat_n; replace current note
                    (new_note (shift_note previous_eigth ; with previous note
                                (:sign %)      ;  + or -
                                (:n %)) "eight"))        ;    n semitones
              intervals_map)))
     :else ; or if vector of scores
     (reduce into [] (map #(gen_note_from_intervals % bar_n beat_key beat_n intervals_map) score))))
  ([inputs to_pass_on] [(gen_note_from_intervals 
                          (nth inputs 0)
                          (nth inputs 1)
                          (nth inputs 2)
                          (nth inputs 3)
                          (nth inputs 4)
                          ) to_pass_on]))

(count (gen_note_from_intervals [tbar] 1 "eight" 4 seconds))
(count (gen_note_from_intervals [[tbar] 1 "eight" 4 seconds] "a"))
(first (gen_note_from_intervals [[tbar] 1 "eight" 4 seconds] "a"))
; (pprint tbar)
; (nav_eight [tbar] 1 "quarter" 2 - 1)
; ((nav_eight [tbar] 1 "quarter" 2 - 1) "pitch")
; (tgen_note_from_intervals [tbar] 1 "eight" 4 seconds)
; (count (tgen_note_from_intervals [tbar] 1 "eight" 4 seconds))

; (tgen_note_from_intervals [[tbar][tbar]] 1 "eight" 4 seconds)
; (count (tgen_note_from_intervals [[tbar]] 1 "eight" 4 seconds))
; (first (tgen_note_from_intervals [[tbar][tbar]] 1 "eight" 4 seconds))
; (pprint (tgen_note_from_intervals [tbar] 1 "eight" 4 seconds))
; (count (gen_note_from_intervals [tbar] 1 "eight" 4 seconds))


; (defn gen_sc [score bar beat_key beat_n]
  ; "- generate notes for scalar movements
     ; i.e. tone or semitone up or down
   ; - get previous eigth note (quarter note down or up)
     ; add in semitone/tone up/down
   ; "
   ; (nth                    ; in score   
     ; ((nth score (- bar 1));  get bar number (e.g. 1 for 1st bar)
       ; beat_key)           ;    get beat key (e.g. "quarter") 
     ; (- beat_n 1))         ;      get beat number (e.g. 1)
  ; )

; (defn filt_sc_db [vec_to_filt score bar beat_key beat_n]
  ; "filter generated notes for scalar movements
   ; i.e. in scale tone or semitone up or down
   ; for 'db' quarter downbeat, note must be in 'downbeats' group"
  ; )

; (defn filt_sc_up [vec_to_filt score bar beat_key beat_n]
  ; "filter generated notes for scalar movements
   ; i.e. in scale tone or semitone up or down
   ; for 'ub' quarter upbeat, note must be in 'upbeats' group"
  ; )

; (defn per_beat_gen_melody [gen_map score];
  ; (as-> gen_map v
    ; ((get v :g)     ;apply generator function
     ; score
     ; (get v :bar)   ;on bar
     ; (get v :k)     ;   beat key 
     ; (get v :n))    ;   beat key number
    ; ((get gen_map :f)     ;apply filter function
     ; v                    ;on list of generated notes
     ; score
     ; (get gen_map :bar)   ;on bar
     ; (get gen_map :k)     ;   beat key 
     ; (get gen_map :n))    ;   beat key number
  ; ))

; (defn gen_melody [score gen_maps]
  ; "-use with
   ; (swap! score gen_melody a_prepared_gen_map) 
  ; "
  ; (map per_beat_gen_melody
       ; (reduce conj [] (repeat (count gen_maps) score))
       ; gen_maps))

