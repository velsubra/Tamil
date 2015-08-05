package tamil.lang;

public abstract class AbstractTamilCharacter {
    public abstract String getSoundSizeDigest();

    public abstract String getSoundStrengthDigest();

    public abstract String getConsonantDigest();

    public abstract String getVowelDigest();

    public abstract String getCharacterTypeDigest();

    public abstract int getCodePointsCount();

    public abstract int getNumericStrength();
}
