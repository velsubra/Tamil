package tamil.lang.api.join;

import tamil.lang.TamilWord;

/**
 * <p>
 *     The interface to appendNodesToAllPaths words based on some primitive புணர்ச்சி laws.
 *     Please use {@link tamil.lang.TamilFactory#createWordJoiner(tamil.lang.TamilWord)}   to create an instance of words joiner.
 * </p>
 *
 * @author velsubra
 */
public interface WordsJoiner {


    /**
     * adds word to the current sum of the joiner by means of doing புணர்ச்சி
     * @param word the word to be added
     */
    public void addVaruMozhi(TamilWord word);


    /**
     * adds the current sum of the joiner into the given word by doing புணர்ச்சி
     * @param word the word to be inserted
     */
    public void addNilaiMozhi(TamilWord word);



    /**
     * The effective word that is generated.
     * @return  the sum out of one or more additions using {@link #addVaruMozhi(tamil.lang.TamilWord)}
     */
    public TamilWord getSum();
}
