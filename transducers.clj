(ns transducers.basic)

(defn look [x] (prn x) x)

(defn- apply-map [result f coll]
  (if (seq coll)
    (apply-map (conj result (-> coll first f))
               f
               (rest coll))
    result))

(defn my-map [f coll]
  (apply-map [] f coll))

(my-map inc [1 2 3])

(look 3)
