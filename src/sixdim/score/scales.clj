(ns sixdim.score.scales
  (:use overtone.core)
  ; (:require [sixdim.time.loop :refer [bar_bpm]])
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

(defn gen_sc [score bar beat_key beat_n]
  "- generate notes for scalar movements
     i.e. tone or semitone up or down
   - get previous eigth note (quarter note down or up)
     add in semitone/tone up/down
   "
   (nth                    ; in score   
     ((nth score (- bar 1));  get bar number (e.g. 1 for 1st bar)
       beat_key)           ;    get beat key (e.g. "quarter") 
     (- beat_n 1))         ;      get beat number (e.g. 1)
  )

(defn filt_sc_db [vec_to_filt score bar beat_key beat_n]
  "filter generated notes for scalar movements
   i.e. in scale tone or semitone up or down
   for 'db' quarter downbeat, note must be in 'downbeats' group"
  )

(defn filt_sc_up [vec_to_filt score bar beat_key beat_n]
  "filter generated notes for scalar movements
   i.e. in scale tone or semitone up or down
   for 'ub' quarter upbeat, note must be in 'upbeats' group"
  )

(defn per_beat_gen_melody [gen_map score];
  (as-> gen_map v
    ((get v :g)     ;apply generator function
     score
     (get v :bar)   ;on bar
     (get v :k)     ;   beat key 
     (get v :n))    ;   beat key number
    ((get gen_map :f)     ;apply filter function
     v                    ;on list of generated notes
     score
     (get gen_map :bar)   ;on bar
     (get gen_map :k)     ;   beat key 
     (get gen_map :n))    ;   beat key number
  ))

(defn gen_melody [score gen_maps]
  "-use with
   (swap! score gen_melody a_prepared_gen_map) 
  "
  (map per_beat_gen_melody
       (reduce conj [] (repeat (count gen_maps) score))
       gen_maps))

