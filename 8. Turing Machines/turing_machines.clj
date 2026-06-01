(ns turing-machines
  (:require [clojure.test :refer [deftest is run-tests]])
  (:import (java.io Writer)))

(defrecord TM [initial-state accept-states transitions])

(defrecord Tape [left head right]
  Object
  (toString [_] (format "%s[%s]%s" left head right)))

(defmethod print-method Tape
  [self ^Writer writer]
  (.write writer (str self)))

(defn make-tape
  ([s]
   (let [result (drop-while #(= % \_) s)]
     (make-tape "" (if (empty? result) \_ (first result)) (rest result))))
  ([left head right]
   (let [new-left (drop-while #(= % \_) left)
         new-right (reverse (drop-while #(= % \_) (reverse right)))]
     (->Tape (apply str new-left)
             head
             (apply str new-right)))))

(defn write-tape
  [{:keys [left right]} value]
  (make-tape left value right))

(defn shift-head
  [{:keys [left head right]} direction]
  (case direction
    :left (make-tape (or (butlast left) ())
                     (or (last left) \_)
                     (str head right))
    :right (make-tape (str left head)
                      (or (first right) \_)
                      (rest right))
    (throw (ex-info (str "Bad direction: " direction) {}))))

(defn accepts
  [{:keys [initial-state accept-states transitions]} input]
  (loop [tape (make-tape input)
         current-state initial-state]
    (if (contains? accept-states current-state)
      (str tape)
      (if-let [[write-symbol direction new-state]
               ((transitions current-state) (.head tape))]
        (recur (shift-head (write-tape tape write-symbol) direction)
               new-state)
        nil))))

;;; Problem 1
(def tm-1 (->TM :q0
                #{:q2}
                {:q0 {\a [\a :right :q1]
                      \_ [\_ :left :q2]}
                 :q1 {\a [\a :right :q0]}}))

(deftest test-problem1
  (is (= "[_]"
         (accepts tm-1 "")))
  (is (= "a[a]"
         (accepts tm-1 "aa")))
  (is (= "aaaaaaa[a]"
         (accepts tm-1 "aaaaaaaa")))
  (is (= "aaaaaaaaaaaaaaaaaaaaaaaaa[a]"
         (accepts tm-1 "aaaaaaaaaaaaaaaaaaaaaaaaaa")))
  (is (nil? (accepts tm-1 "a")))
  (is (nil? (accepts tm-1 "aaa")))
  (is (nil? (accepts tm-1 "aaaaaaa")))
  (is (nil? (accepts tm-1 "aaaaaaaaaaaaaaaaaaaaaaaaa"))))

;;; Problem 2
(def tm-2 (->TM :q0
                #{:q2}
                {:q0 {\0 [\0 :right :q1]
                      \1 [\1 :right :q3]}
                 :q1 {\1 [\1 :right :q1]
                      \_ [\_ :left :q2]}
                 :q3 {\0 [\0 :right :q3]
                      \_ [\_ :left :q2]}}))

(deftest test-problem2
  (is (= "[0]"
         (accepts tm-2 "0")))
  (is (= "[1]"
         (accepts tm-2 "1")))
  (is (= "1[0]"
         (accepts tm-2 "10")))
  (is (= "0111111111[1]"
         (accepts tm-2 "01111111111")))
  (is (nil? (accepts tm-2 "")))
  (is (nil? (accepts tm-2 "00")))
  (is (nil? (accepts tm-2 "100000000001")))
  (is (nil? (accepts tm-2 "10011010100101011"))))

(run-tests)
