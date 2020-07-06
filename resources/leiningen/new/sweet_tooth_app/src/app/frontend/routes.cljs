(ns minimal.frontend.routes
  (:require [minimal.frontend.components.home :as h]))

(def frontend-routes
  [["/"
    {:name       :home
     :lifecycle  {}
     :components {:main [h/component]}
     :title      "Home"}]])
