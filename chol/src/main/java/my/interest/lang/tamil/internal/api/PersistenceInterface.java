package my.interest.lang.tamil.internal.api;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.StringUtils;
import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.generated.types.*;
import my.interest.lang.tamil.generated.types.Properties;
import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.FileBasedPersistence;
import my.interest.lang.tamil.impl.PropertyFinderForResource;
import my.interest.lang.tamil.impl.dictionary.DefaultPlatformDictionaryBase;
import my.interest.lang.tamil.multi.ExecuteManager;
import my.interest.lang.tamil.multi.WordGeneratorFromIdai;
import my.interest.lang.tamil.multi.WordGeneratorFromPeyar;
import my.interest.lang.tamil.multi.WordGeneratorFromVinaiyadi;
import my.interest.lang.tamil.punar.PropertyDescriptionContainer;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.xml.AppCache;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.feature.FeatureConstants;
import tamil.lang.api.persist.manager.*;
import tamil.lang.api.persist.matcher.DescriptionMatcher;
import tamil.lang.api.persist.matcher.IdaichcholDescriptionMatcher;
import tamil.lang.api.persist.matcher.PeyarchcholDescriptionMatcher;
import tamil.lang.api.persist.matcher.RootVerbDescriptionMatcher;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.derived.DerivativeWithPaal;
import tamil.lang.known.derived.DerivativeWithTenseAndPaal;
import tamil.lang.known.derived.VinaiyadiDerivative;
import tamil.lang.known.non.derived.NonStartingIdaichchol;
import tamil.lang.known.non.derived.Peyarchchol;
import tamil.lang.known.non.derived.Vinaiyadi;

