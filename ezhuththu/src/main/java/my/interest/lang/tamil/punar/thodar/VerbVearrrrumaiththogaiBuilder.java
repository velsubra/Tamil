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
public class VerbVearrrrumaiththogaiBuilder extends AbstractThodarmozhiBuilder {
    public VerbVearrrrumaiththogaiBuilder() {

    }


    protected VerbVearrrrumaiththogaiBuilder(VerbVearrrrumaiththogaiBuilder from) {
        super(from);

    }

    /**
     * @param known
     * @return
     */
    @Override
    public ThodarMozhiBuilder accept(IKnownWord known, boolean clone) {
         return null;
    }


    @Override
    public ThodarMozhiBuilder cloneContext() {
        return new VerbVearrrrumaiththogaiBuilder(this);
    }
}