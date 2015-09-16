package tamil.lang.unicode;

import tamil.lang.AbstractTamilCharacter;
import tamil.lang.OliAlavu;
import tamil.lang.TamilCharacter;
import tamil.lang.UyirEzhuthu;

import static tamil.lang.OliAlavu.KURIL;
import static tamil.lang.OliAlavu.NEDIL;
import static tamil.lang.TamilCharacter.DIGEST_CHAR_TYPE._U_;

public class UnicodeUyirezhuthu extends AbstractTamilCharacter implements UyirEzhuthu {
    public static UnicodeUyirezhuthu a = new UnicodeUyirezhuthu('\u0B85', KURIL, TamilCharacter.DIGEST_VOWEL._a_);
    public static UnicodeUyirezhuthu aa = new UnicodeUyirezhuthu('\u0B86', NEDIL, TamilCharacter.DIGEST_VOWEL._aa_);
    public static UnicodeUyirezhuthu E = new UnicodeUyirezhuthu('\u0B87', KURIL, TamilCharacter.DIGEST_VOWEL._E_);
    public static UnicodeUyirezhuthu EE = new UnicodeUyirezhuthu('\u0B88', NEDIL, TamilCharacter.DIGEST_VOWEL._EE_);
    public static UnicodeUyirezhuthu U = new UnicodeUyirezhuthu('\u0B89', KURIL, TamilCharacter.DIGEST_VOWEL._U_);
    public static UnicodeUyirezhuthu UU = new UnicodeUyirezhuthu('\u0B8A', NEDIL, TamilCharacter.DIGEST_VOWEL._UU_);
    public static UnicodeUyirezhuthu A = new UnicodeUyirezhuthu('\u0B8E', KURIL, TamilCharacter.DIGEST_VOWEL._A_);
    public static UnicodeUyirezhuthu AA = new UnicodeUyirezhuthu('\u0B8F', NEDIL, TamilCharacter.DIGEST_VOWEL._AA_);
    public static UnicodeUyirezhuthu I = new UnicodeUyirezhuthu('\u0B90', NEDIL, TamilCharacter.DIGEST_VOWEL._I_);
    public static UnicodeUyirezhuthu O = new UnicodeUyirezhuthu('\u0B92', KURIL, TamilCharacter.DIGEST_VOWEL._O_);
    public static UnicodeUyirezhuthu OO = new UnicodeUyirezhuthu('\u0B93', NEDIL, TamilCharacter.DIGEST_VOWEL._OO_);
    public static UnicodeUyirezhuthu OU = new UnicodeUyirezhuthu('\u0B94', NEDIL, TamilCharacter.DIGEST_VOWEL._OU_);
    private int value;
    private OliAlavu oliAlavu;
    private TamilCharacter.DIGEST_VOWEL vowelDigest;

    public UnicodeUyirezhuthu(int value, OliAlavu oliAlavu, TamilCharacter.DIGEST_VOWEL vowelDigest) {
        this.value = value;
        this.oliAlavu = oliAlavu;
        this.vowelDigest = vowelDigest;
    }

    public boolean isKuril() {
        return KURIL.equals(oliAlavu);
    }

    public boolean isNedil() {
        return NEDIL.equals(oliAlavu);
    }

    @Override
    public String getSoundSizeDigest() {
        return oliAlavu.toString();
    }

    @Override
    public String getSoundStrengthDigest() {
        return "_00_";
    }

    @Override
    public String getConsonantDigest() {
        return "_00_";
    }

    @Override
    public String getVowelDigest() {
        return vowelDigest.toString();
    }

    @Override
    public String getCharacterTypeDigest() {
        return _U_.toString();
    }

    @Override
    public int getCodePointsCount() {
        return 1;
    }

    @Override
    public int getNumericStrength() {
        //TODO To replace with aayutha ezhuthu value once it is implemented
        return (value - '\u0B83') * 100 + 1;
    }
}
