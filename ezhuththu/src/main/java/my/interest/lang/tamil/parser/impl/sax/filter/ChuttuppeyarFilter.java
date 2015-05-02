package my.interest.lang.tamil.parser.impl.sax.filter;

import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.Chuttuppeyar;
import tamil.lang.known.non.derived.IBasePeyar;
import tamil.lang.known.non.derived.IPeyarchchol;
import tamil.lang.known.non.derived.Ottu;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class ChuttuppeyarFilter extends AbstractKnownWordFilter {
    public ChuttuppeyarFilter() {
        super(Chuttuppeyar.class);
    }

    public List<IKnownWord> filterMatched(IKnownWord recognized, TamilWordPartContainer nilaimozhi, TamilWordPartContainer varumozhi, List<IKnownWord> tail) {
        if (tail.size() < 2) {

            return ignore();
        }
        IKnownWord next = tail.get(0);
        IKnownWord nextnext = tail.get(1);

        if (!Ottu.class.isAssignableFrom(next.getClass())) {
           return  ignore();
        }

        if (!IPeyarchchol.class.isAssignableFrom(nextnext.getClass())) {
            return  ignore();
        }
        return returnSingle(recognized);
    }
}
