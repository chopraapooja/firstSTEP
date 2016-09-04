(ns transducers.basic)

(defn look [x] (prn x) x)

;---------------------------------------------
;BASIC VERSION
;Map with append recursion

(defn my-map
  ([f coll]
   (my-map [] f coll))
  ([result f coll]
   (if (seq coll)
    (my-map (conj result (-> coll first f))
               f
               (rest coll))
    result)))

(my-map inc [1 2 3])

(my-map inc [])

(my-map inc nil)


;---------------------------------------------
;Map with prepend recursion

(defn my-map [f coll]
  (if (seq coll)
    (cons (-> coll first f) (my-map f (rest coll)))
    []))

(my-map inc [1 2 3])

(my-map inc [])

(my-map inc nil)
