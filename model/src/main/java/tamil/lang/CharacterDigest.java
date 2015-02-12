package tamil.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Gets different character digest for a Word.  This provides a different view of a word through
 * <ol>
 * <li>Character type (ஆய்தம் = 1, உயிர் = 2, மெய் = 3, உயிர்மெய் = 4 )</li>
 * <li>Constants(மெய்யெழுத்துகள் க் = 1, ங் = 2  ,ச் = 3 ...  ன் = 18 )  </li>
 * <li>Sound size (மாத்திரை =>  மெய், ஆயுதம் = 0.5, குறில் = 1 , நெடில் = 2.0 )</li>
 * <li>Sound strength (வல்லினம் = 3, மெல்லினம் = 1 , இடையினம் = 2 )</li>
 * <li>Vowels(உயிரெழுத்துகள் அ = 1, ஆ = 2 .... ஔ = 12 ) </li>
 * </ol>
 * <p/>
 * for every character in the word.
 * <b> For a character that does not belong to the specific digest type, the digest value will be 0 . For eg) The vowel digest of க் = 0.</b>
 * </p>
 * <p>
 * Non tamil characters will have 0 as the number value and _0.0_ as the string representation.
 * </p>
 * <p/>
 * <p>
 * {@link TamilWord} w = TamilWord.from("செந்தமிழ்");
 *
 * </p>
 * <pre>
 * System.out.println("getCharacterTypeDigest\t: " + w.getCharacterTypeDigest().toString());
 * System.out.println("getConsonantDigest\t\t: " + w.getConsonantDigest().toString());
 * System.out.println("getSoundSizeDigest\t\t: " + w.getSoundSizeDigest().toString());
 * System.out.println("getSoundStrengthDigest\t: " + w.getSoundStrengthDigest().toString());
 * System.out.println("getVowelDigest\t\t\t: " + w.getVowelDigest().toString());
 *
 *     The above code prints
 *
 * getCharacterTypeDigest   : _4__3__4__4__3_
 * getConsonantDigest       : _03__08__07__10__15_
 * getSoundSizeDigest       : _1.0__0.5__1.0__1.0__0.5_
 * getSoundStrengthDigest   : _3__1__3__1__2_
 * getVowelDigest           : _07__0.0__01__03__0.0_
 * </pre>
 *
 * @author velsubra
 * @see TamilWord#getCharacterTypeDigest()
 * @see TamilWord#getConsonantDigest()
 * @see TamilWord#getSoundSizeDigest()
 * @see TamilWord#getSoundStrengthDigest()
 * @see TamilWord#getVowelDigest()
 */
public final class CharacterDigest extends ArrayList<String> implements Comparable {

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (String s : this) {
            buffer.append(s);
        }
        return buffer.toString();
    }

    /**
     * The digest as an array of numbers
     * @return  array of double
     */
    public List<Double> getNumberArray() {
        List<Double> list = new ArrayList<Double>(size());
        for (String s : this) {
            s = s.substring(1, s.length() - 1);
            list.add(Double.parseDouble(s));
        }
        return list;
    }


    @Override
    public int compareTo(Object o) {
        if (o == null) return 1;
        return this.toString().compareTo(o.toString());
    }
}
