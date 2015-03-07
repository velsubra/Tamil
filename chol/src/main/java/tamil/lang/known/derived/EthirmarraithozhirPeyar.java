package tamil.lang.known.derived;

import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.IEthirmarrai;
import tamil.lang.known.non.derived.IPeyarchchol;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 *     ஏதிர்மறைதொழிர்பெயர்  எ.கா) கல்லாமை
 *
 * </p>
 *
 * @author velsubra
 */
public final class EthirmarraithozhirPeyar extends VinaiyadiDerivative implements IEthirmarrai, IPeyarchchol{
    public EthirmarraithozhirPeyar(TamilWord word, Vinaiyadi vinaiyadi) {
        super(word, vinaiyadi);
    }


    @Override
    public boolean isUyarThinhai() {
        return false;
    }

    @Override
    public boolean isProNoun() {
        return false;
    }
}
