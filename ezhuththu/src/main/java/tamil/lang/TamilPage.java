package tamil.lang;

import common.lang.Page;
import common.lang.Para;
import my.interest.lang.tamil.parser.impl.TamilPageListener;

import java.util.*;

/**
 * <p>
 *     Represents a Para, ie) text with one or more lines.
 *
 * </p>
 * @see #from(String)
 *
 * @author velsubra
 */
public final class TamilPage<T extends Para> extends LinkedList<T> implements Page<T> {

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        boolean first = true;
        for ( Para w : this.toArray(new Para[0])) {
            if (!first) {
                buffer.append("\n");

            }
            first = false;
            buffer.append(w.toString());
        }
        return buffer.toString();
    }

    /**
     * Returns all words in a para.
     * @return  list of TamilWord
     */
    public List<TamilWord> getAllTamilWords() {
        List<TamilWord> list = new java.util.ArrayList<TamilWord>();
        for (TamilPara para : (List<TamilPara>) this)  {
            for (TamilSentence sentence : ((List<TamilSentence>)  para))  {
                    list.addAll(sentence);
            }
        }
        return list;
    }

    /**
     * Create a Para from String with one or more lines
     * @param page the UTF-8 string
     * @return  TamilPage
     */
    public static TamilPage from(String page) {
        return TamilPageListener.readUTF8(page);
    }
}
