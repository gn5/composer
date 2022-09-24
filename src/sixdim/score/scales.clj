(ns sixdim.score.scales
  (:use overtone.core)
    (:require
     [clojure.string :as clj_string])
  (:gen-class))

(def flat_to_case {"C" "C" "Cb" "B" "C#" "d"
                   "D" "D" "Db" "d" "D#" "e"
                   "E" "E" "Eb" "e" "E#" "F"
                   "F" "F" "Fb" "f" "F#" "g"
                   "G" "G" "Gb" "g" "G#" "a"
                   "A" "A" "Ab" "a" "A#" "b"
                   "B" "B" "Bb" "b" "B#" "C"})

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

; (up_scale_note "Ab2" (get_scale "CM5" @atoms/scales) 47)
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

(def test_scale
  {:id "C13"
   :id_long "1st 3-notes mode in C major"
   :downbeats ["C" "E" "G"]
   :upbeats ["D" "F" "A" "B"]
   :scale_chromatics []
   :other_chromatics ["C#" "Eb" "F#" "Ab" "Bb"]})

; (println test_scale)

(defn abs_num [n] (max n (- n)))

(defn abs_diff_2_notes [note1 note2]
  (abs_num (-
            (+ (:interval (note-info note2)) (* 12 (:octave (note-info note2))))
            (+ (:interval (note-info note1)) (* 12 (:octave (note-info note1)))))))

(defn get_next_higher_inversion
  [n_inversion list_notes_no_octave base_octave]
  (concat
   (map #(str  % (+ base_octave 0)) (subvec list_notes_no_octave n_inversion))
   (map #(str  % (+ base_octave 1)) (take n_inversion list_notes_no_octave))))

;(get_next_higher_inversion 0 ["C" "E" "G" "B"] 3)
;(get_next_higher_inversion 1 ["C" "E" "G" "B"] 3)
;(get_next_higher_inversion 2 ["C" "E" "G" "B"] 3)
;(get_next_higher_inversion 3 ["C" "E" "G" "B"] 3)

(defn count_intervals_higher_than_n_semitones
  [n_semitones list_notes]
  (sum (map
        (fn [two_notes]
          (if (> (abs_diff_2_notes (nth two_notes 0) (nth two_notes 1))
                 n_semitones)
            1 0))
        (concat
         (partition 2 (vec list_notes))
         (partition 2 (subvec (vec list_notes) 1))))))

;(count_intervals_higher_than_n_semitones
 ;2 (get_next_higher_inversion 0 ["C" "E" "G" "B"] 3))
;(count_intervals_higher_than_n_semitones
 ;2 (get_next_higher_inversion 1 ["C" "E" "G" "B"] 3))
;(count_intervals_higher_than_n_semitones
 ;2 (get_next_higher_inversion 2 ["C" "E" "G" "B"] 3))
;(count_intervals_higher_than_n_semitones
 ;2 (get_next_higher_inversion 3 ["C" "E" "G" "B"] 3))

(defn make_inversions_map [list_notes_no_octave base_octave]
  (let [n_notes (count list_notes_no_octave)]
    (vec (map #(hash-map
                :first_note (nth (get_next_higher_inversion % list_notes_no_octave base_octave) 0)
                :inversion (get_next_higher_inversion % list_notes_no_octave base_octave)
                :n_higher (count_intervals_higher_than_n_semitones
                           2 (get_next_higher_inversion % list_notes_no_octave base_octave)))
              (range n_notes)))))

;(make_inversions_map  ["C" "E" "G" "B"] 3)
;(count (:inversion (nth (make_inversions_map  ["C" "E" "G" "B"] 3) 0)))

;(take 25 (cycle (make_inversions_map  ["C" "D" "F" "A"] 3)))

;(take 4 (:inversions (reduce #(if (:found_highest %1)
;           (assoc %1 :inversions (concat (:inversions %1) (list %2)))
;           (if (< (:n_higher %2) 3) 
;             %1 
;             (assoc %1
;                    :found_highest true
;                    :inversions (concat (:inversions %1) (list %2))))
;           )
;        {:found_highest false :inversions []}
;        (take 25 (cycle (make_inversions_map  ["C" "D" "F" "A"] 3))))))

(defn order_inversions_by_n_higher [map_of_inversions]
  (let [highest_inversion_n 
       (reduce #(if (> (:n_higher %2) %1)
                  (:n_higher %2)
                  %1)
               0
               map_of_inversions)
        n_notes (count (:inversion (nth map_of_inversions 0)))]
    (take n_notes (:inversions (reduce #(if (:found_highest %1)
                                    (assoc %1 :inversions (concat (:inversions %1) (list %2)))
                                    (if (< (:n_higher %2) highest_inversion_n)
                                      %1
                                      (assoc %1
                                             :found_highest true
                                             :inversions (concat (:inversions %1) (list %2)))))
                                 {:found_highest false :inversions []}
                                 (take 25 (cycle map_of_inversions)))))))

;(order_inversions_by_n_higher (make_inversions_map  ["C" "E" "G" "B"] 3))
;(order_inversions_by_n_higher (make_inversions_map  ["C" "D" "F" "A"] 3))

(defn make_all_inversions [list_notes_no_octave base_octave] 
 (map #(vec (:inversion %)) (order_inversions_by_n_higher (make_inversions_map  list_notes_no_octave base_octave)))
)

;(make_all_inversions  ["C" "E" "G" "B"] 3)
;(make_all_inversions  ["C" "D" "F" "A"] 3)

(defn get_nth_note_from_nth_inversion_octave_n
  [nth_note nth_inversion list_notes_no_octave base_octave]
  (nth (cycle (nth
               (cycle (make_all_inversions list_notes_no_octave base_octave))
               (- nth_inversion 1)))
       (- nth_note 1)))

(defn get_in_Coctave_nth_note_from_nth_inversion_octave_n
  [nth_note nth_inversion list_notes_no_octave base_octave] 
  (clj_string/replace-first  
   (str 
    (:pitch-class 
     (note-info 
      (get_nth_note_from_nth_inversion_octave_n 
       nth_note nth_inversion list_notes_no_octave base_octave))) base_octave) 
   ":" ""))

;(get_nth_note_from_nth_inversion_octave_n 10 1 ["C"] 3)
;(get_in_Coctave_nth_note_from_nth_inversion_octave_n 1 1 ["C" "D" "F" "A"] 3)

;(get_nth_note_from_nth_inversion_octave_n 2 1 ["C" "D" "F" "A"] 3)
;(get_in_Coctave_nth_note_from_nth_inversion_octave_n 2 1 ["C" "D" "F" "A"] 3)

;(get_nth_note_from_nth_inversion_octave_n 3 1 ["C" "D" "F" "A"] 3)
;(get_in_Coctave_nth_note_from_nth_inversion_octave_n 3 1 ["C" "D" "F" "A"] 3)

;(get_nth_note_from_nth_inversion_octave_n 40 50 ["C" "D" "F" "A"] 3)
;(get_in_Coctave_nth_note_from_nth_inversion_octave_n 423 12 ["C" "D" "F" "A"] 3)