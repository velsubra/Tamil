package my.interest.lang.tamil.punar.handler.iyalbu;

import tamil.lang.TamilCharacter;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilWord;
import tamil.lang.TamilSimpleCharacter;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunarchiHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class IyalbuPunarchiHandler extends AbstractPunarchiHandler {
    @Override
    public String getName() {
        return "இயல்புபுணர்ச்சி -1 ";
    }

    public static final IyalbuPunarchiHandler HANDLER = new IyalbuPunarchiHandler();


    public List<TamilWordSplitResult> splitAll(TamilWordPartContainer result) {
        if (result.getWord().size() == 1) {
            if (result.getWord().get(0).isTamilLetter()) {
                if (result.getWord().get(0).asTamilCharacter().isUyirMeyyezhuththu()) {
                    TamilCharacter tc = (TamilCharacter) result.getWord().get(0);
                    List<TamilWordSplitResult> ret = new ArrayList<TamilWordSplitResult>();
                    TamilWordSplitResult r = new TamilWordSplitResult();
                    ret.add(r);
                    TamilWord w = new TamilWord();
                    w.add(tc.getMeiPart());
                    r.add(new TamilWordPartContainer(w));

                    w = new TamilWord();
                    w.add(tc.getUyirPart());
                    r.add(new TamilWordPartContainer(w));
                    return ret;
                }
            }
            return null;
        } else {
            List<TamilWordSplitResult> ret = super.splitAll(result);
            List<TamilWordSplitResult> last = split(result, new TamilWordPartContainer(new TamilWord()));
            if (last != null) {
                if (ret == null) {
                    ret = last;
                } else {
                    ret.addAll(last);
                }
            }
            return ret;
        }
    }


    @Override
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();
        //Split the one that ends in Nilai.
        if (!nilai.isEndingWithUyirMei()) {
            return null;
        }
        if (nilai.size() == 2) {
            if (!nilai.isStartingWithNedil()) {
                return null;
            }
        }

        TamilWord n = new TamilWord();
        n.addAll(nilai.getWord().subList(0, nilai.size() - 1));
        TamilCharacter last = (TamilCharacter) nilai.getWord().getLast();
        n.add(last.getMeiPart());

        TamilWord v = varum.getWord().duplicate();
        v.add(0, last.getUyirPart());

        TamilWordSplitResult split = new TamilWordSplitResult();
        split.add(new TamilWordPartContainer(n));
        split.add(new TamilWordPartContainer(v));
        list.add(split);


        return list;
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {

        if (varum.isStartingWithUyir() && nilai.isEndingWithMei()) {
            TamilWord result = nilai.getWord().duplicate();
            TamilCompoundCharacter mei = (TamilCompoundCharacter) result.removeLast();
            result.add(mei.addUyir((TamilSimpleCharacter) varum.getWord().getFirst()));
            result.addAll(varum.getWord().subList(1, varum.getWord().size()));
            return new TamilWordPartContainer(result);
        }
        return null;


    }
}
