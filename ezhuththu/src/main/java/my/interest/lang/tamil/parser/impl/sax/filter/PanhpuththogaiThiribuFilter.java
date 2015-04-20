package my.interest.lang.tamil.parser.impl.sax.filter;

import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.derived.PanhpupPeyarththiribu;
import tamil.lang.known.non.derived.Chuttuppeyar;
import tamil.lang.known.non.derived.IBasePeyar;
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
    public List<IKnownWord> filterMatched(IKnownWord recognized, TamilWordPartContainer nilaimozhi, TamilWordPartContainer varumozhi, List<IKnownWord> tail) {
        if (tail.size() < 1) {
            return ignore();
        }

        IKnownWord next = tail.get(0);
        if (!IPeyarchchol.class.isAssignableFrom(next.getClass())) {
            return  ignore();
        }
        return returnSingle(recognized);
    }
}
