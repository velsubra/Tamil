package tamil.lang.known.derived;

import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.IThiribu;
import tamil.lang.known.non.derived.Peyarchchol;

/**
 * <p>
 * பிரதிப்பெயர்ச்சொற்கள் வேற்றுமையில் திரியலாம்.  அப்படி திரிந்த சொற்களை இது குறிக்கிறது.
 * எ.கா) என், உன்
 * <p/>
 * நான்  + ஐ = என் + ஐ = என்னை.
 * <p/>
 * "நான் -> என்" ஆக திரிந்துள்ளதையறிக.
 * </p>
 *
 * @author velsubra
 */

//getProNounMaruvi
public final class PeyarchCholThiribu extends PeyarchcholDerivative implements IThiribu {
    public PeyarchCholThiribu(TamilWord word, Peyarchchol peyar) {
        super(word, peyar);
    }

    @Override
    public IKnownWord getRealWord() {
        return this.getPeyar();
    }
}
