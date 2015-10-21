package tamil.lang.api.job;

/**
 * <p>
 *     The context that is passed to {@link tamil.lang.api.job.JobRunnable}.
 * </p>
 *
 * @author velsubra
 */
public interface JobContext<T> {

    /**
     * Returns the current job id
     * @return the job id;
     */
    public long getJobId();


    /**
     * Adds a unit of work
     * @param unitWork the unit work
     */
    public void addResult(T unitWork);

    /**
     * Updates the last unit of work if added. If there is none found. the result is just added
     * @param unitWork  the unit work
     */
    public void updateLastResult(T unitWork);

    /**
     * Removes all the units of work added.
     */

    public void resetResults();

    /**
     * Sets a status message describing the current unit of work being done or finished.
     * @param message
     */
    public void setStatusMessage(String message);

    /**
     * Sets the % of work completed. If the % of work is not known, this does not need to updated.
     * @param percent the percent of work done. negative values indicate that this can not be estimated.
     */
    public void setPercentOfCompletion(int percent);

    /**
     * Updates are made available to other threads that may be polling for job results.
     * @see tamil.lang.api.job.JobManager#findJobResultSnapShot(long, Class)
     */
    public void flush();

}
