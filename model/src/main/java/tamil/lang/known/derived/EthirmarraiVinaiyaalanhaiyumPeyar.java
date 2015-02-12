package tamil.lang.known.derived;

import tamil.lang.TamilWord;
import my.interest.lang.tamil.generated.types.PaalViguthi;
import tamil.lang.known.non.derived.IEthirmarrai;
import tamil.lang.known.non.derived.IPeyarchchol;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 *     ஏதிர்மறைவினையாலணையும்பெயர்   எ.கா)  படிக்காதவன்
 * </p>
 *
 * @author velsubra
 */
public final class EthirmarraiVinaiyaalanhaiyumPeyar extends DerivativeWithPaal implements IPeyarchchol, IEthirmarrai {
    public EthirmarraiVinaiyaalanhaiyumPeyar(TamilWord word, Vinaiyadi vinaiyadi, PaalViguthi viguthi) {
        super(word, vinaiyadi, viguthi);
    }
}
