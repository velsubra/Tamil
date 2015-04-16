package tamil.lang.spi;

import tamil.lang.api.parser.CompoundWordParser;

/**
 * <p>
 *     The parser provider interface.
 * </p>
 *
 * @author velsubra
 */
public interface CompoundWordParserProvider {

    /**
     * Creates a new parser.
     * @return the compound parser.
     */
    public CompoundWordParser crate();
}
