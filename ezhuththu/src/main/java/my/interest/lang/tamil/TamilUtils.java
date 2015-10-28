package my.interest.lang.tamil;

import tamil.lang.exception.TamilPlatformException;
import tamil.util.regex.SimpleMatcher;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilUtils extends EzhuththuUtils {


    public final static long ONE_SECOND = 1000;
    public final static long SECONDS = 60;

    public final static long ONE_MINUTE = ONE_SECOND * 60;
    public final static long MINUTES = 60;

    public final static long ONE_HOUR = ONE_MINUTE * 60;
    public final static long HOURS = 24;

    public final static long ONE_DAY = ONE_HOUR * 24;

    public final static long ONE_YEAR = ONE_DAY * 365;

    private TamilUtils() {

    }

    public static int[] flattenList(List<int[]> list) {
        int length = 0;
        for (int[] representation : list) {
            length += representation.length;
        }
        int[] full = new int[length];
        int index = 0;
        for (int[] representation : list) {
            for (int codepoint : representation) {
                full[index++] = codepoint;
            }

        }
        return full;
    }


    public static String toRxGroupName(String str) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                buffer.append(ch);
            } else if (ch >= 'A' && ch <= 'Z') {
                buffer.append(ch);
            } else if (ch >= '0' && ch <= '0') {
                buffer.append(ch);
            } else {
                buffer.append("0");
            }
        }
        return buffer.toString();
    }

    /**
     * converts time (in milliseconds) to human-readable format
     * "<w> days, <x> hours, <y> minutes and (z) seconds"
     */

    public static String millisToLongDHMS(long duration) {
        return millisToLongDHMS(duration, true);
    }

    public static String millisToLongDHMS(long duration, boolean includemillis) {
        if (duration < 0) {
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
                if (!res.toString().equals("")) {
                    res.append(" and ");
                }

                res.append(duration).append(" ms");

            }
            return res.toString();
        } else {
            return duration + " ms";
        }
    }


//    public static boolean isUyarThinhaipPeyar(TamilWord w) {
//        //E.gகலாம் -உயர்திணை
//        List<IKnownWord> list = PersistenceInterface.findMatchingDerivedWords(w, true, 5, peyar);
//        boolean isUyarthinai = false;
//        for (IKnownWord ipeyar : list) {
//            if (((IPeyarchchol) ipeyar).isUyarThinhai()) {
//                isUyarthinai = true;
//                break;
//            }
//        }
//        return isUyarthinai;
//    }


    public static SimpleMatcher transpose(SimpleMatcher matcher) {
        return new SimpleMatcherTransposed0(matcher);
    }

    private static class SimpleMatcherTransposed0 implements SimpleMatcher {

        SimpleMatcher wrapped = null;
        private int lastMatchPointerStart = 0;
        private int lastMatchPointerEnd = 0;

        private int previousStart = 0;
        private boolean finished = false;

        SimpleMatcherTransposed0(SimpleMatcher wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public boolean find() {
            while (!finished) {

                boolean yes = wrapped.find();
                lastMatchPointerStart = previousStart;
                if (yes) {

                    previousStart = wrapped.end();
                    if (lastMatchPointerStart == wrapped.start()) {

                        continue;
                    } else {

                        lastMatchPointerEnd = wrapped.start();
                        return true;
                    }
                } else {
                    finished = true;
                    lastMatchPointerEnd = getSourceLength();
                    return lastMatchPointerStart < lastMatchPointerEnd;
                }
            }
            lastMatchPointerStart = lastMatchPointerEnd;
            return false;
        }

        @Override
        public int start() {

            return lastMatchPointerStart;
        }

        @Override
        public int end() {

            return lastMatchPointerEnd;
        }
        @Override
        public String getPattern() {
            return wrapped.getPattern();
        }
        @Override
        public boolean isTransposed() {
            return !wrapped.isTransposed();
        }


        public int getSourceLength() {
            return wrapped.getSourceLength();
        }
    }

}
