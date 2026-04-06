(ns parallelism)

; Problem #1
;
; Runtime Analysis:
; Run #  T1  T24
;  1      3253.069659  302.64424
;  2      3297.527167  300.976599
;  3      3258.986865  337.120649
;  4      3250.503966  298.626312
;  5      3265.374134  324.713799
; Average 3265.092358  312.8163198
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

(def n 150000)
(time (fact-seq n))
(time (fact-par n))
