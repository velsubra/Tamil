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
    public AppDescription findAppByName(String name);
    public AppDescription createAppByName(String name) throws ServiceException;
    public void deleteApplicationByName(String name);
    public void updateApplication(AppDescription app);

}
