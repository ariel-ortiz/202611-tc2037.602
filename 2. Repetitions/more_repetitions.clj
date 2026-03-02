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

(run-tests)
