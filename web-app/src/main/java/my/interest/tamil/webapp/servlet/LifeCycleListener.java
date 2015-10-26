package my.interest.tamil.webapp.servlet;


import my.interest.lang.tamil.impl.job.ExecuteManager;
import tamil.lang.TamilCharacterLookUpContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class LifeCycleListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //To change body of implemented methods use File | Settings | File Templates.
        TamilCharacterLookUpContext.lookup(0);
        ExecuteManager.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
       // System.out.println("ExecuteManager.stop(); ........");
        ExecuteManager.stop();
    }
}
