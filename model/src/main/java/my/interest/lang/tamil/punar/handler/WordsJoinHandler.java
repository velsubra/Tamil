package my.interest.lang.tamil.punar.handler;

import my.interest.lang.tamil.punar.TamilWordPartContainer;

import my.interest.lang.tamil.punar.handler.verrrrumai.VAllHandler;
import tamil.lang.TamilWord;
import tamil.lang.api.join.WordsJoiner;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class WordsJoinHandler extends VinaiMutruCreationHandler implements WordsJoiner {

    @Override
    public String getName() {
        return "Joiner Handler";
    }

    public WordsJoinHandler() {
        prependInstanceHandler(VAllHandler.HANDLER);
    }

    /**
     * adds word to the current joiner by means of doing புணர்ச்சி
     *
     * @param word the word to be added
     */
    @Override
    public void addVaruMozhi(TamilWord word) {
        add(new TamilWordPartContainer(word));
    }

    /**
     * The effective word that is returned.
     *
     * @return the sum out of one or more additions using {@link #add(tamil.lang.TamilWord)}
     */
    @Override
    public TamilWord getSum() {
        return vinaiMutru;
    }

}
