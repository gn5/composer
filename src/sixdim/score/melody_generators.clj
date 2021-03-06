(ns sixdim.score.melody_generators
  (:use overtone.core)
  (:require
    [sixdim.score.score :as score]
    [sixdim.score.score_nav :as nav]
    [sixdim.score.scales :as scales])
  (:gen-class))

(def seconds [{:sign + :n 1} {:sign + :n 2}
              {:sign - :n 1} {:sign - :n 2}])
(def seconds_down [{:sign - :n 1} {:sign - :n 2}])
(def seconds_up [{:sign + :n 1} {:sign + :n 2}])
(def n34_57_down [{:sign - :n 3} {:sign - :n 4}
                 {:sign - :n 5} {:sign - :n 7}])
(def n34_57_up [{:sign + :n 3} {:sign + :n 4}
               {:sign + :n 5} {:sign + :n 7}])

(defn gen_note_from_intervals_eight
  [intervals_map score bar_n beat_key beat_n extra_gen_args]
  "- generate possible scalar note: semitone or tone up or down
   - from 1 score, generate vector of n (length intervals) scores
     where each score has new note at beat_n
     - new note is previous eigth note +- n semitones"
  (let [previous_eigth ((nav/nav_eight score bar_n beat_key beat_n - 1) "pitch")
        current_note   (nav/get_score_beat score bar_n beat_key beat_n)]
        ; scale (scales/get_scale (current_note "scale_id")
                                ; (:scales extra_gen_args))]
          (map #(score/replace_score_note score bar_n beat_key beat_n; replace current note
                  (assoc current_note 
                         "pitch" 
                         (scales/shift_note previous_eigth ; with previous note
                                            (:sign %)      ;  + or -
                                            (:n %))))      ;  n semitones
            intervals_map)))
(defn gen_note_from_intervals_34_57_down_eight
  [score_ bar_n_ beat_key_ beat_n_ extra_gen_args] 
  (gen_note_from_intervals_eight n34_57_down 
                           score_ bar_n_ beat_key_ beat_n_ extra_gen_args))
(defn gen_note_from_intervals_34_57_up_eight
  [score_ bar_n_ beat_key_ beat_n_ extra_gen_args] 
  (gen_note_from_intervals_eight n34_57_up
                           score_ bar_n_ beat_key_ beat_n_ extra_gen_args))
(defn gen_note_from_intervals_seconds_eight 
  [score_ bar_n_ beat_key_ beat_n_ extra_gen_args] 
  (gen_note_from_intervals_eight seconds 
                           score_ bar_n_ beat_key_ beat_n_ extra_gen_args))
(defn gen_note_from_intervals_seconds_down_eight
  [score_ bar_n_ beat_key_ beat_n_ extra_gen_args] 
  (gen_note_from_intervals_eight seconds_down 
                           score_ bar_n_ beat_key_ beat_n_ extra_gen_args))
(defn gen_note_from_intervals_seconds_up_eight
  [score_ bar_n_ beat_key_ beat_n_ extra_gen_args] 
  (gen_note_from_intervals_eight seconds_up 
                           score_ bar_n_ beat_key_ beat_n_ extra_gen_args))


(defn gen_note_from_intervals_triplet
  [intervals_map score bar_n beat_key beat_n extra_gen_args]
  (let [previous_eigth ((nav/nav_triplet score bar_n beat_key beat_n - 1) "pitch")
        current_note   (nav/get_score_beat score bar_n beat_key beat_n)]
          (map #(score/replace_score_note score bar_n beat_key beat_n
                  (assoc current_note 
                         "pitch" 
                         (scales/shift_note previous_eigth ; with previous note
                                            (:sign %)      ;  + or -
                                            (:n %))))      ;  n semitones
            intervals_map)))
(defn gen_note_from_intervals_34_57_down_triplet
  [score_ bar_n_ beat_key_ beat_n_ extra_gen_args] 
  (gen_note_from_intervals_triplet n34_57_down 
                           score_ bar_n_ beat_key_ beat_n_ extra_gen_args))
(defn gen_note_from_intervals_34_57_up_triplet
  [score_ bar_n_ beat_key_ beat_n_ extra_gen_args] 
  (gen_note_from_intervals_triplet n34_57_up
                           score_ bar_n_ beat_key_ beat_n_ extra_gen_args))
(defn gen_note_from_intervals_seconds_triplet
  [score_ bar_n_ beat_key_ beat_n_ extra_gen_args] 
  (gen_note_from_intervals_triplet seconds 
                           score_ bar_n_ beat_key_ beat_n_ extra_gen_args))
(defn gen_note_from_intervals_seconds_down_triplet
  [score_ bar_n_ beat_key_ beat_n_ extra_gen_args] 
  (gen_note_from_intervals_triplet seconds_down 
                           score_ bar_n_ beat_key_ beat_n_ extra_gen_args))
(defn gen_note_from_intervals_seconds_up_triplet
  [score_ bar_n_ beat_key_ beat_n_ extra_gen_args] 
  (gen_note_from_intervals_triplet seconds_up 
                           score_ bar_n_ beat_key_ beat_n_ extra_gen_args))

