package my.interest.lang.tamil.punar;

import tamil.lang.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilWordPartContainer {

    public CharacterDigest getDIGEST_CHARACTER_TYPE() {
        digest();
        return DIGEST_CHARACTER_TYPE;
    }

    public CharacterDigest getDIGEST_CONSONANT() {
        digest();
        return DIGEST_CONSONANT;
    }

    public CharacterDigest getDIGEST_VOWEL() {
        digest();
        return DIGEST_VOWEL;
    }

    public CharacterDigest getDIGEST_SOUND_SIZE() {
        digest();
        return DIGEST_SOUND_SIZE;
    }

    public CharacterDigest getDIGEST_SOUND_STRENGTH() {
        digest();
        return DIGEST_SOUND_STRENGTH;
    }

    private CharacterDigest DIGEST_CHARACTER_TYPE = null;
    private CharacterDigest DIGEST_CONSONANT = null;
    private CharacterDigest DIGEST_VOWEL = null;
    private CharacterDigest DIGEST_SOUND_SIZE = null;
    private CharacterDigest DIGEST_SOUND_STRENGTH = null;

    private String DIGEST_CHARACTER_TYPE_STR = null;
    private String DIGEST_CONSONANT_STR = null;
    private String DIGEST_VOWEL_STR = null;
    private String DIGEST_SOUND_SIZE_STR = null;
    private String DIGEST_SOUND_STRENGTH_STR = null;

    public int size() {
        return word.size();
    }


    public TamilWord getWord() {
        return word;
    }

    private TamilWord word;

    public TamilWordPartContainer(TamilWord word) {
        this.word = word;


    }

    private boolean digested = false;

    private void digest() {
        if (digested) return;

        this.DIGEST_CONSONANT = word.getConsonantDigest();
        this.DIGEST_VOWEL = word.getVowelDigest();
        this.DIGEST_CHARACTER_TYPE = word.getCharacterTypeDigest();
        this.DIGEST_SOUND_SIZE = word.getSoundSizeDigest();
        this.DIGEST_SOUND_STRENGTH = word.getSoundStrengthDigest();

        this.DIGEST_CONSONANT_STR = DIGEST_CONSONANT.toString();
        this.DIGEST_VOWEL_STR = DIGEST_VOWEL.toString();
        this.DIGEST_CHARACTER_TYPE_STR = DIGEST_CHARACTER_TYPE.toString();
        this.DIGEST_SOUND_SIZE_STR = DIGEST_SOUND_SIZE.toString();
        this.DIGEST_SOUND_STRENGTH_STR = DIGEST_SOUND_STRENGTH.toString();
        digested = true;
    }

    public boolean isPureTamilWord() {
        return word.isPure();
    }

    public boolean isThanikKurilOtru() {
        digest();
        return DIGEST_SOUND_SIZE_STR.equals(TamilCharacter.DIGEST_SOUND_SIZE._O_.toString() + TamilCharacter.DIGEST_SOUND_SIZE._H_.toString());
    }

    public boolean iStartingWithTwoKurils() {
        digest();
        return DIGEST_SOUND_SIZE_STR.startsWith(TamilCharacter.DIGEST_SOUND_SIZE._O_.toString() + TamilCharacter.DIGEST_SOUND_SIZE._O_.toString());
    }


    public boolean isEndingWithMei() {
        digest();
        return DIGEST_CHARACTER_TYPE_STR.endsWith(TamilCharacter.DIGEST_CHAR_TYPE._M_.toString());
    }


    public boolean isEndingWithUyirMei() {
        digest();
        return DIGEST_CHARACTER_TYPE_STR.endsWith(TamilCharacter.DIGEST_CHAR_TYPE._UM_.toString());
    }

    public boolean isEndingWithUyir() {
        digest();
        return DIGEST_CHARACTER_TYPE_STR.endsWith(TamilCharacter.DIGEST_CHAR_TYPE._U_.toString());
    }

    public boolean isStartingWithUyir() {
        digest();
        return DIGEST_CHARACTER_TYPE_STR.startsWith(TamilCharacter.DIGEST_CHAR_TYPE._U_.toString());
    }

    public boolean isStartingWithMei() {
        digest();
        return DIGEST_CHARACTER_TYPE_STR.startsWith(TamilCharacter.DIGEST_CHAR_TYPE._M_.toString());
    }

    public boolean isStartingWithUyirMei() {
        digest();
        return DIGEST_CHARACTER_TYPE_STR.startsWith(TamilCharacter.DIGEST_CHAR_TYPE._UM_.toString());
    }

    public boolean isStartingWithNedil() {
        digest();
        return DIGEST_SOUND_SIZE_STR.startsWith(TamilCharacter.DIGEST_SOUND_SIZE._T_.toString());
    }


    public boolean isStartingWithKuril() {
        digest();
        return DIGEST_SOUND_SIZE_STR.startsWith(TamilCharacter.DIGEST_SOUND_SIZE._O_.toString());
    }


    @Override
    public String toString() {
        return word.toString();
    }

    public boolean isThanikKurilOtruJoined() {
        digest();
        if (word.size() < 3) return false;
        if (word.get(2).isTamilLetter()) {
            if (word.get(2).asTamilCharacter().isUyirMeyyezhuththu()) {
                return DIGEST_SOUND_SIZE_STR.startsWith(TamilCharacter.DIGEST_SOUND_SIZE._O_.toString() + TamilCharacter.DIGEST_SOUND_SIZE._H_.toString()) && DIGEST_CONSONANT.get(1).equals(DIGEST_CONSONANT.get(2));
            }
        }
        return false;


    }

    public boolean isKutriyaLugaram() {
        digest();
        if (word.size() == 1) return false;
        if (!isEndingWithKUSUDUTHUPURU()) return false;
        if (word.size() == 2) {
            if (iStartingWithTwoKurils()) return false;
        }
        return true;

    }


    public boolean isUkkurralh() {
        digest();
        if (word.size() == 1) return false;

        if (word.size() == 2) {
            if (iStartingWithTwoKurils()) return false;
        }

        return word.endsWith(TamilSimpleCharacter.U, false);

    }

    public boolean isEndingWithUgaaram() {
        digest();
        if (!DIGEST_VOWEL_STR.endsWith(TamilSimpleCharacter.U.getVowelDigest())) return false;
        return true;
    }

    public boolean isStartingWithUgaaram() {
        digest();
        if (!DIGEST_VOWEL_STR.startsWith(TamilSimpleCharacter.U.getVowelDigest())) return false;
        return true;
    }


    public boolean isEndingWithKUSUDUTHUPURU() {
        digest();
        if (!isEndingWithUgaaram()) return false;
        if (!DIGEST_SOUND_STRENGTH_STR.endsWith(TamilSimpleCharacter.DIGEST_SOUND_STRENGTH._V_.toString()))
            return false;
        return true;
    }

    //   Nannool 158
    // ஆவி - உயிர் = 12
//    ஞணநமன யரல வழள மெய் = 11
//    சாய்முகரம் (குற்றியலுகரம் = 1
//
//            மொத்தம் = 24.
    public boolean isEndingFine() {
        digest();
        if (isEndingWithUyir()) return false;

        if (DIGEST_CONSONANT_STR.endsWith(TamilSimpleCharacter.DIGEST_CONSONANT_TYPE._NGA_.toString())) {
            return false;
        }


        //Nothing ends in vallinam
        if (isEndingWithMei()) {
            if (DIGEST_CONSONANT_STR.endsWith(TamilSimpleCharacter.DIGEST_CONSONANT_TYPE._NTHA_.toString())) {
                return false;
            }
            return !DIGEST_SOUND_STRENGTH_STR.endsWith(TamilSimpleCharacter.DIGEST_SOUND_STRENGTH._V_.toString());
        }
        return true;
    }


    public boolean isStartingFine() {
        digest();
        if (isStartingWithMei()) return false;
        if (isStartingWithOneConsonantOfType(TamilCompoundCharacter.IDD)) return false;
        if (isStartingWithOneConsonantOfType(TamilCompoundCharacter.IRR)) return false;
        if (isStartingWithOneConsonantOfType(TamilCompoundCharacter.ING)) return false;
        if (isStartingWithOneConsonantOfType(TamilCompoundCharacter.INNN)) return false;
        if (isStartingWithOneConsonantOfType(TamilCompoundCharacter.IN)) return false;
        if (isStartingWithOneConsonantOfType(TamilCompoundCharacter.IR)) return false;
        if (isStartingWithOneConsonantOfType(TamilCompoundCharacter.IL)) return false;
        if (isStartingWithOneConsonantOfType(TamilCompoundCharacter.ILL)) return false;
        if (isStartingWithOneConsonantOfType(TamilCompoundCharacter.ILLL)) return false;
        if (isStartingWithOneConsonantOfType(TamilCompoundCharacter.IV)) {
            if (isStartingWithUgaaram()) {
                return false;

            }
        }
        return true;
    }

    public boolean isStartingWithNannool158_1() {
        digest();
        return DIGEST_CONSONANT_STR.startsWith(TamilSimpleCharacter.DIGEST_CONSONANT_TYPE._NYA_.toString()) ||
                DIGEST_CONSONANT_STR.startsWith(TamilSimpleCharacter.DIGEST_CONSONANT_TYPE._NTHA_.toString()) ||
                DIGEST_CONSONANT_STR.startsWith(TamilSimpleCharacter.DIGEST_CONSONANT_TYPE._MA_.toString()) ||
                DIGEST_CONSONANT_STR.startsWith(TamilSimpleCharacter.DIGEST_CONSONANT_TYPE._VA_.toString()) ||
                DIGEST_CONSONANT_STR.startsWith(TamilSimpleCharacter.DIGEST_CONSONANT_TYPE._YA_.toString());

    }

    public boolean iStartingWithKuril() {
        digest();
        return DIGEST_SOUND_SIZE_STR.startsWith(TamilCharacter.DIGEST_SOUND_SIZE._O_.toString());
    }

    public boolean isNilaiWithNannool158_2() {
        digest();
        if (word.size() == 2) {
            if (iStartingWithKuril()) {
                return word.get(1).toString().equals(TamilCompoundCharacter.IY.toString());
            } else {
                return false;
            }
        }
        if (word.size() == 1) {
            return DIGEST_VOWEL_STR.startsWith(TamilSimpleCharacter.DIGEST_VOWEL._I_.toString()) ||
                    word.get(0).toString().equals(TamilCompoundCharacter.ITH_U.toString()) ||
                    word.get(0).toString().equals(TamilCompoundCharacter.INTH_O.toString());
        }
        return false;
    }

    public boolean isStartingWithNannool158_2() {
        digest();

        return word.get(0).asTamilCharacter().isUyirMeyyezhuththu() && DIGEST_CONSONANT_STR.startsWith(TamilSimpleCharacter.DIGEST_CONSONANT_TYPE._NYA_.toString()) ||
                DIGEST_CONSONANT_STR.startsWith(TamilSimpleCharacter.DIGEST_CONSONANT_TYPE._NTHA_.toString()) ||
                DIGEST_CONSONANT_STR.startsWith(TamilSimpleCharacter.DIGEST_CONSONANT_TYPE._NYA_.toString()) ||
                DIGEST_CONSONANT_STR.startsWith(TamilSimpleCharacter.DIGEST_CONSONANT_TYPE._MA_.toString());
    }


    //ஞநமய
    public boolean isStartingWithTwoConsonantsOfTypeNannool158_2() {
        digest();

        return isStartingWithTwoConsonantsOfType(TamilCompoundCharacter.INJ) ||
                isStartingWithTwoConsonantsOfType(TamilCompoundCharacter.INTH) ||
                isStartingWithTwoConsonantsOfType(TamilCompoundCharacter.IM) ||
                isStartingWithTwoConsonantsOfType(TamilCompoundCharacter.IY);


    }

    //க ச த ப
    public boolean isStartingWithTwoConsonantsOfKASATHABA() {
        digest();

        return isStartingWithTwoConsonantsOfType(TamilCompoundCharacter.IK) ||
                isStartingWithTwoConsonantsOfType(TamilCompoundCharacter.ICH) ||
                isStartingWithTwoConsonantsOfType(TamilCompoundCharacter.ITH) ||
                isStartingWithTwoConsonantsOfType(TamilCompoundCharacter.IP);


    }


    //க ச த ப
    public boolean isEndingWithTwoConsonantsOfKASATHABA() {
        digest();

        return isEndingWithTwoConsonantsOfType(TamilCompoundCharacter.IK) ||
                isEndingWithTwoConsonantsOfType(TamilCompoundCharacter.ICH) ||
                isEndingWithTwoConsonantsOfType(TamilCompoundCharacter.ITH) ||
                isEndingWithTwoConsonantsOfType(TamilCompoundCharacter.IP);


    }

    //க ச த ப
    public boolean isStartingWithOneConsonantsOfKASATHABA() {
        digest();
        return isStartingWithOneConsonantOfType(TamilCompoundCharacter.IK) ||
                isStartingWithOneConsonantOfType(TamilCompoundCharacter.ICH) ||
                isStartingWithOneConsonantOfType(TamilCompoundCharacter.ITH) ||
                isStartingWithOneConsonantOfType(TamilCompoundCharacter.IP);


    }


    public boolean isEndingWithTwoConsonantsOfTHARRA() {
        digest();

        return isEndingWithTwoConsonantsOfType(TamilCompoundCharacter.ITH) ||
                isEndingWithTwoConsonantsOfType(TamilCompoundCharacter.IRR);


    }

    public boolean isEndingWithSomethingWhichCanBeFollowedByOttu() {
        digest();
        return isEndingWithMei() && (
                isEndingWithOneConsonantOfType(TamilCompoundCharacter.IM));

    }


    public boolean isStartingWithOneConsonantOfType(TamilCompoundCharacter c) {
        digest();
        if (word.size() < 1) return false;
        return DIGEST_CONSONANT_STR.startsWith(c.getConsonantDigest());
    }

    public boolean isEndingWithOneConsonantOfType(TamilCompoundCharacter c) {
        digest();
        if (word.size() < 1) return false;
        return DIGEST_CONSONANT_STR.endsWith(c.getConsonantDigest());
    }

    public boolean isEndingWithTwoConsonantsOfType(TamilCompoundCharacter c) {
        digest();
        if (word.size() < 2) return false;
        return DIGEST_CONSONANT_STR.endsWith(c.getConsonantDigest() + c.getConsonantDigest());
    }

    public boolean isStartingWithTwoConsonantsOfType(TamilCompoundCharacter c) {
        digest();
        if (word.size() < 2) return false;
        return DIGEST_CONSONANT_STR.startsWith(c.getConsonantDigest() + c.getConsonantDigest());
    }

    public boolean isEndingWithTwoConsonantsOfAnyType() {
        digest();
        if (word.size() < 2) return false;
        return word.getLast().asTamilCharacter().isMeyyezhuththu() && word.get(size() - 2).asTamilCharacter().isMeyyezhuththu();
    }

    public boolean isStartingWithTwoConsonantsOfType(TamilCompoundCharacter c, TamilCompoundCharacter c1) {
        digest();
        if (word.size() < 2) return false;
        return DIGEST_CONSONANT_STR.startsWith(c.getConsonantDigest() + c1.getConsonantDigest());
    }

    public boolean isStartingWithMeiFollowedByUyirMei() {
        digest();
        if (word.size() < 2) return false;
        return DIGEST_CHARACTER_TYPE_STR.startsWith(TamilSimpleCharacter.DIGEST_CHAR_TYPE._M_.toString() + TamilSimpleCharacter.DIGEST_CHAR_TYPE._UM_);
    }


    public boolean isStartingWithYagaraUdambadumeiYuir() {
        digest();
        return DIGEST_VOWEL.get(0).equals(TamilSimpleCharacter.DIGEST_VOWEL._I_.toString()) ||
                DIGEST_VOWEL.get(0).equals(TamilSimpleCharacter.DIGEST_VOWEL._AA_.toString()) ||
                DIGEST_VOWEL.get(0).equals(TamilSimpleCharacter.DIGEST_VOWEL._E_.toString()) ||
                DIGEST_VOWEL.get(0).equals(TamilSimpleCharacter.DIGEST_VOWEL._EE_.toString());
    }

    public boolean isStartingWithVagaraUdambadumeiYuir() {
        digest();
        return DIGEST_VOWEL.get(0).equals(TamilSimpleCharacter.DIGEST_VOWEL._a_.toString()) ||
                DIGEST_VOWEL.get(0).equals(TamilSimpleCharacter.DIGEST_VOWEL._AA_.toString()) ||
                DIGEST_VOWEL.get(0).equals(TamilSimpleCharacter.DIGEST_VOWEL._aa_.toString()) ||
                DIGEST_VOWEL.get(0).equals(TamilSimpleCharacter.DIGEST_VOWEL._U_.toString()) ||
                DIGEST_VOWEL.get(0).equals(TamilSimpleCharacter.DIGEST_VOWEL._UU_.toString()) ||
                DIGEST_VOWEL.get(0).equals(TamilSimpleCharacter.DIGEST_VOWEL._A_.toString()) ||
                DIGEST_VOWEL.get(0).equals(TamilSimpleCharacter.DIGEST_VOWEL._O_.toString()) ||
                DIGEST_VOWEL.get(0).equals(TamilSimpleCharacter.DIGEST_VOWEL._OO_.toString()) ||
                DIGEST_VOWEL.get(0).equals(TamilSimpleCharacter.DIGEST_VOWEL._OU_.toString());
    }

    public boolean isEndingWithYagaraUdambadumeiYuir() {
        digest();
        return DIGEST_VOWEL_STR.endsWith(TamilSimpleCharacter.DIGEST_VOWEL._I_.toString()) ||
                DIGEST_VOWEL_STR.endsWith(TamilSimpleCharacter.DIGEST_VOWEL._AA_.toString()) ||
                DIGEST_VOWEL_STR.endsWith(TamilSimpleCharacter.DIGEST_VOWEL._E_.toString()) ||
                DIGEST_VOWEL_STR.endsWith(TamilSimpleCharacter.DIGEST_VOWEL._EE_.toString());
    }

    public boolean isEndingWithVagaraUdambadumeiYuir() {
        digest();
        return DIGEST_VOWEL_STR.endsWith(TamilSimpleCharacter.DIGEST_VOWEL._a_.toString()) ||
                //   DIGEST_VOWEL_STR.endsWith(TamilSimpleCharacter.DIGEST_VOWEL._AA_.toString()) ||
                DIGEST_VOWEL_STR.endsWith(TamilSimpleCharacter.DIGEST_VOWEL._aa_.toString()) ||
                DIGEST_VOWEL_STR.endsWith(TamilSimpleCharacter.DIGEST_VOWEL._U_.toString()) ||
                DIGEST_VOWEL_STR.endsWith(TamilSimpleCharacter.DIGEST_VOWEL._UU_.toString()) ||
                DIGEST_VOWEL_STR.endsWith(TamilSimpleCharacter.DIGEST_VOWEL._A_.toString()) ||
                DIGEST_VOWEL_STR.endsWith(TamilSimpleCharacter.DIGEST_VOWEL._O_.toString()) ||
                DIGEST_VOWEL_STR.endsWith(TamilSimpleCharacter.DIGEST_VOWEL._OO_.toString()) ||
                DIGEST_VOWEL_STR.endsWith(TamilSimpleCharacter.DIGEST_VOWEL._OU_.toString());
    }

    public boolean isOneLetterChuttuUyir() {
        digest();
        if (word.size() != 1) return false;
        if (!word.get(0).isTamilLetter()) return false;
        if (!word.get(0).asTamilCharacter().isUyirezhuththu()) return false;
        return DIGEST_VOWEL_STR.endsWith(TamilSimpleCharacter.DIGEST_VOWEL._a_.toString()) ||
                DIGEST_VOWEL_STR.endsWith(TamilSimpleCharacter.DIGEST_VOWEL._E_.toString()) ||
                DIGEST_VOWEL_STR.endsWith(TamilSimpleCharacter.DIGEST_VOWEL._A_.toString()) ||
                DIGEST_VOWEL_STR.endsWith(TamilSimpleCharacter.DIGEST_VOWEL._U_.toString());

    }

    public boolean isStartingWithOtruAndFowlloedBySameInam() {
        digest();
        if (word.size() < 2) return false;
        return DIGEST_CONSONANT.get(0).equals(DIGEST_CONSONANT.get(1));


    }

    public boolean isEndingwithIru() {
        digest();
        if (size() < 2) return false;
        if (word.getLast().equals(TamilCompoundCharacter.IR_U)) {
            if (!word.get(size() - 2).isPureTamilLetter()) return false;
            if (!word.get(size() - 2).asTamilCharacter().isUyirMeyyezhuththu()) {
                return false;
            }
            return word.get(size() - 2).asTamilCharacter().getUyirPart().equals(TamilSimpleCharacter.E);
        }
        return false;
    }

    public boolean isEndingwithIdu() {
        digest();
        if (size() < 2) return false;
        if (word.getLast().equals(TamilCompoundCharacter.IDD_U)) {
            if (!word.get(size() - 2).isPureTamilLetter()) return false;
            if (!word.get(size() - 2).asTamilCharacter().isUyirMeyyezhuththu()) {
                return false;
            }
            return word.get(size() - 2).asTamilCharacter().getUyirPart().equals(TamilSimpleCharacter.E);
        }
        return false;
    }

    public boolean isInavezhuththuSeries() {
        digest();
        if (word.size() < 2) return false;
        if (!word.get(0).isPureTamilLetter() || !word.get(1).isPureTamilLetter()) return false;
        if (!word.get(0).asTamilCharacter().isMeyyezhuththu() || !word.get(1).asTamilCharacter().isUyirMeyyezhuththu())
            return false;

        if (word.get(0).equals(TamilCompoundCharacter.ING)) {
            return word.get(1).asTamilCharacter().getMeiPart().equals(TamilCompoundCharacter.IK);
        }
        if (word.get(0).equals(TamilCompoundCharacter.INJ)) {
            return word.get(1).asTamilCharacter().getMeiPart().equals(TamilCompoundCharacter.ICH);
        }
        if (word.get(0).equals(TamilCompoundCharacter.INTH)) {
            return word.get(1).asTamilCharacter().getMeiPart().equals(TamilCompoundCharacter.ITH);
        }
        if (word.get(0).equals(TamilCompoundCharacter.IM)) {
            return word.get(1).asTamilCharacter().getMeiPart().equals(TamilCompoundCharacter.IP);
        }
        return false;

    }


}
