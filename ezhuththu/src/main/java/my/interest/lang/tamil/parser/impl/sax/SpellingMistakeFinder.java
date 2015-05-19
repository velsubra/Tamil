package my.interest.lang.tamil.parser.impl.sax;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.Theriyaachchol;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class SpellingMistakeFinder extends TokenRecognizer {

    // private Set<TamilWord> searchFinds = new java.util.HashSet<TamilWord>();

    @Override
    public TokenMatcherResult match(ParsingContext context) {
        if (isUnKnownAlreadyThere(context.tail)) {
            return TokenMatcherResult.DisContinue();
        } else {
            boolean candicate = false;
            if (context.nilaimozhi.size() == 0) {
                candicate = true;
            } else {
                if (context.varumozhi.size() > 1) {
                    List<IKnownWord> result = context.dictionary.search(context.varumozhi.getWord(), false, 1, null);
                    if (result.isEmpty()) {
                        candicate = true;
//                        TamilWord oneless = varumozhi.getWord().duplicate();
//                        oneless.removeLast();
//                        if (!dictionary.search(oneless, false, 1, null).isEmpty()) {
//
//                        }
                    }

                }
            }
            if (candicate) {
                List<IKnownWord> spelledWrong = new ArrayList<IKnownWord>();
                spelledWrong.add(new Theriyaachchol(context.varumozhi.getWord().duplicate()));
                return TokenMatcherResult.Matching(context.nilaimozhi, spelledWrong);
            } else {
                return TokenMatcherResult.Continue();
            }
        }

    }

    private boolean isUnKnownAlreadyThere(List<IKnownWord> tail) {
        for (IKnownWord k : tail) {
            if (Theriyaachchol.class.isAssignableFrom(k.getClass())) {
                return true;
            }
        }
        return false;
    }
}
