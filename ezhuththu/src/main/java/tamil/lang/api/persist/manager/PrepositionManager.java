package tamil.lang.api.persist.manager;

import my.interest.lang.tamil.generated.types.IdaichcholDescription;
import my.interest.lang.tamil.punar.PropertyDescriptionContainer;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface PrepositionManager {
    public IdaichcholDescription findPrepositionDescription(String root);
    public PropertyDescriptionContainer getConsolidatedPropertyContainerFor(IdaichcholDescription root);
}
