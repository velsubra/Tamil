package tamil.lang.api.job;

/**
 * <p>
 *     Represents a snapshot of job result object.
 * </p>
 *
 * @author velsubra
 */
public interface JobResultSnapShot<T> {

    /**
     * Returns new chunk of work for the given  continuousQueryId.
     * @param continuousQueryId  the continuous id that could be 0 or that  which  was earlier  returned by {@link JobResultChunk#getLatestContinuousQueryId()}
     * @return  the result chunk.
     * @throws Exception
     */
    public JobResultChunk<T> getNewResults(int continuousQueryId) throws Exception;

    /**
     * Returns the status on the job.
     * @return  the job status.
     */
    public JobStatus getStatus();




}
