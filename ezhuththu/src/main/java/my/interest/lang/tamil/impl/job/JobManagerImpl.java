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

import javax.xml.bind.JAXBContext;
import java.io.ByteArrayInputStream;
import java.util.Date;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class JobManagerImpl implements JobManager {

    static ObjectPersistenceInterface persist = new FileBasedPersistenceImpl("xml");
    static ObjectSerializerManager manager = new ObjectSerializerManagerImpl();
    private static final String JOB_PATH = "jobs/id";

    public <T> JobResultSnapShot<T> findJobResultSnapShot(long id, Class<T> resultType) {
        try {
            byte[] data = persist.get(id, JOB_PATH);
            JAXBContext jaxbContext = JAXBContext.newInstance(JobResultBean.class);
            JobResultBean job = (JobResultBean) TamilUtils.deSerialize(jaxbContext, JobResultBean.class.getClassLoader(), new ByteArrayInputStream(data));
            ObjectSerializer<T> serializer = manager.findSerializer(resultType);
            if (serializer == null) {
                throw new ServiceException("Serializer not found for:" + resultType);
            }
            return new JobResultImpl<T>(job, serializer);
        } catch (Exception se) {

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
            long id = persist.create(JOB_PATH, TamilUtils.toXMLJAXBData(bean));
            bean.setId(id);
            bean.setStatus(JobStatus.SUBMITTED);
            bean.setCreated(new Date());
            bean.setUpdated(bean.getCreated());
            bean.setName(JOB_PATH);
            bean.setPercentOfCompletion(-1);
            persist.update(id, JOB_PATH, TamilUtils.toXMLJAXBData(bean));
            JobContextImpl<T> context = new JobContextImpl<T>(bean, persist, serializer);
            InnerJobRunnable innerJobRunnable = new InnerJobRunnable(runnable, context);
            ExecuteManager.fire(innerJobRunnable);
            return id;
        } catch (Exception se) {
            throw new ServiceException("Unable to submit a job:" + se.getMessage());
        }


    }
}
