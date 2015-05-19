package my.interest.lang.tamil.parser.impl.sax;

import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import tamil.lang.TamilWord;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class SpecificTokenRecognizer extends AbstractTokenRecognizer {
    protected SpecificTokenRecognizer(TamilWord token) {
        super(token);
    }

    protected TokenMatcherResult afterMatch(ParsingContext context) {
        return TokenMatcherResult.Matching(getNilaiMozhi(context), getKnowns());
    }


    @Override
    public TokenMatcherResult match(ParsingContext context) {
        if (token.equals(context.varumozhi.getWord())) {
            return afterMatch(context);
        }
        if (token.endsWith(context.varumozhi.getWord(), false)) {
            return TokenMatcherResult.Continue();
        }
        return TokenMatcherResult.DisContinue();
    }
}
