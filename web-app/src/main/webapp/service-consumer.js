//Enable this for server deployment
var server = "rest/";

//Enable this for local deployment , For server deployment comment-out.
server = "";


var current_questions_id = null;
var current_answer_id = null;


var map_pages = new Object();
map_pages['edit-verb.html'] = 'வினைமுற்று';
map_pages['edit-peyarechcham.html'] = 'பெயரெச்சம்';
map_pages['edit-ethirmarrai-peyarechcham.html'] = 'எதிர்மறைப்பெயரெச்சம்';
map_pages['edit-vinaiyechcham.html'] = 'வினையெச்சம்';
map_pages['edit-vinaiyaalanaiyum-peyar.html'] = 'வினையாலணையும்பெயர்';
map_pages['edit-ethirmarrai-vinaiyaalanaiyum-peyar.html'] = 'எதிர்மறைவினையாலணையும்பெயர்';
map_pages['edit-thozhirrPeyar.html'] = 'தொழிற்பெயர் ';
map_pages['thodar-muttukal.html'] = 'தொடர்வினைமுற்று';
map_pages['muttu-muttukal.html'] = 'முற்றுவினைமுற்று';
map_pages['thodar-muttu-muttukal.html'] = 'தொடர்முற்றுவினைமுற்று'
map_pages['edit-vinai-english.html'] = 'ஆங்கிலப்பொருள்';


var global_prop_values = new Object();


var map_idai_pages = new Object();
map_idai_pages['edit-preposition.html'] = 'விவரக்குறிப்புகள் ';


var map_peyar_pages = new Object();
map_peyar_pages['edit-noun.html'] = 'விவரக்குறிப்புகள் ';
map_peyar_pages['edit-noun-english.html'] = 'ஆங்கிலப்பொருள்';


var map_suggestion_word = new Object();


function _getSuggestionFor(word, correct) {
    if (word == null) return [];

    var suggestion = map_suggestion_word[word];
    if (suggestion != null) {
        return suggestion;
    }

    suggestion = JSON.parse(jQuery.ajax({
        type: "PUT",
        data: word + "",
        url: server + "lookup/words/?maxcount=10&suggest=" + correct,
        contentType: "text/plain; charset=utf-8",

        async: false
    }).responseText).results;
    map_suggestion_word[word] = suggestion;
    return suggestion;

}


function _setupVinaiyadiTabs(vinai) {

    _setupAllTabs(map_pages, vinai);

}

function _setupIdaiTabs(idai) {

    _setupAllTabs(map_idai_pages, idai);

}


function _setupPeyarTabs(peyar) {

    _setupAllTabs(map_peyar_pages, peyar);

}

function _setupAllTabs(page_map, word) {

    var filename = window.location.pathname.replace(/^.*[\\\/]/, '')
    var first = true;
    var divcontent = "";
    for (var m in page_map) {
        var val = page_map[m];
        if (!first) {
            divcontent += " | ";
        }
        if (m == filename) {
            divcontent += "<label class='selecteditem'>" + val + "</label>";
        } else {
            divcontent += "<a href='" + m + "#" + encodeURI(word) + "'>" + val + "</a>";
        }

        first = false;
    }
    divcontent += "<hr/>";

    $("#_common_tabs").html(divcontent);

}

var property_name_map = new Object();
var global_properties = null;
var global_idai_properties = null;


var transMAP = new Object();
var listVerbsMap = new Object();

function TEMP_WORD(word) {
    this.tamilWord = word;
    this.known = true;
    this.actual = word;
    this.parsed = true;
}

function getTamilWord(eng, parse, arrayvalue, join) {
    if (eng == "") {
        return new TEMP_WORD(eng);
    }
    if (eng.indexOf("\\") == 0) {
        return new TEMP_WORD(eng.substring(1));
    }

    var t = transMAP[eng];
    if (t != null) {
        if (parse && !t.parsed) {
            t = null;
        }
    }
    if (t == null) {

        if (t == null) {
            t = _translitSync(eng, parse, arrayvalue, join);

        }

        transMAP[eng] = t;
        transMAP[t] = t;

    }
    return t;

}


function getTamilWordAsync(callback, eng, parse, arrayvalue, join) {

    if (eng == "") {
        return new TEMP_WORD(eng);
    }
    if (eng.indexOf("\\") == 0) {
        callback(TEMP_WORD(eng.substring(1)));
    }

    var t = transMAP[eng];
    if (t != null) {
        if (parse && !t.parsed) {
            t = null;
        }
    }
    if (t == null) {

        _translitAsync(function (result) {
            console.log(result);
            console.log(eng);
            transMAP[eng] = result;
            transMAP[result.tamilWord] = result;
            callback(result);
        }, eng, parse, arrayvalue, join);


    } else {
        callback(t);
    }

}


jQuery.ajaxSetup({
    beforeSend: function () {
        // show gif here, eg:
        $(".ajaxloading").css("border", "2px red");
        $(".ajaxloading").css("background", "url('progress.gif') 50% 50% no-repeat");

    },
    complete: function () {
        // hide gif here, eg:
        $(".ajaxloading").css("border", "1px green");
        $(".ajaxloading").css('background', 'none');
    }
});

(function ($) {
    $.fn.onEnter = function (func) {
        this.bind('keypress', function (e) {
            if (e.keyCode == 13) func.apply(this, [e]);
        });
        return this;
    };
})(jQuery);

function _get_handler_list() {
    jQuery.ajax({
        type: "GET",
        dataType: "json",
        url: server + "handlers/list/",
        success: function (data, status, jqXHR) {
            var options = $("#rule");
            $.each(data.handlers, function (item) {
                options.append($("<option />").val(data.handlers[item]).text(data.handlers[item]));
            });
        },
        error: function (jqXHR, status) {
            alert(status);
        }
    });
}

function _parse_load() {

    var joined = $(location).attr('hash');

    if (joined == "") {

        joined = $.cookie('joined');
    } else {
        joined = joined.slice(1);
        joined = decodeURI(joined)
        $.cookie('joined', joined);
    }
    if (joined != null) {
        $("#joined").val(joined);
        $("#joinedenglish").val(joined);
    }


}


function _split_load() {
    if ($.cookie('joined') != "") {
        $("#joined").val($.cookie('joined'));
        $("#joinedenglish").val($.cookie('joined'));
    }
    _get_handler_list();
}

function _join_load() {
    if ($.cookie('first') != "") {
        $("#first").val($.cookie('first'));
        $("#second").val($.cookie('second'));
        $("#firstenglish").val($.cookie('first'));
        $("#secondenglish").val($.cookie('second'));
    }
    _get_handler_list();
}

function _onkeypress(event, typed) {

    var ew = event.which;

    if (65 <= ew && ew <= 90)
        return true;
    if (97 <= ew && ew <= 122)
        return true;
    if (ew == 8 || ew == 46 || ew == 44) return true;
    if (event.keyCode == 37 || event.keyCode == 39 || event.keyCode == 13) return true;
    return false;

}

function _translitSync(english, parse, arrayvalue, join) {
    if (arrayvalue == null) {
        arrayvalue = false;
    }
    if (!join) {
        join = false;
    }

    return $.parseJSON(jQuery.ajax({
        type: "PUT",
        data: english + "",
        url: server + "punarchi/translit/" + (parse ? "?parse=true" : "?array=" + arrayvalue) + "&join=" + join,
        contentType: "text/plain; charset=utf-8",
        async: false
    }).responseText);

}


function _translitAsync(callback, english, parse, arrayvalue, join) {
    if (arrayvalue == null) {
        arrayvalue = false;
    }
    if (!join) {
        join = false;
    }

    jQuery.ajax({
        type: "PUT",
        data: english + "",
        url: server + "punarchi/translit/" + (parse ? "?parse=true" : "?array=" + arrayvalue) + "&join=" + join,
        contentType: "text/plain; charset=utf-8",
        success: function (data, status, jqXHR) {
            callback(data);
        }
    });

}


function _translitSyncBulk(english, join) {

    if (!join) {
        join = false;
    }
    var data = $.parseJSON(jQuery.ajax({
        type: "PUT",
        data: english + "",
        url: server + "punarchi/translitbulk/?join=" + join,
        contentType: "text/plain; charset=utf-8",
        async: false
    }).responseText);


    $.each(data, function (item) {
        var r = data[item];
        transMAP[r.actual] = r;
    });

}


function _translit(typed, txt, arrayvalue, tojoin) {
    if (typed.value == "") {
        $(txt).val("");
        $(txt).trigger("input");
        return;
    }
    var tamilword = getTamilWord(typed.value, false, arrayvalue, tojoin).tamilWord;// _translitSync(typed.value);
    if ($(txt).val != tamilword) {
        $(txt).val(tamilword);
        $(txt).trigger("input");
    }


}

function _silent_add_Vinayadi_property(vinaiyadi, propname, propvalue) {

    return _silent_add_property('root-verbs/verbs', vinaiyadi, propname, propvalue);


}

