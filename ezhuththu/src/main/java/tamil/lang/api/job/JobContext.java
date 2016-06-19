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
     * Gets the total number of unit work added
     * @return the number of unit of work performed.
     */
    public int getUnitResultCount();

    /**
     * Returns the current job id
     * @return the job id;
     */
    public long getJobId();

    /**
     * Sets the title id for this job. This can be typically used to identify if two jobs are doing the same work.
     * This mostly an internal field.
     * @param titleId  to be treated as hash code for this job.
     */
    public void setTitleId(String titleId);


    /**
     * Sets the title message for this job.
     *
     * @param titleMessage
     */
    public void setTitleMessage(String titleMessage);


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
     *
     * @see #setAutoFlush(boolean)
     */
    public void flush();

    /**
     * Sets auto flash.
     * @param flush  when true every update is visible to the client. The default value is false.
     * @ee #flush
     */
    public void setAutoFlush(boolean flush);

    /**
     * Sets a server property
     * @param name name of the property
     * @param value the value of the property
     */
    public void setServerProperty(String name, String value);

    /**
     * Sets a client property
     * @param name name of the property
     * @param value the value of the property
     */
    public void setClientProperty(String name, String value);

    /**
     * Gets the job category
     * @return job category
     */
    public String getJobCategory();

}
