(ns parallelism)

; Problem 1  n = 150000
; Run #    T1           T24
; 1        3253.069659  302.64424
; 2        3297.527167  300.976599
; 3        3258.986865  337.120649
; 4        3250.503966  298.626312
; 5        3265.374134  324.713799
; Average  3265.092358  312.8163198
;
; S24:  10.43773023

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

; (def n 150000)
; (time (fact-seq n))
; (time (fact-par n))

; Problem 4  n = 800000
; Run #    T1           T24
; 1        4523.082846  1084.900641
; 2        4478.193253  907.292667
; 3        4519.969959  920.617177
; 4        4402.29829   942.490745
; 5        4425.408484  942.619227
; Average  4469.790566  959.5840914
;
; S24:  4.658049885

(defn create-random-data
  [n]
  (repeatedly n #(rand-int 1000)))

; (create-random-data 100)

(defn insertion-sort
  [s]
  (loop [s s
         r ()]
    (if (empty? s)
      r
      (let [x              (first s)
            [before after] (split-with #(< % x) r)]
        (recur (rest s)
               (concat before [x] after))))))

;(apply <= (insertion-sort (create-random-data 1000)))

(defn merge-algorithm
  [a b]
  (loop [a a
         b b
         r []]
    (cond
      (empty? a)
      (concat r b)

      (empty? b)
      (concat r a)

      (< (first a) (first b))
      (recur (rest a)
             b
             (conj r (first a)))

      :else
      (recur a
             (rest b)
             (conj r (first b))))))

; (merge-algorithm [1 4 6 9] [2 3 5 7 8 10])

(defn hybrid-sort-seq
  [s]
  (if (< (count s) 100)
    (insertion-sort s)
    (let [[a b] (split-at (quot (count s) 2) s)]
      (merge-algorithm (hybrid-sort-seq a)
                       (hybrid-sort-seq b)))))

(defn hybrid-sort-par
  [s]
  (if (< (count s) 100)
    (insertion-sort s)
    (let [splitted (split-at (quot (count s) 2) s)]
      (apply merge-algorithm (pmap hybrid-sort-par splitted)))))

; (def n 800000)
; (time (apply <= (hybrid-sort-seq (create-random-data n))))
; (time (apply <= (hybrid-sort-par (create-random-data n))))
