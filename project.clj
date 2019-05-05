(defproject dracul-8 "0.1.0-SNAPSHOT"
  :description "A dynamically recompiling CHIP-8 emulator"
  :url "https://github.com/flambard/dracul-8"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [elit/dynne "0.4.3"]
                 [seesaw "1.5.0"]
                 ]
  :main ^:skip-aot dracul-8.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
