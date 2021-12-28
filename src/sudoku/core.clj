(ns sudoku.core
  (:gen-class)
  (:require
   [cli-matic.core :refer [run-cmd]]
   [clojure.string :as str]))

(defn display-board
 "Displays a board"
 [board]
 (prn (partition 9 board)))

(defn parse-entry
  "Given a single entry from a board file returns either nil for an unset entry
   or an int for a set entry"
  [entry] (if (= entry "_") nil (Integer/parseInt entry)))

(defn parse-board
  "Takes a string containing a (non-clojure) sequence of whitespace separated 
   board entries with unset entries represented by '_' (see the 'boards' folder 
   for examples) and returns a vector of those entries with unset entries 
   represented by nil"
  [raw-board]
  (let [entries (str/split raw-board #"\s+")]
    (map parse-entry entries)))

(defn solve-board
 "Takes an unprocessed board, parses it and solves it (hopefully :|)"
 [{:keys [:board]}]
 (let [parsed-board (parse-board board)]
   (display-board parsed-board)))

(def cli-options
  {:command "sudoku"
   :description "A sudoku solver"
   :opts [{:option "board"
           :short 0
           :as "The filename of a board to solve"
           :type :slurp}]
   :runs solve-board})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run-cmd args cli-options))

