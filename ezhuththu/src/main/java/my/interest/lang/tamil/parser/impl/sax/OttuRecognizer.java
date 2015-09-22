package my.interest.lang.tamil.parser.impl.sax;

import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilWord;
import tamil.lang.api.parser.VallinavottuEndingOk;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.IIdaichchol;
import tamil.lang.known.non.derived.IKaddalhai;
import tamil.lang.known.non.derived.idai.Thaan;

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
    public TokenMatcherResult afterMatch(ParsingContext context) {
        if (context.nilaimozhi.getWord().isEmpty()) {
            return TokenMatcherResult.DisContinue();
        }

        if (context.tail.isEmpty()) {

            if (!context.set.isFeatureEnabled(VallinavottuEndingOk.class)) {
                return TokenMatcherResult.DisContinue();
            } else {
                List<TamilWordPartContainer> listnilaimozhi = getNilaiMozhi(context);
                return TokenMatcherResult.Matching(listnilaimozhi, getKnowns());
            }
        }

        IKnownWord next = context.tail.get(0);
        if (IKaddalhai.class.isAssignableFrom(next.getClass())) {
            return TokenMatcherResult.DisContinue();
        }
        if (IIdaichchol.class.isAssignableFrom(next.getClass())) {
            //சேர்வதைத்தான்
            if (!Thaan.class.isAssignableFrom(next.getClass())) {
                return TokenMatcherResult.DisContinue();
            }
        }
        if (next.getWord().startsWith(token, false)) {
            List<TamilWordPartContainer> listnilaimozhi = getNilaiMozhi(context);
            return TokenMatcherResult.Matching(listnilaimozhi, getKnowns());

        } else {
            return TokenMatcherResult.DisContinue();
        }


    }
}
