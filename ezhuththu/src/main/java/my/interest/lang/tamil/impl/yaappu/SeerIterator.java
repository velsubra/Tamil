package my.interest.lang.tamil.impl.yaappu;

import common.lang.impl.AbstractCharacter;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilWord;
import tamil.lang.exception.TamilPlatformException;
import tamil.yaappu.seer.Ntear;
import tamil.yaappu.seer.Ntirai;
import tamil.yaappu.seer.AbstractSeer;

import java.util.Iterator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class SeerIterator implements Iterator<AbstractSeer> {
    int index = 0;
    TamilWord w = null;

    public SeerIterator(TamilWord w) {
        this.w = w;
        if (this.w == null) {
            this.w = TamilWord.from("");
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

    public AbstractSeer next() {
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

    private void consumeAllOttu(TamilWord w) {
        while (hasNext()) {
            AbstractCharacter ch = w.charAt(index);
            if (!ch.isPureTamilLetter()) {
                throw new TamilPlatformException("Not a Tamil character " + ch.toString() + " at Index:" + index);
            }
            if (ch.asTamilCharacter().isMeyyezhuththu() || ch.asTamilCharacter().isAaythavezhuththu()) {
                w.add(ch);
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
