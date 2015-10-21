package tamil.lang.api.persist.object;

import tamil.lang.exception.TamilPlatformException;

import java.util.List;

/**
 * <p>
 * Simple interface to persist object that be shared among multiple requests.
 * </p>
 *
 * @author velsubra
 */
public interface ObjectPersistenceInterface {

    /**
     * Persists the object represented as byte[]
     *
     * @param category the category of the object. E.g) jobs/data/
     * @param data     the byte array of the object
     * @return the id created. The scope of the id is with in  the category only.
     * @throws TamilPlatformException
     */
    public long create(String category, byte[] data) throws TamilPlatformException;

    /**
     * Updates the previously created object
     *
     * @param id       the id of the object
     * @param category the category of the object
     * @param data     new byte array representing the object
     * @throws TamilPlatformException
     */
    public void update(long id, String category, byte[] data) throws TamilPlatformException;

    /**
     * Gets the object data
     *
     * @param id    the id of the object
     * @param category the category of the object
     * @return
     * @throws TamilPlatformException
     */
    public byte[] get(long id, String category) throws TamilPlatformException;

    /**
     * Deletes the object that was persisted earlier
     * @param id
     * @param category
     * @throws TamilPlatformException
     */
    public void delete(long id, String category) throws TamilPlatformException;

    public boolean exists(long id, String category) throws TamilPlatformException;

    public List<Long> list(String category) throws TamilPlatformException;
}
