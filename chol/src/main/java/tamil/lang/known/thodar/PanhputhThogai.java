package tamil.lang.known.thodar;

import my.interest.lang.tamil.TamilUtils;
import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.IPeyarchchol;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class PanhputhThogai extends AbstractThodarMozhi implements IPeyarchchol {
    public PanhputhThogai(TamilWord word,List<? extends IKnownWord> list) {
        super(word,list);
    }

    public boolean isUyarThinhai() {
        return false;
    }

    @Override
    public boolean isProNoun() {
        return false;
    }

    @Override
    public List<TYPE_SIG> getTypes() {
        return (List<TYPE_SIG>)Arrays.asList(new TYPE_SIG[]{TYPE_SIG.PT,TYPE_SIG.N});
    }
}
