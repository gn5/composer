(ns sixdim.core
  (:use overtone.core)
  (:require [sixdim.midi.receivers :refer [midi-out-virtualport]])  
  (:gen-class))

(print midi-out-virtualport)

; (overtone.meqidi/midi-note midi-out-virtualport 66 127 500 0)

; init
(def quarter_downbeat (atom 1))
(add-watch quarter_downbeat :watcher_qd
           (fn [key atom old-state new-state]
             (println "quarter_downbeat atom has changed:")
             (println "  old-state" old-state)
             (println "  new-state" new-state)))
(def quarter_upbeat (atom 1))

; init
(def bar_bpm (atom 60))
(def eight_swing (atom 0.5)) ; start placement upbeat triplet feel 0.6
(def legato (atom 0.90)) ;percentage

; init song
(def bar_metronome (metronome @bar_bpm))
(def p1 (atom [44 55 66 77]))

(defn inc_subdiv [current_beat n_max_beat]
  (if (>= current_beat n_max_beat)
    1
    (inc current_beat)))

(swap! quarter_downbeat inc_bar_beat 4)
(print @quarter_downbeat)

(defn bar_looper [metronome_ beat_]    
    (at (metronome_ beat_)]
      (println "beat_" beat_))
        ; (apply-at (nome (+ 0.25 beat)) (overtone.midi/midi-note midi-out-virtualport 66 127 500 1))
        ; (apply-at (nome beat) (overtone.midi/midi-note midi-out-virtualport 66 127 500 0))
        ; (apply-at (nome beat) (swap! quarter_downbeat inc_bar_beat 4))
        ; (at (nome (+ 0.25 beat)) (swap! quarter_downbeat inc_bar_beat 4))
        ; (at (nome (+ 0.5 beat)) (swap! quarter_downbeat inc_bar_beat 4))
        ; (at (nome (+ 0.75 beat)) (swap! quarter_downbeat inc_bar_beat 4))
        ; recall looper on next bar
    (apply-at (metronome_ (inc beat_)) #'bar_looper [metronome_ (inc beat_)]))

; turn on the metronome
(bar_looper bar_metronome (bar_metronome))
; (stop)

; (print bar_metronome)
(bar_metronome)
; (print @p1)
  
(defn bar_player
  [beat]
  (println "bar trigger.. beat = " beat))
(defn quarter_player
  [beat]
  (println "quarter trigger.. beat = " beat))
(defn eighth_player
  [beat]
  (println "eigthh trigger.. beat = " beat))
(defn triplet_player
  [beat]
  (println "triplet trigger.. beat = " beat))
(defn sixteenth_player
  [beat]
  (println "sixteenth trigger.. beat = " beat))
              
(defn trigger_bar_note
  [beat metro]
    (apply-at (metro beat) #'bar_player [beat]))
(defn trigger_quarter_note
  [beat metro]
    (apply-at (metro beat) #'quarter_player [beat]))
(defn trigger_eighth_note
  [beat metro]
    (apply-at (metro beat) #'eighth_player [beat]))
(defn trigger_triplet_note
  [beat metro]
    (apply-at (metro beat) #'triplet_player [beat]))
(defn trigger_sixteenth_note
  [beat metro]
    (apply-at (metro beat) #'sixteenth_player [beat]))

(defn qq
  [bbb]
  (println "sixteenth trigger.. beat = " bbb))
(defn trigg
  [bb metro]
    (apply-at (metro bb) #'qq [bb]))

(defn play_loop 
  [bar_beat metro val]
  (println "looping at bar bpm; bar_beat =" bar_beat " val =" val)
  ;(map trigger_quarter_note [0.25 0.5 0.75] [metro metro metro])
  (map trigg [0.25 0.5 0.75] [metro metro metro])
  (let [next_bar_beat (+ bar_beat 1)]
 (ns sixdim.core
  (:use overtone.core)
  (:require [
             sixdim.midi.receivers :refer [midi-out-virtualport]  
             sixdim.time.loop :refer [location 
                                      bar_bpm
                                      bar_metronome
                                      midi-out-virtualport
                                      loop_start_bar
                                      loop_end_bar
                                      play_loop]])  
  (:gen-class))
; (load-file "src/sixdim/core.clj")
(print midi-out-virtualport)
; (overtone.meqidi/midi-note midi-out-virtualport 66 127 500 0)

; (def legato (atom 0.90)) ; as percentage
; (def eighth_swing (atom 0.5)) ; placement of eighth upbeats (triplet feel is at 0.6)
(def score (atom [44 55 66 77])) ; init partition score
(def n_bars (atom 4)) ;number of bars in whole partition

(add-watch location :watcher_location
           (fn [key atom old-state new-state]
             (println "location update:" new-state)))

(play_loop (bar_metronome) bar_metronome 0)   (apply-at (metro next_bar_beat) #'play_loop [next_bar_beat metro (inc val)])))
