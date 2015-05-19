package my.interest.lang.tamil.parser.impl.sax.filter.known;

import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import my.interest.lang.tamil.parser.impl.sax.filter.known.AbstractKnownWordFilter;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.derived.PanhpupPeyarththiribu;
import tamil.lang.known.non.derived.IPeyarchchol;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class PanhpuththogaiThiribuFilter  extends AbstractKnownWordFilter {
    public PanhpuththogaiThiribuFilter() {
        super(PanhpupPeyarththiribu.class);
    }

    @Override
    public List<IKnownWord> filterMatched(IKnownWord recognized, ParsingContext context) {
        if (context.tail.size() < 1) {
            return ignore();
        }

        IKnownWord next = context.tail.get(0);
        if (!IPeyarchchol.class.isAssignableFrom(next.getClass())) {
            return  ignore();
        }
        return returnSingle(recognized);
    }
}
