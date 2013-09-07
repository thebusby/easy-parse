(ns easy-parse
  "A simple library to assist with parsing Documents, especially XML."
  )

(declare easy-parse)

(defprotocol ParseElems
  (parse-element [expr tree]))
(extend-protocol ParseElems

  clojure.lang.IFn
  (parse-element [query tree]
    (query tree))

  clojure.lang.PersistentVector
  (parse-element [[query children] tree]
    (mapv #(easy-parse %1 children)
          (query tree))))

(defn easy-parse [tree expr]
  "Parses a tree, and returns the requested fields into the provided map structure."
  (->> expr
       (mapv (fn [[k v]]
               (vector k (parse-element v tree))))
       (filter (fn [[k v]] (if (vector? v) (seq v) v)))
       (into {})))
