package tamil.lang.api.number;

import tamil.lang.TamilWord;

/**
 * <p>
 * Converts a decimal  number into Tamil text.
 *   <pre>
 *         12011 - பன்னிரண்டாயிரத்து பதினொன்று
 *     </pre>
 * </p>
 *
 * <p>
 * It can also convert text into the decimal number
 *   <pre>
 *        ஒரு கோடியே கோடி - 100000000000000
 *     </pre>
 * </p>
 *
 * @author velsubra
 * @see tamil.lang.TamilFactory#getNumberReader()
 */
public interface NumberReader {

    /**
     * Reads a number into text form with features.
     *
     * @param number   number that can contain digits and a dot. All other characters will be ignored.
     * @param features features that controls how the  text should be generated.
     * @return the Tamil text form of that number.
     * @throws tamil.lang.api.number.NotANumberException if the input is not a perfect number.
     */
    public TamilWord readNumber(String number, ReaderFeature... features) throws NotANumberException;


    /**
     * Reads a number into text
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



    /**
     * Reads number text into a numeric value.
     * @param numbertext   (E.g ஒரு கோடியே கோடி)
     * @param features
     * @return   number in decimal form .(Eg. 100000000000000)
     * @throws NotANumberException
     */
    public String readAsNumber(String numbertext, ReaderFeature... features) throws NotANumberException;

//
//    /**
//     * Reads number text into a numeric value.
//     * @param numbertext
//     * @param features
//     * @return
//     * @throws NotANumberException
//     */
//    public double readAsDouble(TamilWord numbertext, ReaderFeature... features) throws NotANumberException;
//
//
//
//    /**
//     * Reads number text into a numeric value.
//     * @param numbertext
//     * @param features
//     * @return
//     * @throws NotANumberException
//     */
//    public double readAsLong(TamilWord numbertext, ReaderFeature... features) throws NotANumberException;

}
