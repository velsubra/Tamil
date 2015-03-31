package common.lang.impl;

import tamil.lang.TamilCharacter;

/**
 * <p>
 *     Represents an Unicode character.
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
        if (isTamilLetter()) {
            return ((TamilCharacter) this).isPureTamilLetter();
        }
        return false;
    }

    public  int getNumericStrength() {
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

    public String getSoundSizeDigest() {
        return "_0.0_";
    }

    public String getSoundStrengthDigest() {
        return "_00_";
    }

    public abstract int getCodePointsCount();


}
