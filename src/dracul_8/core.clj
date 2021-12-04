(ns dracul-8.core
  (:gen-class)
  (:require [seesaw.core :refer [canvas alert listen frame pack! show!]]
            [dynne.sampled-sound :refer [play square-wave trim]]
            [clojure.java.io :refer [input-stream]]
            [clojure.pprint :refer [cl-format]]))

;; Longest beep: 4250 milliseconds

(defn byte-to-nibbles [byte]
  [(-> byte (bit-and 2r11110000) (bit-shift-right 4))
   (-> byte (bit-and 2r1111))])

(defn bytes-to-ops [bytes]
  (partition 4 (mapcat byte-to-nibbles bytes)))

(defn read-bytes-from-file [file-path]
  (with-open [in (input-stream file-path)]
    (loop [bytes []]
      (let [byte (.read in)]
        (if (= byte -1)
          bytes
          (recur (conj bytes byte)))))))

(defn print-ops [ops]
  (loop [[[n0 n1 n2 n3] & rest] ops
         addr 0x200]
    (cl-format true "~3,'0X: ~1X~1X~1X~1X~%" addr n0 n1 n2 n3)
    (when (not (nil? rest))
      (recur rest (+ addr 2)))))

(defn -main
  "Start the GUI"
  [& args]
  (if (nil? args)
    (println "Please provide a path to a ROM file.")
    (let [file-path (first args)
          file-bytes (read-bytes-from-file file-path)]
      (print-ops (bytes-to-ops file-bytes))
      (println "Running ROM:" file-path)
      (let [screen-canvas (canvas :id :canvas
                                  :background "#222222"
                                  :paint nil)
            frame (frame :title "DRACUL-8"
                         :width 512
                         :height 256
                         :on-close :exit
                         :content screen-canvas)
            beep (square-wave 0.5 110.0)]
        (listen frame :key-pressed (fn [key]
                                     (play beep 44100)
                                     (println (.toString key))))
        (show! frame)))))