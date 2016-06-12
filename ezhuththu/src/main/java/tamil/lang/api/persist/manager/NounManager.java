package tamil.lang.api.persist.manager;

import my.interest.lang.tamil.generated.types.GlobalTypes;
import my.interest.lang.tamil.generated.types.PeyarchcholDescription;
import my.interest.lang.tamil.generated.types.Properties;
import my.interest.lang.tamil.punar.PropertyDescriptionContainer;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface NounManager {
    @Deprecated
    public PeyarchcholDescription findNounDescription(String root);
   // public List<PeyarchcholDescription> findNounDescriptions(String root);
    public PropertyDescriptionContainer getConsolidatedPropertyContainerFor(PeyarchcholDescription root);
    public GlobalTypes getNounGlobalTypes();
  //  public Properties getNounGlobalProperties();

}
