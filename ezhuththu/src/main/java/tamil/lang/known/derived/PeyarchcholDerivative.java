package tamil.lang.known.derived;

import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.AbstractKnownWord;
import tamil.lang.known.non.derived.IBasePeyar;
import tamil.lang.known.non.derived.Peyarchchol;

/**
 * <p>
 *    பெயர்ச்சொல்லை தன்னகத்தேகொண்ட சொல்லைக்குறிப்பது.
 * </p>
 *
 * @author velsubra
 */
public abstract class PeyarchcholDerivative extends AbstractKnownWord implements IBasePeyar {

    public Peyarchchol getPeyar() {
        return peyar;
    }

    public void setPeyar(Peyarchchol peyar) {
        this.peyar = peyar;
    }

    private Peyarchchol peyar;


    public PeyarchcholDerivative(TamilWord word, Peyarchchol peyar) {
        super(word);
        this.peyar = peyar;

    }



}