package my.interest.lang.tamil.punar;

import tamil.lang.TamilWord;

import java.util.LinkedList;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilWordSplitResult extends LinkedList<TamilWordPartContainer>  {
    public TamilWordSplitResult() {
    }

    public TamilWordSplitResult(TamilWord w, TamilWord w2) {
        add(new TamilWordPartContainer(w.duplicate()));
        add(new TamilWordPartContainer(w2.duplicate()));
    }

    public TamilWordSplitResult(TamilWord w, TamilWord w2, TamilWord w3) {
        add(new TamilWordPartContainer(w.duplicate()));
        add(new TamilWordPartContainer(w2.duplicate()));
        add(new TamilWordPartContainer(w3.duplicate()));
    }

    public TamilWordSplitResult(TamilWordPartContainer w, TamilWordPartContainer w2) {
        add(w);
        add(w2);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        boolean  first = true;
        for (TamilWordPartContainer c : this) {
             if (!first) {
                 buffer.append("+");
             }
            buffer.append(c.getWord().toString());
            first = false;
        }

        return buffer.toString();
    }
    public boolean equals(Object obj) {
        if (obj == null) return false;
        return toString().equals(obj.toString());
    }

}
