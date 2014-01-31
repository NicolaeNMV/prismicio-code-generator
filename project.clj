(defproject pcg "1.0"
  :description "prismic.io code generator"
  :source-paths ["src-clj"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2156" :exclusions [org.apache.ant/ant]]
                 [prismatic/dommy "0.1.2"]
                 ]
  :plugins [[lein-cljsbuild "1.0.2"]]
  :cljsbuild {
              :builds {
                       :dev {
                             :source-paths ["src"]
                             :compiler {:output-to "public/js/compiled.js"
                                        :optimizations :whitespace
                                        :pretty-print true}}
                       :prod {
                             :source-paths ["src"]
                             :compiler {:output-to "public/js/compiled.js"
                                        :optimizations :advanced
                                        :pretty-print false}}}})
