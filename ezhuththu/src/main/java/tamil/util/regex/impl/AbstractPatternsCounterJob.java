package tamil.util.regex.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import tamil.lang.api.job.JobContext;
import tamil.lang.api.job.JobRunnable;
import tamil.lang.exception.TamilPlatformException;
import tamil.util.regex.SimpleMatcher;

import java.util.List;

/**
 * <p>
 *     Job to count the number  patterns of a given list.
 *     E.g
 *     <pre>
 *         source :aஇருள்சேர் இருவினையும் சேரா இறைவன்
 *
 *         The job produces the following JSON . This contains only a single work unit that gets updated.
 *         [{"labels":["${எழுத்து}","${(மொழி)}","${(தேமா)}","${இடைவெளி}${எழுத்து}","${வலியுகரவரிசை}","${அகரவரிசை}"],"counts":[17,3,1,3,0,1]}]
 *
 *         where,
 *         labels - Array, the given list of patterns whose matchers are returned by {@link #getMatchers()}. See {@link #PROP_LABELS_ARRAY}
 *         counts - Array, the list counts of patterns in the same order as in the labels. See {@link #PROP_COUNTS_ARRAY}
 *
 *         "${எழுத்து}","${(மொழி)}","${(தேமா)}","${இடைவெளி}${எழுத்து}","${வலியுகரவரிசை}","${அகரவரிசை} are the given list of patterns to be counted on the source.
 *
 *     </pre>
 *
 * </p>
 *
 * @author velsubra
 */
public abstract class AbstractPatternsCounterJob implements JobRunnable<JSONObject> {


    public static final String PROP_LABELS_ARRAY = "labels";
    public static final String PROP_COUNTS_ARRAY = "counts";

    protected String source;
    protected String title = null;
    private List<SimpleMatcher> matchers = null;


    public abstract List<SimpleMatcher> getMatchers();


    /**
     * Creates an un-submitted job.
     * @param source the source or the pointer to the source text that needs to be processed. Please see {@link #resolveSource(tamil.lang.api.job.JobContext, String)}
     * @param title the title message for the job.
     */
    public AbstractPatternsCounterJob(String source, String title) {
        this.source = source;
        this.title = title;
    }


    /**
     * Method that gets called as the first operation from the job. This method allows override the actual input source with in the the job context.
     * One use case is that users can pass an URL in the constructor and read that url in this method and return the content of the url for the processing.
     * @param context  the job context
     * @param inputSource  the input source that was passed in the constructor.
     * @return the input source that will actually be processed. The default implementation is to return the same source.
     */
    protected String resolveSource(JobContext<JSONObject> context, String inputSource) {
        return inputSource;
    }


    public final void run(JobContext<JSONObject> context) {
        try {
            String actualInput = this.source;
            context.setTitleMessage(title);
            context.setTitleId(title);
            source = resolveSource(context, source);
            config(context);
            matchers = getMatchers();

            JSONObject json = new JSONObject();
            JSONArray labels = new JSONArray();
            JSONArray counts = new JSONArray();
            json.put(PROP_LABELS_ARRAY, labels);
            json.put(PROP_COUNTS_ARRAY, counts);
            for (SimpleMatcher matcher : matchers) {

                counts.put(0);
                labels.put(matcher.getPattern());
            }
            double singleLabelWeight = 100.0/ (matchers.size());
            for (int i = 0; i < matchers.size(); i++) {
                SimpleMatcher matcher = matchers.get(i);
                context.setStatusMessage("Counting pattern:" + matcher.getPattern());
                while (matcher.find()) {
                    int singlePercent =  (int) (1.0 / source.length() * matcher.end() *100);
                    int overallPercent = (int) (1.0 * i * singleLabelWeight + singlePercent /100.0 * singleLabelWeight);
                    int count = counts.getInt(i);
                    count++;
                    counts.put(i, count);
                    patternFound(context, matcher, singlePercent, overallPercent, json);
                }

                context.setStatusMessage("Finished counting pattern:" + matcher.getPattern());

            }


            context.updateLastResult(json);


        } catch (Exception e) {
            e.printStackTrace();
            throw new TamilPlatformException(e.getMessage());
        }
    }


    /**
     * Called whenever a pattern is found
     * @param context the job context
     * @param matcher the matcher that had matched a pattern
     * @param currentSearchCompletedPercent  the % of completion of the current matcher
     * @param overallSearchCompletedPercent the overall % of completion of the job
     * @param json  the json representing the work unit of the job.
     */
    protected void patternFound(JobContext<JSONObject> context, SimpleMatcher matcher, int currentSearchCompletedPercent, int overallSearchCompletedPercent, JSONObject json) {
        context.setAutoFlush(false);
        context.setPercentOfCompletion(overallSearchCompletedPercent);
        context.setStatusMessage("Counting pattern:" + matcher.getPattern() +" at " + currentSearchCompletedPercent +" %");
        context.setAutoFlush(true);
        context.updateLastResult(json);
    }


    /**
     * Called once to initialize the configuration of job context
     * @param context  the job context.
     */
    protected void config(JobContext<JSONObject> context) {
        context.setAutoFlush(true);

    }
}


