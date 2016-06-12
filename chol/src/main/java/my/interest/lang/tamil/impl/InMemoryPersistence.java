package my.interest.lang.tamil.impl;

import my.interest.lang.tamil.generated.types.AppDescription;
import my.interest.lang.tamil.generated.types.GlobalTypes;
import my.interest.lang.tamil.internal.api.PersistenceInterface;

import my.interest.lang.tamil.generated.types.TamilRootWords;
import tamil.lang.exception.service.ServiceException;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class InMemoryPersistence extends PersistenceInterface {

    TamilRootWords rootwords = null;
    @Override
    public TamilRootWords getAllRootWords() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void persist(TamilRootWords verbs) {
        //To change body of implemented methods use File | Settings | File Templates.
        this.rootwords = verbs;
    }

    @Override
    public void lock() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void unlock() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public GlobalTypes getNounGlobalTypes() {
        return rootwords.getPeyar().getGlobalTypes();
    }

    public AppDescription findAppByName(String name) {
        return null;
    }

    public AppDescription createAppByName(String name) throws ServiceException {
        return null;
    }

    public void deleteApplicationByName(String name) {

    }

    public void updateApplication(AppDescription app) {

    }
}
