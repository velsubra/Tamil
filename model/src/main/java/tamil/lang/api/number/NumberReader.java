package tamil.lang.api.number;

import tamil.lang.TamilWord;

/**
 * <p>
 * Converts a absolute number into Tamil text.
 * <pre>
 *         12011 - பன்னிரண்டாயிரத்து பதினொன்று
 *     </pre>
 * </p>
 *
 * @author velsubra
 * @see tamil.lang.TamilFactory#getNumberReader()
 */
public interface NumberReader {

    /**
     * Reads a number in string form with features.
     *
     * @param number   number that can contain digits and a dot. All other characters will be ignored.
     * @param features features that controls how the  text should be generated.
     * @return the Tamil text form of that number.
     * @throws tamil.lang.api.number.NotANumberException if the input is not a perfect number.
     */
    public TamilWord readNumber(String number, ReaderFeature... features) throws NotANumberException;


    /**
     * Reads a number in string form.
     *
     * @param number number that can contain digits and a dot. All other characters will be ignored.
     * @return the Tamil text form of that number.
     * @throws tamil.lang.api.number.NotANumberException if the input is not a perfect number.
     */
    public TamilWord readNumber(String number) throws NotANumberException;


    /**
     * Reads a number in a long form .
     *
     * @param number the number to be read
     * @return the string form of the number
     */
    public TamilWord readNumber(long number);

    /**
     * Reads a number in a double form
     *
     * @param number the number to be read
     * @return the string form of the number
     */
    public TamilWord readNumber(double number);

}
