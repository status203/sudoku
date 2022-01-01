(ns sudoku.domain.grid
  (:require [clojure.string :as str]))

(def full-set (set (take 9 (iterate inc 1))))

(defn parse-grid
  "Takes a string containing a (non-clojure) sequence of whitespace separated 
   board entries with unset entries represented by '_' (see the 'boards' folder 
   for examples) and returns a vector of those entries with unset entries 
   represented by a full set of possibilities"
  [raw-board]
  (let [entries (str/split raw-board #"\s+")]
    (map #(if (= % "_") full-set (Integer/parseInt %))
         entries)))

(defn cell->row-index
  "Takes a 1D cell location and returns the 0-based row index of that cell"
  [cell]
  (quot cell 9))

(defn cell->col-index
  "Takes a 1D cell location and returns the 0-based column index of that cell"
  [cell]
  (rem cell 9))

(defn cell->subgrid-idxs
  "Takes a 1D cell location and returns the 0-based indices of [row column] of
   the cell"
  [cell]
  (let [row-idx (cell->row-index cell)
        col-idx (cell->col-index cell)]
    [(quot row-idx 3) (quot col-idx 3)]))

(defn cell->row
  "Takes a grid and a 1D cell location and returns the row containing that cell"
  [grid cell]
  (let [idx (cell->row-index cell)]
    (take 9 (drop (* 9 idx) grid))))

(flatten [[1 [2 3 [] [4] 5] 6 7]])

(defn cell->col
  "Takes a grid and a 1D cell location and returns the column containing that cell"
  [grid cell]
  (let [idx (cell->col-index cell)]
    (->> (partition 9 grid)
         (map #(first (drop idx %))))))

(defn cell->subgrid
  "Takes a grid and a 1D cell location and returns the subgrid containing that cell"
  [grid cell]
  (let [[sg-row-idx sg-col-idx] (cell->subgrid-idxs cell)]
    (->> grid
         (partition 9)
         (drop (* 3 sg-row-idx))
         (take 3)
         (map #(->> %
                    (drop (* 3 sg-col-idx))
                    (take 3)))
         flatten)))

(def all-refs
 (->> (concat (range (int \a) (inc (int \z)))
              (range (int \A) (inc (int \Z)))
              (range (int \0) (inc (int \9)))
              [\@ \#])
      (map char)))

(defn prepare-print-grid
  "Returns a map of
     :grid - vector of ints for definites and references (chars) for non-definites
     :refs - vector of maps [{:ref :possibilities}*]"
  [grid]
  (loop [[cell & rest-grid] grid 
         remaining-refs all-refs
         printable-grid []
         printable-refs []]
    (cond
      (nil? cell) {:grid printable-grid :refs printable-refs}
      (int? cell) (recur rest-grid 
                         remaining-refs
                         (conj printable-grid cell)
                         printable-refs)
      :else (let [ref (first remaining-refs)]
              (recur rest-grid
                     (rest remaining-refs)
                     (conj printable-grid ref)
                     (conj printable-refs {:ref ref :possibilities cell}))))))

(def vertical-separator " | ")
(def horizontal-separator (apply str (repeat 29 \-)))

(defn row->string
  "Takes a row and prints it"
  [row]
  (->> row
       (partition 3)
       (interpose :separator)
       flatten
       (map #(cond (int? %) (str \space % \space)
                   (char? %) (str \[ % \])
                   :else "|"))
       (apply str)))

(comment
  (row->string [1 2 3 \a 5 \b \c 8 9])
  )

(defn print-grid
  "Takes a grid and outputs it - definites output in the grid and possibiles
   referenced and displayed below"
  [grid]
  (println
   (apply str
    (let [{:keys [:grid :refs]} (prepare-print-grid grid)]
      [(->> (partition 9 grid)
            (map row->string)
            (partition 3)
            (interpose horizontal-separator)
            flatten
            (interpose \newline)
            (apply str))
       \newline
       \newline
       (->> refs
            (map #(str (:ref %) ": " (:possibilities %) \newline))
            (apply str))]))))

(comment
  (def easy1
    (let [_ nil]
      [6 7 _   _ 3 8   4 2 9
       3 1 4   _ 9 5   _ 8 6
       _ _ 2   _ 4 7   _ _ _

       _ _ 1   _ 6 _   _ _ _
       _ 3 _   8 _ _   5 _ 4
       _ _ _   3 2 _   _ 6 7

       5 6 _   _ 8 _   _ _ _
       _ _ _   4 _ _   _ 5 _
       1 _ 8   _ _ _   6 _ 2]))
  (def peasy1 (parse-grid easy1))
  
  (prepare-print-grid peasy1)
  (print-grid peasy1)
  (print-grid (concat (take 80 peasy1) [#{1 2 3}]))
  )

