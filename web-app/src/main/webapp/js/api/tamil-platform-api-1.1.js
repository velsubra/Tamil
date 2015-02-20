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
        this.context = window.location.pathname.substring(0, window.location.pathname.indexOf("/apps/resources/"));
        // console.log("context:" + this.context);


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
            var cache = new Object();


            this.language = language;

            this.puturl = this.context + "/api/translit/one/";
            this.geturl = this.context + "/api/translit/one/?word=";


            /**
             * Transliterates a string into Tamil. Un-recognized character for   transliteration will be left as they are.
             * <p>
             *<b>Usage:  {@link TamilFactory}.createTransliterator().transliterate('thamizh')</b>
             * </p>
             * @param {string } word - the word to be transliterated. This can have parts in Tamil as well.
             * @param {string} features - the comma separated list of numbers indicating what features are to be used. Please refer to java doc of
             * the class tamil.lang.api.feature.Feature that defines various features as public static final variables.
             * The variable ends with number. You have to specify that number if you need the feature. E.g) TRANSLIT_JOIN_FEATURE_VAL_110 means the join feature.
             * If you need that feature, you have to specify "110" as the required feature. Multiple features can be specified. Features not-recognized will be ignored.
             * @return {json} - the Tamil transliterated object.
             *        <b> json.tamil </b> gives the transliterated string. No English letters will be present in it.
             */
            this.transliterate = function (word, features) {
                if (word == "") {
                    return word;
                }
                features = typeof features !== 'undefined' ? features.trim() : "0";
                if (!features) {
                    features = "0";
                }
                var thiscache = cache[features];
                if (!thiscache) {
                    cache[features] = new Object();
                    thiscache = cache[features];
                }

                var existing = thiscache[word];
                if (existing) {
                    return existing;
                }
                var result = "";

                if (typeof TAMIL_APPLET_INJECTED !== 'undefined') {
                    //If applet is initialized.
                    result = $.parseJSON(TAMIL_APPLET_INJECTED.transliterate(word, features));
                } else {

                    if (word.length > 20) {
                        method = "PUT";
                        url = this.puturl + (features && features != "0" ? "?features=" + features : "");
                        content = word;
                    } else {
                        method = "GET";
                        url = this.geturl + encodeURI(word) + (features && features != "0" ? "&features=" + features : "");
                        content = "";
                    }
                    result = $.parseJSON(jQuery.ajax({
                        type: method,
                        url: url,
                        data: content,
                        contentType: "text/plain; charset=utf-8",
                        async: false
                    }).responseText);
                }
                thiscache[word] = result;
                return result;
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
            this.geturl = this.context + "/api/number/reader/one/?number=";

            /**
             * Method to read the number as tamil text
             * @param word  the number as a string
             * @param features  the comma separated list of features to be used.
             * {json} - the Tamil text as the number is read.
             *        <b> json.tamil </b> gives the actual string. No digits  will be present in it.
             *        <b> json.error-occurred </b> will be true if there is an error.
             *        {"error":"true","emessage":"Index:9 Invalid character:w"}
             */
            this.readNumber = function (word, features) {
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

                if (typeof TAMIL_APPLET_INJECTED !== 'undefined') {
                    result = $.parseJSON(TAMIL_APPLET_INJECTED.readNumber(word, features));
                } else {


                    method = "GET";
                    url = this.geturl + encodeURI(word);
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
        tamil = SYS_TRANSLIT.transliterate($(this).val(), true).tamil;
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
        $('#tamil_text').hide();
        $('#tamil_text').html("");
    });

    $(".tamil-text").on('blur', function () {
        $(this).val(SYS_TRANSLIT.transliterate($(this).val(), "110").tamil);
        $('#tamil_text').hide();
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

