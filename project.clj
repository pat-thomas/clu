(defproject clu "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure       "1.6.0"]
                 [org.clojure/clojurescript "0.0-2760"]
                 [org.omcljs/om             "0.8.8"]
                 [om-utils                  "0.4.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [http-kit                  "2.1.18"]
                 [org.clojure/data.json     "0.2.6"]
                 [quil                      "2.2.6"]
                 [overtone                  "0.9.1"]
                 [clojurewerkz/quartzite    "2.0.0"]
                 [org.clojure/core.async    "0.1.346.0-17112a-alpha"]]
  :plugins [[lein-cljsbuild "1.0.6"]]
  :cljsbuild {:builds [{:id           "dev"
                        :source-paths ["src/clu/gui"]
                        :compiler     {:output-to     "public/out/clu.js"
                                       :output-dir    "public/out"
                                       :optimizations :none
                                       :source-map    true}}]})
