package tamil.lang.spi;

import tamil.lang.api.parser.CompoundWordParser;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.IBasePeyar;
import tamil.lang.known.non.derived.IBaseVinai;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     The parser provider interface.
 * </p>
 *
 * @author velsubra
 */
public interface CompoundWordParserProvider {

    public static final List<Class<? extends IKnownWord>> search = new ArrayList<Class<? extends IKnownWord>>() {
        {
            add(IBasePeyar.class);
            add(IBaseVinai.class);
        }

    };

    /**
     * Creates a new parser.
     * @return the compound parser.
     */
    public CompoundWordParser crate();
}
