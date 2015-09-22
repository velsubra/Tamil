package tamil.lang.api.parser;

import tamil.lang.TamilWord;

import java.util.List;

/**
 * <p>
 * Interface to parse compound words (தொடர்மொழி ). If the input word is already known, it just returns that word.
 * </p>
 *
 * @author velsubra
 *
 * @see tamil.lang.TamilFactory#getCompoundWordParser()
 */
public interface CompoundWordParser {

    /**
     * Parses a given word and returns as soon as it finds a single valid parsing.
     *
     * @param singleWord the single word. There should not be any space or other characters.
     * @return the parsed result. Returns null if this cannot be parsed.
     */
    public ParserResult quickParse(TamilWord singleWord);

    /**
     * parses a given word for all of its possible splits.
     *
     * @param singleWord the word to be parsed.
     * @param maxReturn the number of maximum parse results. >= 1.
     * @param features the list of parsing features used to parse.
     * @return the returns set of results.
     */
    public ParserResultCollection parse(TamilWord singleWord, int maxReturn, ParseFeature ... features);



}
