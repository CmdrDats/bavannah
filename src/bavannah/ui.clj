(ns bavannah.ui
  (:use [quil.core]))

(defn hexagon [[x y] size]
  (let [c size
        a (/ c 2)
        b (* (sin (radians 60)) c)]
    (begin-shape)
    (let [vertex #(vertex (- (+ x %1) size) (- (+ y %2) size))]
      (vertex 0 b)
      (vertex a 0)
      (vertex (+ a c) 0)
      (vertex (* 2 c) b)
      (vertex (+ a c) (* 2 b))
      (vertex a (* 2 b))
      (vertex 0 b))
    (end-shape)
    )
  )

(defn setup []
  (smooth)                         
  (frame-rate 5)                   
  (background 200)
  (set-state! :mouse-position (atom [0 0])))

(defn cell-state [state position]
  (get (:map state) position 0))

(def gamestate (atom {:map {}}))

(defn draw-empty-cell [[x y] size]
  (stroke-weight 2)
  (fill 128 56 56 128)
  (hexagon [x y] size))

(defn draw-filled-cell [[x y] size]
  (stroke-weight 2)
  (fill 56 128 56 128)
  (hexagon [x y] size)
  (stroke-weight 0.5)
  (fill 255 255 255 128)
  (ellipse x y size size))

(defn draw []
  (background-float 196)
  (let [size 20
        a (/ size 2)
        b (* (sin (radians 60)) size)]
    (doseq [x (range 9)
            y (range 9)]
      (let [cell (cell-state state [x y])
            posx (+ 300 (- (* x (+ a size)) (* y (+ size a))))
            posy (+ 100 (+ (* x b) (* y b)))]
        (cond
         (= cell 0)
         (draw-empty-cell [posx posy] size)

         (= cell 1)
         (draw-filled-cell [posx posy] size)))
      )

    (let [[x y] @(state :mouse-position)]
      (stroke-weight 4)
      (ellipse x y 4 4)

      
      )))


(defn mouse-moved []
  (let [x (mouse-x)  y (mouse-y)]
    (reset! (state :mouse-position) [x y])))

(defsketch example
  :title "Bavannah"
  :setup setup
  :draw draw
  :mouse-moved mouse-moved
  :size [600 600])

;; {map :map :as state