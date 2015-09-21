package tamil.lang.unicode;

import tamil.lang.*;

public class UnicodeAayuthavezhuthu extends AbstractTamilCharacter implements AayuthaEzhuthu {
    public static final UnicodeAayuthavezhuthu AKTHU = new UnicodeAayuthavezhuthu('\u0B83');
    private int value;

    public UnicodeAayuthavezhuthu(int value) {
        this.value = value;
    }

    @Override
    public String getCharacterTypeDigest() {
        return TamilCharacter.DIGEST_CHAR_TYPE._AY_.toString();
    }

    @Override
    public int getCodePointsCount() {
        return 1;
    }

    @Override
    public int getNumericStrength() {
        return 1;
    }

    public int getValue() {
        return value;
    }
}
