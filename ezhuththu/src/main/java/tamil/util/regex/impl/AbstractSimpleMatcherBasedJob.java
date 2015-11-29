package tamil.util.regex.impl;


import my.interest.lang.tamil.TamilUtils;
import org.json.JSONObject;
import tamil.lang.api.job.JobContext;
import tamil.lang.api.job.JobRunnable;
import tamil.lang.exception.TamilPlatformException;
import tamil.util.regex.SimpleMatcher;

import java.util.Date;


/**
 * A background matcher that finds a series of matching and records to the job context.
 * Every unit of work is represented in a JSON of the form
 * <pre>
 *     {"preSkippedCount":0,"match":"ருள்சேர்","preMatch":"இ","postMatch":""} where
 *
 *     match - the actual text matched by the given pattern against which the matcher {@link #createMatcher()} was created.
 *                 This property will be present. See {@link #PROP_MATCH_TEXT}
 *     preMatch - the text that was not matched. The match follows immediately this text.
 *                preMatch does not contain all the unmatched text.
 *                The size never exceed the shoulder size returned by {@link #getShoulderSize()}
 *                This may not be present to indicate null string. See {@link #PROP_PRE_MATCH_TEXT}
 *     preSkippedCount - The number of characters that were skipped.
 *                These many characters are not available as part of the preMatch.
 *                This count does not include the length of preMatch.
 *                This may not be present to indicate a value of 0 . See {@link #PROP_PRE_SKIPPED_COUNT}
 *     postMatch - the text that was not matched. This follows immediately the matched text. This could be null.
 *                This may not be present to indicate null string. See {@link #PROP_POST_MATCH_TEXT}
 *     postSkippedCount - this may be in the last unit of match.
 *                This returns the last number of characters that were not matched. This count does not include the length of postMatch.
 *                This property may not be present. See {@link #PROP_POST_SKIPPED_COUNT}
 * </pre>
 * <p/>
 * <p>
 * While presenting the search result, The result may be presented in the end user application in the following order
 * <pre>
 *
 * StringBuffer buffer = new StringBuffer();
 * for (JSONObject json : resultSnapShot.getNewResults(0).getChunk()) {
 *   if (json.has(AbstractSimpleMatcherBasedJob.{@link #PROP_PRE_SKIPPED_COUNT})) {
 *      buffer.append("\n.... " + json.getInt(AbstractSimpleMatcherBasedJob.PROP_PRE_SKIPPED_COUNT) + " code points skipped...\n");
 *   }
 *   if (json.has(AbstractSimpleMatcherBasedJob.{@link #PROP_PRE_MATCH_TEXT})) {
 *       buffer.append(json.getString(AbstractSimpleMatcherBasedJob.{@link #PROP_PRE_MATCH_TEXT}));
 *   }
 *   buffer.append("\n\n");  //Match starts
 *   buffer.append(json.getString(AbstractSimpleMatcherBasedJob.{@link #PROP_MATCH_TEXT));
 *   //Match ends
 *   buffer.append("\n\n");
 *   if (json.has(AbstractSimpleMatcherBasedJob.{@link #PROP_MATCH_TEXT))) {
 *      buffer.append(json.getString(AbstractSimpleMatcherBasedJob.PROP_POST_MATCH_TEXT));
 *   }
 *   if (json.has(AbstractSimpleMatcherBasedJob.{@link #PROP_POST_SKIPPED_COUNT})) {
 *     buffer.append("\n.... " + json.getInt(AbstractSimpleMatcherBasedJob.{@link #PROP_POST_SKIPPED_COUNT}) + " code points skipped...\n");
 *   }
 * }
 *  System.out.println(buffer.toString());
 *
 * </pre>
 * <p/>
 * <p/>
 * </p>
 * <p/>
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
     *
     * @return the matcher
     */
    public abstract SimpleMatcher createMatcher();


    /**
     * Just gets the previously created matcher
     * @return matcher if it was created already or creates a new one
     */
    public SimpleMatcher getMatcher() {
        if (createdMatcher == null) {

            createdMatcher = createMatcher();
        }
        return  createdMatcher;
    }



    protected String source = null;
    protected String title = null;
    private SimpleMatcher createdMatcher = null;

    /**
     * The size of the text around any match that need to be included in each work unit.
     * The length of the fields preMatch and postMatch never exceeds this value. If the size of the text around a match is more than this value, that part is skipped and not included in the work unit.
     * The skipped length is represented in the fields  preSkippedCount and  postSkippedCount
     *
     * @return the max size of the  text that need to be included around a match.
     */
    protected int getShoulderSize() {
        return shoulderSize;
    }


    /**
     * Method that gets called as the first operation from the job. This method allows override the actual input source with in the the job context.
     * One use case is that users can pass an URL in the constructor and read that url in this method and return the content of the url for the processing.
     *
     * @param context     the job context
     * @param inputSource the input source that was passed in the constructor.
     * @return the input source that will actually be processed. The default implementation is to return the same source.
     */
    protected String resolveSource(JobContext<JSONObject> context, String inputSource) {
        return inputSource;
    }

    private int shoulderSize = 200;


    /**
     * Constructor for a job.
     *
     * @param source the source or the pointer to the source text that needs to be processed. Please see {@link #resolveSource(tamil.lang.api.job.JobContext, String)}
     * @param title  the title for the job
     */
    public AbstractSimpleMatcherBasedJob(String source, String title) {
        this.source = source;
        this.title = title;
    }

    public final void run(JobContext<JSONObject> context) {
        try {
            int longSourceKB = 10;
            String actualInput = this.source;
            context.setTitleMessage(title);
            this.source = resolveSource(context, this.source);
            config(context);
            shoulderSize = getShoulderSize();
            int lastEnd = 0;
            JSONObject previousMatch = null;
            SimpleMatcher  matcher = getMatcher();
            boolean longSource = source.length() -lastEnd > 1024 * longSourceKB;

            if (!longSource) {
                context.setStatusMessage("Entering the search ...");
            } else {
                context.setStatusMessage("Entering the search in a  long source of data (" + (source.length() - lastEnd)/ 1024 +" KB)  at " + new Date().toString() + ". It might take a while to find the first match ...");
            }
            int matches = 0;
            while (matcher.find()) {
                matches ++;
                if (previousMatch != null) {
                    insertPreviousMatch(context, previousMatch, true);
                }
                JSONObject match = new JSONObject();
               // System.out.println("Last end:" + lastEnd + " start:" + matcher.start() + " End:" + matcher.end());
                String nonMatchText = source.substring(lastEnd, matcher.start());
                String matchText = source.substring(matcher.start(), matcher.end());


                if (nonMatchText.length() > shoulderSize) {
                    int skipped = nonMatchText.length() - shoulderSize;

                    match.put(PROP_PRE_MATCH_TEXT, nonMatchText.substring(skipped));
                    if (previousMatch != null) {
                        if (skipped <= shoulderSize) {

                            previousMatch.put(PROP_POST_MATCH_TEXT, nonMatchText.substring(0, skipped));
                            skipped = 0;
                        } else {
                            previousMatch.put(PROP_POST_MATCH_TEXT, nonMatchText.substring(0, shoulderSize));
                            skipped = skipped - shoulderSize;

                        }
                        updatePreviousMatch(context, previousMatch);

                    }
                    if (skipped > 0) {
                        match.put(PROP_PRE_SKIPPED_COUNT, skipped);
                    }

                } else {
                    match.put(PROP_PRE_MATCH_TEXT, nonMatchText);

                }

                match.put(PROP_MATCH_TEXT, matchText);

                //update looping context
                lastEnd = matcher.end();

                previousMatch = match;

                longSource = (source.length() - lastEnd) > 1024 * longSourceKB;

                if (!longSource) {
                    context.setStatusMessage(matches + " match(es) found. We are almost there. Continuing to find the next match ...");
                } else {
                    context.setStatusMessage(matches + " match(es) found. Continuing to find the next match. The source is still long (" + (source.length() - lastEnd)/ 1024 +" KB). It might take a while to find the next match ...");
                }
            }
            if (previousMatch != null) {
                String lastNonMatchText = source.substring(lastEnd, source.length());
                if (lastNonMatchText.length() > 0) {
                    if (lastNonMatchText.length() > shoulderSize) {
                        previousMatch.put(PROP_POST_SKIPPED_COUNT, lastNonMatchText.length() - shoulderSize);
                        lastNonMatchText = lastNonMatchText.substring(0, shoulderSize);
                    }
                    previousMatch.put(PROP_POST_MATCH_TEXT, lastNonMatchText);
                }
                insertPreviousMatch(context, previousMatch, false);
            }
            context.setStatusMessage("Total matches found:" + matches);


        } catch (Exception e) {
            e.printStackTrace();
            throw new TamilPlatformException(e.getMessage());
        }
    }

    protected void updatePreviousMatch(JobContext<JSONObject> context, JSONObject previousMatch) {
        context.updateLastResult(previousMatch);
    }

    protected void insertPreviousMatch(JobContext<JSONObject> context, JSONObject previousMatch, boolean currentMatchAvailable) {
        context.addResult(previousMatch);
        if (currentMatchAvailable && source.length() > 0) {
            context.setPercentOfCompletion((int) (1.0 * createdMatcher.start() / source.length() * 100.0));
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
