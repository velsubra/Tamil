package my.interest.lang.tamil.parser.impl.sax.filter.known;

import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.Vinaiyadi;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class VinaiyadiFilter extends AbstractKnownWordFilter {
    public VinaiyadiFilter() {
        super(Vinaiyadi.class);
    }

    @Override
    public List<IKnownWord> filterMatched(IKnownWord recognized, ParsingContext context) {
       if (!context.tail.isEmpty()) {
            return ignore();
       }  else {
           return returnSingle(recognized);
       }
    }
}