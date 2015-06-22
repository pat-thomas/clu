(defproject clu "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2760"]
                 [org.omcljs/om "0.8.8"]
                 [om-utils "0.4.0"]]
  :plugins [[lein-cljsbuild "1.0.6"]]
  :source-paths ["server"]
  :cljsbuild {:builds [{:id           "dev"
                        :source-paths ["src/clu/gui"]
                        :compiler     {:output-to     "out/clu.js"
                                       :output-dir    "out"
                                       :optimizations :none
                                       :source-map    true}}]})
