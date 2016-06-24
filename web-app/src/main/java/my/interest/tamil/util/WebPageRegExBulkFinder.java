package my.interest.tamil.util;

import my.interest.lang.tamil.TamilUtils;
import org.json.JSONObject;
import tamil.lang.TamilFactory;
import tamil.lang.api.job.JobContext;
import tamil.lang.api.job.JobManager;
import tamil.lang.api.job.JobResultSnapShot;
import tamil.lang.api.job.JobRunnable;
import tamil.util.IPropertyFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by velsubra on 6/19/16.
 */
public class WebPageRegExBulkFinder implements JobRunnable<JSONObject> {
    WebPageRegExFinder finder = null;
    int depth;
    String childRxURL = null;
    List<String> fullprocessed = new ArrayList<String>();
    String dataUrl = null;

    public WebPageRegExBulkFinder(String dataUrl, String submiturl, String viewurl, String scripturl, String cssurl, String pattern, IPropertyFinder aliasFinder, String baseFeatures, String alternativeFeature, int depth, String recursive_pattern) {
        finder = new WebPageRegExFinder(dataUrl, submiturl, viewurl, scripturl, cssurl, pattern, aliasFinder, baseFeatures, alternativeFeature);
        this.depth = depth;
        this.childRxURL = recursive_pattern;
        this.dataUrl = dataUrl;

    }

    public void run(WebPageRegExFinder finder, JobContext<JSONObject> context) throws Exception {
        fullprocessed.add(java.net.URLDecoder.decode(this.dataUrl, TamilUtils.ENCODING));
        JobManager manager = TamilFactory.getJobManager("jobs/search/rx/webpage");
        long id = manager.submit(finder, JSONObject.class);
        context.setStatusMessage("Waiting on job:" + id);
        JobResultSnapShot<JSONObject> snapShot = manager.findJobResultSnapShot(id, JSONObject.class);
        while (!snapShot.isDone()) {
            Thread.currentThread().sleep(5000);
            snapShot = manager.findJobResultSnapShot(id, JSONObject.class);
        }

        List<String> processed = finder.getProcessedDecodedUrl();
        for (String p : processed) {
            if (!fullprocessed.contains(p)) {

            }
        }
    }

    public void run(JobContext<JSONObject> context) throws Exception {

        run(this.finder, context);
    }
}
