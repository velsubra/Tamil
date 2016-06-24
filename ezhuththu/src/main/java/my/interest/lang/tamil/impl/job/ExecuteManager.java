package my.interest.lang.tamil.impl.job;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class ExecuteManager extends ThreadPoolExecutor {

    private static final int TOTAL_JOBS_ALLOWED = 10;
    private static final int QCAPACITY = TOTAL_JOBS_ALLOWED * 1000;

    private static  ExecuteManager manager = new ExecuteManager(TOTAL_JOBS_ALLOWED /2 , TOTAL_JOBS_ALLOWED, 0, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(QCAPACITY));

    private ExecuteManager(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }
    public static void start() {
        if (manager.isShutdown()) {
            manager = new ExecuteManager(TOTAL_JOBS_ALLOWED /2 , TOTAL_JOBS_ALLOWED, 0, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(QCAPACITY));

        }
    }

    public static void stop() {
        manager.shutdownNow();

    }

    public synchronized static void fire(Runnable run) {
        while (manager.getQueue().size() == QCAPACITY -1 ) {
            try {
                Thread.currentThread().sleep(5);
                //System.out.println(PersistenceInterface.totalWordsSize());
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        manager.submit(run);
    }
}
