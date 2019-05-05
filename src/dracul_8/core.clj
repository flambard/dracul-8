(ns dracul-8.core
  (:gen-class)
  (:require [seesaw.core :refer [canvas alert listen frame pack! show!]]))


(defn -main
  "Start the GUI"
  [& args]
  (let [screen-canvas (canvas :id :canvas
                              :background "#222222"
                              :paint nil)
        frame (frame :title "DRACUL-8"
                     :width 512
                     :height 256
                     :on-close :exit
                     :content screen-canvas)]
    (listen frame :key-pressed (fn [key] (println (.toString key))))
    (show! frame)))
