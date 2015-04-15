package tamil.lang.api.persist.matcher;

import tamil.lang.api.persist.manager.PersistenceManager;

/**
 * <p>
 *     A generic matcher interface that can be used to find a persisted description.
 * </p>
 *
 * @author velsubra
 */
public  interface DescriptionMatcher<T> {

    public boolean matches(T pattern, T root, PersistenceManager per);
}