function _silent_add_property(base, word, propname, propvalue) {


    if (_is_IssueRelated(propname)) {
        $('#header-notes').trigger("click");
        $('.showhide-notes').show();

    }

    var failed = false;
    jQuery.ajax({
        type: "PUT",
        data: propvalue + "",
        url: server + base + "/name/" + encodeURI(word) + "/property/" + propname + "/",
        contentType: "text/plain; charset=utf-8",

        async: false,

        statusCode: {
            500: function (data, status, XHR) {
                alert(jQuery.parseJSON(data.responseText).message);
                failed = true;
            }
        }
    });

    //console.log(!failed);
    return !failed;
}


function _silent_get_property(base, word, propname) {

    return jQuery.ajax({
        type: "GET",
        url: server + base + "/name/" + encodeURI(word) + "/property/" + propname + "/",
        contentType: "text/plain; charset=utf-8",

        async: false,

        statusCode: {
            404: function (data, status, XHR) {
                return null;
            }
        }
    }).responseText;

}

function _getWordsCount_Verbs() {
    return _getWordsCount("root-verbs");
}

function _getWordsCount_Prepositions() {
    return _getWordsCount("root-prepositions");
}

function _getWordsCount_Nouns() {
    return _getWordsCount("root-nouns");
}

function _getWordsCount(base) {

    return jQuery.ajax({
        type: "GET",
        url: server + base + "/words/count/",
        contentType: "text/plain; charset=utf-8",

        async: false,

        statusCode: {
            404: function (data, status, XHR) {
                return "-1";
            }
        }
    }).responseText;

}

//Returns Properties
function _silent_get_properties_With_Start(base, word, propname) {

    var props = jQuery.ajax({
        type: "GET",
        url: server + base + "/name/" + encodeURI(word) + "/properties/?select=" + propname,
        contentType: "text/plain; charset=utf-8",

        async: false,

        statusCode: {
            404: function (data, status, XHR) {
                return null;
            }
        }
    }).responseText;

    if (props == null) {
        return null;
    }
    return JSON.parse(props);

}


function _join_handler() {
    var nilai = $("#first").val();
    var varum = $("#second").val();

    if (nilai == "") {
        alert("நிலைமொழியை இடுக!");
        $("#first").focus();
        return;

    }
    if (varum == "") {
        alert("வருமொழியை இடுக !");
        $("#second").focus();
        return;

    }
    $.cookie('first', nilai);
    $.cookie('second', varum);

    var handler = $("#rule").val();
    jQuery.ajax({
        type: "PUT",
        data: handler,
        url: server + "punarchi/join/" + encodeURI(nilai) + "/" + encodeURI(varum) + "/",
        contentType: "text/plain; charset=utf-8",
        success: function (data, status, jqXHR) {

            $("#result_table tr:gt(0)").detach();
            var table = $("#result_table tr:last");
            $.each(data, function (item) {

                table.after('<tr> <th>' + data[item].handlerName + '</th><td>' + data[item].result + ' </td> </tr>');
                // alert(data[item].handlerName);
            });
        },
        error: function (jqXHR, status) {
            //  alert(status);
        }
    });
}


function _loadlist_all_verbs() {

    $("#vinaiyadi").val($.cookie('vinaiyadi'));
    $("#vinaiyadienglish").val($.cookie('vinaiyadi'));
    // alert($.cookie('autosearch'));
    if ($.cookie('autosearch') == 'true') {
        $('#autosearch').attr("checked", true);
    }
    // alert("_loadlist_all_verbs");
    toggleSearch();
    _list_all_verbs(false);
    $("#count_all").html("மொத்தவினையடிகள் #" + _getWordsCount_Verbs());

}


function _loadlist_all_nouns() {

    $("#noun").val($.cookie('noun'));
    $("#nounenglish").val($.cookie('noun'));
    // alert($.cookie('autosearch'));
    if ($.cookie('autosearch') == 'true') {
        $('#autosearch').attr("checked", true);
    }
    // alert("_loadlist_all_verbs");
    toggleSearch();
    _list_all_nouns(false);
    $("#count_all").html("மொத்தப்பெயர்ச்சொற்கள்   #" + _getWordsCount_Nouns());

}


function _loadlist_all_prepositions() {

    $("#preposition").val($.cookie('preposition'));
    $("#prepositionenglish").val($.cookie('preposition'));
    // alert($.cookie('autosearch'));
    if ($.cookie('autosearch') == 'true') {
        $('#autosearch').attr("checked", true);
    }
    // alert("_loadlist_all_verbs");
    toggleSearch();
    _list_all_prepositions(false);
    $("#count_all").html("மொத்தவிடைச்சொற்கள்  #" + _getWordsCount_Prepositions());

}

function _list_all_verbs_fromDerived(go) {
    var vinaiyadi = $("#vinaiyadi").val();
    $.cookie('vinaiyadi', vinaiyadi);
    // alert(vinaiyadi);
    if (!go && !$('#autosearch').prop('checked')) {
        return;
    }

    if (vinaiyadi.trim() == "") {
        _list_all_verbs
    } else {

        var list = listVerbsMap[vinaiyadi];
        if (list == null) {

            list = $.parseJSON(jQuery.ajax({
                type: "GET",
                url: server + "lookup/verbs/?noprops=true" + (vinaiyadi.trim() == "" ? "" : "&pattern=" + encodeURI(vinaiyadi)),
                contentType: "text/plain; charset=utf-8",
                async: false

            }).responseText);
        }
    }
}


var listNounsMap = new Object();


function add_new_noun_sync(noun) {
    jQuery.ajax({
        type: "POST",
        dataType: "json",
        url: server + "root-nouns/nouns/name/" + encodeURI(noun) + "/",
        contentType: "text/plain; charset=utf-8",
        async: false,
        statusCode: {
            500: function (data) {
                $("#noun").focus();
                // alert(JSON.stringify( data) );
                alert(jQuery.parseJSON(data.responseText).message);
            }
        }
    });
}


function add_new_noun() {
    var noun = $("#noun").val();
    if (noun == "") {
        alert("பெயர்ச்சொல்லை காணமுடியவில்லை.   ")
        return;
    }

    add_new_noun_sync(noun);
    //  alert("add_new_verb");

    delete listNounsMap[noun];

    _list_all_nouns(true);
    $("#nounenglish").focus();
    $("#nounenglish").select();
    $("#count_all").html("மொத்தப்பெயர்ச்சொற்கள்   #" + _getWordsCount_Nouns());


}


function _delete_Noun(noun) {

    if (!confirm("இந்தச்செயல், பெயர்ச்சொல் " + noun + " ஐ நீக்கும். சரியா?")) {
        return;
    }


    jQuery.ajax({
        type: "DELETE",
        dataType: "json",
        url: server + "root-nouns/nouns/name/" + encodeURI(noun) + "/",
        contentType: "text/plain; charset=utf-8",
        async: false,
        statusCode: {
            500: function (data) {


                alert(jQuery.parseJSON(data.responseText).message);
            }
        }
    });

    delete listNounsMap[noun];
    _list_all_nouns(true);
    $("#count_all").html("மொத்தப்பெயர்ச்சொற்கள்   #" + _getWordsCount_Nouns());
}


function _list_all_nouns(go) {
    var noun = $("#noun").val();
    $.cookie('noun', noun);

    if (!go && !$('#autosearch').prop('checked')) {
        return;
    }

    var list = listNounsMap[noun];
    if (list == null) {

        list = $.parseJSON(jQuery.ajax({
            type: "GET",
            url: server + "root-nouns/nouns/?noprops=true" + (noun.trim() == "" ? "" : "&pattern=" + encodeURI(noun)),
            contentType: "text/plain; charset=utf-8",
            async: false

        }).responseText);
    }

    $("#result_table tr:gt(0)").detach();
    if (list != null && list.value != null) {

        listNounsMap[noun] = list;
        var table = $("#result_table tr:last");
        var toadd = noun.trim() != "";

        if (list.value != null && list.value.list != null && list.value.list.word != null) {
            var data = list;

            $.each(data.value.list.word, function (item) {
                if (toadd) {
                    toadd = data.value.list.word[item].root != noun;
                }

                var resultrow = '<tr> <td>' + (item + 1) + '</td><td>';

                // resultrow += data.value.list.word[item].root + '</td><td>';
                resultrow += '<a  title="மாற்றஞ்செய்க" href=\"javascript:_edit_Noun(\'' + data.value.list.word[item].root + '\');">' + data.value.list.word[item].root + '</a></td><td>';
                resultrow += '<a  title="நீக்குக " href=\"javascript:_delete_Noun(\'' + data.value.list.word[item].root + '\');">' + data.value.list.word[item].root + ' ஐ நீக்குக</a></td>';

                table.after(resultrow + ' </td> </tr>');// JSON.stringify( data[item].splitLists) +' </td> </tr>');

            });
        }
        if (toadd) {
            $("#add_new_noun_link").show();
        } else {
            $("#add_new_noun_link").hide();
        }
    }
}


var listPrepositionsMap = new Object();


