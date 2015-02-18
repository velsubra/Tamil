package tamil.lang.known.non.derived;

import tamil.lang.TamilWord;

/**
 * <p>
 *     தெரியாச்சொல்
 *     An unknown word, This is mainly to represent a candidate word. For e.g) when a தொடர்மொழி  is parsed and the parsing could not complete
 *     as there is one part unrecognized. That part is temporarily represented by this class.
 * </p>
 *
 * @author velsubra
 */
public final class Theriyaachchol  extends AbstractKnownWord {

    public Theriyaachchol(TamilWord word) {
        super(word);
    }
}
