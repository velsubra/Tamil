package tamil.util.regex.impl;

import tamil.lang.api.regex.*;
import tamil.util.IPropertyFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vjhp on 10/25/2015.
 */
public class YaappuPatternFinderJob extends FeaturedPatternsFinderJob {
    @Override
    public List<RXFeature> getBaseFeatures() {
        List<RXFeature> list = new ArrayList<RXFeature>();
        list.add(RXIncludeCanonicalEquivalenceFeature.FEATURE);
        list.add(RXKuttuFeature.FEATURE);
        return list;
    }

    @Override
    public List<RXFeature> getAlternatives() {

        List<RXFeature> list = new ArrayList<RXFeature>();
        list.add(RXAythamAsKurrilFeature.FEATURE);
        list.add(RXKuttuAcrossCirFeature.FEATURE);
        return list;
    }

    @Override
    public IPropertyFinder getAliasFinder() {
        return null;
    }

    public YaappuPatternFinderJob(String source, String pattern) {
        super(source, pattern);
    }
}
