package my.interest.lang.tamil.parser.impl;

import common.lang.impl.UnknownCharacter;
import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.internal.api.TamilCharacterParserListener;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilWord;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilWordListener implements TamilCharacterParserListener<TamilWord> {

    public TamilWordListener() {
    }

    TamilWordListener(boolean readAll) {
        this.readAll = readAll;
    }

    TamilWord word = new TamilWord();

    private boolean readAll = false;

    /**
     * Called when a tamil character is encountered
     *
     * @param tamil
     * @return true when parsing is to be finished, false otherwise
     */
    @Override
    public boolean tamilCharacter(TamilCharacter tamil) {
        if (word == null) {
            word = new TamilWord();
        }
        word.add(tamil);
        return false;

    }

    @Override
    public boolean nonTamilCharacter(int nonTamil) {
        if (!readAll && nonTamil == ' ') {
            if (word == null) {
                word = new TamilWord();
            }
            return true;
        } else {
            if (word == null) {
                word = new TamilWord();
            }

            word.add(UnknownCharacter.getFor(nonTamil));
            return false;
        }
    }


    @Override
    public TamilWord get() {

        return word;
    }

    public static TamilWord readUTF8(String word) {
        try {
            return readUTF8(new ByteArrayInputStream(word.getBytes(EzhuththuUtils.ENCODING)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static TamilWord readUTF8(String word, boolean readAll) {
        try {
            return readUTF8(new ByteArrayInputStream(word.getBytes(EzhuththuUtils.ENCODING)), readAll);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static TamilWord readUTF8(InputStream inputStream) {
        return readUTF8(inputStream, false);
    }

    public static TamilWord readUTF8(InputStream inputStream, boolean readAll) {
        try {
            return EzhuththuUtils.readUTF8(inputStream, new TamilWordListener(readAll));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
