(defproject sweet-tooth-app/lein-template "0.1.0"
  :description "generate a new sweet tooth app"
  :url "http://sweettooth.dev"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :eval-in-leiningen true

  :profiles {:dev {:source-paths ["dev/src"]
                   :dependencies [[sweet-tooth/generate "0.1.0"]]}})
