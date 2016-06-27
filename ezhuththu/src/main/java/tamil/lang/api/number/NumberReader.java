package tamil.lang.api.number;

import tamil.lang.TamilWord;

/**
 * <p>
 * Converts a decimal  number into Tamil text.
 * <pre>
 *         12011 - பன்னிரண்டாயிரத்து பதினொன்று
 *     </pre>
 * </p>
 * <p/>
 * <p>
 * It can also convert text into the decimal number
 *
 * <pre>
 *     ஒரு கோடியே கோடி - 100000000000000
 *     பத்திலட்சங்கோடியே நாநூற்று இருபதுகோடியே எட்டுகோடியே பத்திலட்சத்து எண்பத்தெட்டு   -  <b>1000000000042000000081000088</b>  .ie)1,000,000,000,042,000,000,081,000,088
 *     </pre>
 *
 * When the text is read as a number, the following higher powers  of 10 can be used in the text.
 *
 *
 *  <hr/>
 * இச்சொற்களை உருவாக்கியவர் அறிவியலறிஞரான    <a href='https://www.facebook.com/ilango.pichandy'>இளங்கோ பிச்சையாண்டியவர்களாவார்.</a>
 * <pre>
 *       எண்ணம் = 10^6
 *       இரட்டம் = 10^9
 *       மூவகம் = 10^12
 *       நாவகம் = 10^15
 *       ஐவகம் = 10^18
 *       அறுவகம் = 10^21
 *       எழுவகம் = 10^24
 *       எண்மகம் = 10^27
 *       தொட்டகம் = 10^30
 *
 *      ஓர் எண்மகத்து நாற்பத்திரண்டு நாவகத்து எட்டு கோடியே எண்ணத்து எண்பத்தெட்டு - <b>1000000000042000000081000088</b>  .ie)1,000,000,000,042,000,000,081,000,088
 *     </pre>
 *     <hr/>
 * இச்சொற்களை உருவாக்கியவர்     <a href='https://www.facebook.com/kottalam'> செயபாண்டியன் கோட்டாளமவர்களாவார்.</a>
 *
 * <pre>
 *       இருமடியாயிரம் = 10^6
 *       மும்மடியாயிரம் = 10^9
 *       நான்மடியாயிரம் = 10^12
 *       ஐமடியாயிரம் = 10^15
 *       அறுமடியாயிரம் = 10^18
 *       எழுமடியாயிரம் = 10^21
 *       எண்மடியாயிரம் = 10^24
 *       ஒன்பதுமடியாயிரம் = 10^27
 *       பன்மடியாயிரம் = 10^30
 *
 *      <a href='https://drive.google.com/file/d/0BzwpbxABzaV5SzVpQ24tY0NGVXc/edit'>மேலுமறிக.</a>
 *
 *      <a href='https://ta.wikipedia.org/wiki/%E0%AE%9F%E0%AE%BF%E0%AE%B0%E0%AE%BF%E0%AE%B2%E0%AF%8D%E0%AE%B2%E0%AE%BF%E0%AE%AF%E0%AE%A9%E0%AF%8D'>மேலும், விக்கி</a>
 *
 *      ஓர் ஒன்பதுமடியாயிரத்து  நாற்பத்திரண்டு ஐமடியாயிரத்து எட்டு கோடியே இருமடியாயிரத்து எண்பத்தெட்டு  -  <b>1000000000042000000081000088</b>  .ie)1,000,000,000,042,000,000,081,000,088
 *     </pre>
 * <p/>
 * </pre>
 *  <hr/>
 * <a href='https://en.wikipedia.org/wiki/Tamil_numerals#Multiples_of_ten_.28.E0.AE.AA.E0.AE.A4.E0.AE.BF.E0.AE.A9.E0.AF.8D.E0.AE.AA.E0.AF.86.E0.AE.B0.E0.AF.81.E0.AE.95.E0.AF.8D.E0.AE.95.E0.AE.AE.E0.AF.8D.29'>WIKI</a>
 * <pre>
 *
 * ஒரு மெய்யிரத்தாம்பலே  நாற்பத்திரண்டு நெளையே எட்டு கோடியே மெய்யிரத்து எண்பத்தெட்டு  -  <b>1000000000042000000081000088</b>  .ie)1,000,000,000,042,000,000,081,000,088
 * </pre>
 * <p/>
 * <p/>
 *  <hr/>
 * For larger numbers one can use {@link #readAsNumber(String, ReaderFeature...)}
 * <p/>
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
     *
     * @param numbertext (E.g ஒரு கோடியே கோடி)
     * @param features
     * @return number in decimal form .(Eg. 100000000000000)
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
