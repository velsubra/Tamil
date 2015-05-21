package my.interest.lang.tamil.impl.dictionary;

import my.interest.lang.tamil.impl.FeatureSet;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.dictionary.*;
import tamil.lang.api.feature.FeatureConstants;
import tamil.lang.api.join.WordsJoiner;
import tamil.lang.api.parser.CompoundWordParser;
import tamil.lang.api.parser.ParserResult;
import tamil.lang.api.parser.ParserResultCollection;
import tamil.lang.exception.TamilPlatformException;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.derived.VinaiyadiDerivative;
import tamil.lang.known.non.derived.*;

import java.util.*;
import java.util.logging.Logger;

/**
 * <p>
 * Provides a basic implementation of a Tamil Dictionary.
 * </p>
 *
 * @author velsubra
 */
public abstract class DefaultPlatformDictionaryBase implements TamilDictionary {

    static final Logger logger = Logger.getLogger(DefaultPlatformDictionaryBase.class.getName());

    public DefaultPlatformDictionaryBase() {

    }

    public int size() {

        return set.size();
    }


    public IKnownWord peek(IKnownWord known) {
        List<Class<? extends IKnownWord>> list = new ArrayList<Class<? extends IKnownWord>>();
        list.add(known.getClass());
        List<IKnownWord> listRet = search(known.getWord(), true, 1, list);
        if (listRet.isEmpty()) {
            return null;
        } else {
            return listRet.get(0);
        }
    }

    private static final Comparator<IKnownWord> REVERSED = new Comparator<IKnownWord>() {

        public int compare(IKnownWord o1, IKnownWord o2) {
            return TamilWord.reverse(o1.getWord()).compareTo(TamilWord.reverse(o2.getWord()));
//            int ret = o1.compareTo(o2);
//            if (ret == 0) {
//                return 0;
//            } else {
//                int sub = TamilWord.reverse(o1.getWord()).compareTo(TamilWord.reverse(o2.getWord()));
//                if (sub == 0) {
//                    return ret;
//                } else {
//                    return sub;
//                }
//            }
        }
    };

    private static final Comparator<IKnownWord> DIRECT = new Comparator<IKnownWord>() {
        public int compare(IKnownWord o1, IKnownWord o2) {
            return o1.compareTo(o2);
        }
    };

    protected final SortedSet<IKnownWord> set = Collections.synchronizedSortedSet(new TreeSet<IKnownWord>(DIRECT));
    protected final SortedSet<IKnownWord> reversedset = Collections.synchronizedSortedSet(new TreeSet<IKnownWord>(REVERSED));
    protected static final Map<Integer, List<IKnownWord>> suggestions = new HashMap<Integer, List<IKnownWord>>();
    protected static final Map<String, List<IKnownWord>> english_mapping = Collections.synchronizedMap(new HashMap<String, List<IKnownWord>>());

    private static CompoundWordParser parser = null;


//    protected List<IKnownWord> findMatchingDerivedWords(String start, int max, List<Class<? extends IKnownWord>> includeTypes) {
//        List<IKnownWord> ret = findMatchingDerivedWords(start, false, max, includeTypes);
//        return ret;
//    }

//    protected List<IKnownWord> findMatchingDerivedWords(String start, boolean exact, int max, List<Class<? extends IKnownWord>> includeTypes) {
//        TamilWord search = EnglishToTamilCharacterLookUpContext.getBestMatch(start);
//        return findMatchingDerivedWords(search, exact, max, includeTypes);
//    }

    private List<IKnownWord> findMatchingDerivedWords(TamilWord search, boolean exact, int max, List<Class<? extends IKnownWord>> includeTypes) {

        return findMatchingDerivedWords(set, search, exact, max, includeTypes);
    }

    private static List<IKnownWord> findMatchingDerivedWords(SortedSet<IKnownWord> thisset, TamilWord start, boolean exact, int max, List<Class<? extends IKnownWord>> includeTypes) {
        FeatureSet set = exact ? new FeatureSet(FeatureConstants.DICTIONARY_EXACT_MATCH_VAL_160) : FeatureSet.EMPTY;
        return findMatchingDerivedWords(thisset, start, max, includeTypes, set);
    }


