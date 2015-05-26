/**
 * Tamil platform Javascript APIs  that can be imported by application javascript resources.
 * This javascript library works only within the Tamil Platform.   This can be included in an html resource as follows.
 *
 <script src="${R_JQUERY_JS_PATH}" type="text/javascript" charset="utf-8"></script>
 <script src="${R_PLATFORM_JS_PATH}" type="text/javascript" charset="utf-8"></script>

 <script type='text/javascript'>//<![CDATA[

 var trans = TamilFactory.createTransliterator(); // This can be cached for repeated usages.
 var tamil_text = trans.transliterate('vaNakkam').tamil;  // This will return  வணக்கம்

 //]]>
 </script>
 *
 * @author velsubra
 */


/**
 * TamilFactory - Starting point for Tamil Platform. This object is ready to be used.
 * <p>
 *
 * @see {@link TamilFactory#createResourceAccessor}
 * <br/>
 * @see {@link TamilFactory#createTransliterator}
 * </p>
 * @type  {Object}
 * @property {string} version - Version of the platform.
 * @property {string} context - Context root of the platform.
 *
 *
 */
var TamilFactory = new function () {

        /**
         * The version of the platform.
         * @type {string}
         * @const
         */
        this.version = "1.1";

        /**
         * The context root of the platform ReST API.
         * @type {string}
         * @const
         */
        this.context = window.location.pathname.substring(0, window.location.pathname.indexOf("/apps/resources/") < 0 ? window.location.pathname.indexOf("/", 1) : window.location.pathname.indexOf("/apps/resources/"));
        //   console.log("context:" + this.context);


        /**
         * Creates a dictionary instance.
         * This can be cached for repeated execution such that previously looked up  words are returned from cache.
         * <p>
         * <b>Usage: {@link TamilFactory}.createDictionary(4)</b>
         * </p>
         * @constructor
         * @param max  the maximum number of results  to be returned.
         *
         */

        this.createDictionary = function (max , path) {
            //private properties
            var cacheDictionary = new Object();

            if (!max) {
                max = "1";
            }
            this.dictionary_subpath = typeof path !== 'undefined' ? path.trim() : "";
            if (this.dictionary_subpath) {
                this.dictionary_subpath = "dictionary/" + this.dictionary_subpath;
            }
            if (this.dictionary_subpath && this.dictionary_subpath.length > 1 && this.dictionary_subpath.indexOf("/") != 0  ) {
                this.dictionary_subpath = "/" + this.dictionary_subpath;
            }

            this.getdicturl = this.context + "/api" +  this.dictionary_subpath +"/dictionary/search/?max=" + max + "&word=";
            this.get_base_types = this.context + "/api" +  this.dictionary_subpath +"/dictionary/base-types/";

            this.get_word_types = this.context + "/api" +  this.dictionary_subpath +"/dictionary/word-types/";
            this.size_of_words = this.context + "/api" +  this.dictionary_subpath +"/dictionary/size/";

            // this.putdicturl = this.context + "/api/parse/bulk/?max=" + max + "&word=";


            /**
             *  searches the dictionary asynchronously.
             *
             * @param callback  the callback to receive the result.
             * @param word   the word to be looked-up. Word may be filtered to remove non-tamil characters from.
             * @param features  the features.
             *
             * @return  {json}   returns   non empty list  when words are found.
             * {"given":"கற்பனை","list":[{"type":"பெயர்ச்சொல்","tamil":"கற்பனை"},{"type":"வேற்றுமைத்தொடர்","tamil":"கற்பனைக்கண்"},{"type":"வேற்றுமைத்தொடர்","tamil":"கற்பனைக்கு"},{"type":"வேற்றுமைத்தொடர்","tamil":"கற்பனையது"},{"type":"வேற்றுமைத்தொடர்","tamil":"கற்பனையால்"}]}
             * when parsing succeeds,
             *
             *
             *
             */
            this.searchAsync = function (callback, word, features) {
                //  console.log("Dictionary:" + word);
                this.dosearch(callback, word, features);
            }

            /**
             * Looks-up  a word from local cache.
             * @param word   the word that is already parsed.
             * @param features  the features
             *
             * @return {json} - Please refer to method searchAsync  for return type   details
             */
            this.lookupDictionaryCache = function (word, features) {

                features = typeof features !== 'undefined' ? features.trim() : "0";
                if (!features) {
                    features = "0";
                }
                var thiscache_cacheDictionary = cacheDictionary[features];
                if (!thiscache_cacheDictionary) {
                    return null;
                }

                return thiscache_cacheDictionary[word];

            }


            //private base method.
            this.dosearch = function (callback, word, features) {

                features = typeof features !== 'undefined' ? features.trim() : "0";
                if (!features) {
                    features = "0";
                }
                var thiscache_cacheDictionary = cacheDictionary[features];
                if (!thiscache_cacheDictionary) {
                    cacheDictionary[features] = new Object();
                    thiscache_cacheDictionary = cacheDictionary[features];
                }

                var existing = thiscache_cacheDictionary[word];
                if (existing) {

                    callback(existing)
                    return;

                }
                var result = "";
                var url = null;
                var done = false;
                //if (typeof TAMIL_APPLET_INJECTED !== 'undefined') {  //Applets is not yet supporting this.
                //    //If applet is initialized.
                //    // console.log(word);
                //    try {
                //        result = $.parseJSON(TAMIL_APPLET_INJECTED.parse(word, features));
                //        // console.log(result);
                //        thiscache_cacheDictionary[word] = result;
                //        done = true;
                //
                //        if (callback) {
                //            callback(result);
                //        } else {
                //            return result;
                //        }
                //    } catch (e) {
                //        console.log(e);
                //    }
                //}
                if (!done) { // say if there is an issue with applet.


                    method = "GET";
                    url = this.getdicturl + encodeURI(word) + (features && features != "0" ? "&features=" + features : "");
                    content = "";

                    jQuery.ajax({
                        type: method,
                        url: url,
                        data: content,
                        contentType: "text/plain; charset=utf-8",
                        async: true,
                        success: function (data, status, jqXHR) {
                            //  filterDuplicateEntries(data);
                            thiscache_cacheDictionary[word] = data;
                            callback(data);
                        }
                    });

                }


            }


            /**
             * Gets all parent types for a given type
             * @param callback  the callback.
             * @param type the fully qualified name of the type extending or implementing   tamil.lang.known.IKnownWord
             *
             */
            this.getParentTypesAsync = function (callback, type) {

                type = typeof type !== 'undefined' ? type.trim() : "tamil.lang.known.IKnownWord";
                if (!type) {
                    features = "tamil.lang.known.IKnownWord";
                }
                var thiscache_cacheDictionary = cacheDictionary["base-types"];
                if (!thiscache_cacheDictionary) {
                    cacheDictionary["base-types"] = new Object();
                    thiscache_cacheDictionary = cacheDictionary["base-types"];
                }

                var existing = thiscache_cacheDictionary[type];
                if (existing) {

                    callback(existing);
                    return;

                }
                var result = "";
                var url = null;
                var done = false;
                //if (typeof TAMIL_APPLET_INJECTED !== 'undefined') {  //Applets is not yet supporting this.
                //    //If applet is initialized.
                //    // console.log(word);
                //    try {
                //        result = $.parseJSON(TAMIL_APPLET_INJECTED.parse(word, features));
                //        // console.log(result);
                //        thiscache_cacheDictionary[word] = result;
                //        done = true;
                //
                //        if (callback) {
                //            callback(result);
                //        } else {
                //            return result;
                //        }
                //    } catch (e) {
                //        console.log(e);
                //    }
                //}
                if (!done) { // say if there is an issue with applet.


                    method = "GET";
                    url = this.get_base_types + encodeURI(type) +"/";
                    content = "";

                    jQuery.ajax({
                        type: method,
                        url: url,
                        data: content,
                        contentType: "text/plain; charset=utf-8",
                        async: true,
                        success: function (data, status, jqXHR) {
                            //  filterDuplicateEntries(data);
                            thiscache_cacheDictionary[type] = data;
                            callback(data);
                        }
                    });

                }


            }


            /**
             * Gets all word types that are available in the dictionary
             * @param callback  the callback.
             *
             *
             */
            this.getWordTypesAsync = function (callback) {



                var url = null;
                var done = false;
                //if (typeof TAMIL_APPLET_INJECTED !== 'undefined') {  //Applets is not yet supporting this.
                //    //If applet is initialized.
                //    // console.log(word);
                //    try {
                //        result = $.parseJSON(TAMIL_APPLET_INJECTED.parse(word, features));
                //        // console.log(result);
                //        thiscache_cacheDictionary[word] = result;
                //        done = true;
                //
                //        if (callback) {
                //            callback(result);
                //        } else {
                //            return result;
                //        }
                //    } catch (e) {
                //        console.log(e);
                //    }
                //}
                if (!done) { // say if there is an issue with applet.


                    method = "GET";
                    url = this.get_word_types;
                    content = "";

                    jQuery.ajax({
                        type: method,
                        url: url,
                        data: content,
                        contentType: "text/plain; charset=utf-8",
                        async: true,
                        success: function (data, status, jqXHR) {
                            callback(data);
                        }
                    });

                }


            }


            /**
             * Gets the size of the dictionary
             * @param callback  the callback.
             *
             *
             */
            this.sizeAsync = function (callback) {



                var url = null;
                var done = false;
                //if (typeof TAMIL_APPLET_INJECTED !== 'undefined') {  //Applets is not yet supporting this.
                //    //If applet is initialized.
                //    // console.log(word);
                //    try {
                //        result = $.parseJSON(TAMIL_APPLET_INJECTED.parse(word, features));
                //        // console.log(result);
                //        thiscache_cacheDictionary[word] = result;
                //        done = true;
                //
                //        if (callback) {
                //            callback(result);
                //        } else {
                //            return result;
                //        }
                //    } catch (e) {
                //        console.log(e);
                //    }
                //}
                if (!done) { // say if there is an issue with applet.


                    method = "GET";
                    url = this.size_of_words;
                    content = "";

                    jQuery.ajax({
                        type: method,
                        url: url,
                        data: content,
                        contentType: "text/plain; charset=utf-8",
                        async: true,
                        success: function (data, status, jqXHR) {
                            callback(data);
                        }
                    });

                }


            }

            return this;
        }


        /**
         * Creates a compound word (தொடர்மொழி) parser.
         * This can be cached for repeated execution such that previously parsed text are returned from cache.
         * <p>
         * <b>Usage: {@link TamilFactory}.createParser()</b>
         * </p>
         * @constructor
         * @param max  the maximum number of parse results to be returned.
         *
         */

        this.createParser = function (max) {
            //private properties
            var cacheParser = new Object();
            if (!max) {
                max = "1";
            }

            this.getparserurl = this.context + "/api/parse/one/?max=" + max + "&word=";
            this.putparserurl = this.context + "/api/parse/bulk/?max=" + max + "&word=";


            /**
             *  Parses asynchronously.
             *
             * @param callback  the callback to receive the result.
             * @param word   the word to be parsed. Word may be filtered to remove non-tamil characters from.
             * @param features  the features.
             *
             * @return  {json}   returns
             * {"parsed":"true","given":"..கற்பனையாகவோ?","splitways":[{"splits":[{"type":"பெயர்ச்சொல்","tamil":"கற்பனை"},{"type":"வினையெச்சம்","tamil":"ஆக"},{"type":"இடைச்சொல்","tamil":"ஓ"}],"tamil":"கற்பனையாகவோ"}]}
             * when parsing succeeds,
             *
             * {"parsed":"false","given":"கற்ப"} otherwise.
             *
             */
            this.parseAsync = function (callback, word, features) {

                this.doparse(callback, word, features);
            }

            /**
             * Looks-up the parsed word from local cache.
             * @param word   the word that is already parsed.
             * @param features  the features
             *
             * @return {json} - Please refer to method parseAsync  for return type   details
             */
            this.lookupParserCache = function (word, features) {

                features = typeof features !== 'undefined' ? features.trim() : "0";
                if (!features) {
                    features = "0";
                }
                var thiscache_cacheParser = cacheParser[features];
                if (!thiscache_cacheParser) {
                    return null;
                }

                return thiscache_cacheParser[word];

            }


            //private base method.
            this.doparse = function (callback, word, features) {

                features = typeof features !== 'undefined' ? features.trim() : "0";
                if (!features) {
                    features = "0";
                }
                var thiscache_cacheParser = cacheParser[features];
                if (!thiscache_cacheParser) {
                    cacheParser[features] = new Object();
                    thiscache_cacheParser = cacheParser[features];
                }

                var existing = thiscache_cacheParser[word];
                if (existing) {

                    callback(existing)
                    return;

                }
                var result = "";
                var done = false;
                var url = null;
                //if (typeof TAMIL_APPLET_INJECTED !== 'undefined') {  //Applets is not yet supporting this.
                //    //If applet is initialized.
                //    // console.log(word);
                //    try {
                //        result = $.parseJSON(TAMIL_APPLET_INJECTED.parse(word, features));
                //        // console.log(result);
                //        thiscache_cacheParser[word] = result;
                //        done = true;
                //
                //        if (callback) {
                //            callback(result);
                //        } else {
                //            return result;
                //        }
                //    } catch (e) {
                //        console.log(e);
                //    }
                //}
                if (!done) { // say if there is an issue with applet.


                    method = "GET";
                     url = this.getparserurl + encodeURI(word) + (features && features != "0" ? "&features=" + features : "");
                    content = "";

                    jQuery.ajax({
                        type: method,
                        url: url,
                        data: content,
                        contentType: "text/plain; charset=utf-8",
                        async: true,
                        success: function (data, status, jqXHR) {
                            thiscache_cacheParser[word] = data;
                            callback(data);

                        }
                    });

                }


            }

            return this;
        }


        /**
         * Creates  Transliterator  to Tamil. This can be cached for repeated execution of the same resource.
         * <p>
         * <b>Usage: {@link TamilFactory}.createTransliterator()</b>
         * </p>
         * @constructor
         * @param {string} language - the id for source language, It is ignored now as English is the only language supported.
         *
         *
         */

        this.createTransliterator = function (language) {


            //private properties
            var cache_translit = new Object();

            var BG_HANDLE = null;


            this.language = language;

            this.puturl = this.context + "/api/translit/one/";
            this.geturl = this.context + "/api/translit/one/?word=";


            /**
             * Transliterates a string into Tamil. Un-recognized character for   transliteration will be left as they are.
             * <p>
             * <b>Usage:  {@link TamilFactory}.createTransliterator().transliterate('thamizh')</b>
             * </p>
             * @param {string } word - the word to be transliterated. This can have parts in Tamil as well.
             * @param {string} features - the comma separated list of numbers indicating what features are to be used. Please refer to java doc of
             * the class tamil.lang.api.feature.FeatureConstants that defines various features as public static final variables.
             * The variable ends with number. You have to specify that number if you need the feature. E.g) TRANSLIT_JOIN_FEATURE_VAL_110 means the join feature.
             * If you need that feature, you have to specify "110" as the required feature. Multiple features can be specified. Features not-recognized will be ignored.
             * @return {json} - the Tamil transliterated object.
             *        <b> json.tamil </b> gives the transliterated string. No English letters will be present in it.
             *        <b> json.given </b> gives the origianl text.
             */

            this.transliterate = function (word, features) {
                return this.transWrapper(null, word, features);
            }

            /**
             *  Transliterates asynchronously
             *
             * @param callback  the callback to receive the result.
             * @param word   the word to be transliterated.
             * @param features  the features.
             */
            this.transliterateAsync = function (callback, word, features) {
                this.dotransliterate(callback, word, features);
            }

            // Private function.
            this.transWrapper = function (callback, word, features) {

                var ret = "";
                var w = "";
                var json;
                var needsTranslit = false;
                for (var i = 0; i < word.length; i++) {
                    var ch = word.charAt(i);
                    var chval = word.charCodeAt(i);
                    if (ch == "\n" || ch == " " || i == word.length - 1) {
                        if (ch == "\n" || ch == " ") {
                            if (w) {
                                if (needsTranslit) {
                                    //Async is really broken at this level; //TODO: fix
                                    json = this.dotransliterate(null, w, features);
                                    if (json.error) {
                                        return json;
                                    }
                                    ret += json.tamil;
                                } else {
                                    ret += w;
                                }
                            }
                            ret += ch;

                        } else { //last char
                            w += ch;
                            if (chval < 128) {
                                needsTranslit = true;
                            }
                            if (needsTranslit) {
                                //Async is really broken at this level; //TODO: fix
                                json = this.dotransliterate(null, w, features);
                                if (json.error) {
                                    return json;
                                }
                                ret += json.tamil;
                            } else {
                                ret += w;
                            }
                        }

                        w = "";
                        needsTranslit = false;
                    } else {
                        if (chval < 128) {
                            needsTranslit = true;
                        }

                        w += ch;
                    }
                }
//                console.log("input:" + word + " output:" + ret);
                var retjson = {
                    "tamil": ret ? ret : word,
                    "given": word

                };
                if (callback) {
                    callback(retjson);
                } else {
                    return retjson;
                }
            }


            //private base method.
            this.dotransliterate = function (callback, word, features) {

                var empty_json = {
                    "tamil": "",
                    "given": ""

                };

                if (word === "") {
                    if (callback) {
                        callback(empty_json);
                        return;
                    } else {
                        return empty_json;
                    }
                }

                features = typeof features !== 'undefined' ? features.trim() : "0";
                if (!features) {
                    features = "0";
                }
                var thiscache_cache = cache_translit[features];
                if (!thiscache_cache) {
                    cache_translit[features] = new Object();
                    thiscache_cache = cache_translit[features];
                }

                var existing = thiscache_cache[word];
                if (existing) {
                    if (callback) {
                        callback(existing);
                        return;
                    } else {
                        return existing;
                    }
                }
                var result = "";
                var done = false;
                var url = null;

                if (typeof TAMIL_APPLET_INJECTED !== 'undefined') {
                    //If applet is initialized.
                    // console.log(word);
                    try {
                        result = $.parseJSON(TAMIL_APPLET_INJECTED.transliterate(word, features));
                        // console.log(result);
                        thiscache_cache[word] = result;
                        done = true;
                      //  console.log("Using applet......");
                       // console.log(result);
                        if (callback) {
                            callback(result);
                            return;
                        } else {
                            return result;
                        }
                    } catch (e) {
                        console.log(e);
                    }
                } else {
                  //  console.log("Applet not found ... " + TAMIL_APPLET_INJECTED);
                }
                if (!done) { // say if there is an issue with applet.

                    if (word.length > 20) {
                        method = "PUT";
                        url = this.puturl + (features && features != "0" ? "?features=" + features : "");
                        content = word;
                    } else {
                        method = "GET";
                        url = this.geturl + encodeURI(word) + (features && features != "0" ? "&features=" + features : "");
                        content = "";
                    }
                    if (!callback) {
                        result = $.parseJSON(jQuery.ajax({
                            type: method,
                            url: url +"&sync",
                            data: content,
                            contentType: "text/plain; charset=utf-8",
                            async: false
                        }).responseText);
                        thiscache_cache[word] = result;
                        return result;
                    } else {
                        if (BG_HANDLE) {
                            clearTimeout(BG_HANDLE);
                          //  console.log("Clearing async call ...");
                        }
                        BG_HANDLE = setTimeout(function() {
                            existing = thiscache_cache[word];
                            if (existing) {
                                callback(existing);
                                return;
                            }

                            //console.log("Making async call ..." + url);

                            jQuery.ajax({
                                type: method,
                                url: url+"&async",
                                data: content,
                                contentType: "text/plain; charset=utf-8",
                                async: true,
                                success: function (data, status, jqXHR) {
                                    thiscache_cache[word] = data;
                                    callback(data);
                                   // console.log("Passing async response ..---------*****---------." + data.splitways);
                                    return;
                                }
                            })}, 100);
                    }
                }


            }

            return this;
        }


        /**
         * Creates a new number reader object.
         * This is convert a number string into Tamil text.
         * <pre>12011 - பன்னிரண்டாயிரத்து பதினொன்று         </pre>
         *
         *  @constructor
         */
        this.createNumberReader = function () {
            //private properties
            var cachedNumbers = new Object();
            this.getnumberurl = this.context + "/api/number/reader/tonumber/one/?number=";
            this.gettexturl = this.context + "/api/number/reader/totext/one/?number=";


            /**
             * Method to read tamil number text as number
             * @param word  the number in text form.
             * @param features  the comma separated list of features to be used.
             * {json} - the Tamil text as the number is read.
             *        <b> json.number </b> gives the actual number read
             *        <b> json.error </b> will be true if there is an error.
             *        {"error":"true","emessage":"Index:9 Invalid character:w"}
             */
            this.readAsNumber = function (word, features) {
                return readNumberCallApi(this.getnumberurl, word,features, true);
            }



            /**
             * Method to read the number as tamil text
             * @param word  the number as a string
             * @param features  the comma separated list of features to be used.
             * {json} - the Tamil text as the number is read.
             *        <b> json.tamil </b> gives the actual string. No digits  will be present in it.
             *        <b> json.error </b> will be true if there is an error.
             *        {"error":"true","emessage":"Index:9 Invalid character:w"}
             */
            this.readNumber = function (word, features) {
                return readNumberCallApi(this.gettexturl, word,features,false);
            }


            readNumberCallApi = function (apiurl,word, features, texttonumber) {
                if (word == "") {
                    return word;
                }
                features = typeof features !== 'undefined' ? features.trim() : "0";
                if (features == "") {
                    features = "0";
                }
                cached = cachedNumbers[features];
                if (cached == null) {
                    cached = new Object();
                    cachedNumbers[features] = cached;
                }
                existing = cached[word];
                if (existing) {
                    return existing;
                }
                var result = "";
                var url = null;

                if (typeof TAMIL_APPLET_INJECTED !== 'undefined') {

                    var js = null;

                       if (texttonumber) {
                           console.log("read as number:" + word + ":" + features+ ":" + apiurl);
                           js = TAMIL_APPLET_INJECTED.readAsNumber(word, features);

                       } else {
                           js = TAMIL_APPLET_INJECTED.readNumber(word, features);
                       }
                    console.log(js);
                    result = $.parseJSON(js);
                   // console.log("Using applet......");
                    //    console.log(result);
                } else {


                    method = "GET";
                    url = apiurl + encodeURI(word);
                    if (features && features != "0") {
                        url += "&features=" + features;
                    }


                    result = $.parseJSON(jQuery.ajax({
                        type: method,
                        url: url,
                        contentType: "text/plain; charset=utf-8",
                        async: false
                    }).responseText);
                }
                cached[word] = result;
                return result;
            }
            return this;
        }


        /**
         * Creates an interface that can be used to  access a resource. This is typically used to execute server side scripts.
         * This can be cached for repeated execution of the same resource.
         * <p>
         * <b>Usage:  {@link TamilFactory}.createResourceAccessor('${R_APP_NAME}','groovy/test.gv')</b>
         * </p>
         * @constructor
         * @param {string} appname  - the name of the application. Defaulted all application root.
         * @param {string} resourcename - the name of the resource. Defaulted to home page.
         * @param {string}  method - the http method used to access, Defaulted to GET
         * @param {string} queryparams - query parameters any. E.g) a=b&c=d
         * @param {string} body - the body of the request (Non GET types)
         *
         *
         */
        this.createResourceAccessor = function (appname, resourcename, method, queryparams, body) {


            this.appname = typeof appname !== 'undefined' ? appname.trim() : "";
            this.resourcename = typeof resourcename !== 'undefined' ? resourcename : "";
            this.method = typeof method !== 'undefined' ? method : 'GET';
            this.queryparams = typeof queryparams !== 'undefined' ? queryparams : "";
            this.body = typeof body !== 'undefined' ? body : "";

            if (this.queryparams) {
                if (this.queryparams.indexOf("?") < 0 && this.queryparams.length > 0) {
                    this.queryparams += "?";
                }
            }
            if (this.appname.length > 0 && this.appname.indexOf("/") < 0) {
                this.appname += "/";
            }


            this.url = this.context + "/apps/resources/" + this.appname + this.resourcename;
            if (this.queryparams) {
                this.url += this.queryparams;
            }

            /**
             * Access the resource synchronously. If the resource was groovy script, it would execute.
             * <p>
             *  <b>Usage: {@link TamilFactory}.createResourceAccessor('${R_APP_NAME}','groovy/test.gv').access()</b>
             *  </p>
             *
             * @return {json} - the jqxhr  object returned.
             * <b> json.responseText </b> will return the responseText returned by the remote script (groovy/test.gv)
             *
             */
            this.access = function () {
                return jQuery.ajax({
                    type: this.method,
                    url: this.url,
                    data: this.body,
                    contentType: "text/plain; charset=utf-8",
                    async: false
                });
            }

            return this;

        }


    }
    ;


