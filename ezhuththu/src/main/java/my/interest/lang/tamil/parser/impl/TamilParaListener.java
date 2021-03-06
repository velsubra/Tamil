package my.interest.lang.tamil.parser.impl;

import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.internal.api.TamilCharacterParserListener;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilPara;
import tamil.lang.TamilSentence;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilParaListener implements TamilCharacterParserListener<TamilPara> {

    TamilSentenceListener currentsentence = new TamilSentenceListener();
    TamilPara para = new TamilPara();

    /**
     * Called when a tamil character is encountered
     *
     * @param tamil
     * @return true when parsing is to be finished, false otherwise
     */
    @Override
    public boolean tamilCharacter(TamilCharacter tamil) {
        if (currentsentence.tamilCharacter(tamil)) {
            para.add(currentsentence.get());
            currentsentence = new TamilSentenceListener();

        }
        return false;
    }

    @Override
    public boolean nonTamilCharacter(int nonTamil) {
        if (nonTamil == '\n') {
            para.add(currentsentence.get());
            currentsentence = new TamilSentenceListener();
            return true;
        } else {

            if (currentsentence.nonTamilCharacter(nonTamil)) {
                para.add(currentsentence.get());
                currentsentence = new TamilSentenceListener();

            }
        }
        return false;
    }

    @Override
    public TamilPara get() {
        TamilSentence last = currentsentence.get();
        if (!last.isEmpty()) {
            para.add(last);
            currentsentence = new TamilSentenceListener();
        }
        return para;
    }

    public static TamilPara readUTF8(String para) {
        try {
            return readUTF8(new ByteArrayInputStream(para.getBytes(EzhuththuUtils.ENCODING)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static TamilPara readUTF8(InputStream inputStream) {
        try {
            return EzhuththuUtils.readUTF8(inputStream, new TamilParaListener());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
