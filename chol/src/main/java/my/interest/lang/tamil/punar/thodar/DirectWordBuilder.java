package my.interest.lang.tamil.punar.thodar;

import tamil.lang.known.IKnownWord;
import my.interest.lang.tamil.punar.AbstractThodarmozhiBuilder;
import my.interest.lang.tamil.punar.ThodarMozhiBuilder;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class DirectWordBuilder extends AbstractThodarmozhiBuilder {
    public DirectWordBuilder() {

    }

    private  DirectWordBuilder(DirectWordBuilder from) {
        super(from);
    }


    /**
     * @param know
     * @return
     */
    @Override
    public ThodarMozhiBuilder accept(IKnownWord know, boolean clone) {
        if (atLogicalConclusion) {
            return null;
        } else {
            DirectWordBuilder ret = clone? (DirectWordBuilder) cloneContext():this;
            ret.list.add(know);
            ret.atLogicalConclusion = true;
            return ret;
        }
    }


    @Override
    public ThodarMozhiBuilder cloneContext() {
        DirectWordBuilder ret = new DirectWordBuilder(this);
        return ret;
    }
}
