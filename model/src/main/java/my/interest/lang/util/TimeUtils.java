package my.interest.lang.util;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TimeUtils {
    public final static long ONE_SECOND = 1000;
    public final static long SECONDS = 60;

    public final static long ONE_MINUTE = ONE_SECOND * 60;
    public final static long MINUTES = 60;

    public final static long ONE_HOUR = ONE_MINUTE * 60;
    public final static long HOURS = 24;

    public final static long ONE_DAY = ONE_HOUR * 24;

    public final static long ONE_YEAR = ONE_DAY * 365;

    private TimeUtils() {
    }

    /**
     * converts time (in milliseconds) to human-readable format
     *  "<w> days, <x> hours, <y> minutes and (z) seconds"
     */

    public static String millisToLongDHMS(long duration) {
        return millisToLongDHMS(duration, true);
    }

    public static String millisToLongDHMS(long duration, boolean includemillis) {
        if (duration < 0 ) {
            return "[Clock not in sync]";
        }
        StringBuffer res = new StringBuffer();
        long temp = 0;

        if (duration >= ONE_SECOND) {
            temp = duration / ONE_YEAR;
            if (temp > 0) {
                duration -= temp * ONE_YEAR;
                res.append(temp).append(" year").append(temp > 1 ? "s" : "").append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_DAY;
            if (temp > 0) {
                duration -= temp * ONE_DAY;
                res.append(temp).append(" day").append(temp > 1 ? "s" : "").append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_HOUR;
            if (temp > 0) {
                duration -= temp * ONE_HOUR;
                res.append(temp).append(" hour").append(temp > 1 ? "s" : "").append(duration >= ONE_MINUTE ? ", " :
                        "");
            }

            temp = duration / ONE_MINUTE;
            if (temp > 0) {
                duration -= temp * ONE_MINUTE;
                res.append(temp).append(" minute").append(temp > 1 ? "s" : "");
            }

            if (!res.toString().equals("") && duration >= ONE_SECOND) {
                if (includemillis) {
                    res.append(", ");
                } else {
                    res.append(" and ");
                }
            }

            temp = duration / ONE_SECOND;
            if (temp > 0) {
                duration -= temp * ONE_SECOND;
                res.append(temp).append(" second").append(temp > 1 ? "s" : "");
            }
            if (includemillis) {
                if (!res.toString().equals("") ) {
                    res.append(" and ");
                }

                res.append(duration).append(" ms");

            }
            return res.toString();
        } else {
            return duration + " ms";
        }
    }


}