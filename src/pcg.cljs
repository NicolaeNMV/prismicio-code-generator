(ns pcg
  (:require [clojure.browser.event :as event]
            [clojure.browser.dom   :as dom]
            [dommy.utils :as utils]
            [dommy.core :as dommy])
  (:use-macros
    [dommy.macros :only [node sel sel1]]))

(defn log [& args]
  (.log js/console (apply pr-str args)))

(defn log-obj [obj]
  (.log js/console obj))

(def source      (dom/get-element "source"))
(def destination (dom/get-element "destination"))

(defn ^:export start []
  (dommy/listen! (sel1 :#generate) :click generate)
  (generate))

(defn generate []
  (let [json-string (:value (sel1 :#input))]
    (log json-string)
    (log (sel1 :#input))))
