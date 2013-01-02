(ns personal_website.main_template
    (:require [personal_website.homepage_enlive :as homepage])
  (:use [personal_website.defaults :only [*default*]]
        [net.cgrand.enlive-html :only [deftemplate content append do->
                                       set-attr sniptest at emit* wrap]]
        [net.cgrand.moustache :only [app]]
        [personal_website.utils
         :only [run-server render-to-response render-request page-not-found]]))


(def section-model
  {:homepage {:header homepage/header :navigation homepage/navigation
              :content homepage/content :footer homepage/footer}
   :musings {:header musings/header :navigation musings/navigation
             :content musings/content :footer musings/footer}})

(defn make-section [section]
  (let [sec (section-model (section :section))
        cont (section :content)
        scs (keys cont)]
    (map #(apply (get sec %) (cont %)) scs)))
  
 ;; =======================================
;; Helper Functions
;; =======================================

(defn add-links [items]
  "puts links around the items of the list items,
    where the items are maps with keys :label and :href.
    Link is made iff the item has a :href value"
  (map #(if-let [href (% :href)]
    ((wrap :a {:href href}) (get % :label ""))
    (get % :label "")) items))

(defn add-list-item [item]
  "Returns function that adds item as an :li element to the node argument"
  #((append ((wrap :li {:id (format "%s_%d"
                                (get (:attrs %) :id "list_item")
                                 (inc (count (:content %))))
                        :class "list_item"}) item)) %))

(defn make-list [items]
  "Returns function that adds all the items as :li elements to the node argument."
  (apply do->
         (cons (content ())
               (map add-list-item (add-links items)))))

(defn format-contact [info]
  "Put contact info in right form"
  [{:label (format "Email: %s" (info :email))
    :href (format "mailto:%s" (info :email))}
   {:label "Facebook"
    :href (format "http://www.facebook.com/%s" (info :facebook))}
   {:label "Twitter"
    :href (format "http://twitter.com/%s" (info :twitter))}])
   
;; ========================================
;; The main template for the homepage
;; ========================================

(deftemplate homepage_template "personal_website/html/homepage.html"
  [{:keys [name nav-items about contact-info]}]
  [:.about] (content about)
  [:.contact] (make-list (format-contact contact-info))
  [:#logo] (content name)
  [:.navigation] (make-list nav-items))  

  
(deftemplate index "personal_website/html/main_template.html"
  [{:keys [title sections]}]
  [:#title] (content title)
  [:body]   [])


 


;; =======================================
;; Routes
;; =======================================


(def routes
     (app
      [""]  (fn [req] (render-to-response (index *default*)))
      ["Projects"] (fn [req] (render-to-response(index *default*)))
      ["Musings"] (fn [req] (render-to-response (index *default*)))
      ["Sites"] (fn [req] (render-to-response (index *default*)))
      [&]   page-not-found))

;; ========================================
;; The App
;; ========================================

(defonce *server* (run-server routes))