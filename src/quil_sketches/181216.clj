(ns quil-sketches.181216
  (:require [quil.core :refer :all :as q]
            [clojure.tools.logging :as log]))

(def title "181216")
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

(defn rescale [value old-min old-max new-min new-max]
  "Rescales value from range [old-min, old-max] to [new-min, new-max]"
  (let [old-spread (- old-max old-min)
        new-spread (- new-max new-min)]
    (+ (* (- value old-min) (/ new-spread old-spread))
       new-min)))

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
  (no-stroke)
  (doseq [y (range 10 (- height 10) (h 0.01))
          x (range 0 (width) (w 0.01))]
    (let [hue (rescale (random y (+ y (h 0.01))) 0 (h) 100 160) ;green -> blue
          diam (q/random (w 0.01))
          x (q/random (width))
          y (q/random (- y (h 0.009)) (+ y (h 0.001)))]
    (q/fill hue 70 100)
    (q/ellipse x y diam diam))))

(q/defsketch qs181216
  :title title
  :size (rsquare)
  :settings settings
  :setup setup
  :draw draw
  :key-typed key-press
  :features [:exit-on-close])
