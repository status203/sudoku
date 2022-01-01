(ns sudoku.domain.grid-test
  (:require [clojure.test :refer [deftest is]]
            [sudoku.domain.grid :as sut]))

(def small-grid (let [_ sut/full-set] [1 _
                                       2 1]))

(defn rotate-left
  "Rotates a seq 1 left"
  [s] (concat (rest s) [(first s)]))

(def seqs-grid (flatten (take 9 (iterate rotate-left (range 9)))))

(comment 
  (partition 9 seqs-grid)
  ;; => ((0 1 2   3 4 5   6 7 8)
  ;;     (1 2 3   4 5 6   7 8 0)
  ;;     (2 3 4   5 6 7   8 0 1)
  ;;
  ;;     (3 4 5   6 7 8   0 1 2)
  ;;     (4 5 6   7 8 0   1 2 3)
  ;;     (5 6 7   8 0 1   2 3 4)
  ;;
  ;;     (6 7 8   0 1 2   3 4 5)
  ;;     (7 8 0   1 2 3   4 5 6)
  ;;     (8 0 1   2 3 4   5 6 7))
)

(deftest parse-grid-tests
  (is (= [1 sut/full-set
          2 1]
         (sut/parse-grid small-grid))))

(deftest cell->row-index-tests
  (is (= 0
         (sut/cell->row-index 0)
         (sut/cell->row-index 1)
         (sut/cell->row-index 8)))
  (is (= 1
         (sut/cell->row-index 9)
         (sut/cell->row-index 17)))
  (is (= 8
         (sut/cell->row-index 72)
         (sut/cell->row-index 80))))

(deftest cell->col-index-tests
  (is (= 0
         (sut/cell->col-index 0)
         (sut/cell->col-index 9)))
  (is (= 1
         (sut/cell->col-index 1)
         (sut/cell->col-index 10)))
  (is (= 8
         (sut/cell->col-index 8)
         (sut/cell->col-index 17))))

(deftest cell->subgrid-idxs-tests
  (is (= [0 0] 
         (sut/cell->subgrid-idxs 0)
         (sut/cell->subgrid-idxs 20)))
  (is (= [0 2] (sut/cell->subgrid-idxs 15)))
  (is (= [2 1] (sut/cell->subgrid-idxs 77))))

(deftest cell->row-tests
  (is (= [0 1 2 3 4 5 6 7 8]
         (sut/cell->row seqs-grid 0)
         (sut/cell->row seqs-grid 8)))
  (is (= [4 5 6 7 8 0 1 2 3] (sut/cell->row seqs-grid 40))))

(deftest cell->col-tests
  (is (= [0 1 2 3 4 5 6 7 8]
         (sut/cell->col seqs-grid 0)
         (sut/cell->col seqs-grid 9)))
  (is (= [4 5 6 7 8 0 1 2 3] (sut/cell->col seqs-grid 4))))

(deftest cell->subgrid-tests
  (is (= [0 1 2
          1 2 3
          2 3 4]
         (sut/cell->subgrid seqs-grid 0)
         (sut/cell->subgrid seqs-grid 1)
         (sut/cell->subgrid seqs-grid 20)
         (sut/cell->subgrid seqs-grid 44)
         (sut/cell->subgrid seqs-grid 77))))