package tamil.util.regex;

import tamil.lang.exception.TamilPlatformException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;

/**
 * <p>
 * A list of one or more compiled {@link java.util.regex.Matcher}. This matcher finds something when one of the internal matchers finds.
 * If multiple matches find, the one with lower start index is used to return the selection. Sub sequent finds can make use of other matchers.
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
     *
     * @return the start index
     */
    public int start() {
        if (shortDistantMatcher == null) {
            throw new TamilPlatformException("No match was found");
        }
        return shortDistantMatcher.start();
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
        if (shortDistantMatcher == null) {
            List<Matcher> listFound = new ArrayList<Matcher>();


            for (Matcher m : list) {
                if (m.find()) {

                    listFound.add(m);
                }
            }
            list = listFound;
            Collections.sort(list, new Comparator<Matcher>() {
                public int compare(Matcher o1, Matcher o2) {
                    return o1.start() - o2.start();
                }
            });
            if (list.isEmpty()) {
                return false;
            } else {
                shortDistantMatcher = list.get(0);
                return true;
            }
        } else {
            shortDistantMatcher = nextUnConsumedMatcher();
            if (shortDistantMatcher == null) {
                return find();
            } else {
                return true;
            }
        }
    }

    private Matcher nextUnConsumedMatcher() {
        int index = list.indexOf(shortDistantMatcher);
        if (index < list.size() - 1) {
            for (int i = index + 1; i < list.size(); i++) {
                if (list.get(i).start() >= shortDistantMatcher.end()) {
                    return list.get(i);
                }
            }
        }
        return null;

    }

    public int end() {
        if (shortDistantMatcher == null) {
            throw new TamilPlatformException("No match was found");
        }
        return shortDistantMatcher.end();
    }


}
