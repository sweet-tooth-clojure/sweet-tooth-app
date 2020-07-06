(ns minimal.frontend.components.app
  (:require [re-frame.core :as rf]
            [sweet-tooth.frontend.routes :as stfr]
            [sweet-tooth.frontend.nav.flow :as stnf]))

(defn app
  []
  [:div.app
   [:div.head
    [:div.container
     [:nav [:a {:href (stfr/path :home)} "Home"]]]]
   [:div.container
    [:div.main @(rf/subscribe [::stnf/routed-component :main])]]])
