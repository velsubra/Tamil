package tamil.lang;

import common.lang.impl.AbstractCharacter;
import tamil.lang.exception.NoMeiPartException;
import tamil.lang.exception.NoUyirPartException;

/**
 * <p>
 * Represents a single abstract Tamil character (தமிழெழுத்து).
 * The erroneous representation  of  தமிழெழுத்து in Unicode is resolved at this level.
 *
 * You can use {@link TamilWord#from(String)} to read a tamil word.
 *
 * Note: All  TamilCharacters are immutable. The platform make sure there is exactly one instance of TamilCharacter for each recognized Tamil character(247 + extended)
 *
 * </p>
 *
 * @author velsubra
 *
 * @see TamilCharacterLookUpContext#lookup(int)
 * @see TamilWord#from(String)
 */
public abstract class TamilCharacter extends AbstractCharacter {

    protected TamilCharacter() {

    }





    protected static final int UYIR = 1;
    protected static final int UYIR_MEI = 2;
    protected static final int MEI = 4;
    protected static final int AAYTHAM = 8;

    protected static final int VADA_MOZHI = 16;

    protected static final int KURIL = 32;
    //protected static final int NEDIL = 64;


    protected static final int VALLINAM = 128;
    protected static final int MELLINAM = 256;
    protected static final int IDAIYINAM = 512;

    /**
     * Integer holding the specification of character. Bits in this number are used to store specification of this character.
     * E.g) the least significant bit indicates if this character is a vowel. This is not to be directly used by the end-user.
     * Please use {@link #isUyirezhuththu()}  instead.
     */
    protected int typeSpecification = 0;


    /**
     * Returns if the character is உயிரெழுத்து
     *
     * @return true for உயிரெழுத்து , false otherwise.
     */
    public final boolean isUyirezhuththu() {
        return (typeSpecification & UYIR) != 0;
    }

    /**
     * Returns if the character is உயிர்மெய்யெழுத்து
     *
     * @return true for உயிர்மெய்யெழுத்து , false otherwise
     */
    public final boolean isUyirMeyyezhuththu() {
        return (typeSpecification & UYIR_MEI) != 0;
    }

    /**
     * Returns if the character represented is  மெய்யெழுத்து
     *
     * @return true for மெய்யெழுத்து false for others.
     */
    public final boolean isMeyyezhuththu() {
        return (typeSpecification & MEI) != 0;
    }

    /**
     * Returns if the character represented is  a borrowed letter
     *
     * @return true for borrowed letter false for all pure tamil 247 characters.
     */
    public final boolean isVadaMozhiYezhuththu() {
        return (typeSpecification & VADA_MOZHI) != 0;
    }

    /**
     * Returns if the character is ஃ, ஆய்தவெழுத்து.
     *
     * @return true for ஃ false for others
     */
    public final boolean isAaythavezhuththu() {
        return (typeSpecification & AAYTHAM) != 0;
    }


    /**
     * Returns if the character is  குறில்
     *
     * @return true for குறில் , false otherwise
     */
    public final boolean isKurilezhuththu() {
        return (typeSpecification & KURIL) != 0;
    }

    /**
     * Returns if the character is   வல்லினம்
     *
     * @return true for வல்லினம், false otherwise
     */
    public final boolean isVallinam() {
        return (typeSpecification & VALLINAM) != 0;
    }

    /**
     * Returns if the character is    மெல்லினம்
     *
     * @return true for மெல்லினம் , false otherwise
     */
    public final boolean isMellinam() {
        return (typeSpecification & MELLINAM) != 0;
    }

    /**
     * Returns if the character is    இடையினம்
     *
     * @return true for இடையினம்  , false otherwise
     */
    public final boolean isIdaiyinam() {
        return (typeSpecification & IDAIYINAM) != 0;
    }

    /**
     * Returns if the character is  one for the first 30 letters.
     *
     * @return true for , false otherwise
     */
    public final boolean isMuthalezhuththu() {
        return isUyirMeyyezhuththu() || isMeyyezhuththu();
    }

    /**
     * <p>
     * Returns if the character is simple character ie) instance of {@link TamilSimpleCharacter}
     * </p>
     *
     * @return true for , false otherwise
     */
    public final boolean isTamilSimpleCharacter() {
        return TamilSimpleCharacter.class.isAssignableFrom(getClass());
    }

    /**
     * Gets Mei part of the letter
     * <p>
     * E.g)
     * {@link  TamilCompoundCharacter#IK} is returned for  {@link TamilSimpleCharacter#KA}
     * </p>
     *
     * @return letter representing the Mei part.
     * @throws NoMeiPartException if no Mei part is available.
     */
    public abstract TamilCompoundCharacter getMeiPart() throws NoMeiPartException;


    /**
     * Gets Uyir part of teh letter
     * <p>
     * E.g)
     * {@link TamilSimpleCharacter#a} is returned for  {@link TamilSimpleCharacter#KA}
     * </p>
     *
     * @return letter representing the Uyir part
     * @throws NoUyirPartException if no Uyir part is available
     */
    public abstract TamilSimpleCharacter getUyirPart() throws NoUyirPartException;


