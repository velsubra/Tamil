package my.interest.lang.tamil.punar.handler.iyalbu;

import tamil.lang.TamilWord;
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
public class JustAddHandler extends AbstractPunarchiHandler {
    @Override
    public String getName() {
        return "இயல்புபுணர்ச்சி -2";
    }

    public static final JustAddHandler HANDLER = new JustAddHandler();
    static final TamilWord IKKU = TamilWord.from("க்கு");


    @Override
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        if (nilai.isEndingWithMei() && varum.isStartingWithUyir()) return null;
        if (!varum.isStartingFine() && !IKKU.equals(varum.getWord())) return null;
        List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();
        //Split the one that ends in Nilai.
        TamilWordSplitResult split = new TamilWordSplitResult();
        split.add(nilai);
        split.add(varum);
        list.add(split);


        return list;
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        if (nilai.isEndingWithMei() && varum.isStartingWithUyir()) return null;
        TamilWord result = nilai.getWord().duplicate();
        result.addAll(varum.getWord());
        return new TamilWordPartContainer(result);


    }
}
