(ns sudoku.domain)

(def full-set (set (take 9 (iterate inc 1))))

(defn prepare-grid
  "Takes a 1D vector of integers (set values) and nils (empty cells) 
   representing an initial sudoku grid and replaces all nils with a full set 
   representing possible entries."
  [grid]
  (map #(or % full-set) grid))


(comment
  )