(defn gen_note_from_intervals_sixteen
  [intervals_map score bar_n beat_key beat_n extra_gen_args]
  (let [previous_eigth ((nav/nav_sixteen score bar_n beat_key beat_n - 1) "pitch")
        current_note   (nav/get_score_beat score bar_n beat_key beat_n)]
          (map #(score/replace_score_note score bar_n beat_key beat_n
                  (assoc current_note 
                         "pitch" 
                         (scales/shift_note previous_eigth ; with previous note
                                            (:sign %)      ;  + or -
                                            (:n %))))      ;  n semitones
            intervals_map)))
(defn gen_note_from_intervals_34_57_down_sixteen
  [score_ bar_n_ beat_key_ beat_n_ extra_gen_args] 
  (gen_note_from_intervals_sixteen n34_57_down 
                           score_ bar_n_ beat_key_ beat_n_ extra_gen_args))
(defn gen_note_from_intervals_34_57_up_sixteen
  [score_ bar_n_ beat_key_ beat_n_ extra_gen_args] 
  (gen_note_from_intervals_sixteen n34_57_up
                           score_ bar_n_ beat_key_ beat_n_ extra_gen_args))
(defn gen_note_from_intervals_seconds_sixteen
  [score_ bar_n_ beat_key_ beat_n_ extra_gen_args] 
  (gen_note_from_intervals_sixteen seconds 
                           score_ bar_n_ beat_key_ beat_n_ extra_gen_args))
(defn gen_note_from_intervals_seconds_down_sixteen
  [score_ bar_n_ beat_key_ beat_n_ extra_gen_args] 
  (gen_note_from_intervals_sixteen seconds_down 
                           score_ bar_n_ beat_key_ beat_n_ extra_gen_args))
(defn gen_note_from_intervals_seconds_up_sixteen
  [score_ bar_n_ beat_key_ beat_n_ extra_gen_args] 
  (gen_note_from_intervals_sixteen seconds_up 
                           score_ bar_n_ beat_key_ beat_n_ extra_gen_args))





















(defn gen_note_from_scale
  [score bar_n beat_key beat_n extra_gen_args]
  (let [current_note (nav/get_score_beat score bar_n beat_key beat_n) 
        scale_id (:scale_id extra_gen_args)]
    ;replace current note
    (list (score/replace_score_note 
        score bar_n beat_key beat_n
        (score/replace_note_scale current_note scale_id)))))

(defn gen_note_play_false
  [score bar_n beat_key beat_n extra_gen_args]
  (let [current_note (nav/get_score_beat score bar_n beat_key beat_n)]
    (list (score/replace_score_note 
        score bar_n beat_key beat_n 
        (score/replace_note_play current_note false)))))

(defn gen_note_play_true
  [score bar_n beat_key beat_n extra_gen_args]
  (let [current_note (nav/get_score_beat score bar_n beat_key beat_n)]
    (list (score/replace_score_note 
        score bar_n beat_key beat_n 
        (score/replace_note_play current_note true)))))

; (mgens/gen_note_from_intervals_seconds_up 
  ; @atoms/score1 1 "eight" 1 {:scale_id "ra"}) 
   
; (list (mgens/gen_note_from_scale
  ; @atoms/score1 1 "eight" 1 {:scale_id "ra"})) 


(def closest_note_inc_map
  {"up_and_down" [[{:sign + :n 0}] 
                  [{:sign + :n 1} {:sign - :n 1}]
                  [{:sign + :n 2} {:sign - :n 2}]
                  [{:sign + :n 3} {:sign - :n 3}]
                  [{:sign + :n 4} {:sign - :n 4}]
                  [{:sign + :n 5} {:sign - :n 5}]
                  [{:sign + :n 6} {:sign - :n 6}]
                  [{:sign + :n 7} {:sign - :n 7}]
                  [{:sign + :n 8} {:sign - :n 8}]
                  [{:sign + :n 9} {:sign - :n 9}]
                  [{:sign + :n 10} {:sign - :n 10}]
                  [{:sign + :n 11} {:sign - :n 11}]]
   "up_first"  [[{:sign + :n 0}] 
                [{:sign + :n 1}][{:sign - :n 1}]
                [{:sign + :n 2}][{:sign - :n 2}]
                [{:sign + :n 3}][{:sign - :n 3}]
                [{:sign + :n 4}][{:sign - :n 4}]
                [{:sign + :n 5}][{:sign - :n 5}]
                [{:sign + :n 6}][{:sign - :n 6}]
                [{:sign + :n 7}][{:sign - :n 7}]
                [{:sign + :n 8}][{:sign - :n 8}]
                [{:sign + :n 9}][{:sign - :n 9}]
                [{:sign + :n 10}][{:sign - :n 10}]
                [{:sign + :n 11}][{:sign - :n 11}]]
   "down_first" [[{:sign + :n 0}] 
                [{:sign - :n 1}][{:sign + :n 1}]
                [{:sign - :n 2}][{:sign + :n 2}]
                [{:sign - :n 3}][{:sign + :n 3}]
                [{:sign - :n 4}][{:sign + :n 4}]
                [{:sign - :n 5}][{:sign + :n 5}]
                [{:sign - :n 6}][{:sign + :n 6}]
                [{:sign - :n 7}][{:sign + :n 7}]
                [{:sign - :n 8}][{:sign + :n 8}]
                [{:sign - :n 9}][{:sign + :n 9}]
                [{:sign - :n 10}][{:sign + :n 10}]
                [{:sign - :n 11}][{:sign + :n 11}]]})

