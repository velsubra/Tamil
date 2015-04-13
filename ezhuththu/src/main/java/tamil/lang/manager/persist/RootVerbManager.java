package tamil.lang.manager.persist;

import my.interest.lang.tamil.generated.types.RootVerbDescription;
import my.interest.lang.tamil.punar.PropertyDescriptionContainer;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface RootVerbManager {

    public RootVerbDescription findRootVerbDescription(String root);
    public PropertyDescriptionContainer getConsolidatedPropertyContainerFor(RootVerbDescription root);
}
