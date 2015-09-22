package tamil.lang;


import common.lang.CompoundCharacter;
import tamil.lang.exception.NoUyirPartException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * Represents all more than 2 bytes characters. (4 bytes, 6 bytes ....etc)
 * <p/>
 * <pre>
 * 4 bytes characters  (pure tamil only)
 * க் - ன்             18
 * கா - னா  18
 * |
 * கௌ    11       198
 * ----------------------
 * 216
 * ----------------------
 * </pre>
 * <p/>
 * There are other 4 bytes extended characters and  other extended characters of 6 bytes and 8 bytes etc ....
 * </p>
 *
 * @author velsubra
 *
 * @see TamilCharacterLookUpContext#lookup(int)
 * @see TamilWord#from(String)
 */
public final class TamilCompoundCharacter extends TamilCharacter implements CompoundCharacter {
    static List<TamilCompoundCharacter> characters = new ArrayList<TamilCompoundCharacter>();

    private TamilCompoundCharacter(int consonant, int vowel) {
        this(consonant, vowel, null);

    }

    private TamilCompoundCharacter(int consonant, int vowel , int[]vd) {
       // this(new int[]{consonant}, vowel);
        this.consonants = new int[]{consonant};
        this.vowel = vowel;
        characters.add(this);
        this.vowelDecomposed = vd;

    }
//
//    private TamilCompoundCharacter(int[] consonants, int vowel) {
//        this.consonants = consonants;
//        this.vowel = vowel;
//        characters.add(this);
//    }


    int[] consonants = null;
    int vowel;
    int[] vowelDecomposed =  null;

    void init() {
        //Order is important
        this.typeSpecification |= _isUyirezhuththu() ? UYIR : 0;
        this.typeSpecification |= _isMeyyezhuththu() ? MEI : 0;
        this.typeSpecification |= _isUyirMeyyezhuththu() ? UYIR_MEI : 0;
        this.typeSpecification |= _isAaythavezhuththu() ? AAYTHAM : 0;
        this.typeSpecification |= _isVadaMozhiYezhuththu() ? VADA_MOZHI : 0;
        this.typeSpecification |= _isKurilezhuththu() ? KURIL : 0;
        this.typeSpecification |= _isNtedizhuththu() ? NEDIL : 0;
        this.typeSpecification |= _isVallinam() ? VALLINAM : 0;
        this.typeSpecification |= _isMellinam() ? MELLINAM : 0;
        this.typeSpecification |= _isIdaiyinam() ? IDAIYINAM : 0;

    }


    @Override
    public int[] getConsonants() {
        return consonants.clone();
    }


    @Override
    public int getVowel() {
        return vowel;
    }

    public static final int PULLI = '\u0BCD';


    private boolean _isMeyyezhuththu() {
        return consonants.length == 1 && vowel == PULLI && TamilSimpleCharacter.isUyirMeyyezhuththu(consonants[0]);
    }

    private boolean _isVadaMozhiYezhuththu() {
        return consonants.length == 1 && TamilSimpleCharacter.isVadaMozhiYezhuththu(consonants[0]);
    }

    private boolean _isAaythavezhuththu() {
        return false;
    }

    private boolean _isUyirezhuththu() {
        return false;
    }

    private boolean _isUyirMeyyezhuththu() {
        return !isMeyyezhuththu();
    }

    private boolean _isKurilezhuththu() {
        //அ and its series is part of TamilSimpleCharacter ; so it wont be here.
        return  isE() || isU() || isA() || isO();
    }

    private boolean _isNtedizhuththu() {
        //அ and its series is part of TamilSimpleCharacter ; so it wont be here.
        return !_isMeyyezhuththu() && !_isKurilezhuththu();
    }

    /**
     * Returns the first code point for the underlying consonant.
     * @return  the code point value for the consonant.
     */
    int getConsonant() {
        return consonants[0];
    }


    //1 க

    /**
     * returns if  க
     *
     * @return true if the current letter is க
     */
    public boolean isKa() {
        return (consonants.length == 1) && TamilSimpleCharacter.isKa(getConsonant());
    }

    // 2  ங
    /**
     * returns if  ங
     *
     * @return true if the current letter is ங
     */
    public boolean isNga() {
        return (consonants.length == 1) && TamilSimpleCharacter.isNga(getConsonant());
    }

    // 3 ச
    /**
     * returns if  ச
     *
     * @return true if the current letter is ச
     */
    public boolean isSa() {
        return (consonants.length == 1) && TamilSimpleCharacter.isSa(getConsonant());
    }

    // 4 ஞ
    /**
     * returns if  ஞ
     *
     * @return true if the current letter is ஞ
     */
    public boolean isNya() {
        return (consonants.length == 1) && TamilSimpleCharacter.isNya(getConsonant());
    }

    // 5 ட
    public boolean isDa() {
        return (consonants.length == 1) && TamilSimpleCharacter.isDa(getConsonant());
    }

    // 6 ண
    public boolean isNNNa() {
        return (consonants.length == 1) && TamilSimpleCharacter.isNNNa(getConsonant());
    }

    // 7 த
    public boolean isTha() {
        return (consonants.length == 1) && TamilSimpleCharacter.isTha(getConsonant());
    }

    // 8 ந
    public boolean isNtha() {
        return (consonants.length == 1) && TamilSimpleCharacter.isNtha(getConsonant());
    }

    // 9 ப
    public boolean isPa() {
        return (consonants.length == 1) && TamilSimpleCharacter.isPa(getConsonant());
    }

    // 10 ம
    public boolean isMa() {
        return (consonants.length == 1) && TamilSimpleCharacter.isMa(getConsonant());
    }

    // 11 ய
    public boolean isYa() {
        return (consonants.length == 1) && TamilSimpleCharacter.isYa(getConsonant());
    }

    // 12 ர
    /**
     * returns if  ர
     *
     * @return true if the current letter is ர, false otherwise
     */
    public boolean isRa() {
        return (consonants.length == 1) && TamilSimpleCharacter.isRa(getConsonant());
    }

    // 13 ல
    public boolean isLa() {
        return (consonants.length == 1) && TamilSimpleCharacter.isLa(getConsonant());
    }

    // 14 வ
    public boolean isVa() {
        return (consonants.length == 1) && TamilSimpleCharacter.isVa(getConsonant());
    }

    // 15 ழ
    /**
     * returns if  ழ
     *
     * @return true if the current letter is ழ, false otherwise
     */
    public boolean isLLLa() {
        return (consonants.length == 1) && TamilSimpleCharacter.isLLLa(getConsonant());
    }

    // 16 ள
    /**
     * returns if  ள
     *
     * @return true if the current letter is ள, false otherwise
     */
    public boolean isLLa() {
        return (consonants.length == 1) && TamilSimpleCharacter.isLLa(getConsonant());
    }

    // 17 ற
    /**
     * returns if  ற
     *
     * @return true if the current letter is ற, false otherwise
     */
    public boolean isRRa() {
        return (consonants.length == 1) && TamilSimpleCharacter.isRRa(getConsonant());
    }

