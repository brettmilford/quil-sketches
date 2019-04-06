(ns quil-sketches.181218
  (:require [quil.core :refer :all :as q]
            [clojure.tools.logging :as log]))

(def title "181218")
(def fr 1)

;scalling
(defn h
  ([] (h 1.0)) ([value] (* (height) value)))

(defn w
  ([] (w 1.0))
  ([value] (* (width) value)))

(def final nil) ; when set will scale up for ~300 dpi @ specified paper size, e.g. a4p, a3l etc

(defn fscale [x y] ;should also check the display density if useing that feature
  (if (= final "a3p") ;check the width first in portrait modes?
    [(/ (* 10 x) 2) (/ (* 10 x) 2)];arbitary multiplier
    [x y]))

(defn rsquare
  ([] (rsquare 600))
  ([x] (fscale x x)))

(defn rgratio
  ([] (rgratio 600))
  ([x] (fscale (* x 1.618) x)))

(defn gauss [mean variance]
  (+ mean (* variance (random-gaussian))))

(defn key-press []
  (log/info "key pressed" (q/raw-key))
  (if (= (q/raw-key) \s)
    (q/save-frame (str "artefacts/" title "-" (width) "x" (height) "-####.tif")))
  (if (= (q/raw-key) \h)
    (q/frame-rate (* fr 2)))
  (if (= (q/raw-key) \n)
    (q/frame-rate fr))
  (if (= (q/raw-key) \d)
    (q/frame-rate (/ fr 2))))

(defn setup []
  (log/info "Display density is" (display-density))
  (q/frame-rate fr)
  ; http://colorizer.org/
  (color-mode :hsb 360 100 100 1.0)
  (q/background 0 0 100))

(defn settings []
  ; enable hidpi support, can only be 1 or 2
  (pixel-density (display-density)))