    /**
     * Adds uyir to a given consonant
     *
     * @param s should be Uyir
     * @return the uyirmei letter.
     */
    public abstract TamilCharacter addUyir(TamilSimpleCharacter s);

    /**
     * Returns if the character is  one of 247 letters of tamil
     *
     * @return true for a pure tamil letter, false otherwise
     */
    public boolean isPureTamilLetter() {
        if (isVadaMozhiYezhuththu()) return false;
        if (isAaythavezhuththu()) return true;
        if (isMeyyezhuththu()) return true;
        if (isUyirezhuththu()) return true;
        if (isUyirMeyyezhuththu()) return true;
        return false;

    }


    public static enum DIGEST_CONSONANT_TYPE {
        _AKTHU_, _KA_, _NGA_, _SA_, _NYA_, _DA_, _NNNA_, _THA_, _NTHA_, _PA_, _MA_, _YA_, _RA_, _LA_, _VA_, _LLLA_, _LLA_, _RRA_, _NA_;

        @Override
        public String toString() {
            return "_" + ordinalToTwoCharString(this.ordinal()) + "_";
        }
    }

    private static String ordinalToTwoCharString(int index) {
        if (index < 10) {
            return "0" + index;
        } else {
            return String.valueOf(index);
        }
    }

    public static enum DIGEST_CHAR_TYPE {
        _AY_, _U_, _M_, _UM_;

        @Override
        public String toString() {
            return "_" + (this.ordinal() + 1) + "_";
        }
    }

    public static enum DIGEST_VOWEL {
        _a_, _aa_, _E_, _EE_, _U_, _UU_, _A_, _AA_, _I_, _O_, _OO_, _OU_;

        @Override
        public String toString() {
            return "_" + ordinalToTwoCharString(this.ordinal() + 1) + "_";
        }
    }

    private String charDigest = null;

    public String getCharacterTypeDigest() {
        if (charDigest == null) {
            if (isUyirMeyyezhuththu()) {
                charDigest = DIGEST_CHAR_TYPE._UM_.toString();
            } else if (isAaythavezhuththu()) {
                charDigest = DIGEST_CHAR_TYPE._AY_.toString();
            } else if (isMeyyezhuththu()) {
                charDigest = DIGEST_CHAR_TYPE._M_.toString();
            } else if (isUyirezhuththu()) {
                charDigest = DIGEST_CHAR_TYPE._U_.toString();
            } else {
                charDigest = super.getCharacterTypeDigest();
            }
        }
        return charDigest;
    }

    public static enum DIGEST_SOUND_STRENGTH {
        _M_, _I_, _V_;

        @Override
        public String toString() {
            return "_" + (this.ordinal() + 1) + "_";
        }
    }

    public static enum DIGEST_SOUND_SIZE {
        _H_, _O_, _T_;

        @Override
        public String toString() {
            double val = 0;
            if (this == _H_) {
                val = 0.5;
            } else if (this == _O_) {
                val = 1.0;
            } else {
                val = 2.0;
            }
            return "_" + val + "_";
        }
    }

    private String soundDigest = null;

    public String getSoundSizeDigest() {
        if (soundDigest == null) {


            if (isAaythavezhuththu() || isMeyyezhuththu()) {
                soundDigest = DIGEST_SOUND_SIZE._H_.toString();
            } else if (isKurilezhuththu()) {
                soundDigest = DIGEST_SOUND_SIZE._O_.toString();
            } else {
                soundDigest = DIGEST_SOUND_SIZE._T_.toString();
            }
        }
        return soundDigest;
    }


    @Override
    public int compareTo(Object o) {

        if (!TamilCharacter.class.isAssignableFrom(o.getClass())) {
            return -1;
        }
        return new Integer(getNumericStrength()).compareTo(((TamilCharacter) o).getNumericStrength());
    }


    public boolean equals(TamilCharacter ch, boolean full) {
        if (ch == null) return false;
        if (full) {
            return compareTo(ch) == 0;
        } else {

            if (ch.isAaythavezhuththu()) {
                return isAaythavezhuththu();
            } else {
                if (isAaythavezhuththu()) {
                    return false;
                }
            }

            if (ch.isUyirezhuththu()) {
                if (isUyirezhuththu() || isUyirMeyyezhuththu()) {
                    return ch.getUyirPart().getValue() <= getUyirPart().getValue();
                } else {
                    return false;
                }
            }

            if (ch.isMeyyezhuththu()) {
                if (isMeyyezhuththu() || isUyirMeyyezhuththu()) {
                    return ch.getMeiPart().getConsonant() == getMeiPart().getConsonant();
                } else {
                    return false;
                }
            }

            if (ch.isUyirMeyyezhuththu()) {
                if (isUyirMeyyezhuththu()) {
                    return ch.getMeiPart().getConsonant() == getMeiPart().getConsonant() && ch.getUyirPart().getValue() <= getUyirPart().getValue();
                } else {
                    return false;
                }
            }

            throw new RuntimeException("UnExpected char:" + ch);

        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (TamilCharacter.class.isAssignableFrom(o.getClass())) {
            return equals((TamilCharacter) o, true);

        } else {
            return false;
        }
    }


}
