package my.interest.lang.tamil.impl.yaappu;

import common.lang.impl.AbstractCharacter;
import my.interest.lang.tamil.impl.FeatureSet;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilWord;
import tamil.lang.exception.TamilPlatformException;
import tamil.yaappu.asai.Ntear;
import tamil.yaappu.asai.Ntirai;
import tamil.yaappu.asai.AbstractAsai;

import java.util.Iterator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class AsaiIterator implements Iterator<AbstractAsai> {
    int index = 0;
    TamilWord w = null;
    private FeatureSet featureSet = null;

    public AsaiIterator(TamilWord w, FeatureSet set) {
        this.w = w;
        if (this.w == null) {
            this.w = TamilWord.from("");
        }
        this.featureSet = set;
        if (this.featureSet == null) {
            this.featureSet = FeatureSet.EMPTY;
        }
    }

    public boolean hasNext() {
        return w.size() > index;
    }

    private TamilCharacter getTamilChar() {
        AbstractCharacter ch = w.charAt(index++);
        if (!ch.isPureTamilLetter()) {
            throw new TamilPlatformException("Not a Tamil character " + ch.toString() + " at Index:" + index);
        }
        return ch.asTamilCharacter();
    }

    public AbstractAsai next() {
        TamilCharacter tamil = getTamilChar();
        TamilWord seer = new TamilWord(tamil);
        if (tamil.isMeyyezhuththu() || tamil.isAaythavezhuththu()) {
            throw new TamilPlatformException("Unexpected Tamil character " + tamil.toString() + " at Index:" + index);
        }
        if (tamil.isKurilezhuththu()) {
            if (!hasNext()) {
                return new Ntear(seer);
            } else {
                tamil = getTamilChar();
                seer.add(tamil);
                consumeAllOttu(seer);
                if (tamil.isMeyyezhuththu() || tamil.isAaythavezhuththu()) {
                    return new Ntear(seer);
                } else {
                    return new Ntirai(seer);
                }
            }
        } else {

            consumeAllOttu(seer);
            return new Ntear(seer);

        }

    }

    private void consumeAllOttu(TamilWord  seer) {
        while (hasNext()) {
            AbstractCharacter ch = w.charAt(index);
            if (!ch.isPureTamilLetter()) {
                throw new TamilPlatformException("Not a Tamil character " + ch.toString() + " at Index:" + index);
            }
            if (ch.asTamilCharacter().isMeyyezhuththu() || ch.asTamilCharacter().isAaythavezhuththu()) {
                seer.add(ch);
                index++;
            } else {
                break;
            }
        }
    }

    public void remove() {
        throw new UnsupportedOperationException("Unsupported!");
    }
}
