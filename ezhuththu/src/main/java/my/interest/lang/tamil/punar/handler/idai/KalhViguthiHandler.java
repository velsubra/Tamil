package my.interest.lang.tamil.punar.handler.idai;

import tamil.lang.TamilCompoundCharacter;
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
public class KalhViguthiHandler extends AbstractPunarchiHandler {

    public static final KalhViguthiHandler HANDLER = new KalhViguthiHandler();

    @Override
    public String getName() {
        return "  கள்-விகுதி    ";
    }

    @Override
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilaic, TamilWordPartContainer varumc) {
        TamilWord nilai = nilaic.getWord();
        TamilWord varum = varumc.getWord();
        if (varum.toString().startsWith("கள்")) {
            if (nilai.endsWith(TamilCompoundCharacter.ING)) {

                nilai.removeLast();
                nilai.add(TamilCompoundCharacter.IM);
                List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();
                TamilWordSplitResult split = new TamilWordSplitResult();
                split.add(new TamilWordPartContainer(nilai));
                split.add(new TamilWordPartContainer(varum));
                list.add(split);
                return list;
            } else if (nilai.endsWith(TamilCompoundCharacter.IRR)) {
                nilai.removeLast();
                nilai.add(TamilCompoundCharacter.IL);
                List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();
                TamilWordSplitResult split = new TamilWordSplitResult();
                split.add(new TamilWordPartContainer(nilai));
                split.add(new TamilWordPartContainer(varum));
                list.add(split);
                return list;
            }   else if (nilai.endsWith(TamilCompoundCharacter.IDD)) {
                nilai.removeLast();
                nilai.add(TamilCompoundCharacter.ILL);
                List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();
                TamilWordSplitResult split = new TamilWordSplitResult();
                split.add(new TamilWordPartContainer(nilai));
                split.add(new TamilWordPartContainer(varum));
                list.add(split);
                return list;
            }

        }

        return null;


    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {

        return null;
    }
}