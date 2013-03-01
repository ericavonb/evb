(ns personal-website.core
  (:use [personal_website.main_template :only [routes]]
        [personal_website.utils :only [*webdir* run-server render-to-response
                                       render-request page-not-found]]))



;; ========================================
;; The App
;; ========================================

(defonce *server* (run-server routes))



(defn compile-stylus 
(defn -main
  "I don't do a whole lot."
  [& args]
  (println "------------------------------------------")
  (println "------------------------------------------"))
