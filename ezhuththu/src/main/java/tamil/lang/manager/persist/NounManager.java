package tamil.lang.manager.persist;

import my.interest.lang.tamil.generated.types.IdaichcholDescription;
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
}
