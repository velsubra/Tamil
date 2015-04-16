package my.interest.lang.tamil.bean;

import my.interest.lang.tamil.TamilUtils;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.known.derived.PanhpupPeyarththiribu;
import my.interest.lang.tamil.internal.api.HandlerFactory;

import tamil.lang.known.AbstractVinaiyadiPeyarechcham;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.derived.VinaiMuttu;
import tamil.lang.known.derived.VinaiyadiDerivative;
import tamil.lang.known.non.derived.*;
import my.interest.lang.tamil.punar.TamilWordPartContainer;

import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class SimpleSplitResult implements Comparable {

    private static TamilDictionary dictionary = TamilFactory.getSystemDictionary();

    private Map<String, List<IKnownWord>> mapContext = null;

    public Map<String, List<IKnownWord>> getMapContext() {
        return mapContext;
    }

    public void insertIntoList(String s) {
        int index = 0;
        for (int i = 0; i < splitList.size(); i++) {
            if (s.compareTo(splitList.get(i)) <= 0) {
                index = i;
                break;
            }

        }
        splitList.add(index, s);
    }

    public List<String> getSplitList() {
        return splitList;
    }

    public SimpleSplitResult duplicate() {
        SimpleSplitResult ret = new SimpleSplitResult();
        for (String item : splitList) {
            ret.getSplitList().add(item);
        }
        return ret;
    }

    public void setSplitList(List<String> splitList) {
        this.splitList = splitList;
    }

    private List<String> splitList = new ArrayList<String>();

    @Override
    public String toString() {
        return TamilUtils.getSeparatedListOfString(splitList, "", "", false, "+");
    }

//
//    /**
//     * @return null if it cannot split.
//     */
//    public List<SimpleSplitResult> checkAndsplitToKnown(boolean quickReturn) {
//        return checkAndsplitToKnown(false, quickReturn);
//    }

    /**
     * @return null if it cannot split.
     */
    public List<SimpleSplitResult> checkAndsplitToKnown(boolean firstpartUnknownOk, boolean quickReturn) {

        mapContext = new HashMap<String, List<IKnownWord>>();
        return checkAndsplitToKnownPrivate(firstpartUnknownOk, quickReturn, new HashMap<String, Set<SimpleSplitResult>>(), new HashSet<String>(), mapContext, new HashSet<String>(), false, true);
    }

    /**
     * @return null if it cannot split.
     */
    private List<SimpleSplitResult> checkAndsplitToKnownPrivate(boolean firstpartUnknownOk, boolean quickReturn, Map<String, Set<SimpleSplitResult>> processed, Set<String> processing, Map<String, List<IKnownWord>> cache, Set<String> unknowns, boolean firstIdaiAllowed, boolean lastpart) {
        if (splitList.isEmpty()) return null;

        List<SimpleSplitResult> list = new ArrayList<SimpleSplitResult>();
        list.add(new SimpleSplitResult());

        boolean first = true;
        boolean lastIdai = false;
        //boolean lastIdai = !firstIdaiAllowed;
        boolean lastsplit = false;
        int count1 = 0;
        for (String w : splitList) {
            if (w.length() > 40) return  null;// Defence for now.
            count1++;
            lastsplit = count1 == splitList.size();

            List<IKnownWord> knowns = cache.get(w);
            if (knowns == null) {
                knowns = unknowns.contains(w) ? null :  dictionary.search(TamilWord.from(w),true,1, null);
            }
            if (knowns == null || knowns.isEmpty()) {
                unknowns.add(w);
                Set<SimpleSplitResult> set = splitUnknown(first && firstpartUnknownOk && splitList.size() == 1, quickReturn, w, processed, processing, cache, unknowns, !lastIdai && firstIdaiAllowed, lastsplit && lastpart);
                if (set == null || set.isEmpty()) {
                    if (first && firstpartUnknownOk) {
                        TamilWord word = TamilWord.from(w);
                        TamilWordPartContainer cont = new TamilWordPartContainer(word);
                        if (cont.isEndingFine()) {
                            if (knowns == null) {
                                knowns = new ArrayList<IKnownWord>();
                            }
                            knowns.add(new Theriyaachchol(word));
                            first = false;
                        } else {
                            return null;
                        }
                    } else {
                        return null;
                    }
                }
                if (knowns == null || knowns.isEmpty()) {
                    //set containing multiple split
                    List<SimpleSplitResult> sub = new ArrayList<SimpleSplitResult>(set);
                    HashMap<SimpleSplitResult, List<SimpleSplitResult>> allPossibilities = new HashMap<SimpleSplitResult, List<SimpleSplitResult>>();
                    allPossibilities.put(sub.get(0), list);

                    //Take copy
                    for (int i = 1; i < sub.size(); i++) {
                        allPossibilities.put(sub.get(i), duplicate(list));
                    }


                    for (Map.Entry<SimpleSplitResult, List<SimpleSplitResult>> pair : allPossibilities.entrySet()) {
                        for (SimpleSplitResult h : pair.getValue()) {
                            h.getSplitList().addAll(pair.getKey().getSplitList());
                        }
                    }


                    //Copy them back
                    for (int i = 1; i < sub.size(); i++) {
                        list.addAll(allPossibilities.get(sub.get(i)));
                    }
                }


            }


            if (knowns != null && !knowns.isEmpty()) {
                cache.put(w, knowns);
                boolean allIdai = isAllJustOfKind(knowns, NonStartingIdaichchol.class);
                if (allIdai && lastIdai) {
                    return null;
                }

                //abort those would form two idais
                List<SimpleSplitResult> newList = new ArrayList<SimpleSplitResult>();

                for (SimpleSplitResult item : list) {

                    if (!firstIdaiAllowed && allIdai) {

                        if (item.getSplitList().isEmpty()) continue;
                    }
                    String toAdd = w;
                    if (knowns.size() == 1) {
                        if (IThiribu.class.isAssignableFrom(knowns.get(0).getClass())) {
                            toAdd = w + "(" + ((IThiribu) knowns.get(0)).getRealWord().getWord() + ")";
                            cache.put(toAdd, knowns);
                        }
                    }

                    item.getSplitList().add(toAdd);
                    newList.add(item);
                }
                list = newList;
                lastIdai = allIdai;
            }


        }

//        boolean validate = true;
//        if (!validate) {
//            return list;
//        }


        List<SimpleSplitResult> ret = new ArrayList<SimpleSplitResult>();

        for (SimpleSplitResult r : list) {

            if (ret.contains(r)) {
                continue;
            }
            List<IKnownWord> previousKnowns = null;
            boolean toadd = true;
            int count = 0;
            for (String w : r.getSplitList()) {
                List<IKnownWord> knowns = cache.get(w);
                if (knowns == null) {
                    toadd = false;
                    break;
                }
                count++;

                if (previousKnowns != null) {
                    toadd = !(isAllJustOfKind(previousKnowns, AbstractVinaiyadiPeyarechcham.class) && (isAllJustOfKind(knowns, VinaiyadiDerivative.class) || isAllJustOfKind(knowns, Vinaiyadi.class)));


                    if (toadd) {
                        //  toadd = !(isAllJustOfKind(previousKnowns, Vinaiyadi.class) && isAllJustOfKind(knowns, Vinaiyadi.class));
                        if (isAllJustOfKind(knowns, Vinaiyadi.class)) {
                            toadd = isAtleastKind(previousKnowns, IVinaiyechcham.class) || isAtleastKind(previousKnowns, Peyarchchol.class);
                        }


                    }

                    if (toadd) {

                        if (isAllJustOfKind(previousKnowns, PanhpupPeyarththiribu.class)) {
                            toadd = isAtleastKind(knowns, Peyarchchol.class);
                        }


                    }

                    if (toadd) {
                        //  toadd = !(isAllJustOfKind(previousKnowns, Vinaiyadi.class) && isAllJustOfKind(knowns, Vinaiyadi.class));
                        toadd = isAtleastKind(knowns, IPeyarchchol.class) || !(isAllJustOfKind(previousKnowns, Vinaiyadi.class));
                    }
//                    if (toadd) {
//                       toadd =   !isAllJustOfKind(knowns, Ottu.class) ||  !isAllJustOfKind(previousKnowns, IIdaichchol.class) ;
//                    }

                    if (toadd) {
                        toadd = !(isAllJustOfKind(previousKnowns, IVinaiyechcham.class) && isAllJustOfKind(knowns, IBasePeyar.class));
                    }

                    if (toadd) {
                        toadd = !(isAllJustOfKind(previousKnowns, IPeyarechcham.class) && isAllJustOfKind(knowns, IBaseVinai.class));
                    }

                    if (toadd) {
                        if (isAllJustOfKind(previousKnowns, VinaiMuttu.class)) {
                            toadd = isAtleastKind(knowns, INonStartingIdaichchol.class);
                        }
                    }
                    if (toadd && lastpart) {
                        if (count == r.getSplitList().size()) {
                            //last
                            toadd = !isAtleastKind(knowns, INonEndingIdaichchol.class);

                            if (toadd && isAllJustOfKind(knowns, Vinaiyadi.class)) {
                                toadd = isAtleastKind(previousKnowns, IPeyarchchol.class);
                            }
                        }


                    }
                    if (!toadd) {
                        break;
                    }

                } else {


                    if (!toadd) {
                        break;
                    }
                }
                previousKnowns = knowns;


            }

            if (toadd) {

                ret.add(r);
                if (quickReturn) {
                    break;
                }
            }
        }
        return ret;
    }

    private static boolean isAllJustOfKind(List<IKnownWord> all, Class<? extends IKnownWord> clz) {
        if (all == null) return false;
        for (IKnownWord w : all) {
            if (!clz.isAssignableFrom(w.getClass())) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAtleastKind(List<IKnownWord> all, Class<? extends IKnownWord> clz) {
        if (all == null) return false;
        for (IKnownWord w : all) {
            if (clz.isAssignableFrom(w.getClass())) {
                return true;
            }
        }
        return false;
    }


    private static List<SimpleSplitResult> duplicate(List<SimpleSplitResult> list) {
        List<SimpleSplitResult> duplicates = new ArrayList<SimpleSplitResult>();
        for (SimpleSplitResult split : list) {

            duplicates.add(split.duplicate());
        }
        return duplicates;
    }


    private Set<SimpleSplitResult> splitUnknown(boolean firstpartUnknownOk, boolean quickReturn, String joined, Map<String, Set<SimpleSplitResult>> processed, Set<String> processing, Map<String, List<IKnownWord>> cache, Set<String> unknowns, boolean firstIdaiAllowed, boolean last) {
        if (processed.containsKey(joined)) {
            return processed.get(joined);
        }
        if (processing.contains(joined)) {
            throw new RuntimeException(joined + " is being processed");
        }
        Set<SimpleSplitResult> list = new HashSet<SimpleSplitResult>();
        processing.add(joined);
        try {
            // SimpleSplitResult list = new SimpleSplitResult();
            // System.out.println("Splitting  :" + joined + " : " + processed.size());
            FullSplitResult full = HandlerFactory.split(joined);
            if (full != null && !full.isEmpty()) {
                for (HandlerSplitResult h : full) {
                    if (h.getSplitLists().isEmpty()) {
                        continue;
                    }
                    boolean lastsr = false;
                    int count = 0;
                    for (SimpleSplitResult sr : h.getSplitLists()) {
                        count++;
                        if (sr.getSplitList().contains(joined)) {
                            throw new RuntimeException(joined + " is again there at " + sr.getSplitList() + " Buggy handler exists:");
                        }
                        lastsr = count == h.getSplitLists().size();

                        List<SimpleSplitResult> sub = sr.checkAndsplitToKnownPrivate(firstpartUnknownOk, quickReturn, processed, processing, cache, unknowns, firstIdaiAllowed, lastsr && last);
                        if (sub == null || sub.isEmpty()) {
                            continue;
                        }


                        //  System.out.println("Handler:" + h.getHandlerName());
                        list.addAll(sub);

                    }
                }
            }

            if (list.isEmpty()) {
                list = null;
            }
            processed.put(joined, list);
            return list;
        } finally {
            processing.remove(joined);
        }
    }


    @Override
    public boolean equals(Object o) {
        return toString().compareTo(((SimpleSplitResult) o).toString()) == 0;
    }

    @Override
    public int compareTo(Object o) {
        return toString().compareTo(((SimpleSplitResult) o).toString());
    }
}
