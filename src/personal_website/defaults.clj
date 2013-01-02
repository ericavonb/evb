(ns personal_website.defaults)


(def *default*
  {:title "EvB"
   :sections {:section :homepage
              :content { :header {:name "Erica von Buelow"}
                         :navigation {:nav-items {:label "About"}
                                      {:href "Projects" :label "Projects"}
                                      {:href "Musings" :label "Musings"}
                                      {:href "Sites" :label "Sites"}
                                      {:label "Contact"}}
                        :contents {:about "hi there"
                                   :contact-info {:email "evonbuelow@gmail.com"
                                                  :facebook "ericavonbuelow"
                                                  :twitter "evonbuelow"}}
                         :footer {}}}})


  