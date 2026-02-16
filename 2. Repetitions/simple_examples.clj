(ns simple-examples)

(defn !
  [n]
  (if (zero? n)
    1
    (*' n (! (dec n)))))

(! 0)
(! 2)
(! 3)
(! 4)
(! 5)
(! 20)
(! 30)
(! 100)
(! 1000)
; (! 10000)
