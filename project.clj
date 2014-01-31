(defproject pcg "1.0"
  :description "prismic.io code generator"
  :source-paths ["src-clj"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2156"
                  :exclusions [org.apache.ant/ant]]
                 [hiccup "1.0.4"]]
  :plugins [[lein-cljsbuild "1.0.2"]]
  :cljsbuild {
    :builds [{:source-paths ["src"]
              :compiler {:output-to "public/js/main.js"
                         :optimizations :whitespace
                         :pretty-print true}}]})
