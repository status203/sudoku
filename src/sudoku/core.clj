(ns sudoku.core
  (:gen-class)
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(defn parse-entry
  "Given a single entry from a board file returns either nil for an unset entry
   or an int for a set entry"
  [entry] (if (= entry "_") nil (Integer/parseInt entry)))


(defn load-board
  "Takes a filename of a file containing a (non-clojure) sequence of whitespace
   separated board entries with unset entries represented by '_' (see the
   'boards' folder for examples) and returns a vector of those entries with 
   unset entries represented by nil"
  [filename]
  (let [raw (slurp filename)
        entries (str/split raw #"\s+")]
    (map parse-entry entries)))

(comment
  (str/split "a   b" #"\s+")
  (load-board "boards/easy1"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (prn (partition 9 (load-board (first args)))))

