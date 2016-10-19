package tamil.util.regex;

import my.interest.lang.util.OrderedMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * <p>
 *     A list of one or more compiled {@link tamil.util.regex.TamilPattern}.
 * </p>
 *
 * @author velsubra
 */
public final class FeaturedPatternsList {

    List<TamilPattern> patternlist = null;
    private String basePattern = null;
    FeaturedPatternsList(String basePattern, List<TamilPattern> patternlist) {
        this.patternlist = patternlist;
        this.basePattern = basePattern;
    }
    public FeaturedMatchersList matchersList(CharSequence source)  {

        OrderedMap<Matcher,TamilPattern> list = new OrderedMap<Matcher, TamilPattern>();
        for (TamilPattern p : patternlist) {
            list.put(p.matcher(source),p);
        }
        return new FeaturedMatchersList(basePattern, list, source);

    }

    /**
     * Gets the size of patterns list
     * @return the size
     */
    public int getPatternsListSize() {
        return  this.patternlist.size();
    }
}
