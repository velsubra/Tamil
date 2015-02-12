package tamil.lang.known.non.derived;

import tamil.lang.TamilWord;

/**
 * <p>
 *      பெயர்ச்சொல்லைக்குறிப்பது
 *
 * </p>
 *
 * @author velsubra
 */
public final class Peyarchchol extends AbstractKnownWord implements  IPeyarchchol , IBasePeyar{
    private int  overloadCount = 0;
    private boolean uyarThinhai;
    public Peyarchchol(TamilWord word, int overloadCount) {
        this(word,overloadCount,false);
    }

    public boolean isUyarThinhai() {
        return uyarThinhai;
    }

    public Peyarchchol(TamilWord word, int overloadCount, boolean uyarThinhai) {
        super(word);
        this.overloadCount = overloadCount;
        this.uyarThinhai = uyarThinhai;
    }

    @Override
    public int compareTo(Object o) {
        int ret = super.compareTo(o);
        if (ret == 0) {
            if (Peyarchchol.class.isAssignableFrom(o.getClass())) {
            return new Integer(overloadCount).compareTo(new Integer(((Peyarchchol)o).overloadCount));
            } else {
                return ret;
            }
        } else {
            return ret;
        }
    }


    @Override
    public IPeyarchchol getPeyar() {
        return this;
    }
}
