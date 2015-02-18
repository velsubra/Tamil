package tamil.lang;

import my.interest.lang.tamil.impl.DefaultNumberReader;

import my.interest.lang.tamil.impl.dictionary.DictionaryCollection;
import my.interest.lang.tamil.punar.handler.WordsJoinHandler;
import my.interest.lang.tamil.translit.EnglishToTamilCharacterLookUpContext;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.api.join.WordsJoiner;
import tamil.lang.api.number.NumberReader;
import tamil.lang.api.trans.Transliterator;
import tamil.lang.exception.service.ServiceException;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * <p>
 *    The platform class that can provide entry point for different services.
 * </p>
 *
 * @see TamilWord#from(String)
 *
 * @author velsubra
 */
public final class TamilFactory {

    /**
     * This has to be init.
     */

    private static TamilDictionary systemDictionary = null;
    public static void init() {
        TamilCharacterLookUpContext.lookup(0);
    }

    private TamilFactory() {

    }
    static {
        ServiceLoader loader =  ServiceLoader.load(TamilDictionary.class);

        loader.reload();
        systemDictionary = new DictionaryCollection(loader.iterator());
    }


    /**
     * Gets the system dictionary that can be used to look for known tamil words.
     *
     * @return the dictionary object.
     * @throws  tamil.lang.exception.service.ServiceException when there is an issue while getting this service.
     */
    public static TamilDictionary getSystemDictionary() throws ServiceException {

        return systemDictionary;
    }

    /**
     * Gets  the Transliterator to tamil
     *
     * @param fromLanguage the source language. Currently it is ignored. English is the only supported language!
     * @return the Transliterator
     * @throws  tamil.lang.exception.service.ServiceException when there is an issue while getting this service.
     */
    public static Transliterator getTransliterator(String fromLanguage) {
        return EnglishToTamilCharacterLookUpContext.TRANSLIST;
    }

    /**
     * Gets the WordsJoiner that can be used to appendNodesToAllPaths multiple words using simple புணர்ச்சி laws.
     *
     * @param nilaiMozhi the initial word. (நிலைமொழி )
     * @return the WordsJoiner
     * @throws  tamil.lang.exception.service.ServiceException when there is an issue while creating this service.
     */
    public static WordsJoiner createWordJoiner(TamilWord nilaiMozhi) {
        WordsJoinHandler handler = new WordsJoinHandler();
        handler.add(nilaiMozhi);
        return handler;
    }


    /**
     * Gets the number reader interface.
     * @return  the instance of a number reader.
     * @throws  tamil.lang.exception.service.ServiceException when there is an issue while getting this service.
     */
    public static NumberReader getNumberReader() {
          return DefaultNumberReader.reader;
    }

}
