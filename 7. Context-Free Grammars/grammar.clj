(ns grammar
  (:require [clojure.test :refer [deftest is run-tests]])
  (:require [instaparse.core :refer [parser]])
  (:import (instaparse.gll Failure)))

(defn fails? [r] (instance? Failure r))
(defn succeeds? [r] (not (fails? r)))

(def start-and-end (parser "

  S = '#'
    | '$'
    | '#' T '#'
    | '$' T '$'

  T = epsilon
    | '#' T
    | '$' T

"))

(deftest test-start-and-end
  (is (succeeds? (start-and-end "$")))
  (is (succeeds? (start-and-end "#")))
  (is (succeeds? (start-and-end "$$")))
  (is (succeeds? (start-and-end "##")))
  (is (succeeds? (start-and-end "$$$$$$$#$$#$$#$##$")))
  (is (succeeds? (start-and-end "#$$$#$$$$#$$$$#$####")))
  (is (fails? (start-and-end "")))
  (is (fails? (start-and-end "$#")))
  (is (fails? (start-and-end "#$")))
  (is (fails? (start-and-end "###$$#$#$$$#$$$####$")))
  (is (fails? (start-and-end "$#$#$#$$$$#$$$#$$$$#$$#")))
  (is (fails? (start-and-end "#######################$"))))

(run-tests)
