(ns {{ns}}.frontend.core
  (:require [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [re-frame.db :as rfdb]
            [integrant.core :as ig]
            [meta-merge.core :as mm]

            [sweet-tooth.frontend.core.flow :as stcf]
            [sweet-tooth.frontend.load-all-handler-ns] ;; for side effects
            [sweet-tooth.frontend.core.utils :as stcu]
            [sweet-tooth.frontend.config :as stconfig]
            [sweet-tooth.frontend.nav.flow :as stnf]
            [sweet-tooth.frontend.routes :as stfr]
            [sweet-tooth.frontend.js-event-handlers.flow :as stjehf]

            [{{ns}}.frontend.components.app :as app]
            [{{ns}}.frontend.handlers] ;; for side effects
            [{{ns}}.frontend.routes :as froutes]
            [{{ns}}.frontend.subs] ;; for side effects
            [{{ns}}.cross.endpoint-routes :as eroutes]))

(enable-console-print!)

;; treat node lists as seqs
(extend-protocol ISeqable
  js/NodeList
  (-seq [node-list] (array-seq node-list))

  js/HTMLCollection
  (-seq [node-list] (array-seq node-list)))

(defn system-config
  "This is a function instead of a static value so that it will pick up
  reloaded changes"
  []
  (mm/meta-merge stconfig/default-config
                 {::stfr/frontend-router {:use    :reitit
                                          :routes froutes/frontend-routes}
                  ::stfr/sync-router     {:use    :reitit
                                          :routes eroutes/routes}

                  ;; Treat handler registration as an external service,
                  ;; interact with it via re-frame effects
                  ::stjehf/handlers {}}))

(defn -main []
  (rf/dispatch-sync [::stcf/init-system (system-config)])
  (rf/dispatch-sync [::stnf/dispatch-current])
  (rdom/render [app/app] (stcu/el-by-id "app")))

(defonce initial-load (delay (-main)))
@initial-load

(defn stop [_]
  (when-let [system (:sweet-tooth/system @rfdb/app-db)]
    (ig/halt! system)))
