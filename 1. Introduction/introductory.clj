(ns introductory
  (:require [clojure.test :refer [deftest is run-tests]])
  (:require [clojure.math.numeric-tower :refer [sqrt]]))

; Problem 1
(defn gibibytes->bytes
  "Convert gibibytes to the corresponding number of bytes."
  [gibibytes]
  (* gibibytes 1024 1024 1024))

; Problem 2
(defn fahrenheit->celsius
  "Convert f degrees Fahrenheit to degrees Celsius."
  [f]
  (/ (* 5.0
        (- f 32.0))
     9.0))

; Probleam 3
(defn sign
  "Determines if n is positive (1), negative (-1) or zero (0)."
  [n]
  (if (neg? n)
    -1
    (if (pos? n)
      1
      0)))

(deftest test-gibibytes->bytes
  (is (= 0 (gibibytes->bytes 0)))
  (is (= 1073741824 (gibibytes->bytes 1)))
  (is (= 5368709120 (gibibytes->bytes 5)))
  (is (= 26415122612224 (gibibytes->bytes 24601))))

(deftest test-fahrenheit->celsius
  (is (= 100.0 (fahrenheit->celsius 212.0)))
  (is (= 0.0 (fahrenheit->celsius 32.0)))
  (is (= -40.0 (fahrenheit->celsius -40.0))))

(deftest test-sign
  (is (= -1 (sign -5)))
  (is (= 1 (sign 10)))
  (is (= 0 (sign 0))))

(run-tests)
