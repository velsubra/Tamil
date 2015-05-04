package my.interest.lang.tamil.parser.impl.sax;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.api.parser.ParseAsNumberFeature;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.IPeyarchchol;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class AththuRecognizer extends SpecificTokenRecognizer {
    protected AththuRecognizer() {
        super(TamilWord.from("அத்து"));
    }

    @Override
    public TokenMatcherResult match(TamilWordPartContainer nilaimozhi, TamilWordPartContainer varumozhi, List<IKnownWord> tail, TamilDictionary dictionary, FeatureSet set) {
        TokenMatcherResult result = super.match(nilaimozhi, varumozhi, tail,dictionary,set);
        if (result.isMatching()) {

            if (tail.isEmpty() || set.isFeatureEnabled(ParseAsNumberFeature.class)) {
                return  TokenMatcherResult.DisContinue();
            }
            IKnownWord next = tail.get(0);
            if (!IPeyarchchol.class.isAssignableFrom(next.getClass())) {
                return  TokenMatcherResult.DisContinue();
            }
            if (nilaimozhi.getWord().getLast().asTamilCharacter().isMeyyezhuththu()) {
                TamilWord plus_am = nilaimozhi.getWord().duplicate();
                TamilCharacter last = plus_am.removeLast().asTamilCharacter();
                plus_am.add(last.addUyir(TamilSimpleCharacter.a));
                plus_am.add(TamilCompoundCharacter.IM);
                return  TokenMatcherResult.Matching(new TamilWordPartContainer(plus_am), result.getMatchedWords()) ;
            }  else {
                return  TokenMatcherResult.DisContinue();
            }

        }
        return result;

    }
}
