(ns sixdim.atoms
  (:gen-class))
;; global app atoms 

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; score atoms
(defonce n_bars (atom 4)) ; init number of bars in whole score
(defonce score1 (atom [])) ; init partition score
(defonce score2 (atom []))
(defonce score3 (atom []))
(defonce score4 (atom []))
(defonce score5 (atom []))
(defonce score6 (atom []))
(defonce score7 (atom []))
(defonce score8 (atom []))

; midi channel to use for each voice ("score{1..8}" atoms)
(defonce midi_channel1 (atom 0))
(defonce midi_channel2 (atom 1))
(defonce midi_channel3 (atom 2))
(defonce midi_channel4 (atom 3))
(defonce midi_channel5 (atom 4))
(defonce midi_channel6 (atom 5))
(defonce midi_channel7 (atom 6))
(defonce midi_channel8 (atom 7))

; mute/unmute score1..8 to respective midi channel
;                                     e.g. midi_channel1
(defonce to_midi1 (atom false))
(defonce to_midi2 (atom false))
(defonce to_midi3 (atom false))
(defonce to_midi4 (atom false))
(defonce to_midi5 (atom false))
(defonce to_midi6 (atom false))
(defonce to_midi7 (atom false))
(defonce to_midi8 (atom false))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; midi-cc score atoms
(defonce cc1 (atom [])) ; init partition cc score
(defonce cc2 (atom []))
(defonce cc3 (atom []))
(defonce cc4 (atom []))
(defonce cc5 (atom []))
(defonce cc6 (atom []))
(defonce cc7 (atom []))
(defonce cc8 (atom []))

; midi channel to use for each cc ("cc{1..8}" atoms)
(defonce midi_channel_cc1 (atom 0))
(defonce midi_channel_cc2 (atom 1))
(defonce midi_channel_cc3 (atom 2))
(defonce midi_channel_cc4 (atom 3))
(defonce midi_channel_cc5 (atom 4))
(defonce midi_channel_cc6 (atom 5))
(defonce midi_channel_cc7 (atom 6))
(defonce midi_channel_cc8 (atom 7))

; mute/unmute cc1..8 to respective midi channel
;                                     e.g. midi_channel_cc1
(defonce to_midi_cc1 (atom false))
(defonce to_midi_cc2 (atom false))
(defonce to_midi_cc3 (atom false))
(defonce to_midi_cc4 (atom false))
(defonce to_midi_cc5 (atom false))
(defonce to_midi_cc6 (atom false))
(defonce to_midi_cc7 (atom false))
(defonce to_midi_cc8 (atom false))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; scale atoms
(def scales (atom []))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; play loop atoms
(defonce loop_start_bar (atom 1)) ; first bar of play loop 
(defonce loop_end_bar (atom 2)) ; last bar of play loop 
; init location to last quarter and bar to 0
(defonce location ; current beat location (within loop start-end) used to trigger midi player(s)
  (atom {"bar" 0     ; bar number
         "quarter" 4 ; downbeats (quarter notes)
         "eight" 0   ; upbeats (eighth notes in-between quarter notes)
         "triplet" 0 ; triplets upbeats in-between quarter notes)
         "sixteen" 0 ; sixteenth upbeats in-between eighth notes
         "current_subdiv" "quarter" ; latest updated bar sub-division/beat
         }))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; melody atoms
(defonce gen_maps (atom []))
(defonce active_patterns (atom [])) 
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; gui atoms
(defonce key_press (atom "t"))
(defonce log1 (atom "init"))
(defonce menu (atom "base"))

(defonce selection_bar_start (atom 1))
(defonce selection_bar_end (atom 2))
(defonce bar_view_horizontal (atom ""))
(defonce bar_view_vertical (atom ""))
(defonce selection_eight_start (atom 2))
(defonce selection_eight_end (atom 8))
(defonce selection_triplet_start (atom 2))
(defonce selection_triplet_end (atom 12))
(defonce selection_sixteen_start (atom 2))
(defonce selection_sixteen_end (atom 16))

