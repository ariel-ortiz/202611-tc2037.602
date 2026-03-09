(ns simples-examples)

(defn $map
  [fn s]
  (if (empty? s)
    ()
    (cons (fn (first s))
          ($map fn (rest s)))))

($map #(* 2 %) [4 1 2 3 0 -10])

(defn add
  [number]
  (fn [x] (+ x number)))

(def f (add 5))
(f 10)
(def g (add 20))
(g 6)
((add 50) 1)

(defn h
  [a b c d e]
  (* a (+ b c (- d e))))

(h 2 3 4 5 6)

(defn h-curry
  [a]
  (fn [b]
    (fn [c]
      (fn [d]
        (fn [e]
          (* a (+ b c (- d e))))))))

(((((h-curry 2) 3) 4) 5) 6)

(defn add-all
  [& params]
  (if (empty? params)
    0
    (+ (first params) (apply add-all (rest params)))))

(add-all)
(add-all 1 2 3 4 5)

(defn composite
  [f g]
  (fn [x] (f (g x))))

(defn f1 [x] (* 3 x))
(defn f2 [x] (+ x 5))
(def f3 (composite f1 f2))
(def f4 (composite f2 f1))
(def f5 (composite f3 f4))

(f1 1)
(f2 1)
(f3 1)
(f4 1)
(f5 1)
