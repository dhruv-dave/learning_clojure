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

;; Functions which work with sequences call 'seq' implicitly.
(def astring "Hello World")
(map str astring)
;; Still seq-able
(map str (set astring))  ;; -> Proves 'set' implicitly uses 'seq'
;; Still works
(map str (into [] astring))
;; And still does
(map str (seq astring))


;; Creating lazy-sequences.
;; Create a lazy-sequence with an upper bound limit of 'limit'
(defn lazy-limit
  [limit]
  (lazy-seq (cons (rand-int limit)
                  (lazy-limit limit))))

;; "For example, give me a list of first 20 numbers divisible by 2. (take 20 fn)"
;; lazy-seq is not needed since (range) is lazy. Figured it out after.
;; For the sake of using lazy-seq, I am going to assume that no one is going to
;; ask for 1,000,000 numbers divisible by 2
(defn nums-by-2
  []
  (lazy-seq (remove nil?
                    (for [k (range 1000000)]
                      (when (= 0 (mod k 2))
                        k)))))

;; Since we know range is lazy
(defn by-2
  []
  (remove nil?
          (map #(when (= 0 (mod % 2))
                  %)
               (range))))

;; Since we know map is lazy
(defn map-by-2
  []
  (remove nil?
          (map #(when (= 0 (mod % 2))
                  %)
               (range 1000))))
