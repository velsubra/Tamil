package my.interest.lang.tamil;

import tamil.util.IPropertyFinder;
import my.interest.lang.tamil.punar.EnglishToTamilPropertyFinder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
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

    public static final class IndexContext {
        IndexContext() {

        }

//        private int mapFinalIndex(int finalIndex)  {
//            if (parent != null)  {
//                Range inner =   parent.getSourceIndex(finalIndex);
//                return inner.from;
//
//            }  else {
//                return  finalIndex;
//            }
//        }

        public Range getSourceIndexRecursively(int finalIndex) {
            Range range = getSourceIndex(finalIndex);
            if (parent == null) {
                return range;
            }  else {
                return  parent.getSourceIndexRecursively(range.from);
            }
        }

        public Range getSourceIndex(int finalIndex) {

            if (finalIndex >= finalString.length()) {
                return new Range(0);
                //throw new IndexOutOfBoundsException("String size is " + finalString.length() + " Given index:" + finalIndex);
            }
            Iterator<Range> it = ranges.keySet().iterator();
            Range pretarget = null;

            while (it.hasNext()) {
                Range target = it.next();

                if (finalIndex >= target.to) {
                    pretarget = target;
                    continue;
                } else {
                    // Last range covering is found.
                    if (finalIndex >= target.from) {
                        //Range just matches
                        return ranges.get(target);
                    } else {
                        // index does not fall into the known range
                        if (pretarget == null) {
                            return new Range(finalIndex);
                        } else {
                            Range prevsource = ranges.get(pretarget);
                            return new Range(prevsource.to + finalIndex - target.from);
                        }
                    }
                }
            }
            if (pretarget == null) {
                return new Range(finalIndex);
            } else {
                Range prevsource = ranges.get(pretarget);
                return new Range(prevsource.to + finalIndex - pretarget.to);
            }

        }

       public static final class Range {
            public Range(int from) {
                this(from, from);
            }

            public Range(int from, int to) {
                this.from = from;
                this.to = to;
            }

            public int from;
            public int to;

            public String toString() {
                return "From:" + from + " To:" + to;
            }
        }

        public  IndexContext resolveInner(String openmarker, String closeMarker,boolean exceptionWhenNotAllResolved, boolean esecapeUnresolved, boolean keepUnresolved, int level) {
            if (moreToResolve && level <5) {
                IndexContext innerContext =  replaceWithContext(this, openmarker, closeMarker, finalString, this.keys, exceptionWhenNotAllResolved, esecapeUnresolved, keepUnresolved, level);

                return innerContext;
            }
            else {
                return this;
            }
        }

        public String finalString = null;
        public LinkedHashMap<Range, Range> ranges = new LinkedHashMap<Range, Range>();
        public boolean moreToResolve = false;

        public IndexContext parent = null;
        private IPropertyFinder keys = null;

    }

    public static String replace(String openmarker, String closeMarker, String origString, IPropertyFinder keys, boolean exceptionWhenNotAllResolved, boolean esecapeUnresolved, boolean keepUnresolved) {
        return replaceWithContext(openmarker, closeMarker, origString, keys, exceptionWhenNotAllResolved, esecapeUnresolved, keepUnresolved).finalString;
    }

    public static IndexContext replaceWithContext(String openmarker, String closeMarker, String origString, IPropertyFinder keys, boolean exceptionWhenNotAllResolved, boolean esecapeUnresolved, boolean keepUnresolved) {
        return replaceWithContext(null, openmarker, closeMarker, origString, keys,exceptionWhenNotAllResolved, esecapeUnresolved, keepUnresolved, 0);
    }
    public static IndexContext replaceWithContext(IndexContext parent, String openmarker, String closeMarker, String origString, IPropertyFinder keys, boolean exceptionWhenNotAllResolved, boolean esecapeUnresolved, boolean keepUnresolved, int depth) {
        if (closeMarker == null) {
            openmarker = null;
        }
        if (openmarker == null || openmarker.trim().equals("")) {
            openmarker = "${";
            closeMarker = "}";
        }
        IndexContext context = new IndexContext();
        context.parent = parent;
        context.keys = keys;
        context.finalString = origString;
        if (keys == null || origString == null) return context;
        StringBuffer finalString = new StringBuffer();
        int index = 0;
        int i = 0;
        String key = null;
        // CheckStyle:MagicNumber OFF
        while ((index = origString.indexOf(openmarker, i)) > -1) {
           // String remaining = origString.substring(index + openmarker.length());
           // String resolved =  origString.substring(0,index + openmarker.length());

            //int endIndex = origString.indexOf(closeMarker, index + openmarker.length() + closeMarker.length() - 1);
            int endIndex = origString.indexOf(closeMarker, index + openmarker.length());
            if (endIndex < 0) {
                throw new RuntimeException("Matching marker " + closeMarker + " could not be found at:" +
                        origString);
            }
            IndexContext.Range sourcerange = new IndexContext.Range(index, endIndex + closeMarker.length());
            IndexContext.Range endrange = null;

            key = origString.substring(index + openmarker.length(), endIndex);
            finalString.append(origString.substring(i, index));
            String val = keys.findProperty(key);
            if (val != null) {
                endrange = new IndexContext.Range(finalString.length(), finalString.length() + val.length());
                finalString.append(val);
                if (!context.moreToResolve) {
                    context.moreToResolve = val.indexOf(openmarker) >= 0;
                }

            } else {
                if (exceptionWhenNotAllResolved) {
                    throw new RuntimeException("Variable " + openmarker + key + closeMarker + " could not be resolved.");
                }
                int mark = finalString.length();
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

                endrange = new IndexContext.Range(mark, finalString.length() - mark);
            }
            context.ranges.put(endrange, sourcerange);
            i = index + openmarker.length() + closeMarker.length() + key.length();
        }

        finalString.append(origString.substring(i));
        context.finalString = finalString.toString();
        return  context.resolveInner(openmarker,closeMarker,exceptionWhenNotAllResolved,esecapeUnresolved,keepUnresolved,depth ++);

    }
}