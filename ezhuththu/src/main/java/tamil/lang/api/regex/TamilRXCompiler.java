package tamil.lang.api.regex;

import my.interest.lang.tamil.internal.api.IPropertyFinder;
import tamil.lang.exception.service.ServiceException;
import tamil.util.regx.TamilPattern;

import java.util.Properties;


/**
 * <p>
 *     The Tamil regular expression compiler. The expression can contain   It is capable of compiling an expression and return standard {@link java.util.regex.Matcher}
 * </p>
 *
 * @author velsubra
 */
public interface TamilRXCompiler {

    /**
     * Compiles a regular expression.
     * @param rx the Tamil regular expression
     * @return  the compiled pattern. One can get Java matcher by calling  {@link tamil.util.regx.TamilPattern#matcher(CharSequence)}
     * @throws ServiceException
     */
    public TamilPattern compile(String rx) throws ServiceException;

    /**
     * Compiles a regular expression that can contain aliases.
     * @param rx the Tamil regular expression
     * @param aliases the aliases definition. Defined aliases could be used in the regular expression.
     *                Example: குற்றியலுகரம் can be defined as  குற்றியலுகரம்=${வலியுகரவரிசை}  and ${குற்றியலுகரம்} can be used in the Tamil regular expression to mean any of the குற்றியலுகரம் character.
     *                <b><Note:/b>வலியுகரவரிசை can be calculated by
     *                {@link tamil.lang.api.ezhuththu.TamilCharacterSetCalculator#find(String)} method as வலி and  உகரவரிசை  are available as already known character sets returned by {@link tamil.lang.api.ezhuththu.TamilCharacterSetCalculator#getEzhuththuDescriptions()}  }
     *
     *                <p>
     *                   To define   குற்றியலுகரம்=${வலியுகரவரிசை},  IPropertyFinder.findProperty("குற்றியலுகரம்") should return the string literal  ${வலியுகரவரிசை}
     *                </p>
     * @return  the compiled pattern. One can get Java matcher by calling  {@link tamil.util.regx.TamilPattern#matcher(CharSequence)}
     * @throws ServiceException
     */
    public TamilPattern compile(String rx, IPropertyFinder aliases) throws ServiceException;
}
