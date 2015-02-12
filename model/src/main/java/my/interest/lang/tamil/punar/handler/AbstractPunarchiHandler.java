package my.interest.lang.tamil.punar.handler;

import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import tamil.lang.TamilWord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public abstract class AbstractPunarchiHandler {

    public abstract String getName();

    public String getDescription() {
        return getName();
    }

    protected boolean isEmptyVaruMozhiOk() {
        return false;
    }


    public List<TamilWordSplitResult> splitAll(TamilWordPartContainer result) {
        List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();
        splitAllInto(result, list);
        return list;
    }


    public void splitAllInto(TamilWordPartContainer result, List<TamilWordSplitResult> list) {


        TamilWord nilai = new TamilWord();
        TamilWord varum = result.getWord().duplicate();
        for (int i = 0; i < result.getWord().size() - (isEmptyVaruMozhiOk() ? 0 : 1); i++) {
            nilai.add(varum.remove(0));
            TamilWordPartContainer n = new TamilWordPartContainer(nilai.duplicate());
            TamilWordPartContainer v = new TamilWordPartContainer(varum.duplicate());
            if (!n.isPureTamilWord() || !v.isPureTamilWord()) continue;
            List<TamilWordSplitResult> attempted = splitAndFilter(n, v);

            if (attempted != null && !attempted.isEmpty()) {
                list.addAll(attempted);

            }
        }

    }


    protected List<TamilWordSplitResult> splitAndFilter(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        List<TamilWordSplitResult> list = split(nilai, varum);
        List<TamilWordSplitResult> ret = null;
        if (list != null) {
            ret = new ArrayList<TamilWordSplitResult>();
            for (TamilWordSplitResult r : list) {
                boolean valid = true;
                for (TamilWordPartContainer c : r) {
                    if (c.size() > 1 && (!c.isEndingFine() || c.isStartingWithMei())) {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    ret.add(r);
                }
            }
        }
        return ret;
    }


    /**
     * OVerride this for handing every candidate.
     *
     * @param nilai
     * @param varum
     * @return
     */
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        return null;
    }


//    /**
//     * This method is to be implemented when there is more than one possible results.
//     * @param nilai
//     * @param varum
//     * @return
//     */
//    public List<TamilWordPartContainer> joinAll(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
//
//        TamilWordPartContainer join = join(nilai, varum);
//        List<TamilWordPartContainer> list = new ArrayList<TamilWordPartContainer>();
//        if (join != null) {
//            list.add(join);
//            return list;
//        }
//        return Collections.emptyList();
//
//    }

    public abstract TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum);

}