    protected static List<IKnownWord> findMatchingDerivedWords(SortedSet<IKnownWord> thiset, TamilWord search, int max, List<Class<? extends IKnownWord>> includeTypes, FeatureSet set) {
        // System.out.println(start);
        boolean exact = set.isFeatureEnabled(ExactMatchSearch.class);
        boolean startwithHigerlength = exact ? false : set.isFeatureEnabled(StartsWithHigherLengthSearch.class);
        boolean suggest = set.isFeatureEnabled(AutoSuggestFeature.class);
        boolean reversed = set.isFeatureEnabled(ReverseSearchFeature.class);
        List<IKnownWord> ret = new ArrayList<IKnownWord>();
        if (search == null || thiset.isEmpty() || max <= 0) return ret;


        Comparator<? super IKnownWord> comparator = thiset.comparator();
        synchronized (thiset) {
            IKnownWord base = new Theriyaachchol(search);
            Iterator<IKnownWord> sub = thiset.tailSet(base).iterator();

            if (max < 0) {
                max = -max;
            }
            while (max > 0) {

                if (sub.hasNext()) {
                    IKnownWord re = sub.next();
                    boolean add = false;

                    if (!suggest) {
                        if (exact) {
                            add = re.getWord().equals(search);
                        } else {
                            if (reversed) {
                                add = re.getWord().endsWith(search, false);
                            } else {
                                add = re.getWord().startsWith(search, false);
                            }

                        }
                    } else {
                        if (comparator == null) {
                            throw new RuntimeException("Comp is null");
                        }
                        //   System.out.println("Candidate:" + re.getWord().toString());
                        add = comparator.compare(re, base) >= 0;
                    }
                    if (add) {
                        if (includeTypes == null || includeTypes.isEmpty() || isInIncludeTypes(includeTypes, re.getClass())) {
                            if (!startwithHigerlength || re.getWord().size() > search.size()) {
                                max--;
                                ret.add(re);
                            }
                        }
                    } else {


                        break;
                    }
                } else {
                    break;
                }

            }
        }

        return ret;
    }


