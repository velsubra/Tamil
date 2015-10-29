package tamil.util.regex;

import tamil.lang.exception.TamilPlatformException;

import java.util.*;
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
    LinkedList<Matcher> foundUnused = null;
    private String basePattern;
    private Matcher shortDistantMatcher = null;
    private CharSequence source = null;
    private int lastIndex = 0;

    FeaturedMatchersList(String basePattern, List<Matcher> list, CharSequence source) {
        this.list = new ArrayList<Matcher>(list);
        this.basePattern = basePattern;
        this.source = source;

    }


    private void searchAgain() {
        LinkedList<Matcher> temp = new LinkedList<Matcher>();
        shortDistantMatcher = null;

        for (Matcher m : list) {
            while (true) {
                if (m.find()) {
                    if (m.start() < lastIndex) {
                        continue;
                    }
                    temp.add(m);
                    break;
                } else {
                    break;
                }
            }
        }
        if (temp.isEmpty()) return;
        Collections.sort(temp, new Comparator<Matcher>() {
            public int compare(Matcher o1, Matcher o2) {
                return o1.start() - o2.start();
            }
        });
        this.list = new ArrayList<Matcher>(temp);


        shortDistantMatcher = temp.remove(0);
        this.foundUnused = temp;
    }


    private void disCardFoundMatchers() {
        LinkedList<Matcher> temp = new LinkedList<Matcher>();
        for (Matcher m : foundUnused) {
            if (m.start() >= lastIndex) {
                temp.add(m);
            }

        }
        this.foundUnused = temp;
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

    private void insertCurrentMatcher() {

        int count = -1;
        int index = -1;
        for (Matcher m : foundUnused) {
            count ++;
            if (m.start() < shortDistantMatcher.start()) {
                continue;
            }  else {

                index = count;
                break;
            }
        }
        if (index < 0) {
            foundUnused.addLast(shortDistantMatcher);
        }  else {
            foundUnused.add(index, shortDistantMatcher);
        }
    }

    public boolean find() {
        if (shortDistantMatcher == null) {
            searchAgain();

        } else {
            lastIndex = shortDistantMatcher.end();
            disCardFoundMatchers();
            if (foundUnused.isEmpty()) {
                searchAgain();
            } else {
                Matcher next = foundUnused.get(0);
                if (shortDistantMatcher.find()) {
                    if (shortDistantMatcher.start() <= next.start()) {
                        // Retain  shortDistantMatcher
                    } else {

                        //Insert
                        insertCurrentMatcher();
                        // get the first one.
                        shortDistantMatcher = foundUnused.remove(0);
                    }
                } else {
                    list.remove(shortDistantMatcher);
                    shortDistantMatcher = foundUnused.remove(0);
                }
            }

        }


        return shortDistantMatcher != null;


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

    public String getPattern() {
        return basePattern;
    }

    public boolean isTransposed() {
        return false;
    }

    public int getSourceLength() {
        return source.length();
    }


}
