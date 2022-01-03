;;;

;;; must have pianoteq in jack auto-connect ON but disconnected
;;; after code below executed, then set active midi inputs to vir3

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; setup overtone and check
(ns overtone.examples.midi.keyboard
  (:use overtone.live))

;(def condevices [(midi-connected-devices)])
;(println condevices)
;(println (count condevices))

(def receiver_ [(first (midi-connected-receivers))])
(println receiver_)
(println count receiver_)
;(overtone.midi/midi-note (first receiver_) (note :c5) 60 1000 0)


(def buffer_play (atom {:note (note :c3)  :velocity 80}) )
(add-watch buffer_play :watch_to_pianoteq
      (fn [key atom old-state new-state]
      (println "buffer_play atom has changed:")
      (println "  old-state" old-state)
      (overtone.midi/midi-note-on (first receiver_) (get new-state :note) (get new-state :velocity) 0)
        (println "  new-state" new-state)
        ))

(def buffer_play_off (atom {:note (note :c3)}) )
(add-watch buffer_play_off :watch_to_pianoteq_off
      (fn [key atom old-state new-state]
      (println "buffer_play_off atom has changed:")
      (println "  old-state" old-state)
      (overtone.midi/midi-note-off (first receiver_) (get new-state :note) 0)
        (println "  new-state" new-state)
        ))

; tests:
;(reset! buffer_play {:note (note :d5)  :velocity 95})
;(reset! buffer_play_off {:note (note :d5)})

;(pr1intln digg)
;(let [receiver (first (midi-connected-receivers))]
;  (println receiver)
 ; (overtone.midi/midi-note receiver_ (note :c3) 80 1000 0)
;  (overtone.midi/midi-note-off receiver (note :c3) 0)
                                        ;  (overtone.midi/midi-control receiver 64 127 0)
                                        ;
;)


;(definst ding
;  [note 60 velocity 100]
;  (let [freq (midicps note)
;        snd  (sin-osc freq)
;        env  (env-gen (perc 0.1 0.8) :action FREE)]
;    (* velocity env snd)))

;(defn midi-player [event]
;  (ding (:note event) (/ (:velocity event) 127.0)))




(on-event [:midi :note-on] (fn [{note :note velocity :velocity}]
                               (println "Note on: ", note)
                             (reset! buffer_play {:note note :velocity velocity})

                         ;      (println "Note on2: ", note)
                               ) ::note-on-printer)

;;(remove-event-handler ::note-printer)

(on-event [:midi :note-off] (fn [{note :note}]
                              (do
                               (reset! buffer_play_off {:note note})
                                (println "Note off: ", note)
                              )
                              ) ::note-off-printer)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; eartrainer levels code
(def all_chords [
    [(note :a3) (note :c4) (note :e4) (note :g4)] ;6
    [(note :b3) (note :d4) (note :f4) (note :a4)] ;7
    [(note :b3) (note :d4) (note :e4) (note :ab4)] ;dom 3 with 6 in bass
    [(note :c4) (note :e4) (note :g4) (note :b4)] ;1

    [(note :g3) (note :a3) (note :c4) (note :e4) ] ;; inver 3
    [(note :a3) (note :b3) (note :d4) (note :f4) ]
    [(note :ab3) (note :b3) (note :d4) (note :e4) ]
    [(note :b3) (note :c4) (note :e4) (note :g4) ]

    [(note :e3) (note :g3) (note :a3) (note :c4)  ] ;; inver 2
    [(note :a3) (note :b3) (note :d4) (note :f3) ]
    [(note :ab3) (note :b3) (note :d4) (note :e3) ]
    [(note :b3) (note :c4) (note :e4) (note :g3) ]

                 ]
)
(def speed 50)
(def length_chords 4)
(def end_level 4)
(def n_chords 4)
(def level (atom 4))
(def min_level 4)
(def success (atom 2))
(def buffer_notes (atom (note :c4) (note :e4) (note :g4)))
(def buffer_chords (atom [
  [(note :a4) (note :c5) (note :e5) (note :g5)]
  [(note :a4) (note :c5) (note :e5) (note :g5)]
  [(note :a4) (note :c5) (note :e5) (note :g5)]
  ]))
(def buffer_guess (atom [
    [(note :a4) (note :c5) (note :e5) (note :g5)]
    [(note :a4) (note :c5) (note :e5) (note :g5)]
    [(note :a4) (note :c5) (note :e5) (note :g5)]
    [(note :b4) (note :d5) (note :f5) (note :a5)]
]))

(def metro (metronome speed))
(def notes_velocity 60)
(def legato 0.90) ;percentage
(def up_beat 0.5) ;start placement upbeat triplet feel 0.6
(def def_vol 50) ;default volume pianoteq
(def def_volbass 15)