var SYS_TRANSLIT = TamilFactory.createTransliterator();


/**
 * Adds event on a element with  class.
 */
$(document).ready(function () {
    if ($('body').length) {
        $('body').append("<div id=\"tamil_text\" style=\"z-index:500;position:absolute;display:none\"></div>");
    }

    // Binds events for   tamil-text

    $(".tamil-text").on('focus', function () {
       // console.log($(this).val());
        tamil = SYS_TRANSLIT.transliterate($(this).val(), "110").tamil;
       // console.log(tamil);
        $(this).val(tamil);
        $(this).$popupDiv("#tamil_text");
        if (tamil) {
            if (tamil.indexOf("\n") >= 0) {
                $('#tamil_text').html("<pre>" + tamil + "</pre>");
            } else {
                $('#tamil_text').html(tamil);
            }
        }
    });

    $(".tamil-text").on('input', function () {
        tamil = SYS_TRANSLIT.transliterate($(this).val(), "110").tamil;

        $(this).$popupDiv("#tamil_text");
        if (tamil) {
            if (tamil.indexOf("\n") >= 0) {
                $('#tamil_text').html("<pre>" + tamil + "</pre>");
            } else {
                $('#tamil_text').html(tamil);
            }
        }

    });


    $(".tamil-text").on('enter', function () {
        $(this).val(SYS_TRANSLIT.transliterate($(this).val(), "110").tamil);
       // $('#tamil_text').hide();
        $('#tamil_text').html("");
    });

    $(".tamil-text").on('blur', function () {
        $(this).val(SYS_TRANSLIT.transliterate($(this).val(), "110").tamil);
       // $('#tamil_text').hide();
        $('#tamil_text').html("");
    });

});

/**
 * Show a pop up div for a given component
 * @param divToPop , the div to popup
 */
jQuery.fn.$popupDiv = function (divToPop) {
    var pos = $(this).offset();
    var h = $(this).height();
    // var w = $(this).width();

    $(divToPop).css({left: pos.left, top: pos.top + h + 10});
    $(divToPop).show();

};

/**
 Javascript string to byte[]
 */
String.prototype.getBytes = function () {
    return toUTF8Array(this);
}

function toUTF8Array(str) {
    var arr = [];
    for (var i = 0; i < str.length; i++) {
        arr.push(str.charCodeAt(i))


    }
    return arr;
}
