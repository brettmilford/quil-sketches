(ns quil-sketches.181219
  (:require [quil.core :refer :all :as q]
            [clojure.tools.logging :as log]))

(def title "181219")
(def fr 1)
(def seed (System/nanoTime))

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

(defn key-press []
  (log/info "key pressed" (q/raw-key))
  (when (= (q/raw-key) \s)
    (let [filename (str "artefacts/" title "-" (width) "x" (height) "-" seed "-####.tif")]
      (q/save-frame filename)
      (log/info "Saved" filename)))
  (if (= (q/raw-key) \h)
    (q/frame-rate (* fr 2)))
  (if (= (q/raw-key) \n)
    (q/frame-rate fr))
  (if (= (q/raw-key) \d)
    (q/frame-rate (/ fr 2))))

(defn setup []
  (log/info "Display density is" (display-density))
  (q/frame-rate fr)
  
  (log/info "setting seed to:" seed)
  ; Random.setSeed() only uses the highest 48 bits of the seed
  (random-seed seed)

  ; http://colorizer.org/
  (color-mode :hsb 360 100 100 1.0)
  (q/background 0 0 100))

(defn settings []
  ; enable hidpi support, can only be 1 or 2
  (pixel-density (display-density)))

(defn deformation
  "polygon deformation takes a polygon specified as n xy tuples and adds a point at random"
  [p])

(defn draw []
  (let [gr (q/create-graphics 400 400 :p2d)]
  (q/with-graphics gr
    (q/stroke 255 0 0)
    (q/begin-shape :triangles)
    (q/vertex 200 40)
    (q/vertex 320 120)
    (q/vertex 320 280)
    (q/vertex 200 360)
    (q/vertex 80 280)
    (q/vertex 80 120)
    (q/end-shape))
  (q/image gr 0 0))
  )


(q/defsketch qs181219
  :title title
  :size (rsquare)
  :settings settings
  :setup setup
  :draw draw
  :key-typed key-press
  :features [:exit-on-close])
