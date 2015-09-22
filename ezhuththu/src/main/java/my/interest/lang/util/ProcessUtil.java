package my.interest.lang.util;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class ProcessUtil {
    private static String ANT_EXEC;
    private static String JAVA_EXEC;
    private static String SHELL_EXTENSION;
    private static String SHELL_EXEC;
    private static String SHELL_EXEC_EXIT_COMMAND;

    public static String getANT_EXEC() {
        return ANT_EXEC;
    }

    public static String getJAVA_EXEC() {
        return JAVA_EXEC;
    }

    public static String getENV_DEFAULT_ANT_HOME() {
        return ENV_DEFAULT_ANT_HOME;
    }

    public static String getENV_DEFAULT_JAVA_HOME() {
        return ENV_DEFAULT_JAVA_HOME;
    }

    public static int getEACH_PROCESS_TIMEOUT_SECS() {
        return EACH_PROCESS_TIMEOUT_SECS;
    }

    public static String getUSER_HOME() {
        return USER_HOME;
    }

    public static boolean isSHELL_SET_SETENV_STYLE() {
        return SHELL_SET_SETENV_STYLE;
    }

    public static boolean isOS_WINDOWS() {
        return OS_WINDOWS;
    }

    public static String getCOPY_COMMAND() {
        return COPY_COMMAND;
    }

    public static String getSHELL_SET_PROP_COMMAND() {
        return SHELL_SET_PROP_COMMAND;
    }

    public static String getSHELL_EXEC_EXIT_COMMAND() {
        return SHELL_EXEC_EXIT_COMMAND;
    }

    public static String getSHELL_EXEC() {
        return SHELL_EXEC;
    }

    public static String getSHELL_EXTENSION() {
        return SHELL_EXTENSION;
    }

    private static String SHELL_SET_PROP_COMMAND;
    private static String COPY_COMMAND;
    private static boolean OS_WINDOWS;
    private static boolean SHELL_SET_SETENV_STYLE = true;
    private static String USER_HOME;

    private static int EACH_PROCESS_TIMEOUT_SECS = 300;

    private static String ENV_DEFAULT_JAVA_HOME = null;
    private static String ENV_DEFAULT_ANT_HOME = null;

    static {
        USER_HOME = System.getProperty("user.home");
        ENV_DEFAULT_JAVA_HOME = System.getProperty("JAVA_HOME", System.getProperty("java.home"));
        ENV_DEFAULT_ANT_HOME =
                System.getProperty("ANT_HOME", System.getProperty("ant.home"));
        String osname = System.getProperty("os.name");
        if (osname != null && osname.toUpperCase().indexOf("WINDOWS") ==
                -1) {
            SHELL_SET_PROP_COMMAND = "setenv";
            ANT_EXEC = "ant";
            SHELL_EXTENSION = ".sh";
            SHELL_EXEC = "csh";
            SHELL_EXEC_EXIT_COMMAND = "exit";
            COPY_COMMAND = "cp";
            JAVA_EXEC = "java";
            //Make it false if  SHELL_SET_PROP_COMMAND
            //uses export style which requires = between var and value otherwise a space. export a=b vs setenv a b
            SHELL_SET_SETENV_STYLE = true;

        } else {
            SHELL_SET_PROP_COMMAND = "set";
            SHELL_EXTENSION = ".bat";
            ANT_EXEC = "ant.bat";
            SHELL_EXEC = "cmd";
            SHELL_EXEC_EXIT_COMMAND = "exit";
            COPY_COMMAND = "copy";
            SHELL_SET_SETENV_STYLE = false;
            JAVA_EXEC = "java.exe";
            OS_WINDOWS = true;
        }

    }
}

