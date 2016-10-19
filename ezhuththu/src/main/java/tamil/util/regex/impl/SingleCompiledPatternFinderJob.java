package tamil.util.regex.impl;

import my.interest.lang.tamil.TamilUtils;
import org.json.JSONObject;
import tamil.lang.api.job.JobContext;
import tamil.util.regex.SimpleMatcher;
import tamil.util.regex.TamilPattern;

import java.util.regex.Matcher;

/**
 * <p>
 * Job implementation to search for Tamil regular Expression.
 * </p>
 *
 * @author velsubra
 */
public class SingleCompiledPatternFinderJob extends AbstractSimpleMatcherBasedJob {
    private TamilPattern pattern = null;

    /**
     * constructor for the job
     *
     * @param source  the source text search
     * @param pattern the pattern to be found
     */
    public SingleCompiledPatternFinderJob(String source, TamilPattern pattern, String title) {
        super(source, title);
        this.pattern = pattern;
    }

    @Override
    public SimpleMatcher createMatcher() {
        return new SimpleMatcher0(pattern.matcher(source),this.pattern, this.source);
    }

    protected void config(JobContext<JSONObject> context) {
        super.config(context);

    }

    private static class SimpleMatcher0 implements SimpleMatcher {
        private Matcher matcher = null;
        private TamilPattern pattern = null;
        private String source = null;

        SimpleMatcher0(Matcher matcher,TamilPattern pattern, String source) {
            this.matcher = matcher;
            this.pattern = pattern;
            this.source = source;
        }

        public boolean find() {
            return matcher.find();
        }

        public int start() {
            return matcher.start();
        }

        public int end() {
            return matcher.end();
        }

        public String getPattern() {
            return this.pattern.getTamilPattern();
        }

        public boolean isTransposed() {
            return false;
        }


        public int getSourceLength() {
            return source.length();
        }

        public String group() {
            return matcher.group();
        }

        public String group(String name) {
            return matcher.group(name);
        }

        public int groupCount() {
            return matcher.groupCount();
        }

        public String group(int group) {
            return matcher.group(group);
        }

        public MatchingModel buildMatchingModel() {
            return TamilUtils.buildMatchingModel(pattern,matcher);
        }
    }


}
