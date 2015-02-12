package my.interest.lang.tamil;

import my.interest.lang.tamil.internal.api.IPropertyFinder;
import my.interest.lang.tamil.punar.EnglishToTamilPropertyFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class StringUtils {
    static final char quote = '\"';

    public static List<String> parseWithQuote(String input, String with) {
        return parseWithQuote(input, with, quote);
    }

    private static String stripQuote(String s, char q) {
        if (s.length() > 1 && s.startsWith(String.valueOf(q)) && s.endsWith(String.valueOf(q))) {
            return s.substring(1, s.length() - 1);
        } else {
            return s;
        }
    }

    private static String unEscape(String where, char quote) {


        boolean escaping = false;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < where.length(); i++) {

            char c = where.charAt(i);
            if (escaping) {
                if (c == '\\') {

                    buffer.append(c);
                } else if (c == quote) {

                    buffer.append(c);
                } else {
                    buffer.append(c);
                }
                escaping = false;
            } else {
                if (c == '\\') {
                    escaping = true;
                } else {
                    buffer.append(c);
                }
            }


        }

        return buffer.toString();


    }


    public static List<String> parseWithQuote(String input, String with, char quote) {
        List<String> ret = new ArrayList<String>();
        if (input == null) return ret;
        int index_D = findIndexOf(with, input, quote);

        while (index_D >= 0) {

            String text = input.substring(0, index_D);
            if (text.length() > 0) {
                ret.add(unEscape(stripQuote(text, quote), quote));
            }
            input = input.substring(index_D + with.length(), input.length());

            index_D = findIndexOf(with, input, quote);
        }

        if (!input.trim().equals("")) {
            ret.add(unEscape(stripQuote(input, quote), quote));
        }

        return ret;
    }


    public static int findIndexOf(String what, String where, char quote) {
        if (where == null || what == null) return -1;
        boolean insideQuote = false;
        boolean escaping = false;

        for (int i = 0; i < where.length(); i++) {
            if (where.length() - i < what.length()) {
                return -1;
            }
            char c = where.charAt(i);
            if (c == '\\') {
                escaping = !escaping;
            } else if (c == quote) {
                if (!escaping) {
                    insideQuote = !insideQuote;
                }
                escaping = false;
            } else {

                if (!insideQuote) {
                    if (where.substring(i, where.length()).startsWith(what)) {
                        return i;
                    }
                }
                escaping = false;
            }


        }
        if (insideQuote) {
            throw new IllegalArgumentException("Un-matched quote.");
        }
        if (escaping) {
            throw new IllegalArgumentException("Invalid escape sequence.");
        }
        return -1;

    }



    public static String replaceFor$(String origString, IPropertyFinder keys, boolean exceptionWhenNotAllResolved) {
        return replace("${", "}", origString, keys, exceptionWhenNotAllResolved, false, false);
    }

    public static String replaceForT(String origString) {
        return replace("T{", "}", origString, EnglishToTamilPropertyFinder.FINDER, false, false, false);
    }

    public static String replace(String origString, IPropertyFinder keys, boolean exceptionWhenNotAllResolved) {
        return replace(null, null, origString, keys, exceptionWhenNotAllResolved, true, true);
    }

    public static String replace(String openmarker, String closeMarker, String origString, IPropertyFinder keys, boolean exceptionWhenNotAllResolved, boolean esecapeUnresolved, boolean keepUnresolved) {
        if (closeMarker == null) {
            openmarker = null;
        }
        if (openmarker == null || openmarker.trim().equals("")) {
            openmarker = "${";
            closeMarker = "}";
        }
        if (keys == null || origString == null) return origString;
        StringBuffer finalString = new StringBuffer();
        int index = 0;
        int i = 0;
        String key = null;
        // CheckStyle:MagicNumber OFF
        while ((index = origString.indexOf(openmarker, i)) > -1) {
            int endIndex = origString.indexOf(closeMarker, index + openmarker.length() + closeMarker.length() -1);
            if (endIndex < 0) {
                throw new RuntimeException("Matching marker " + closeMarker + " could not be found at:" +
                        origString);
            }
            key = origString.substring(index + openmarker.length(), endIndex);
            finalString.append(origString.substring(i, index));
            String val = keys.findProperty(key);
            if (val != null) {
                finalString.append(val);
            } else {
                if (exceptionWhenNotAllResolved) {
                    throw new RuntimeException("Variable ${" + key + "} could not be resolved.");
                }
                if (esecapeUnresolved) {
                    finalString.append("\\");
                }
                if (keepUnresolved) {
                    finalString.append(openmarker);
                    finalString.append(key);
                }
                if (esecapeUnresolved) {
                    finalString.append("\\");
                }
                if (keepUnresolved) {
                    finalString.append(closeMarker);
                }
            }
            i = index + openmarker.length() + closeMarker.length() + key.length();
        }

        finalString.append(origString.substring(i));
        return finalString.toString();
    }
}