package tamil.lang;

import common.lang.Para;
import common.lang.Sentence;
import my.interest.lang.tamil.parser.impl.TamilParaListener;

import java.util.LinkedList;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilPara<T extends Sentence> extends LinkedList<T> implements Para<T> {

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        boolean first = true;
        for ( Sentence w : this.toArray(new Sentence[0])) {
            if (!first) {
                buffer.append(".");

            }
            first = false;
            buffer.append(w.toString());
        }
        if (size() ==1) {
            buffer.append(".");
        }
        return buffer.toString();
    }

    @Override
    public int getPreviousNewLinesCount() {
        return previousNewLinesCount;
    }

    public void setPreviousNewLinesCount(int previousNewLinesCount) {
        this.previousNewLinesCount = previousNewLinesCount;
    }

    int previousNewLinesCount =0;

    public Sentence getHeadSentence() {
        return this.getFirst();
    }

    public static TamilPara from(String sentence) {
        return TamilParaListener.readUTF8(sentence);
    }



}
