package my.interest.lang.tamil.impl.job;

import my.interest.lang.tamil.generated.types.JobResultBean;

import tamil.lang.api.job.JobResultChunk;
import tamil.lang.api.job.JobResultSnapShot;
import tamil.lang.api.job.JobStatus;
import tamil.lang.api.persist.object.ObjectSerializer;


import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class JobResultImpl<T> implements JobResultSnapShot {

    private JobResultBean bean = null;

    ObjectSerializer<T> serializer = null;



    JobResultImpl(JobResultBean bean,ObjectSerializer<T> serializer) {
        this.bean = bean;

        this.serializer = serializer;

    }

    public ObjectSerializer<T> getSerializer() {
        return  serializer;
    }


    public ObjectSerializer.SERIALIZED_TYPE getSerializedType() {
        return ObjectSerializer.SERIALIZED_TYPE.valueOf(bean.getChunkType());
    }

    public JobResultChunk<T> getLastResults(int maxChunks) throws Exception {
        int total = bean.getChunks().size();
        int index = 0;
        if (maxChunks < total) {
            index = total - maxChunks;
        }

        List<T> chunks = new ArrayList<T>();
        int lastId = 0;
        for (int i = index ; i < total; i++) {
            my.interest.lang.tamil.generated.types.JobResultChunk chunk = bean.getChunks().get(i);
            lastId = chunk.getId();
            byte[] data = chunk.getData();
            chunks.add(serializer.deserialize(data));
        }

        return new JobResultChunk<T>(bean.getId(), lastId, chunks);
    }




    public JobResultChunk<T> getNewResults(int continuousQueryId) throws Exception {


        int lastId = continuousQueryId;
        List<T> chunks = new ArrayList<T>();


        for (my.interest.lang.tamil.generated.types.JobResultChunk chunk : bean.getChunks()) {
            lastId = chunk.getId();
            if (chunk.getId() <= continuousQueryId) {
                continue;
            }
            byte[] data = chunk.getData();
            chunks.add(serializer.deserialize(data));


        }
        return new JobResultChunk<T>(bean.getId(), lastId, chunks);

    }

    public JobStatus getStatus() {
        JobStatusImpl status = new JobStatusImpl();
        status.setEndTime(bean.getFinished());
        status.setLastUpdatedTime(bean.getUpdated());
        status.setStartTime(bean.getCreated());
        status.setStatus(JobStatus.STATUS.valueOf(bean.getStatus().toString()));
        status.setStatusMessage(bean.getStatusMessage());
        status.setCompletionPercent(bean.getPercentOfCompletion());
        if (bean.getException() != null) {
            status.setTrace(bean.getException().getTrace());
            status.setExceptionMessages(bean.getException().getMessages());
        }
        return status;
    }


    public boolean isDone() {
        JobStatus status = getStatus();

        return status.getStatus() == JobStatus.STATUS.FAILED ||  status.getStatus() == JobStatus.STATUS.FINISHED ;
    }


    public String getTitleMessage() {
        return bean.getTitleMessage();
    }

    public String getTitleId() {
        return bean.getTitleId();
    }
}
