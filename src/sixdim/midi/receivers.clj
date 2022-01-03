(ns sixdim.midi.receivers
  (:import
    (java.util.regex Pattern)
    (javax.sound.midi Sequencer Synthesizer
                      MidiSystem MidiDevice Receiver Transmitter MidiEvent
                      MidiMessage ShortMessage SysexMessage
                      InvalidMidiDataException MidiUnavailableException
                      MidiDevice$Info)
    (javax.swing JFrame JScrollPane JList
                 DefaultListModel ListSelectionModel)
    (java.awt.event MouseAdapter)
    (java.util.concurrent FutureTask ScheduledThreadPoolExecutor TimeUnit)) 
  (:use overtone.core)
  (:gen-class))

(defn- with-receiver
  "copied from 
   https://github.com/overtone/overtone/blob/master/src/overtone/midi.clj
   Add a midi receiver to the sink device info. This is a connection
   from which the MIDI device will receive MIDI data"
  [sink-info]
  (let [^MidiDevice dev (:device sink-info)]
    (if (not (.isOpen dev))
      (.open dev))
    (assoc sink-info :receiver (.getReceiver dev))))

(def midi-out-virtualport  
  (with-receiver 
    (overtone.midi/midi-find-device 
      (overtone.midi/midi-sinks) "virtualport")))


