package my.interest.lang.tamil.parser.impl;

import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.internal.api.TamilCharacterParserListener;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilSentence;
import tamil.lang.TamilWord;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilSentenceListener implements TamilCharacterParserListener<TamilSentence> {

    TamilSentence sentence = new TamilSentence();
    TamilWordListener currentWord = new TamilWordListener();

    /**
     * Called when a tamil character is encountered
     *
     * @param tamil
     * @return true when parsing is to be finished, false otherwise
     */
    @Override
    public boolean tamilCharacter(TamilCharacter tamil) {
        if (currentWord.tamilCharacter(tamil)) {
            TamilWord last = currentWord.get();
            if (last != null) {
                sentence.add(last);
            }
            currentWord = new TamilWordListener();

        }
        return false;
    }

    @Override
    public boolean nonTamilCharacter(int nonTamil) {
        if (nonTamil == '.') {
            TamilWord last = currentWord.get();
            if (last != null) {
                sentence.add(last);
            }
            currentWord = new TamilWordListener();
            return true;
        } else {

            if (currentWord.nonTamilCharacter(nonTamil)) {
                TamilWord last = currentWord.get();
                if (last != null) {
                    sentence.add(last);
                }

                currentWord = new TamilWordListener();

            }
        }
        return false;
    }

    @Override
    public TamilSentence get() {
        TamilWord last = currentWord.get();
        if (last != null && !last.isEmpty()) {
            sentence.add(last);
            currentWord = new TamilWordListener();

        }
        return sentence;
    }

    public static TamilSentence readUTF8(String sentence) {
        try {
            return readUTF8(new ByteArrayInputStream(sentence.getBytes(EzhuththuUtils.ENCODING)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static TamilSentence readUTF8(InputStream inputStream) {
        try {
            return EzhuththuUtils.readUTF8(inputStream, new TamilSentenceListener());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
