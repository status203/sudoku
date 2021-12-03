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

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


