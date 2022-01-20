(ns sixdim.score.scales
  (:use overtone.core)
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


(defn add_scale_Amin7sixthdim [scales]
  (conj scales
    {:id "Ams"
     :id_long "A min7 sixth-dim" 
     :downbeats ["A" "C" "E" "G"]
     :upbeats ["B" "D" "F"]
     :scale_chromatics ["Ab"]
     :other_chromatics ["Db" "Eb" "Gb" "Bb"]}))


(defn in_scale_group [note_str scale scale_group]
  (let [true_nil 
          (some #(= note_str %) ((keyword scale_group) scale))]
    (if (= true_nil nil) false true)))

; (def tbar
  ; {"quarter" (reduce conj [] (repeat 4 (new_note "E4" "quarter")))
   ; "eight"   (reduce conj [] (repeat 4 (new_note "D4" "eight"  )))})

; (replace_score_note [tbar] 1 "eight" 4 (new_note "A1" "eight"))

; (gen_note_from_intervals_seconds [tbar tbar] 2 "eight" 4)
; (count (gen_note_from_intervals_seconds [tbar tbar] 1 "eight" 4))
; (first (gen_note_from_intervals_seconds [tbar tbar] 1 "eight" 4))

