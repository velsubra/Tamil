package tamil.lang.known.derived;

import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.IIdaichchol;
import tamil.lang.known.non.derived.IThiribu;
import tamil.lang.known.non.derived.Peyarchchol;

/**
 * <p>
 * பண்புப்பெயர்த்திரிபு : பண்புப்பெயர் புணர்ச்சியில்  பண்புப்பெயர்கள் திரிந்து புணரும்.  இந்த திரிபு  ஓர் இடைச்சொல்லாக  கருதப்படுகிறது.
 * <p/>
 * எ.கா)
 * பெருமை + ஊர் = பேர் + ஊர் = பேரூர்.
 * <p/>
 * இங்கு "பேர்" ஒரு திரிபாகக்கருதப்படுகிறது.  இது தனித்து பொருளைக்கொடுக்கவல்லதல்ல. பண்புத்தொகையில்மட்டுமே  (எ.கா பேரூர்)  வரக்கூடியது.
 * </p>
 *
 * @author velsubra
 */
public final class PanhpupPeyarththiribu extends PeyarchcholDerivative implements IIdaichchol, IThiribu {


    public PanhpupPeyarththiribu(TamilWord word, Peyarchchol peyarchchol) {
        super(word, peyarchchol);

    }

    @Override
    public boolean isAtomic() {
        return true;
    }

    @Override
    public IKnownWord getRealWord() {
        return this.getPeyar();
    }
}
