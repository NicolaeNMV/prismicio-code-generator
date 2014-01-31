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
  (boilerplate))

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

(defn boilerplate []
  "package models

import io.prismic._
import controllers._

case class RichStructuredText(st: Fragment.StructuredText) {
  def text: Option[String] = {
    Some(st.blocks.collect { case b: Fragment.StructuredText.Block.Text => b.text }.mkString(\"\\n\")).filterNot(_.isEmpty)
  }
  def html(linkResolver: DocumentLinkResolver): Option[String] = {
    Some(st.asHtml(linkResolver))
  }
}

case class RichColor(color: Fragment.Color) {
  def text: Option[String] = {
    Some(color.hex)
  }
  def html: Option[String] = {
    Some(color.asHtml)
  }
}

object PcgImplicits {
  implicit def toRichStructuredText(st: Fragment.StructuredText): RichStructuredText = {
    new RichStructuredText(st)
  }

  implicit def toRichStructuredTextOpt(st: Option[Fragment.StructuredText]): Option[RichStructuredText] = {
    st.map(new RichStructuredText(_))
  }

  implicit def toRichColor(color: Fragment.Color): RichColor = {
    new RichColor(color)
  }

  implicit def toRichColorOpt(color: Option[Fragment.Color]): Option[RichColor] = {
    color.map(new RichColor(_))
  }
}")