    // 18  ன
    public boolean isNa() {
        return (consonants.length == 1) && TamilSimpleCharacter.isNa(getConsonant());
    }


//
//    // 1 extended     ஶ
//    public boolean is_SHA() {
//        return (consonants.length == 1) && TamilSimpleCharacter.is_SHA(getConsonant());
//    }
//    public static boolean is_SHA(int value) {
//
//    }
//
//    // 2 extended  ஜ
//    public boolean is_JA() {
//        return (consonants.length == 1) && TamilSimpleCharacter.is_JA(getConsonant());
//    }
//
//    // 3 extended  ஷ
//    public boolean is_SSA() {
//        return (consonants.length == 1) && TamilSimpleCharacter.is_SSA(getConsonant());
//    }
//
//    // 4 extended   ஸ
//    public boolean is_SA() {
//        return (consonants.length == 1) && TamilSimpleCharacter.is_SA(getConsonant());
//    }
//
//    // 6 extended   ஹ
//    public boolean is_HA() {
//        return (consonants.length == 1) && TamilSimpleCharacter.is_HA(getConsonant());
//    }
//
//
//    // 6 extended  க்ஷ
//    public boolean is_KSHA() {
//        return (consonants.length == 3) && TamilSimpleCharacter.isKa(getConsonant()) && consonants[1] =='\u0BCD' && consonants[2] =='\u0BB7';
//    }


    // ஆ

    /**
     *
     * @return     true if ஆ, false otherwise
     */
    public boolean isaa() {
        return (consonants.length == 1) && isaa(vowel);
    }

    //3 e   இ
    /**
     *
     * @return     true if இ, false otherwise
     */
    public boolean isE() {
        return (consonants.length == 1) && isE(vowel);
    }

    //4 ee   ஈ
    public boolean isEE() {
        return (consonants.length == 1) && isEE(vowel);
    }

    //5 u   உ
    public boolean isU() {
        return (consonants.length == 1) && isU(vowel);
    }

    //6 uu ஊ
    public boolean isUU() {
        return (consonants.length == 1) && isUU(vowel);
    }

    //7 A எ
    /**
     *
     * @return     true if எ, false otherwise
     */
    public boolean isA() {
        return (consonants.length == 1) && isA(vowel);
    }

    //8 AA ஏ
    /**
     *
     * @return     true if ஏ, false otherwise
     */
    public boolean isAA() {
        return (consonants.length == 1) && isAA(vowel);
    }

    //9 i  ஐ
    /**
     *
     * @return     true if ஐ, false otherwise
     */
    public boolean isI() {
        return (consonants.length == 1) && isI(vowel);
    }

    //10 o  ஒ
    public boolean isO() {
        return (consonants.length == 1) && isO(vowel);
    }

    //11  oo  ஓ
    public boolean isOO() {
        return (consonants.length == 1) && isOO(vowel);
    }

    //12 OU  ஔ
    public boolean isOU() {
        return (consonants.length == 1) && isOU(vowel);
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (int va : consonants) {
            buffer.append(Character.toString((char) va));
        }
        buffer.append(Character.toString((char) getVowel()));
        return buffer.toString();
    }


    //These are not real Uyirs (vowels) but the symbols of them.

    private static boolean isContinuation(int value) {
        return isaa(value) || isE(value) || isEE(value) || isU(value) || isUU(value) || isA(value) || isAA(value) || isI(value) || isO(value) || isOO(value) || isOU(value);
    }

    public static final int aa = '\u0BBE';

    // ஆ
    public static boolean isaa(int value) {
        return value == aa;
    }

    public static final int E = '\u0BBF';

    //3 e   இ
    public static boolean isE(int value) {
        return value == E;
    }

    public static final int EE = '\u0BC0';

    //4 ee   ஈ
    public static boolean isEE(int value) {
        return value == EE;
    }

    public static final int U = '\u0BC1';

    //5 u   உ
    public static boolean isU(int value) {
        return value == U;
    }

    public static final int UU = '\u0BC2';

    //6 uu ஊ
    public static boolean isUU(int value) {
        return value == UU;
    }

    public static final int A = '\u0BC6';

    //7 A எ
    public static boolean isA(int value) {
        return value == A;
    }

    public static final int AA = '\u0BC7';

    //8 AA ஏ
    public static boolean isAA(int value) {
        return value == AA;
    }

    public static final int I = '\u0BC8';

    //9 i  ஐ
    public static boolean isI(int value) {
        return value == I;
    }

    public static final int O = '\u0BCA';

    //0BCA;TAMIL VOWEL SIGN O;Mc;0;L;0BC6 0BBE;;;;N;;;;;
    public static final int[] O__ = new int[]{A,aa};

    //10 o  ஒ
    public static boolean isO(int value) {
        return value == O;
    }

    public static final int OO = '\u0BCB';

    public static final int[] OO__ = new int[]{AA,aa};

    //11  oo  ஓ
    public static boolean isOO(int value) {
        return value == OO;
    }

    public static final int OU = '\u0BCC';



    //12 OU  ஔ
    public static boolean isOU(int value) {
        return value == OU;
    }


    public static final int OU_ = '\u0BD7';

    //length mark for ஔ
    public static boolean isOU_(int value) {
        return value == OU_;
    }
    public static final int[] OU__ = new int[]{A,OU_};




    public static final TamilCompoundCharacter IK = new TamilCompoundCharacter(TamilSimpleCharacter.KA.getValue(), PULLI);
    public static final TamilCompoundCharacter ING = new TamilCompoundCharacter(TamilSimpleCharacter.NGA.getValue(), PULLI);
    public static final TamilCompoundCharacter ICH = new TamilCompoundCharacter(TamilSimpleCharacter.SA.getValue(), PULLI);
    public static final TamilCompoundCharacter INJ = new TamilCompoundCharacter(TamilSimpleCharacter.NYA.getValue(), PULLI);
    public static final TamilCompoundCharacter IDD = new TamilCompoundCharacter(TamilSimpleCharacter.DA.getValue(), PULLI);
    public static final TamilCompoundCharacter INNN = new TamilCompoundCharacter(TamilSimpleCharacter.NNNA.getValue(), PULLI);
    public static final TamilCompoundCharacter ITH = new TamilCompoundCharacter(TamilSimpleCharacter.THA.getValue(), PULLI);
    public static final TamilCompoundCharacter INTH = new TamilCompoundCharacter(TamilSimpleCharacter.NTHA.getValue(), PULLI);
    public static final TamilCompoundCharacter IP = new TamilCompoundCharacter(TamilSimpleCharacter.PA.getValue(), PULLI);
    public static final TamilCompoundCharacter IM = new TamilCompoundCharacter(TamilSimpleCharacter.MA.getValue(), PULLI);
    public static final TamilCompoundCharacter IY = new TamilCompoundCharacter(TamilSimpleCharacter.YA.getValue(), PULLI);
    public static final TamilCompoundCharacter IR = new TamilCompoundCharacter(TamilSimpleCharacter.RA.getValue(), PULLI);
    public static final TamilCompoundCharacter IL = new TamilCompoundCharacter(TamilSimpleCharacter.LA.getValue(), PULLI);
    public static final TamilCompoundCharacter IV = new TamilCompoundCharacter(TamilSimpleCharacter.VA.getValue(), PULLI);
    public static final TamilCompoundCharacter ILLL = new TamilCompoundCharacter(TamilSimpleCharacter.LLLA.getValue(), PULLI);
    public static final TamilCompoundCharacter ILL = new TamilCompoundCharacter(TamilSimpleCharacter.LLA.getValue(), PULLI);
    public static final TamilCompoundCharacter IRR = new TamilCompoundCharacter(TamilSimpleCharacter.RRA.getValue(), PULLI);
    public static final TamilCompoundCharacter IN = new TamilCompoundCharacter(TamilSimpleCharacter.NA.getValue(), PULLI);

