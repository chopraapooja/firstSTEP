(ns transducers.basic)

(defn look [x] (prn x) x)

;;---------------------------------------------
;; BASIC VERSION
;; Map with append recursion

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


;;---------------------------------------------
;; Map with prepend recursion

(defn my-map [f coll]
  (if (seq coll)
    (cons (-> coll first f) (my-map f (rest coll)))
    []))

(my-map inc [1 2 3])

(my-map inc [])

(my-map inc nil)

;;---------------------------------------------
;; my-map as reduce function

(defn my-map-reducer-with-inc [result el]
  (conj result (inc el)))

(reduce my-map-reducer-with-inc [] [1 2 3])

(defn my-map-reducer [f]
  (fn [result el] (conj result (f el))))

(reduce (my-map-reducer dec) [] [1 2 3])

;;---------------------------------------------
;; filter as reduce function

(defn my-filter-reducer [f]
  (fn [result el] (if (f el)
                    (conj result el)
                    result)))

(reduce (my-filter-reducer even?) [] (range 10))

;;---------------------------------------------
;; more generic filter map
(defn my-map-reducer [f]
  (fn [reducer]
    (fn [result el] (reducer result (f el)))))

(defn my-filter-reducer [f]
  (fn [reducer]
    (fn [result el] (if (f el)
                    (reducer result el)
                    result))))

(reduce ((my-map-reducer dec) conj) [] [1 2 3])
(reduce ((my-map-reducer dec) +) 0 [1 2 3])

(reduce ((my-filter-reducer even?) conj) [] (range 10))
(reduce ((my-filter-reducer even?) +) 0 (range 10))

;;---------------------------------------------
;;
;; reducing function
;; operation
;; powerful reducing function (because it performs operation while reducing)

(def powerful-reducing-function ((my-filter-reducer even?) conj))
(powerful-reducing-function [] 4)
(powerful-reducing-function [2] 1)

;; reducing funcions are composable
(def powerful-reducing-function ((my-filter-reducer even?) conj))
(def super-powerful-reducing-function ((my-map-reducer inc) powerful-reducing-function))

(reduce powerful-reducing-function [] (range 10))
(reduce super-powerful-reducing-function [] (range 10))
