package my.interest.lang.tamil.internal.api;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public  interface DescriptionMatcher<T> {

    public boolean matches(T pattern, T root, PersistenceInterface per);
}
