package tamil.lang.api.job;

import tamil.lang.api.persist.object.ObjectSerializer;

/**
 * <p>
 *     Represents a snapshot of job result object.
 * </p>
 *
 * @author velsubra
 */
public interface JobResultSnapShot<T> {

    /**
     * Returns the total number of units done in this snapshot.
     * @return total number of units
     */
    public int getTotalUnitsDone();

    /**
     * Returns new chunk of work for the given  continuousQueryId.
     * @param continuousQueryId  the continuous id that could be 0 or that  which  was earlier  returned by {@link JobResultChunk#getLatestContinuousQueryId()}
     * @return  the result chunk.
     * @throws Exception
     */
    public JobResultChunk<T> getNewResults(int continuousQueryId) throws Exception;


    /**
     * Gets the last list of unit work
     * @param maxChunks  max number of last list of unit work
     * @return  the result chunk
     * @throws Exception
     */
    public JobResultChunk<T> getLastResults(int maxChunks) throws Exception;

    /**
     * Returns the status on the job.
     * @return  the job status.
     */
    public JobStatus getStatus();

    /**
     * Gets the object serializer associated with this instance
     * @return  the object serializer
     */
    public ObjectSerializer<T> getSerializer();

    /**
     * Gets the type of the serialization of unit work.
     * @return the original type
     */
    public ObjectSerializer.SERIALIZED_TYPE getSerializedType();


    /**
     * says if the job is done
     * @return true if the job is done else false;
     */
    public boolean isDone();

    /**
     * Returns the title message of this job
     * @return the title string. It could be null.
     */
    public String getTitleMessage();

    /**
     * Returns the title id of this job
     * @return the title string. It could be null.
     */
    public String getTitleId();




}
