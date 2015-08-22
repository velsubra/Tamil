package my.interest.lang.tamil.impl.rx;

import my.interest.lang.tamil.internal.api.IPropertyFinder;
import tamil.lang.api.regex.TamilRXCompiler;
import tamil.lang.exception.service.ServiceException;
import tamil.util.regx.TamilPattern;



/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class RegXCompilerImpl implements TamilRXCompiler {
    public static final TamilRXCompiler DEFAULT= new RegXCompilerImpl();

    public TamilPattern compile(String rx) throws ServiceException {
        return compile(rx, null);
    }


    public TamilPattern compile(String rx, IPropertyFinder aliases) throws ServiceException {
        return TamilPattern.compile(rx, aliases);
    }
}
