package tamil.util.regex.impl;


import org.json.JSONObject;
import tamil.lang.api.job.JobContext;
import tamil.lang.api.job.JobRunnable;
import tamil.lang.exception.TamilPlatformException;
import tamil.util.regex.SimpleMatcher;


/**
 * A background matcher that finds a series of matching and records to the job context.
 * Created by vjhp on 10/25/2015.
 */
public abstract class SimpleMatcherBasedJob implements JobRunnable<JSONObject> {
    public abstract SimpleMatcher getMatcher();


    protected String source = null;
    protected String title = null;
    private SimpleMatcher matcher = null;

    protected int getShoulderSize() {
        return shoulderSize;
    }

    private int shoulderSize = 100;


    public SimpleMatcherBasedJob(String source, String title) {
        this.source = source;
        this.title = title;
    }

    public void run(JobContext<JSONObject> context) {
        try {
            context.setTitleMessage(title);
            context.setTitleId(title);
            config(context);
            shoulderSize = getShoulderSize();
            int lastEnd = 0;
            JSONObject previousMatch = null;
            matcher = getMatcher();
            while (matcher.find()) {
                JSONObject match = new JSONObject();
                String nonMatchText = source.substring(lastEnd, matcher.start());
                String matchText = source.substring(matcher.start(), matcher.end());


                if (nonMatchText.length() > shoulderSize) {
                    int skipped = nonMatchText.length() - shoulderSize;
                    nonMatchText = nonMatchText.substring(skipped);
                    match.put("preMatch", nonMatchText);
                    if (previousMatch != null) {
                        if (skipped <= shoulderSize) {

                            previousMatch.put("postMatch", nonMatchText.substring(lastEnd, lastEnd + skipped));
                            skipped = 0;
                        } else {
                            previousMatch.put("postMatch", nonMatchText.substring(lastEnd, lastEnd + shoulderSize));
                            skipped = skipped - shoulderSize;
                        }
                        updatePreviousMatch(context, previousMatch);

                    }
                    match.put("preSkippedCount", skipped);
                } else {
                    match.put("preMatch", nonMatchText);
                    match.put("preSkippedCount", 0);
                }

                match.put("match", matchText);

                //update looping context
                lastEnd = matcher.end();
                matchFound(context, match);
                previousMatch = match;
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new TamilPlatformException(e.getMessage());
        }
    }

    protected void updatePreviousMatch(JobContext<JSONObject> context, JSONObject previousMatch) {
        context.updateLastResult(previousMatch);
    }

    protected void matchFound(JobContext<JSONObject> context, JSONObject match) {
        context.addResult(match);
        if (source.length() > 0) {
            context.setPercentOfCompletion((int) (matcher.start() / source.length() * 100.0));
        }
    }

    protected void config(JobContext<JSONObject> context) {
        context.setAutoFlush(true);
        context.setTitleMessage("Searching for a pattern ...");
    }
}