    private static boolean isInIncludeTypes(List<Class<? extends IKnownWord>> list, Class<? extends IKnownWord> cls) {
        for (Class kn : list) {
            if (kn.isAssignableFrom(cls)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public List<IKnownWord> lookup(TamilWord word) {
        return findMatchingDerivedWords(word, true, 10, null);
    }

    @Override
    public IKnownWord peek(TamilWord word) {
        List<IKnownWord> list = findMatchingDerivedWords(word, true, 1, null);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<IKnownWord> search(TamilWord word, boolean exactMatch, int maxCount, List<Class<? extends IKnownWord>> includeTypes) {
        return findMatchingDerivedWords(word, exactMatch, maxCount, includeTypes);
    }


    @Override
    public List<IKnownWord> search(final TamilWord word, int maxCount, List<Class<? extends IKnownWord>> includeTypes, DictionaryFeature... features) {
        FeatureSet set = new FeatureSet(features);
        boolean reversed = set.isFeatureEnabled(ReverseSearchFeature.class);
        List<IKnownWord> list = findMatchingDerivedWords(reversed ? this.reversedset : this.set, word, maxCount, includeTypes, set);


        boolean suggest = set.isFeatureEnabled(AutoSuggestFeature.class);
        if (suggest) {
            List<IKnownWord> ret = new ArrayList<IKnownWord>();
            Set<String> unique = new java.util.HashSet<String>();
            for (IKnownWord ik : list) {
                String str = ik.getWord().toString();
                if (unique.contains(str)) {
                    continue;
                } else {
                    ret.add(ik);
                    unique.add(str);

                }
            }

            //Parsing and creating new thodarmozhi
            if (ret.isEmpty()) {
                if (parser == null) {
                    parser = TamilFactory.getCompoundWordParser();
                }
                ParserResultCollection parsedlist = parser.parse(word, 3, FeatureConstants.PARSE_WITH_UNKNOWN_VAL_170);
                //Parse
                if (parsedlist != null && !parsedlist.isEmpty()) {
                    for (int k = 0; k < parsedlist.size(); k++) {
                        if (ret.size() == maxCount) break;
                        ParserResult parsed = parsedlist.getList().get(k);
                        if (parsed.getSplitWords().size() > 1) {
                            IKnownWord first = parsed.getSplitWords().get(0);
                            IKnownWord last = parsed.getSplitWords().get(parsed.getSplitWords().size() - 1);
                            if (!IThiribu.class.isAssignableFrom(first.getClass()) && (IBasePeyar.class.isAssignableFrom(first.getClass()) || IBaseVinai.class.isAssignableFrom(first.getClass()))) {
                                List<IKnownWord> lastPossibles = search(last.getWord(), false, maxCount, null);
                                if (!lastPossibles.isEmpty()) {
                                    WordsJoiner joiner = TamilFactory.createWordJoiner(first.getWord());
                                    for (int i = 1; i < parsed.getSplitWords().size() - 1; i++) {
                                        joiner.addVaruMozhi(parsed.getSplitWords().get(i).getWord());
                                    }
                                    TamilWord partialsum = joiner.getSum();
                                    for (IKnownWord l : lastPossibles) {
                                        WordsJoiner appender = TamilFactory.createWordJoiner(partialsum);
                                        appender.addVaruMozhi(l.getWord());
                                        ret.add(new Theriyaachchol(appender.getSum()));
                                        if (ret.size() == maxCount) break;
                                    }

                                }
                            }
                        }
                    }
                }
            }

            ret.addAll(suggest(word, maxCount - ret.size(), includeTypes));
            list = ret;

            ret = new ArrayList<IKnownWord>();
            unique.clear();
            for (IKnownWord ik : list) {
                String str = ik.getWord().toString();
                if (unique.contains(str)) {
                    continue;
                } else {
                    ret.add(ik);
                    unique.add(str);

                }
            }

            list = ret;

            Collections.sort(list, new Comparator<IKnownWord>() {
                @Override
                public int compare(IKnownWord o1, IKnownWord o2) {
                    boolean onesuggestionmatches = o1.getWord().suggestionHashCode() == word.suggestionHashCode();
                    boolean twosuggestionmatches = o2.getWord().suggestionHashCode() == word.suggestionHashCode();
                    if (onesuggestionmatches) {
                        if (!twosuggestionmatches) {
                            return -1;
                        }
                    } else if (twosuggestionmatches) {
                        return 1;
                    }


                    boolean onestarts = o1.getWord().startsWith(word, false);
                    boolean twostarts = o2.getWord().startsWith(word, false);
                    if (onestarts) {
                        if (twostarts) {
                            int lengthdiff = o1.getWord().size() - o2.getWord().size();
                            if (lengthdiff == 0) return o1.compareTo(o2);
                            else return lengthdiff;
                        } else {
                            return -1;
                        }
                    } else {
                        if (twostarts) {
                            return 1;
                        } else {
                            int lengthdiff = o1.getWord().size() - o2.getWord().size();
                            if (lengthdiff == 0) return o1.compareTo(o2);
                            else return lengthdiff;
                        }
                    }


                }
            });
        }


        return list;
    }


    public IKnownWord peekEnglish(String english) {
        TamilWord word = lookupEnglish(english);
        if (word == null) return null;
        return peek(word);
    }


    @Override
    public List<IKnownWord> suggest(TamilWord word, int maxCount, List<Class<? extends IKnownWord>> includeTypes) {
        return onlySuggestMatchingDerivedWords(word, maxCount, includeTypes);
    }

    /**
     * Adds a new word to the dictionary.
     *
     * @param word the known word to be added
     */
    @Override
    public void add(IKnownWord word) {
        addKnown(word);
    }


    protected void addKnown(IKnownWord w) {
        if (ITheriyaachchol.class.isAssignableFrom(w.getClass())) {
            throw new TamilPlatformException(w + ":" + w.getClass().getName() + " can not be added");
        }

        w.getWord().setLocked();
        set.add(w);
        reversedset.add(w);

        synchronized (suggestions) {
            int code = w.getWord().suggestionHashCode();
            List<IKnownWord> linked = suggestions.get(code);
            if (linked == null) {
                linked = new LinkedList<IKnownWord>();
                suggestions.put(code, linked);

            }
            if (!linked.contains(w)) {
                linked.add(w);
            }
        }

        if (VinaiyadiDerivative.class.isAssignableFrom(w.getClass())) {
            TamilDictionary related = ((VinaiyadiDerivative) w).getVinaiyadi().getRelatedDictionary();
            if (related != this) {
                related.add(w);
            }
        }

        if (w.getWord().toString().equals("அத்து")) {
            System.out.println("Added ------------------------------:" + w);
        }


        // vowelset.appendNodesToAllPaths(w);
    }


    protected void removeKnown(IKnownWord w) {
        if (w.getWord().toString().equals("அத்து")) {
            System.out.println("Removing ------------------------------:" + w);
            new RuntimeException("removing ").printStackTrace();
            ;
        }
        set.remove(w);
        reversedset.remove(w);
        synchronized (suggestions) {
            int code = w.getWord().suggestionHashCode();
            List<IKnownWord> linked = suggestions.get(code);
            if (linked != null) {
                linked.remove(w);

            }
        }


    }


    private static TamilWord lookupEnglish(String eng) {
        List<IKnownWord> list = english_mapping.get(eng);
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(0).getWord();
        }
    }


    protected static List<IKnownWord> onlySuggestMatchingDerivedWords(TamilWord search, int max, List<Class<? extends IKnownWord>> includeTypes) {

        List<IKnownWord> list = suggestions.get(search.suggestionHashCode());
        if (list == null) {
            list = new ArrayList<IKnownWord>();
        }

        while (list.size() > max) {
            list.remove(list.size() - 1);
        }

        return list;
    }

}
