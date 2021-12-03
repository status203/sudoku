(ns sudoku.domain-test
  (:require [clojure.test :refer [deftest is]]
            [sudoku.domain :as sut]))

(def small-grid (let [_ nil] [1 _
                              2 1]))

(deftest prepare-grid
  (is (= [1 sut/full-set
          2 1]
         (sut/prepare-grid small-grid))))