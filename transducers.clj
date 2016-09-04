(ns transducers.basic)

(defn look [x] (prn x) x)

(defn my-map
  ([f coll]
   (my-map [] f coll))
  ([result f coll]
   (if (seq coll)
    (apply-map (conj result (-> coll first f))
               f
               (rest coll))
    result)))

(my-map inc [1 2 3])

(my-map inc [])
