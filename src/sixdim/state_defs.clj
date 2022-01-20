(ns sixdim.state_defs
  (:gen-class))
;; global app state definitions

; bpm (n beats per minute) of bar (=4 quarter notes)
(def bar_bpm 20) 

(def default_note_volume 63) ; in midi range 1 to 127

; (def legato (atom 0.90)) ; as percentage
; (def eighth_swing (atom 0.5)) ; placement of eighth upbeats (triplet feel is at 0.6)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; GUI defs
(def default_color [0.99 0.99 0.99]) ; close to 1 (white)
(def default_bg_color [0.01 0.01 0.01]) ; close to 0 (black)
(def default_font nil)
(def default_font_size 20)

