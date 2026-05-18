(ns instaparse-example
  (:require [instaparse.core :refer [parser]])
  (:import (instaparse.gll Failure)))

(defn fails? [r] (instance? Failure r))
(defn succeeds? [r] (not (fails? r)))

(def example (parser "

  A = 'a' A 'b'
      | epsilon

"))

(succeeds? (example "aaabbb"))
(succeeds? (example "aaabb"))
(succeeds? (example ""))
(succeeds? (example "bbbaaa"))
