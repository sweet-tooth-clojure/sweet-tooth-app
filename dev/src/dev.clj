(ns dev
  (:require [sweet-tooth.generate :as g]
            [sweet-tooth.app.generate :as ag]
            [clojure.java.io :as io]))

(defn -main []
  (g/generate :sweet-tooth/app-template))
