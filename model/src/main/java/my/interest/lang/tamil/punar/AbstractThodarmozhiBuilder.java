package my.interest.lang.tamil.punar;

import tamil.lang.known.IKnownWord;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public abstract class AbstractThodarmozhiBuilder implements ThodarMozhiBuilder {

   protected ArrayList<IKnownWord> list = new ArrayList<IKnownWord>();
   protected boolean atLogicalConclusion = false;

    public AbstractThodarmozhiBuilder() {

    }


    protected  AbstractThodarmozhiBuilder(AbstractThodarmozhiBuilder from) {
        this.list.addAll(from.list);
        this.atLogicalConclusion = from.atLogicalConclusion;
    }

    @Override
    public boolean isAtLogicalCompletion() {
        return atLogicalConclusion;
    }


    @Override
    public List<IKnownWord> getThodarMozhi() {
        return  list;
    }

}
