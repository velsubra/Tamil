package my.interest.lang.tamil.parser.impl.sax.filter.known;

import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.VUrubu;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class VUrubuFilter  extends AbstractKnownWordFilter {
    public VUrubuFilter() {
        super(VUrubu.class);
    }

    @Override
    public List<IKnownWord> filterMatched(IKnownWord recognized, ParsingContext context) {
//        if (tail.isEmpty()) {
//            return returnSingle(recognized);
//        }  else {
//         //  IKnownWord next = tail.get()
//        }
//
//        IKnownWord next = tail.get(0);
//        if (!IPeyarchchol.class.isAssignableFrom(next.getClass())) {
//            return  ignore();
//        }
        return returnSingle(recognized);
    }
}