    public static final TamilCompoundCharacter IJ_ = new TamilCompoundCharacter(TamilSimpleCharacter.JA_.getValue(), PULLI);
    public static final TamilCompoundCharacter IH_ = new TamilCompoundCharacter(TamilSimpleCharacter.HA_.getValue(), PULLI);
    public static final TamilCompoundCharacter ISH_ = new TamilCompoundCharacter(TamilSimpleCharacter.SHA_.getValue(), PULLI);
    public static final TamilCompoundCharacter ISS_ = new TamilCompoundCharacter(TamilSimpleCharacter.SSA_.getValue(), PULLI);
    public static final TamilCompoundCharacter ISSS_ = new TamilCompoundCharacter(TamilSimpleCharacter.SSSA_.getValue(), PULLI);


    public static final TamilCompoundCharacter IK_aa = new TamilCompoundCharacter(TamilSimpleCharacter.KA.getValue(), aa);
    public static final TamilCompoundCharacter ING_aa = new TamilCompoundCharacter(TamilSimpleCharacter.NGA.getValue(), aa);
    public static final TamilCompoundCharacter ICH_aa = new TamilCompoundCharacter(TamilSimpleCharacter.SA.getValue(), aa);
    public static final TamilCompoundCharacter INJ_aa = new TamilCompoundCharacter(TamilSimpleCharacter.NYA.getValue(), aa);
    public static final TamilCompoundCharacter IDD_aa = new TamilCompoundCharacter(TamilSimpleCharacter.DA.getValue(), aa);
    public static final TamilCompoundCharacter INNN_aa = new TamilCompoundCharacter(TamilSimpleCharacter.NNNA.getValue(), aa);
    public static final TamilCompoundCharacter ITH_aa = new TamilCompoundCharacter(TamilSimpleCharacter.THA.getValue(), aa);
    public static final TamilCompoundCharacter INTH_aa = new TamilCompoundCharacter(TamilSimpleCharacter.NTHA.getValue(), aa);
    public static final TamilCompoundCharacter IP_aa = new TamilCompoundCharacter(TamilSimpleCharacter.PA.getValue(), aa);
    public static final TamilCompoundCharacter IM_aa = new TamilCompoundCharacter(TamilSimpleCharacter.MA.getValue(), aa);
    public static final TamilCompoundCharacter IY_aa = new TamilCompoundCharacter(TamilSimpleCharacter.YA.getValue(), aa);
    public static final TamilCompoundCharacter IR_aa = new TamilCompoundCharacter(TamilSimpleCharacter.RA.getValue(), aa);
    public static final TamilCompoundCharacter IL_aa = new TamilCompoundCharacter(TamilSimpleCharacter.LA.getValue(), aa);
    public static final TamilCompoundCharacter IV_aa = new TamilCompoundCharacter(TamilSimpleCharacter.VA.getValue(), aa);
    public static final TamilCompoundCharacter ILLL_aa = new TamilCompoundCharacter(TamilSimpleCharacter.LLLA.getValue(), aa);
    public static final TamilCompoundCharacter ILL_aa = new TamilCompoundCharacter(TamilSimpleCharacter.LLA.getValue(), aa);
    public static final TamilCompoundCharacter IRR_aa = new TamilCompoundCharacter(TamilSimpleCharacter.RRA.getValue(), aa);
    public static final TamilCompoundCharacter IN_aa = new TamilCompoundCharacter(TamilSimpleCharacter.NA.getValue(), aa);

    public static final TamilCompoundCharacter IJ_aa = new TamilCompoundCharacter(TamilSimpleCharacter.JA_.getValue(), aa);
    public static final TamilCompoundCharacter IH_aa = new TamilCompoundCharacter(TamilSimpleCharacter.HA_.getValue(), aa);
    public static final TamilCompoundCharacter ISH_aa = new TamilCompoundCharacter(TamilSimpleCharacter.SHA_.getValue(), aa);
    public static final TamilCompoundCharacter ISS_aa = new TamilCompoundCharacter(TamilSimpleCharacter.SSA_.getValue(), aa);
    public static final TamilCompoundCharacter ISSS_aa = new TamilCompoundCharacter(TamilSimpleCharacter.SSSA_.getValue(), aa);


    public static final TamilCompoundCharacter IK_E = new TamilCompoundCharacter(TamilSimpleCharacter.KA.getValue(), E);
    public static final TamilCompoundCharacter ING_E = new TamilCompoundCharacter(TamilSimpleCharacter.NGA.getValue(), E);
    public static final TamilCompoundCharacter ICH_E = new TamilCompoundCharacter(TamilSimpleCharacter.SA.getValue(), E);
    public static final TamilCompoundCharacter INJ_E = new TamilCompoundCharacter(TamilSimpleCharacter.NYA.getValue(), E);
    public static final TamilCompoundCharacter IDD_E = new TamilCompoundCharacter(TamilSimpleCharacter.DA.getValue(), E);
    public static final TamilCompoundCharacter INNN_E = new TamilCompoundCharacter(TamilSimpleCharacter.NNNA.getValue(), E);
    public static final TamilCompoundCharacter ITH_E = new TamilCompoundCharacter(TamilSimpleCharacter.THA.getValue(), E);
    public static final TamilCompoundCharacter INTH_E = new TamilCompoundCharacter(TamilSimpleCharacter.NTHA.getValue(), E);
    public static final TamilCompoundCharacter IP_E = new TamilCompoundCharacter(TamilSimpleCharacter.PA.getValue(), E);
    public static final TamilCompoundCharacter IM_E = new TamilCompoundCharacter(TamilSimpleCharacter.MA.getValue(), E);
    public static final TamilCompoundCharacter IY_E = new TamilCompoundCharacter(TamilSimpleCharacter.YA.getValue(), E);
    public static final TamilCompoundCharacter IR_E = new TamilCompoundCharacter(TamilSimpleCharacter.RA.getValue(), E);
    public static final TamilCompoundCharacter IL_E = new TamilCompoundCharacter(TamilSimpleCharacter.LA.getValue(), E);
    public static final TamilCompoundCharacter IV_E = new TamilCompoundCharacter(TamilSimpleCharacter.VA.getValue(), E);
    public static final TamilCompoundCharacter ILLL_E = new TamilCompoundCharacter(TamilSimpleCharacter.LLLA.getValue(), E);
    public static final TamilCompoundCharacter ILL_E = new TamilCompoundCharacter(TamilSimpleCharacter.LLA.getValue(), E);
    public static final TamilCompoundCharacter IRR_E = new TamilCompoundCharacter(TamilSimpleCharacter.RRA.getValue(), E);
    public static final TamilCompoundCharacter IN_E = new TamilCompoundCharacter(TamilSimpleCharacter.NA.getValue(), E);

