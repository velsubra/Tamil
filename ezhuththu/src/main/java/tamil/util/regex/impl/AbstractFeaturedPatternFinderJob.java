package tamil.util.regex.impl;

import tamil.lang.TamilFactory;
import tamil.lang.api.regex.RXFeature;
import tamil.util.IPropertyFinder;
import tamil.util.regex.SimpleMatcher;

import java.util.List;

/**
 * The job applies a set of alternative {@link tamil.lang.api.regex.RXFeature}s to in order to the pattern.
 * Pattern is matched if any of the search with altering the feature combination matches.
 * Created by vjhp on 10/25/2015.
 */
public abstract class AbstractFeaturedPatternFinderJob extends AbstractSimpleMatcherBasedJob {

    private String pattern = null;

    /**
     * The base list of features that are applied. The alternative features are applied on top of these.
     * @return  the list of Base features.
     */
    public abstract List<RXFeature> getBaseFeatures();

    /**
     * Gets the alternative list of features that need to be applied to find a match
     * @return the list of alternative features. It could be null or empty.
     */
    public abstract List<RXFeature> getAlternatives();

    /**
     * Method to return the alias finder if there are any custom definitions.
     * @return the alias finder
     */
    public abstract IPropertyFinder getAliasFinder();


    protected SimpleMatcher getMatcher() {
        List<RXFeature> alternatives = getAlternatives();
        return TamilFactory.getRegEXCompiler().compileToPatternsList(pattern, getAliasFinder(), getBaseFeatures(), alternatives == null ? null : alternatives.toArray(new RXFeature[0])).matchersList(source);

    }


    /**
     * Creates an un-submitted job
     *
     * @param source  the source to search in.
     * @param pattern the pattern to be searched for.
     */
    public AbstractFeaturedPatternFinderJob(String source, String pattern) {
        super(source, "Finding the  pattern:" + pattern + " with possible combinations of features.");
        this.pattern = pattern;
    }
}
