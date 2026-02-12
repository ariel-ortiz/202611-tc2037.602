(ns introductory
  (:require [clojure.test :refer [deftest is run-tests]])
  (:require [clojure.math.numeric-tower :refer [sqrt]]))

; Problem 1
(defn gibibytes->bytes
  "Convert gibibytes to the corresponding number of bytes."
  [gibibytes]
  (* gibibytes 1024 1024 1024))

(gibibytes->bytes 5)

(deftest test-gibibytes->bytes
  (is (= 0 (gibibytes->bytes 0)))
  (is (= 1073741824 (gibibytes->bytes 1)))
  (is (= 5368709120 (gibibytes->bytes 5)))
  (is (= 26415122612224 (gibibytes->bytes 24601))))

(run-tests)
