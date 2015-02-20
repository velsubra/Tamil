package tamil.lang.spi;

import tamil.lang.api.dictionary.TamilDictionary;

/**
 * <p>
 *     The dictionary provider interface that allows to provided external dictionary integration.
 *     This interface loaded using {@link java.util.ServiceLoader} interface.
 * </p>
 *
 * @author velsubra
 */
public interface TamilDictionaryProvider {


    /**
     * Callback to the dictionary provider.
     * @return the TamilDictionary object.
     */
    public TamilDictionary create();
}
