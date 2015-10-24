package tamil.lang.api.job;

import java.util.List;

/**
 * <p>
 *     Job Manager interface that can be used to submit asynchronous process.
 *     To submit a job, you need to implement {@link tamil.lang.api.job.JobRunnable} which does the background work.
 *     Every job can produce a list of unit work. Unit work is represented an instance of a specific class. The class should be object serializable.  See {@link tamil.lang.api.persist.object.ObjectSerializer}
 *
 * </p>
 *
 * @author velsubra
 * @see tamil.lang.TamilFactory#getJobManager(String)
 */
public interface JobManager {

    /**
     * Finds the snapshot of the job result created by previously submitted job.<b> Note:</b> This method only returns the snapshot. We need to call again in order to poll for the job result.
     * @param id  the job id returned by {@link #submit(JobRunnable, Class)}
     * @param resultType  the class that represents the unit result that can be produced by {@link tamil.lang.api.job.JobRunnable} that was submitted.
     * @param <T> the type of of the unit work
     * @return  the snapshot of the result.
     */
    public <T> JobResultSnapShot<T> findJobResultSnapShot(long id, Class<T> resultType);

    /**
     * Submits a new background job
     * @param runnable the runnable object implementing job
     * @param resultType  the class that represents the unit result that can be produced by the runnable that is being submitted.
     * @param <T>   the type of of the unit work
     * @return  the job id that can be used to find the job result using {@link #findJobResultSnapShot(long, Class)}
     */
    public <T> long submit(JobRunnable<T> runnable, Class<T> resultType);


    /**
     * Lists all jobs known to this job manager.
     * @return the list of job ids. Empty list when there is no job found.
     */
    public List<Long> listJobIds();
}
