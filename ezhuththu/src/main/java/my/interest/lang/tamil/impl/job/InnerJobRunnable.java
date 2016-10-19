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
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            System.out.println("Used Memory MB:"
                    + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000);

            contextImpl.setRunning();
            runnable.run(contextImpl);
            contextImpl.setPercentOfCompletion(100);
            contextImpl.setCompleted();
        } catch (Throwable t) {
            t.printStackTrace();
            contextImpl.setFailed(t);
        }
    }
}
