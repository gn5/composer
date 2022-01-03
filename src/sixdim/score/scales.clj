(ns sixdim.score.scales
  (:use overtone.core)
  ; (:require [sixdim.time.loop :refer [bar_bpm]])
  (:gen-class))

(def map_note_flats
  {:A "A" :a "Ab" :B "B" :b "Bb" :C "C" :c "Cb"
   :D "D" :d "Db" :E "E" :e "Eb" :F "F" :f "Fb"
   :G "G" :g "Gb"})

(def atom scales [])

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
       (reduce conj [] (repeat (count gen_map) score))
       gen_maps))

