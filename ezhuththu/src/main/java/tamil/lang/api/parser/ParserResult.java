package tamil.lang.api.parser;

import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Represents the results of parsing done by {@link tamil.lang.api.parser.CompoundWordParser}
 * </p>
 *
 * @author velsubra
 */
public class ParserResult {
    public ParserResult(IKnownWord direct) {
        this.compoundTamilWord = direct.getWord();
        splitWords.add(direct);
    }
    public ParserResult(TamilWord compound, List<IKnownWord> splits) {
        this.compoundTamilWord = compound;
        this.splitWords.addAll(splits);
    }

    private TamilWord compoundTamilWord;

    private final List<IKnownWord> splitWords = new ArrayList<IKnownWord>();

    public TamilWord getCompoundTamilWord() {
        return compoundTamilWord;
    }

    public List<IKnownWord> getSplitWords() {
        return splitWords;
    }
}
