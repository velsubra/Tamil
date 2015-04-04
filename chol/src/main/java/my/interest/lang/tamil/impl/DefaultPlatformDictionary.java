package my.interest.lang.tamil.impl;

import my.interest.lang.tamil.internal.api.PersistenceInterface;
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
import tamil.lang.known.non.derived.IBasePeyar;
import tamil.lang.known.non.derived.IBaseVinai;
import tamil.lang.known.non.derived.IThiribu;
import tamil.lang.known.non.derived.Theriyaachchol;
import tamil.lang.spi.TamilDictionaryProvider;

import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class DefaultPlatformDictionary implements TamilDictionary, TamilDictionaryProvider {

    public DefaultPlatformDictionary() {

    }

    private static CompoundWordParser parser = null;

    public static final DefaultPlatformDictionary dict = new DefaultPlatformDictionary();


    @Override
    public List<IKnownWord> lookup(TamilWord word) {
        return PersistenceInterface.findMatchingDerivedWords(word, true, -1, null);
    }


    @Override
    public IKnownWord peek(TamilWord word) {
        List<IKnownWord> list = PersistenceInterface.findMatchingDerivedWords(word, true, 1, null);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<IKnownWord> search(TamilWord word, boolean exactMatch, int maxCount, List<Class<? extends IKnownWord>> includeTypes) {
        return PersistenceInterface.findMatchingDerivedWords(word, exactMatch, maxCount, includeTypes);
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
                           return  -1;
                        }
                    }  else if (twosuggestionmatches) {
                        return  1;
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

    @Override
    public IKnownWord peekEnglish(String english) {
        TamilWord word = PersistenceInterface.lookupEnglish(english);
        if (word == null) return null;
        return peek(word);
    }


    @Override
    public List<IKnownWord> suggest(TamilWord word, int maxCount, List<Class<? extends IKnownWord>> includeTypes) {
        return PersistenceInterface.onlySuggestMatchingDerivedWords(word, maxCount, includeTypes);
    }

    @Override
    public TamilDictionary create() {
        return this;
    }
}