(defn inc_note_to_closest [note_oct n_vec]
  (vec (map #(scales/shift_note note_oct (:sign %) (:n %)) n_vec)))
; (inc_note_to_closest "A4" [{:sign - :n 8} {:sign + :n 8}])

(defn note_oct_del_not_in_scale_key [note_oct scale scale_key_str]
  (as-> note_oct v
    (note-info v) 
    (:pitch-class v) 
    (name v) 
    (reduce #(if (= %1 %2) "in" %1) v ((keyword scale_key_str) scale)) 
    (if (= v "in") note_oct nil)))

(defn note_oct_vec_del_not_in_scale_key [note_oct_vec scale scale_key_str]
  (as-> note_oct_vec v
    (map #(note_oct_del_not_in_scale_key % scale scale_key_str) v)
    (filter string? v)
    (vec v)))
  
(def beat_key_to_scale_key 
  {"quarter" "downbeats"
   "eight" "upbeats"
   "triplet" "downbeats"
   "sixteen" "downbeats"})

(defn gen_closest_scale_note_up_and_down [score bar_n beat_key beat_n extra_gen_args]
  (let [current_note (nav/get_score_beat score bar_n beat_key beat_n)]
    (let [scale (scales/get_scale (current_note "scale_id")
                                  (:scales extra_gen_args))]
      (let [new_notes_vec
            (as-> (current_note "pitch") v
              (map #(inc_note_to_closest v %)   
                   (closest_note_inc_map "up_and_down"))
            (map #(note_oct_vec_del_not_in_scale_key 
                    % scale 
                    (beat_key_to_scale_key beat_key))
                 v)
              (filter (complement empty?) v)
              (first v))]
        (map #(score/replace_score_note 
                score bar_n beat_key beat_n 
                (assoc current_note "pitch" %)) 
             new_notes_vec)))))

(defn gen_closest_scale_note_up_first [score bar_n beat_key beat_n extra_gen_args]
  (let [current_note (nav/get_score_beat score bar_n beat_key beat_n)]
    (let [scale (scales/get_scale (current_note "scale_id")
                                  (:scales extra_gen_args))]
      (let [new_notes_vec
            (as-> (current_note "pitch") v
              (map #(inc_note_to_closest v %)   
                   (closest_note_inc_map "up_first"))
            (map #(note_oct_vec_del_not_in_scale_key 
                    % scale 
                    (beat_key_to_scale_key beat_key))
                 v)
              (filter (complement empty?) v)
              (first v))]
        (map #(score/replace_score_note 
                score bar_n beat_key beat_n 
                (assoc current_note "pitch" %)) 
             new_notes_vec)))))

(defn gen_closest_scale_note_down_first [score bar_n beat_key beat_n extra_gen_args]
  (let [current_note (nav/get_score_beat score bar_n beat_key beat_n)]
    (let [scale (scales/get_scale (current_note "scale_id")
                                  (:scales extra_gen_args))]
      (let [new_notes_vec
            (as-> (current_note "pitch") v
              (map #(inc_note_to_closest v %)   
                   (closest_note_inc_map "down_first"))
            (map #(note_oct_vec_del_not_in_scale_key 
                    % scale 
                    (beat_key_to_scale_key beat_key))
                 v)
              (filter (complement empty?) v)
              (first v))]
        (map #(score/replace_score_note 
                score bar_n beat_key beat_n 
                (assoc current_note "pitch" %)) 
             new_notes_vec)))))

(defn gen_shift_up_scale1 [score bar_n beat_key beat_n extra_gen_args]
  (let [current_note (nav/get_score_beat score bar_n beat_key beat_n)]
    (let [scale (scales/get_scale (current_note "scale_id")
                                  (:scales extra_gen_args))]
      (let [new_pitch 
            (scales/up_scale_note_n (current_note "pitch") scale 1)]
        (list (score/replace_score_note 
                score bar_n beat_key beat_n 
                (assoc current_note "pitch" new_pitch)))))))

; (gen_shift_up_scale1 @atoms/score1 1 "quarter" 1 {:scales @atoms/scales})

(defn gen_shift_down_scale1 [score bar_n beat_key beat_n extra_gen_args]
  (let [current_note (nav/get_score_beat score bar_n beat_key beat_n)]
    (let [scale (scales/get_scale (current_note "scale_id")
                                  (:scales extra_gen_args))]
      (let [new_pitch 
            (scales/down_scale_note_n (current_note "pitch") scale 1)]
        (list (score/replace_score_note 
                score bar_n beat_key beat_n 
                (assoc current_note "pitch" new_pitch)))))))







