package my.interest.lang.tamil.parser.impl;

import common.lang.impl.UnknownCharacter;
import tamil.lang.TamilCharacter;
import my.interest.lang.tamil.TamilUtils;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.internal.api.TamilCharacterParserListener;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilWordListener implements TamilCharacterParserListener<TamilWord> {

    public TamilWordListener() {}

    TamilWordListener(boolean  readAll) {
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
            //word.setPure(false);
            word.add(new UnknownCharacter(nonTamil));
            return false;
        }
    }

    @Override
    public TamilWord get() {

        return word;
    }

    public static TamilWord readUTF8(String word) {
        return readUTF8(new ByteArrayInputStream(word.getBytes()));
    }

    public static TamilWord readUTF8(String word, boolean  readAll) {
        return readUTF8(new ByteArrayInputStream(word.getBytes()), readAll);
    }

    public static TamilWord readUTF8(InputStream inputStream) {
        return readUTF8(inputStream, false);
    }

    public static TamilWord readUTF8(InputStream inputStream, boolean readAll) {
        try {
            return TamilUtils.readUTF8(inputStream, new TamilWordListener(readAll));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
