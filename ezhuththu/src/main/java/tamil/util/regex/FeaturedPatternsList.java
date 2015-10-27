package tamil.util.regex;

import java.util.ArrayList;
import java.util.List;
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

        List<Matcher> list = new ArrayList<Matcher>();
        for (TamilPattern p : patternlist) {
            list.add(p.matcher(source));
        }
        return new FeaturedMatchersList(basePattern,list);

    }

    /**
     * Gets the size of patterns list
     * @return the size
     */
    public int getPatternsListSize() {
        return  this.patternlist.size();
    }
}
