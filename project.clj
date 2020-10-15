(defproject vk-narciss "0.1.0-SNAPSHOT"
  :description "Scan your vk friends for narcissism"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [http-kit "2.5.0"]
                 [org.clojure/data.json "1.0.0"]]
  :repl-options {:init-ns vk-narciss.start}
  :main vk-narciss.start)
