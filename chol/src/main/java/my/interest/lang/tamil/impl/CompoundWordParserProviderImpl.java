package my.interest.lang.tamil.impl;

import my.interest.lang.tamil.bean.MultipleWordSplitResult;
import my.interest.lang.tamil.bean.SimpleSplitResult;
import my.interest.lang.tamil.internal.api.HandlerFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.parser.CompoundWordParser;
import tamil.lang.api.parser.ParseFeature;
import tamil.lang.api.parser.ParseWithUnknownFeature;
import tamil.lang.api.parser.ParserResult;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.thodar.ThodarMozhiBuilder;
import tamil.lang.spi.CompoundWordParserProvider;
import tamil.util.PathBuilder;

import java.util.ArrayList;
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

    /**
     * Creates a new parser.
     *
     * @return the compound parser.
     */
    @Override
    public CompoundWordParser crate() {
        return imp;
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
            TamilWord pure = singleWord.filterToPure();
            FeatureSet set = new FeatureSet(features);

            MultipleWordSplitResult result = HandlerFactory.parse(pure.toString(), false, set.isFeatureEnabled(ParseWithUnknownFeature.class), maxReturn);
            List<SimpleSplitResult> list = result.getSplit();
            if (list == null || list.isEmpty()) {
                return null;
            }

            List<ParserResult> ret = new ArrayList<ParserResult>();
            for (SimpleSplitResult r : list) {
                List<String> splits = r.getSplitList();
                if (splits.size() == 1) {
                   List<IKnownWord> knowns =  result.getGivenWord().getMapContext().get(splits.get(0));
                    for ( IKnownWord k : knowns) {
                        ParserResult p = new ParserResult(k);
                        ret.add(p);

                    }
                } else {
                    ThodarMozhiBuilder builder   = new ThodarMozhiBuilder();
                    for (String s : splits) {
                        builder.appendNodesToAllPaths(result.getGivenWord().getMapContext().get(s));
                    }

                    List<List<IKnownWord>> paths = builder.getPaths();
                    for (List<IKnownWord> l : paths) {
                        ParserResult p = new ParserResult(pure, l);
                        ret.add(p);
                    }
                }
            }
            return ret;
        }
    }
}
