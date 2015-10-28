package tamil.util.regex;

/**
 * <p>
 *     Simple matcher interface
 * </p>
 *
 * @author velsubra
 *
 * @see tamil.lang.api.regex.TamilRXCompiler#compileToPatternsList(String, tamil.util.IPropertyFinder, java.util.List, tamil.lang.api.regex.RXFeature...)
 * @see tamil.lang.TamilFactory#transpose(SimpleMatcher)
 */
public interface SimpleMatcher {

    /**
     * Finds the next pattern . If the matcher is transposed (see {@link #isTransposed()}), it will match what the underlying pattern could not match.
     * @return true if the pattern is found
     */
    public boolean find();

    /**
     * Gets the starting index of the pattern
     * @return the index if the matcher is looking at the match else it throws exception.
     */
    public int start();

    /**
     * Returns the offset after the last character matched.
     * @return
     */
    public int end();

    /**
     * The string representing the underlying pattern
     * @return  the pattern
     */
    public String getPattern();

    /**
     * Checks if the matcher is transposed such that it matches what it would skip if it were not transposed.
     * @return true if it is transposed, false otherwise
     */
    public boolean isTransposed();

    /**
     * Gets the underlying source string's length
     * @return the length of source string.
     */
    public int getSourceLength();



}
