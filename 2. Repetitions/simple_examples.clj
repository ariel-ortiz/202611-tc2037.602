(ns simple-examples)

(defn fact-v1
  [n]
  (if (zero? n)
    1
    (*' n (fact-v1 (dec n)))))

(defn fact-v2
  [n]
  (loop [i 1
         result 1]
    (if (> i n)
      result
      (recur (inc i)
             (*' result i)))))

(defn fact-v3
  [n]
  (reduce *' (range 1 (inc n))))

(fact-v1 1000)
(fact-v2 0)
(fact-v2 1)
(fact-v2 5)
(fact-v2 1000)
(fact-v2 10000)
(fact-v3 0)
(fact-v3 1)
(fact-v3 5)
(fact-v3 1000)
(fact-v3 10000)
