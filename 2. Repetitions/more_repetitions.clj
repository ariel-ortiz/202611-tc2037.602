(ns more-repetitions
  (:require [clojure.test :refer [deftest is run-tests]]))

; Problem 1
(defn expand
  [s]
  (mapcat repeat
          (range 1 (inc (count s)))
          s))

; Problema 2

; Recursive solution
;(defn insert
;  [n s]
;  (cond
;    (empty? s)       (list n)
;    (<= n (first s)) (cons n s)
;    :else            (cons (first s)
;                           (insert n (rest s)))))

; loop/recur solution
;(defn insert
;  [n s]
;  (loop [s s
;         result []]
;    (if (or (empty? s) (<= n (first s)))
;      (concat result [n] s)
;      (recur (rest s)
;             (conj result (first s))))))

; Sequence API solution
(defn insert
  [n s]
  (let [split-result (split-with #(< % n) s)]
    (concat (first split-result)
            [n]
            (second split-result))))

; Problem 3

(defn insertion-sort
  [s]
  (reduce (fn [accum x] (insert x accum)) () s))

; Problem 5

; Recursive solution
(defn binary-aux
  [n]
  (if (zero? n)
    ()
    (cons (rem n 2)
          (binary-aux (quot n 2)))))

(defn binary
  [n]
  (reverse (binary-aux n)))

(binary 11)

(deftest test-expand
  (is (= () (expand ())))
  (is (= '(a) (expand '(a))))
  (is (= '(1 2 2 3 3 3 4 4 4 4) (expand '(1 2 3 4))))
  (is (= '(a b b c c c d d d d e e e e e)
         (expand '(a b c d e)))))

(deftest test-insert
  (is (= '(14) (insert 14 ())))
  (is (= '(4 5 6 7 8) (insert 4 '(5 6 7 8))))
  (is (= '(1 3 5 6 7 9 16) (insert 5 '(1 3 6 7 9 16))))
  (is (= '(1 5 6 10) (insert 10 '(1 5 6)))))

(deftest test-insertion-sort
  (is (= () (insertion-sort ())))
  (is (= '(0 1 3 3 4 6 7 8 9)
         (insertion-sort '(4 3 6 8 3 0 9 1 7))))
  (is (= '(1 2 3 4 5 6) (insertion-sort '(1 2 3 4 5 6))))
  (is (= '(1 5 5 5 5 5 5) (insertion-sort '(5 5 5 1 5 5 5)))))

(deftest test-binary
  (is (= () (binary 0)))
  (is (= '(1 1 1 1 0) (binary 30)))
  (is (= '(1 0 1 1 0 0 0 0 0 1 0 0 0 0 1 1) (binary 45123))))

(run-tests)