(defn smi2 [nn] (overtone.midi/midi-note-on (first receiver_) nn def_vol 0))
(defn smi2bass [nn] (overtone.midi/midi-note-on (first receiver_) nn def_volbass 0))
(defn smi2off [nn] (overtone.midi/midi-note-off (first receiver_) nn 0))

(defn play_chord [tti1 tti2 chord_] (do
  (println "begin play_chord:" chord_)
  (doseq [x (take (count chord_) (range))]
    (def note_index (nth chord_ x))
    (apply-at (metro tti1)    smi2 [note_index])
    (apply-at (metro tti2) smi2off [note_index])
    ;(println "sent note" note_index " at position " )
    )
  (println "end play_chord")
))

;(def chord1 (into [] (flatten (take 1 (shuffle all_chords)))))
;(play_chord (metro) chord1)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(on-event [:midi :note-on] (fn [{note :note velocity :velocity}]
                             (swap! buffer_notes (fn [value] (concat value [note])))
          ) ::note-on-to-game)

(add-watch buffer_guess :watch_guess
               (fn [key atom old-state new-state]
                 (println "buffer_guess atom changed to :" new-state)
                 ))

(add-watch buffer_notes :watch_notes
  (fn [key atom old-state new-state]
    (println "buffer_notes atom changed to :" new-state)
   (if (= length_chords (count new-state)) (do
     (println "buffer_notes to buffer_chords :" new-state)
     (swap! buffer_chords (fn [value] (concat value [new-state])))
     (reset! buffer_notes [])
  ))))


(defn compare_buffers [a b]
  (println "compare")
  (println (sort (into [] (flatten a))))
  (println (sort (into [] (flatten b))))
  (if (= (sort (into [] (flatten a))) (sort (into [] (flatten b))))
    (do
        (println "equal")
        (swap! success inc) );if same
    (do
        (println "different")
        (reset! success 0)) ;if different
))

(add-watch buffer_chords :watch_chords
      (fn [key atom old-state new-state] (do
      (println "buffer_chords atom changed to :" new-state)
      ;; update level and trigger-play new sequence to guess
      ;; if length equal n notes then empty the atom
      (if (= n_chords (count new-state)) (do
                                            (println "sleep 1 sec..")
                                            (Thread/sleep 1000)
                                            (println "sleep done")
                                            (reset! buffer_chords [])
                                            (println "length reached, so reset")
                                            (compare_buffers new-state @buffer_guess)
                                            )
                  ))
))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(add-watch success :watch_success
           (fn [key atom old-state new-state]
             (println "success atom changed to :" new-state)
             (if (= 0 new-state) (do
                                   (println "wrong guess, so level down")
                     ;    (overtone.midi/midi-note (first receiver_) (note :c5) def_vol 330 0)
                                   (if (> @level min_level)  (swap! level dec))
                                   (println  "new level is:" @level)
               ))
           (if (= end_level new-state) (do
                                         (swap! level inc)
                                         (reset! success 1)
                                         (println "lvl up so success reset to:" @success)
                                   (println  "new seq, level is:" @level)
                                         )

               )
             ;; create new guess sequence and put it into atom buffer_guess
             ;; and play new sequence on metronome tempo

             (if (not= end_level new-state) (do
                                   (println  "new seq, level is:" @level)
                                   (def last_buff (vector (nth all_chords (dec @level)))) ; index start at 0
                                      (def last_buff2 (vector (nth all_chords (dec (dec @level))))) ; index start at 0
                                      (def toshuff (into [] (concat
                                                             (take @level all_chords) (take @level all_chords)
                                                             last_buff last_buff last_buff2)))
                                      (println "new to shuffle sequence: " toshuff)
                                      (def new_vector (into [] (take n_chords (shuffle toshuff))))

                                      (println "new guess sequence: " new_vector)
                                      (println (range n_chords))
                                      (reset! buffer_guess new_vector)

                                      (def beat_ (metro))
                                      (doseq [xx (range n_chords)]
                                        (def chord_index (nth new_vector xx))
                                        (def ti1 (+ xx            (+ 1 beat_)))
                                        (def ti2 (+ xx  (+ legato (+ 1 beat_))))
                                        (println "sent chord" chord_index " at position " xx)
                                        (play_chord ti1 ti2 chord_index)
                                      )


                                      )))
)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(println "all chords: " all_chords)
;(reset! buffer_guess [[(note :c4) (note :e4) (note :g4)]])
;(reset! buffer_notes [(note :c4) (note :e4) (note :g4)])
;(reset! buffer_notes [(note :c4) (note :e4) ])
;(reset! buffer_notes [(note :c4) (note :e4) (note :g4) (note :g4)])
;(reset! buffer_notes [(note :c4) (note :e4) ])
;(reset! buffer_notes [(note :c4) (note :e4) (note :g4) (note :g4)])
