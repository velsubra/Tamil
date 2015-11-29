package tamil.util.regex.impl;

import org.json.JSONObject;
import tamil.lang.TamilFactory;
import tamil.lang.api.dictionary.DictionarySearchCallback;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.api.job.JobContext;
import tamil.lang.api.job.JobRunnable;
import tamil.lang.exception.TamilPlatformException;
import tamil.lang.known.IKnownWord;
import tamil.util.regex.TamilPattern;

import java.util.regex.Matcher;

/**
 * Created by velsubra on 11/28/15.
 */
public class DictionaryRegExFinderJob implements JobRunnable<JSONObject>, DictionarySearchCallback {
    TamilDictionary dictionary = null;
    TamilPattern pattern = null;
    int maxResults = 500;
    Class<? extends IKnownWord> type = null;

    JobContext<JSONObject> context = null;

    public DictionaryRegExFinderJob(String regx) {
        this(regx, null);
    }

    public DictionaryRegExFinderJob(String regx, Class<? extends IKnownWord> type) {
        this(TamilFactory.getSystemDictionary(), regx, type);
    }

    public DictionaryRegExFinderJob(TamilDictionary dictionary, String regx, Class<? extends IKnownWord> type) {
        this(dictionary, TamilPattern.compile(regx), type, 500);
    }

    public DictionaryRegExFinderJob(TamilDictionary dictionary, String regx) {
        this(dictionary, TamilPattern.compile(regx), null, 500);
    }

    /**
     * @param dictionary the dictionary to be searched
     * @param pattern    the pattern to be searched for
     * @param type       the type of the word to be returned
     * @param max        the max number of word to be returned.  -ve value means search must be done against the full dictionary.
     */
    public DictionaryRegExFinderJob(TamilDictionary dictionary, TamilPattern pattern, Class<? extends IKnownWord> type, int max) {
        this.dictionary = dictionary;
        this.pattern = pattern;
        this.maxResults = max;
        this.type = type;
        if (this.dictionary == null) {
            this.dictionary = TamilFactory.getSystemDictionary();
        }
    }

    protected void config(JobContext<JSONObject> context) {
        context.setAutoFlush(true);

    }

    public void run(JobContext<JSONObject> context) {
        context.setTitleMessage("Searching Dictionary for:" + pattern.getTamilPattern() + (type == null ? "" : " with type:" + type.getSimpleName()));
        if (maxResults == 0) {
            return;
        }
        config(context);
        context.setPercentOfCompletion(0);
        this.context = context;
        context.setStatusMessage("Started searching ....");
        dictionary.search(this, pattern);
    }

    public boolean matchFound(int index, IKnownWord word, Matcher matcher) {
        try {
            if (type != null) {
                if (!type.isAssignableFrom(word.getClass())) {
                    return true;
                }
            }
            maxResults--;
            String w = word.getWord().toString();

            JSONObject match = new JSONObject();
            //String matchText = w.substring(matcher.start(), matcher.end());
            match.put(AbstractSimpleMatcherBasedJob.PROP_MATCH_TEXT, w);
            match.put(AbstractSimpleMatcherBasedJob.PROP_POST_MATCH_TEXT, "\n");
            context.addResult(match);
            context.setStatusMessage("Found#:" + context.getUnitResultCount() + " Last word:" + w);
            context.setPercentOfCompletion((int) (1.0* index / dictionary.size() * 100.0));
            return (maxResults != 0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new TamilPlatformException(e.getMessage());
        }
    }
}
