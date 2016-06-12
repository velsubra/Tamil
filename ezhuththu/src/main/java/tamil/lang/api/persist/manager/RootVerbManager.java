package tamil.lang.api.persist.manager;

import my.interest.lang.tamil.generated.types.RootVerbDescription;
import my.interest.lang.tamil.punar.PropertyDescriptionContainer;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface RootVerbManager {

    @Deprecated
    public RootVerbDescription findRootVerbDescription(String root);
    //public List<RootVerbDescription> findRootVerbDescriptions(String root);
    public PropertyDescriptionContainer getConsolidatedPropertyContainerFor(RootVerbDescription root);
}