    public static final TamilCompoundCharacter IJ_E = new TamilCompoundCharacter(TamilSimpleCharacter.JA_.getValue(), E);
    public static final TamilCompoundCharacter IH_E = new TamilCompoundCharacter(TamilSimpleCharacter.HA_.getValue(), E);
    public static final TamilCompoundCharacter ISH_E = new TamilCompoundCharacter(TamilSimpleCharacter.SHA_.getValue(), E);
    public static final TamilCompoundCharacter ISS_E = new TamilCompoundCharacter(TamilSimpleCharacter.SSA_.getValue(), E);
    public static final TamilCompoundCharacter ISSS_E = new TamilCompoundCharacter(TamilSimpleCharacter.SSSA_.getValue(), E);


    public static final TamilCompoundCharacter IK_EE = new TamilCompoundCharacter(TamilSimpleCharacter.KA.getValue(), EE);
    public static final TamilCompoundCharacter ING_EE = new TamilCompoundCharacter(TamilSimpleCharacter.NGA.getValue(), EE);
    public static final TamilCompoundCharacter ICH_EE = new TamilCompoundCharacter(TamilSimpleCharacter.SA.getValue(), EE);
    public static final TamilCompoundCharacter INJ_EE = new TamilCompoundCharacter(TamilSimpleCharacter.NYA.getValue(), EE);
    public static final TamilCompoundCharacter IDD_EE = new TamilCompoundCharacter(TamilSimpleCharacter.DA.getValue(), EE);
    public static final TamilCompoundCharacter INNN_EE = new TamilCompoundCharacter(TamilSimpleCharacter.NNNA.getValue(), EE);
    public static final TamilCompoundCharacter ITH_EE = new TamilCompoundCharacter(TamilSimpleCharacter.THA.getValue(), EE);
    public static final TamilCompoundCharacter INTH_EE = new TamilCompoundCharacter(TamilSimpleCharacter.NTHA.getValue(), EE);
    public static final TamilCompoundCharacter IP_EE = new TamilCompoundCharacter(TamilSimpleCharacter.PA.getValue(), EE);
    public static final TamilCompoundCharacter IM_EE = new TamilCompoundCharacter(TamilSimpleCharacter.MA.getValue(), EE);
    public static final TamilCompoundCharacter IY_EE = new TamilCompoundCharacter(TamilSimpleCharacter.YA.getValue(), EE);
    public static final TamilCompoundCharacter IR_EE = new TamilCompoundCharacter(TamilSimpleCharacter.RA.getValue(), EE);
    public static final TamilCompoundCharacter IL_EE = new TamilCompoundCharacter(TamilSimpleCharacter.LA.getValue(), EE);
    public static final TamilCompoundCharacter IV_EE = new TamilCompoundCharacter(TamilSimpleCharacter.VA.getValue(), EE);
    public static final TamilCompoundCharacter ILLL_EE = new TamilCompoundCharacter(TamilSimpleCharacter.LLLA.getValue(), EE);
    public static final TamilCompoundCharacter ILL_EE = new TamilCompoundCharacter(TamilSimpleCharacter.LLA.getValue(), EE);
    public static final TamilCompoundCharacter IRR_EE = new TamilCompoundCharacter(TamilSimpleCharacter.RRA.getValue(), EE);
    public static final TamilCompoundCharacter IN_EE = new TamilCompoundCharacter(TamilSimpleCharacter.NA.getValue(), EE);

    public static final TamilCompoundCharacter IJ_EE = new TamilCompoundCharacter(TamilSimpleCharacter.JA_.getValue(), EE);
    public static final TamilCompoundCharacter IH_EE = new TamilCompoundCharacter(TamilSimpleCharacter.HA_.getValue(), EE);
    public static final TamilCompoundCharacter ISH_EE = new TamilCompoundCharacter(TamilSimpleCharacter.SHA_.getValue(), EE);
    public static final TamilCompoundCharacter ISS_EE = new TamilCompoundCharacter(TamilSimpleCharacter.SSA_.getValue(), EE);
    public static final TamilCompoundCharacter ISSS_EE = new TamilCompoundCharacter(TamilSimpleCharacter.SSSA_.getValue(), EE);


    public static final TamilCompoundCharacter IK_U = new TamilCompoundCharacter(TamilSimpleCharacter.KA.getValue(), U);
    public static final TamilCompoundCharacter ING_U = new TamilCompoundCharacter(TamilSimpleCharacter.NGA.getValue(), U);
    public static final TamilCompoundCharacter ICH_U = new TamilCompoundCharacter(TamilSimpleCharacter.SA.getValue(), U);
    public static final TamilCompoundCharacter INJ_U = new TamilCompoundCharacter(TamilSimpleCharacter.NYA.getValue(), U);
    public static final TamilCompoundCharacter IDD_U = new TamilCompoundCharacter(TamilSimpleCharacter.DA.getValue(), U);
    public static final TamilCompoundCharacter INNN_U = new TamilCompoundCharacter(TamilSimpleCharacter.NNNA.getValue(), U);
    public static final TamilCompoundCharacter ITH_U = new TamilCompoundCharacter(TamilSimpleCharacter.THA.getValue(), U);
    public static final TamilCompoundCharacter INTH_U = new TamilCompoundCharacter(TamilSimpleCharacter.NTHA.getValue(), U);
    public static final TamilCompoundCharacter IP_U = new TamilCompoundCharacter(TamilSimpleCharacter.PA.getValue(), U);
    public static final TamilCompoundCharacter IM_U = new TamilCompoundCharacter(TamilSimpleCharacter.MA.getValue(), U);
    public static final TamilCompoundCharacter IY_U = new TamilCompoundCharacter(TamilSimpleCharacter.YA.getValue(), U);
    public static final TamilCompoundCharacter IR_U = new TamilCompoundCharacter(TamilSimpleCharacter.RA.getValue(), U);
    public static final TamilCompoundCharacter IL_U = new TamilCompoundCharacter(TamilSimpleCharacter.LA.getValue(), U);
    public static final TamilCompoundCharacter IV_U = new TamilCompoundCharacter(TamilSimpleCharacter.VA.getValue(), U);
    public static final TamilCompoundCharacter ILLL_U = new TamilCompoundCharacter(TamilSimpleCharacter.LLLA.getValue(), U);
    public static final TamilCompoundCharacter ILL_U = new TamilCompoundCharacter(TamilSimpleCharacter.LLA.getValue(), U);
    public static final TamilCompoundCharacter IRR_U = new TamilCompoundCharacter(TamilSimpleCharacter.RRA.getValue(), U);
    public static final TamilCompoundCharacter IN_U = new TamilCompoundCharacter(TamilSimpleCharacter.NA.getValue(), U);

