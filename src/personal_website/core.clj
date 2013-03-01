(ns personal_website.core
  (:use [personal_website.defaults :only [*default* *homepage* ]]
        [personal_website.main_template :only [routes index]]
        [personal_website.utils :only [*webdir* run-server render
                                       render-to-response render-snippet
                                       render-request page-not-found]]))


;; ========================================
;; Helpers
;; ========================================

(defn copy-file [source-path dest-path]
  "Copy a file from the source and place it in the destination."
  (clojure.java.io/copy (clojure.java.io/file source-path) (clojure.java.io/file dest-path)))

(defn get_design []
  "Get the css files from the Roots project and put it in the css folder."
  (doseq [[src dest] (map #(vector
                            (str (.getPath %))
                            (format "%scss/%s" *webdir* (.getName %)))
   (rest (file-seq (clojure.java.io/file
                    (clojure.string/replace
                     *webdir*
                     #"/personal_website/src/personal_website/"
                     "/my_website_design/public/css/")))))]
    (copy-file src dest)))

(defn put_html [page]
  "Put the html for the page and put it in a file in the Roots project."
  (let [content (render (index page))
        file (new java.io.File
                  (clojure.string/replace
                   *webdir*
                   #"/personal_website/src/personal_website/"
                   "/my_website_design/views/homepage.html"))
        bw (new java.io.BufferedWriter
                (new  java.io.FileWriter (.getAbsoluteFile file)))]
    (do
      (if (not (.exists file))
        (.createNewFile file))
      (.write bw content)
      (.close bw))))

(defn get_scripts [& scripts]
  "Get the javascript files from the Roots project and put
   it in the js folder."
  (doseq [[src dest] (map #(vector
                            (str (.getPath %))
                            (format "%sjs/%s" *webdir* (.getName %)))
   (filter #(re-matches #".*\.js" (.getName %)) (rest (file-seq (clojure.java.io/file
                    (clojure.string/replace
                     *webdir*
                     #"/personal_website/src/personal_website/"
                     "/my_website_design/public/js/"))))))]
    (copy-file src dest)))


;; ========================================
;; The App
;; ========================================

(defonce ^:dynamic *server* (run-server routes))


(defn -main
  "Main function and entry point."
  [& args]
  (put_html *homepage*)
  (get_design)
  (get_scripts)
  (println "------------------------------------------")
  (println "------------------------------------------"))
