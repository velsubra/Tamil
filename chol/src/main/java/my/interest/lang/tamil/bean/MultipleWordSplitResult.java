package my.interest.lang.tamil.bean;

import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.Theriyaachchol;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class MultipleWordSplitResult {

    public MultipleWordSplitResult(SimpleSplitResult givenWord) {
         this.givenWord = givenWord;
    }
    public SimpleSplitResult getGivenWord() {
        return givenWord;
    }



    public List<SimpleSplitResult> getSplit() {
        return split;
    }

    public void setSplit(List<SimpleSplitResult> split) {
        this.split = split;
    }

    SimpleSplitResult givenWord;
    List<SimpleSplitResult> split;


    public boolean isKnownToTheContext(String w) {
        Map<String, List<IKnownWord>> mapContext =  givenWord.getMapContext();

        List<IKnownWord> list = mapContext.get(w);
        if (list == null) {
            return false;
        }
        for (IKnownWord wrd : list) {
            if (Theriyaachchol.class.isAssignableFrom(wrd.getClass())) {
                return false;
            }
        }
        return !list.isEmpty();
    }

    public String getFirstFoundUnknown(SimpleSplitResult split) {
        for (String s : split.getSplitList()) {
            if (!isKnownToTheContext(s)) {
                return s;
            }
        }
        return null;
    }

    public boolean isKnown(String w) {
        Map<String, List<IKnownWord>> mapContext =  givenWord.getMapContext();
        List<IKnownWord> list = mapContext.get(w);
        if (list == null) {
            return false;
        }
        for (IKnownWord wrd : list) {
            if (Theriyaachchol.class.isAssignableFrom(wrd.getClass())) {
                return false;
            }
        }
        return !list.isEmpty();
    }

}
