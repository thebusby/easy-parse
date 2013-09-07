(ns easy-parse-test
  (:require [clojure.test :refer :all]
            [easy-parse :refer :all]
            [clojure.zip :as zip]
            [clojure.data.xml :as xml]
            [clojure.data.zip.xml :as zf]
            [clojure.data.zip :as z]))

(def test-data
  [{:input "
<RESPONSES>
 <RESPONSE STATUS= \"OK\">
   <USER>foo-bar</USER>
 </RESPONSE>
</RESPONSES>
"
    :parse-fmt
    {:response-status #(zf/xml1-> % :RESPONSE (zf/attr :STATUS))
     :user-id         #(zf/xml1-> % :RESPONSE :USER zf/text)}
    :expected {:response-status "OK" :user-id "foo-bar"}}
   {:input "
<RESPONSES>
 <RESPONSE STATUS=\"OK\">
   <ALBUM>
      <GN_ID>153857537-C0E89B7AA71C102BD8986ADD02B0DE9C</GN_ID>
      <ARTIST>Perfume</ARTIST>
      <TITLE>GAME</TITLE>
      <PKG_LANG>JPN</PKG_LANG>
      <DATE>2008</DATE>
      <GENRE NUM=\"105247\" ID=\"35495\">ダンス &amp; クラブ</GENRE>
      <MATCHED_TRACK_NUM>5</MATCHED_TRACK_NUM>
      <TRACK_COUNT>12</TRACK_COUNT>
      <TRACK>
         <TRACK_NUM>5</TRACK_NUM>
         <GN_ID>153857542-F5B1994A6C9EBF79D7FFB02EDF189A1D</GN_ID>
         <TITLE>チョコレイト・ディスコ</TITLE>
      </TRACK>
   </ALBUM>
 </RESPONSE>
</RESPONSES>
"
    :parse-fmt
    {:response-status #(zf/xml1-> % :RESPONSE (zf/attr :STATUS))
     :album
     [#(zf/xml-> % :RESPONSE :ALBUM)
      {:gn-id  #(zf/xml1-> % :GN_ID zf/text)
       :artist #(zf/xml1-> % :ARTIST zf/text)
       :title #(zf/xml1-> % :TITLE zf/text)
       :pkg-lang #(zf/xml1-> % :PKG_LANG zf/text)
       :date #(zf/xml1-> % :DATE zf/text)
       :genre [#(zf/xml-> % :GENRE)
               {:id #(zf/xml1-> % (zf/attr :ID))
                :num #(zf/xml1-> % (zf/attr :NUM))
                :genre #(zf/xml1-> % zf/text)}]
       :matched-track-num #(zf/xml1-> % :MATCHED_TRACK_NUM zf/text)
       :track-count #(zf/xml1-> % :TRACK_COUNT zf/text)
       :track [#(zf/xml-> % :TRACK)
               {:track-num #(zf/xml1-> % :TRACK_NUM zf/text)
                :gn-id  #(zf/xml1-> % :GN_ID zf/text)
                :title #(zf/xml1-> % :TITLE zf/text)}]}]}
    :expected
    {:response-status "OK"
     :album [{:gn-id "153857537-C0E89B7AA71C102BD8986ADD02B0DE9C"
              :artist "Perfume"
              :title "GAME"
              :pkg-lang "JPN"
              :date "2008"
              :genre [{:genre "ダンス & クラブ", :num "105247", :id "35495"}],
              :matched-track-num "5"
              :track-count "12"
              :track [{:track-num "5"
                       :gn-id "153857542-F5B1994A6C9EBF79D7FFB02EDF189A1D"
                       :title "チョコレイト・ディスコ"}]}]}}
   ])

(defn- parse-xml [input parse-fmt]
  (when-let [xml-obj (xml/parse-str input)]
    (let [zip (zip/xml-zip xml-obj)]
      (->> ((partial easy-parse zip) parse-fmt)
           (into {})))))

(deftest test-easy-parse
  (testing "easy-parse"
    (doseq [{:keys [input parse-fmt expected]} test-data]
      (is (= (parse-xml input parse-fmt) expected)))))
