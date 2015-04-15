package tamil.lang.api.persist.manager;

/**
 * <p>
 *     The manager for storing all persisted configuration.
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
