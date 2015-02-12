/**
 * Created with IntelliJ IDEA.
 * User: velsubra
 * Date: 8/13/14
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */

var search_word = [];

var thread = null;
var threadautocomplete = null;


function _updateSearchResults() {
    var word = $('#words').val();


    var search_word_new = JSON.parse(jQuery.ajax({
        type:"PUT",
        data:word + "",
        url:server + "lookup/words/?maxcount=10",
        contentType:"text/plain; charset=utf-8",

        async:false
    }).responseText).results;

    search_word.splice(0, search_word.length)
    for (var i = 0; i < search_word_new.length; i++) {
        search_word.push(search_word_new[i]);
    }


}


var mapvalue = new Object();
mapvalue['TENSE'] = 'காலம்';
mapvalue['EAN'] = 'தன்மை-ஒருமை';
mapvalue['OAM'] = 'தன்மை-பன்மை';
mapvalue['AAY'] = 'முன்னிலை-ஒருமை';
mapvalue['EER'] = 'முன்னிலை-பன்மை';
mapvalue['THU'] = 'படர்க்கை-ஒன்றன்பால்';
mapvalue['A'] = 'படர்க்கை-பலவின்பால்';
mapvalue['AAN'] = 'படர்க்கை-ஆண்பால்';
mapvalue['AALH'] = 'படர்க்கை-பெண்பால்';
mapvalue['AAR'] = 'படர்க்கை-பல்ர்பால்-ஒருமை';
mapvalue['AR'] = 'படர்க்கை-பல்ர்பால்-பன்மை';
mapvalue['PAAL'] = 'பால்';
mapvalue['PRESENT'] = 'நிகழ்காலம்';
mapvalue['PAST'] = 'இறந்தகாலம்';
mapvalue['FUTURE'] = 'எதிர்காலம்';
mapvalue['MUTTU'] = 'முற்றுக்காலம்';
mapvalue['THODAR'] = 'தொடர்காலம்';
mapvalue['TRUE'] = '-ஆம்-';
mapvalue['FALSE'] = '-இல்லை-';
mapvalue['ROOT'] = 'வேர்ச்சொல்';
mapvalue['IDAICHCHOL'] = 'இடைச்சொல்';
mapvalue['PEYARCHCHOL'] = 'பெயர்ச்சொல்';
mapvalue['EERRUKEDDATHU'] = 'ஈறுகெட்டது';
mapvalue['THANI'] = 'தனி';
mapvalue['KADDALHAI'] = 'கட்டளை';
mapvalue['PEYARECHCHAM'] = 'பெயரெச்சம்';
mapvalue['VINAIYECHCHAM'] = 'வினையெச்சம்';
mapvalue['ETHIRMARRAI'] = 'எதிர்மறை';
mapvalue['THALAI'] = 'தலை';
mapvalue['MUDI'] = 'முடி';
mapvalue['UYARTHINHAI'] = 'உயர்திணை';


function _toTamil(w) {
    var va = mapvalue[w.toUpperCase()];
    if (va != null) {
        return va;
    } else {
        return w;
    }
}

function _getTotalWords() {
    return jQuery.ajax({
        type:"GET",
        url:server + "lookup/words/count/",
        contentType:"text/plain; charset=utf-8",

        async:false
    }).responseText;


}

function _onChange_Word_To_Look(autoparse) {
    $("#wordcount").html(" <div class='note'> காத்திருங்கள் ....  </div>");
    clearTimeout(thread);
    thread = setTimeout(function () {

        _findWord(autoparse);
    }, 500);

}


var wordDescribeCache = new Object();


function _describeWord(word) {

    if (word == null || word.trim() == "") return null;
    //alert("wordlookup:"+ word);
    var desc = wordDescribeCache[word];
    if (desc == null) {
        desc = $.parseJSON(jQuery.ajax({
            type:"PUT",
            data:word + "",
            url:server + "lookup/words/describe/",
            contentType:"text/plain; charset=utf-8",
            async:false
        }).responseText);

    }
    wordDescribeCache[word] = desc;

    return desc;

}


var wordLookupCache = new Object();


function _lookupWord(word) {

    if (word == null || word.trim() == "") return null;
    //alert("wordlookup:"+ word);
    var desc = wordLookupCache[word];
    if (desc == null) {
        desc = $.parseJSON(jQuery.ajax({
            type:"PUT",
            data:word + "",
            url:server + "lookup/words/",
            contentType:"text/plain; charset=utf-8",
            async:false
        }).responseText);

    }
    wordLookupCache[word] = desc;

    return desc;

}


function _findWord(autoparse) {

    $("#result_words tr:gt(0)").detach();
    var table = $("#result_words tr:last");


    var word = $("#searched").val();
    var desc = null;
    if (word != "") {
        $.cookie('searched', word);

        desc = _describeWord(word);

    }


    var found = false;
    if (desc != null && desc.value != null && desc.value.words != null) {
        var data = desc.value.words;
        if (data.length > 0) {
            found = true;
            if (data.length == 1) {
                $("#wordcount").html("காணப்பட்ட சொல்   #" + data.length);
            } else {
                $("#wordcount").html("காணப்பட்ட சொற்கள்  #" + data.length);
            }
            $.each(data, function (item) {
                var row = '<tr><td>' + (item + 1) + '</td> <td>' + data[item].value + '</td><td>';
                row += '<table>';

                row += '<tr><th>சொல்வகை</th>' +
                    '<td>' + data[item].type + '</td></tr>';

                var props = data[item].properties;
                if (props != null && props.property != null) {
                    var prop = props.property;
                    $.each(prop, function (it) {
                        row += '<tr><th>' + _toTamil(prop[it].name) + '</th>' +
                            '<td>' + _toTamil(prop[it].value) + '</td></tr>';
                    });
                }


                row += '</table></td> </tr>';
                // alert(data[item].handlerName);

                table.after(row);
            });
        }
    }

    $("#parseresult").hide();
    if (desc == null) {
        $("#wordcount").html("");
    } else if (!found) {
        if (!autoparse) {
            $("#wordcount").html("<div  class='note'> இந்தச்சொல் காணப்படவில்லை. " +
                "<a href='parse.html#" + word + "'>பகுக்க</a>");

        }
        else {
            $("#parseresult").show();
            $("#wordcount").html("<div  class='note'> இந்தச்சொல் காணப்படவில்லை. </div>");
            _parseWord(word, 3);
        }
    }

}


