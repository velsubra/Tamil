package tamil.lang.known.derived;

import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.AbstractKnownWord;
import tamil.lang.known.non.derived.IBaseVinai;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 *     வினையை அடிப்படையாகக்கொண்ட சொற்கள்
 * </p>
 *
 * @author velsubra
 */
public abstract class VinaiyadiDerivative extends AbstractKnownWord implements IBaseVinai {

    private Vinaiyadi vinaiyadi;



    public VinaiyadiDerivative(TamilWord word, Vinaiyadi vinaiyadi) {
        super(word);
        this.vinaiyadi = vinaiyadi;



    }

    public Vinaiyadi getVinaiyadi() {
        return vinaiyadi;
    }

    public void setVinaiyadi(Vinaiyadi vinaiyadi) {
        this.vinaiyadi = vinaiyadi;
    }


}