    public static final TamilCompoundCharacter IJ_U = new TamilCompoundCharacter(TamilSimpleCharacter.JA_.getValue(), U);
    public static final TamilCompoundCharacter IH_U = new TamilCompoundCharacter(TamilSimpleCharacter.HA_.getValue(), U);
    public static final TamilCompoundCharacter ISH_U = new TamilCompoundCharacter(TamilSimpleCharacter.SHA_.getValue(), U);
    public static final TamilCompoundCharacter ISS_U = new TamilCompoundCharacter(TamilSimpleCharacter.SSA_.getValue(), U);
    public static final TamilCompoundCharacter ISSS_U = new TamilCompoundCharacter(TamilSimpleCharacter.SSSA_.getValue(), U);


    public static final TamilCompoundCharacter IK_UU = new TamilCompoundCharacter(TamilSimpleCharacter.KA.getValue(), UU);
    public static final TamilCompoundCharacter ING_UU = new TamilCompoundCharacter(TamilSimpleCharacter.NGA.getValue(), UU);
    public static final TamilCompoundCharacter ICH_UU = new TamilCompoundCharacter(TamilSimpleCharacter.SA.getValue(), UU);
    public static final TamilCompoundCharacter INJ_UU = new TamilCompoundCharacter(TamilSimpleCharacter.NYA.getValue(), UU);
    public static final TamilCompoundCharacter IDD_UU = new TamilCompoundCharacter(TamilSimpleCharacter.DA.getValue(), UU);
    public static final TamilCompoundCharacter INNN_UU = new TamilCompoundCharacter(TamilSimpleCharacter.NNNA.getValue(), UU);
    public static final TamilCompoundCharacter ITH_UU = new TamilCompoundCharacter(TamilSimpleCharacter.THA.getValue(), UU);
    public static final TamilCompoundCharacter INTH_UU = new TamilCompoundCharacter(TamilSimpleCharacter.NTHA.getValue(), UU);
    public static final TamilCompoundCharacter IP_UU = new TamilCompoundCharacter(TamilSimpleCharacter.PA.getValue(), UU);
    public static final TamilCompoundCharacter IM_UU = new TamilCompoundCharacter(TamilSimpleCharacter.MA.getValue(), UU);
    public static final TamilCompoundCharacter IY_UU = new TamilCompoundCharacter(TamilSimpleCharacter.YA.getValue(), UU);
    public static final TamilCompoundCharacter IR_UU = new TamilCompoundCharacter(TamilSimpleCharacter.RA.getValue(), UU);
    public static final TamilCompoundCharacter IL_UU = new TamilCompoundCharacter(TamilSimpleCharacter.LA.getValue(), UU);
    public static final TamilCompoundCharacter IV_UU = new TamilCompoundCharacter(TamilSimpleCharacter.VA.getValue(), UU);
    public static final TamilCompoundCharacter ILLL_UU = new TamilCompoundCharacter(TamilSimpleCharacter.LLLA.getValue(), UU);
    public static final TamilCompoundCharacter ILL_UU = new TamilCompoundCharacter(TamilSimpleCharacter.LLA.getValue(), UU);
    public static final TamilCompoundCharacter IRR_UU = new TamilCompoundCharacter(TamilSimpleCharacter.RRA.getValue(), UU);
    public static final TamilCompoundCharacter IN_UU = new TamilCompoundCharacter(TamilSimpleCharacter.NA.getValue(), UU);

    public static final TamilCompoundCharacter IJ_UU = new TamilCompoundCharacter(TamilSimpleCharacter.JA_.getValue(), UU);
    public static final TamilCompoundCharacter IH_UU = new TamilCompoundCharacter(TamilSimpleCharacter.HA_.getValue(), UU);
    public static final TamilCompoundCharacter ISH_UU = new TamilCompoundCharacter(TamilSimpleCharacter.SHA_.getValue(), UU);
    public static final TamilCompoundCharacter ISS_UU = new TamilCompoundCharacter(TamilSimpleCharacter.SSA_.getValue(), UU);
    public static final TamilCompoundCharacter ISSS_UU = new TamilCompoundCharacter(TamilSimpleCharacter.SSSA_.getValue(), UU);


    public static final TamilCompoundCharacter IK_A = new TamilCompoundCharacter(TamilSimpleCharacter.KA.getValue(), A);
    public static final TamilCompoundCharacter ING_A = new TamilCompoundCharacter(TamilSimpleCharacter.NGA.getValue(), A);
    public static final TamilCompoundCharacter ICH_A = new TamilCompoundCharacter(TamilSimpleCharacter.SA.getValue(), A);
    public static final TamilCompoundCharacter INJ_A = new TamilCompoundCharacter(TamilSimpleCharacter.NYA.getValue(), A);
    public static final TamilCompoundCharacter IDD_A = new TamilCompoundCharacter(TamilSimpleCharacter.DA.getValue(), A);
    public static final TamilCompoundCharacter INNN_A = new TamilCompoundCharacter(TamilSimpleCharacter.NNNA.getValue(), A);
    public static final TamilCompoundCharacter ITH_A = new TamilCompoundCharacter(TamilSimpleCharacter.THA.getValue(), A);
    public static final TamilCompoundCharacter INTH_A = new TamilCompoundCharacter(TamilSimpleCharacter.NTHA.getValue(), A);
    public static final TamilCompoundCharacter IP_A = new TamilCompoundCharacter(TamilSimpleCharacter.PA.getValue(), A);
    public static final TamilCompoundCharacter IM_A = new TamilCompoundCharacter(TamilSimpleCharacter.MA.getValue(), A);
    public static final TamilCompoundCharacter IY_A = new TamilCompoundCharacter(TamilSimpleCharacter.YA.getValue(), A);
    public static final TamilCompoundCharacter IR_A = new TamilCompoundCharacter(TamilSimpleCharacter.RA.getValue(), A);
    public static final TamilCompoundCharacter IL_A = new TamilCompoundCharacter(TamilSimpleCharacter.LA.getValue(), A);
    public static final TamilCompoundCharacter IV_A = new TamilCompoundCharacter(TamilSimpleCharacter.VA.getValue(), A);
    public static final TamilCompoundCharacter ILLL_A = new TamilCompoundCharacter(TamilSimpleCharacter.LLLA.getValue(), A);
    public static final TamilCompoundCharacter ILL_A = new TamilCompoundCharacter(TamilSimpleCharacter.LLA.getValue(), A);
    public static final TamilCompoundCharacter IRR_A = new TamilCompoundCharacter(TamilSimpleCharacter.RRA.getValue(), A);
    public static final TamilCompoundCharacter IN_A = new TamilCompoundCharacter(TamilSimpleCharacter.NA.getValue(), A);

    public static final TamilCompoundCharacter IJ_A = new TamilCompoundCharacter(TamilSimpleCharacter.JA_.getValue(), A);
    public static final TamilCompoundCharacter IH_A = new TamilCompoundCharacter(TamilSimpleCharacter.HA_.getValue(), A);
    public static final TamilCompoundCharacter ISH_A = new TamilCompoundCharacter(TamilSimpleCharacter.SHA_.getValue(), A);
    public static final TamilCompoundCharacter ISS_A = new TamilCompoundCharacter(TamilSimpleCharacter.SSA_.getValue(), A);
    public static final TamilCompoundCharacter ISSS_A = new TamilCompoundCharacter(TamilSimpleCharacter.SSSA_.getValue(), A);


