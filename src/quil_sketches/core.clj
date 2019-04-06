(ns quil-sketches.core)

;reload
;(defn refresh [sketch]
;  (require :reload sketch))
;(defn refresh []
;  (use :reload 'sketch.dynamic)
;  (.loop example))

(defn weighted-choice [& items-and-weights]
  "Given a sequence of alternating item, weight arguments, chooses one of the
   items with a probability equal to the weight.  Each weight should be
   between 0.0 and 1.0, and all weights should sum to 1.0."
  (assert (zero? (mod (count items-and-weights) 2)))
  (assert (>= (count items-and-weights) 2))
  (let [r (random 0 1.0)]
    (loop [weight-seen 0
           remaining-items items-and-weights]
      (if (<= (count remaining-items) 2)
        (first remaining-items)
        (let [new-weight (second remaining-items)
              end-bound (+ weight-seen new-weight)]
          (if (and (> r weight-seen) (< r end-bound ))
            (first remaining-items)
            (recur (+ weight-seen (second remaining-items)) (drop 2 remaining-items))))))))

(defn mkratri [x y l]
  "Make a right angle triangle at x y starting point of size l"
  (log/info "Point 1:" x "," y)
  (log/info "Point 2:" (- x (/ l 2)) "," y)
  (log/info "Point 3:" x "," (- y (/ l 2)))
  (q/triangle x y (- x (/ l 2)) y x (- y (/ l 2))))

(defn mketri [x y l]
  "Make a equalatral triangle at x y starting point of length l"
  (log/info "Point 1:" x "," y)
  (log/info "Point 2:" (+ x (/ l 2)) "," (+ y (/ l 2)))
  (log/info "Point 3:" x "," (+ y (/ l 2)))
  (q/triangle x y (+ x (/ l 2)) (+ y (/ l 2)) x (+ y (/ l 2))))


;  ; draw the watercolor blob shape
;  (let [the-blob-mask (q/create-graphics 250 250)
;        the-texture-mask (q/create-graphics 250 250)
;        the-overlay (q/create-graphics 250 250)
;        layer-alpha (q/create-graphics 250 250)
;        final-poly (q/create-graphics 250 250)]
;(with-graphics the-blob-mask
;  (background 0 0 0)
;  (stroke 0 0 layer-alpha)
;  (fill 0 0 layer-alpha)
;  (begin-shape)
;  (doseq [[x y] final-poly]
;    (vertex x y))
;  (end-shape))
;
;; draw the circles onto the texture mask
;(with-graphics the-texture-mask
;  (background 0 0 0)
;  (no-stroke)
;
;  (fill 0 0 layer-alpha)
;  (doseq [j (range 900)]
;    (let [x (random 0 (w))
;          y (random 0 (h))
;          len (abs-gauss (w 0.03) (w 0.02))
;          [hue sat bright] (color-fn)]
;      (fill hue sat bright)
;      (ellipse x y len len)))
;
;  ; Blend the watercolor blob shape layer into the current
;  ; layer, only taking the darkest pixel from each. This
;  ; effectively means we're taking the "intersection" of
;  ; the two masks.
;  (blend the-blob-mask 0 0 (w) (h) 0 0 (w) (h) :darkest))
;
;(with-graphics the-overlay
;  ; make the whole background red
;  (background 5 80 80)
;
;  ; apply the combination blob/texture mask
;  (mask-image the-texture-mask)))
;
;; apply the masked layer
;(image the-overlay 0 0)

