package tamil.lang.known;

import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class SimpleSplitKnownResult implements Comparable {
    public List<IKnownWord> getSplitList() {
        return splitList;
    }

    public SimpleSplitKnownResult duplicate() {
        SimpleSplitKnownResult ret = new SimpleSplitKnownResult();
        for (IKnownWord item : splitList) {
            ret.getSplitList().add(item);
        }
        return ret;
    }

    public void setSplitList(List<IKnownWord> splitList) {
        this.splitList = splitList;
    }

    private List<IKnownWord> splitList = new ArrayList<IKnownWord>();

    @Override
    public String toString() {
        return splitList.toString();
    }




    @Override
    public boolean equals(Object o) {
        return toString().compareTo(((SimpleSplitKnownResult) o).toString()) == 0 ;
    }

    @Override
    public int compareTo(Object o) {
        return toString().compareTo(((SimpleSplitKnownResult) o).toString());
    }
}
