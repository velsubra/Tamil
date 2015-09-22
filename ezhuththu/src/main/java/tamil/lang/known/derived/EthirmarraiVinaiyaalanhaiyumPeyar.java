package tamil.lang.known.derived;

import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.generated.types.PaalViguthi;
import tamil.lang.known.non.derived.IEthirmarrai;
import tamil.lang.known.non.derived.IPeyarchchol;
import tamil.lang.known.non.derived.idai.Kalh;
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
         this(word,vinaiyadi, viguthi,false);
    }
    public EthirmarraiVinaiyaalanhaiyumPeyar(TamilWord word, Vinaiyadi vinaiyadi, PaalViguthi viguthi, boolean derived) {
        super(word, vinaiyadi, viguthi);
         if (derived) return;
        if (viguthi == PaalViguthi.AAR || viguthi == PaalViguthi.AR) {
            TamilWord withkalh = word.duplicate();
            withkalh.addAll(Kalh.KALH.getWord());
            EthirmarraiVinaiyaalanhaiyumPeyar kalh = new EthirmarraiVinaiyaalanhaiyumPeyar(withkalh, vinaiyadi, viguthi, true);
            TamilFactory.getSystemDictionary().add(kalh);
        }

    }

    @Override
    public boolean isProNoun() {
        return false;
    }
}
