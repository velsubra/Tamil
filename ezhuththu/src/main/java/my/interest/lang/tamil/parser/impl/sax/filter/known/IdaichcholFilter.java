package my.interest.lang.tamil.parser.impl.sax.filter.known;

import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.IIdaichchol;
import tamil.lang.known.non.derived.idai.*;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class IdaichcholFilter extends AbstractKnownWordFilter {
    public IdaichcholFilter() {
        super(IIdaichchol.class);
    }

    @Override
    public List<IKnownWord> filterMatched(IKnownWord recognized, ParsingContext context) {
        if (!context.tail.isEmpty()) {
            IKnownWord next = context.tail.get(0);
            if ( !Aaga.class.isAssignableFrom(recognized.getClass()) && !Aay.class.isAssignableFrom(recognized.getClass()) && !VUrubu.class.isAssignableFrom(recognized.getClass()) && Ottu.class.isAssignableFrom(next.getClass())) {
                return ignore();
            }
        }
        return returnSingle(recognized);

    }
}