(ns leiningen.new.sweet-tooth-app
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files sanitize-ns project-name]]
            [leiningen.core.main :as main]
            [me.raynes.fs :as fs]))

(def render (renderer "sweet-tooth-app"))

(defn sweet-tooth-app
  "generate a new sweet tooth application"
  [name]
  (let [data {:raw-name  name
              :name      (project-name name)
              :ns        (sanitize-ns name)
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' sweet-tooth-app project.")
    (->files data
             #_st:files
             [".gitignore" (render ".gitignore" data)]
             ["README.md" (render "README.md" data)]
             ["bin/build.sh" (render "bin/build.sh" data)]
             ["bin/check-ready.sh" (render "bin/check-ready.sh" data)]
             ["deps.edn" (render "deps.edn" data)]
             ["dev/resources/dev.edn" (render "dev/resources/dev.edn" data)]
             ["dev/src/dev.clj" (render "dev/src/dev.clj" data)]
             ["dev/src/user.clj" (render "dev/src/user.clj" data)]
             ["package.json" (render "package.json" data)]
             ["project.clj" (render "project.clj" data)]
             ["resources/config.edn" (render "resources/config.edn" data)]
             ["resources/public/index.html" (render "resources/public/index.html" data)]
             ["shadow-cljs.edn" (render "shadow-cljs.edn" data)]
             ["src/{{sanitized}}/backend/duct.clj" (render "src/app/backend/duct.clj" data)]
             ["src/{{sanitized}}/backend/main.clj" (render "src/app/backend/main.clj" data)]
             ["src/{{sanitized}}/cross/endpoint_routes.cljc" (render "src/app/cross/endpoint_routes.cljc" data)]
             ["src/{{sanitized}}/frontend/components/app.cljs" (render "src/app/frontend/components/app.cljs" data)]
             ["src/{{sanitized}}/frontend/components/home.cljs" (render "src/app/frontend/components/home.cljs" data)]
             ["src/{{sanitized}}/frontend/core.cljs" (render "src/app/frontend/core.cljs" data)]
             ["src/{{sanitized}}/frontend/handlers.cljs" (render "src/app/frontend/handlers.cljs" data)]
             ["src/{{sanitized}}/frontend/routes.cljs" (render "src/app/frontend/routes.cljs" data)]
             ["src/{{sanitized}}/frontend/subs.cljs" (render "src/app/frontend/subs.cljs" data)])

    #_st:chmod
    (fs/chmod "+x" (str (project-name name) "/bin/build.sh"))
    (fs/chmod "+x" (str (project-name name) "/bin/check-ready.sh"))))
