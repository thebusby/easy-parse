Available via [clojars](https://clojars.org/easy-parse)    
Current stable version: [easy-parse "1.4.3"]


# easy-parse

A simple library to easy Document parsing.

## Usage
```clojure
(def myspec {:ALBUM_NAME  #(zf/xml1-> %1 :RESPONSE :ALBUM :TITLE :DISPLAY zf/text)
             :TRACKS     [#(zf/xml-> %1 :RESPONSE :ALBUM :TRACK)
                           {:TRACK_NAME #(zf/xml1-> %1 :TITLE :DISPLAY zf/text)
                            :TRACK_NUM  #(zf/xml1-> %1 :ORD zf/text)}]})
```


## Artifacts

easy-parse artifacts are [released to Clojars](https://clojars.org/easy-parse).

If you are using Maven, add the following repository definition to your `pom.xml`:

``` xml
<repository>
  <id>clojars</id>
  <url>http://clojars.org/repo</url>
</repository>
```

### The Most Recent Release

With Leiningen:

    [easy-parse "1.4.3"]


With Maven:

    <dependency>
      <groupId>easy-parse</groupId>
      <artifactId>easy-parse</artifactId>
      <version>1.4.3</version>
    </dependency>


## License

MIT
http://opensource.org/licenses/MIT

Copyright (C) 2013 Alan Busby
