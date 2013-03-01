(ns personal_website.defaults
  (:use [personal_website.homepage_enlive
         :only [header nav contents footer]
         :as homepage]))

(def *jquery* {:type "text/javascript" :src "js/jquery.js" :head true :script ""})

(def *josefin* {:href "http://fonts.googleapis.com/css?family=Josefin+Sans:400,400italic" :rel "stylesheet" :type "text/css"})

(def *default*
  {:title "EvB"
   :sections {:section :homepage
              :content { :header {:name "Erica von Buelow"}
                         :navigation {:nav-items [{:label "About"}
                                      {:href "Projects" :label "Projects"}
                                      {:href "Musings" :label "Musings"}
                                      {:href "Sites" :label "Sites"}
                                      {:label "Contact"}]}
                        :contents {:about "hi there"
                                   :contact-info {:email "evonbuelow@gmail.com"
                                                  :facebook "ericavonbuelow"
                                                  :twitter "evonbuelow"}}
                        :footer {}}}})


(def *homepage*
  {:title "EvB"
   :scripts [*jquery*
             {:type "text/javascript" :src "js/homepage.js" :body true}]
   :style [*josefin*
           {:href "css/homepage_style.css" :media "screen"
            :rel "stylesheet" :type "text/css"}]
   
   :header {:snippet homepage/header
            :args {:name "Erica von Buelow"}
            :attr {:id "homepage_header"}}
   
   :nav {:snippet homepage/nav
         :args {:nav-items [{:label "About"}
                {:href "Projects" :label "Projects"}
                {:href "Musings" :label "Musings"}
                {:href "Sites" :label "Sites"}
                {:label "Contact"}]}
         :attr {:id "homepage_nav"}}
   
   :contents {:snippet homepage/contents
              :args {:about "I am a (mostly) self taught programmer and designer."
                     :contact-info {:email "evonbuelow@gmail.com"
                                    :facebook "ericavonbuelow"
                                    :twitter "evonbuelow"
                                    :github "ericavonb"}}
              :attr {:id "homepage_contents"}}
   
   :footer {:snippet homepage/footer
            :args {}
            :attr {:id "homepage_footer"}}})

  