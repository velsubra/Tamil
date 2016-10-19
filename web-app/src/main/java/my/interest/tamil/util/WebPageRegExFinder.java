package my.interest.tamil.util;

import common.lang.impl.AbstractCharacter;
import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.util.NameValuePair;
import org.json.JSONObject;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.job.JobContext;
import tamil.lang.api.parser.ParseFailureFindIndexFeature;
import tamil.lang.api.parser.ParserResult;
import tamil.lang.api.parser.ParserResultCollection;
import tamil.lang.api.parser.VallinavottuEndingOk;
import tamil.lang.api.regex.RXFeature;
import tamil.util.IPropertyFinder;
import tamil.util.regex.FeaturedPatternsList;
import tamil.util.regex.SimpleMatcher;
import tamil.util.regex.TamilPattern;

import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by velsubra on 6/11/16.
 */
public class WebPageRegExFinder extends AbstractJSoupJob {

    FeaturedPatternsList patternsList;

    protected int matchCount = 0;
    String pattern = null;
    IPropertyFinder aliasFinder = null;
    String baseFeatures = null;
    String alternativeFeature = null;


    public WebPageRegExFinder(String dataUrl, String submiturl, String viewurl, String scripturl, String cssurl, String pattern, IPropertyFinder aliasFinder, String baseFeatures, String alternativeFeature) {
        super(dataUrl, submiturl, viewurl, scripturl, cssurl);
        this.pattern = pattern;
        this.aliasFinder = aliasFinder;
        this.baseFeatures = baseFeatures;
        this.alternativeFeature = alternativeFeature;


    }

    public void run(JobContext<JSONObject> context) throws Exception {
        if (this.baseFeatures == null) {
            this.baseFeatures = "";
        }
        if (this.alternativeFeature == null) {
            this.alternativeFeature = "";
        }
        this.patternsList = TamilFactory.getRegEXCompiler().compileToPatternsList(this.pattern, this.aliasFinder, FeatureSet.findFeatures(RXFeature.class, baseFeatures), FeatureSet.findFeatures(RXFeature.class, this.alternativeFeature).toArray(new RXFeature[0]));
        super.run(context);
    }

    @Override
    public NameValuePair<String, Integer> process(String text) throws Exception {
        SimpleMatcher matcher = this.patternsList.matchersList(text);
        StringBuffer buffer = new StringBuffer();

        int index = 0;
        while (matcher.find()) {
            SimpleMatcher.MatchingModel model = matcher.buildMatchingModel();
            System.out.println("\n\n===> Model:" + model.asJson().toString(3));

            System.out.println("\n\n===> Model:" + model.asGoJsJson().toString(3));

            buffer.append(text.substring(index, matcher.start()));

            index = matcher.end();

            String tamil = text.substring(matcher.start(), index);
            matchCount++;

            String pattern = TamilWord.from(tamil, true).toUnicodeStringRepresentation(FeatureSet.EMPTY);

            if (tamil.length() > 0) {
                pattern +=  ":" + Character.UnicodeBlock.of(tamil.charAt(0)).toString();
            }

            if (tamil.equals(String.valueOf(AbstractCharacter.ZWNBSP))) {
                tamil = "zero-width-bom";
            }

            buffer.append("<span title='" + pattern + "'  id='" + tamil_result_id_prefix + matchCount + "'  class='error'>" + tamil + "</span>");
            buffer.append("<sup  class='error'>" + matchCount + "</sup>");

        }
        String last = text.substring(index);
        buffer.append(last);

        return new NameValuePair<String, Integer>(buffer.toString(), matchCount);
    }
}
