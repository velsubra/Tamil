package tamil.lang.api.number;

import tamil.lang.TamilWord;

/**
 * <p>
 * Converts a number into Tamil text
 * <pre>
 *         12011 - பன்னிரண்டாயிரத்து பதினொன்று
 *     </pre>
 * </p>
 *
 * @author velsubra
 *
 * @see tamil.lang.TamilFactory#getNumberReader()
 */
public interface NumberReader {

    /**
     * Reads a number in string form.
     *
     * @param number number that can contain digits and a dot. All other characters will be ignored.
     * @return the Tamil text form of that number.
     */
    public TamilWord readNumber(String number);

    /**
     * Reads a number in a string form
     *
     * @param number the number to be read
     * @return the string form of the number
     */
    public TamilWord readNumber(long number);

    /**
     * Reads a number in a string form
     *
     * @param number the number to be read
     * @return the string form of the number
     */
    public TamilWord readNumber(double number);

}
