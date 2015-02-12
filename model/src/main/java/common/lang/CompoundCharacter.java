package common.lang;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface CompoundCharacter extends common.lang.Character {

    /**
     * Returns the code point for all the consonants
     * @return  the array of consonants
     */
    public int[] getConsonants();

    /**
     * Returns the code point for the vowel.
     * @return the code point
     */
    public int getVowel();
}