(defonce active_view_bar (atom 1))
(defonce active_scores_n (atom [1]))
(defonce active_scores (atom []))
(defonce active_score (atom []))
(defonce active_ccs_n (atom [1]))
(defonce active_ccs (atom []))
(defonce active_cc (atom []))

(defn foo [bar] bar)
(defonce active_generator (atom foo))
(defonce active_filter (atom foo))
(defonce active_scale (atom "CM5"))
; (defonce active_scale (atom "CM6"))
; hold vec of alternative scores in buffer
(defonce scores_buffer (atom []))
; active index being checked out (range 1 to count)
(defonce index_scores_buffer (atom 1))
(defonce n_scores_buffer (atom 1))


; text areas for main window horizontal panels
(defonce text_hoz1_1 (atom "---"))
(defonce text_hoz1_2 (atom "---"))
(defonce text_hoz1_3 (atom "---"))
(defonce text_hoz1_4 (atom "---"))
(defonce text_hoz2_1 (atom "---"))
(defonce text_hoz2_2 (atom "---"))
(defonce text_hoz2_3 (atom "---"))
(defonce text_hoz2_4 (atom "---"))
(defonce text_hoz3_1 (atom "---"))
(defonce text_hoz3_2 (atom "---"))
(defonce text_hoz3_3 (atom "---"))
(defonce text_hoz3_4 (atom "---"))

; undo/redo score changes
(defonce score1_undo (atom {:back [] :forw []}))
(defonce score2_undo (atom {:back [] :forw []}))
(defonce score3_undo (atom {:back [] :forw []}))
(defonce score4_undo (atom {:back [] :forw []}))
(defonce score5_undo (atom {:back [] :forw []}))
(defonce score6_undo (atom {:back [] :forw []}))
(defonce score7_undo (atom {:back [] :forw []}))
(defonce score8_undo (atom {:back [] :forw []}))
(defonce n_score_active_undo (atom {:back 0 :forw 0}))
(defonce n_score1_undo (atom {:back 0 :forw 0}))
(defonce n_score2_undo (atom {:back 0 :forw 0}))
(defonce n_score3_undo (atom {:back 0 :forw 0}))
(defonce n_score4_undo (atom {:back 0 :forw 0}))
(defonce n_score5_undo (atom {:back 0 :forw 0}))
(defonce n_score6_undo (atom {:back 0 :forw 0}))
(defonce n_score7_undo (atom {:back 0 :forw 0}))
(defonce n_score8_undo (atom {:back 0 :forw 0}))

; undo/redo cc changes
(defonce cc1_undo (atom {:back [] :forw []}))
(defonce cc2_undo (atom {:back [] :forw []}))
(defonce cc3_undo (atom {:back [] :forw []}))
(defonce cc4_undo (atom {:back [] :forw []}))
(defonce cc5_undo (atom {:back [] :forw []}))
(defonce cc6_undo (atom {:back [] :forw []}))
(defonce cc7_undo (atom {:back [] :forw []}))
(defonce cc8_undo (atom {:back [] :forw []}))

(defonce n_cc_active_undo (atom {:back 0 :forw 0}))
(defonce n_cc1_undo (atom {:back 0 :forw 0}))
(defonce n_cc2_undo (atom {:back 0 :forw 0}))
(defonce n_cc3_undo (atom {:back 0 :forw 0}))
(defonce n_cc4_undo (atom {:back 0 :forw 0}))
(defonce n_cc5_undo (atom {:back 0 :forw 0}))
(defonce n_cc6_undo (atom {:back 0 :forw 0}))
(defonce n_cc7_undo (atom {:back 0 :forw 0}))
(defonce n_cc8_undo (atom {:back 0 :forw 0}))

