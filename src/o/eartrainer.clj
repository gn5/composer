;(use 'overtone.live) do in lein repl before starting emacs and m-x cider-connect

(demo 7 (lpf (mix (saw [50 (line 100 1600 5) 101 100.5]))
                   (lin-lin (lf-tri (line 2 20 5)) -1 1 400 4000)))



let [receiver (first (midi-connected-receivers))]
                                        ;Play a midi note c4 at 80 velocity for 1 millisecond on the fourth channel
                                        ;Note that the channel is zero-indexed, whereas normal mixers/midi devices start counting them from 1.
(overtone.midi/midi-note receiver (note :c4) 80 1 3)

                                        ;Turn on the sustain pedal to full on the first channel




(ns cmj.overtone
  (:use [overtone.live]
        [overtone.inst.piano]))

(piano)
