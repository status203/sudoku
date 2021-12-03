(ns sudoku.core
  (:gen-class))

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

(def full-set (set (take 9 (iterate inc 1))))

(defn prepare-grid
  "Takes a 1D vector of integers (set values) and nils (empty cells) 
   representing an initial sudoku grid and replaces all nils with a full set 
   representing possible entries."
  [grid]
  (map #(or % full-set) grid))

(comment
  (partition 9 (prepare-grid easy1))
  )

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


