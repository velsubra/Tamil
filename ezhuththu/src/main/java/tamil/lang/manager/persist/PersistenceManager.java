package tamil.lang.manager.persist;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface PersistenceManager {
    public ApplicationManager getApplicationManager();
    public RootVerbManager getRootVerbManager();
    public PrepositionManager getPrepositionManager();
    public NounManager getNounManager();
}
