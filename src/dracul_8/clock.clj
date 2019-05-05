(ns dracul-8.clock
  (:require [clojure.core.async :refer
             [<!! >!! alts!! close! sliding-buffer timeout chan thread]]))

;;; 6 ticks every 100 ms => 60 ticks per second (60 Hz)
(def ticks 6)
(def interval 100)

(def tick-channel
  (chan (sliding-buffer ticks)))

(def termination-channel
  (chan))

(defn start-clock
  [tick-channel termination-channel]
  (thread (loop [count 0]
            (let [[v c] (alts!! [termination-channel (timeout interval)])]
              (when-not (= c termination-channel)
                (dotimes [tick ticks]
                  (>!! tick-channel (+ count tick)))
                (recur (+ count ticks)))))))
