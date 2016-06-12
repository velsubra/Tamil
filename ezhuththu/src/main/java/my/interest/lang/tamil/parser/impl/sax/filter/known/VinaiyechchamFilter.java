package my.interest.lang.tamil.parser.impl.sax.filter.known;

import my.interest.lang.tamil.parser.impl.sax.TokenRecognizer;
import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import tamil.lang.known.AbstractVinaiyadiPeyarechcham;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.derived.PeyarchcholDerivative;
import tamil.lang.known.derived.VinaiMuttu;
import tamil.lang.known.non.derived.*;
import tamil.lang.known.non.derived.idai.Ottu;

import java.util.List;

/**
 * Created by velsubra on 6/10/16.
 */
public class VinaiyechchamFilter extends AbstractKnownWordFilter {
    public VinaiyechchamFilter() {
        super(IVinaiyechcham.class);
    }


    @Override
    public List<IKnownWord> filterMatched(IKnownWord recognized, ParsingContext context) {
        if (context.tail.isEmpty()) {
            return returnSingle(recognized);
        }

        IKnownWord next = context.tail.get(0);
        if (TokenRecognizer.isWordOfOneOfTypes(next, PeyarchcholDerivative.class, IPeyarchchol.class)) {
            return ignore();
        }
        return returnSingle(recognized);
    }
}
