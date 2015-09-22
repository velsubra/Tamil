package my.interest.lang.tamil.parser.impl.sax;

import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;
import tamil.lang.api.parser.ParseAsNumberFeature;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.idai.Aththu;
import tamil.lang.known.non.derived.IPeyarchchol;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class AththuRecognizer extends SpecificTokenRecognizer {
    protected AththuRecognizer() {
        super(Aththu.ATHTHU.getWord());
    }

    @Override
    public TokenMatcherResult afterMatch(ParsingContext context) {


        if (context.tail.isEmpty() || context.set.isFeatureEnabled(ParseAsNumberFeature.class)) {
            return TokenMatcherResult.DisContinue();
        }
        IKnownWord next = context.tail.get(0);
        if (!IPeyarchchol.class.isAssignableFrom(next.getClass())) {
            return TokenMatcherResult.DisContinue();
        }
        if (context.nilaimozhi.getWord().getLast().asTamilCharacter().isMeyyezhuththu()) {
            TamilWord plus_am = context.nilaimozhi.getWord().duplicate();
            TamilCharacter last = plus_am.removeLast().asTamilCharacter();
            plus_am.add(last.addUyir(TamilSimpleCharacter.a));
            plus_am.add(TamilCompoundCharacter.IM);
            return TokenMatcherResult.Matching(new TamilWordPartContainer(plus_am), getKnowns());
        } else {
            return TokenMatcherResult.DisContinue();
        }


    }
}
