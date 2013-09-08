(defproject easy-parse "1.4.4-SNAPSHOT"
  :description "Library to simplify the parsing of Documents, especially XML"
  :url         "https://github.com/thebusby/easy-parse"
  :license     {:name "MIT"
                :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.4.0"]]
  :profiles
  {:dev
   {:dependencies [[org.clojure/data.xml	"0.0.7"]
                   [org.clojure/data.zip	"0.1.1"]]}})
