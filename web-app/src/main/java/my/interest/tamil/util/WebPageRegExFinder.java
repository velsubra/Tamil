package my.interest.tamil.util;

import my.interest.lang.util.NameValuePair;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.parser.ParseFailureFindIndexFeature;
import tamil.lang.api.parser.ParserResult;
import tamil.lang.api.parser.ParserResultCollection;
import tamil.lang.api.parser.VallinavottuEndingOk;
import tamil.lang.api.regex.RXFeature;
import tamil.util.IPropertyFinder;
import tamil.util.regex.TamilPattern;

import java.util.regex.Matcher;

/**
 * Created by velsubra on 6/11/16.
 */
public class WebPageRegExFinder extends AbstractJSoupJob {
    private String rx  = null;
    TamilPattern pattern = null;

    int matchCount = 0;
    public WebPageRegExFinder(String rx, String dataUrl, String submiturl, String viewurl, String scripturl, String cssurl, IPropertyFinder finder) {
        super(dataUrl, submiturl, viewurl, scripturl, cssurl);
        this.rx = rx;
        pattern = TamilFactory.getRegEXCompiler().compile(rx,finder,null);
    }

    @Override
    public NameValuePair<String, Integer> process(String text) throws Exception {
        Matcher matcher = pattern.matcher(text);
        StringBuffer buffer = new StringBuffer();

        int index = 0;
        while (matcher.find()) {
            index = matcher.end();
            String tamil = text.substring(matcher.start(), index);

            buffer.append(text.substring(index, matcher.start()));
            buffer.append("<span class='found'>" + tamil + "</span>");
            buffer.append("<sup>" + matchCount + "</sup>");
            matchCount ++;

        }
        String last = text.substring(index);
        buffer.append(last);

        return new NameValuePair<String, Integer>(buffer.toString(), matchCount);
    }
}
