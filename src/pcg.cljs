(ns pcg
  (:require [clojure.browser.event :as event]
            [clojure.browser.dom   :as dom]
            [dommy.utils :as utils]
            [dommy.core :as dommy])
  (:use-macros
    [dommy.macros :only [node sel sel1]]))

(defn log [& args] (.log js/console (apply pr-str args)))
(defn log-obj [obj] (.log js/console obj))

(defn generate [mask]
  (let [
        fields (reformat mask)
        declaration (class-def fields)
        attrs (attributes fields)
        ]
    (str (boilerplate) "\n\n" declaration "\n" (class-import) "\n\n" (class-headers "product") "\n" attrs "\n}" "\n\n" )))

(defn on-click []
  (let [
        json (dommy/value (sel1 :#input))
        mask (js->clj (JSON/parse json))
        code (generate mask)
        ]
    (-> (sel1 :#output) (dommy/set-value! code))))

(defn ^:export start []
  (-> (sel1 :#output) (dommy/set-value! ""))
  (dommy/listen! (sel1 :#generate) :click on-click)
  (on-click))

(defn rename [name] (clojure.string/replace name #"\[(\d+)\]" "$1"))

(defn reformat-field [fields]
  (reduce (fn [acc [name obj]] (conj acc (merge obj {"name" (rename name)}))) [] fields))

(defn reformat [mask] (flatten (reduce extract-section-content [] mask)))

(defn extract-section-content [acc [key val]] (conj acc (reformat-field val)))

(defn class-def [mask]
  "class Product(val document: io.prismic.Document)(implicit ctx: Prismic.Context) {")

(defn class-import []
  "import PcgImplicits._")

(defn class-headers [mask-name]
  (str "val maskName = "\" mask-name "\"\n"
       "def id: String = document.id" "\n"
       "def slug: String = document.slug" "\n"
       "def tags: Seq[String] = document.tags"))

(defn attributes [fields] (clojure.string/join "\n" (map attribute fields)))

(defn attribute [stuff]
  (let [
        name (get stuff "name")
        type (get stuff "type")
        ]
    (str "def " name ": Option[RichStructuredText] = document.getStructuredText(s\"$maskName." name ")")))

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
