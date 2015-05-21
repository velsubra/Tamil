package tamil.lang.api.join;

import tamil.lang.known.IKnownWord;

/**
 * <p>
 *     Joins known words and based on the type of புணர்ச்சி.
 *
 * </p>
 *
 * @author velsubra
 */
public interface KnownWordsJoiner {

    public static  enum TYPE {
        VEATTUMAI,
        ALVAZHI
    }
    /**
     * adds word to the current sum of the joiner by means of doing புணர்ச்சி
     * @param word the word to be added
     */
    public void addVaruMozhi(IKnownWord word, TYPE type);


    /**
     * adds the current sum of the joiner into the given word by doing புணர்ச்சி
     * @param word the word to be inserted
     */
    public void addNilaiMozhi(IKnownWord word, TYPE type);



    /**
     * The effective word that is generated.
     * @return  the sum out of one or more additions using {@link #addVaruMozhi(tamil.lang.known.IKnownWord, tamil.lang.api.join.KnownWordsJoiner.TYPE)}
     */
    public IKnownWord getSum();
}
