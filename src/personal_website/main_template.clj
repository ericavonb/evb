(ns personal_website.main_template
  (:gen-class)
  (:require [personal_website.homepage_enlive :as homepage])
  (:use [personal_website.defaults :only [*default* *homepage* ]]
        [net.cgrand.enlive-html :only [deftemplate content append do->
                                       set-attr sniptest at emit* wrap
                                       html-content]]
        [net.cgrand.moustache :only [app]]
        [personal_website.utils
         :only [*webdir* run-server render-to-response render-request page-not-found]]))


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
  [{:label (format "Email: %s" (:email info))
    :href (format "mailto:%s" (:email info))}
   {:label "Facebook"
    :href (format "http://www.facebook.com/%s" (:facebook info))}
   {:label "Twitter"
    :href (format "http://twitter.com/%s" (:twitter info))}])
   
;; ========================================
;; The main template
;; ========================================)
(defn file-to-string [uri]
  (->
      (new java.util.Scanner (new java.io.File uri))
    (.useDelimiter "\\A")
    (.next)))

(defn section [{:keys [snippet args attr]}]
  (do->
   (content (snippet args))
   (apply set-attr (flatten (into [] attr)))))

(defn format-scripts [scripts loc]
    (map #(append ((wrap :script (dissoc % :body :script))
                   (if (= loc :body)
                     (file-to-string (str *webdir* (:src %)))
                     (:script %))))
         (filter #(contains? % loc) scripts)))




(deftemplate index "personal_website/html/main_template.html"
  [{:keys [title scripts style header nav contents footer]}]
  [:title] (content title)
  [:head] (apply do-> (concat (map #(append ((wrap :link %) [""])) style)
                              (format-scripts scripts :head)))
  [:header] (section header)
  [:nav] (section nav)
  [:.contents] (section contents)
  [:footer] (section footer)
  [:body] (apply do-> (format-scripts scripts :body)))



;; =======================================
;; Routes
;; =======================================


(def routes
     (app
      [""]  (fn [req] (render-to-response (index *homepage*)))))

;      ["Projects"] (fn [req] (render-to-response(index *default*)))
;      ["Musings"] (fn [req] (render-to-response (index *default*)))
;      ["Sites"] (fn [req] (render-to-response (index *default*)))
;      [&]   page-not-found))

