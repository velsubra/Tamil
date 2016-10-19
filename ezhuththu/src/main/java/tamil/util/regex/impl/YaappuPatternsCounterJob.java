package tamil.util.regex.impl;

import org.json.JSONObject;
import tamil.lang.TamilFactory;
import tamil.lang.api.job.JobContext;
import tamil.lang.api.regex.*;
import tamil.util.IPropertyFinder;
import tamil.util.regex.SimpleMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 *  Job to count the number of  patterns found in the input source.
 *  It applies all possible feature alternatives that are applicable in யாப்பு context while matching.
 *
 * <br/>
 * E.g)
 * <pre>
 *       source :aஇருள்சேர் இருவினையும் சேரா இறைவன்
 *       The job produces the following JSON . This contains only a single work unit that gets updated.
 *
 *       [{"labels":["${எழுத்து}","${(மொழி)}","${(தேமா)}","${இடைவெளி}${எழுத்து}","${வலியுகரவரிசை}","${அகரவரிசை}"],"counts":[17,3,1,3,0,1]}]
 *
 *        where,
 *         labels - Array, the given list of patterns
 *         counts - Array, the list counts of  patterns, in the same order as the labels
 *
 *       "${எழுத்து}","${(மொழி)}","${(தேமா)}","${இடைவெளி}${எழுத்து}","${வலியுகரவரிசை}","${அகரவரிசை}" are the given list patterns.
 *
 *
 * </pre>
 *
 * @author velsubra
 */
public class YaappuPatternsCounterJob extends AbstractPatternsCounterJob {
    private List<String> patterns = null;
    private IPropertyFinder aliasFinder = null;

    public YaappuPatternsCounterJob(String source, String title, List<String> patterns, IPropertyFinder aliasFinder) {
        super(source, title);
        this.patterns = patterns;
        this.aliasFinder = aliasFinder;
    }

    @Override
    public List<SimpleMatcher> getMatchers(JobContext<JSONObject> context) {
        List<RXFeature> alternatives = getAlternatives();

        List<SimpleMatcher> list = new ArrayList<SimpleMatcher>(this.patterns.size());
        for (String pattern : this.patterns) {
            list.add(TamilFactory.getRegEXCompiler().compileToPatternsList(pattern, getAliasFinder(), getBaseFeatures(), alternatives == null ? null : alternatives.toArray(new RXFeature[0])).matchersList(source));
            context.setStatusMessage("Creating matchers #  " + list.size() +" of " + this.patterns.size());
            context.flush();
        }
        return list;

    }


    protected List<RXFeature> getBaseFeatures() {
        List<RXFeature> list = new ArrayList<RXFeature>();
        list.add(RXIncludeCanonicalEquivalenceFeature.FEATURE);
        list.add(RXOverrideSysDefnFeature.FEATURE);
        list.add(RXKeLaAsKouFeature.FEATURE);
        return list;
    }


    protected List<RXFeature> getAlternatives() {

        List<RXFeature> list = new ArrayList<RXFeature>();
        list.add(RXAythamAsKurrilFeature.FEATURE);
        list.add(RXKuttuAcrossCirFeature.FEATURE);
        list.add(RXKuttuFeature.FEATURE);
        list.add(RXDesolvedKuttialigaramFeature.FEATURE);
        return list;
    }


    protected IPropertyFinder getAliasFinder() {
        return aliasFinder;
    }

}
