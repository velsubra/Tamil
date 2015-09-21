package tamil.lang;

public abstract class AbstractTamilCharacter {
    public String getSoundSizeDigest(){
        return OliAlavu.NONE.toString();
    }

    public String getSoundStrengthDigest(){
        return Inam.NONE.toString();
    }

    public String getConsonantDigest(){
        return TamilCharacter.DIGEST_CONSONANT_TYPE._NONE_.toString();
    }

    public String getVowelDigest(){
        return TamilCharacter.DIGEST_VOWEL._NONE_.toString();
    }

    public abstract String getCharacterTypeDigest();

    public abstract int getCodePointsCount();

    public abstract int getNumericStrength();
}
