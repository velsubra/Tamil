package my.interest.lang.tamil.parser.impl.sax;

import common.lang.impl.AbstractCharacter;
import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.NumberDictionary;
import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.api.parser.*;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.idai.Ottu;
import tamil.lang.known.non.derived.idai.Um;
import tamil.lang.known.thodar.ThodarMozhiBuilder;
import tamil.lang.spi.CompoundWordParserProvider;

import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class SaxParser implements CompoundWordParser {

    static final TamilWord AAGUM = TamilWord.from("ஆகும்");
    static final TamilWord ENRRU = TamilWord.from("என்று");
    static final TamilWord ENAPTHU = TamilWord.from("என்பது");



    public SaxParser() {
        recognizers = new ArrayList<TokenRecognizer>() {
            {
                //Order is important; smaller first

                add(new SpecificTokenRecognizer(Um.UM.getWord()));
                add(new SpecificTokenRecognizer(AAGUM));
                add(new SpecificTokenRecognizer(ENRRU));
                add(new SpecificTokenRecognizer(ENAPTHU));
                add(new AththuRecognizer());
                add(new OttuRecognizer(Ottu.IK.getWord()));
                add(new OttuRecognizer(Ottu.ICH.getWord()));
                add(new OttuRecognizer(Ottu.ITH.getWord()));
                add(new OttuRecognizer(Ottu.IP.getWord()));

            }
        };
    }

    private List<TokenRecognizer> recognizers = null;
    private  AnyKnownWordMatcher anywordmatcher =  new AnyKnownWordMatcher();


    public ParserResult quickParse(TamilWord singleWord) {
        ParserResultCollection list = parse(singleWord, 10, null);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.getList().get(0);
    }


    public ParserResultCollection parse(TamilWord singleWord, int maxReturn, ParseFeature... features) {
        ParserResultCollection collection = new ParserResultCollection();
        FeatureSet set = features == null ? FeatureSet.EMPTY : new FeatureSet(features);
        TamilDictionary dictionary =  set.getDictionary();

//        TamilDictionary dictionary = set.isFeatureEnabled(ParseAsNumberFeature.class) ? NumberDictionary.INSTANCE : null;
//        if (dictionary == null) {
//            dictionary = set.isFeatureEnabled(ParseAsNumberFeature.class) ? NumberDictionary.INSTANCE : null;
//        }
        if (dictionary == null) {
            dictionary  = TamilFactory.getSystemDictionary();
        }


        singleWord = singleWord.filterToPure();
        if (singleWord.isEmpty()) {
            return collection;
        }
        if (set.isFeatureEnabled(ParseWithUnknownFeature.class)) {
            recognizers.add(new SpellingMistakeFinder());
            maxReturn = 15;
        }

        parseInternal(maxReturn, collection, singleWord, ParsingContext.contextFor(new TamilWordPartContainer(singleWord), null, new ArrayList<IKnownWord>(), dictionary, set));

        if (collection.isEmpty()) {
            if (!singleWord.isEmpty() && set.isFeatureEnabled(ParseFailureFindIndexFeature.class)) {
                TamilWord trial = singleWord.duplicate();
                AbstractCharacter last = trial.removeLast();
                int codepointssize = trial.getCodePointsTotalCount();
                ParserResult.PARSE_HINT hint = null;
                while (!trial.isEmpty()) {
                    parseInternal(1,  collection, trial, ParsingContext.contextFor(new TamilWordPartContainer(trial), null , new ArrayList<IKnownWord>(),dictionary,set));

                    if (collection.isEmpty()) {

                        List<IKnownWord> knownWords = dictionary.search(trial, false, 1, CompoundWordParserProvider.search);
                        if (!knownWords.isEmpty()) {
                            if (knownWords.get(0).getWord().startsWith(trial, true)) {
                                break;
                            }
                        }
                        last = trial.removeLast();
                        codepointssize -= last.getCodePointsCount();


                    } else {
                        for (ParserResult re : collection.getList()) {
                            re.setParsed(false);
                            re.setParseHint(new ParserResult.PARSE_HINT(trial.size(), trial.size() + 1, codepointssize, codepointssize + last.getCodePointsCount(), null));

                        }
                        break;

                    }
                }
                if (collection.isEmpty()) {

                    hint = new ParserResult.PARSE_HINT(trial.size(), trial.size() + 1, codepointssize, codepointssize + last.getCodePointsCount(), null);
                    collection.add(new ParserResult(trial, null, hint));
                    if (collection.size() == maxReturn) {
                        return collection;
                    }

                }

            }
        }

        return collection;
    }

    private void inlineMethod(int maxReturn,  TamilWord originalCompoundWord, ParserResultCollection listToReturn, List<TokenRecognizer> rec, ParsingContext context) {
        Map<TokenMatcherResult.MATCHING_STATUS, List<TokenMatcherResult>> statusmap = parseReturnChance( rec, context);
        List<TokenMatcherResult> matching = statusmap.get(TokenMatcherResult.MATCHING_STATUS.MATCHING);


        rec.clear();
        if (matching != null) {
            for (TokenMatcherResult match : matching) {
                rec.add(match.tokenRecognizerCache);
                for (TamilWordPartContainer m : match.getNilaiMozhi()) {
                    List<IKnownWord> bases = new ArrayList<IKnownWord>();

                    if (m.size() > 0) {
                        AnyKnownWordMatcher matcher = new AnyKnownWordMatcher(true);
                        ThodarMozhiBuilder baseChcker = new ThodarMozhiBuilder();
                        baseChcker.multiplyPathsWithNodes(match.getMatchedWords());
                        baseChcker.addIntoExistingPaths(context.tail);
                        for (List<IKnownWord> path : baseChcker.getPaths()) {
                            TokenMatcherResult result = matcher.matchRoot(ParsingContext.contextFor(new TamilWordPartContainer(new TamilWord()), m, path, context.dictionary, context.set));
                            if (result.isMatching()) {
                                bases.addAll(result.getMatchedWords());
                            }

                        }
                    }

                    if (!bases.isEmpty() || m.size() == 0) {
                        //This implies parsing is over.
                        ThodarMozhiBuilder builder = new ThodarMozhiBuilder();
                        builder.multiplyPathsWithNodes(bases);
                        builder.multiplyPathsWithNodes(match.getMatchedWords());
                        builder.addIntoExistingPaths(context.tail);
                        for (List<IKnownWord> path : builder.getPaths()) {
                            ParserResult parserResult = new ParserResult(originalCompoundWord, path, null);
                            if (!listToReturn.contains(parserResult)) {
                                listToReturn.add(parserResult);
                                if (listToReturn.size() == maxReturn) {
                                    return;
                                }
                            }
                        }


                    } else {
                        ThodarMozhiBuilder builder = new ThodarMozhiBuilder();
                        builder.multiplyPathsWithNodes(match.getMatchedWords());
                        builder.addIntoExistingPaths(context.tail);
                        for (List<IKnownWord> path : builder.getPaths()) {
                            parseInternal(maxReturn, listToReturn, originalCompoundWord, ParsingContext.contextFor( m, null, path, context.dictionary,context.set));

                        }
                    }
                }
            }
        }


        List<TokenMatcherResult> conts = statusmap.get(TokenMatcherResult.MATCHING_STATUS.CONTINUE);
        if (conts != null) {
            for (TokenMatcherResult cont : conts) {
                rec.add(cont.tokenRecognizerCache);
            }
        }

    }

    /**
     *
     * @param maxReturn
     * @param listToReturn
     * @param originalCompoundWord   the original compound word being parsed.
     * @param context  its varumozhi will be null. This starts a fresh parsing.
     */
    void parseInternal(int maxReturn,  ParserResultCollection listToReturn, TamilWord originalCompoundWord,ParsingContext context) {

        TamilWord varumozhi = new TamilWord();
        TamilWord nilaimozhi = context.nilaimozhi.getWord().duplicate();
        List<TokenRecognizer> rec = new ArrayList<TokenRecognizer>(recognizers);
        while (!nilaimozhi.isEmpty()) {
            TamilCharacter t = nilaimozhi.removeLast().asTamilCharacter();
            if (t.isUyirMeyyezhuththu()) {
                varumozhi.addFirst(t.getUyirPart());
                nilaimozhi.addLast(t.getMeiPart());

                // will repeat outside  ; this is for vowel seperation
                if (listToReturn.size() == maxReturn) {
                    break;
                }

                inlineMethod(maxReturn, originalCompoundWord, listToReturn, rec, ParsingContext.contextFor(new TamilWordPartContainer(nilaimozhi), new TamilWordPartContainer(varumozhi), context.tail, context.dictionary, context.set));


                nilaimozhi.removeLast();
                varumozhi.removeFirst();

            }
            if (listToReturn.size() == maxReturn) {
                break;
            }


            varumozhi.addFirst(t);
            if (!rec.isEmpty()) {
                //This is for character separation
                inlineMethod(maxReturn, originalCompoundWord, listToReturn, rec, ParsingContext.contextFor( new TamilWordPartContainer(nilaimozhi), new TamilWordPartContainer(varumozhi),context.tail,context.dictionary,context.set));

            }

            if (rec.isEmpty()) {
                break;
            }


        }


    }

    private Map<TokenMatcherResult.MATCHING_STATUS, List<TokenMatcherResult>> parseReturnChance( List<TokenRecognizer> rec, ParsingContext context) {
        Map<TokenMatcherResult.MATCHING_STATUS, List<TokenMatcherResult>> map = new HashMap<TokenMatcherResult.MATCHING_STATUS, List<TokenMatcherResult>>();
        for (TokenRecognizer token : rec) {
            if (anywordmatcher == token) {
                 continue;
            }

            TokenMatcherResult result = token.matchRoot(context);
            if (result.getStatus() == TokenMatcherResult.MATCHING_STATUS.DISCONTINUE) continue;

            result.tokenRecognizerCache = token;
            List<TokenMatcherResult> list = map.get(result.getStatus());
            if (list == null) {
                list = new ArrayList<TokenMatcherResult>();
                map.put(result.getStatus(), list);
            }
            list.add(result);
          //  break;
//
//            if (result.getStatus() == TokenMatcherResult.MATCHING_STATUS.CONTINUE) {
//                break;
//            }
//
//            if (result.getStatus() == TokenMatcherResult.MATCHING_STATUS.MATCHING) {
//                break;
//            }
        }
        if (map.isEmpty()) {
            TokenMatcherResult result = anywordmatcher.matchRoot(context);
            if (result.getStatus() != TokenMatcherResult.MATCHING_STATUS.DISCONTINUE) {
                result.tokenRecognizerCache = anywordmatcher;
                List<TokenMatcherResult> list = new ArrayList<TokenMatcherResult>();
                list.add(result);
                map.put(result.getStatus(), list);
            }
        }
        return map;
    }

}
