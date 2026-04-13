(ns automata
  (:require [clojure.test :refer [deftest is run-tests]]))

(defrecord DFA [initial-state
                accept-states
                transitions])

; Problem 1

(defn accepts?
  [{:keys [initial-state accept-states transitions]} input]
  (loop [input          input
         current-state  initial-state]
    (if (empty? input)
      (contains? accept-states current-state)
      (recur (rest input)
             ((transitions current-state) (first input))))))

(def dfa-1 (->DFA :q0
                  #{:q2}
                  {:q0 {\a :q1
                        \b :q0}
                   :q1 {\a :q1
                        \b :q2}
                   :q2 {\a :q2
                        \b :q2}}))

(deftest test-problem1
  (is (accepts? dfa-1 "ab"))
  (is (accepts? dfa-1 "abba"))
  (is (accepts? dfa-1 "aaab"))
  (is (accepts? dfa-1 "abbbbbbbbb"))
  (is (not (accepts? dfa-1 "")))
  (is (not (accepts? dfa-1 "a")))
  (is (not (accepts? dfa-1 "baa")))
  (is (not (accepts? dfa-1 "bbba"))))

; Problem 2

(def dfa-2 (->DFA :q0
                  #{:q2}
                  {:q0 {\0 :q1
                        \1 :q3}
                   :q1 {\0 :q1
                        \1 :q2}
                   :q2 {\0 :q1
                        \1 :q2}
                   :q3 {\0 :q3
                        \1 :q3}}))

(deftest test-problem2
  (is (accepts? dfa-2 "01"))
  (is (accepts? dfa-2 "0101"))
  (is (accepts? dfa-2 "01111"))
  (is (accepts? dfa-2 "000001"))
  (is (not (accepts? dfa-2 "")))
  (is (not (accepts? dfa-2 "00")))
  (is (not (accepts? dfa-2 "1001011")))
  (is (not (accepts? dfa-2 "1001010"))))

(run-tests)
