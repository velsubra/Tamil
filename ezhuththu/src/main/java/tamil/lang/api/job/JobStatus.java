package tamil.lang.api.job;


import java.util.Date;
import java.util.List;

/**
 * <p>
 * The object representing the job status
 * </p>
 *
 * @author velsubra
 */
public interface JobStatus {

    public static enum STATUS {
        SUBMITTED, RUNNING, PAUSED, FAILED,  FINISHED
    }

    /**
     * returns true if the job is running or submitted.
     * @return
     */
    public boolean shouldWait();

    /**
     * Returns the status object
     *
     * @return the status
     */
    public STATUS getStatus();

    /**
     * The last status message that was set
     *
     * @return the status message
     */
    public String getStatusMessage();

    /**
     * Gets the start time
     *
     * @return date representing the time the job was started
     */
    public Date getStartTime();

    /**
     * Gets the end time of the job
     *
     * @return end time, null if the job has not finished or failed.
     */
    public Date getEndTime();

    /**
     * Gets the last updated time
     *
     * @return the last updated time
     */
    public Date getLastUpdatedTime();

    /**
     * The % of completion.
     *
     * @return a negative number if the completion can not be deterministically computed. Otherwise, the percent of completion is returned.
     */
    public int getCompletionPercent();

    /**
     * Gets the exception trace. Applicable if there was an un-handled exception from {@link tamil.lang.api.job.JobRunnable#run(JobContext)}
     *
     * @return the trace.
     */
    public String getExceptionTrace();

    /**
     * Gets the exception messages. Applicable if there was an un-handled exception from {@link tamil.lang.api.job.JobRunnable#run(JobContext)}
     *
     * @return the trace.
     */
    public List<String> getExceptionMessages();
}