function add_new_preposition_sync(preposition) {
    jQuery.ajax({
        type: "POST",
        dataType: "json",
        url: server + "root-prepositions/prepositions/name/" + encodeURI(preposition) + "/",
        contentType: "text/plain; charset=utf-8",
        async: false,
        statusCode: {
            500: function (data) {
                $("#preposition").focus();
                // alert(JSON.stringify( data) );
                alert(jQuery.parseJSON(data.responseText).message);
            }
        }
    });
}


function add_new_preposition() {
    var preposition = $("#preposition").val();
    if (preposition == "") {
        alert("இடைச்சொல்லை  காணமுடியவில்லை.   ")
        return;
    }

    add_new_preposition_sync(preposition);
    //  alert("add_new_verb");

    delete listPrepositionsMap[preposition];

    _list_all_prepositions(true);
    $("#prepositionenglish").focus();
    $("#prepositionenglish").select();
    $("#count_all").html("மொத்தவிடைச்சொற்கள்  #" + _getWordsCount_Prepositions());


}


function _delete_Preposition(preposition) {

    if (!confirm("இந்தச்செயல், இடைச்சொல்  " + preposition + " ஐ நீக்கும். சரியா?")) {
        return;
    }


    jQuery.ajax({
        type: "DELETE",
        dataType: "json",
        url: server + "root-prepositions/prepositions/name/" + encodeURI(preposition) + "/",
        contentType: "text/plain; charset=utf-8",
        async: false,
        statusCode: {
            500: function (data) {


                alert(jQuery.parseJSON(data.responseText).message);
            }
        }
    });

    delete listPrepositionsMap[preposition];
    _list_all_prepositions(true);
    $("#count_all").html("மொத்தவிடைச்சொற்கள்  #" + _getWordsCount_Prepositions());
}

function _list_all_prepositions(go) {
    var preposition = $("#preposition").val();
    $.cookie('preposition', preposition);


    if (!go && !$('#autosearch').prop('checked')) {
        return;
    }

    var list = listPrepositionsMap[preposition];
    if (list == null) {

        list = $.parseJSON(jQuery.ajax({
            type: "GET",
            url: server + "root-prepositions/prepositions/?noprops=true" + (preposition.trim() == "" ? "" : "&pattern=" + encodeURI(preposition)),
            contentType: "text/plain; charset=utf-8",
            async: false

        }).responseText);
    }

    $("#result_table tr:gt(0)").detach();
    if (list != null && list.value != null) {

        listPrepositionsMap[preposition] = list;
        var table = $("#result_table tr:last");
        var toadd = preposition.trim() != "";

        if (list.value != null && list.value.list != null && list.value.list.word != null) {
            var data = list;

            $.each(data.value.list.word, function (item) {
                if (toadd) {
                    toadd = data.value.list.word[item].root != preposition;
                }

                var resultrow = '<tr> <td>' + (item + 1) + '</td><td>';

                // resultrow +=  data.value.list.word[item].root  + '</td><td>';
                resultrow += '<a  title="மாற்றஞ்செய்க" href=\"javascript:_edit_Preposition(\'' + data.value.list.word[item].root + '\');">' + data.value.list.word[item].root + '</a></td><td>';
                resultrow += '<a  title="நீக்குக " href=\"javascript:_delete_Preposition(\'' + data.value.list.word[item].root + '\');">' + data.value.list.word[item].root + ' ஐ நீக்குக</a></td>';

                table.after(resultrow + ' </td> </tr>');// JSON.stringify( data[item].splitLists) +' </td> </tr>');

            });
        }
        if (toadd) {
            $("#add_new_preposition_link").show();
        } else {
            $("#add_new_preposition_link").hide();
        }
    }
}


function _list_all_verbs(go) {
    var vinaiyadi = $("#vinaiyadi").val();

    $.cookie('vinaiyadi', vinaiyadi);
    // alert(vinaiyadi);
    if (!go && !$('#autosearch').prop('checked')) {
        return;
    }


    var list = listVerbsMap[vinaiyadi];
    if (list == null) {

        list = $.parseJSON(jQuery.ajax({
            type: "GET",
            url: server + "root-verbs/verbs/?noprops=true" + (vinaiyadi.trim() == "" ? "" : "&pattern=" + encodeURI(vinaiyadi)),
            contentType: "text/plain; charset=utf-8",
            async: false

        }).responseText);
    }

    $("#result_table tr:gt(0)").detach();
    if (list != null && list.value != null) {

        listVerbsMap[vinaiyadi] = list;
        var table = $("#result_table tr:last");
        var toadd = vinaiyadi.trim() != "";

        if (list.value != null && list.value.list != null && list.value.list.verb != null) {
            var data = list;
            // alert(data.value.list.verb.length);
            $.each(data.value.list.verb, function (item) {
                if (toadd) {
                    toadd = data.value.list.verb[item].root != vinaiyadi;
                }

                var resultrow = '<tr> <td>' + (item + 1) + '</td><td>';

                resultrow += '<a  title="மாற்றஞ்செய்க" href=\"javascript:_edit_Vinayadi(\'' + data.value.list.verb[item].root + '\');">' + data.value.list.verb[item].root + '</a></td><td>';
                resultrow += '<a  title="நீக்குக " href=\"javascript:_delete_Vinayadi(\'' + data.value.list.verb[item].root + '\');">' + data.value.list.verb[item].root + ' ஐ நீக்குக</a></td>';
//                    if (data.value.list.verb[item].description != null) {
//                        resultrow += '<table><tr><th>வினைப்பகுதி</th><th>மதிப்பு</th>';
//                        if (data.value.list.verb[item].description.property != null) {
//                            $.each(data.value.list.verb[item].description.property, function (prop) {
//                                //Filter them out
//                                if (_is_property_of_type(data.value.list.verb[item].description.property[prop].name, PROP_BASE)) {
//
//                                    resultrow += '<tr> <td>' + _get_property_description(data.value.list.verb[item].description.property[prop].name) + '</td>';
//                                    resultrow += '<td>' + data.value.list.verb[item].description.property[prop].value + '</td></tr>';
//                                }
//                            });
//                        }
//                        resultrow += "</td></tr></table>";
//                    }

                table.after(resultrow + ' </td> </tr>');// JSON.stringify( data[item].splitLists) +' </td> </tr>');
                // alert(data[item].handlerName);
            });
        }
        if (toadd) {
            $("#add_new_verb_link").show();
        } else {
            $("#add_new_verb_link").hide();
        }
    }
}

function _edit_Vinayadi(val) {
    if (val == null) {
        val = $("#vinaiyadi_edit").val();
    }
    $.cookie('vinaiyadi_edit', val);
    location.href = "edit-verb.html#" + val;
    return true;
}

function _edit_Peyarechcham(val) {
    if (val == null) {
        val = $("#vinaiyadi_edit").val();
    }
    $.cookie('vinaiyadi_edit', val);
    location.href = "edit-peyarechcham.html#" + val;
    return true;
}


function _edit_VinaiyaalanaiyumPeyar(val) {
    if (val == null) {
        val = $("#vinaiyadi_edit").val();
    }
    $.cookie('vinaiyadi_edit', val);
    location.href = "edit-vinaiyaalanaiyum-peyar.html#" + val;
    return true;
}

function _edit_Preposition(val) {
    if (val == null) {
        val = $("#preposition_edit").val();
    }
    $.cookie('preposition_edit', val);
    location.href = "edit-preposition.html#" + val;
    return true;
}


function _edit_Noun(val) {
    if (val == null) {
        val = $("#noun_edit").val();
    }
    $.cookie('noun_edit', val);
    location.href = "edit-noun.html#" + val;
    return true;
}


function _edit_ThoarVinaimuttu(val) {
    if (val == null) {
        val = $("#vinaiyadi_edit").val();
    }
    $.cookie('vinaiyadi_edit', val);
    location.href = "thodar-muttukal.html#" + val;
    return true;
}

function _edit_MuttuVinaimuttu(val) {
    if (val == null) {
        val = $("#vinaiyadi_edit").val();
    }
    $.cookie('vinaiyadi_edit', val);
    location.href = "muttu-muttukal.html#" + val;
    return true;
}


function _edit_ThoarMuttuVinaimuttu(val) {
    if (val == null) {
        val = $("#vinaiyadi_edit").val();
    }
    $.cookie('vinaiyadi_edit', val);
    location.href = "thodar-muttu-muttukal.html#" + val;
    return true;
}


function _edit_Vinaiyechcham(val) {
    if (val == null) {
        val = $("#vinaiyadi_edit").val();
    }
    $.cookie('vinaiyadi_edit', val);
    location.href = "edit-vinaiyechcham.html#" + val;
    return true;
}


function _silent_delete_Vinayadi_property(vinaiyadi, propname) {
    return _silent_delete_property('root-verbs/verbs', vinaiyadi, propname);

}


