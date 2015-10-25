package tamil.lang.api.job;

/**
 * <p>
 * The runnable object that can be submitted for execution.    User would implement this interface.
 * </p>
 *
 * @author velsubra
 * @see tamil.lang.api.job.JobManager
 */
public interface  JobRunnable<T> {

    /**
     * The run method to be implemented by the user
     *
     * @param context the job context passed. User can update the job results through this.
     *                <b>Note:</b> {@link JobContext#flush()} must be called for the result to be available to the other thread that polls for the results.
     */
    public abstract void run(JobContext<T> context);

}
