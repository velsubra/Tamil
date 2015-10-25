package my.interest.lang.tamil.impl.job;

import tamil.lang.api.job.JobRunnable;
import tamil.lang.api.persist.object.ObjectPersistenceInterface;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class InnerJobRunnable implements Runnable {

    JobRunnable runnable;
    JobContextImpl contextImpl;


    public InnerJobRunnable(JobRunnable runnable, JobContextImpl contextImpl) {
        this.runnable = runnable;
        this.contextImpl = contextImpl;

    }

    public void run() {

        try {
            contextImpl.setRunning();
            runnable.run(contextImpl);
            contextImpl.setPercentOfCompletion(100);
            contextImpl.setCompleted();
        } catch (Throwable t) {
            contextImpl.setFailed(t);
        }
    }
}
