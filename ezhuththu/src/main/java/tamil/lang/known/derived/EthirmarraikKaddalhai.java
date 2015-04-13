package tamil.lang.known.derived;

import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.IEthirmarrai;
import tamil.lang.known.non.derived.IKaddalhai;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class EthirmarraikKaddalhai extends VinaiyadiDerivative implements IKaddalhai, IEthirmarrai {
    public EthirmarraikKaddalhai(TamilWord word, Vinaiyadi vinaiyadi) {
        super(word, vinaiyadi);
    }

    @Override
    public Vinaiyadi getKaddalhaiVinaiyadi() {
        return this.getVinaiyadi();
    }
}
