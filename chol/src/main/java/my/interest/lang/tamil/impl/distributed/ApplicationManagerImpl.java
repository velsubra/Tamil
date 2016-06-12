package my.interest.lang.tamil.impl.distributed;

import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.generated.types.AppDescription;
import my.interest.lang.tamil.generated.types.AppsDescription;
import my.interest.lang.tamil.generated.types.TamilRootWords;
import my.interest.lang.tamil.impl.persist.FileBasedPersistenceImpl;
import my.interest.lang.tamil.internal.api.DefinitionFactory;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import tamil.lang.TamilFactory;
import tamil.lang.api.persist.manager.ApplicationManager;
import tamil.lang.api.persist.object.ObjectPersistenceInterface;
import tamil.lang.exception.service.ServiceException;

import javax.xml.bind.JAXBContext;
import java.io.File;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by velsubra on 3/28/16.
 */
public class ApplicationManagerImpl implements ApplicationManager {
    private String lastUpdatedKey = null;
    ObjectPersistenceInterface lastUpdatedKeyPersistence = null;
    AppsDescription appsDescription = null;
    static final String CATEGORY = "manager";
    String path = new File(PersistenceInterface.getWorkDir(), "applications.xml").getAbsolutePath();



    ApplicationManagerImpl() {
        lastUpdatedKeyPersistence =   new FileBasedPersistenceImpl("app");
    }

    public AppDescription findAppByName(String name) {
        return null;
    }

    public AppDescription createAppByName(String name) throws ServiceException {
        return null;
    }

    public void deleteApplicationByName(String name) {

    }

    public void updateApplication(AppDescription app) {

    }

    private void readInternal() throws Exception{
        boolean toRead = !lastUpdatedKeyPersistence.exists(1, CATEGORY) || appsDescription == null || lastUpdatedKey == null;
        if (!toRead  ) {
            byte[] data = lastUpdatedKeyPersistence.get(1, CATEGORY);
            if (!lastUpdatedKey.equals( new String(data))) {
                toRead = true;
            }
        }

        if (toRead) {
            localizeFile();
            this.lastUpdatedKey = String.valueOf(new Date().getTime());
            lastUpdatedKeyPersistence.update(1,CATEGORY, this.lastUpdatedKey.getBytes());
            loadAppDescription();
        }
    }

    private void loadAppDescription() throws  Exception {
        this.appsDescription = TamilUtils.deSerializeNonRootElement(this.path, AppsDescription.class);
    }

    private void localizeFile()  {
        File file = new File(path);
        if (!file.exists()) {
            InputStream in = DefinitionFactory.class.getResourceAsStream("/data/applications.xml");
            try {
                TamilUtils.writeToFile(file, in);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
