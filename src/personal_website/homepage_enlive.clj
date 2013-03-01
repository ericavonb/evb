(ns personal_website.homepage_enlive
  (:use [net.cgrand.enlive-html :only [deftemplate
                                       defsnippet
                                       content
                                       append
                                       do->
                                       set-attr
                                       sniptest
                                       at
                                       emit*
                                       wrap
                                       any-node]]))


;; =======================================
;; Helper Functions
;; =======================================

(defn add-links [items]
  "puts links around the items of the list items,
    where the items are maps with keys :label and :href.
    Link is made iff the item has a :href value"
  (map #(if-let [href (:href %)]
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
  [{:label (format "Email: %s" (:email info))
    :href (format "mailto:%s" (:email info))}
   {:label "Github"
    :href (format "https://github.com/%s" (:github info))}
   {:label "Facebook"
    :href (format "http://www.facebook.com/%s" (:facebook info))}
   {:label "Twitter"
    :href (format "http://twitter.com/%s" (:twitter info))}])
   
;; ========================================
;; The main template for the homepage
;; ========================================

(deftemplate homepage_template "personal_website/html/homepage.html"
  [{:keys [name nav-items about contact-info]}]
  [:.about] (content about)
  [:.contact] (make-list (format-contact contact-info))
  [:#logo] (content name)
  [:.navigation] (make-list nav-items))


(defsnippet header "personal_website/html/homepage.html" [:header :> any-node]
  [{:keys [name nav-items about contact-info]}]
  [:.name] (content ((wrap :a {:href "/"}) name)))

(defsnippet nav "personal_website/html/homepage.html" [:nav :> any-node]
  [{:keys [nav-items]}]
  [:.navigation] (make-list nav-items))

(defsnippet contents "personal_website/html/homepage.html" [:.contents :> any-node]
  [{:keys [about contact-info]}]
  [:.about] (content about)
  [:.contact] (make-list (format-contact contact-info)))

(defsnippet footer "personal_website/html/homepage.html" [:footer :> any-node]
  [args]
  [:.site-nav] (content [""]))



  
    

