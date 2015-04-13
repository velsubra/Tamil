package tamil.lang.known.derived;

import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.IEthirmarrai;
import tamil.lang.known.non.derived.IVinaiyechcham;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 *     ஏதிர்மறைவினையெச்சம் எ.கா) சொல்லாமல் , ஓடாமல்
 * </p>
 *
 * @author velsubra
 */
public class EthirmarraiVinaiyechcham extends VinaiyadiDerivative implements IVinaiyechcham, IEthirmarrai {
    public EthirmarraiVinaiyechcham(TamilWord word, Vinaiyadi vinaiyadi) {
        super(word, vinaiyadi);
    }
}
