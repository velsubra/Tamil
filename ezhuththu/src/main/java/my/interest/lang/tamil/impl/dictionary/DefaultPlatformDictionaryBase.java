package my.interest.lang.tamil.impl.dictionary;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.translit.EnglishToTamilCharacterLookUpContext;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.dictionary.AutoSuggestFeature;
import tamil.lang.api.dictionary.DictionaryFeature;
import tamil.lang.api.dictionary.ExactMatchSearch;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.api.feature.FeatureConstants;
import tamil.lang.api.join.WordsJoiner;
import tamil.lang.api.parser.CompoundWordParser;
import tamil.lang.api.parser.ParserResult;
import tamil.lang.known.IKnownWord;
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

    static final Logger logger  = Logger.getLogger(DefaultPlatformDictionaryBase.class.getName());

    public DefaultPlatformDictionaryBase() {

    }

    protected static final SortedSet<IKnownWord> set = Collections.synchronizedSortedSet(new TreeSet<IKnownWord>());
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

    protected List<IKnownWord> findMatchingDerivedWords(TamilWord search, boolean exact, int max, List<Class<? extends IKnownWord>> includeTypes) {

        return findMatchingDerivedWords(set, search, exact, max, includeTypes);
    }

    protected static List<IKnownWord> findMatchingDerivedWords(SortedSet<IKnownWord> thisset, TamilWord start, boolean exact, int max, List<Class<? extends IKnownWord>> includeTypes) {
        return findMatchingDerivedWords(thisset, start, exact, max, includeTypes, false);
    }


    protected static List<IKnownWord> findMatchingDerivedWords(SortedSet<IKnownWord> thiset, TamilWord search, boolean exact, int max, List<Class<? extends IKnownWord>> includeTypes, boolean suggest) {
        // System.out.println(start);
        List<IKnownWord> ret = new ArrayList<IKnownWord>();
        if (search == null || thiset.isEmpty() || max <= 0) return ret;


        Comparator<? super IKnownWord> comparator = thiset.comparator();
        synchronized (thiset) {
            IKnownWord base = new AbstractKnownWord(search) {
            };
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
                            add = re.getWord().startsWith(search, false);
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
                            max--;
                            ret.add(re);
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
        return findMatchingDerivedWords(word, true, 1, null);
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
        boolean exactMatch = set.isFeatureEnabled(ExactMatchSearch.class);
        List<IKnownWord> list = search(word, exactMatch, maxCount, includeTypes);


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
                List<ParserResult> parsedlist = parser.parse(word, 3, FeatureConstants.PARSE_WITH_UNKNOWN_VAL_170);
                //Parse
                if (parsedlist != null && !parsedlist.isEmpty()) {
                    for (int k = 0; k < parsedlist.size(); k++) {
                        if (ret.size() == maxCount) break;
                        ParserResult parsed = parsedlist.get(k);
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


        w.getWord().setLocked();
        set.add(w);

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


        // vowelset.appendNodesToAllPaths(w);
    }



    protected  void removeKnown(IKnownWord w) {

        set.remove(w);
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