(defn draw []
  (no-loop)
  (def left-x 20)
  (def top-y 20)
  (def rect-height 240)
  (def minimum-width 360)
  ; don't create outlines on polygons
  (no-stroke)
  (fill 140 80 90 0.1); 10% opacity

  ; 1.1 draw the rectangle layers
  ;(doseq [i (range 20)]
  ;  (let [actual-width (+ minimum-width (* 10 i))]
  ;    (rect left-x top-y actual-width rect-height)))

  ;; 1.2 use 2% opacity
  ;(fill 0 0 50 0.02)
  ;(doseq [i (range 100)]
  ;  (let [actual-width (+ minimum-width (* 2 i))]
  ;    (rect left-x top-y actual-width rect-height)))

  ;; 1.3 uniform rands
  ;(doseq [i (range 20)]
  ;(let [actual-width (+ minimum-width (random 0 200))]
  ;  (rect left-x top-y actual-width rect-height)))

  ;; 1.4 gaus rands
  ;(doseq [i (range 20)]
  ;(let [actual-width (+ minimum-width (abs (gauss 0 100)))]
  ;  (rect left-x top-y actual-width rect-height)))

  ; 2.1 random quadrilaterals
  ;(doseq [i (range 20)]
  ;(let [top-width (+ minimum-width (abs (gauss 0 100)))
  ;      bottom-width (+ minimum-width (abs (gauss 0 100)))]

  ;  ; in Processing, the y-axis is inverted with 0 at the top
  ;  (begin-shape)
  ;  (vertex left-x                  top-y)            ; top left
  ;  (vertex (+ left-x top-width)    top-y)            ; top right
  ;  (vertex (+ left-x bottom-width) (+ top-y rect-height)) ; bottom right
  ;  (vertex left-x                  (+ top-y rect-height)) ; bottom left
  ;  (end-shape :close)))

  ; 2.2 fanout quadrilaterals
  ;(doseq [i (range 20)]
  ;(let [top-y 30 ;leave space at the top and bottom for fan-out
  ;      top-x-extend (abs (gauss 0 100))
  ;      top-width (+ minimum-width top-x-extend)
  ;      right-top-offset (* -1 (abs (gauss (* 0.1 top-x-extend) 1.0)))
  ;      bottom-x-extend (abs (gauss 0 100))
  ;      bottom-width (+ minimum-width bottom-x-extend)
  ;      right-bottom-offset (abs (gauss (* 0.1 bottom-x-extend) 1.0))]

  ;  (begin-shape)

  ;  ; top left
  ;  (vertex left-x                   top-y)
  ;  ; begin top fan-out
  ;  (vertex (+ left-x minimum-width) top-y)
  ;  ; top right
  ;  (vertex (+ left-x top-width)     (+ top-y right-top-offset))
  ;  ; bottom right
  ;  (vertex (+ left-x bottom-width)  (+ top-y rect-height right-bottom-offset))
  ;  ;begin bottom fan-out
  ;  (vertex (+ left-x minimum-width) (+ top-y rect-height))
  ;  ; bottom left
  ;  (vertex left-x                   (+ top-y rect-height))

  ;  (end-shape :close)))

  ; 2.3 bezier curves
  ;(doseq [i (range 20)]
  ;(let [top-width (+ minimum-width (abs (gauss 0 20)))
  ;      control-1-width (+ minimum-width 40 (abs (gauss 0 100)))
  ;      control-2-width (+ minimum-width 40 (abs (gauss 0 100)))
  ;      bottom-width (+ minimum-width (abs (gauss 0 20)))]

  ;  (begin-shape)
  ;  (vertex left-x top-y) ; top left

  ;  ; make the curve
  ;  ; top right
  ;  (vertex (+ left-x top-width) top-y)

  ;  (bezier-vertex
  ;    ; control 1
  ;    (+ left-x control-1-width) (+ top-y (* 0.333 rect-height))
  ;    ; control 2
  ;    (+ left-x control-2-width) (+ top-y (* 0.666 rect-height))
  ;    ; bottom right
  ;    (+ left-x bottom-width)    (+ top-y rect-height))

  ;  ; bottom left
  ;  (vertex left-x (+ top-y rect-height))
  ;  (end-shape :close)))

  ; 3.1 tiny things 
  ; make the fill fully opaque
  (fill 0 0 20 1.0)

  ;; fill in the non-transitional area of the rectangle
  (rect left-x top-y minimum-width rect-height)

  ;; draw dots
  ;(doseq [i (range 20000)]
  ;  (let [x (+ minimum-width (abs (gauss 0 60)))
  ;        y (random top-y (+ top-y (- rect-height 2)))]
  ;    (rect x y 2 2)))

  ; 3.2 tiny lines
  ;(fill 0 0 20 0.1)  ; black with 0.1 alpha

  ;(stroke-weight 0.1)
  ;(stroke 0 0 20 1.0)
  ;(doseq [i (range 5000)]
  ;  (let [line-start-x (+ left-x minimum-width)
  ;        line-end-x (+ left-x minimum-width (abs (gauss 0 60)))
  ;        y (random top-y (+ top-y rect-height))]
  ;    (line line-start-x y line-end-x y)))

  ; 3.3 vertical lines
  ;(stroke-weight 0.1)
  ;(stroke 0 0 20 1.0)
  ;(doseq [i (range 1000)]
  ;  (let [x (+ left-x minimum-width (abs (gauss 0 60)))
  ;        bottom-y (+ top-y rect-height -1)]
  ;    (line x top-y x bottom-y)))

  ; 3.4 spikey lines
  ;(stroke-weight 0.1)
  ;(stroke 0 0 20 1.0)
  ;(doseq [i (range 8000)]
  ;  (let [top-x (+ left-x minimum-width (gauss 0 50))
  ;        bottom-x (+ left-x minimum-width (gauss 0 50))
  ;        bottom-y (+ top-y rect-height)]
  ;    (line top-x (random top-y bottom-y) bottom-x (random top-y bottom-y))))

  ; 3.5 spikey clusters
  (stroke-weight 0.1)
  (stroke 0 0 20 1.0)
  (doseq [i (range 1000)]
    (let [top-x (+ left-x minimum-width (gauss 0 60))
          bottom-x (+ left-x minimum-width (gauss 0 60))
          bottom-y (+ top-y rect-height)
          mean-start-y (random top-y bottom-y)
          mean-finish-y (random top-y bottom-y)]
      (doseq [i (range 8)]
        (line (gauss top-x 5)
              (gauss mean-start-y 5)
              (gauss bottom-x 5)
              (gauss mean-finish-y 5)))))
  )

(q/defsketch qs181218
  :title title
  :size (rgratio 400)
  :settings settings
  :setup setup
  :draw draw
  :key-typed key-press
  :features [:exit-on-close])
