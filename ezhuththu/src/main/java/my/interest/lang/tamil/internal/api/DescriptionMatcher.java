package my.interest.lang.tamil.internal.api;

import tamil.lang.manager.persist.PersistenceManager;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public  interface DescriptionMatcher<T> {

    public boolean matches(T pattern, T root, PersistenceManager per);
}
