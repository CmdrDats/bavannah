(ns bavannah.state)

(defn initialize [size players]
  {:map
   (zipmap
    (for [x (range (dec (* 2 size)))
          y (range (max (inc (- x size)) 0) (min (+ x size) (dec (* 2 size))))]
      [x, y])
    (repeat 0))
   :size size
   :players (cycle players)})


(defn place [{map :map size :size players :players :as state} position]
  (if (= 0 (map position))
    {:map (assoc map position 1)
     :size size
     :players (rest players)}
    state))