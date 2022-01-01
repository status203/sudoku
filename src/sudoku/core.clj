(ns sudoku.core
  (:gen-class)
  (:require
   [cli-matic.core :refer [run-cmd]]
   [sudoku.domain.grid :as grid]))

(defn display-board
 "Displays a board"
 [board]
 (prn (partition 9 board)))

(defn parse-entry
  "Given a single entry from a board file returns either nil for an unset entry
   or an int for a set entry"
  [entry] (if (= entry "_") nil (Integer/parseInt entry)))

(defn solve-board
 "Takes an unprocessed board, parses it and solves it (hopefully :|)"
 [{:keys [:board]}]
 (let [parsed-board (grid/parse-grid board)]
   (grid/print-grid parsed-board)))

(def cli-options
  {:command "sudoku"
   :description "A sudoku solver"
   :opts [{:option "board"
           :short 0
           :as "The filename of a board to solve"
           :type :slurp}]
   :runs solve-board})

(defn -main
  "Load & solve board"
  [& args]
  (run-cmd args cli-options))