function _parse_handler() {
    var joined = $("#joined").val();
    if (joined == "") {
        alert("புணர்த்தப்பட்ட சொல்லை இடுக.  ");
        $("#joined").focus();
        return;

    }
    $.cookie('joined', joined);
    _parseWord(joined, 10);
}


var wordParsedCache = new Object();


function _parseWordSync(word) {

    if (word == null || word.trim() == "") return null;

    var desc = wordParsedCache[word];
    if (desc == null) {
        desc = $.parseJSON(jQuery.ajax({
            type:"PUT",
            data:word + "",
            url:server + "punarchi/parse/?maxpathscount=1",
            contentType:"text/plain; charset=utf-8",
            async:false
        }).responseText);

    }
    wordParsedCache[word] = desc;

    return desc;

}

var threadspellCheck = null;

function _isWordOk(tamil) {
    var desc = _lookupWord(tamil)
    var found = false;
    if (desc != null && desc.value != null && desc.value.words != null) {
        var data = desc.value.words;
        if (data.length > 0) {
            found = true;
        }
    }


    if (!found) {
        var descparse = _parseWordSync(tamil);

        if (descparse != null && descparse.length > 0) {
            found = true;
        }
    }
    console.log(tamil + ":parse found:" + found);
    return found;
}

function _getWordSplitIfParsed(tamil) {
    var desc = _lookupWord(tamil)
    var found = "";
    if (desc != null && desc.value != null && desc.value.words != null) {
        var data = desc.value.words;
        if (data.length > 0) {
            return found;
        }
    }


    var descparse = _parseWordSync(tamil);

   // console.log(tamil + ":"+ descparse);
    if (descparse != null && descparse.length > 0 && descparse[0].splitLists && descparse[0].splitLists.length> 0 && descparse[0].splitLists[0].splitList && descparse[0].splitLists[0].splitList.length > 1) {
        found += "(";
        for (var i = 0; i < descparse[0].splitLists[0].splitList.length; i++) {
           if (i !=0) {
               found += " + ";
           }
           found +=  descparse[0].splitLists[0].splitList[i];
        }
        found += ")";

    }

    if (found == "") {
        found = null;
    }
    return found;
}


function _parseWord(joined, count) {
    if (count == null) {
        count = "3";
    }

    $("#status").html(" <div class='small_text'> காத்திருங்கள் ....  </div>");

    var start_time = new Date().getTime();

    jQuery.ajax({
            type:"PUT",
            data:joined + "",
            url:server + "punarchi/parse/?maxpathscount=" + count,
            contentType:"text/plain; charset=utf-8",
            success:function (data, status, jqXHR) {
                var time_taken = new Date().getTime() - start_time;
                $("#result_table tr:gt(0)").detach();
                var table = $("#result_table tr:last");
                if (data == null || data.length == 0) {
                    $("#status").html(" <div class='note'> பகுக்கமுடியவில்லை. (உலாவிநேரம்=" + time_taken + "  மில்லிவினாடிகள்) </div>");
                } else {
                    if (data.length == 1) {
                        $("#status").html("பகுக்கப்பட்ட வழி   #" + data.length + " (உலாவிநேரம்=" + time_taken + "  மில்லிவினாடிகள்)");
                    } else {
                        $("#status").html("பகுக்கப்பட்ட வழிகள்  #" + data.length + " (உலாவிநேரம்=" + time_taken + "  மில்லிவினாடிகள்)");
                    }
                    $.each(data, function (item) {
                        var handlerid = '<tr> <th>' + data[item].handlerName + '</th><td>';
                        var result = "";
                        $.each(data[item].splitLists, function (sub) {

                            var first = true;
                            $.each(data[item].splitLists[sub].splitList, function (sub1) {
                                if (!first) {
                                    result += ' + '
                                }
                                var full = data[item].splitLists[sub].splitList[sub1];
                                var thribu = full.indexOf("(");
                                if (thribu > 0) {
                                    result += "<label>" + full.substring(thribu, full.length);
                                    +"</label>";
                                    full = full.substring(0, thribu);
                                }

                                result += "<label class='lookup'>" + full + "</label>";


                                first = false;
                            });

                        });

                        table.after(handlerid + result + ' </td> </tr>');// JSON.stringify( data[item].splitLists) +' </td> </tr>');
                        // alert(data[item].handlerName);
                    });

                    $('.lookup').mouseenter(function (e) {

                        $("#lookupresult").css({position:"absolute", left:e.pageX, top:e.pageY});
                        $('#lookupresult').show();


                        $("#searched").val($(this).text());
                        $("#searched").trigger("input");

                    });

                    $(".lookup").mouseleave(function () {
                        $('#lookupresult').hide();
                    });
                }
            },
            error:function (jqXHR, status) {
                alert(jqXHR.responseText);
            }
        }
    )
    ;


}


