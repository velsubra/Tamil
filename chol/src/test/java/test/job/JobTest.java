package test.job;

import my.interest.lang.tamil.generated.types.RootVerbDescription;
import my.interest.lang.tamil.impl.job.ExecuteManager;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.api.job.JobContext;
import tamil.lang.api.job.JobManager;
import tamil.lang.api.job.JobResultSnapShot;
import tamil.lang.api.job.JobRunnable;
import tamil.util.regex.TamilPattern;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class JobTest {

    static {
        TamilFactory.init();
        System.setProperty("http.proxyHost", "www-proxy.us.oracle.com");
        System.setProperty("http.proxyPort", "80");
    }


    @Test
    public void testSearch() throws Exception {
        try {

            JobManager manager = TamilFactory.getJobManager("jobs/custom/category");
            long id = manager.submit(new VerbSearcher(), Long.class);
            JobResultSnapShot<Long> resultSnapShot = manager.findJobResultSnapShot(id, Long.class);

            while (true) {
                if (resultSnapShot == null) {
                    throw new Exception("Not found");
                }
                System.out.println(resultSnapShot.getStatus().getCompletionPercent() +" %");

                if (resultSnapShot.isDone()) break;
                Thread.currentThread().sleep(100);
                resultSnapShot = manager.findJobResultSnapShot(id, Long.class);

            }
        } finally {
            ExecuteManager.stop();
        }
    }

    static class VerbSearcher extends JobRunnable<Long> {


        @Override
        public void run(JobContext<Long> context) {
            context.setTitleMessage("Finding verbs");
            context.setTitleId("Non Vallinam starting verbs");
            List<RootVerbDescription> list = PersistenceInterface.get().getAllRootWords().getVinai().getVerbs().getList().getVerb();
            TamilPattern pattern = TamilFactory.getRegEXCompiler().compile("${!vali}${ezhuththu}*");
            context.setPercentOfCompletion(0);
            int count = 0;
            long matchingCount = 0;
            context.setStatusMessage("Starting the search");
            for (RootVerbDescription root : list) {
                count++;
                double percent = count * 100.0 / list.size();
                context.setPercentOfCompletion((int) percent);
                if (pattern.matcher(root.getRoot()).matches()) {
                   // context.addResult(root.getRoot());
                    matchingCount ++;
                    context.updateLastResult(matchingCount);
                }

                context.flush();

            }
            context.setStatusMessage("Ended successfully!");

        }
    }
}
