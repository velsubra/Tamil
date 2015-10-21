package my.interest.lang.tamil.impl.persist;

import my.interest.lang.tamil.TamilUtils;
import tamil.lang.api.persist.object.ObjectPersistenceInterface;
import tamil.lang.exception.TamilPlatformException;
import tamil.lang.exception.service.ServiceException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class FileBasedPersistenceImpl implements ObjectPersistenceInterface {
    File workDir = null;
    String extension = null;

    public FileBasedPersistenceImpl(String extension) {
        workDir = getWorkDir();
        if (extension == null || extension.trim().equals("")) {
            extension = "dat";
        }
        this.extension = extension;
    }


    private File mapToFile(long id, String name) {
        File file = mapToFile(name);
        String idstr = String.valueOf(id);
        while (idstr.length() < 10) {
            idstr = "0" + idstr;
        }
        return new File(file, idstr + "." + extension);
    }

    private File mapToFile(String name) {
        if (name != null) {
            name = name.trim();
            name = name.replaceAll("\\\\", "/");
        }

        while (name.endsWith("/")) {
            name = name.substring(0, name.length() - 1);
        }

        while (name.startsWith("/")) {
            name = name.substring(1);
        }

        File file = null;
        if (name == null || name.equals("")) {
            file = new File(workDir.getAbsolutePath());
        } else {
            file = new File(workDir, name);
        }
        return file;
    }


    private void createInternal(long id, String name, byte[] data) throws TamilPlatformException {
        File file = mapToFile(id, name);
        if (file.exists()) {
            throw new ServiceException("Retry with different id. Data already exists:" + file.getAbsolutePath());
        }
        try {
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                throw new ServiceException("Could not create  parent directories:" + file.getParentFile().getAbsolutePath());
            }

            if (!file.createNewFile()) {
                throw new ServiceException("Please retry with different id: File might got just created at:" + file.getAbsolutePath());
            }
            TamilUtils.writeToFile(file, data);
        } catch (TamilPlatformException tpe) {
            throw tpe;
        } catch (Exception e) {
            e.printStackTrace();

            throw new ServiceException(e.getMessage());
        }
    }

    public long create(String name, byte[] data) throws TamilPlatformException {
        long tryValue = list(name).size() + 1;
        for (int i = 0; i < 3; i++) {
            try {
                createInternal(tryValue, name, data);
                return tryValue;
            } catch (Exception e) {
                //Retry
                tryValue++;
            }
        }
        throw new TamilPlatformException("Unable to create object with id:" + tryValue + " name:" + name);
    }

    public void update(long id, String name, byte[] data) throws TamilPlatformException {
        File file = mapToFile(id, name);
        if (!file.exists()) {
            throw new ServiceException("Data does not exists at:" + file.getAbsolutePath());
        }
        try {
            TamilUtils.writeToFile(file, data);
        } catch (TamilPlatformException tpe) {
            throw tpe;
        } catch (Exception e) {
            e.printStackTrace();

            throw new ServiceException(e.getMessage());
        }
    }

    public byte[] get(long id, String name) throws TamilPlatformException {
        File file = mapToFile(id, name);
        if (!file.exists()) {
            throw new ServiceException("Data does not exists");
        }
        try {
            return TamilUtils.readAllFromFile(file.getAbsolutePath());
        } catch (TamilPlatformException tpe) {
            throw tpe;
        } catch (Exception e) {
            e.printStackTrace();

            throw new ServiceException(e.getMessage());
        }
    }

    public void delete(long id, String name) throws TamilPlatformException {
        File file = mapToFile(id, name);
        if (!file.exists()) {
            throw new ServiceException("Data does not exists");
        }
        try {
            if (!file.delete()) {
                throw new ServiceException("Cloud not delete file " + file.getAbsolutePath());
            }
        } catch (TamilPlatformException tpe) {
            throw tpe;
        } catch (Exception e) {
            e.printStackTrace();

            throw new ServiceException(e.getMessage());
        }
    }

    public boolean exists(long id, String name) throws TamilPlatformException {
        File file = mapToFile(id, name);
        return file.exists();
    }

    public List<Long> list(String name) throws TamilPlatformException {
        File file = mapToFile(name);
        if (!file.exists()) {
            return Collections.emptyList();
        }
        try {
            String[] names = TamilUtils.findFilesWithPatternAt(file, ".*\\." + extension);
            List<Long> ret = new ArrayList<Long>();
            for (String n : names) {
                try {
                    ret.add(Long.parseLong(n.substring(0, n.length() - 1 - extension.length())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return ret;
        } catch (TamilPlatformException tpe) {
            throw tpe;
        } catch (Exception e) {
            e.printStackTrace();

            throw new ServiceException(e.getMessage());
        }
    }


    public static boolean isOnCloud() {
        return new File("/customer/scratch").exists();
    }


    static File WORK_DIR = null;

    public static File getWorkDir() {
        if (WORK_DIR == null) {
            if (isOnCloud()) {
                WORK_DIR = new File("/customer/scratch/i18n");
            } else {

                WORK_DIR = new File(System.getProperty("user.home"), "tamil-platform");
                if (!WORK_DIR.exists()) {
                    WORK_DIR.mkdirs();
                }
                System.out.println("Work Dir:" + WORK_DIR.getAbsolutePath());

            }
        }
        return WORK_DIR;
    }

}
