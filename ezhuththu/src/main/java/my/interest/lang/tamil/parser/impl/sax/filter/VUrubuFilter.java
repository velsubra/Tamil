package my.interest.lang.tamil.parser.impl.sax.filter;

import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.derived.PanhpupPeyarththiribu;
import tamil.lang.known.non.derived.IPeyarchchol;
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
    public List<IKnownWord> filterMatched(IKnownWord recognized, TamilWordPartContainer nilaimozhi, TamilWordPartContainer varumozhi, List<IKnownWord> tail) {
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