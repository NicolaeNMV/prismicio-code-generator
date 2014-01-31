(ns pcg
  (:require [clojure.browser.event :as event]
            [clojure.browser.dom   :as dom]
            [dommy.utils :as utils]
            [dommy.core :as dommy])
  (:use-macros
    [dommy.macros :only [node sel sel1]]))

(defn log [& args] (.log js/console (apply pr-str args)))
(defn log-obj [obj] (.log js/console obj))

; Implement me!
(defn generate [mask]
  (str "source code for " mask))

(defn on-click []
  (let [
        json (dommy/value (sel1 :#input))
        mask (js->clj (JSON/parse json))
        code (generate mask)
        ]
    (-> (sel1 :#output) (dommy/set-value! code))))

(defn ^:export start []
  (dommy/listen! (sel1 :#generate) :click on-click)
  (generate))
