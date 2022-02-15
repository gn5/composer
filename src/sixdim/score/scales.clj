(ns sixdim.score.scales
  (:use overtone.core)
  (:gen-class))

(def flat_to_case {
  "C" "C" "Cb" "B" "C#" "d" 
  "D" "D" "Db" "d" "D#" "e" 
  "E" "E" "Eb" "e" "E#" "F" 
  "F" "F" "Fb" "f" "F#" "g" 
  "G" "G" "Gb" "g" "G#" "a" 
  "A" "A" "Ab" "a" "A#" "b" 
  "B" "B" "Bb" "b" "B#" "C" })

(defn shift_note_nooctave [note direction n_semitones]
  "- increase or decrease note by n_semitones
   - direction must be '+' or '-' function
   - note must be in format with octave, e.g. A4 or Ab4
   - e.g. (shift_note \"A4\" + 1)"
  (as-> note v
    (note-info v) 
    (:pitch-class v) 
    (v NOTES) 
    (direction v n_semitones) 
    (mod v 12)
    (REVERSE-NOTES v)
    (name v)))

(def c_inc_to (partial shift_note_nooctave "C4" +))

(defn get_new_note_octave [note direction n_semitones]
  "- get new octave number of note after increase or decrease
       by n_semitones
   - direction must be '+' or '-' function
   - note must be in format with octave, e.g. A4 or Ab4
   - e.g. (get_new_note_octave \"A4\" + 1)"
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

(defn in_scale_group [note_str scale scale_group]
  (let [true_nil 
          (some #(= note_str %) ((keyword scale_group) scale))]
    (if (= true_nil nil) false true)))

(defn get_scale [scale_id scales_vec]
  (first (filter #(= scale_id (:id %)) scales_vec)))

; (defn up_scale_note [note_oct scale]

; (up_scale_note "C2" (scales/get_scale "CM5" @atoms/scales))

(defn ab_to_upbeats [scale_]
  (assoc scale_ :upbeats 
                  (vec (flatten (conj 
                        (:upbeats scale_) 
                        (:scale_chromatics scale_))))
                :scale_chromatics 
                  (vec (flatten (conj 
                        (:upbeats scale_) 
                        (:scale_chromatics scale_))))))

; (ab_to_upbeats (scales/get_scale "CM5" @atoms/scales))

(defn get_scale_id [note_oct_ scale_]
  (let [note_str (name (:pitch-class (note-info note_oct_)))]
    (as-> ["downbeats" "upbeats" "scale_chromatics" "other_chromatics"] v
      (filter #(in_scale_group note_str scale_ %) v) 
      (first v))))

; (get_scale_id "C2" (scales/get_scale "CM5" @atoms/scales))
; (get_scale_id "D2" (scales/get_scale "CM5" @atoms/scales))
; (get_scale_id "Ab2" (scales/get_scale "CM5" @atoms/scales))

(defn up_scale_notes [note_oct_ scale_ range_max_] 
  (let [scale_id (get_scale_id note_oct_ scale_)]
    (as-> (range range_max_) v
    (mapv #(shift_note note_oct_ + %) v) 
    (filterv #(in_scale_group 
               (name (:pitch-class (note-info %))) 
               (ab_to_upbeats scale_)
               scale_id) v))))

(defn up_scale_note_n [note_oct_ scale_ n]
  (-> (up_scale_notes note_oct_ scale_ 100) 
      (nth n)))

; (up_scale_note "Ab2" (scales/get_scale "CM5" @atoms/scales) 47)
; (up_scale_note_n "Ab2" (scales/get_scale "CM5" @atoms/scales) 2)
; (up_scale_note_n "Ab2" (scales/get_scale "CM5" @atoms/scales) 1)
; (up_scale_note_n "Ab2" (scales/get_scale "CM5" @atoms/scales) 0)

(defn down_scale_notes [note_oct_ scale_ range_max_] 
  (let [scale_id (get_scale_id note_oct_ scale_)]
    (as-> (range range_max_) v
    (mapv #(shift_note note_oct_ - %) v) 
    (filterv #(in_scale_group 
               (name (:pitch-class (note-info %))) 
               (ab_to_upbeats scale_)
               scale_id) v))))

; (down_scale_notes "Ab2" (scales/get_scale "CM5" @atoms/scales) 100)

(defn down_scale_note_n [note_oct_ scale_ n]
  (-> (down_scale_notes note_oct_ scale_ 100) 
      (nth n)))

; (down_scale_note_n "Ab2" (scales/get_scale "CM5" @atoms/scales) 0)
; (down_scale_note_n "Ab2" (scales/get_scale "CM5" @atoms/scales) 2)