(defonce auto_play (atom 
  {:requested 0 ; 1 when keypress or midi 
   :silent_pre_bar 1 ; 1 to play a silent pre bar  
   :state "inactive" ; for one-shot: first_silent first_to_last inactive 
   :start_loop 1
   :end_loop 2
   :scores [1] ; which scores e.g. [1 2] to midi play (mute/unmute)
   :ccs []}))

; (defonce to_midi_auto_play (atom "w"))

(defonce atoms_to_save (atom [
  ["scales" scales]
  ["n_bars" n_bars]
  ["midi_channel1" midi_channel1]
  ["midi_channel2" midi_channel2]
  ["midi_channel3" midi_channel3]
  ["midi_channel4" midi_channel4]
  ["midi_channel5" midi_channel5]
  ["midi_channel6" midi_channel6]
  ["midi_channel7" midi_channel7]
  ["midi_channel8" midi_channel8]
  ["midi_channel_cc1" midi_channel_cc1]
  ["midi_channel_cc2" midi_channel_cc2]
  ["midi_channel_cc3" midi_channel_cc3]
  ["midi_channel_cc4" midi_channel_cc4]
  ["midi_channel_cc5" midi_channel_cc5]
  ["midi_channel_cc6" midi_channel_cc6]
  ["midi_channel_cc7" midi_channel_cc7]
  ["midi_channel_cc8" midi_channel_cc8]
  ["score1" score1]
  ["score2" score2]
  ["score3" score3]
  ["score4" score4]
  ["score5" score5]
  ["score6" score6]
  ["score7" score7]
  ["score8" score8]
  ["score1" score1]
  ["cc2" cc2]
  ["cc3" cc3]
  ["cc4" cc4]
  ["cc5" cc5]
  ["cc6" cc6]
  ["cc7" cc7]
  ["cc8" cc8]
  ["scores_buffer" scores_buffer]
  ["index_scores_buffer" index_scores_buffer]
  ["n_scores_buffer" n_scores_buffer]
  ["score1_undo" score1_undo]
  ["score2_undo" score2_undo]
  ["score3_undo" score3_undo]
  ["score4_undo" score4_undo]
  ["score5_undo" score5_undo]
  ["score6_undo" score6_undo]
  ["score7_undo" score7_undo]
  ["score8_undo" score8_undo]
  ["n_score_active_undo" n_score_active_undo]
  ["n_score1_undo" n_score1_undo]
  ["n_score2_undo" n_score2_undo]
  ["n_score3_undo" n_score3_undo]
  ["n_score4_undo" n_score4_undo]
  ["n_score5_undo" n_score5_undo]
  ["n_score6_undo" n_score6_undo]
  ["n_score7_undo" n_score7_undo]
  ["n_score8_undo" n_score8_undo]
  ["cc1_undo" cc1_undo]
  ["cc2_undo" cc2_undo]
  ["cc3_undo" cc3_undo]
  ["cc4_undo" cc4_undo]
  ["cc5_undo" cc5_undo]
  ["cc6_undo" cc6_undo]
  ["cc7_undo" cc7_undo]
  ["cc8_undo" cc8_undo]
  ["n_cc_active_undo" n_cc_active_undo]
  ["n_cc1_undo" n_cc1_undo]
  ["n_cc2_undo" n_cc2_undo]
  ["n_cc3_undo" n_cc3_undo]
  ["n_cc4_undo" n_cc4_undo]
  ["n_cc5_undo" n_cc5_undo]
  ["n_cc6_undo" n_cc6_undo]
  ["n_cc7_undo" n_cc7_undo]
  ["n_cc8_undo" n_cc8_undo]
  ["active_scores" active_scores]
  ["active_score" active_score]
  ["active_ccs" active_ccs]
  ["active_cc" active_cc] 
  ["active_scores_n" active_scores_n]
  ["active_ccs_n" active_ccs_n]
  ["auto_play" auto_play]
  ]))
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
