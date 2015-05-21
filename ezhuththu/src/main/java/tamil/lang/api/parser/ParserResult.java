package tamil.lang.api.parser;

import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.ITheriyaachchol;
import tamil.lang.known.non.derived.Theriyaachchol;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Represents the results of parsing done by {@link tamil.lang.api.parser.CompoundWordParser}
 * </p>
 *
 * @author velsubra
 */
public class ParserResult implements Comparable {

    private TamilWord compoundTamilWord;
    private boolean parsed;

    public void setParseHint(PARSE_HINT parseHint) {
        this.parseHint = parseHint;
    }

    public void setParsed(boolean parsed) {
        this.parsed = parsed;
    }

    private PARSE_HINT parseHint;


    public ParserResult(IKnownWord direct, boolean parsed, PARSE_HINT hint) {
        this.parsed = parsed;
        this.parseHint = hint;
        this.compoundTamilWord = direct.getWord();
        splitWords.add(direct);

    }

    public ParserResult(TamilWord compound, List<IKnownWord> splits, PARSE_HINT hint) {
        this.parsed = splits != null && splits.size() > 0;
        this.parseHint = hint;
        this.compoundTamilWord = compound;
        if (splits != null) {
            this.splitWords.addAll(splits);
        }
    }


    /**
     * Tells if the system could parse the word
     *
     * @return true, when successfully parsed based on the features passed, false otherwise.
     */
    public boolean isParsed() {
        return parsed;
    }


    /**
     * Returns parsing hints that have been encountered during parsing.
     *
     * @return the parse hint object, null when no parse hint was available
     * @see tamil.lang.api.feature.FeatureConstants#PARSE_FIND_FAILURE_INDEX_VAL_175
     */
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


    @Override
    public boolean equals(Object o) {
        return compareTo(o) == 0;
    }

    @Override
    public int compareTo(Object o) {
        ParserResult v = (ParserResult) o;
        int dif = splitWords.size() - v.splitWords.size();
        if (dif == 0) {
            for (int i = 0; i < splitWords.size(); i++) {
                dif = splitWords.get(i).compareTo(v.splitWords.get(i));
                if (dif == 0) continue;
                return dif;
            }
            return dif;
        } else {
            return dif;
        }
    }


    /**
     * The class containing details for parse hints.
     */
    public static final class PARSE_HINT {
        private int tamilStartIndex;
        private int tamilEndIndex;
        private int unicodeStartIndex;
        private int unicodeEndIndex;

        public PARSE_HINT(int tamilStart, int tamilEnd, int unistart, int uniend, String message) {
            this.tamilEndIndex = tamilEnd;
            this.tamilStartIndex = tamilStart;
            this.unicodeEndIndex = uniend;
            this.unicodeStartIndex = unistart;
            this.message = message;
        }


        private String message;

        /**
         * Gets the starting index of given Tamil word that has some hint
         *
         * @return index of the character from which the hint is applicable.
         */
        public int getTamilStartIndex() {
            return tamilStartIndex;
        }

        /**
         * Gets the end index    of given Tamil word that has some hint
         *
         * @return index of the character up to which the hint is applicable.
         */
        public int getTamilEndIndex() {
            return tamilEndIndex;
        }

        /**
         * Gets the starting index of given Tamil word that has some hint
         *
         * @return index of the unicode character from which the hint is applicable.
         */
        public int getUnicodeStartIndex() {
            return unicodeStartIndex;
        }

        /**
         * Gets the end index    of given Tamil word that has some hint
         *
         * @return index of the unicode character up to which the hint is applicable.
         */
        public int getUnicodeEndIndex() {
            return unicodeEndIndex;
        }


        /**
         * Gets the hint message
         *
         * @return message if there is some useful hint, null otherwise.
         */
        public String getMessage() {
            return message;
        }
    }

    public Theriyaachchol findUnknownPart() {
        for (IKnownWord know : splitWords) {
            if (ITheriyaachchol.class.isAssignableFrom(know.getClass()))  {
                return (Theriyaachchol)know;
            }
        }
        return null;
    }
}
