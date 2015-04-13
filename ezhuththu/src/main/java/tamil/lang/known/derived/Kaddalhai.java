package tamil.lang.known.derived;

import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.IKaddalhai;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 *        கட்டளை  , word with imperative sense.    எ.கா) நடக்கட்டும், நடக்கலாம்.
 * </p>
 *
 * @author velsubra
 */
public final class Kaddalhai extends VinaiyadiDerivative implements IKaddalhai {
    public Kaddalhai(TamilWord word, Vinaiyadi vinaiyadi) {
        super(word, vinaiyadi);
    }

    @Override
    public Vinaiyadi getKaddalhaiVinaiyadi() {
        return this.getVinaiyadi();
    }
}
