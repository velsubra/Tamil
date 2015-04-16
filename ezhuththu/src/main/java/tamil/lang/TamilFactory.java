package tamil.lang;

import my.interest.lang.tamil.impl.DefaultNumberReader;
import my.interest.lang.tamil.impl.dictionary.DictionaryCollection;
import my.interest.lang.tamil.punar.handler.WordsJoinHandler;
import my.interest.lang.tamil.translit.EnglishToTamilCharacterLookUpContext;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.api.join.WordsJoiner;
import tamil.lang.api.number.NumberReader;
import tamil.lang.api.parser.CompoundWordParser;
import tamil.lang.api.trans.Transliterator;
import tamil.lang.exception.service.ServiceException;
import tamil.lang.api.persist.manager.PersistenceManager;
import tamil.lang.spi.CompoundWordParserProvider;
import tamil.lang.spi.PersistenceManagerProvider;
import tamil.lang.spi.TamilDictionaryProvider;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * <p>
 * The platform class that can provide entry point for different services.
 * If you use as API, Please make sure you call {@link TamilFactory#init()}  before using any API.
 * </p>
 *
 * @author velsubra
 * @see TamilWord#from(String)
 */
public final class TamilFactory {

    /**
     * This has to be init.
     */
    public static void init() {
        TamilCharacterLookUpContext.lookup(0);
    }


    private static TamilDictionary systemDictionary = null;
    private static CompoundWordParser systemParser = null;
    private static PersistenceManager persistenceManager = null;

    private TamilFactory() {

    }

    static {
        try {
            ServiceLoader loader = ServiceLoader.load(TamilDictionaryProvider.class);
            loader.reload();
            systemDictionary = new DictionaryCollection(loader.iterator());


            loader = ServiceLoader.load(CompoundWordParserProvider.class);
            loader.reload();
            Iterator<CompoundWordParserProvider> it = loader.iterator();
            if (it.hasNext()) {
                systemParser = it.next().crate();
            }

            loader = ServiceLoader.load(PersistenceManagerProvider.class);
            loader.reload();
            Iterator<PersistenceManagerProvider> its = loader.iterator();
            if (its.hasNext()) {
                persistenceManager = its.next().create();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }


    /**
     * Gets the system dictionary that can be used to look for known tamil words.
     *
     * @return the dictionary object.
     * @throws tamil.lang.exception.service.ServiceException when there is an issue while getting this service.
     */
    public static TamilDictionary getSystemDictionary() throws ServiceException {

        return systemDictionary;
    }

    /**
     * Gets  the Transliterator to tamil
     *
     * @param fromLanguage the source language. Currently it is ignored. English is the only supported language!
     * @return the Transliterator
     * @throws tamil.lang.exception.service.ServiceException when there is an issue while getting this service.
     */
    public static Transliterator getTransliterator(String fromLanguage) throws ServiceException {
        return EnglishToTamilCharacterLookUpContext.TRANSLIST;
    }

    /**
     * Gets the WordsJoiner that can be used to appendNodesToAllPaths multiple words using simple புணர்ச்சி laws.
     *
     * @param nilaiMozhi the initial word. (நிலைமொழி )
     * @return the WordsJoiner
     * @throws tamil.lang.exception.service.ServiceException when there is an issue while creating this service.
     */
    public static WordsJoiner createWordJoiner(TamilWord nilaiMozhi) throws ServiceException {
        WordsJoinHandler handler = new WordsJoinHandler();
        handler.add(nilaiMozhi);
        return handler;
    }


    /**
     * Gets the number reader interface.
     *
     * @return the instance of a number reader.
     * @throws tamil.lang.exception.service.ServiceException when there is an issue while getting this service.
     */
    public static NumberReader getNumberReader() throws ServiceException {
        return DefaultNumberReader.reader;
    }


    public static CompoundWordParser getCompoundWordParser() throws ServiceException {
        if (systemParser == null) {
            throw new ServiceException("Unimplemented!");
        } else {
            return systemParser;
        }
    }

    public static PersistenceManager getPersistenceManager() throws ServiceException {
        if (persistenceManager == null) {
            throw new ServiceException("Unimplemented!");
        } else {
            return persistenceManager;
        }
    }

}
