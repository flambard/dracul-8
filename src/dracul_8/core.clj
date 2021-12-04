(ns dracul-8.core
  (:gen-class)
  (:require [seesaw.core :refer [canvas alert listen frame pack! show!]]
            [dynne.sampled-sound :refer [play square-wave trim]]))

;; Longest beep: 4250 milliseconds


(defn -main
  "Start the GUI"
  [& args]
  (if (nil? args)
    (println "Please provide a path to a ROM file.")
    (let [file-path (first args)
          screen-canvas (canvas :id :canvas
                                :background "#222222"
                                :paint nil)
          frame (frame :title "DRACUL-8"
                       :width 512
                       :height 256
                       :on-close :exit
                       :content screen-canvas)
          beep (square-wave 0.5 110.0)]
      (println "Running ROM:" file-path)
      (listen frame :key-pressed (fn [key]
                                   (play beep 44100)
                                   (println (.toString key))))
      (show! frame))))
