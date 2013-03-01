(ns personal_website.utils
  (:require [net.cgrand.enlive-html :as html])
  (:use [ring.adapter.jetty :only [run-jetty]]
        [ring.util.response :only [response file-response]]
        [ring.middleware.reload :only [wrap-reload]]
        [ring.middleware.file :only [wrap-file]]
        [ring.middleware.stacktrace :only [wrap-stacktrace]]))

(def *webdir* (format "%s/src/personal_website/" (System/getProperty "user.dir")))

(defn render [t]
  (apply str t))

(defn render-snippet [s]
  (apply str (html/emit* s)))

(def render-to-response
     (comp response render))

(defn page-not-found [req]
  {:status 404
   :headers {"Content-type" "text/html"}
   :body "Page Not Found"})

(defn render-request [afn & args]
  (fn [req] (render-to-response (apply afn args))))

(defn serve-file [filename]
  (file-response
   {:root *webdir*
    :index-files? true
    :html-files? true}))

(defn run-server* [app & {:keys [port] :or {port 8000}}]
  (let [nses (if-let [m (meta app)]
               [(-> (:ns (meta app)) str symbol)]
               [])]
    (println "run-server*" nses)
    (run-jetty
     (-> app
         (wrap-file *webdir*)
         (wrap-reload nses)
         (wrap-stacktrace))
     {:port port :join? false})))


(defmacro run-server [app]
  `(run-server* (var ~app)))


(defn find-port [app & {:keys [port] :or {port 8000}}]
  (letfn [(port-help [p next_p]
          (try (run-server* app)
               (catch Exception e
                 (if (> p 9000)
                         (str "Too many ports are in use! "
                              "Try shutting down some processes and try again.")
                         (do (println "port: " p "is in use." "\n" e)
                             (port-help next_p (+ 100 next_p)))))))]
               (if (not= port 8000)
                 (port-help port 8000)
                 (port-help port (+ 100 port)))))


    
(defmulti parse-int type)
(defmethod parse-int java.lang.Integer [n] n)
(defmethod parse-int java.lang.String [s] (Integer/parseInt s))

(defmacro maybe-substitute
  ([expr] `(if-let [x# ~expr] (html/substitute x#) identity))
  ([expr & exprs] `(maybe-substitute (or ~expr ~@exprs))))

(defmacro maybe-content
  ([expr] `(if-let [x# ~expr] (html/content x#) identity))
  ([expr & exprs] `(maybe-content (or ~expr ~@exprs))))

(defn pluralize [astr n]
  (if (= n 1)
    (str astr)
    (str astr "s")))

(defmacro slime_use []
  `(do (use '[personal_website.core :as core])
      (use '[personal_website.defaults :as defaults])
      (use '[personal_website.utils :as utils])
      (use '[personal_website.main_template :as main_template])))
