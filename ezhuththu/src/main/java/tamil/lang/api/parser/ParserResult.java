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

    private TamilWord compoundTamilWord;
    private boolean parsed;

    public void setParseHint(PARSE_HINT parseHint) {
        this.parseHint = parseHint;
    }

    public void setParsed(boolean parsed) {
        this.parsed = parsed;
    }

    private   PARSE_HINT parseHint;



    public ParserResult(IKnownWord direct, boolean parsed, PARSE_HINT hint) {
        this.parsed = parsed;
        this.parseHint = hint;
        this.compoundTamilWord = direct.getWord();
        splitWords.add(direct);

    }
    public ParserResult(TamilWord compound, List<IKnownWord> splits,  PARSE_HINT hint) {
        this.parsed = splits!= null && splits.size() > 0;
        this.parseHint = hint;
        this.compoundTamilWord = compound;
        if (splits!= null) {
            this.splitWords.addAll(splits);
        }
    }



    public boolean isParsed() {
        return parsed;
    }



    public PARSE_HINT getParseHint() {
        return parseHint;
    }



    private final List<IKnownWord> splitWords = new ArrayList<IKnownWord>();

    public TamilWord getCompoundTamilWord() {
        return compoundTamilWord;
    }

    public List<IKnownWord> getSplitWords() {
        return splitWords;
    }


    public static final  class  PARSE_HINT {
        private int tamilStartIndex;
        private int tamilEndIndex;
        private int unicodeStartIndex;
        private  int unicodeEndIndex;

        public PARSE_HINT(int tamilStart, int tamilEnd, int unistart, int uniend, String message) {
            this.tamilEndIndex = tamilEnd;
            this.tamilStartIndex =tamilStart;
            this.unicodeEndIndex = uniend;
            this.unicodeStartIndex = unistart;
            this.message = message;
        }

        private String message;

        public int getTamilStartIndex() {
            return tamilStartIndex;
        }

        public int getTamilEndIndex() {
            return tamilEndIndex;
        }

        public int getUnicodeStartIndex() {
            return unicodeStartIndex;
        }

        public int getUnicodeEndIndex() {
            return unicodeEndIndex;
        }

        public String getMessage() {
            return message;
        }
    }
}
