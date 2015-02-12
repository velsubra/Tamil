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
    console.log("context:" + this.context);


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
        var cachedJoin = new Object();
        var cachedNoJoin = new Object();

        this.language = language;

        this.puturl = this.context + "/api/translit/one/";
        this.geturl = this.context + "/api/translit/one/?word=";


        /**
         * Transliterates a string into Tamil. Un-recognized character for   transliteration will be left as they are.
         * <p>
         *<b>Usage:  {@link TamilFactory}.createTransliterator().transliterate('thamizh')</b>
         * </p>
         * @param {string } word - the word to be transliterated. This can have parts in Tamil as well.
         * @param {boolean} join - the flag to indicate if the  transliterated word should be joined using புணர்ச்சி
         * @return {json} - the Tamil transliterated object.
         *        <b> json.tamil </b> gives the transliterated string. No English letters will be present in it.
         */
        this.transliterate = function (word, join) {
            if (word == "") {
                return word;
            }
            cached = join ? cachedJoin : cachedNoJoin;
            existing = cached[word];
            if (existing) {
                return existing;
            }
            if (word.length > 20) {
                method = "PUT";
                url = this.puturl + (join ? "?join=true" : "");
                content = word;
            } else {
                method = "GET";
                url = this.geturl + encodeURI(word) + (join ? "&join=true" : "");
                content = "";
            }
            result = $.parseJSON(jQuery.ajax({
                type: method,
                url: url,
                data: content,
                contentType: "text/plain; charset=utf-8",
                async: false
            }).responseText);
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


};



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
        tamil = SYS_TRANSLIT.transliterate($(this).val(), true).tamil;

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
        $(this).val(SYS_TRANSLIT.transliterate($(this).val(), true).tamil);
        $('#tamil_text').hide();
        $('#tamil_text').html("");
    });

    $(".tamil-text").on('blur', function () {
        $(this).val(SYS_TRANSLIT.transliterate($(this).val(), true).tamil);
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

