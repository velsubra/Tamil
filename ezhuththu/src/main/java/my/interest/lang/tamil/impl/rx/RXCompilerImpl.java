package my.interest.lang.tamil.impl.rx;

import my.interest.lang.tamil.impl.FeatureSet;
import tamil.lang.api.regex.RXFeature;
import tamil.util.IPropertyFinder;
import tamil.lang.api.regex.RxDescription;
import tamil.lang.api.regex.TamilRXCompiler;
import tamil.lang.exception.service.ServiceException;
import tamil.util.regex.TamilPattern;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class RXCompilerImpl implements TamilRXCompiler {
    public static final TamilRXCompiler DEFAULT = new RXCompilerImpl();

    public TamilPattern compile(String rx) throws ServiceException {
        return compile(rx, null);
    }


    public TamilPattern compile(String rx, IPropertyFinder aliases, RXFeature ... features) throws ServiceException {
        return TamilPattern.compile(rx, aliases, features);
    }


    public Set<? extends RxDescription> getRegXDescriptions() throws ServiceException {
        return Collections.unmodifiableSet(new LinkedHashSet<RxDescription>(RxRegistry.map.values()));
    }
}