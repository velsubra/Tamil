package tamil.lang.api.job;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *    Represents Chunk of fresh work that has been finished from last poll.   Please see {@link tamil.lang.api.job.JobResultSnapShot#getNewResults(int)}
 * </p>
 *
 * @author velsubra
 */
public final class JobResultChunk<T> implements Serializable {

    public JobResultChunk(long jobid, int lastQueryId, List<T> chunk) {
        this.jobid = jobid;
        this.latestContinuousQueryId = lastQueryId;
        this.chunk = chunk;
    }

    List<T> chunk;
    long jobid;
    int latestContinuousQueryId;

    /**
     * Returns the job id associated with the result
     * @return the job id
     */
    public long getJobid() {
        return jobid;
    }

    /**
     * The latest id that needs to be presented to   {@link tamil.lang.api.job.JobResultSnapShot#getNewResults(int)} to get the next chunk of results.
     * Please note that {@link tamil.lang.api.job.JobResultSnapShot} needs to be got again before trying to get the fresh finished work
     * @return  the latest continuous id.
     */
    public int getLatestContinuousQueryId() {
        return latestContinuousQueryId;
    }

    /**
     * The new chunk of work that is done.
     * @return the list of work units. It can be empty if no fresh unit of work has been completed.
     */
    public List<T> getChunk() {
        return chunk;
    }


}
