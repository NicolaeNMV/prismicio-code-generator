(ns pcg
  (:require [clojure.browser.event :as event]
            [clojure.browser.dom   :as dom]))

(defn log [& args]
  (.log js/console (apply pr-str args)))

(defn log-obj [obj]
  (.log js/console obj))

(defn ^:export start []
  (log "haha" "test"))

; (log-obj (dom/element "Text node"))
; (log-obj (dom/element :li))
; (log-obj (dom/element :li {:class "foo"}))
; (log-obj (dom/element :li {:class "bar"} "text node"))
; (log-obj (dom/element [:ul [:li :li :li]]))
; (log-obj (dom/element :ul [:li :li :li]))
; (log-obj (dom/element :li {} [:ul {} [:li :li :li]]))
; (log-obj (dom/element [:li {:class "baz"} [:li {:class "quux"}]]))


; (event/listen source
;               :click
;               (fn [e]
;                 (let [i (swap! success-count inc)
;                       e (dom/element :li
;                                      {:id "testing"
;                                       :class "test me out please"}
;                                      "It worked!")]
;                   (log-obj e)
;                   (log i)
;                   (dom/append destination
;                               e))))
