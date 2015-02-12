package my.interest.lang.tamil.multi;

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

    private static final ExecuteManager manager = new ExecuteManager(10, TOTAL_JOBS_ALLOWED, 0, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(TOTAL_JOBS_ALLOWED * 100));

    public ExecuteManager(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public static void stop() {
        manager.shutdownNow();

    }

    public synchronized static void fire(Runnable run) {
        while (manager.getQueue().size() == TOTAL_JOBS_ALLOWED) {
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
