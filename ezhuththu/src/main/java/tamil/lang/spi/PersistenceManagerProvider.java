package tamil.lang.spi;

import tamil.lang.api.persist.manager.PersistenceManager;

/**
 * <p>
 *     Provides the persistence manager.   It can be loaded through ServiceLoader interface.
 * </p>
 *
 * @author velsubra
 */
public interface  PersistenceManagerProvider {

    /**
     *  Creates the persistence manager.
     * @return the persistence manager
     */
    public PersistenceManager create();
}
