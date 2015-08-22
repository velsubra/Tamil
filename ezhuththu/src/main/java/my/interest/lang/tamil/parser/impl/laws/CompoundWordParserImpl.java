package my.interest.lang.tamil.parser.impl.laws;

import common.lang.impl.AbstractCharacter;
import my.interest.lang.tamil.bean.MultipleWordSplitResult;
import my.interest.lang.tamil.bean.SimpleSplitResult;
import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.HandlerFactory;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.api.parser.*;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.thodar.ThodarMozhiBuilder;
import tamil.lang.spi.CompoundWordParserProvider;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class CompoundWordParserImpl implements CompoundWordParser {


    @Override
    public ParserResult quickParse(TamilWord singleWord) {
        ParserResultCollection list = parse(singleWord, 1, null);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.getList().get(0);
    }

    @Override
    public ParserResultCollection parse(TamilWord singleWord, int maxReturn, ParseFeature... features) {
        ParserResultCollection retcollection = new ParserResultCollection();
        if (singleWord == null) return retcollection;

        if (maxReturn <= 0) {
            return retcollection;
        }
        FeatureSet set = new FeatureSet(features);
        ParserResultCollection ret = parsePrivate(singleWord, maxReturn, set);
        TamilDictionary dictionary = set.getDictionary();
        if (dictionary == null) {
            dictionary = TamilFactory.getSystemDictionary();
        }

        if (ret.isEmpty()) {
            if (!singleWord.isEmpty() && set.isFeatureEnabled(ParseFailureFindIndexFeature.class)) {

                TamilWord trial = singleWord.duplicate();
                AbstractCharacter last = trial.removeLast();
                int codepointssize = trial.getCodePointsTotalCount();
                ParserResult.PARSE_HINT hint = null;
                while (!trial.isEmpty()) {
                    ret = parsePrivate(trial, 1, set);
                    if (ret.isEmpty()) {
                        ret = null;

                        List<IKnownWord> knownWords = dictionary.search(trial, false, 1, CompoundWordParserProvider.search);
                        if (!knownWords.isEmpty()) {
                            if (knownWords.get(0).getWord().startsWith(trial, true)) {
                                break;
                            }
                        }
                        last = trial.removeLast();
                        codepointssize -= last.getCodePointsCount();


                    } else {
                        for (ParserResult re : ret.getList()) {
                            re.setParsed(false);
                            re.setParseHint(new ParserResult.PARSE_HINT(trial.size(), trial.size() + 1, codepointssize, codepointssize + last.getCodePointsCount(), null));

                        }
                        break;

                    }
                }
                if (ret == null) {
                    ret = new ParserResultCollection();
                    hint = new ParserResult.PARSE_HINT(trial.size(), trial.size() + 1, codepointssize, codepointssize + last.getCodePointsCount(), null);
                    ret.add(new ParserResult(trial, null, hint));

                }
                return ret;

            }

        }
        return ret;

    }


    private ParserResultCollection parsePrivate(TamilWord singleWord, int maxReturn, FeatureSet set) {


        ParserResultCollection ret = new ParserResultCollection();
        TamilWord pure = singleWord.filterToPure();

        MultipleWordSplitResult result = HandlerFactory.parse(pure.toString(), false, set.isFeatureEnabled(ParseWithUnknownFeature.class), maxReturn);
        List<SimpleSplitResult> list = result.getSplit();


        if (list != null) {
            for (SimpleSplitResult r : list) {
                List<String> splits = r.getSplitList();
                if (splits.size() == 1) {
                    List<IKnownWord> knowns = result.getGivenWord().getMapContext().get(splits.get(0));
                    for (IKnownWord k : knowns) {
                        ParserResult p = new ParserResult(k, true, null);
                        ret.add(p);

                    }
                } else {
                    ThodarMozhiBuilder builder = new ThodarMozhiBuilder();
                    for (String s : splits) {
                        builder.multiplyPathsWithNodes(result.getGivenWord().getMapContext().get(s));
                    }

                    List<List<IKnownWord>> paths = builder.getPaths();
                    for (List<IKnownWord> l : paths) {
                        ParserResult p = new ParserResult(pure, l, null);
                        ret.add(p);
                    }
                }
            }
        }
        return ret;
    }
}