function _silent_delete_property(base, word, propname) {

    if (_is_IssueRelated(propname)) {
        $('#header-notes').trigger("click");
        $('.showhide-notes').show();

    }
    var failed = false;
    jQuery.ajax({
        type: "DELETE",
        url: server + base + "/name/" + encodeURI(word) + "/property/" + propname + "/",
        contentType: "text/plain; charset=utf-8",
        async: false,
        statusCode: {
            500: function (data, status, XHR) {
                alert(jQuery.parseJSON(data.responseText).message);
                failed = true;
            }
        }
    });
    return !failed;

}

function _delete_Vinayadi_property(prop) {
    var vinaiyadi = $("#vinaiyadi_edit").val();
    _silent_delete_Vinayadi_property(vinaiyadi, prop);
    var global_properties_json = $.parseJSON(_get_global_properties());
    _edit_Vinayadi_init_for(vinaiyadi, global_properties_json);
}


function add_new_verb() {
    var vinaiyadi = $("#vinaiyadi").val();
    if (vinaiyadi == "") {
        alert("வினையடியை காணமுடியவில்லை.   ")
        return;
    }

    jQuery.ajax({
        type: "POST",
        dataType: "json",
        url: server + "root-verbs/verbs/name/" + encodeURI(vinaiyadi) + "/",
        contentType: "text/plain; charset=utf-8",
        async: false,
        statusCode: {
            500: function (data) {
                $("#vinaiyadi").focus();
                // alert(JSON.stringify( data) );
                alert(jQuery.parseJSON(data.responseText).message);
            }
        }
    });

    //  alert("add_new_verb");


    delete listVerbsMap[vinaiyadi];

    _list_all_verbs(true);
    $("#vinaiyadienglish").focus();
    $("#vinaiyadienglish").select();
    $("#count_all").html("மொத்தவினையடிகள் #" + _getWordsCount_Verbs());

}

function _delete_Vinayadi(vinaiyadi) {

    if (!confirm("இந்தச்செயல், வினையடி " + vinaiyadi + " ஐ நீக்கும். சரியா?")) {
        return;
    }


    jQuery.ajax({
        type: "DELETE",
        dataType: "json",
        url: server + "root-verbs/verbs/name/" + encodeURI(vinaiyadi) + "/",
        contentType: "text/plain; charset=utf-8",
        async: false,
        statusCode: {
            500: function (data) {

                // alert(JSON.stringify( data) );
                alert(jQuery.parseJSON(data.responseText).message);
            }
        }
    });

    // alert("_delete_Vinayadi")
    delete listVerbsMap[vinaiyadi];
    _list_all_verbs(true);
    $("#count_all").html("மொத்தவினையடிகள் #" + _getWordsCount_Verbs());
}

function _add_Vinayadi_property() {
    var vinaiyadi = $("#vinaiyadi_edit").val();
    var propname = $("#select_verb_props").val();
    var propvalue = $("#select_verb_props_value").val();

    _silent_add_Vinayadi_property(vinaiyadi, propname, propvalue);

    var global_properties_json = $.parseJSON(_get_global_properties());
    _edit_Vinayadi_init_for(vinaiyadi, global_properties_json);

}


function _remove_from_global_property(array, property) {

    $.each(array, function (index) {

        if (array[index].name == property) {
            //Remove from array
            array.splice(index, 1);

            //This is to break the loop!!
            return false;
        }
    });


}

function _populate_edit_verb_property() {
    var val = $("#select_verb_props").val();

    jQuery.ajax({
            type: "GET",
            dataType: "json",
            url: server + "root-verbs/types/name/" + val + "/values/",
            success: function (data, status, jqXHR) {
                var options = $("#select_verb_props_values");
                if (data.value.property == null || data.value.property.length == 0) {
                    options.hide();
                    $("#select_verb_props_value_div").show();
                    $("#select_verb_props_value").show();
                    $("#select_verb_props_value").val("");

                } else {
                    options.show();
                    options.empty();
                    $("#select_verb_props_value_div").hide();
                    $("#select_verb_props_value").hide();
                    $.each(data.value.property, function (item) {
                        options.append($("<option />").val(data.value.property[item].name).text(data.value.property[item].value));
                    });
                    options.trigger("change");
                }

            },
            error: function (jqXHR, status) {
                //alert(status);
            }
        }
    )


}

function _select_verb_prop_value_changed() {
    $("#select_verb_props_value").val($("#select_verb_props_values").val());
}

function _populate_Vinaiyadigal(vinaiyadi, transitve) {

    var table;
    if (transitve) {
        $("#_vinai_table tr:gt(0)").detach();
        table = $("#_vinai_table tr:last");
    } else {
        $("#_intransitive_vinai_table tr:gt(0)").detach();
        table = $("#_intransitive_vinai_table tr:last");
    }


    jQuery.ajax({
        type: "GET",
        url: server + "compound-word/" + PROP_BASE + "/name/" + encodeURI(vinaiyadi) + "/?transitive=" + transitve,
        contentType: "text/plain; charset=utf-8",
        success: function (data, status, jqXHR) {


            if (data.value.rows != null) {

                $.each(data.value.rows, function (prop) {
                    var row = data.value.rows[prop];

                    var resultrow = '<tr> <th>' + row.rowname + '</th><td>';
                    if (row.present != null && row.present.list != null) {
                        resultrow += '<b>';
                        $.each(row.present.list, function (pre) {
                            resultrow += row.present.list[pre].value + '&nbsp;&nbsp;&nbsp;';
                        });
                        resultrow += "</b><br/><label class='small_text'>";
                        $.each(row.present.list, function (pre) {
                            resultrow += '(' + row.present.list[pre].equation + ')';
                        });
                        resultrow += '</label>';
                    }

                    resultrow += '</td><td>';

                    if (row.past != null && row.past.list != null) {
                        resultrow += '<b>';
                        $.each(row.past.list, function (pre) {
                            resultrow += row.past.list[pre].value + '&nbsp;&nbsp;&nbsp;';
                        });
                        resultrow += "</b><br/><label class='small_text'>";
                        $.each(row.past.list, function (pre) {
                            resultrow += '(' + row.past.list[pre].equation + ')';
                        });
                        resultrow += '</label>';
                    }

                    resultrow += '</td><td>';

                    if (row.future != null && row.future.list != null) {
                        resultrow += '<b>';
                        $.each(row.future.list, function (pre) {
                            resultrow += row.future.list[pre].value + '&nbsp;&nbsp;&nbsp;';
                        });
                        resultrow += "</b><br/><label class='small_text'>";
                        $.each(row.future.list, function (pre) {
                            resultrow += '(' + row.future.list[pre].equation + ')';
                        });
                        resultrow += '</label>';
                    }
                    resultrow += '</td>';

//                    resultrow += row.past + '</b></br>(';
//                    resultrow += row.pasteq + ')</td><td><b>';
//
//                    resultrow += row.future + '</b></br>(';
//                    resultrow += row.futureeq;

                    table.after(resultrow + '</tr>');// JSON.stringify( data[item].splitLists) +' </td> </tr>');
                    // alert(data[item].handlerName);
                });
            }


        },
        error: function (jqXHR, status) {
            if (jqXHR.status == 404) {
                alert(vinaiyadi + " காணப்படவில்லை.வினைமுற்றுகளைப்பெறமுடியவில்லை.");
            } else {
                // alert(status);
            }

        }
    })


}


function _copyPropertiesFrom(from) {
    var to = $("#vinaiyadi_edit").val();
    if (from == to) {
        return;
    }
    if (!confirm("இந்தச்செயல் வினைப்பகுதிகளை " + from + "-இலிருந்தெடுத்து " + to + "-இக்குச்சேர்க்கும. சரியா? ")) {
        return;
    }

    from = $.parseJSON(jQuery.ajax({
        type: "GET",
        url: server + "root-verbs/verbs/name/" + encodeURI(from) + "/",
        contentType: "text/plain; charset=utf-8",
        async: false
    }).responseText);

    if (from.value.description != null && from.value.description.property != null) {
        $.each(from.value.description.property, function (prop) {

            if (_is_property_of_type(from.value.description.property[prop].name, PROP_BASE)) {
                if (!_is_VigaaramRelated(from.value.description.property[prop].name) && !_isLockingRelated(from.value.description.property[prop].name)) {
                    _silent_add_Vinayadi_property(to, from.value.description.property[prop].name, from.value.description.property[prop].value);
                }
            }
        });

        var global_properties_json = $.parseJSON(_get_global_properties());
        _edit_Vinayadi_init_for(to, global_properties_json);
    }


}


function _is_property_of_type(prop, type) {
    return prop.indexOf(type) == 0 || prop.indexOf("property.") == 0;
}


function _is_VigaaramRelated(prop) {
    return prop.indexOf("vigaaram.") >= 0;
}


function _isLockingRelated(prop) {
    return prop.indexOf(".locked") >= 0;
}

function _is_IssueRelated(prop) {
    return prop.indexOf("issue.") == 0;
}


function _isTransitive_GenerationHavingissue(prop, value) {

    if ("issue.transitive.generation." + PROP_BASE == prop) {
        return ( value == 'true') ? 1 : 0;
    } else {
        return -1;
    }

}

