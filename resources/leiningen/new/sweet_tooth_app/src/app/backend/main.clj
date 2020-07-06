(ns minimal.backend.main
  (:gen-class)
  (:require [duct.core :as duct]
            [integrant.core :as ig]
            [minimal.backend.duct]
            [taoensso.timbre :as log]
            [sweet-tooth.endpoint.system :as es]))

(duct/load-hierarchy)

(defn init-system
  [env-str profiles]
  (ig/init (es/config (keyword env-str)) profiles))

(defn start-server
  [config]
  (let [system (ig/init config [:duct/daemon])]
    (log/info "initialized system" ::system-init-success {:system (keys system)})
    (duct/await-daemons system)))

(defn -main
  [cmd]
  (let [env    (keyword (or (System/getenv "APP_ENV") :dev))
        config (es/config env)]
    (log/info "-main" ::-main {:cmd cmd :env env})
    (case cmd
      "server"
      (start-server config)

      "deploy/check"
      (do (println "a-ok!")
          (System/exit 0)))))
