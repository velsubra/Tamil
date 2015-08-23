package my.interest.lang.tamil.parser.impl;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.parser.impl.laws.CompoundWordParserImpl;
import my.interest.lang.tamil.parser.impl.sax.SaxParser;
import tamil.lang.TamilWord;
import tamil.lang.api.parser.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class CompositeParser implements CompoundWordParser {

    List<CompoundWordParser> parsers = new ArrayList<CompoundWordParser>();

    public CompositeParser() {
        parsers.add(new SaxParser());
        parsers.add(new CompoundWordParserImpl());
    }


    public ParserResult quickParse(TamilWord singleWord) {
        ParserResultCollection list = parse(singleWord, 10, null);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.getList().get(0);
    }

    public ParserResultCollection parse(TamilWord singleWord, int maxReturn, ParseFeature... features) {

        FeatureSet set = new FeatureSet(features);

        boolean eageradded = false;
        if (!set.isFeatureEnabled(EagerlyParsingFeature.class)) {
            set.addFeature(EagerlyParsingFeature.FEATURE);
            eageradded = true;
        }

        ParserResultCollection collection = tryParsing(singleWord, maxReturn > 5 ? maxReturn : 5, set);
        if (eageradded && (collection.isEmpty() || !collection.getList().get(0).isParsed())) {
            set.removeFeature(EagerlyParsingFeature.class);
            collection = tryParsing(singleWord, maxReturn > 5 ? maxReturn : 5, set);
        }
        while (maxReturn >= 0 && collection.getList().size() > maxReturn) {
            collection.getList().remove(collection.size() - 1);
        }
        return collection;
    }

    private ParserResultCollection tryParsing(TamilWord singleWord, int maxReturn, FeatureSet set) {
        ParserResultCollection collection = null;
        for (CompoundWordParser parser : parsers) {
            collection = parser.parse(singleWord, maxReturn, set.getFeatures(ParseFeature.class).toArray(new ParseFeature[0]));
            if (collection.isEmpty()) {
                continue;
            } else {
                if (collection.getList().get(0).isParsed()) {
                    return collection;
                }
            }
        }
        return collection;
    }


}
