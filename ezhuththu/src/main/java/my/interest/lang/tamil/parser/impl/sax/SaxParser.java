package my.interest.lang.tamil.parser.impl.sax;

import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilWord;
import tamil.lang.api.parser.CompoundWordParser;
import tamil.lang.api.parser.ParseFeature;
import tamil.lang.api.parser.ParserResult;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.thodar.ThodarMozhiBuilder;

import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class SaxParser implements CompoundWordParser {
    private List<TokenRecognizer> recognizers = new ArrayList<TokenRecognizer>() {
        {
            add(new AnyKnownWordMatcher());
            add(new SpecificTokenRecognizer(TamilWord.from("அது")));
            add(new SpecificTokenRecognizer(TamilWord.from("என்று")));

            add(new AththuRecognizer());
            add(new OttuRecognizer(TamilWord.from("க்")));
            add(new OttuRecognizer(TamilWord.from("ச்")));
            add(new OttuRecognizer(TamilWord.from("த்")));
            add(new OttuRecognizer(TamilWord.from("ப்")));

        }
    };


    public ParserResult quickParse(TamilWord singleWord) {
        List<ParserResult> list = parse(singleWord, 1, null);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }


    public List<ParserResult> parse(TamilWord singleWord, int maxReturn, ParseFeature... features) {
        singleWord = singleWord.filterToPure();
        if (singleWord.isEmpty()) {
            return Collections.emptyList();
        }

        List<ParserResult> listToReturn = new ArrayList<ParserResult>();
        parseInternal(listToReturn, singleWord, singleWord, new ArrayList<IKnownWord>());
        return listToReturn;
    }

    private void inlineMethod(TamilWord originalCompoundWord, List<ParserResult> listToReturn, List<TokenRecognizer> rec, List<IKnownWord> tail, TamilWordPartContainer nilaimozhi, TamilWordPartContainer  varumozhi) {
        Map<TokenMatcherResult.MATCHING_STATUS, List<TokenMatcherResult>> statusmap = parse(nilaimozhi, varumozhi, tail, rec);
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
                        baseChcker.addIntoExistingPaths(tail);
                        for (List<IKnownWord> path : baseChcker.getPaths()) {
                            TokenMatcherResult result = matcher.match(new TamilWordPartContainer(new TamilWord()), m, path);
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
                        builder.addIntoExistingPaths(tail);
                        for (List<IKnownWord> path : builder.getPaths()) {
                            ParserResult parserResult = new ParserResult(originalCompoundWord, path, null);
                            if (!listToReturn.contains(parserResult)) {
                                listToReturn.add(parserResult);
                            }
                        }


                    } else {
                        ThodarMozhiBuilder builder = new ThodarMozhiBuilder();
                        builder.multiplyPathsWithNodes(match.getMatchedWords());
                        builder.addIntoExistingPaths(tail);
                        for (List<IKnownWord> path : builder.getPaths()) {
                            parseInternal(listToReturn, originalCompoundWord, m.getWord(), path);

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


    private void parseInternal(List<ParserResult> listToReturn, TamilWord originalCompoundWord, TamilWord singleWord, List<IKnownWord> tail) {

        TamilWord varumozhi = new TamilWord();
        TamilWord nilaimozhi = singleWord.duplicate();
        List<TokenRecognizer> rec = new ArrayList<TokenRecognizer>(recognizers);
        while (!nilaimozhi.isEmpty()) {
            TamilCharacter t = nilaimozhi.removeLast().asTamilCharacter();
            if (t.isUyirMeyyezhuththu()) {
                varumozhi.addFirst(t.getUyirPart());
                nilaimozhi.addLast(t.getMeiPart());

                // will repeat outside  ; this is for vowel seperation

                inlineMethod(originalCompoundWord, listToReturn, rec, tail, new TamilWordPartContainer(nilaimozhi), new TamilWordPartContainer(varumozhi));


                nilaimozhi.removeLast();
                varumozhi.removeFirst();

            }

            varumozhi.addFirst(t);
            if (!rec.isEmpty()) {
                //This is for character separation
                inlineMethod(originalCompoundWord, listToReturn, rec, tail, new TamilWordPartContainer( nilaimozhi), new TamilWordPartContainer(varumozhi));
            }

            if (rec.isEmpty()) {
                break;
            }
//            if (!listToReturn.isEmpty()) {
//                break;
//            }


        }


    }

    private Map<TokenMatcherResult.MATCHING_STATUS, List<TokenMatcherResult>> parse(TamilWordPartContainer nilai, TamilWordPartContainer varum, List<IKnownWord> tail, List<TokenRecognizer> rec) {
        Map<TokenMatcherResult.MATCHING_STATUS, List<TokenMatcherResult>> map = new HashMap<TokenMatcherResult.MATCHING_STATUS, List<TokenMatcherResult>>();
        for (TokenRecognizer token : rec) {
            TokenMatcherResult result = token.matchRoot(nilai, varum, tail);
            result.tokenRecognizerCache = token;
            List<TokenMatcherResult> list = map.get(result.getStatus());
            if (list == null) {
                list = new ArrayList<TokenMatcherResult>();
                map.put(result.getStatus(), list);
            }
            list.add(result);
        }
        return map;
    }

}
