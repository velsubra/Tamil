package my.interest.lang.tamil.impl.job;

import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.generated.types.JobException;
import my.interest.lang.tamil.generated.types.JobResultBean;
import my.interest.lang.tamil.generated.types.JobResultChunk;
import my.interest.lang.tamil.generated.types.JobStatus;
import tamil.lang.api.job.JobContext;
import tamil.lang.api.persist.object.ObjectPersistenceInterface;
import tamil.lang.api.persist.object.ObjectSerializer;
import tamil.lang.exception.service.ServiceException;

import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class JobContextImpl<T> implements JobContext<T> {
    JobResultBean bean = null;
    ObjectPersistenceInterface persist = null;
    ObjectSerializer<T> serializer = null;
    boolean autoFlush = false;

    public JobContextImpl(JobResultBean bean, ObjectPersistenceInterface persist, ObjectSerializer<T> serializer) {
        this.bean = bean;
        this.persist = persist;
        this.serializer = serializer;
    }

    public long getJobId() {
        return bean.getId();
    }


    public void setTitleId(String titleiId) {
         bean.setTitleId(titleiId);
        if (autoFlush) flush();
    }


    public void setTitleMessage(String titleMessage) {
      bean.setTitleMessage(titleMessage);
        if (autoFlush) flush();
    }


    public void addResult(T result) {
        try {
            bean.setUpdated(new Date());
            JobResultChunk chunk = new JobResultChunk();

            chunk.setData(serializer.serialize(result));
            chunk.setWhen(new Date());
            if (bean.getChunks().isEmpty()) {
                chunk.setId(1);
            } else {
                int lastID = bean.getChunks().get(bean.getChunks().size() - 1).getId();
                chunk.setId(1 + lastID);
            }
            bean.getChunks().add(chunk);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }  finally {
            if (autoFlush) flush();
        }
    }

    public void updateLastResult(T result) {
        try {
            bean.setUpdated(new Date());
            if (bean.getChunks().isEmpty()) {
                addResult(result);
            } else {
                JobResultChunk chunk = bean.getChunks().get(bean.getChunks().size() - 1);
                chunk.setId(1 + chunk.getId());
                chunk.setData(serializer.serialize(result));
                chunk.setWhen(new Date());

            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        } finally {
            if (autoFlush) flush();
        }
    }

    public void resetResults() {
        bean.setUpdated(new Date());
        bean.getChunks().clear();
        if (autoFlush) flush();
    }

    public void setStatusMessage(String message) {
        bean.setUpdated(new Date());
        bean.setStatusMessage(message);
        if (autoFlush) flush();

    }

    public void setPercentOfCompletion(int percent) {
        if (percent >100) {
            percent = percent % 100;
        }
        bean.setPercentOfCompletion(percent);
        bean.setUpdated(new Date());
        if (autoFlush) flush();

    }

    public void setRunning() {
        bean.setUpdated(new Date());
        bean.setStatus(JobStatus.RUNNING);
        if (autoFlush) flush();
    }

    public void setCompleted() {
        bean.setStatus(JobStatus.FINISHED);
        bean.setFinished(new Date());
        flush();
    }

    public void setFailed(Throwable t) {
        bean.setStatus(JobStatus.FAILED);
        bean.setFinished(new Date());
        bean.setException(new JobException());
        try {
            bean.getException().setTrace(new String(TamilUtils.fromThrowable(t), TamilUtils.ENCODING));
            bean.getException().getMessages().addAll(Arrays.asList(TamilUtils.getAllInnerErrorMessages(t)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        flush();
    }

    public void flush() {
        persist.update(bean.getId(), bean.getCategoryName(), TamilUtils.toXMLJAXBData(bean));
      //  System.out.println(bean.getPercentOfCompletion());
    }

    public void setAutoFlush(boolean flush) {
       autoFlush = flush;
    }
}
