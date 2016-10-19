package my.interest.lang.tamil.impl.number.known;

import tamil.lang.TamilWord;
import tamil.lang.api.number.NumberSystemFeature;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.AbstractKnownWord;
import tamil.lang.known.non.derived.IPeyarchchol;

import java.math.BigInteger;
import java.util.Set;

/**
 * <p>
 * <p/>
 * </p>
 *
 * @author velsubra
 */
public class KnownNumberComponent extends AbstractKnownWord implements IPeyarchchol {
    public BigInteger getPosition() {
        return position;
    }

    protected BigInteger position;

    public int getUnit() {
        return unit;
    }

    protected int unit;
    protected int power;
    protected NumberSystemFeature.NumberSystem  numberSystem;

    public int getPower() {
        return power;
    }

    //    public KnownNumberComponent(int position, int unit, TamilWord word) {
//        this(new BigInteger(String.valueOf(position)),unit, word);
//    }
    public KnownNumberComponent(  int pow, int unit, TamilWord word) {
        this(null,pow,unit,word);
    }
    public KnownNumberComponent( NumberSystemFeature.NumberSystem system, int pow, int unit, TamilWord word) {
        super(word);
        this.type = TamilWord.from("எண்பகுதி");
        this.position = new BigInteger("10").pow(pow);
        this.unit = unit;
        this.power = pow;
        numberSystem =  (system == null)?  NumberSystemFeature.NumberSystem.DEFAULT: system;
    }


    public boolean isUyarThinhai() {
        return false;
    }

    public boolean isProNoun() {
        return false;
    }


    public boolean isCrore() {
        return power == 7;
    }
}