import javax.script.CompiledScript;
import javax.script.ScriptException;
import java.io.File;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public abstract class PersistenceInterface extends DefaultPlatformDictionaryBase implements PersistenceManager, RootVerbManager, PrepositionManager, NounManager, ApplicationManager {

    protected static final TamilWord iduword = TamilWord.from("இடு");


    public void setAutoLoad(boolean autoLoad) {
        this.autoLoad = autoLoad;
    }

    protected boolean autoLoad = true;


    public static Map<String, RootVerbDescriptionMatcher> matchermap = new HashMap<String, RootVerbDescriptionMatcher>();
    public static Map<String, PeyarchcholDescriptionMatcher> peyarmatchermap = new HashMap<String, PeyarchcholDescriptionMatcher>();
    public static Map<String, IdaichcholDescriptionMatcher> idaimatchermap = new HashMap<String, IdaichcholDescriptionMatcher>();

    static {
        matchermap.put("STARTS_MATCHER", RootVerbDescriptionMatcher.STARTS_MATCHER);
        matchermap.put("TRAVERSE", RootVerbDescriptionMatcher.TRAVERSE);
        matchermap.put("EXACT_MATCHER", RootVerbDescriptionMatcher.EXACT_MATCHER);
        matchermap.put("ISSUE", RootVerbDescriptionMatcher.ISSUE);
        matchermap.put("NEW", RootVerbDescriptionMatcher.NEW);
        matchermap.put("VINAIMUTTU_UNLOCKED", RootVerbDescriptionMatcher.VINAIMUTTU_UNLOCKED);
        matchermap.put("UNCHANGED_THOZHIRR", RootVerbDescriptionMatcher.NO_CHANGE_ON_THOZHIRPEYAR);
        matchermap.put("UNCHANGED_ENGLISH", RootVerbDescriptionMatcher.NO_CHANGE_ON_ENGLISH);


        peyarmatchermap.put("STARTS_MATCHER", PeyarchcholDescriptionMatcher.STARTS_MATCHER);
        peyarmatchermap.put("TRAVERSE", PeyarchcholDescriptionMatcher.TRAVERSE);
        peyarmatchermap.put("EXACT_MATCHER", PeyarchcholDescriptionMatcher.EXACT_MATCHER);
        peyarmatchermap.put("ISSUE", PeyarchcholDescriptionMatcher.ISSUE);
        peyarmatchermap.put("NEW", PeyarchcholDescriptionMatcher.NEW);
        peyarmatchermap.put("DEFN_UNLOCKED", PeyarchcholDescriptionMatcher.DEFN_UNLOCKED);
        peyarmatchermap.put("definition.type:p", new PeyarchcholDescriptionMatcher.ValueBasedMatcher("definition.type", "p"));
        peyarmatchermap.put("definition.type:i", new PeyarchcholDescriptionMatcher.ValueBasedMatcher("definition.type", "i"));
        peyarmatchermap.put("definition.type:k", new PeyarchcholDescriptionMatcher.ValueBasedMatcher("definition.type", "k"));
        peyarmatchermap.put("definition.type:s", new PeyarchcholDescriptionMatcher.ValueBasedMatcher("definition.type", "s"));
        peyarmatchermap.put("definition.type:pa", new PeyarchcholDescriptionMatcher.ValueBasedMatcher("definition.type", "pa"));
        peyarmatchermap.put("definition.type:t", new PeyarchcholDescriptionMatcher.ValueBasedMatcher("definition.type", "t"));
        peyarmatchermap.put("definition.type:o", new PeyarchcholDescriptionMatcher.ValueBasedMatcher("definition.type", "o"));
        peyarmatchermap.put("meaning.english.words:", new PeyarchcholDescriptionMatcher.ValueBasedMatcher("meaning.english.words", null));
        peyarmatchermap.put("definition.type:", new PeyarchcholDescriptionMatcher.ValueBasedMatcher("definition.type", null));   // un-categorized


        idaimatchermap.put("STARTS_MATCHER", IdaichcholDescriptionMatcher.STARTS_MATCHER);
        idaimatchermap.put("TRAVERSE", IdaichcholDescriptionMatcher.TRAVERSE);
        idaimatchermap.put("EXACT_MATCHER", IdaichcholDescriptionMatcher.EXACT_MATCHER);
        idaimatchermap.put("ISSUE", IdaichcholDescriptionMatcher.ISSUE);
        idaimatchermap.put("NEW", IdaichcholDescriptionMatcher.NEW);
        idaimatchermap.put("DEFN_UNLOCKED", IdaichcholDescriptionMatcher.DEFN_UNLOCKED);

        TamilFactory.init();
    }

    /**
     * Do not create this on your local env. /customer/scratch is used when the app is deployed in the oracle cloud.
     *
     * @return
     */
    public static boolean isOnCloud() {
        return new File("/customer/scratch").exists();
    }

    static File WORK_DIR = null;

    public static File getWorkDir() {
        if (WORK_DIR == null) {
            if (isOnCloud()) {
                WORK_DIR = new File("/customer/scratch/i18n");
            } else {

                WORK_DIR = new File(System.getProperty("user.home"), "tamil-platform");
                if (!WORK_DIR.exists()) {
                    WORK_DIR.mkdirs();
                }
                System.out.println("Work Dir:" + WORK_DIR.getAbsolutePath());

            }
        }
        return WORK_DIR;
    }

    /**
     * TODO: Need to fix this
     *
     * @return
     */
    public static PersistenceInterface get() {

        return FileBasedPersistence.ME_SINGLETON;

    }


    private static final SortedSet<IKnownWord> consonantset = Collections.synchronizedSortedSet(new TreeSet<IKnownWord>(new Comparator<IKnownWord>() {
        @Override
        public int compare(IKnownWord o1, IKnownWord o2) {
            int ret = o1.getWord().getConsonants().compareTo(o2.getWord().getConsonants());
            if (ret == 0) {
                ret = o1.getWord().getVowels().compareTo(o2.getWord().getVowels());
            }
            if (ret == 0) {
                ret = o1.getWord().size() - o2.getWord().size();
            }
            return ret;
        }
    }));


    public static synchronized void addEnglishMappings(String eng, IKnownWord w) {
        if (eng == null || w == null) return;
        eng = eng.trim();
        List<String> list = TamilUtils.parseString(eng);
        addEnglishMappings(list, w);

    }

    public static synchronized void addEnglishMappings(List<String> list, IKnownWord w) {
        for (String e : list) {
            addEnglishMapping(e, w);
        }
    }


    private static synchronized void addEnglishMapping(String eng, IKnownWord w) {
        List<IKnownWord> list = english_mapping.get(eng);
        int insertIndex = 0;
        if (list == null) {
            list = new LinkedList<IKnownWord>();
            english_mapping.put(eng, list);

        } else {
            for (int i = 0; i < list.size(); i++) {
                int compare = (w.compareTo(list.get(i)));
                if (compare == 0) {
                    return;
                }
                if (compare < 0) {
                    insertIndex = i;
                    break;
                }
            }
        }
        list.add(insertIndex, w);
    }


    private static final SortedSet<IKnownWord> hashset = Collections.synchronizedSortedSet(new TreeSet<IKnownWord>(new Comparator<IKnownWord>() {
        @Override
        public int compare(IKnownWord o1, IKnownWord o2) {
            return o1.getWord().suggestionHashCode() - o2.getWord().suggestionHashCode();
        }
    }));

    public boolean isEmptyKnown() {
        return set.isEmpty();
    }

    protected static TreeSet<RootVerbDescription> roots = null;
    protected static TreeSet<PeyarchcholDescription> peyars = null;
    protected static TreeSet<IdaichcholDescription> idais = null;

    public int totalWordsSize() {
        return set.size();
    }


    @Override
    public List<IKnownWord> suggest(TamilWord search, int max, List<Class<? extends IKnownWord>> includeTypes) {
        List<IKnownWord> list = super.suggest(search, (int) (max / 3), includeTypes);
        if (list.size() < max) {
            List<IKnownWord> sug = suggestions.get(search.suggestionHashCode());
            if (sug != null) {
                if (sug.size() >= max - list.size()) {
                    list.addAll(sug.subList(0, max - list.size()));
                } else {
                    list.addAll(sug);
                }
            }
            list.addAll(findMatchingDerivedWords(consonantset, search, max - list.size() - 1, includeTypes, new FeatureSet(FeatureConstants.DICTIONARY_AUTO_SUGGEST_VAL_165)));
            list.addAll(findMatchingDerivedWords(hashset, search, max - list.size(), includeTypes, new FeatureSet(FeatureConstants.DICTIONARY_AUTO_SUGGEST_VAL_165)));


        }

        return list;
    }


    protected void removeKnown(IKnownWord w) {
        consonantset.remove(w);
        hashset.remove(w);
        super.removeKnown(w);

    }

    protected void addKnown(IKnownWord w) {
        consonantset.add(w);
        hashset.add(w);
        super.addKnown(w);
    }


    public static void addIfNotFound(IKnownWord w) {
        if (FileBasedPersistence.ME_SINGLETON.peek(w) == null) {
            FileBasedPersistence.ME_SINGLETON.addKnown(w);
        }
    }

    public static void addOrUpdateKnown(IKnownWord w) {

        FileBasedPersistence.ME_SINGLETON.removeKnown(w);
        FileBasedPersistence.ME_SINGLETON.addKnown(w);
    }


    public static void addDerivativeWithTense(PropertyDescriptionContainer container, boolean transitive, GenericTenseTable table, Class<? extends VinaiyadiDerivative> cls) {
        try {
            Vinaiyadi vi = Vinaiyadi.get(TamilWord.from(table.getRoot()), container, transitive);
            //if (set.contains(vi)) return;
            addOrUpdateKnown(vi);
            for (TableRow r : table.getRows()) {
                if (r.getPresent() != null) {
                    for (DerivedValue d : r.getPresent().getList()) {
                        VinaiyadiDerivative p = cls.getConstructor(TamilWord.class, Vinaiyadi.class, SimpleTense.class).newInstance(TamilWord.from(d.getValue()), vi, SimpleTense.PRESENT);
                        addOrUpdateKnown(p);
                    }
                }
                if (r.getPast() != null) {
                    for (DerivedValue d : r.getPast().getList()) {
                        VinaiyadiDerivative p = cls.getConstructor(TamilWord.class, Vinaiyadi.class, SimpleTense.class).newInstance(TamilWord.from(d.getValue()), vi, SimpleTense.PAST);
                        addOrUpdateKnown(p);
                    }
                }
                if (r.getFuture() != null) {
                    for (DerivedValue d : r.getFuture().getList()) {
                        VinaiyadiDerivative p = cls.getConstructor(TamilWord.class, Vinaiyadi.class, SimpleTense.class).newInstance(TamilWord.from(d.getValue()), vi, SimpleTense.FUTURE);
                        addOrUpdateKnown(p);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void addDerivativeWithPaal(PropertyDescriptionContainer container, boolean transitive, GenericTenseTable table, Class<? extends DerivativeWithPaal> cls) {
        try {
            Vinaiyadi vi = Vinaiyadi.get(TamilWord.from(table.getRoot()), container, transitive);
            // if (set.contains(vi)) return;
            addOrUpdateKnown(vi);

            for (TableRow r : table.getRows()) {
                PaalViguthi viguthi = PaalViguthi.fromValue(r.getRowname());
                if (r.getPresent() != null) {
                    for (DerivedValue d : r.getPresent().getList()) {
                        DerivativeWithPaal vinai = cls.getConstructor(TamilWord.class, Vinaiyadi.class, PaalViguthi.class).newInstance(TamilWord.from(d.getValue()), vi, viguthi);
                        addOrUpdateKnown(vinai);
                    }
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public static void addDerivative(PropertyDescriptionContainer container, boolean transitive, GenericTenseTable table, Class<? extends VinaiyadiDerivative> cls) {
        try {
            Vinaiyadi vi = Vinaiyadi.get(TamilWord.from(table.getRoot()), container, transitive);
            // if (set.contains(vi)) return;
            addOrUpdateKnown(vi);

            for (TableRow r : table.getRows()) {

                if (r.getPresent() != null) {
                    for (DerivedValue d : r.getPresent().getList()) {
                        VinaiyadiDerivative vinai = cls.getConstructor(TamilWord.class, Vinaiyadi.class).newInstance(TamilWord.from(d.getValue()), vi);
                        addOrUpdateKnown(vinai);
                    }
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public static void addDerivativeWithTenseAndPaal(PropertyDescriptionContainer container, boolean transitive, GenericTenseTable table, boolean thodar, boolean muutu, Class<? extends DerivativeWithTenseAndPaal> cls) {
        try {
            Vinaiyadi vi = Vinaiyadi.get(TamilWord.from(table.getRoot()), container, transitive);
            // if (set.contains(vi)) return;
            addOrUpdateKnown(vi);

            for (TableRow r : table.getRows()) {
                PaalViguthi viguthi = PaalViguthi.fromValue(r.getRowname());
                if (r.getPresent() != null) {
                    for (DerivedValue d : r.getPresent().getList()) {
                        DerivativeWithTenseAndPaal vinai = cls.getConstructor(TamilWord.class, Vinaiyadi.class, SimpleTense.class, PaalViguthi.class).newInstance(TamilWord.from(d.getValue()), vi, SimpleTense.PRESENT, viguthi);

                        if (thodar) {
                            vinai.addProperty("thodar", "true");
                        }
                        if (muutu) {
                            vinai.addProperty("muttu", "true");
                        }
                        addOrUpdateKnown(vinai);
                    }
                }
                if (r.getPast() != null) {
                    for (DerivedValue d : r.getPast().getList()) {
                        DerivativeWithTenseAndPaal vinai = cls.getConstructor(TamilWord.class, Vinaiyadi.class, SimpleTense.class, PaalViguthi.class).newInstance(TamilWord.from(d.getValue()), vi, SimpleTense.PAST, viguthi);
                        if (thodar) {
                            vinai.addProperty("thodar", "true");
                        }
                        if (muutu) {
                            vinai.addProperty("muttu", "true");
                        }
                        addOrUpdateKnown(vinai);
                    }
                }
                if (r.getFuture() != null) {
                    for (DerivedValue d : r.getFuture().getList()) {
                        DerivativeWithTenseAndPaal vinai = cls.getConstructor(TamilWord.class, Vinaiyadi.class, SimpleTense.class, PaalViguthi.class).newInstance(TamilWord.from(d.getValue()), vi, SimpleTense.FUTURE, viguthi);
                        if (thodar) {
                            vinai.addProperty("thodar", "true");
                        }
                        if (muutu) {
                            vinai.addProperty("muttu", "true");
                        }
                        addOrUpdateKnown(vinai);
                    }
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Definition findDefinition(String name) {
        if (name == null) return null;
        TamilRootWords verbs = getAllRootWords();
        if (verbs.getVinai() == null) return null;
        for (Definition defn : getAllRootWords().getVinai().getDefinitions().getDefinition()) {
            if (name.equals(defn.getName())) {
                return defn;
            }
        }
        return null;
    }


    public PropertyDescriptionContainer getConsolidatedPropertyContainerFor(RootVerbDescription root) {
        RootVerbsDescription verbs = getAllRootWords().getVinai().getVerbs();
        Properties pack = null;
        if (root.getDescription() != null && root.getDescription().getName() != null && !root.getDescription().getName().trim().equals("")) {

            for (Properties named : verbs.getNamedDescription()) {
                if (root.getDescription().getName().equals(named.getName())) {
                    pack = named;
                    break;
                }
            }
        }

        return new PropertyDescriptionContainer(root, new PropertyDescriptionContainer(pack), new PropertyDescriptionContainer(verbs.getGlobalDescription()));


    }

    public PropertyDescriptionContainer getConsolidatedPropertyContainerFor(PeyarchcholDescription noun) {


        PeyarchchchorrkalhDescription nouns = getAllRootWords().getPeyar().getWords();
//        Properties pack = null;
//        if (root.getDescription() != null && root.getDescription().getName() != null && !root.getDescription().getName().trim().equals("")) {
//
//            for (Properties named : verbs.getNamedDescription()) {
//                if (root.getDescription().getName().equals(named.getName())) {
//                    pack = named;
//                    break;
//                }
//            }
//        }

        // return new PropertyDescriptionContainer(root.getDescription());


        return new PropertyDescriptionContainer(noun, new PropertyDescriptionContainer(noun.getDescription()), new PropertyDescriptionContainer(nouns.getGlobalDescription()));


    }

    public PropertyDescriptionContainer getConsolidatedPropertyContainerFor(IdaichcholDescription root) {
        //IdaichchorrkalhDescription idais = getAllRootWords().getIdai().getWords();
        return new PropertyDescriptionContainer(root, new PropertyDescriptionContainer(root.getDescription()));


    }

    //    public RootVerbDescription findRootVerbDescription(String root) {
//        RootVerbsDescription verbs = getAllRootWords().getVerbs();
//        return findRootVerbDescription(verbs, root, null);
//    }
//
//    public RootVerbDescription findRootVerbDescription(String root, Boolean bool) {
//        RootVerbsDescription verbs = getAllRootWords().getVerbs();
//        return findRootVerbDescription(verbs, root, bool);
//    }
//
//
    public RootVerbDescription findNextRootVerbDescription(String root, RootVerbDescriptionMatcher matcher) {

        return findRootVerbDescription(root, Boolean.TRUE, matcher);
    }

    public RootVerbDescription findPreviousRootVerbDescription(String root, RootVerbDescriptionMatcher matcher) {

        return findRootVerbDescription(root, Boolean.FALSE, matcher);
    }

    public RootVerbDescription findRootVerbDescription(String root) {
        return findRootVerbDescription(root, null, RootVerbDescriptionMatcher.EXACT_MATCHER);
    }

    public PeyarchcholDescription findNextPeyarchcholDescription(String root, PeyarchcholDescriptionMatcher matcher) {

        return findPeyarchcholDescription(root, Boolean.TRUE, matcher);
    }

    public PeyarchcholDescription findPreviousPeyarchcholDescription(String root, PeyarchcholDescriptionMatcher matcher) {

        return findPeyarchcholDescription(root, Boolean.FALSE, matcher);
    }


    public IdaichcholDescription findNextIdaichcholDescription(String root, IdaichcholDescriptionMatcher matcher) {

        return findIdaichcholDescription(root, Boolean.TRUE, matcher);
    }

    public IdaichcholDescription findPreviousIdaichcholDescription(String root, IdaichcholDescriptionMatcher matcher) {

        return findIdaichcholDescription(root, Boolean.FALSE, matcher);
    }


    public PeyarchcholDescription findPeyarchcholDescription(String root) {
        return findPeyarchcholDescription(root, null, PeyarchcholDescriptionMatcher.EXACT_MATCHER);
    }


    public IdaichcholDescription findIdaichcholDescription(String root) {
        return findIdaichcholDescription(root, null, IdaichcholDescriptionMatcher.EXACT_MATCHER);
    }


    public RootVerbDescription findRootVerbDescription(String root, Boolean next, RootVerbDescriptionMatcher matcher) {
        RootVerbsDescription all = null;
        if (next == null) {
            all = findAllVerbsWithPattern(root, 1, null, RootVerbDescriptionMatcher.EXACT_MATCHER);

        } else {


            if (next) {
                all = findAllVerbsWithPattern(root, 1, true, matcher);
            } else {
                all = findAllVerbsWithPattern(root, 1, false, matcher);
            }
        }
        if (all == null) {
            return null;
        }

        if (all.getList() != null && !all.getList().getVerb().isEmpty()) {

            return all.getList().getVerb().get(0);
        } else {
            return null;
        }

    }

    public PeyarchcholDescription findPeyarchcholDescription(String root, Boolean next, PeyarchcholDescriptionMatcher matcher) {
        PeyarchchchorrkalhDescription all = null;
        if (next == null) {
            all = findAllNounsWithPattern(root, 1, null, PeyarchcholDescriptionMatcher.EXACT_MATCHER);

        } else {


            if (next) {
                all = findAllNounsWithPattern(root, 1, true, matcher);
            } else {
                all = findAllNounsWithPattern(root, 1, false, matcher);
            }
        }
        if (all == null) {
            return null;
        }

        if (all.getList() != null && !all.getList().getWord().isEmpty()) {

            return all.getList().getWord().get(0);
        } else {
            return null;
        }

    }


    public IdaichcholDescription findIdaichcholDescription(String root, Boolean next, IdaichcholDescriptionMatcher matcher) {
        IdaichchorrkalhDescription all = null;
        if (next == null) {
            all = findAllPrepositionsWithPattern(root, 1, null, IdaichcholDescriptionMatcher.EXACT_MATCHER);

        } else {


            if (next) {
                all = findAllPrepositionsWithPattern(root, 1, true, matcher);
            } else {
                all = findAllPrepositionsWithPattern(root, 1, false, matcher);
            }
        }
        if (all == null) {
            return null;
        }

        if (all.getList() != null && !all.getList().getWord().isEmpty()) {

            return all.getList().getWord().get(0);
        } else {
            return null;
        }

    }


    private <T extends Object> List<T> findAllWords(T pattern, int max, Boolean forward, TreeSet<T> roots, DescriptionMatcher<T> matcher) {


        Iterator<T> sub = null;
        synchronized (roots) {
            if (forward == null) {
                sub = roots.tailSet(pattern, true).iterator();
            } else {
                if (forward) {
                    sub = roots.tailSet(pattern, false).iterator();
                } else {
                    sub = roots.headSet(pattern, false).descendingIterator();
                }
            }
            List<T> ret = new ArrayList<T>();

            if (max < 0) {
                max = -max;
            }
            while (max > 0) {

                if (sub.hasNext()) {
                    T re = sub.next();
                    boolean add = matcher.matches(pattern, re, this);

                    if (add) {
                        max--;
                        ret.add(re);

                    }
                } else {
                    break;
                }

            }


            return ret;
        }
    }

    public PeyarchchchorrkalhDescription findAllNounsWithPattern(String pattern, int max, Boolean forward, PeyarchcholDescriptionMatcher matcher) {
        if (pattern == null) {
            pattern = "";
        }
        PeyarchchchorrkalhDescription ret = new PeyarchchchorrkalhDescription();
        ret.setList(new PeyarchchchorrkalhList());
        PeyarchcholDescription temp = new PeyarchcholDescription();
        temp.setRoot(pattern);
        //This should trigger reloading ...
        getAllRootWords();
        ret.getList().getWord().addAll(findAllWords(temp, max, forward, peyars, matcher));
        return ret;
    }


    public IdaichchorrkalhDescription findAllPrepositionsWithPattern(String pattern, int max, Boolean forward, IdaichcholDescriptionMatcher matcher) {
        if (pattern == null) {
            pattern = "";
        }
        IdaichchorrkalhDescription ret = new IdaichchorrkalhDescription();
        ret.setList(new IdaichchorrkalhList());
        IdaichcholDescription temp = new IdaichcholDescription();
        temp.setRoot(pattern);
        //This should trigger reloading ...
        getAllRootWords();
        ret.getList().getWord().addAll(findAllWords(temp, max, forward, idais, matcher));
        return ret;
    }


    public RootVerbsDescription findAllVerbsWithPattern(String pattern, int max, Boolean forward, RootVerbDescriptionMatcher matcher) {
        if (pattern == null) {
            pattern = "";
        }
        RootVerbsDescription ret = new RootVerbsDescription();
        ret.setList(new RootVerbsList());
        RootVerbDescription temp = new RootVerbDescription();
        temp.setRoot(pattern);
        //This should trigger reloading ...
        getAllRootWords();
        ret.getList().getVerb().addAll(findAllWords(temp, max, forward, roots, matcher));
        return ret;


    }

    public abstract TamilRootWords getAllRootWords();

    public abstract void persist(TamilRootWords verbs);

    public abstract void lock();

    public abstract void unlock();

    public boolean addRootVerb(String verb) {
        lock();

        try {
            TamilRootWords words = getAllRootWords();
            RootVerbDescription desc = findRootVerbDescription(verb, null, RootVerbDescriptionMatcher.EXACT_MATCHER);
            if (desc == null) {
                desc = new RootVerbDescription();
                desc.setRoot(verb);
                TamilWordPartContainer container = new TamilWordPartContainer(TamilWord.from(verb));

                PropertyDescriptionContainer propContainer = new PropertyDescriptionContainer(null);
                propContainer.setDeletable();
                if (container.isEndingwithIru()) {
                    propContainer.setSetName("ending_with_iru");
                } else if (container.isKutriyaLugaram() || (container.isEndingWithUgaaram() && (container.size() > 2 || container.isStartingWithNedil()))) {
                    if (!container.getWord().endsWith(TamilWord.from("படு")) && !container.isEndingwithIdu()) {
                        propContainer.setSetName("kutriyalugaram");
                    } else {
                        propContainer.setSetName("kutriyalugaram_padu");
                    }
                } else {
                    if (container.getWord().endsWith(TamilWord.from("கொள்"))) {
                        propContainer.setSetName("ending_with_kolh");

                    } else if (container.getWord().endsWith(TamilCompoundCharacter.INNN) || container.getWord().endsWith(TamilCompoundCharacter.ILL)) {
                        propContainer.setSetName("past-id-future-iv");

                    } else {
                        if (container.getWord().endsWith(TamilCompoundCharacter.IL)) {
                            propContainer.setSetName("past-it-future-iv");

                        }
                    }
                }

                desc.setDescription(propContainer.getProperties());

                words.getVinai().getVerbs().getList().getVerb().add(0, desc);
                ExecuteManager.fire(new WordGeneratorFromVinaiyadi(desc));
                roots.add(desc);

                persist(words);


                return true;

            } else {
                return false;
            }
        } finally {

            unlock();

        }

    }

    public boolean addRootNoun(String verb) {
        lock();

        try {
            TamilRootWords words = getAllRootWords();
            PeyarchcholDescription desc = findPeyarchcholDescription(verb);
            if (desc == null) {
                desc = new PeyarchcholDescription();
                desc.setRoot(verb);
                words.getPeyar().getWords().getList().getWord().add(0, desc);
                PropertyDescriptionContainer propContainer = new PropertyDescriptionContainer(null);
                propContainer.setDeletable();
                desc.setDescription(propContainer.getProperties());
                peyars.add(desc);
                TamilWord n = TamilWord.from(verb);
                TamilWord n_rm_ak = TamilUtils.trimFinalAKOrReturn(n);
                addOrUpdateKnown(new Peyarchchol(n_rm_ak, n.size() - n_rm_ak.size(), false));
                ExecuteManager.fire(new WordGeneratorFromPeyar(desc, this));
                persist(words);


                return true;

            } else {
                return false;
            }
        } finally {

            unlock();

        }

    }


    public boolean addRootPreposition(String prep) {
        lock();

        try {
            TamilRootWords words = getAllRootWords();
            IdaichcholDescription desc = findIdaichcholDescription(prep);
            if (desc == null) {
                desc = new IdaichcholDescription();
                desc.setRoot(prep);
                words.getIdai().getWords().getList().getWord().add(0, desc);
                PropertyDescriptionContainer propContainer = new PropertyDescriptionContainer(null);
                propContainer.setDeletable();
                desc.setDescription(propContainer.getProperties());
                idais.add(desc);
                addOrUpdateKnown(new NonStartingIdaichchol(TamilWord.from(prep), false));
                persist(words);

                ExecuteManager.fire(new WordGeneratorFromIdai(desc, this));
                return true;

            } else {
                return false;
            }
        } finally {

            unlock();

        }

    }


    public void deleteRootVerb(String verb) {
        lock();

        try {
            RootVerbDescription desc = findRootVerbDescription(verb, null, RootVerbDescriptionMatcher.EXACT_MATCHER);
            if (desc != null) {
                if (!getConsolidatedPropertyContainerFor(desc).canTheHolderBeDeleted()) {
                    throw new RuntimeException("இந்தவினையடி நீக்கப்படமுடியாது. ");
                }
                TamilRootWords verbs = getAllRootWords();
                if (!verbs.getVinai().getVerbs().getList().getVerb().remove(desc)) {
                    throw new RuntimeException("Could not be found:" + desc.getRoot());
                }
                removeKnown(Vinaiyadi.get(TamilWord.from(verb), null, true));
                removeKnown(Vinaiyadi.get(TamilWord.from(verb), null, false));
                roots.remove(desc);
                persist(verbs);
            }


        } finally {

            unlock();

        }
    }


    public void deleteNoun(String verb) {
        lock();

        try {
            PeyarchcholDescription desc = findPeyarchcholDescription(verb);
            if (desc != null) {
                if (!getConsolidatedPropertyContainerFor(desc).canTheHolderBeDeleted()) {
                    throw new RuntimeException(" நீக்கப்படமுடியாது. ");
                }
                TamilRootWords verbs = getAllRootWords();
                if (!verbs.getPeyar().getWords().getList().getWord().remove(desc)) {
                    throw new RuntimeException("Could not be found:" + desc.getRoot());
                }
                peyars.remove(desc);
                TamilWord n = TamilWord.from(verb);
                TamilWord n_rm_ak = TamilUtils.trimFinalAKOrReturn(n);
                removeKnown(new Peyarchchol(n_rm_ak, n.size() - n_rm_ak.size(), false));
                persist(verbs);
            }


        } finally {

            unlock();

        }
    }


    public void deletePrep(String verb) {
        lock();

        try {
            IdaichcholDescription desc = findIdaichcholDescription(verb);
            if (desc != null) {
                if (!getConsolidatedPropertyContainerFor(desc).canTheHolderBeDeleted()) {
                    throw new RuntimeException(" நீக்கப்படமுடியாது. ");
                }
                TamilRootWords verbs = getAllRootWords();
                if (!verbs.getIdai().getWords().getList().getWord().remove(desc)) {
                    throw new RuntimeException("Could not be found:" + desc.getRoot());
                }
                idais.remove(desc);
                removeKnown(new NonStartingIdaichchol(TamilWord.from(verb), false));

                persist(verbs);
            }


        } finally {

            unlock();

        }
    }

    private void addProperty(Properties properties, String property, String value) {
//        if (value != null){
//            value = value.replaceAll("\n", "\\n");
//        }
        boolean found = false;
        for (Property p : properties.getProperty()) {
            if (property.equals(p.getName())) {
                p.setValue(value);
                found = true;
                break;
            }
        }
        if (!found) {
            Property p = new Property();
            p.setName(property);
            p.setValue(value);
            properties.getProperty().add(p);
        }


    }

    public boolean addRootVerbProperty(String verb, String property, String value) {

        lock();

        try {

            TamilRootWords verbs = getAllRootWords();
            RootVerbDescription desc = findRootVerbDescription(verb, null, RootVerbDescriptionMatcher.EXACT_MATCHER);
            if (desc != null) {
                if (desc.getDescription() == null) {
                    desc.setDescription(new Properties());
                }

                if (!getConsolidatedPropertyContainerFor(desc).canPropertyBeUpdated(property)) {
                    throw new RuntimeException("இந்த வினையடியில் மாற்றஞ்செய்யமுடியாது. ஏதேனும்  பிழையிருந்தால் \"பிழையிருக்கிறதென்பதை\" கீழே சுட்டலாம்.");
                }
                if (PropertyDescriptionContainer.PROP_TRANS.equals(property) && !"true".equals(value)) {
                    addProperty(desc.getDescription(), PropertyDescriptionContainer.PROP_INTRANS, "true");
                } else if (PropertyDescriptionContainer.PROP_INTRANS.equals(property) && !"true".equals(value)) {
                    addProperty(desc.getDescription(), PropertyDescriptionContainer.PROP_TRANS, "true");
                }

                addProperty(desc.getDescription(), property, value);

                ExecuteManager.fire(new WordGeneratorFromVinaiyadi(desc));

                persist(verbs);
                return true;
            } else {
                return false;
            }

        } finally {

            unlock();

        }
    }

    public void deleteRootVerbProperty(String verb, String property) {
        lock();

        try {
            TamilRootWords verbs = getAllRootWords();
            RootVerbDescription desc = findRootVerbDescription(verb, null, RootVerbDescriptionMatcher.EXACT_MATCHER);
            if (desc != null) {
                if (desc.getDescription() == null) {
                    desc.setDescription(new Properties());
                }
                if (!getConsolidatedPropertyContainerFor(desc).canPropertyBeUpdated(property)) {
                    throw new RuntimeException("இந்த வினையடியில் மாற்றஞ்செய்யமுடியாது. ஏதேனும் பிழையிருந்தால் \"பிழையிருக்கிறதென்பதை\" கீழே சுட்டலாம்.");
                }
                boolean deleted = false;
                for (Property p : desc.getDescription().getProperty()) {
                    if (property.equals(p.getName())) {
                        desc.getDescription().getProperty().remove(p);
                        deleted = true;
                        break;
                    }
                }
                if (deleted) {

                    ExecuteManager.fire(new WordGeneratorFromVinaiyadi(desc));
                    persist(verbs);
                }

            }

        } finally {

            unlock();

        }
    }


    public boolean addIdaiProperty(String idai, String property, String value) {

        lock();

        try {

            TamilRootWords verbs = getAllRootWords();
            IdaichcholDescription desc = findIdaichcholDescription(idai);
            if (desc != null) {
                if (desc.getDescription() == null) {
                    desc.setDescription(new Properties());
                }

                if (!getConsolidatedPropertyContainerFor(desc).canPropertyBeUpdated(property)) {
                    throw new RuntimeException("இந்த இடைச்சொல்லில்  மாற்றஞ்செய்யமுடியாது. ஏதேனும்  பிழையிருந்தால் \"பிழையிருக்கிறதென்பதை\" கீழே சுட்டலாம்.");
                }

                addProperty(desc.getDescription(), property, value);
                ExecuteManager.fire(new WordGeneratorFromIdai(desc, this));
                persist(verbs);
                return true;
            } else {
                return false;
            }

        } finally {

            unlock();

        }
    }

    public void deleteIdaiProperty(String idai, String property) {
        lock();

        try {
            TamilRootWords verbs = getAllRootWords();
            IdaichcholDescription desc = findIdaichcholDescription(idai);
            if (desc != null) {
                if (desc.getDescription() == null) {
                    desc.setDescription(new Properties());
                }
                if (!getConsolidatedPropertyContainerFor(desc).canPropertyBeUpdated(property)) {
                    throw new RuntimeException("இந்த இடைச்சொல்லில்  மாற்றஞ்செய்யமுடியாது. ஏதேனும் பிழையிருந்தால் \"பிழையிருக்கிறதென்பதை\" கீழே சுட்டலாம்.");
                }
                boolean deleted = false;
                for (Property p : desc.getDescription().getProperty()) {
                    if (property.equals(p.getName())) {
                        desc.getDescription().getProperty().remove(p);
                        deleted = true;
                        break;
                    }
                }
                if (deleted) {
                    ExecuteManager.fire(new WordGeneratorFromIdai(desc, this));
                    persist(verbs);
                }

            }

        } finally {

            unlock();

        }
    }

    private void deleteProperty(Properties properties, String property) {


        for (Property p : properties.getProperty()) {
            if (property.equals(p.getName())) {
                properties.getProperty().remove(p);
                break;
            }
        }


    }


    public boolean addPeyarProperty(String peyar, String property, String value) {

        lock();

        try {

            TamilRootWords verbs = getAllRootWords();
            PeyarchcholDescription desc = findPeyarchcholDescription(peyar);
            if (desc != null) {
                if (desc.getDescription() == null) {
                    desc.setDescription(new Properties());
                }
                PropertyDescriptionContainer cont = getConsolidatedPropertyContainerFor(desc);
                if (!cont.canPropertyBeUpdated(property)) {
                    throw new RuntimeException("இந்த பெயர்ச்சொல்லில்   மாற்றஞ்செய்யமுடியாது. ஏதேனும்  பிழையிருந்தால் \"பிழையிருக்கிறதென்பதை\" கீழே சுட்டலாம்.");
                }


                addProperty(desc.getDescription(), property, value);
                cont = getConsolidatedPropertyContainerFor(desc);
                if (cont.isPanhpupPeyar() && peyar.endsWith("மை")) {
                    addProperty(desc.getDescription(), "i.definition.type.pa.mai", "true");
                    String th = cont.getPanhputhThiribu();
                    if (th == null || th.trim().equals("")) {
                        TamilWord tamil = TamilWord.from(peyar);
                        tamil.removeLast();
                        addProperty(desc.getDescription(), "i.i.definition.type.pa.mai.true.pp", tamil.toString());
                    }

                } else {
                    deleteProperty(desc.getDescription(), "i.definition.type.pa.mai");
                }
                ExecuteManager.fire(new WordGeneratorFromPeyar(desc, this));
                persist(verbs);
                return true;
            } else {
                return false;
            }

        } finally {

            unlock();

        }
    }

    public void deletePeyarProperty(String peyar, String property) {
        lock();

        try {
            TamilRootWords verbs = getAllRootWords();
            PeyarchcholDescription desc = findPeyarchcholDescription(peyar);
            if (desc != null) {
                if (desc.getDescription() == null) {
                    desc.setDescription(new Properties());
                }
                PropertyDescriptionContainer cont = getConsolidatedPropertyContainerFor(desc);
                if (!cont.canPropertyBeUpdated(property)) {
                    throw new RuntimeException("இந்த பெயர்ச்சொல்லில்   மாற்றஞ்செய்யமுடியாது. ஏதேனும் பிழையிருந்தால் \"பிழையிருக்கிறதென்பதை\" கீழே சுட்டலாம்.");
                }
                boolean deleted = false;
                for (Property p : desc.getDescription().getProperty()) {
                    if (property.equals(p.getName())) {
                        desc.getDescription().getProperty().remove(p);
                        deleted = true;
                        break;
                    }
                }
                if (deleted) {
                    ExecuteManager.fire(new WordGeneratorFromPeyar(desc, this));
                    persist(verbs);
                }

            }

        } finally {

            unlock();

        }
    }

    private static AppResource findAppResourceFromApp(TamilRootWords all, AppDescription app, String resource, boolean local) {
        if (app == null || resource == null || resource.trim().equals("")) return null;
        if (app.getResources() == null) {
            app.setResources(new AppResources());
        }
        if (app.getCache() == null) {
            app.setCache(new AppCache());
        }
        if (app.getCache().getInheritanceList().isEmpty()) {
            app.getCache().buildInheritanceOrder(all, app);
        }
        if (app.getCache().getAppClassLoader() == null) {
            app.getCache().buildClassloader(app);
        }

        for (AppDescription inherit : app.getCache().getInheritanceList()) {
            for (AppResource r : inherit.getResources().getResources()) {
                if (resource.equals(r.getName())) {
                    return r;
                }
            }
            if (!local && inherit.getCache() != null) {

                AppResource extexisting = inherit.getCache().getExternalResource(resource);
                if (extexisting != null) {
                    return extexisting;
                } else {
                    int count = 0;
                    for (ExternalResource ext : inherit.getExternalResources()) {
                        count++;
                        if (ext.getUrl() == null) continue;
                        ext.setUrl(ext.getUrl().trim());
                        if (!ext.getUrl().endsWith("/")) {
                            ext.setUrl(ext.getUrl() + "/");
                        }
                        try {
                            java.net.URI url = new java.net.URI(ext.getUrl() + resource);
                            InputStream in = null;
                            String proxy = System.getProperty("http.proxyHost");
                            int port = 80;
                            if (proxy != null && ext.getUrl().toLowerCase().startsWith("http")) {
                                in = url.toURL().openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy, port))).getInputStream();
                            } else {
                                in = url.toURL().openConnection().getInputStream();
                            }
                            AppResource external = new AppResource();
                            external.setContent(URLEncoder.encode(new String(TamilUtils.readAllFrom(in, false), EzhuththuUtils.ENCODING), EzhuththuUtils.ENCODING).getBytes(EzhuththuUtils.ENCODING));
                            external.setName(resource);
                            if (resource.toLowerCase().endsWith(".html") || resource.toLowerCase().endsWith(".htm")) {
                                external.setType(AppResourceType.HTML);
                            } else if (resource.toLowerCase().endsWith(".js") || resource.toLowerCase().endsWith(".javascript")) {
                                external.setType(AppResourceType.JAVASCRIPT);
                            } else if (resource.toLowerCase().endsWith(".css") || resource.toLowerCase().endsWith(".style")) {
                                external.setType(AppResourceType.CSS);
                            } else if (resource.toLowerCase().endsWith(".xsd") || resource.toLowerCase().endsWith(".xml") || resource.toLowerCase().endsWith(".xhtml")) {
                                external.setType(AppResourceType.XML);
                            } else if (resource.toLowerCase().endsWith(".txt") || resource.toLowerCase().endsWith(".text")) {
                                external.setType(AppResourceType.TEXT);

                            } else if (resource.toLowerCase().endsWith(".json")) {
                                external.setType(AppResourceType.JSON);
                            } else {
                                external.setType(AppResourceType.UNKNOWN);
                            }
                            inherit.getCache().putExternalResource(external);
                            return external;

                        } catch (Exception e) {

                            AppResource external = new AppResource();
                            try {
                                external.setContent(URLEncoder.encode(new String(TamilUtils.fromThrowable(e), EzhuththuUtils.ENCODING), EzhuththuUtils.ENCODING).getBytes(EzhuththuUtils.ENCODING));
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                            external.setName(resource);
                            external.setType(AppResourceType.TEXT);
                            e.printStackTrace();
                            if (inherit.getExternalResources().size() == count) {
                                return external;
                            }
                            //inherit.getCache().putExternalResource(external);
                        }
                    }
                }
            }
        }

        return null;
    }

    public List<String> getAppNames() {
        List<String> ret = new ArrayList<String>();
        TamilRootWords file = this.getAllRootWords();
        if (file.getApps() == null) {
            file.setApps(new Apps());
        }
        if (file.getApps().getApps() == null) {
            file.getApps().setApps(new AppsDescription());
        }
        if (file.getApps().getApps().getList() == null) {
            file.getApps().getApps().setList(new AppsDescriptionList());
        }
        for (AppDescription a : file.getApps().getApps().getList().getApp()) {
            ret.add(a.getName());
        }
        return ret;
    }


    public List<String> getAppContexts() {
        List<String> ret = new ArrayList<String>();
        TamilRootWords file = this.getAllRootWords();
        if (file.getApps() == null) {
            file.setApps(new Apps());
        }
        if (file.getApps().getApps() == null) {
            file.getApps().setApps(new AppsDescription());
        }
        if (file.getApps().getApps().getList() == null) {
            file.getApps().getApps().setList(new AppsDescriptionList());
        }
        for (AppDescription a : file.getApps().getApps().getList().getApp()) {
            ret.add(a.getRoot());
        }
        return ret;
    }

    public static AppDescription findApp(String name, TamilRootWords file, boolean context) {
        return EzhuththuUtils.findApp(name, file, context);
    }

    public void createAppAs(String code, String name, String as) {
        lock();
        try {
            TamilRootWords file = getAllRootWords();
            AppDescription app = findApp(name, file, false);
            if (app == null) {
                throw new RuntimeException("Source app not found");
            }

            AppDescription target = findApp(as, file, false);

            if (target != null) {
                throw new RuntimeException("Target app already found");
            }
            target = new AppDescription();
            target.setPlatform("1.1");
            target.setName(as);
            target.setRoot(as);
            target.setLibraryDependencies(app.getLibraryDependencies());
            target.setResourceInheritance(app.getResourceInheritance());
            target.setDescription(app.getDescription());
            target.setCode(code == null ? target.getCode() : code);
            target.setResources(new AppResources());
            target.getResources().setWelcome(app.getResources().getWelcome());
            for (AppResource r : app.getResources().getResources()) {
                AppResource rnew = new AppResource();
                rnew.setName(r.getName());
                rnew.setType(r.getType());
                rnew.setContent(r.getContent());
                target.getResources().getResources().add(rnew);

            }
            file.getApps().getApps().getList().getApp().add(target);
            persist(file);

        } finally {
            unlock();
        }


    }


    public void createApp(String code, String name) {
        lock();

        try {
            TamilRootWords file = getAllRootWords();
            if (name == null || name.trim().equals("")) {
                throw new RuntimeException("App name is mandatory!");
            }
            name = name.trim();
            if (name.contains("/") || name.contains("\\") || name.contains(".")) {
                throw new RuntimeException("Invalid application name!. Can not have a slash or a dot.");
            }
            AppDescription app = findApp(name, file, false);
            if (app == null) {
                app = new AppDescription();
                app.setName(name);
                app.setRoot(name);
                app.setCode(code);
                app.setResources(new AppResources());
                file.getApps().getApps().getList().getApp().add(app);
                createResourceToApp(code, name, "index.html");
                updateApp(code, name, "index.html", null, null, "New app (No description yet)", null);
                addOrUpdateResourceToApp(code, name, "index.html", AppResourceType.HTML.toString(), ("<!DOCTYPE html>\n" +
                        "<html xmlns=\"http://www.w3.org/1999/html\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title> New Application:" + name +
                        "    </title> </head> <body>" +
                        "<h1> It is a new Tamil application (\"T{thamizhppayanmi}\") named as ${R_APP_NAME} created at:" + new Date().toString() + "  </h1>" +
                        " </body>\n" +
                        "</html>" +
                        "").getBytes());
                persist(file);
            }

        } finally {
            unlock();
        }

    }

    public void deleteApp(String code, String name) {
        lock();

        try {
            TamilRootWords file = getAllRootWords();
            if (name == null || name.trim().equals("")) {
                throw new RuntimeException("App name is mandatory!");
            }
            AppDescription app = findApp(name, file, false);
            if (app == null) {
                throw new RuntimeException("App not found");
            }
            if (app.getCode() != null) {
                if (!app.getCode().equals(code)) {
                    throw new RuntimeException("Security Code does not match: Access Denied.");
                }
            }
            file.getApps().getApps().getList().getApp().remove(app);
            persist(file);


        } finally {
            unlock();
        }

    }

    public void createResourceToApp(String code, String name, String resource) {
        lock();

        try {
            TamilRootWords file = getAllRootWords();
            if (name == null || name.trim().equals("")) {
                throw new RuntimeException("App name is mandatory!");
            }
            if (resource == null || resource.trim().equals("")) {
                throw new RuntimeException("Resource name is mandatory!");
            }

            AppDescription app = findApp(name, file, false);
            if (app == null) {
                throw new RuntimeException("App not found");
            }


            if (app.getCode() != null) {
                if (!app.getCode().equals(code)) {
                    throw new RuntimeException("Security Code does not match: Access Denied.");
                }
            }

            while (resource.startsWith("/")) {
                resource = resource.substring(1, resource.length());
            }

            while (resource.endsWith("/")) {
                resource = resource.substring(0, resource.length() - 1);
            }

            resource = resource.trim();

            if (resource.equals("")) {
                throw new RuntimeException("Invalid resource name! Empty!");
            }

            resource = resource.replace('\\', '/');

            if (resource.contains("//") || resource.contains("..")) {
                throw new RuntimeException("Invalid resource name!. Can not have multiple slashes together or dots.");
            }


            AppResource res = findAppResourceFromApp(file, app, resource, true);
            if (res == null) {
                res = new AppResource();
                res.setName(resource);
                res.setType(AppResourceType.UNKNOWN);
                res.setContent(new byte[0]);
                app.getResources().getResources().add(res);
                persist(file);

            }


        } finally {
            unlock();
        }

    }

    public String addOrUpdateResourceToApp(String code, String name, String resource, String type, byte[] content) {
        if (content == null) {
            content = new byte[0];
        }
        lock();

        try {
            TamilRootWords file = getAllRootWords();
            if (name == null || name.trim().equals("")) {
                throw new RuntimeException("App name is mandatory!");
            }
            if (resource == null || resource.trim().equals("")) {
                throw new RuntimeException("App name is mandatory!");
            }

            AppDescription app = findApp(name, file, false);
            if (app == null) {
                throw new RuntimeException("App not found");
            }

            if (app.getCode() != null) {
                if (!app.getCode().equals(code)) {
                    throw new RuntimeException("Security Code does not match: Access Denied.");
                }
            }

            AppResource res = findAppResourceFromApp(file, app, resource, true);
            if (res == null) {
                throw new RuntimeException("Resource not found!");
            }
            if (type == null) {
                type = AppResourceType.UNKNOWN.toString();
            }
            //content = StringUtils.replaceForT(new String(content)).getBytes();
            // System.out.println(content);
            res.setContent(content);
            res.setType(AppResourceType.fromValue(type.toUpperCase()));

            persist(file);

            if (AppResourceType.GROOVY == res.getType()) {
                // Parse script
                try {
                    compile(app, res, app.getName() + "." + resource, URLDecoder.decode(new String(content), "UTF-8"), true);
                } catch (Exception e) {

                    return e.getMessage();
                }
            }

            return null;


        } finally {
            unlock();
        }

    }



    public List<String> updateClassloader(String code, String name, String paths) {



        lock();

        try {
            TamilRootWords file = getAllRootWords();
            if (name == null || name.trim().equals("")) {
                throw new RuntimeException("App name is mandatory!");
            }


            AppDescription app = findApp(name, file, false);
            if (app == null) {
                throw new RuntimeException("App not found");
            }


            if (app.getCode() != null) {
                if (!app.getCode().equals(code)) {
                    throw new RuntimeException("Security Code does not match: Access Denied.");
                }
            }

            app.setLibraryDependencies(paths);
            persist(file);
            if (app.getCache() == null) {
                app.setCache(new AppCache());
            }
            return  app.getCache().buildClassloader(app);



        } finally {
            unlock();
        }

    }



   // static final GroovyScriptEngineImpl engine = new GroovyScriptEngineImpl();
    static final Map<String, SoftReference<CompiledScript>> compiledScripts = new HashMap<String, SoftReference<CompiledScript>>();


    /**
     * Compiles only if needed.
     * @param app
     * @param resource
     * @param key
     * @param script
     * @param forceCompile
     * @return
     * @throws ScriptException
     */
    public synchronized static CompiledScript compile(AppDescription app, AppResource resource, String key, String script, boolean forceCompile) throws ScriptException {

        CompiledScript c = null;
        if (!forceCompile) {
            SoftReference<CompiledScript> s = compiledScripts.get(key);

            if (s != null) {
                c = s.get();
            }
        }
        if (c == null) {
            script = StringUtils.replaceForT(script);
            script = StringUtils.replaceFor$(script, new PropertyFinderForResource(app, resource, null), false);
            GroovyScriptEngineImpl shell = (GroovyScriptEngineImpl) app.getCache().getGroovyScriptEngine();
            if (shell == null) {
                app.getCache().setGroovyScriptEngine(new GroovyScriptEngineImpl(new GroovyClassLoader(app.getCache().getAppClassLoader())));
                shell = (GroovyScriptEngineImpl) app.getCache().getGroovyScriptEngine();
            }   else {
                System.out.println("Reusing groovy shell!");
            }
            c = shell.compile(script);
            compiledScripts.put(key, new SoftReference<CompiledScript>(c));
        }
        return c;
    }


    public void updateApp(String code, String name, String welcome, String parents, String inheritanceSearchOrder, String desc, String extrnalurls) {
        lock();

        try {
            TamilRootWords file = getAllRootWords();
            if (name == null || name.trim().equals("")) {
                throw new RuntimeException("App name is mandatory!");
            }


            AppDescription app = findApp(name, file, false);
            if (app == null) {
                throw new RuntimeException("App not found");
            }
            if (app.getCode() != null) {
                if (!app.getCode().equals(code)) {
                    throw new RuntimeException("Security Code does not match: Access Denied.");
                }
            }

            app.setRoot(name);


            if (parents != null) {
                List<String> ps = EzhuththuUtils.parseString(parents);
                for (String p : ps) {
                    if (p.equals(name)) {
                        throw new RuntimeException("Same application  can not be parent for itself.");
                    }
                    if (findApp(p) == null) {
                        throw new RuntimeException("Application '" + p + "' does not exist to inherit anything from!");
                    }
                }
                if (app.getResourceInheritance() == null) {
                    app.setResourceInheritance(new ResourceInheritance());
                }
                app.getResourceInheritance().getParentApps().clear();
                app.getResourceInheritance().getParentApps().addAll(ps);
                app.setCache(new AppCache());
            } else {
                app.setResourceInheritance(null);
                app.setCache(new AppCache());
            }

            if (inheritanceSearchOrder != null) {
                if (app.getResourceInheritance() == null) {
                    app.setResourceInheritance(new ResourceInheritance());
                }
                app.getResourceInheritance().setInheritanceOrder(ResourceInheritanceOrder.fromValue(inheritanceSearchOrder));
            }

            app.getResources().setWelcome(welcome);
            app.setDescription(desc);
            app.getExternalResources().clear();
            if (extrnalurls != null && !extrnalurls.trim().equals("")) {
                List<String> urls = TamilUtils.parseString(extrnalurls);
                for (String url : urls) {
                    ExternalResource ext = new ExternalResource();
                    ext.setUrl(url);
                    app.getExternalResources().add(ext);
                }
            }
            persist(file);


        } finally {
            unlock();
        }

    }

    public void deleteResourceFromApp(String code, String name, String resource) {
        lock();

        try {
            TamilRootWords file = getAllRootWords();
            if (name == null || name.trim().equals("")) {
                throw new RuntimeException("App name is mandatory!");
            }
            if (resource == null || resource.trim().equals("")) {
                throw new RuntimeException("App name is mandatory!");
            }

            AppDescription app = findApp(name, file, false);
            if (app == null) {
                throw new RuntimeException("App not found");
            }
            if (app.getCode() != null) {
                if (!app.getCode().equals(code)) {
                    throw new RuntimeException("Security Code does not match: Access Denied.");
                }
            }

            AppResource res = findAppResourceFromApp(file, app, resource, true);
            if (res == null) {
                throw new RuntimeException("Resource not found!");
            }

            if (app.getResources() != null && resource.equals(app.getResources().getWelcome())) {
                app.getResources().setWelcome(null);
            }

            app.getResources().getResources().remove(res);

            persist(file);


        } finally {
            unlock();
        }

    }

    public static AppResource findAppResource(TamilRootWords file, String name, String resource, boolean local) {
        if (name == null || name.trim().equals("")) {
            throw new RuntimeException("App name is mandatory!");
        }
        if (resource == null || resource.trim().equals("")) {
            throw new RuntimeException("App name is mandatory!");
        }

        AppDescription app = findApp(name, file, false);
        if (app == null) {
            throw new RuntimeException("App not found");
        }
        AppResource res = findAppResourceFromApp(file, app, resource, local);
        return res;
    }



    public AppResource findAppResource(String name, String resource, boolean local) {

        TamilRootWords file = getAllRootWords();
        return findAppResource(file, name, resource, local);


    }


    public AppDescription findApp(String name) {


        TamilRootWords file = getAllRootWords();
        if (name == null || name.trim().equals("")) {
            throw new RuntimeException("App name is mandatory!");
        }

        AppDescription app = findApp(name, file, false);

        return app;


    }

    public AppDescription findAppWithContext(String context) {


        TamilRootWords file = getAllRootWords();
        if (context == null || context.trim().equals("")) {
            throw new RuntimeException("Context path is  mandatory!");
        }

        AppDescription app = findApp(context, file, true);

        return app;


    }


    public String getPeyarProperty(String peyar, String property) {


        PeyarchcholDescription desc = findPeyarchcholDescription(peyar);
        if (desc != null) {
            if (desc.getDescription() == null) {
                desc.setDescription(new Properties());
            }
            for (Property p : desc.getDescription().getProperty()) {
                if (property.equals(p.getName())) {

                    return p.getValue();
                }
            }

        }
        return null;

    }


    public String getIdaiProperty(String peyar, String property) {
        TamilRootWords verbs = getAllRootWords();
        IdaichcholDescription desc = findIdaichcholDescription(peyar);
        if (desc != null) {
            if (desc.getDescription() == null) {
                desc.setDescription(new Properties());
            }
            for (Property p : desc.getDescription().getProperty()) {
                if (property.equals(p.getName())) {

                    return p.getValue();
                }
            }

        }
        return null;

    }

    @Override
    public PeyarchcholDescription findNounDescription(String root) {
        return findPeyarchcholDescription(root);
    }

    @Override
    public ApplicationManager getApplicationManager() {
        return this;
    }

    @Override
    public RootVerbManager getRootVerbManager() {
        return this;
    }

    @Override
    public PrepositionManager getPrepositionManager() {
        return this;
    }

    @Override
    public NounManager getNounManager() {
        return this;
    }

    @Override
    public IdaichcholDescription findPrepositionDescription(String root) {
        return findIdaichcholDescription(root);
    }


}
