package my.interest.lang.tamil.impl;

import common.lang.impl.AbstractCharacter;
import my.interest.lang.tamil.bean.MultipleWordSplitResult;
import my.interest.lang.tamil.bean.SimpleSplitResult;
import my.interest.lang.tamil.internal.api.HandlerFactory;
import my.interest.lang.tamil.parser.impl.sax.SaxParser;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.parser.*;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.IBasePeyar;
import tamil.lang.known.non.derived.IBaseVinai;
import tamil.lang.known.thodar.ThodarMozhiBuilder;
import tamil.lang.spi.CompoundWordParserProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class CompoundWordParserProviderImpl implements CompoundWordParserProvider {

    static final CompoundWordParserImpl imp = new CompoundWordParserImpl();

    private static List<Class<? extends IKnownWord>> search = new ArrayList<Class<? extends IKnownWord>>() {
        {
            add(IBasePeyar.class);
            add(IBaseVinai.class);
        }

    };

    /**
     * Creates a new parser.
     *
     * @return the compound parser.
     */
    @Override
    public CompoundWordParser crate() {
        return imp;
       // return new SaxParser();
    }

    static final class CompoundWordParserImpl implements CompoundWordParser {


        @Override
        public ParserResult quickParse(TamilWord singleWord) {
            List<ParserResult> list = parse(singleWord, 1, null);
            if (list == null || list.isEmpty()) {
                return null;
            }
            return list.get(0);
        }

        @Override
        public List<ParserResult> parse(TamilWord singleWord, int maxReturn, ParseFeature... features) {
            if (singleWord == null) return Collections.emptyList();

            if (maxReturn <= 0) {
                return Collections.emptyList();
            }
            FeatureSet set = new FeatureSet(features);
            List<ParserResult> ret = parsePrivate(singleWord, maxReturn, set);

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
                            List<IKnownWord> knownWords = TamilFactory.getSystemDictionary().search(trial, false, 1, search);
                            if (!knownWords.isEmpty()) {
                                if (knownWords.get(0).getWord().startsWith(trial, true)) {
                                    break;
                                }
                            }
                            last = trial.removeLast();
                            codepointssize -= last.getCodePointsCount();


                        } else {
                            for (ParserResult re : ret) {
                                re.setParsed(false);
                                re.setParseHint(new ParserResult.PARSE_HINT(trial.size(), trial.size() + 1, codepointssize, codepointssize + last.getCodePointsCount(), null));

                            }
                            break;

                        }
                    }
                    if (ret == null) {
                        ret = new ArrayList<ParserResult>();
                        hint = new ParserResult.PARSE_HINT(trial.size(), trial.size() + 1, codepointssize, codepointssize + last.getCodePointsCount(), null);
                        ret.add(new ParserResult(trial, null, hint));

                    }
                    return ret;

                }

            }
            return ret;

        }


        private List<ParserResult> parsePrivate(TamilWord singleWord, int maxReturn, FeatureSet set) {


            List<ParserResult> ret = null;
            TamilWord pure = singleWord.filterToPure();

            MultipleWordSplitResult result = HandlerFactory.parse(pure.toString(), false, set.isFeatureEnabled(ParseWithUnknownFeature.class), maxReturn);
            List<SimpleSplitResult> list = result.getSplit();

            ret = new ArrayList<ParserResult>();
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
}
