(ns sudoku.domain.grid)

(def full-set (set (take 9 (iterate inc 1))))

(defn prepare-grid
  "Takes a 1D vector of integers (initally set values) and nils (empty cells) 
   representing an initial sudoku grid and replaces all nils with a full set 
   representing possible entries."
  [grid]
  (map #(or % full-set) grid))


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

(defn split-grid
  "Takes a grid and converts it to a map of two grids :definite & :possible
   with nils populating the gaps in each"
  [grid]
  (reduce (fn [acc cell]
            (let [possible (if (set? cell) cell nil)
                  definite (if possible nil cell)]
              (-> acc
                  (update-in [:definite] conj definite)
                  (update-in [:possible] conj possible))))
          {:definite [] :possible []}
          grid))

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
  (def peasy1 (prepare-grid easy1))
  (partition 9 peasy1)
  (split-grid peasy1)
  )

