package tamil.util.regex;

import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.util.OrderedMap;
import tamil.lang.exception.TamilPlatformException;

import java.util.*;
import java.util.regex.Matcher;

/**
 * <p>
 * A list of one or more compiled {@link java.util.regex.Matcher}. This matcher finds something when one of the internal matcher finds.
 * If multiple matches find, the one with lower start index is used to return the selection. Sub sequent finds can make use of other matchers.
 * </p>
 *
 * @author velsubra
 */
public final class FeaturedMatchersList implements SimpleMatcher {

    List<Matcher> list = null;
    // LinkedList<Matcher> foundUnused = null;
    private String basePattern;
    private Matcher shortDistantMatcher = null;
    private CharSequence source = null;
    private  OrderedMap<Matcher, TamilPattern> mapMatcherToPatter = null;

    FeaturedMatchersList(String basePattern, OrderedMap<Matcher, TamilPattern> map, CharSequence source) {
        this.mapMatcherToPatter = map;
        this.list = new ArrayList<Matcher>(map.keySet());
        this.basePattern = basePattern;
        this.source = source;

    }


    private void searchFirst() {
        LinkedList<Matcher> temp = new LinkedList<Matcher>();
        shortDistantMatcher = null;

        for (Matcher m : list) {
            if (m.find()) {
                temp.add(m);
            }

        }
        if (temp.isEmpty()) return;
        Collections.sort(temp, new Comparator<Matcher>() {
            public int compare(Matcher o1, Matcher o2) {
                return o1.start() - o2.start();
            }
        });
        this.list = new ArrayList<Matcher>(temp);

        shortDistantMatcher = list.remove(0);

    }


    private void searchAgainAndAdjust() {
        System.out.println("Used Memory MB:"
                + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000);


        LinkedList<Matcher> temp = new LinkedList<Matcher>();
        int lastIndex = shortDistantMatcher.end();
        if (shortDistantMatcher.find()) {
            list.add(shortDistantMatcher);
        }
        shortDistantMatcher = null;
        for (Matcher m : list) {
            while (true) {
                if (m.start() < lastIndex) {
                    if (m.find(lastIndex)) {
                        temp.add(m);
                        break;
                    } else {
                        break;
                    }
                } else {
                    temp.add(m);
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
        shortDistantMatcher = list.remove(0);

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
            if (!list.isEmpty()) {
                searchFirst();
            }

        } else {

            searchAgainAndAdjust();

        }
        return shortDistantMatcher != null;


    }

//    private Matcher nextUnConsumedMatcher() {
//        int index = list.indexOf(shortDistantMatcher);
//        if (index < list.size() - 1) {
//            for (int i = index + 1; i < list.size(); i++) {
//                if (list.get(i).start() >= shortDistantMatcher.end()) {
//                    return list.get(i);
//                }
//            }
//        }
//        return null;
//
//    }

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

    public String group() {
        if (shortDistantMatcher == null) {
            throw new TamilPlatformException("No match was found");
        }
        return shortDistantMatcher.group();
    }

    public String group(String name) {
        if (shortDistantMatcher == null) {
            throw new TamilPlatformException("No match was found");
        }
        return shortDistantMatcher.group(name);
    }

    public int groupCount() {
        if (shortDistantMatcher == null) {
            throw new TamilPlatformException("No match was found");
        }
        return shortDistantMatcher.groupCount();
    }

    public String group(int group) {
        return null;
    }

    public MatchingModel buildMatchingModel() {
        return TamilUtils.buildMatchingModel(mapMatcherToPatter.get(shortDistantMatcher), shortDistantMatcher);
    }

    public String getGroupName() {
        if (shortDistantMatcher == null) {
            throw new TamilPlatformException("No match was found");
        }
        return shortDistantMatcher.group();
    }


}
