(ns learning-clojure.chap-3
  (:require [clojure.set :refer :all]
            [clojure.repl :refer :all]))

;; Documentation on 'conj'
(doc conj)

(def v [1 2 3])
(conj v 4)
(conj v 4 5)
(seq v)

(def m {:a 5 :b 6})
(conj m [:c 7])
(conj m [:a 1]) ;; override the value
(apply (partial conj m) [[:c 7] [:d 0]]) ;; using apply and partial with 'conj'
;; sequence of a map returns a vector of it's key value pairs.
(seq (apply (partial conj m) [[:c 7] [:d 0]]))
;; What about a map inside a map
(conj m [:e {:c 7 :d 0}])
(seq (conj m [:e {:c 7 :d 0}])) ;; weird!

;; into
(doc into)
(into v [4 5])


;;Working with 'empty'
(defn swap-pairs [sequential]
  (into (empty sequential)
        (interleave (take-nth 2 (rest sequential))
                    (take-nth 2 sequential))))

(swap-pairs (range 10))
(swap-pairs (into [] (range 10)))

;; Difference between 'rest' and 'next' is 'next' returns 'nil' if no items after the first value; 'rest' returns and empty seq.
(doc next)
(doc rest)
