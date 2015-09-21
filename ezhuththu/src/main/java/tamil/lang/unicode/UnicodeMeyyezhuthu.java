package tamil.lang.unicode;

import tamil.lang.*;

import static tamil.lang.Inam.*;

public class UnicodeMeyyezhuthu extends AbstractTamilCharacter implements Meyyezhuthu {
    public static final UnicodeMeyyezhuthu IK = new UnicodeMeyyezhuthu('\u0B95', VALLINAM, TamilCharacter.DIGEST_CONSONANT_TYPE._KA_);
    public static final UnicodeMeyyezhuthu ING = new UnicodeMeyyezhuthu('\u0B99', MELLINAM, TamilCharacter.DIGEST_CONSONANT_TYPE._NGA_);
    public static final UnicodeMeyyezhuthu ICH = new UnicodeMeyyezhuthu('\u0B9A', VALLINAM, TamilCharacter.DIGEST_CONSONANT_TYPE._SA_);
    public static final UnicodeMeyyezhuthu INJ = new UnicodeMeyyezhuthu('\u0B9E', MELLINAM, TamilCharacter.DIGEST_CONSONANT_TYPE._NYA_);
    public static final UnicodeMeyyezhuthu IDD = new UnicodeMeyyezhuthu('\u0B9F', VALLINAM, TamilCharacter.DIGEST_CONSONANT_TYPE._DA_);
    public static final UnicodeMeyyezhuthu INNN = new UnicodeMeyyezhuthu('\u0BA3', MELLINAM, TamilCharacter.DIGEST_CONSONANT_TYPE._NNNA_);
    public static final UnicodeMeyyezhuthu ITH = new UnicodeMeyyezhuthu('\u0BA4', VALLINAM, TamilCharacter.DIGEST_CONSONANT_TYPE._THA_);
    public static final UnicodeMeyyezhuthu INTH = new UnicodeMeyyezhuthu('\u0BA8', MELLINAM, TamilCharacter.DIGEST_CONSONANT_TYPE._NTHA_);
    public static final UnicodeMeyyezhuthu IP = new UnicodeMeyyezhuthu('\u0BAA', VALLINAM, TamilCharacter.DIGEST_CONSONANT_TYPE._PA_);
    public static final UnicodeMeyyezhuthu IM = new UnicodeMeyyezhuthu('\u0BAE', MELLINAM, TamilCharacter.DIGEST_CONSONANT_TYPE._MA_);
    public static final UnicodeMeyyezhuthu IY = new UnicodeMeyyezhuthu('\u0BAF', IDAIYINAM, TamilCharacter.DIGEST_CONSONANT_TYPE._YA_);
    public static final UnicodeMeyyezhuthu IR = new UnicodeMeyyezhuthu('\u0BB0', IDAIYINAM, TamilCharacter.DIGEST_CONSONANT_TYPE._RA_);
    public static final UnicodeMeyyezhuthu IL = new UnicodeMeyyezhuthu('\u0BB2', IDAIYINAM, TamilCharacter.DIGEST_CONSONANT_TYPE._LA_);
    public static final UnicodeMeyyezhuthu IV = new UnicodeMeyyezhuthu('\u0BB5', IDAIYINAM, TamilCharacter.DIGEST_CONSONANT_TYPE._VA_);
    public static final UnicodeMeyyezhuthu ILLL = new UnicodeMeyyezhuthu('\u0BB4', IDAIYINAM, TamilCharacter.DIGEST_CONSONANT_TYPE._LLLA_);
    public static final UnicodeMeyyezhuthu ILL = new UnicodeMeyyezhuthu('\u0BB3', IDAIYINAM, TamilCharacter.DIGEST_CONSONANT_TYPE._LLA_);
    public static final UnicodeMeyyezhuthu IRR = new UnicodeMeyyezhuthu('\u0BB1', VALLINAM, TamilCharacter.DIGEST_CONSONANT_TYPE._RRA_);
    public static final UnicodeMeyyezhuthu IN = new UnicodeMeyyezhuthu('\u0BA9', MELLINAM, TamilCharacter.DIGEST_CONSONANT_TYPE._NA_);
    private int consonantValue;
    private Inam inam;
    private TamilCharacter.DIGEST_CONSONANT_TYPE consonantDigest;

    private UnicodeMeyyezhuthu(int consonantValue, Inam inam, TamilCharacter.DIGEST_CONSONANT_TYPE consonantDigest) {
        this.consonantValue = consonantValue;
        this.inam = inam;
        this.consonantDigest = consonantDigest;
    }

    @Override
    public String getSoundStrengthDigest() {
        return inam.toString();
    }

    @Override
    public String getConsonantDigest() {
        return consonantDigest.toString();
    }

    @Override
    public String getCharacterTypeDigest() {
        return TamilCharacter.DIGEST_CHAR_TYPE._M_.toString();
    }

    @Override
    public int getCodePointsCount() {
        return 2;
    }

    @Override
    public int getNumericStrength() {
        return (consonantValue - UnicodeAayuthavezhuthu.AKTHU.getValue()) * 100;
    }

    public boolean isVallinam() {
        return VALLINAM.equals(this.inam);
    }

    public boolean isMellinam() {
        return MELLINAM.equals(this.inam);
    }

    public boolean isIdaiyinam() {
        return IDAIYINAM.equals(this.inam);
    }
}
