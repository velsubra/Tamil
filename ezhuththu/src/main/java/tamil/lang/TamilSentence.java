package tamil.lang;

import common.lang.impl.AbstractSentence;
import my.interest.lang.tamil.parser.impl.TamilSentenceListener;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public  class TamilSentence extends AbstractSentence {

    public static TamilSentence from(String sentence) {
        return TamilSentenceListener.readUTF8(sentence);
    }
}
