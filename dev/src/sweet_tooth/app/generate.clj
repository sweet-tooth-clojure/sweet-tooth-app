(ns sweet-tooth.app.generate
  "Generates an app template from minimal project"
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [sweet-tooth.generate :as g]))

(defn files
  []
  (->> "leiningen/new/sweet_tooth_app"
       io/resource
       io/file
       file-seq
       (remove #(.isDirectory %))
       (map (fn [file]
              (let [path (str/replace (.getPath file) #".*sweet_tooth_app/" "")
                    src  (str/replace path #"src/app" "src/{{sanitized}}")]
                `[~src (~'render ~path ~'data)])))
       (sort)))

(def template-files-point
  {:path     ["src" "leiningen" "new" "sweet_tooth_app.clj"]
   :rewrite  (fn [node opts]
               (-> (g/clear-right-anchor node 'st:files)
                   (g/insert-forms-below-anchor 'st:files (files))))
   :strategy ::g/rewrite-file})

(def app-template-generator
  {:points {:template-files template-files-point}
   :opts   nil})

(defmethod g/generator :sweet-tooth/app-template [_] app-template-generator)
