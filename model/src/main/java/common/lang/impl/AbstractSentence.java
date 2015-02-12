package common.lang.impl;

import common.lang.Para;
import common.lang.Sentence;
import common.lang.Word;

import java.util.LinkedList;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public abstract class AbstractSentence<T extends Word> extends LinkedList<T> implements Sentence<T> {


    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        boolean first = true;
        for ( Word w : this.toArray(new Word[0])) {
            if (!first) {
                buffer.append(" ");

            }
            first = false;
            buffer.append(w.toString());
        }
        return buffer.toString();
    }

    public void setPreviousSpaceCount(int previousSpaceCount) {
        this.previousSpaceCount = previousSpaceCount;
    }

    public void setContainingPara(Para containingPara) {
        this.containingPara = containingPara;
    }


    private int previousSpaceCount;
    private Para containingPara;




    @Override
    public Para getContainingPara() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getPreviousSpacesCount() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
