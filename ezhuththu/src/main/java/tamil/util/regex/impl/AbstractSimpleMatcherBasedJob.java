package tamil.util.regex.impl;


import org.json.JSONObject;
import tamil.lang.api.job.JobContext;
import tamil.lang.api.job.JobRunnable;
import tamil.lang.exception.TamilPlatformException;
import tamil.util.regex.SimpleMatcher;


/**
 * A background matcher that finds a series of matching and records to the job context.
 * Every unit of work is represented in a JSON of the form
 * <pre>
 *     {"preSkippedCount":0,"match":"ருள்சேர்","preMatch":"இ","postMatch":""} where
 *
 *     match - the actual text matched by the given pattern against which the matcher {@link #getMatcher()} was created.
 *                 This property will be present.  See {@link #PROP_MATCH_TEXT}
 *     preMatch - the text that was not matched. The match follows immediately this text.
 *                preMatch does not contain all the unmatched text.
 *                The size never exceed the shoulder size returned by {@link #getShoulderSize()}
 *                This may not be present to indicate null string.   See {@link #PROP_PRE_MATCH_TEXT}
 *     preSkippedCount - The number of characters that were skipped.
 *                These many characters are not available as part of the preMatch.
 *                This count does not include the length of preMatch.
 *                This may not be present to indicate a value of 0 . See {@link #PROP_PRE_SKIPPED_COUNT}
 *     postMatch - the text that was not matched. This follows immediately the matched text. This could be null.
 *                This may not be present to indicate null string.   See {@link #PROP_POST_MATCH_TEXT}
 *     postSkippedCount - this may be in the last unit of match.
 *                This returns the last number of characters that were not matched. This count does not include the length of postMatch.
 *                This property may not be present.     See {@link #PROP_POST_SKIPPED_COUNT}
 * </pre>
 *
 * <p>
 * While presenting the search result, The result may be presented in the end user application in the following order
 * <pre>
 *
 *
 * </pre>
 *
 *
 * </p>
 *
 * Created by vjhp on 10/25/2015.
 */
public abstract class AbstractSimpleMatcherBasedJob implements JobRunnable<JSONObject> {

    public static final String PROP_PRE_SKIPPED_COUNT = "preSkippedCount";
    public static final String PROP_PRE_MATCH_TEXT = "preMatch";
    public static final String PROP_MATCH_TEXT = "match";
    public static final String PROP_POST_MATCH_TEXT = "postMatch";
    public static final String PROP_POST_SKIPPED_COUNT = "postSkippedCount";

    /**
     * Method that returns the matcher
     * @return the matcher
     */
    protected abstract SimpleMatcher getMatcher();


    protected String source = null;
    protected String title = null;
    private SimpleMatcher matcher = null;

    /**
     * The size of the text around any match that need to be included in each work unit.
     * The length of the fields preMatch and postMatch never exceeds this value. If the size of the text around a match is more than this value, that part is skipped and not included in the work unit.
     * The skipped length is represented in the fields  preSkippedCount and  postSkippedCount
     * @return  the max size of the  text that need to be included around a match.
     */
    protected int getShoulderSize() {
        return shoulderSize;
    }

    private int shoulderSize = 200;


    public AbstractSimpleMatcherBasedJob(String source, String title) {
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

                    match.put("preMatch", nonMatchText.substring(skipped));
                    if (previousMatch != null) {
                        if (skipped <= shoulderSize) {

                            previousMatch.put("postMatch", nonMatchText.substring(0, skipped));

                        } else {
                            previousMatch.put("postMatch", nonMatchText.substring(0, shoulderSize));
                            skipped = skipped - shoulderSize;
                            match.put("preSkippedCount", skipped);
                        }
                        updatePreviousMatch(context, previousMatch);

                    }

                } else {
                    match.put("preMatch", nonMatchText);

                }

                match.put("match", matchText);

                //update looping context
                lastEnd = matcher.end();
                matchFound(context, match);
                previousMatch = match;
            }
             if (previousMatch != null) {
                 String lastNonMatchText = source.substring(lastEnd, source.length());
                 if (lastNonMatchText.length() > 0) {
                     if (lastNonMatchText.length() > shoulderSize) {
                         previousMatch.put("postSkippedCount", lastNonMatchText.length() - shoulderSize);
                         lastNonMatchText = lastNonMatchText.substring(0, shoulderSize);
                     }
                     previousMatch.put("postMatch", lastNonMatchText);
                 }
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
            context.setPercentOfCompletion((int) (1.0 * matcher.start() / source.length() * 100.0));
        }
//        try {
//            Thread.currentThread().sleep(5000);
//        } catch (Exception e) {}
    }

    protected void config(JobContext<JSONObject> context) {
        context.setAutoFlush(true);

    }

//    private int findRightPlaceToCut(String text, int startBackwardFrom) {
//
//        if (startBackwardFrom <=0) {
//            return 0;
//        } else if (startBackwardFrom> text.length()) {
//            return text.length() - 1;
//        } else {
//
//        }
//    }
}
