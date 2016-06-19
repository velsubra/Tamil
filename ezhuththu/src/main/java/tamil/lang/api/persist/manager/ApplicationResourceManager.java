package tamil.lang.api.persist.manager;

import my.interest.lang.tamil.generated.types.AppDescription;
import my.interest.lang.tamil.generated.types.AppResource;
import tamil.lang.exception.service.ServiceException;

/**
 * Created by velsubra on 6/17/16.
 */
public interface ApplicationResourceManager {

    public AppResource readResourceByName(String name);
    public void deleteResourceByName(String name);
    public void writeResource(AppResource app);

}