function _isInTransitive_GenerationHavingissue(prop, value) {
    if ("issue.intransitive.generation." + PROP_BASE == prop) {
        return (value == 'true') ? 1 : 0;
    } else {
        return -1;
    }

}

function _NAVI_SELECT_changed() {
    var current = $("#NAVI_SELECT").val();
    $.cookie('NAVI_SELECT', current);
}

function _NAVI_SELECT_IDAI_changed() {
    var current = $("#NAVI_SELECT_IDAI").val();
    $.cookie('NAVI_SELECT_IDAI', current);
}

function _NAVI_SELECT_PEYAR_changed() {
    var current = $("#NAVI_SELECT_PEYAR").val();
    $.cookie('NAVI_SELECT_PEYAR', current);
}


function _show_previous_verb() {
    var current = $("#vinaiyadi_edit").val();
    var prev = _getPreviousVerb(current);


    if (prev != null && prev != "") {

        var global_properties_json = $.parseJSON(_get_global_properties());
        _edit_Vinayadi_init_for(prev, global_properties_json);
    } else {
        alert(" முந்தின வினையடி காணப்படவில்லை.");
    }
}

function _show_next_verb() {
    var current = $("#vinaiyadi_edit").val();
    var next = _getNextVerb(current)
    if (next != null && next != "") {
        var global_properties_json = $.parseJSON(_get_global_properties());
        _edit_Vinayadi_init_for(next, global_properties_json);
    } else {
        alert(" அடுத்த  வினையடி காணப்படவில்லை.");
    }
}


function _show_previous_idai() {
    var current = $("#preposition_edit").val();
    var search = $("#NAVI_SELECT_IDAI").val();
    var prev = _getPrevious(search, BASE_URL, current);


    if (prev != null && prev != "") {

        var global_properties_json = $.parseJSON(_get_global_idai_properties());
        _edit_Idai_init_for(prev, global_properties_json);
    } else {
        alert(" முந்தின இடைச்சொல்  காணப்படவில்லை.");
    }
}


function _show_previous_peyar() {
    var current = $("#noun_edit").val();
    var search = $("#NAVI_SELECT_PEYAR").val();
    var prev = _getPrevious(search, BASE_URL, current);


    if (prev != null && prev != "") {

        var global_properties_json = $.parseJSON(_get_global_peyar_properties());
        _edit_Peyar_init_for(prev, global_properties_json);
    } else {
        alert(" முந்தின பெயர்ச்சொல்  காணப்படவில்லை.");
    }
}

function _show_next_idai() {
    var current = $("#preposition_edit").val();
    var search = $("#NAVI_SELECT_IDAI").val();
    var next = _getNext(search, BASE_URL, current)
    if (next != null && next != "") {
        var global_properties_json = $.parseJSON(_get_global_idai_properties());
        _edit_Idai_init_for(next, global_properties_json);
    } else {
        alert(" அடுத்த  இடைச்சொல்  காணப்படவில்லை.");
    }
}


function _show_next_peyar() {
    var current = $("#noun_edit").val();
    var search = $("#NAVI_SELECT_PEYAR").val();
    var next = _getNext(search, BASE_URL, current)
    if (next != null && next != "") {
        var global_properties_json = $.parseJSON(_get_global_peyar_properties());
        _edit_Peyar_init_for(next, global_properties_json);
    } else {
        alert(" அடுத்த  பெயர்ச்சொல்  காணப்படவில்லை.");
    }
}

function _edit_Vinayadi_init() {

    $.get("vinaiydai-header.html", function (data) {
        $("#_common_header").html(data);
    });


    $.get("vinaiyadi-notes.html", function (data) {
        $("#_common_notes").html(data);
    });


    var vinaiyadi = $(location).attr('hash');
    if (vinaiyadi == "") {
        vinaiyadi = $.cookie('vinaiyadi_edit');
    } else {
        vinaiyadi = vinaiyadi.slice(1);
        vinaiyadi = decodeURI(vinaiyadi);
        $.cookie('vinaiyadi_edit', vinaiyadi);
    }
    if (vinaiyadi == "") {
        alert("வினையடியை காணமுடியவில்லை.   ")
        return;
    }


    var global_properties_json = $.parseJSON(_get_global_properties());
    _edit_Vinayadi_init_for(vinaiyadi, global_properties_json);
}


function _edit_Idai_init() {

    $.get("idai-header.html", function (data) {
        $("#_common_header").html(data);
    });

    $.get("idai-notes.html", function (data) {
        $("#_common_notes").html(data);
    });


    $.get("add_new_question.html", function (data) {
        $("#_add_question").html(data);
    });


    var idai = $(location).attr('hash');
    if (idai == "") {
        idai = $.cookie('preposition_edit');
    } else {
        idai = idai.slice(1);
        idai = decodeURI(idai);
        $.cookie('preposition_edit', idai);
    }
    if (idai == "") {
        alert("இடைச்சொல்லை  காணமுடியவில்லை.   ")
        return;
    }


    _edit_Idai_init_for(idai);
}


function _edit_Peyar_init() {

    $.get("peyar-header.html", function (data) {
        $("#_common_header").html(data);
    });

    $.get("peyar-notes.html", function (data) {
        $("#_common_notes").html(data);
    });

    $.get("add_new_question.html", function (data) {
        $("#_add_question").html(data);
    });


    var peyar = $(location).attr('hash');
    if (peyar == "") {
        peyar = $.cookie('noun_edit');
    } else {
        peyar = peyar.slice(1);
        peyar = decodeURI(peyar);
        $.cookie('noun_edit', peyar);
    }
    if (peyar == "") {
        alert("பெயர்ச்சொல்லை  காணமுடியவில்லை.   ")
        return;
    }


    _edit_Peyar_init_for(peyar);
}


function _edit_Vinayadi_init_for(vinaiyadi, global_properties) {
    $("#vinaiyadi_edit").val(vinaiyadi);
    _setupVinaiyadiTabs(vinaiyadi);
    _edit_Vinayadi_init_for_parts_only(vinaiyadi, global_properties);
    _populate_Vinaiyadigal(vinaiyadi, true);
    _populate_Vinaiyadigal(vinaiyadi, false);

}


function _edit_Idai_init_for(idai) {
    $("#preposition_edit").val(idai);
    _setupIdaiTabs(idai);
    _refreshIdaiProps(idai);


}


function _edit_Peyar_init_for(peyar) {
    $("#noun_edit").val(peyar);
    _setupPeyarTabs(peyar);
    _refreshPeyarProps(peyar);


}

function _refreshIdaiProps(idai) {
    var global_properties_json = $.parseJSON(_get_global_idai_properties());
    _edit_Idai_init_for_parts_only(idai, global_properties_json);

}


function _refreshPeyarProps(peyar) {
    var global_properties_json = $.parseJSON(_get_global_peyar_properties());
    _edit_Peyar_init_for_parts_only(peyar, global_properties_json);

}


function _silent_add_property_for_word(base, word, propname, propvalue) {
    _silent_add_property(base, word, propname, propvalue);
    //hack
    if (base.indexOf("noun") >= 0) {
        _refreshPeyarProps(word);
    } else {
        _refreshIdaiProps(word);
    }
    var id = propname.replace(/\./g, "_");

    $("#" + id).focus();
    //   $("html, body").animate({ scrollTop: $("#" + id).scrollTop() }, 1000);

}

function _silent_delete_property_for_word(base, word, propname) {
    _silent_delete_property(base, word, propname);
    //hack
    if (base.indexOf("noun") >= 0) {
        _refreshPeyarProps(word);
    } else {
        _refreshIdaiProps(word);
    }
    var id = propname.replace(/\./g, "_");
    $("#" + id).focus();
    //$("html, body").animate({ scrollTop: $("#" + id).scrollTop() }, 1000);
}

function _show_New_Question(e, property, val) {
    console.log(property + ":" + val);

    $("#new_question_popup").css({position: "absolute", left: e.pageX, top: e.pageY});
    $('#new_question_popup').show('slow');

    current_questions_id = property;
    current_answer_id = val;

}


function _change_answer_type() {
    var opt = $("#questiontype").val();

    if (opt == 'yes_or_no' || opt == 'descriptive') {
        $("#tr_known").hide();
        $("#tr_new").hide();
    } else if (opt == 'known_options') {
        console.log(opt);
        $("#tr_known").show();
        $("#tr_new").hide();
        _load_existing_answers();
    } else {
        $("#tr_known").hide();
        $("#tr_new").show();
        console.log(opt);
    }
}


function _load_existing_answers() {
    jQuery.ajax({
        type: "GET",
        dataType: "json",
        url: server + BASE + "/types-values/",
        success: function (data, status, jqXHR) {
            var options = $("#answers_known");
            options
                .find('option')
                .remove();
            $.each(data.values, function (item) {
                options.append($("<option />").val(data.values[item]).text(data.values[item]));
            });
        },
        error: function (jqXHR, status) {
            alert(status);
        }
    });
}


