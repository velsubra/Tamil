package tamil.util.regex.impl;

import tamil.lang.api.regex.*;
import tamil.util.IPropertyFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds a pattern in the background.   It applies all possible feature alternatives that are applicable in யாப்பு context
 * <br/>
 * E.g)
 * <pre>
 *   source: இருள்சேர் இருவினையும் சேரா இறைவன்
 *   pattern: ${தேமா}
 *
 *   The job does 4 units of work
 *   [{"preSkippedCount":0,"match":"ருள்சேர்","preMatch":"இ"},
 *   {"preSkippedCount":0,"match":"னையும்","preMatch":" இருவி"},
 *   {"preSkippedCount":0,"match":"சேரா","preMatch":" "},
 *   {"preSkippedCount":0,"match":"றைவன்","preMatch":" இ"}]
 *
 * </pre>
 *
 *
 * Created by vjhp on 10/25/2015.
 */
public class YaappuPatternFinderJob extends AbstractFeaturedPatternFinderJob {

    IPropertyFinder aliasFinder = null;
    boolean transpose = false;

    @Override
    public List<RXFeature> getBaseFeatures() {
        List<RXFeature> list = new ArrayList<RXFeature>();
        list.add(RXIncludeCanonicalEquivalenceFeature.FEATURE);
        list.add(RXOverrideSysDefnFeature.FEATURE);
        return list;
    }

    @Override
    public List<RXFeature> getAlternatives() {

        List<RXFeature> list = new ArrayList<RXFeature>();
        list.add(RXAythamAsKurrilFeature.FEATURE);
        list.add(RXKuttuAcrossCirFeature.FEATURE);
        list.add(RXKuttuFeature.FEATURE);
        return list;
    }

    @Override
    public IPropertyFinder getAliasFinder() {
        return aliasFinder;
    }


    @Override
    public boolean isToTransposeMatcher() {
        return transpose;
    }

    public YaappuPatternFinderJob(String source, String pattern) {
        this(source, pattern, null, false);
    }

    public YaappuPatternFinderJob(String source, String pattern, IPropertyFinder aliasFinder, boolean transpose) {
        super(source, pattern,"Finding the  pattern:" + pattern + " with possible combinations of features. Search Negated:" + transpose);
        this.aliasFinder = aliasFinder;
        this.transpose = transpose;
    }
}