    public static final TamilCompoundCharacter IK_AA = new TamilCompoundCharacter(TamilSimpleCharacter.KA.getValue(), AA);
    public static final TamilCompoundCharacter ING_AA = new TamilCompoundCharacter(TamilSimpleCharacter.NGA.getValue(), AA);
    public static final TamilCompoundCharacter ICH_AA = new TamilCompoundCharacter(TamilSimpleCharacter.SA.getValue(), AA);
    public static final TamilCompoundCharacter INJ_AA = new TamilCompoundCharacter(TamilSimpleCharacter.NYA.getValue(), AA);
    public static final TamilCompoundCharacter IDD_AA = new TamilCompoundCharacter(TamilSimpleCharacter.DA.getValue(), AA);
    public static final TamilCompoundCharacter INNN_AA = new TamilCompoundCharacter(TamilSimpleCharacter.NNNA.getValue(), AA);
    public static final TamilCompoundCharacter ITH_AA = new TamilCompoundCharacter(TamilSimpleCharacter.THA.getValue(), AA);
    public static final TamilCompoundCharacter INTH_AA = new TamilCompoundCharacter(TamilSimpleCharacter.NTHA.getValue(), AA);
    public static final TamilCompoundCharacter IP_AA = new TamilCompoundCharacter(TamilSimpleCharacter.PA.getValue(), AA);
    public static final TamilCompoundCharacter IM_AA = new TamilCompoundCharacter(TamilSimpleCharacter.MA.getValue(), AA);
    public static final TamilCompoundCharacter IY_AA = new TamilCompoundCharacter(TamilSimpleCharacter.YA.getValue(), AA);
    public static final TamilCompoundCharacter IR_AA = new TamilCompoundCharacter(TamilSimpleCharacter.RA.getValue(), AA);
    public static final TamilCompoundCharacter IL_AA = new TamilCompoundCharacter(TamilSimpleCharacter.LA.getValue(), AA);
    public static final TamilCompoundCharacter IV_AA = new TamilCompoundCharacter(TamilSimpleCharacter.VA.getValue(), AA);
    public static final TamilCompoundCharacter ILLL_AA = new TamilCompoundCharacter(TamilSimpleCharacter.LLLA.getValue(), AA);
    public static final TamilCompoundCharacter ILL_AA = new TamilCompoundCharacter(TamilSimpleCharacter.LLA.getValue(), AA);
    public static final TamilCompoundCharacter IRR_AA = new TamilCompoundCharacter(TamilSimpleCharacter.RRA.getValue(), AA);
    public static final TamilCompoundCharacter IN_AA = new TamilCompoundCharacter(TamilSimpleCharacter.NA.getValue(), AA);

    public static final TamilCompoundCharacter IJ_AA = new TamilCompoundCharacter(TamilSimpleCharacter.JA_.getValue(), AA);
    public static final TamilCompoundCharacter IH_AA = new TamilCompoundCharacter(TamilSimpleCharacter.HA_.getValue(), AA);
    public static final TamilCompoundCharacter ISH_AA = new TamilCompoundCharacter(TamilSimpleCharacter.SHA_.getValue(), AA);
    public static final TamilCompoundCharacter ISS_AA = new TamilCompoundCharacter(TamilSimpleCharacter.SSA_.getValue(), AA);
    public static final TamilCompoundCharacter ISSS_AA = new TamilCompoundCharacter(TamilSimpleCharacter.SSSA_.getValue(), AA);


    public static final TamilCompoundCharacter IK_I = new TamilCompoundCharacter(TamilSimpleCharacter.KA.getValue(), I);
    public static final TamilCompoundCharacter ING_I = new TamilCompoundCharacter(TamilSimpleCharacter.NGA.getValue(), I);
    public static final TamilCompoundCharacter ICH_I = new TamilCompoundCharacter(TamilSimpleCharacter.SA.getValue(), I);
    public static final TamilCompoundCharacter INJ_I = new TamilCompoundCharacter(TamilSimpleCharacter.NYA.getValue(), I);
    public static final TamilCompoundCharacter IDD_I = new TamilCompoundCharacter(TamilSimpleCharacter.DA.getValue(), I);
    public static final TamilCompoundCharacter INNN_I = new TamilCompoundCharacter(TamilSimpleCharacter.NNNA.getValue(), I);
    public static final TamilCompoundCharacter ITH_I = new TamilCompoundCharacter(TamilSimpleCharacter.THA.getValue(), I);
    public static final TamilCompoundCharacter INTH_I = new TamilCompoundCharacter(TamilSimpleCharacter.NTHA.getValue(), I);
    public static final TamilCompoundCharacter IP_I = new TamilCompoundCharacter(TamilSimpleCharacter.PA.getValue(), I);
    public static final TamilCompoundCharacter IM_I = new TamilCompoundCharacter(TamilSimpleCharacter.MA.getValue(), I);
    public static final TamilCompoundCharacter IY_I = new TamilCompoundCharacter(TamilSimpleCharacter.YA.getValue(), I);
    public static final TamilCompoundCharacter IR_I = new TamilCompoundCharacter(TamilSimpleCharacter.RA.getValue(), I);
    public static final TamilCompoundCharacter IL_I = new TamilCompoundCharacter(TamilSimpleCharacter.LA.getValue(), I);
    public static final TamilCompoundCharacter IV_I = new TamilCompoundCharacter(TamilSimpleCharacter.VA.getValue(), I);
    public static final TamilCompoundCharacter ILLL_I = new TamilCompoundCharacter(TamilSimpleCharacter.LLLA.getValue(), I);
    public static final TamilCompoundCharacter ILL_I = new TamilCompoundCharacter(TamilSimpleCharacter.LLA.getValue(), I);
    public static final TamilCompoundCharacter IRR_I = new TamilCompoundCharacter(TamilSimpleCharacter.RRA.getValue(), I);
    public static final TamilCompoundCharacter IN_I = new TamilCompoundCharacter(TamilSimpleCharacter.NA.getValue(), I);

    public static final TamilCompoundCharacter IJ_I = new TamilCompoundCharacter(TamilSimpleCharacter.JA_.getValue(), I);
    public static final TamilCompoundCharacter IH_I = new TamilCompoundCharacter(TamilSimpleCharacter.HA_.getValue(), I);
    public static final TamilCompoundCharacter ISH_I = new TamilCompoundCharacter(TamilSimpleCharacter.SHA_.getValue(), I);
    public static final TamilCompoundCharacter ISS_I = new TamilCompoundCharacter(TamilSimpleCharacter.SSA_.getValue(), I);
    public static final TamilCompoundCharacter ISSS_I = new TamilCompoundCharacter(TamilSimpleCharacter.SSSA_.getValue(), I);



