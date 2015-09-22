package my.interest.lang.tamil.impl;

import tamil.lang.api.persist.manager.PersistenceManager;
import tamil.lang.spi.PersistenceManagerProvider;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class FileBasedPersistenceManagerProvider implements PersistenceManagerProvider {
    /**
     * Creates the persistence manager.
     *
     * @return the persistence manager
     */
    @Override
    public PersistenceManager create() {
        return FileBasedPersistence.ME_SINGLETON;
    }
}
