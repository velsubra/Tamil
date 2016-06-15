package tamil.lang;

import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.impl.DefaultNumberReader;
import my.interest.lang.tamil.impl.TamilEzhuththuSetCalculatorImpl;
import my.interest.lang.tamil.impl.dictionary.DictionaryCollection;
import my.interest.lang.tamil.impl.job.JobManagerImpl;
import my.interest.lang.tamil.impl.rx.RXCompilerImpl;
import my.interest.lang.tamil.punar.handler.KnownWordsJoinerImpl;
import my.interest.lang.tamil.punar.handler.WordsJoinHandler;
import my.interest.lang.tamil.translit.EnglishToTamilCharacterLookUpContext;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.api.ezhuththu.TamilCharacterSetCalculator;
import tamil.lang.api.job.JobManager;
import tamil.lang.api.join.KnownWordsJoiner;
import tamil.lang.api.join.WordsJoiner;
import tamil.lang.api.number.NumberReader;
import tamil.lang.api.parser.CompoundWordParser;
import tamil.lang.api.persist.manager.PersistenceManager;
import tamil.lang.api.regex.TamilRXCompiler;
import tamil.lang.api.trans.Transliterator;
import tamil.lang.exception.service.ServiceException;
import tamil.lang.known.IKnownWord;
import tamil.lang.spi.CompoundWordParserProvider;
import tamil.lang.spi.PersistenceManagerProvider;
import tamil.lang.spi.TamilDictionaryProvider;
import tamil.util.regex.SimpleMatcher;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * <p>
 * The factory class that can provide entry point for different services.
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
        getRegEXCompiler();
    }


    private static TamilDictionary systemDictionary = null;
    private static CompoundWordParserProvider parserprovider = null;
    private static PersistenceManager persistenceManager = null;
    private static JobManager jobManager = new JobManagerImpl(null);

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
                parserprovider = it.next();
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
     * Gets the WordsJoiner that can be used multiple words using simple புணர்ச்சி laws.
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
     * Gets the WordsJoiner that can be used to add multiple known words using specific புணர்ச்சி laws.
     *
     * @param nilaiMozhi the initial word. (நிலைமொழி )
     * @return the WordsJoiner
     * @throws tamil.lang.exception.service.ServiceException when there is an issue while creating this service.
     */
    public static KnownWordsJoiner createKnownWordJoiner(IKnownWord nilaiMozhi) throws ServiceException {
        KnownWordsJoiner handler = new KnownWordsJoinerImpl(nilaiMozhi);
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


    /**
     * Gets compound word parser.
     *
     * @return CompoundWordParser
     * @throws ServiceException if not available in the  current execution env. It is not available in the applet environment.
     */
    public static CompoundWordParser getCompoundWordParser() throws ServiceException {
        if (parserprovider == null) {
            throw new ServiceException("Unimplemented!");
        } else {
            return parserprovider.crate();
        }
    }

    /**
     * Gets the calculator for finding different character sets based on the properties of Tamil Characters.
     *
     * @return the calculator.
     * @throws ServiceException if the calculator cannot be created.
     */
    public static TamilCharacterSetCalculator getTamilCharacterSetCalculator() throws ServiceException {
        return TamilEzhuththuSetCalculatorImpl.DEFAULT;
    }

    /**
     * Gets the compiler for Tamil regular expression. Tamil regular expression is any Java regular expression that contains variables of the form
     * ${tamil_expression}, where  tamil_expression could be
     * <ol>
     * <li>
     * any tamil character set returned by {@link  tamil.lang.api.ezhuththu.TamilCharacterSetCalculator#getEzhuththuSetDescriptions()} or
     * </li> <li>
     * any set that is calculated by {@link TamilCharacterSetCalculator#find(String)} or
     * </li> <li>
     *  expressions defined by {@link tamil.lang.api.regex.TamilRXCompiler}
     * </li>
     * </ol>
     * The expression ${letter_set} means any single character from the character set "letter_set" that could be basic or calculated. For example ${குறில்}
     * means any குறில் character.  ${குறில்}* mean 0 or more குறிலெழுத்துகள்     .
     *
     * @return the TamilRXCompiler.
     * @throws ServiceException if the compiler can not be obtained.
     */
    public static TamilRXCompiler getRegEXCompiler() throws ServiceException {
        return RXCompilerImpl.DEFAULT;
    }


    /**
     * Returns the persistence manager used to persist root words and rules.
     *
     * @return PersistenceManager
     * @throws ServiceException
     */
    public static PersistenceManager getPersistenceManager() throws ServiceException {
        if (persistenceManager == null) {
            throw new ServiceException("Unimplemented!");
        } else {
            return persistenceManager;
        }
    }

    /**
     * Gets the job manger used to submit background jobs.
     * @param category the category of the jobs that are going to be performed by the job manager.
     *                 The value can be null to mean the default job manager. The value can be path like.
     *                 E.g)  jobs/search. Providing meaningful category will isolate the jobs such that searching the job with the job id will be faster.
     *
     * @return the job manager
     * @throws ServiceException
     */
    public static JobManager getJobManager(String category) throws ServiceException {
        if (category ==null || category.trim().equals("")) {
            return jobManager;
        } else {
           return new JobManagerImpl(category);
        }
    }


    /**
     * Transposes a matcher
     * @param matcher the matcher to be transposed
     * @return the transposed matcher
     */
    public static SimpleMatcher transpose(SimpleMatcher matcher) {
        return TamilUtils.transpose(matcher);
    }


}
