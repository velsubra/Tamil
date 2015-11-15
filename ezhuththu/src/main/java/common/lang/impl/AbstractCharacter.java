package common.lang.impl;

import my.interest.lang.tamil.impl.FeatureSet;
import tamil.lang.CharacterDigest;
import tamil.lang.TamilCharacter;
import tamil.lang.api.regex.RXIncludeCanonicalEquivalenceFeature;
import tamil.lang.exception.TamilPlatformException;

import java.util.List;

/**
 * <p>
 * Represents an abstract character.  It could be Unicode character.
 * </p>
 *
 * @author velsubra
 */
public abstract class AbstractCharacter implements common.lang.Character, Comparable {
//    private int columnNumber;
//
//    @Override
//    public int getColumnNumber() {
//        return columnNumber;
//    }
//
//    public void setColumnNumber(int columnNumber) {
//        this.columnNumber = columnNumber;
//    }
//
//    @Override
//    public int getLineNumber() {
//        return lineNumber;
//    }
//
//    public void setLineNumber(int lineNumber) {
//        this.lineNumber = lineNumber;
//    }
//
//    @Override
//    public Word getContainingWord() {
//        return containingWord;
//    }
//
//    public void setContainingWord(Word containingWord) {
//        this.containingWord = containingWord;
//    }
//
//
//    private int lineNumber;
//
//    private Word containingWord;

    public boolean isTamilLetter() {
        return TamilCharacter.class.isAssignableFrom(getClass());
    }

    public boolean isPureTamilLetter() {

        return false;
    }

    public boolean isWordToStartWith() {
        return false;
    }

    public boolean isWordToEndWith() {
        return false;
    }

    public boolean isWordToContain() {
        return false;
    }

    public int getNumericStrength() {
        return 0;
    }

    public TamilCharacter asTamilCharacter() {
        return (TamilCharacter) this;
    }

    public String getConsonantDigest() {
        return "_00_";
    }

    public String getVowelDigest() {

        return "_00_";
    }

    public String getCharacterTypeDigest() {
        return "_00_";
    }

    public String getPositionDigest() {
        return "_00_";
    }


    /**
     * Generic method to get the value of a digest
     *
     * @param digest_for the type of digest
     * @return the value
     */
    public String getDigest(CharacterDigest.CHAR_DIGEST digest_for) {
        if (digest_for == null) {
            throw new TamilPlatformException("Digest type can not be null");
        }
        switch (digest_for) {
            case VOWEL:
                return getVowelDigest();
            case CONSONANT:
                return getConsonantDigest();
            case CHAR_TYPE:
                return getCharacterTypeDigest();
            case SOUND_SIZE:
                return getSoundSizeDigest();
            case SOUND_STRENGTH:
                return getSoundStrengthDigest();
            case POSITION:
                return getPositionDigest();
            default:
                throw new TamilPlatformException("Undefined:" + digest_for);

        }
    }

    public String getSoundSizeDigest() {
        return "_0.0_";
    }

    public String getSoundStrengthDigest() {
        return "_00_";
    }

    public abstract int getMinCodePointsCount();

    public abstract List<int[]> getCodePoints(FeatureSet set) ;

    public String toUnicodeRegEXRepresentation(FeatureSet set) {
        boolean includeCanon = set.isFeatureEnabled(RXIncludeCanonicalEquivalenceFeature.class);
        StringBuffer buffer = new StringBuffer("(?:");
        List<int[]> codepointslist = getCodePoints(set);
        boolean first = true;
        if (codepointslist.size() > 1) {
            buffer.append("(");
        }
        for (int[] codepoints : codepointslist) {
            if (!first) {
                buffer.append("|");
            }
            first = false;
            buffer.append("(");
            for (int code : codepoints) {
                String val = Integer.toHexString(code);
                while (val.length() < 4) {
                    val = "0" + val;
                }
                buffer.append("\\u" + val);
            }
            buffer.append(")");

        }
        if (codepointslist.size() > 1) {
            buffer.append(")");
        }
        buffer.append(")");
        return buffer.toString();
    }


    /**
     * Translit the character to roman letter(s)
     *
     * @return returns roman letters
     */
    public abstract String translitToEnglish();


}
