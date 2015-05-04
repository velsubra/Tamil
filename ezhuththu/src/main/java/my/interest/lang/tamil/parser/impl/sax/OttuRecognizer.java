package my.interest.lang.tamil.parser.impl.sax;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilWord;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.IIdaichchol;
import tamil.lang.known.non.derived.IKaddalhai;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class OttuRecognizer extends SpecificTokenRecognizer {
    protected OttuRecognizer(TamilWord token) {
        super(token);
    }

    @Override
    public TokenMatcherResult match(TamilWordPartContainer nilaimozhi, TamilWordPartContainer varumozhi, List<IKnownWord> tail , TamilDictionary dictionary, FeatureSet set) {
        if (tail.isEmpty()) return TokenMatcherResult.DisContinue();

        TokenMatcherResult matching = super.match(nilaimozhi, varumozhi, tail, dictionary, set);
        if (matching.isMatching()) {
             if (tail.isEmpty())  {
                 return TokenMatcherResult.DisContinue();
             }
            IKnownWord next = tail.get(0);
            if (IKaddalhai.class.isAssignableFrom(next.getClass())) {
                return TokenMatcherResult.DisContinue();
            }
            if (IIdaichchol.class.isAssignableFrom(next.getClass())) {
                return TokenMatcherResult.DisContinue();
            }
            if (next.getWord().startsWith(token, false)) {
                return matching;
            } else {
                return TokenMatcherResult.DisContinue();
            }
        } else {
            return matching;
        }

    }
}
