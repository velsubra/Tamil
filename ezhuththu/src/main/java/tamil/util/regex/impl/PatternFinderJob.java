package tamil.util.regex.impl;

import org.json.JSONObject;
import tamil.lang.api.job.JobContext;
import tamil.lang.api.job.JobRunnable;
import tamil.util.regex.TamilPattern;

import java.util.regex.Matcher;

/**
 * <p>
 * Implementations to search for Tamil regular Expression.
 * </p>
 *
 * @author velsubra
 */
public final class PatternFinderJob implements JobRunnable<JSONObject> {

    private TamilPattern pattern = null;
    private CharSequence source = null;

    public boolean isMatchNonPattern() {
        return matchNonPattern;
    }

    public void setMatchNonPattern(boolean matchNonPattern) {
        this.matchNonPattern = matchNonPattern;
    }

    private boolean matchNonPattern = false;

    /**
     * constructor for the job
     *
     * @param pattern the pattern to be found
     * @param source  the source text search
     */
    public PatternFinderJob(TamilPattern pattern, CharSequence source) {
        this.pattern = pattern;
        this.source = source;
    }

    public void run(JobContext<JSONObject> context) {
        Matcher matcher = pattern.matcher(source);
        int pointer = 0;
    }
}
