package tamil.lang.known.thodar;

import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.AbstractKnownWord;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public abstract  class AbstractThodarMozhi extends AbstractKnownWord implements IThodarMozhi {
    List<? extends IKnownWord> words = null;
    public AbstractThodarMozhi(TamilWord word, IKnownWord ... knowns) {
        this(word, Arrays.asList(knowns));
    }
    public AbstractThodarMozhi(TamilWord word, List<? extends IKnownWord> list) {
        super(word);
        this.words = list;
    }

    @Override
    public List<? extends IKnownWord> getWords() {
       return  words;
    }


}
