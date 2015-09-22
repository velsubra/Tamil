package my.interest.lang.tamil.impl.number.known;

import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.AbstractKnownWord;
import tamil.lang.known.non.derived.IPeyarchchol;

import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author velsubra
 */
public class KnownNumberComponent extends AbstractKnownWord implements IPeyarchchol {
    public int getPosition() {
        return position;
    }

    protected int position;

    public int getUnit() {
        return unit;
    }

    protected int unit;

    public KnownNumberComponent(int position, int unit, TamilWord word) {
        super(word);
        this.type = TamilWord.from("எண்பகுதி");
        this.position = position;
        this.unit = unit;
    }


    public boolean isUyarThinhai() {
        return false;
    }

    public boolean isProNoun() {
        return false;
    }


    public boolean isCrore() {
        return  position == 10000000;
    }
}