function _add_new_question() {
    var opt = $("#questiontype").val();
    var key = $("#question_key").val();
    var q = $("#question").val();
    if (q.trim().length < 5) {
        alert("கேள்வியை சரியாகக்கொடுக்கவும்.(min 5 characters please!) ");
        $("#question").focus();
        return;
    }

    if (key.trim().length < 1) {
        alert("Please enter a valid key. (at least one english character). This is used for internal purpose only.");
        $("#question_key").focus();
        return;
    }
    var val = null;
    var tamil = false;

    if (opt == 'yes_or_no') {
        val = "boolean";
    }
    else if (opt == 'descriptive') {
        val = "string";
    } else if (opt == 'known_options') {
        val = $("#answers_known").val();
        tamil = true;
    } else {
        if ($("#answers_new").val().indexOf("=") <= 0) {
            $("#answers_new").focus();
            alert("பதிலை கொடுக்கப்பட்ட அமைப்பில் கொடுக்கவும். ");
            return;
        }
        val = "[" + $("#answers_new").val() + "]";
        tamil = true;
    }
    _silent_add_new_question(BASE, q, "i." + current_questions_id + "." + current_answer_id + "." + key, val, tamil);
    $('#new_question_popup').hide();

    //hack
    if (BASE.indexOf("noun") >= 0) {
        _refreshPeyarProps($("#noun_edit").val());
    } else {
        _refreshIdaiProps($("#preposition_edit").val());
    }

}

function _silent_add_new_question(base, q, newid, value, tamil) {
    if (tamil == null) {
        tamil = false;
    }
    var failed = false;
    jQuery.ajax({
        type: "PUT",
        data: value + "",
        url: server + base + "/types/name/" + encodeURI(newid) + "?desc=" + encodeURI(q) + "&tamil=" + tamil,
        contentType: "text/plain; charset=utf-8",

        async: false,

        statusCode: {
            500: function (data, status, XHR) {
                alert(jQuery.parseJSON(data.responseText).message);
                failed = true;
            }
        }
    });

    //console.log(!failed);
    return !failed;

}


function _type_To_component(base, baseupdateurl, word, propname, currentvalue, justvalue, depth) {


    var id = propname.replace(/\./g, "_");

    var data = global_prop_values[propname];
    if (data == null) {

        data = jQuery.ajax({
            type: "GET",
            dataType: "json",
            url: server + base + "/types/name/" + propname + "/values/",
            async: false,
            statusCode: {
                404: function (data, status, XHR) {
                    return null;
                }
            }


        }).responseText;
    }


// console.log("data:" + data);
    if (data == null || data == "") {
        return "";
    }

    var ret = "";

    global_prop_values[propname] = data;
    data = JSON.parse(data);

    var innerPropertyBase = "i." + propname + "." + currentvalue + ".";

    var boolean_type = false;
    //NPE when the type is null needed    data.value == null
    if (data.value == null || data.value.property == null || data.value.property.length == 0) {
        if (currentvalue == null) {
            currentvalue = "";
        }
        if (justvalue) {
            return currentvalue;
        }
        //  ret += "<div data-tip=\"குறிப்பு: aa=ஆ, ea=ஏ, f=ஃ, lh=ள், nt=ந், nh=ண், oa=ஓ, ou=ஔ, rr=ற், zh=ழ்\">  ";
        ret += "<table border=0 width=\"100\">     \
          <tr>          \
            <td nowrap>";
        for (i = 0; i < 4 * depth; i++) {
            ret += "&nbsp;";
        }


        ret += "<input size=\"25\" type=\"text\" id=\"" + id + "\"  value='" + currentvalue + "'> </input>\
        </td>  \
        <td align=\"center\" rowspan=\"2\"><input type=\"button\"  value=\"சேமி \"   \
        onclick=\"_silent_add_property_for_word('" + baseupdateurl + "','" + word + "','" + propname + "',$('#" + id + "').val())\"></input></td> \
        </tr>   \
            <tr> \
                  \
            <td nowrap> ";


        if (propname.indexOf("meaning.english") != 0) {

            for (i = 0; i < 4 * depth; i++) {
                ret += "&nbsp;";
            }

            ret += "<input data-tip=\"குறிப்பு: aa=ஆ, ea=ஏ, f=ஃ, lh=ள், nt=ந், nh=ண், oa=ஓ, ou=ஔ, rr=ற், zh=ழ்\" size=\"25\" value='" + currentvalue + "' \
                          \
            onClick=\"this.select()\" \
            type=\"text\"      \
                        \
            placeholder=\"Type in english here, to transliterate.\" \
            id=\"" + id + "english\" \
            onkeypress=\"return _onkeypress(event,this);\" \
            onkeyup=\"_translit(this,'#" + id + "');\" \
            /><br/>";
        }
        ret += "  \
          \
        </td>  \
               </tr>   \
        </table>";

        return ret;
    }

    else if (data.value.property.length == 2 && (data.value.property[0].name == 'false' || data.value.property[1].name == 'true')) {
        if (currentvalue == null) {
            currentvalue = false;
        }
        boolean_type = true;
        innerPropertyBase = "i." + propname + "." + currentvalue + ".";
        //boolean
        if (justvalue) {
            ret += currentvalue;
        } else {
            ret += "<input id=\"" + id + "\" type='checkbox' onchange=\"javascript:return (this.checked?_silent_add_property_for_word('" + baseupdateurl + "','" + word + "','" + propname + "',true):_silent_delete_property_for_word('" + baseupdateurl + "','" + word + "','" + propname + "'))\" " + ( currentvalue ? "checked" : "") + "></input>";
        }
//        console.log("Current value:" + currentvalue);


    } else {
        if (currentvalue == null) {
            currentvalue = "";
        }
        innerPropertyBase = "i." + propname + "." + currentvalue + ".";
        if (justvalue) {
            ret += currentvalue;
        } else {
            ret += "<select id=\"" + id + "\"  onchange=\"_silent_add_property_for_word('" + baseupdateurl + "','" + word + "','" + propname + "',$('#" + id + "').val())\">"
            var matched = false;
            $.each(data.value.property, function (prop) {
                var name = data.value.property[prop].value;
                var value = data.value.property[prop].name;
                ret += "<option value='" + value + "'";
                if (value == currentvalue) {
                    ret += " selected ";
                    matched = true;
                }
                ret += ">" + name + "</option>";

            });
            if (!matched) {
                ret += "<option selected> ?</option>";
            }
            ret += "</select>";
        }


    }
    if ((currentvalue != null && currentvalue != "") || boolean_type) {

        ret += "&nbsp;&nbsp;<a class='noun_add' title='இதற்குக்கீழே (child question)புதிய கேள்வியை இணை. ' onclick =\"javascript:_show_New_Question(event,'" + propname + "','" + currentvalue + "')\">+</a>";
    }
//declarations
    var innertypes = _get_global_common_properties(base, innerPropertyBase);

//values
    var inner = _silent_get_properties_With_Start(baseupdateurl, word, innerPropertyBase);

    if (innertypes != null && innertypes.value != null && innertypes.value.declare != null) {

        $.each(innertypes.value.declare, function (prop1) {
            console.log(prop1 + ":name:" + innertypes.value.declare[prop1]);
            var innernamedeclaration = innertypes.value.declare[prop1].name;
            var innervalue = null;

            if (inner != null && inner.value != null && inner.value.property != null) {
                $.each(inner.value.property, function (prop) {

                    var innername = inner.value.property[prop].name;
                    if (innername == innernamedeclaration) {
                        innervalue = inner.value.property[prop].value;
                        //break
                        return;
                    }


                });

            }
            // if (prop1 > 0) {
            ret += "<hr/>";
//            } else {
//                ret += "<br/>";
//            }

            for (i = 0; i < 5 * depth; i++) {
                ret += "&nbsp;";
            }

            ret += _get_property_description_with_base(base, innernamedeclaration);
            ret += ":";
            ret += _type_To_component(base, baseupdateurl, word, innernamedeclaration, innervalue, justvalue, depth + 1);
        });


    } else {
        console.log(innerPropertyBase + " returned empty");
    }
// console.log("\neffective component :" + ret);


    return ret;
}


function _findPropValue(descriptions, propname) {
    if (descriptions == null || descriptions.property == null) return null;
    var val = null;
    $.each(descriptions.property, function (prop) {

        var _prop_name = descriptions.property[prop].name;
        var _prop_value = descriptions.property[prop].value;
        // console.log(_prop_name + "=" + _prop_value +" ?" + propname) ;
        if (propname == _prop_name) {
            val = _prop_value;
            return _prop_value;
        }

    });
    return val;

}

