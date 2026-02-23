(ns repetitions
  (:require [clojure.test :refer [deftest is run-tests]])
  (:require [clojure.math.numeric-tower :refer [sqrt]])
  (:require [clojure.algo.generic.math-functions
             :refer [sqr approx=]]))

; Problem 1

; Recursive version
;(defn enlist
;  [s]
;  (if (empty? s)
;    ()
;    (cons (list (first s))
;          (enlist (rest s)))))

; loop/recur version
;(defn enlist
;  [s]
;  (loop [s s
;         result []]
;    (if (empty? s)
;      (seq result)
;      (recur (rest s)
;             (conj result
;                   (list (first s)))))))

(defn enlist
  [s]
  (map list s))

; Problem 2
(defn positives
  [s]
  (filter pos? s))

; Problem 3
(defn add-squares
  [s]
  (reduce + (map sqr s)))

; Problem 4

; Recursive solution
;(defn duplicate
;  [s]
;  (if (empty? s)
;    ()
;    (cons (first s)
;          (cons (first s)
;                (duplicate (rest s))))))

; loop/recur solution
(defn duplicate
  [s]
  (loop [s s
         result []]
    (if (empty? s)
      (seq result)
      (recur (rest s)
             (conj (conj result
                         (first s))
                   (first s))))))

(deftest test-enlist
  (is (= () (enlist ())))
  (is (= '((a) (b) (c)) (enlist '(a b c))))
  (is (= '((1) (2) (3) (4)) (enlist [1 2 3 4])))
  (is (= '(((1 2 3)) (4) ((5)) (7) (8))
         (enlist '((1 2 3) 4 (5) 7 8)))))

(deftest test-positives
  (is (= () (positives ())))
  (is (= () (positives [-4 -1 -10 -13 -5])))
  (is (= [3 6] (positives [-4 3 -1 -10 -13 6 -5])))
  (is (= [4 3 1 10 13 6 5] (positives [4 3 1 10 13 6 5]))))

(deftest test-add-squares
  (is (= 0 (add-squares [])))
  (is (= 25 (add-squares [5])))
  (is (= 30 (add-squares [2 4 1 3])))
  (is (= 385 (add-squares [1 2 3 4 5 6 7 8 9 10]))))

(deftest test-duplicate
  (is (= [1 1 2 2 3 3 4 4 5 5]
         (duplicate [1 2 3 4 5])))
  (is (= ()
         (duplicate ())))
  (is (= '(a a)
         (duplicate '(a))))
  (is (= '(a a b b c c d d e e f f g g h h)
         (duplicate '(a b c d e f g h)))))


(run-tests)