    public static final TamilCompoundCharacter IK_O = new TamilCompoundCharacter(TamilSimpleCharacter.KA.getValue(), O,O__);
    public static final TamilCompoundCharacter ING_O = new TamilCompoundCharacter(TamilSimpleCharacter.NGA.getValue(), O,O__);
    public static final TamilCompoundCharacter ICH_O = new TamilCompoundCharacter(TamilSimpleCharacter.SA.getValue(), O,O__);
    public static final TamilCompoundCharacter INJ_O = new TamilCompoundCharacter(TamilSimpleCharacter.NYA.getValue(), O,O__);
    public static final TamilCompoundCharacter IDD_O = new TamilCompoundCharacter(TamilSimpleCharacter.DA.getValue(), O,O__);
    public static final TamilCompoundCharacter INNN_O = new TamilCompoundCharacter(TamilSimpleCharacter.NNNA.getValue(), O,O__);
    public static final TamilCompoundCharacter ITH_O = new TamilCompoundCharacter(TamilSimpleCharacter.THA.getValue(), O,O__);
    public static final TamilCompoundCharacter INTH_O = new TamilCompoundCharacter(TamilSimpleCharacter.NTHA.getValue(), O,O__);
    public static final TamilCompoundCharacter IP_O = new TamilCompoundCharacter(TamilSimpleCharacter.PA.getValue(), O,O__);
    public static final TamilCompoundCharacter IM_O = new TamilCompoundCharacter(TamilSimpleCharacter.MA.getValue(), O,O__);
    public static final TamilCompoundCharacter IY_O = new TamilCompoundCharacter(TamilSimpleCharacter.YA.getValue(), O,O__);
    public static final TamilCompoundCharacter IR_O = new TamilCompoundCharacter(TamilSimpleCharacter.RA.getValue(), O,O__);
    public static final TamilCompoundCharacter IL_O = new TamilCompoundCharacter(TamilSimpleCharacter.LA.getValue(), O,O__);
    public static final TamilCompoundCharacter IV_O = new TamilCompoundCharacter(TamilSimpleCharacter.VA.getValue(), O,O__);
    public static final TamilCompoundCharacter ILLL_O = new TamilCompoundCharacter(TamilSimpleCharacter.LLLA.getValue(), O,O__);
    public static final TamilCompoundCharacter ILL_O = new TamilCompoundCharacter(TamilSimpleCharacter.LLA.getValue(), O,O__);
    public static final TamilCompoundCharacter IRR_O = new TamilCompoundCharacter(TamilSimpleCharacter.RRA.getValue(), O,O__);
    public static final TamilCompoundCharacter IN_O = new TamilCompoundCharacter(TamilSimpleCharacter.NA.getValue(), O,O__);

    public static final TamilCompoundCharacter IJ_O = new TamilCompoundCharacter(TamilSimpleCharacter.JA_.getValue(), O,O__);
    public static final TamilCompoundCharacter IH_O = new TamilCompoundCharacter(TamilSimpleCharacter.HA_.getValue(), O,O__);
    public static final TamilCompoundCharacter ISH_O = new TamilCompoundCharacter(TamilSimpleCharacter.SHA_.getValue(), O,O__);
    public static final TamilCompoundCharacter ISS_O = new TamilCompoundCharacter(TamilSimpleCharacter.SSA_.getValue(), O,O__);
    public static final TamilCompoundCharacter ISSS_O = new TamilCompoundCharacter(TamilSimpleCharacter.SSSA_.getValue(), O,O__);


    public static final TamilCompoundCharacter IK_OO = new TamilCompoundCharacter(TamilSimpleCharacter.KA.getValue(), OO,OO__);
    public static final TamilCompoundCharacter ING_OO = new TamilCompoundCharacter(TamilSimpleCharacter.NGA.getValue(), OO,OO__);
    public static final TamilCompoundCharacter ICH_OO = new TamilCompoundCharacter(TamilSimpleCharacter.SA.getValue(), OO,OO__);
    public static final TamilCompoundCharacter INJ_OO = new TamilCompoundCharacter(TamilSimpleCharacter.NYA.getValue(), OO,OO__);
    public static final TamilCompoundCharacter IDD_OO = new TamilCompoundCharacter(TamilSimpleCharacter.DA.getValue(), OO,OO__);
    public static final TamilCompoundCharacter INNN_OO = new TamilCompoundCharacter(TamilSimpleCharacter.NNNA.getValue(), OO,OO__);
    public static final TamilCompoundCharacter ITH_OO = new TamilCompoundCharacter(TamilSimpleCharacter.THA.getValue(), OO,OO__);
    public static final TamilCompoundCharacter INTH_OO = new TamilCompoundCharacter(TamilSimpleCharacter.NTHA.getValue(), OO,OO__);
    public static final TamilCompoundCharacter IP_OO = new TamilCompoundCharacter(TamilSimpleCharacter.PA.getValue(), OO,OO__);
    public static final TamilCompoundCharacter IM_OO = new TamilCompoundCharacter(TamilSimpleCharacter.MA.getValue(), OO,OO__);
    public static final TamilCompoundCharacter IY_OO = new TamilCompoundCharacter(TamilSimpleCharacter.YA.getValue(), OO,OO__);
    public static final TamilCompoundCharacter IR_OO = new TamilCompoundCharacter(TamilSimpleCharacter.RA.getValue(), OO,OO__);
    public static final TamilCompoundCharacter IL_OO = new TamilCompoundCharacter(TamilSimpleCharacter.LA.getValue(), OO,OO__);
    public static final TamilCompoundCharacter IV_OO = new TamilCompoundCharacter(TamilSimpleCharacter.VA.getValue(), OO,OO__);
    public static final TamilCompoundCharacter ILLL_OO = new TamilCompoundCharacter(TamilSimpleCharacter.LLLA.getValue(), OO,OO__);
    public static final TamilCompoundCharacter ILL_OO = new TamilCompoundCharacter(TamilSimpleCharacter.LLA.getValue(), OO,OO__);
    public static final TamilCompoundCharacter IRR_OO = new TamilCompoundCharacter(TamilSimpleCharacter.RRA.getValue(), OO,OO__);
    public static final TamilCompoundCharacter IN_OO = new TamilCompoundCharacter(TamilSimpleCharacter.NA.getValue(), OO,OO__);

    public static final TamilCompoundCharacter IJ_OO = new TamilCompoundCharacter(TamilSimpleCharacter.JA_.getValue(), OO,OO__);
    public static final TamilCompoundCharacter IH_OO = new TamilCompoundCharacter(TamilSimpleCharacter.HA_.getValue(), OO,OO__);
    public static final TamilCompoundCharacter ISH_OO = new TamilCompoundCharacter(TamilSimpleCharacter.SHA_.getValue(), OO,OO__);
    public static final TamilCompoundCharacter ISS_OO = new TamilCompoundCharacter(TamilSimpleCharacter.SSA_.getValue(), OO,OO__);
    public static final TamilCompoundCharacter ISSS_OO = new TamilCompoundCharacter(TamilSimpleCharacter.SSSA_.getValue(), OO,OO__);


