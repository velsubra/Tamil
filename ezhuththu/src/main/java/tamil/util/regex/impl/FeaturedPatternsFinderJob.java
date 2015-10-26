package tamil.util.regex.impl;

import tamil.lang.TamilFactory;
import tamil.lang.api.regex.RXFeature;
import tamil.util.IPropertyFinder;
import tamil.util.regex.SimpleMatcher;

import java.util.List;

/**
 * Created by vjhp on 10/25/2015.
 */
public abstract class FeaturedPatternsFinderJob extends SimpleMatcherBasedJob {

    private String pattern = null;

    public abstract List<RXFeature> getBaseFeatures();

    public abstract List<RXFeature> getAlternatives();

    public abstract IPropertyFinder getAliasFinder();

    public SimpleMatcher getMatcher() {
        List<RXFeature> alternatives = getAlternatives();
        return TamilFactory.getRegEXCompiler().compileToPatternsList(pattern, getAliasFinder(), getBaseFeatures(),alternatives == null ? null : alternatives.toArray(new RXFeature[0])).matchersList(source);

    }


    public FeaturedPatternsFinderJob(String source, String pattern) {
        super(source, "Finding the  pattern:" + pattern + " with possible combinations of features.");
        this.pattern = pattern;
    }
}
