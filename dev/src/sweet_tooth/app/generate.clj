(ns sweet-tooth.app.generate
  "Generates an app template from minimal project"
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [sweet-tooth.generate :as g]))


(defn- file-paths
  []
  (->> "leiningen/new/sweet_tooth_app"
       io/resource
       io/file
       file-seq
       (remove #(.isDirectory %))
       (map (fn [file-path]
              (str/replace (.getPath file-path) #".*sweet_tooth_app/" "")))
       (sort)))

(defn- file-templates
  [file-paths]
  (map (fn [path]
         (let [src (str/replace path #"src/app" "src/{{sanitized}}")]
           `[~src (~'render ~path ~'data)]))
       file-paths))

(def template-files-point
  {:path     ["src" "leiningen" "new" "sweet_tooth_app.clj"]
   :rewrite  (fn [node _opts]
               (-> node
                   (g/clear-right-anchor 'st:files)
                   (g/insert-forms-below-anchor 'st:files (file-templates (file-paths)))))
   :strategy ::g/rewrite-file})


(defn- chmods
  [file-paths]
  (->> file-paths
       (filter #(re-find #"^bin/" %))
       (map (fn [path] (list (conj '[fs/chmod "+x" (project-name name)] path))))))

(def template-chmod-point
  {:path     ["src" "leiningen" "new" "sweet_tooth_app.clj"]
   :rewrite  (fn [node _opts]
               (-> (g/clear-right-anchor node 'st:chmod)
                   (g/insert-forms-below-anchor 'st:chmod (chmods (file-paths)))))
   :strategy ::g/rewrite-file})

(def app-template-generator
  {:points {:template-files template-files-point}
   :opts   nil})

(defmethod g/generator :sweet-tooth/app-template [_] app-template-generator)
