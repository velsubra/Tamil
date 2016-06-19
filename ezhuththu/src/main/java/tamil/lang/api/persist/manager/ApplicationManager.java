package tamil.lang.api.persist.manager;

import my.interest.lang.tamil.generated.types.AppDescription;
import tamil.lang.exception.service.ServiceException;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface ApplicationManager {
    public AppDescription readApplicationByName(String name);
    public void deleteApplicationByName(String name);
    public void writeApplication(AppDescription app, boolean overWrite);
    public ApplicationResourceManager getResourceManager(String appName);

}
