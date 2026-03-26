(ns parallelism)

(defn bits
  [x]
  (.bitCount (biginteger x)))

(defn fact-seq
  [n]
  (loop [i 1
         r 1]
    (if (> i n)
      (bits r)
      (recur (inc i)
             (*' r i)))))

(defn fact-partial
  [[start end]]
  (loop [i start
         r 1]
    (if (= i end)
      r
      (recur (inc i)
             (*' r i)))))

(defn fact-ranges
  [n p]
  (partition 2
             1
             (concat (range 1 n (quot n p)) [(inc n)])))

(defn fact-par
  [n]
  (let [p (.availableProcessors (Runtime/getRuntime))]
    (bits (reduce *'
                  (pmap fact-partial
                        (fact-ranges n p))))))

(def n 150000)
(time (fact-seq n))
(time (fact-par n))
