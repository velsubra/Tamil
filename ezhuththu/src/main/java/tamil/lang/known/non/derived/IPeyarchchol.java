package tamil.lang.known.non.derived;

import tamil.lang.known.IKnownWord;

/**
 * <p>
 *     Represents a noun
 * </p>
 *
 * @author velsubra
 */
public interface IPeyarchchol extends IKnownWord {

    /**
     * Tells if the word represented is உயர்திணைப்பெயர்
     * @return   true for an உயர்திணைப்பெயர்  false otherwise.
     */
    public boolean isUyarThinhai();

    /**
     * Tells if the word is a pro noun    எ.கா) நான் , நாம் , அவன்
     * @return true if its is pro noun else other wise.
     */
    public boolean isProNoun();

}
