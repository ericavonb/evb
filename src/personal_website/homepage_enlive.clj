(ns personal_website.homepage_enlive
  (:use [net.cgrand.enlive-html :only [deftemplate content append do->
                                       set-attr sniptest at emit* wrap]]))


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



  
    

