package my.interest.lang.tamil.punar.thodar;

import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.Peyarchchol;
import tamil.lang.known.non.derived.Vinaiyadi;
import my.interest.lang.tamil.punar.AbstractThodarmozhiBuilder;
import my.interest.lang.tamil.punar.ThodarMozhiBuilder;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class VinaiththogaiBuilder extends AbstractThodarmozhiBuilder {
    public VinaiththogaiBuilder() {

    }


    protected VinaiththogaiBuilder(VinaiththogaiBuilder from) {
        super(from);

    }

    /**
     * @param known
     * @return
     */
    @Override
    public ThodarMozhiBuilder accept(IKnownWord known, boolean clone) {



        if (list.isEmpty()) {
            if (!Vinaiyadi.class.isAssignableFrom(known.getClass())) {
                return null;
            } else {
                VinaiththogaiBuilder ret = clone? (VinaiththogaiBuilder) cloneContext() : this;
                ret.list.add(known);
                ret.atLogicalConclusion = false;
                return ret;
            }
        } else {
            if (Peyarchchol.class != known.getClass()) {
                return null;
            } else {
                if (this.list.size() == 1) {
                    VinaiththogaiBuilder ret = clone ? (VinaiththogaiBuilder) cloneContext() : this;
                    ret.list.add(known);
                    ret.atLogicalConclusion = true;
                    return ret;
                } else {
                    return null;
                }
            }
        }

    }


    @Override
    public ThodarMozhiBuilder cloneContext() {
        return new VinaiththogaiBuilder(this);
    }
}
