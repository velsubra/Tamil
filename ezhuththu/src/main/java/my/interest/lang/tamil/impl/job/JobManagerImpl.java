package my.interest.lang.tamil.impl.job;

import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.generated.types.JobResultBean;
import my.interest.lang.tamil.generated.types.JobStatus;
import my.interest.lang.tamil.impl.persist.FileBasedPersistenceImpl;
import my.interest.lang.tamil.impl.persist.ObjectSerializerManagerImpl;
import tamil.lang.api.job.JobManager;
import tamil.lang.api.job.JobResultSnapShot;
import tamil.lang.api.job.JobRunnable;
import tamil.lang.api.persist.object.ObjectPersistenceInterface;
import tamil.lang.api.persist.object.ObjectSerializer;
import tamil.lang.api.persist.object.ObjectSerializerManager;
import tamil.lang.exception.service.ServiceException;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class JobManagerImpl implements JobManager {

    static ObjectPersistenceInterface persist = new FileBasedPersistenceImpl("xml");
    static ObjectSerializerManager manager = new ObjectSerializerManagerImpl();
    private static final String JOB_PATH_DEF = "jobs/id";
    String categoryName = null;

    public JobManagerImpl(String category) {
        if (category == null) {
            category = JOB_PATH_DEF;
        }
        this.categoryName = category;
    }

    public <T> JobResultSnapShot<T> findJobResultSnapShot(long id, Class<T> resultType) {
        byte[] data = null;
        try {
            data = persist.get(id, categoryName);


            JobResultBean job = TamilUtils.deserializeJAXB(new ByteArrayInputStream(data), JobResultBean.class);
            ObjectSerializer<T> serializer = manager.findSerializer(resultType);
            if (serializer == null) {
                throw new ServiceException("Serializer not found for:" + resultType);
            }
            return new JobResultImpl<T>(job, serializer);
        } catch (Exception se) {
            se.printStackTrace();
            if (data != null) {

            }
            return null;
        }

    }


    public <T> long submit(JobRunnable<T> runnable, Class<T> resultType) {

        try {
            ObjectSerializer<T> serializer = manager.findSerializer(resultType);
            if (serializer == null) {
                throw new ServiceException("Serializer not found for:" + resultType);
            }
            JobResultBean bean = new JobResultBean();
            long id = persist.create(categoryName, TamilUtils.toXMLJAXBData(bean));
            bean.setId(id);
            bean.setChunkType(serializer.getSerializedType().toString());
            bean.setStatus(JobStatus.SUBMITTED);
            bean.setCreated(new Date());
            bean.setUpdated(bean.getCreated());
            bean.setCategoryName(categoryName);
            bean.setPercentOfCompletion(-1);
            persist.update(id, categoryName, TamilUtils.toXMLJAXBData(bean));
            JobContextImpl<T> context = new JobContextImpl<T>(bean, persist, serializer);
            InnerJobRunnable innerJobRunnable = new InnerJobRunnable(runnable, context);
            ExecuteManager.fire(innerJobRunnable);
            return id;
        } catch (Exception se) {
            throw new ServiceException("Unable to submit a job:" + se.getMessage());
        }


    }


    public List<Long> listJobIds(int limit) {
        if (limit < 0) {
            return persist.list(categoryName);
        } else {
            List<Long> list = persist.list(categoryName);
            if (list.size() <= limit) {
                return list;
            } else {
                List<Long> ret = new ArrayList<Long>();
                Collections.sort(list);
                for( int i = list.size() - limit ;i < list.size(); i++) {
                    ret.add(list.get(i));
                }
                return ret;
            }
        }
    }
}
