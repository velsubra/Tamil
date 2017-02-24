package tamil.lang.known.thodar;

import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.IThogai;
import tamil.lang.known.non.derived.IPeyarchchol;

import java.util.List;

/**
 * <p>
 *     Represents பண்புத்தொகை. எ.கா) செந்தமிழ்
 * </p>
 *
 * @author velsubra
 */
public class PanhputhThogai extends AbstractThodarMozhi implements IPeyarchchol, IThogai {
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

//    @Override
//    public List<TYPE_SIG> getTypes() {
//        return (List<TYPE_SIG>)Arrays.asList(new TYPE_SIG[]{TYPE_SIG.PT,TYPE_SIG.N});
//    }
}
