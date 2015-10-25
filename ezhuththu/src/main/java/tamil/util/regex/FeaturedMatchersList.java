package tamil.util.regex;

import tamil.lang.exception.TamilPlatformException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * <p>
 *      A list of one or more compiled {@link java.util.regex.Matcher}. This matcher finds something when one of the internal matchers finds.
 *      If multiple matches find, the one with lower start index is used to return the selection. Sub sequent finds can make use of other matchers.
 * </p>
 *
 * @author velsubra
 */
public final class FeaturedMatchersList implements SimpleMatcher {

    List<Matcher> list = null;
    private Matcher shortDistantMatcher = null;

    FeaturedMatchersList(List<Matcher> list) {
        this.list = list;
    }


    /**
     * Returns the start index.
     * @return the start index
     */
    public int start() {
        if (shortDistantMatcher == null) {
            throw new TamilPlatformException("No match was found");
        }
        return  shortDistantMatcher.start();
    }


    public boolean matches() {
        for (Matcher m : list) {
            if (m.matches()) {
                return true;
            }
        }
        return false;
    }

    public boolean find() {
        List<Matcher> listFound = new ArrayList<Matcher>();
        shortDistantMatcher = null;
        int shortDistant = 0;
        for (Matcher m : list) {
            if (m.find()) {
                if (shortDistantMatcher == null) {
                    shortDistantMatcher = m;
                    shortDistant = m.start();
                }  else {
                    if (shortDistant > m.start()) {
                        shortDistant = m.start();
                        shortDistantMatcher = m;
                    }
                }
                listFound.add(m);
            }
        }
        list = listFound;
        return !list.isEmpty();
    }

    public int end() {
        if (shortDistantMatcher == null) {
            throw new TamilPlatformException("No match was found");
        }
        return  shortDistantMatcher.end();
    }


}