function _edit_Idai_init_for_parts_only(idai, gp) {
    $("#preposition_edit").val(idai);
    $('#issue_mark').prop("checked", false);

    $("#english").val("");
    $("#english").trigger("click");

    jQuery.ajax({
        type: "GET",
        url: server + "root-prepositions/prepositions/name/" + encodeURI(idai) + "/",
        contentType: "text/plain; charset=utf-8",
        success: function (data, status, jqXHR) {

            $("#result_table tr:gt(0)").detach();
            var table = $("#result_table tr:last");

            var locked = _findPropValue(data.value.description, PROP_BASE + ".locked") == 'true';

            var issue = _findPropValue(data.value.description, "issue." + PROP_BASE) == 'true';

            $.each(gp.value.declare, function (prop) {

                var desc = gp.value.declare[prop].description;
                var _prop_name = gp.value.declare[prop].name;
                var _prop_value = _findPropValue(data.value.description, _prop_name);
                if (_prop_name == PROP_BASE + ".locked" || _prop_name == "issue." + PROP_BASE) {
                    // no action.
                } else {
                    if (_is_property_of_type(_prop_name, "description")) {
                        $("#english").val(_prop_value);
                    } else {

                        var resultrow = '<tr> <th>' + desc + '</th><td>';

                        resultrow += _type_To_component(BASE, BASE_URL, idai, _prop_name, _prop_value, locked, 1);

                        table.after(resultrow + ' </td> </tr>');
                    }
                }
            });


            if (locked) {
                $('#editable').hide();
                $('#green_mark').prop("checked", true);
                $('#green_mark').attr("disabled", "disabled");


            } else {
                $('#editable').show();
                $('#green_mark').removeAttr("disabled");
                $('#green_mark').removeAttr("checked");
            }

            if (issue) {
                $('#issue_mark').prop("checked", true);
            }


        },

        error: function (jqXHR, status) {
            if (jqXHR.status == 404) {
                alert(idai + " காணப்படவில்லை");
            } else {
                // alert(status);
            }

        }
    })

}


function _edit_Peyar_init_for_parts_only(noun, gp) {
    $("#noun_edit").val(noun);
    $('#issue_mark').prop("checked", false);

    $("#english").val("");
    $("#english").trigger("click");

    jQuery.ajax({
        type: "GET",
        url: server + "root-nouns/nouns/name/" + encodeURI(noun) + "/",
        contentType: "text/plain; charset=utf-8",
        success: function (data, status, jqXHR) {

            $("#result_table tr:gt(0)").detach();
            var table = $("#result_table tr:last");

            var locked = _findPropValue(data.value.description, PROP_BASE + ".locked") == 'true';

            var issue = _findPropValue(data.value.description, "issue." + PROP_BASE) == 'true';
            if (gp.value.declare != null) {
                $.each(gp.value.declare, function (prop) {

                    var desc = gp.value.declare[prop].description;
                    var _prop_name = gp.value.declare[prop].name;
                    var _prop_value = _findPropValue(data.value.description, _prop_name);
                    if (_prop_name == PROP_BASE + ".locked" || _prop_name == "issue." + PROP_BASE) {
                        // no action.
                    } else {
                        if (_is_property_of_type(_prop_name, "description")) {
                            $("#english").val(_prop_value);
                        } else {

                            var resultrow = '<tr> <th>' + desc + '</th><td>';

                            resultrow += _type_To_component(BASE, BASE_URL, noun, _prop_name, _prop_value, locked, 1);

                            table.after(resultrow + ' </td> </tr>');
                        }
                    }
                });
            }

            if (locked) {
                $('#editable').hide();
                $('#green_mark').prop("checked", true);
                $('#green_mark').attr("disabled", "disabled");


            } else {
                $('#editable').show();
                $('#green_mark').removeAttr("disabled");
                $('#green_mark').removeAttr("checked");
            }

            if (issue) {
                $('#issue_mark').prop("checked", true);
            }


        },

        error: function (jqXHR, status) {
            if (jqXHR.status == 404) {
                alert(noun + " காணப்படவில்லை");
            } else {
                // alert(status);
            }

        }
    })

}


function _edit_Vinayadi_init_for_parts_only(vinaiyadi, global_properties) {
    $("#vinaiyadi_edit").val(vinaiyadi);
    $('#mark_intransitive_issue').prop("checked", false);
    $('#mark_transitive_issue').prop("checked", false);
    $("#english").val("");
    $("#english").trigger("click");
    //  global_properties = jQuery.extend({}, global_properties);
    jQuery.ajax({
        type: "GET",
        url: server + "root-verbs/verbs/name/" + encodeURI(vinaiyadi) + "/",
        contentType: "text/plain; charset=utf-8",
        success: function (data, status, jqXHR) {

            $("#result_table tr:gt(1)").detach();
            var table = $("#result_table tr:last");

            var locked = false;


            if (data.value.description != null && data.value.description.property != null) {


                $.each(data.value.description.property, function (prop) {
                    var _prop_name = data.value.description.property[prop].name;
                    var _prop_value = data.value.description.property[prop].value;


                    if (!locked) {
                        locked = _prop_name == PROP_BASE + ".locked" && _prop_value == "true";

                    }
                    if (_is_property_of_type(_prop_name, PROP_BASE)) {
                        _remove_from_global_property(global_properties.value.declare, _prop_name);
                        var resultrow = '<tr> <td>' + _get_property_description(_prop_name) + '</td><td>';
                        resultrow += _prop_value + '</td><td>';

                        resultrow += '<a href="javascript:_delete_Vinayadi_property(\'' + _prop_name + '\')">நீக்குக</a></td></td>';


                        table.after(resultrow + ' </td> </tr>');// JSON.stringify( data[item].splitLists) +' </td> </tr>');
                    } else if (_is_property_of_type(_prop_name, "description")) {
                        $("#english").val(_prop_value);
                    } else {

                        var ret_val = _isInTransitive_GenerationHavingissue(_prop_name, _prop_value);
                        if (ret_val >= 0) {
                            if (ret_val == 1) {

                                $('#mark_intransitive_issue').prop("checked", true);


                            }
                        }

                        ret_val = _isTransitive_GenerationHavingissue(_prop_name, _prop_value);
                        if (ret_val >= 0) {
                            if (ret_val == 1) {
                                $('#mark_transitive_issue').prop("checked", true);


                            }
                        }

                    }
                    // alert(data[item].handlerName);
                });
            }

            if (locked) {
                $('#editable').hide();
                $('#mark_vinai_green').prop("checked", true);
                $('#mark_vinai_green').attr("disabled", "disabled");


            } else {
                $('#editable').show();
                $('#mark_vinai_green').removeAttr("disabled");
                $('#mark_vinai_green').removeAttr("checked");
            }

            var options = $("#select_verb_props");
            options.empty()
            $.each(global_properties.value.declare, function (item) {
                options.append($("<option />").val(global_properties.value.declare[item].name).text(global_properties.value.declare[item].description));
            });

            if (global_properties.value.declare.length != 0) {
                //Change the value field
                $("#select_verb_props").trigger("change");
                $("#_add_prop_link").show();
                $("#select_verb_props").removeAttr("disabled");
            } else {
                $("#_add_prop_link").hide();
                $("#select_verb_props").attr("disabled", "disabled");
            }
        },

        error: function (jqXHR, status) {
            if (jqXHR.status == 404) {
                alert(vinaiyadi + " காணப்படவில்லை");
            } else {
                // alert(status);
            }

        }
    })


}


function _get_global_properties() {
    if (global_properties == null) {
        global_properties = jQuery.ajax({
            type: "GET",
            url: server + "root-verbs/types/?select" + "=" + PROP_BASE + ",property.,description",
            contentType: "text/plain; charset=utf-8",

            async: false
        }).responseText;
    }

    return "" + global_properties;


}


function _get_global_idai_properties() {
    if (global_idai_properties == null) {
        global_idai_properties = jQuery.ajax({
            type: "GET",
            url: server + "root-prepositions/types/?select" + "=" + PROP_BASE + ",property.,description",
            contentType: "text/plain; charset=utf-8",

            async: false
        }).responseText;
    }

    return "" + global_idai_properties;


}


function _get_global_common_properties(base, search) {

    var typeslist = jQuery.ajax({
        type: "GET",
        url: server + base + "/types/?select" + "=" + search,
        contentType: "text/plain; charset=utf-8",

        async: false,

        statusCode: {
            404: function (data, status, XHR) {
                return null;
            }
        }
    }).responseText;

    if (typeslist == null) return null;
    return JSON.parse(typeslist);


}


function _get_global_peyar_properties() {
    if (global_idai_properties == null) {
        global_idai_properties = jQuery.ajax({
            type: "GET",
            url: server + "root-nouns/types/?select" + "=" + PROP_BASE + ",property.,description",
            contentType: "text/plain; charset=utf-8",

            async: false
        }).responseText;
    }

    return "" + global_idai_properties;


}

function _getPreviousVerb(curr) {
    var current = $("#NAVI_SELECT").val();
    return _getPrevious(current, 'root-verbs/verbs', curr);
}

function _getPrevious(current, base, curr) {

    var res = jQuery.ajax({
        type: "GET",
        url: server + base + "/name/" + encodeURI(curr) + "/adjacent/previous/?search=" + current,
        contentType: "text/plain; charset=utf-8",

        async: false
    });

    if (res.status == 200) {
        return res.responseText;
    } else {
        return "";
    }


}

