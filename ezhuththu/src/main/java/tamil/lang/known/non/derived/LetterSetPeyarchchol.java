package tamil.lang.known.non.derived;

import my.interest.lang.tamil.punar.handler.verrrrumai.VAllHandler;
import tamil.lang.TamilWord;

/**
 * Created by velsubra on 6/13/16.
 */
public class LetterSetPeyarchchol extends Peyarchchol {
    public LetterSetPeyarchchol(TamilWord word) {
        super(word, 0);
        VAllHandler.HANDLER.generateAndAdd(this);
    }

    final TamilWord type = TamilWord.from("எழுத்துக்கணம்");

    @Override
    public TamilWord getType() {
        return type;

    }
}
