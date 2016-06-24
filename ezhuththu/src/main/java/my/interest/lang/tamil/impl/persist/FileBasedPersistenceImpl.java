package my.interest.lang.tamil.impl.persist;

import my.interest.lang.tamil.TamilUtils;
import tamil.lang.api.persist.object.ObjectPersistenceInterface;
import tamil.lang.exception.TamilPlatformException;
import tamil.lang.exception.service.ServiceException;

import java.io.File;
import java.security.AccessControlException;
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
        while (idstr.length() < 20) {
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
        if (TamilUtils.isFileExistingNFS(file)) {
            throw new ServiceException("Retry with different id. Data already exists:" + file.getAbsolutePath());
        }
        try {
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                throw new ServiceException("Could not create  parent directories:" + file.getParentFile().getAbsolutePath());
            }

            if (!file.createNewFile()) {
                throw new ServiceException("Please retry with different id: File might got just created at:" + file.getAbsolutePath());
            }
          update(id, name, data);
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
        File r_filelck = new File(file.getAbsolutePath() + ".r_lck");
        File filelck = new File(file.getAbsolutePath() + ".w_lck");
        if (TamilUtils.isFileExistingNFS(filelck)) {
            throw new ServiceException("Lock file already exists at:" + file.getAbsolutePath());
        }
        try {

            if (!TamilUtils.isFileExistingNFS(file)) {
                throw new ServiceException("Data does not exists at:" + file.getAbsolutePath());
            }

            int retry = 0;
            while (TamilUtils.isFileExistingNFS(r_filelck)) {
                if (retry == 1000) {
                    throw new ServiceException("Read seems to be busy. please retry after some time.");
                }
                retry++;

                Thread.currentThread().sleep(100);

            }

            try {
               // System.out.println("Writing size:" + data.length +":" + new String(data));
                TamilUtils.writeToFile(file, data);
            } catch (TamilPlatformException tpe) {
                throw tpe;
            }
        } catch (Exception e) {
            e.printStackTrace();

            throw new ServiceException(e.getMessage());
        } finally {
            if (TamilUtils.isFileExistingNFS(filelck)) {
                filelck.delete();
            }
        }
    }

    public byte[] get(long id, String name) throws TamilPlatformException {
        File file = mapToFile(id, name);
        File r_filelck = new File(file.getAbsolutePath() + ".r_lck");
        try {

            r_filelck.createNewFile();

            File w_filelck = new File(file.getAbsolutePath() + ".w_lck");
            int retry = 0;
            while (TamilUtils.isFileExistingNFS(w_filelck)) {
                if (retry == 1000) {
                    throw new ServiceException("Update seems to be busy. please retry after some time.");
                }
                retry++;

                Thread.currentThread().sleep(100);

            }

            if (!TamilUtils.isFileExistingNFS(file)) {
                throw new ServiceException("Data does not exists");
            }

            try {

                byte[] data = null;
                for (int i =0; i < 10; i++) {
                    data = TamilUtils.readAllFromFile(file.getAbsolutePath());
                    if (data.length ==0) {
                        Thread.currentThread().sleep(100);
                    } else {
                        break;
                    }
                }
              //  System.out.println("Reading size:" + data.length +":" + new String(data));
                if (data.length == 0) {
                    throw new Exception("Empty file");
                }
                return data;
            } catch (TamilPlatformException tpe) {
                throw tpe;
            }
        } catch (Exception e) {
            e.printStackTrace();

            throw new ServiceException(e.getMessage());
        } finally {
            if (TamilUtils.isFileExistingNFS(r_filelck)) {
                r_filelck.delete();
            }
        }
    }

    public void delete(long id, String name) throws TamilPlatformException {
        File file = mapToFile(id, name);
        if (!TamilUtils.isFileExistingNFS(file)) {
            throw new ServiceException("Data does not exists:" + file.getAbsolutePath());
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
        return TamilUtils.isFileExistingNFS(file);
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
        try {
            return new File("/customer/scratch").exists();
        } catch (AccessControlException ae) {
            ae.printStackTrace();
            return false;
        }
    }


    static File WORK_DIR = null;

    public static File getWorkDir() {
        if (WORK_DIR == null) {
            if (isOnCloud()) {
                WORK_DIR = new File("/customer/scratch/i18n");
            } else {
               try {
                   WORK_DIR = new File(System.getProperty("user.home"), "tamil-platform");
                   if (!WORK_DIR.exists()) {
                       WORK_DIR.mkdirs();
                   }
               } catch (AccessControlException ae) {
                   WORK_DIR = new File("/tmp");
               }

                System.out.println("Work Dir:" + WORK_DIR.getAbsolutePath());

            }
        }
        return WORK_DIR;
    }

}
