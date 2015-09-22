package tamil.lang.api.persist.manager;

import my.interest.lang.tamil.generated.types.GlobalTypes;
import my.interest.lang.tamil.generated.types.PeyarchcholDescription;
import my.interest.lang.tamil.punar.PropertyDescriptionContainer;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface NounManager {
    public PeyarchcholDescription findNounDescription(String root);
    public PropertyDescriptionContainer getConsolidatedPropertyContainerFor(PeyarchcholDescription root);
    public GlobalTypes getNounGlobalTypes();

}
