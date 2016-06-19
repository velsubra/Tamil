package my.interest.tamil.util;

import my.interest.lang.util.NameValuePair;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.parser.*;
import tamil.util.regex.TamilPattern;

import java.util.regex.Matcher;

/**
 * Created by velsubra on 6/11/16.
 */
public class WebPageSpellChecker extends AbstractJSoupJob {
    TamilPattern pattern = null;
    CompoundWordParser parser = TamilFactory.getCompoundWordParser();
    int errorcount = 0;

    public WebPageSpellChecker(String dataUrl, String submiturl, String viewurl, String scripturl, String cssurl) {
        super(dataUrl, submiturl, viewurl, scripturl, cssurl);
        pattern = TamilFactory.getRegEXCompiler().compile("(${எழுத்து})+");
    }

    @Override
    public NameValuePair<String, Integer> process(String text) throws Exception {
        Matcher matcher = pattern.matcher(text);
        StringBuffer buffer = new StringBuffer();

        int index = 0;
        while (matcher.find()) {
            buffer.append(text.substring(index, matcher.start()));
            index = matcher.end();
            String tamil = text.substring(matcher.start(), index);
            ParserResultCollection collection = parser.parse(TamilWord.from(tamil), 1, ParseFailureFindIndexFeature.FEATURE, VallinavottuEndingOk.FEATURE);
            if (collection.isEmpty()) {
                errorcount++;
                buffer.append("<span id='" + tamil_result_id_prefix + errorcount + "'  class='error'>" + tamil + "</span>");

                buffer.append("<sup>" + errorcount + "</sup>");
            } else {
                ParserResult result = collection.getList().get(0);
                ParserResult.PARSE_HINT hint = result.getParseHint();
                if (result.isParsed()) {
                    buffer.append(tamil);
                } else {
                    errorcount++;
                    if (hint == null) {

                        buffer.append("<span id='" + tamil_result_id_prefix + errorcount + "'  class='error'>" + tamil + "</span>");
                    } else {
                        buffer.append(tamil.substring(0, hint.getUnicodeStartIndex()));
                        buffer.append("<span id='" + tamil_result_id_prefix + errorcount + "' class='error'>" + tamil.substring(hint.getUnicodeStartIndex()) + "</span>");
                        //buffer.append(tamil.substring(hint.getUnicodeEndIndex()));
                    }
                    buffer.append("<sup>" + errorcount + "</sup>");
                }

            }

        }
        String last = text.substring(index);
        buffer.append(last);
        // System.out.println("Error count:" + errorcount);
        return new NameValuePair<String, Integer>(buffer.toString(), errorcount);
    }
}