function _getNextVerb(curr) {
    var current = $("#NAVI_SELECT").val();
    return _getNext(current, 'root-verbs/verbs', curr);

}

function _getNext(current, base, curr) {

    var res = jQuery.ajax({
        type: "GET",
        url: server + base + "/name/" + encodeURI(curr) + "/adjacent/next/?search=" + current,
        contentType: "text/plain; charset=utf-8",

        async: false
    });
    if (res.status == 200) {
        return res.responseText;
    } else {
        return "";
    }


}


function _get_property_description_with_base(base, propname) {

    var val = property_name_map[propname];
    if (val) {
        return val;
    }

    val = jQuery.ajax({
        type: "GET",
        url: server + base + "/types/name/" + propname + "/description/",
        contentType: "text/plain; charset=utf-8",

        async: false
    }).responseText;

    property_name_map[propname] = val;
    return val;

}


function _get_property_description(propname) {

    return _get_property_description_with_base('root-verbs', propname);

}


function _split_handler() {
    var joined = $("#joined").val();
    if (joined == "") {
        alert("புணர்த்தப்பட்ட சொல்லை இடுக.  ");
        $("#joined").focus();
        return;

    }
    $.cookie('joined', joined);
    var handler = $("#rule").val();
    jQuery.ajax({
        type: "PUT",
        data: handler,
        url: server + "punarchi/split/" + encodeURI(joined) + "/",
        contentType: "text/plain; charset=utf-8",
        success: function (data, status, jqXHR) {

            $("#result_table tr:gt(0)").detach();
            var table = $("#result_table tr:last");
            $.each(data, function (item) {
                var handlerid = '<tr> <th>' + data[item].handlerName + '</th><td>';
                var result = "";
                $.each(data[item].splitLists, function (sub) {
                    result += ' [';
                    var first = true;
                    $.each(data[item].splitLists[sub].splitList, function (sub1) {
                        if (!first) {
                            result += '+'
                        }
                        result += data[item].splitLists[sub].splitList[sub1];
                        first = false;
                    });
                    result += ']';
                });

                table.after(handlerid + result + ' </td> </tr>');// JSON.stringify( data[item].splitLists) +' </td> </tr>');
                // alert(data[item].handlerName);
            });
        },
        error: function (jqXHR, status) {
            alert(jqXHR.responseText);
        }
    });
}


jQuery.fn.selectText = function () {
    var doc = document;
    var element = this[0];
    //  console.log(this, element);
    if (doc.body.createTextRange) {
        var range = document.body.createTextRange();
        range.moveToElementText(element);
        range.select();
    } else if (window.getSelection) {
        var selection = window.getSelection();
        var range = document.createRange();
        range.selectNodeContents(element);
        selection.removeAllRanges();
        selection.addRange(range);
    }
};

$.fn.selectRange = function (start, end) {
    return this.each(function () {
        if (this.setSelectionRange) {
            this.focus();
            this.setSelectionRange(start, end);
        } else if (this.createTextRange) {
            var range = this.createTextRange();
            range.collapse(true);
            range.moveEnd('character', end);
            range.moveStart('character', start);
            range.select();
        }
    });
};


new function ($) {
    $.fn.setCursorPosition = function (pos) {
        if (this.setSelectionRange) {
            this.setSelectionRange(pos, pos);
        } else if (this.createTextRange) {
            var range = this.createTextRange();
            range.collapse(true);
            if (pos < 0) {
                pos = $(this).val().length + pos;
            }
            range.moveEnd('character', pos);
            range.moveStart('character', pos);
            range.select();
        }
    }
}(jQuery);

(function ($, undefined) {
    $.fn.getCursorPosition = function () {
        var el = $(this).get(0);
        var pos = 0;
        if ('selectionStart' in el) {
            pos = el.selectionStart;
        } else if ('selection' in document) {
            el.focus();
            var Sel = document.selection.createRange();
            var SelLength = document.selection.createRange().text.length;
            Sel.moveStart('character', -el.value.length);
            pos = Sel.text.length - SelLength;
        }
        return pos;
    }


})(jQuery);


var OLD_LBL = null;
function _reverseMapCursor(ev, lbl, jarea, p, i) {
    //  console.log(i);
    if (OLD_LBL) {
        OLD_LBL.style.background = lbl.style.background;
    }
    OLD_LBL = lbl;
    lbl.style.background = "#ffffff";
    jarea.focus();
    jarea.selectRange(p + 1, i);
    ev.preventDefault();
    ev.stopPropagation();
    //
    return false;


}

var LAST_TEXT = "";

function trans(jarea, spelcheck, e, toggle, splitcompondwords, suggestwords, joinwords) {
    var area = jarea.get(0).value;
    var newarea = "";
    // var caret = getCaretPosition(this);
    // alert(JSON.stringify(caret));
    var len = jarea.getCursorPosition();

    var tospelcheck = false;

    var tojoin = false;
    if (joinwords) {
        tojoin = joinwords.prop('checked');
    }

    console.log("tojoin:" + tojoin);

    if (spelcheck) {
        tospelcheck = spelcheck.prop('checked');
    }

    var tosplit = false;
    if (splitcompondwords) {
        tosplit = splitcompondwords.prop('checked');
    }

    var tosuggest = false;
    if (suggestwords) {
        tosuggest = suggestwords.prop('checked');
    }

    if (LAST_TEXT.length + 5 < area.length) {
        //do bulk operation
//        setTimeout(function () {
//            $("#temp_tamil").html("காத்திருங்கள். திடீர்மாற்றம் காணப்பட்டுள்ளது.");
//        }, 1);

        _translitSyncBulk(area, tojoin);


    }
    LAST_TEXT = area;

    final_text = "";
    $("#temp_tamil").html("");
    //  console.log("Full:" + len);
    //var lines = area.replace(/\r\n/g, "\n").split("\n");
    // var lines = area.split("\n");
    var word = "";
    var prevwordindex = -1;

    for (var i = 0; i < area.length; i++) {
        word += area[i];
        len--;
        var nextCursor = len == 0 && i < area.length - 1;
        var cachedword = word;

        if (area[i] == ' ' || area[i] == '\n' || i == area.length - 1) {

            // console.log("Now:" + len);

            var parsedFullSplitResult = null;
            if (tosplit) {
                parsedFullSplitResult = _getWordSplitIfParsed(word.trim());
            }
            wordok = true;
            var twordobj = getTamilWord(word.trim(), tospelcheck, false, tojoin);
            var tamilword = twordobj.tamilWord;
            if (toggle) {

                newarea += (word.trim().indexOf("\\") == 0 ? "\\" : "") + tamilword;
            }

            if (tospelcheck) {
                wordok = twordobj.known;
                if (!wordok) {
                    tamilword = "<label style='color: red;   '>" + tamilword + "</label>"
                }

            }
            var currentword = false;
            if (!nextCursor) {
                if (len < 0) {
                    currentword = ( len + word.length) >= 0;
                } else {

                    currentword = len == 0;

                }
            }

            if (currentword) {
                tamilword = "<label style='text-decoration: underline;'>" + tamilword + "</label>";
            }
            word = "";

            if (area[i] == "\n") {
                tamilword += "<br/>";
                newarea += "\n";
            } else {
                tamilword += "&nbsp;";
                newarea += " ";
            }

            if (parsedFullSplitResult) {
                final_text += "<label class='multi_label'>";
            }
            final_text += "<label class='lookup_edit' onclick=\"javascript:_reverseMapCursor(event,this,$('#" + jarea.attr('id') + "')," + prevwordindex + "," + i + ")\">" + tamilword + "</label>";
            if (parsedFullSplitResult) {
                final_text += "" + "<label class='tiny_text'>" + parsedFullSplitResult;
                final_text += "</label> ";
                final_text += "</label>";
            }


            prevwordindex = i;


        }

        if (len == 0) {
            var tempword = getTamilWord(cachedword.trim(), tospelcheck, false, tojoin);

            if (tospelcheck && !tempword.known || tosuggest) {
                var options_all = "<label style='color: red;   '>" + tempword.tamilWord + "</label>";

                var suggestedwords = _getSuggestionFor(tempword.tamilWord, true);

                if (suggestedwords != null) {
                    for (var sug = 0; sug < suggestedwords.length; sug++) {
                        options_all += "<br/><label>" + suggestedwords[sug] + "</label>";
                    }
                }

                $("#temp_tamil").html(options_all);
            } else {
                $("#temp_tamil").html(tempword.tamilWord);
            }

            $("#temp_tamil").show();
        }

    }
    if (toggle) {
        jarea.val(newarea);
        jarea.trigger("click");
    }

    $("#tamil").html(final_text);
    $('.lookup_edit').mouseenter(function (e) {

        $("#lookupresult").css({position: "absolute", left: e.pageX + 20, top: e.pageY + 20});
        $('#lookupresult').show();

        $("#searched").val($(this).text().trim());
        $("#searched").trigger("input");


    });

    $(".lookup_edit").mouseleave(function () {
        $('#lookupresult').hide();
    });

    localStorage.setItem("english-text___", area);
    return true;

}
