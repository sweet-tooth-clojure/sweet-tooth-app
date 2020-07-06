(ns {{ns}}.frontend.routes
  (:require [{{ns}}.frontend.components.home :as h]))

(def frontend-routes
  [["/"
    {:name       :home
     :lifecycle  {}
     :components {:main [h/component]}
     :title      "Home"}]])
