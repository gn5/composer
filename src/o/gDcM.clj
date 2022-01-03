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
(overtone.midi/midi-note (first receiver_) (note :c5) 60 1000 0)


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
(def all_notes [
                (note :c5)
                (note :c6)
                (note :g5)
                (note :e5)
                (note :b5)
                (note :d5)
                (note :f5)
                (note :a5)
                (note :bb5)
                (note :eb5)
                (note :ab5)
                (note :gb5) (note :db5)
                  ])
(println all_notes)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; eartrainer levels code

(def speed 50)
(def end_level 3)
(def n_notes 4)
(def level (atom 3))
(def min_level 3)
(def success (atom 2))
(def buffer_guess (atom [(note :c4) (note :e4) (note :g4) (note :c4)  ]))
(def buffer_notes (atom [(note :c4) (note :e4) (note :g4) ]))
(def metro (metronome speed))
(def notes_velocity 60)
(def legato 0.90) ;percentage
(def up_beat 0.5) ;start placement upbeat triplet feel 0.6
(def def_vol 50) ;default volume pianoteq
(def def_volbass 25)

(println (metro))
(println (metro))
(println (metro))

(defn smi2 [nn] (overtone.midi/midi-note-on (first receiver_) nn def_vol 0))
(defn smi2bass [nn] (overtone.midi/midi-note-on (first receiver_) nn def_volbass 0))
(defn smi2off [nn] (overtone.midi/midi-note-off (first receiver_) nn 0))

(defn compare_buffers [a b]
    (if (= a b)
      (swap! success inc) ;if same
      (reset! success 0) ;if different
      ))

(add-watch buffer_notes :watch_if_5
      (fn [key atom old-state new-state]
      (println "buffer_notes atom has changed:")
      (println "  old-state" old-state)
        (println "  new-state" new-state)
;; update level and trigger-play new sequence to guess
;; if length equal n notes then empty the atom
        (if (= n_notes (count new-state)) (do
                                      (println "sleep 1 sec..")
                                      (Thread/sleep 1000)
                                      (println "sleep done")
                                      (reset! buffer_notes [])
                                      (println "length reached, so reset")
                                      (compare_buffers new-state @buffer_guess)
                                      )
            )))

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
                                         )

               )
             ;; create new guess sequence and put it into atom buffer_guess
             ;; and play new sequence on metronome tempo

             (if (not= end_level new-state) (do
                                   (def last_buff (vector (nth all_notes (dec @level)))) ; index start at 0
                                      (def last_buff2 (vector (nth all_notes (dec (dec @level))))) ; index start at 0
                                      (def toshuff (into [] (concat
                                                             (take @level all_notes) (take @level all_notes)
                                                             last_buff last_buff last_buff2)))
                                      (println "new to shuffle sequence: " toshuff)
                                      (def new_vector (into [] (take n_notes (shuffle toshuff))))

                                      (println "new guess sequence: " new_vector)
                                      (println (range n_notes))
                                      (reset! buffer_guess new_vector)





                                      (def beat_ (metro))
                                      (doseq [x (range n_notes)]
                                        (def note_index (nth new_vector x))
                                        (def ti1 (+ x            (+ 1 beat_)))
                                        (def ti2 (+ x  (+ legato (+ 1 beat_))))
                                        (println "sent note" note_index " at position " x)
                                        (apply-at (metro ti1)    smi2 [note_index])
                                        (apply-at (metro ti2) smi2off [note_index])
                                   ;     (apply-at (metro ti1)    smi2bass [(note :c2)])
                                   ;     (apply-at (metro ti2) smi2off [(note :c2)])

                                        (if (< x 2) (do
                                                     (apply-at (metro ti1) smi2bass [(note :g1)])
                                                     (apply-at (metro ti2) smi2off [(note :g1)])
                                                     (apply-at (metro ti1) smi2bass [(note :g2)])
                                                     (apply-at (metro ti2) smi2off [(note :g2)])

                                                     (apply-at (metro ti1) smi2bass [(note :b3)])
                                                     (apply-at (metro ti2) smi2off [(note :b3)])
                                                     (apply-at (metro ti1) smi2bass [(note :d3)])
                                                     (apply-at (metro ti2) smi2off [(note :d3)])
                                                     (apply-at (metro ti1) smi2bass [(note :f3)])
                                                     (apply-at (metro ti2) smi2off [(note :f3)])
                                                     (apply-at (metro ti1) smi2bass [(note :b4)])
                                                     (apply-at (metro ti2) smi2off [(note :b4)])
                                                     (apply-at (metro ti1) smi2bass [(note :d4)])
                                                     (apply-at (metro ti2) smi2off [(note :d4)])
                                                     (apply-at (metro ti1) smi2bass [(note :f4)])
                                                     (apply-at (metro ti2) smi2off [(note :f4)])
                                                     ))
                                         (if (> x 1) (do
                                                     (apply-at (metro ti1) smi2bass [(note :c1)])
                                                     (apply-at (metro ti2) smi2off [(note :c1)])
                                                     (apply-at (metro ti1) smi2bass [(note :c2)])
                                                     (apply-at (metro ti2) smi2off [(note :c2)])

                                                     (apply-at (metro ti1) smi2bass [(note :e3)])
                                                     (apply-at (metro ti2) smi2off [(note :e3)])
                                                     (apply-at (metro ti1) smi2bass [(note :g3)])
                                                     (apply-at (metro ti2) smi2off [(note :g3)])
                                                     (apply-at (metro ti1) smi2bass [(note :b3)])
                                                     (apply-at (metro ti2) smi2off [(note :b3)])
                                                     (apply-at (metro ti1) smi2bass [(note :e4)])
                                                     (apply-at (metro ti2) smi2off [(note :e4)])
                                                     (apply-at (metro ti1) smi2bass [(note :g4)])
                                                     (apply-at (metro ti2) smi2off [(note :g4)])
                                                     (apply-at (metro ti1) smi2bass [(note :b4)])
                                                     (apply-at (metro ti2) smi2off [(note :b4)])
                                                     ))

                                        )


                                      )))
           )



(add-watch level :watch_level
           (fn [key atom old-state new-state]
             (println "level atom changed to :" new-state)
             ))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; activate game: input to game buffer
(on-event [:midi :note-on] (fn [{note :note velocity :velocity}]
                             (swap! buffer_notes (fn [value] (concat value [note])))
          ) ::note-on-to-game)
(reset! buffer_notes [(note :c4) (note :e4) (note :g4) ])






;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; test:
;(swap! buffer_notes (fn [value] (concat value [1])))
;(reset! buffer_notes @buffer_guess)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; overtone pianoteq code, after metronome metro started
(defn pianoteq_play_note
  [note velocity duration] ; arity 3
  (do
    ()
    ()
    )
  [note velocity] ; arity 2 so assume 1 beat duration
  (do
    ()
    ()
  )
  )

(defn pianoteq_play_game_sequence
  [notes_vec velocity duration] ; arity 3
  (do
    ()
    ()
    )
  [notes_vec velocity] ; arity 2 so assume 1 beat duration
  (do
    ()
    ()
  )
)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;other

;(defn testt [nn] (overtone.midi/midi-note (first receiver_) nn def_vol 3000 0))
;(testt (note :b4))
;(apply-at (metro (+ 2 (metro))) testt [(note :e4)])
;(defn testt [nn] (overtone.midi/midi-note-on (first receiver_) nn def_vol 0))
