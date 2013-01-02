(ns personal_website.musings_enlive
  (:use [net.cgrand.enlive-html :only [deftemplate content append do->
                                       set-attr sniptest at emit* wrap]]))

;; =======================================
;; Helper Functions
;; =======================================

   
;; ========================================
;; The main template for the homepage
;; ========================================

(deftemplate musings_template "personal_website/html/musings.html"
  [{:keys [name nav-items about contact-info]}]
  [:.about] (content about)
  [:.contact] (make-list (format-contact contact-info))
  [:#logo] (content name)
  [:.navigation] (make-list nav-items))