    public static final TamilCompoundCharacter IK_OU = new TamilCompoundCharacter(TamilSimpleCharacter.KA.getValue(), OU,OU__);
    public static final TamilCompoundCharacter ING_OU = new TamilCompoundCharacter(TamilSimpleCharacter.NGA.getValue(), OU,OU__);
    public static final TamilCompoundCharacter ICH_OU = new TamilCompoundCharacter(TamilSimpleCharacter.SA.getValue(), OU,OU__);
    public static final TamilCompoundCharacter INJ_OU = new TamilCompoundCharacter(TamilSimpleCharacter.NYA.getValue(), OU,OU__);
    public static final TamilCompoundCharacter IDD_OU = new TamilCompoundCharacter(TamilSimpleCharacter.DA.getValue(), OU,OU__);
    public static final TamilCompoundCharacter INNN_OU = new TamilCompoundCharacter(TamilSimpleCharacter.NNNA.getValue(), OU,OU__);
    public static final TamilCompoundCharacter ITH_OU = new TamilCompoundCharacter(TamilSimpleCharacter.THA.getValue(), OU,OU__);
    public static final TamilCompoundCharacter INTH_OU = new TamilCompoundCharacter(TamilSimpleCharacter.NTHA.getValue(), OU,OU__);
    public static final TamilCompoundCharacter IP_OU = new TamilCompoundCharacter(TamilSimpleCharacter.PA.getValue(), OU,OU__);
    public static final TamilCompoundCharacter IM_OU = new TamilCompoundCharacter(TamilSimpleCharacter.MA.getValue(), OU,OU__);
    public static final TamilCompoundCharacter IY_OU = new TamilCompoundCharacter(TamilSimpleCharacter.YA.getValue(), OU,OU__);
    public static final TamilCompoundCharacter IR_OU = new TamilCompoundCharacter(TamilSimpleCharacter.RA.getValue(), OU,OU__);
    public static final TamilCompoundCharacter IL_OU = new TamilCompoundCharacter(TamilSimpleCharacter.LA.getValue(), OU,OU__);
    public static final TamilCompoundCharacter IV_OU = new TamilCompoundCharacter(TamilSimpleCharacter.VA.getValue(), OU,OU__);
    public static final TamilCompoundCharacter ILLL_OU = new TamilCompoundCharacter(TamilSimpleCharacter.LLLA.getValue(), OU,OU__);
    public static final TamilCompoundCharacter ILL_OU = new TamilCompoundCharacter(TamilSimpleCharacter.LLA.getValue(), OU,OU__);
    public static final TamilCompoundCharacter IRR_OU = new TamilCompoundCharacter(TamilSimpleCharacter.RRA.getValue(), OU,OU__);
    public static final TamilCompoundCharacter IN_OU = new TamilCompoundCharacter(TamilSimpleCharacter.NA.getValue(), OU,OU__);

    public static final TamilCompoundCharacter IJ_OU = new TamilCompoundCharacter(TamilSimpleCharacter.JA_.getValue(), OU,OU__);
    public static final TamilCompoundCharacter IH_OU = new TamilCompoundCharacter(TamilSimpleCharacter.HA_.getValue(), OU,OU__);
    public static final TamilCompoundCharacter ISH_OU = new TamilCompoundCharacter(TamilSimpleCharacter.SHA_.getValue(), OU,OU__);
    public static final TamilCompoundCharacter ISS_OU = new TamilCompoundCharacter(TamilSimpleCharacter.SSA_.getValue(), OU,OU__);
    public static final TamilCompoundCharacter ISSS_OU = new TamilCompoundCharacter(TamilSimpleCharacter.SSSA_.getValue(), OU,OU__);


    /**
     * Translates the continuation vowel into real Vowel.
     *
     * @return the actual vowel
     */
    private int getRealVowel() {
        int val = getVowel() + TamilSimpleCharacter.aa.getValue() - TamilCompoundCharacter.aa;
        if (val >= TamilSimpleCharacter.aa.getValue() && val <= TamilSimpleCharacter.OU.getValue()) {
            return val;
        } else {
            throw new RuntimeException(getVowel() + ":" + Integer.toHexString(getVowel()) + " is not  valid continuation vowel. char:" + toString());
        }
    }

    public TamilSimpleCharacter asSimpleTamilCharacter() {
        return (TamilSimpleCharacter) TamilCharacterLookUpContext.lookup(consonants[0]).currentChar;
    }

    private boolean _isVallinam() {
        return asSimpleTamilCharacter().isVallinam();
    }

    private boolean _isMellinam() {
        return asSimpleTamilCharacter().isMellinam();
    }

    private boolean _isIdaiyinam() {
        return asSimpleTamilCharacter().isIdaiyinam();
    }


//    @Override
//    public TamilCompoundCharacter duplicate() {
//        return new TamilCompoundCharacter(consonants.clone(), vowel);
//    }

    @Override
    public int getNumericStrength() {
        int total = 0;
        for (int c : consonants) {
            total += c - TamilSimpleCharacter.AKTHU.getValue();
        }

        return (total * 100) + (getVowel() == PULLI ? 0 : (getVowel() - aa) + 2);
    }

    @Override
    public String getConsonantDigest() {
        return asSimpleTamilCharacter().getConsonantDigest();
    }

    @Override
    public String getSoundStrengthDigest() {
        return asSimpleTamilCharacter().getSoundStrengthDigest();
    }

    @Override
    public int[] getCodePoints() {
        int[] ret = Arrays.copyOf(this.consonants, this.consonants.length + 1);
        ret[this.consonants.length] = getVowel();
        return ret;
    }

    @Override
    public int getCodePointsCount() {
        return consonants.length + 1;
    }

    @Override
    public String getVowelDigest() {
        if (isMeyyezhuththu()) {
            return super.getVowelDigest();
        }
        return TamilCharacterLookUpContext.lookup(getRealVowel()).currentChar.getVowelDigest();
    }

    @Override
    public TamilCompoundCharacter getMeiPart() {
        if (isMeyyezhuththu()) {
            return this;
        }
        return (TamilCompoundCharacter) TamilCharacterLookUpContext.lookup(consonants[0]).next(PULLI).currentChar;// new TamilCompoundCharacter(consonants[0], PULLI);
    }

    @Override
    public TamilSimpleCharacter getUyirPart() {
        if (isMeyyezhuththu()) {
            throw new NoUyirPartException("Invalid call. There is no Uyir part in this letter (mei):" + this.toString());
        } else {
            return (TamilSimpleCharacter) TamilCharacterLookUpContext.lookup(this.getRealVowel()).currentChar; // new TamilSimpleCharacter(this.getRealVowel());
        }

    }

    @Override
    public TamilCharacter addUyir(TamilSimpleCharacter s) {
        if (!s.isUyirezhuththu()) {
            throw new RuntimeException(s + " is not Uyir");
        }
        if (!isMeyyezhuththu()) {
            throw new RuntimeException(this + " is not Mei");
        }
        if (s.isa()) {
            return TamilCharacterLookUpContext.lookup(this.getConsonant()).currentChar;// new TamilSimpleCharacter(this.getConsonant());
        } else {
            return TamilCharacterLookUpContext.lookup(this.getConsonant()).next(s.getContinuationVowel()).currentChar;//  new TamilCompoundCharacter(this.getConsonant(), s.getContinuationVowel());
        }
    }


}
