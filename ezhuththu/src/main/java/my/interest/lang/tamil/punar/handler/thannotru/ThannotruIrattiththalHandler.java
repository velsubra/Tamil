package my.interest.lang.tamil.punar.handler.thannotru;

import tamil.lang.TamilCharacter;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilWord;
import tamil.lang.TamilSimpleCharacter;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunharchiHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class ThannotruIrattiththalHandler extends AbstractPunharchiHandler {

    public static  final ThannotruIrattiththalHandler HANDLER = new ThannotruIrattiththalHandler();
    @Override
    public String getName() {
        return "தன்னொற்று இரட்டித்தில்";
    }

    @Override
    public List<TamilWordSplitResult> splitAll(TamilWordPartContainer word) {
        if (!word.isThanikKurilOtruJoined()) {
            return null;
        }
        TamilWord nilai = new TamilWord();
        nilai.add(word.getWord().get(0));
        nilai.add(word.getWord().get(1));
        TamilWord varum = new TamilWord();
        varum.addAll(word.getWord().subList(2, word.getWord().size()));
        TamilCharacter ch = (TamilCharacter) varum.removeFirst();
        varum.addFirst(ch.getUyirPart());
        List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();
        TamilWordSplitResult split = new TamilWordSplitResult();
        TamilWordPartContainer nilaic = new TamilWordPartContainer(nilai);
        if (!nilaic.isEndingFine()) return null;

        split.add(nilaic);
        split.add(new TamilWordPartContainer(varum));
        list.add(split);
        return list;
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        if (nilai.isThanikKurilOtru() && varum.isStartingWithUyir()) {
            TamilWord joined = new TamilWord();
            joined.add(nilai.getWord().get(0));
            joined.add(nilai.getWord().get(1));
            joined.add(((TamilCompoundCharacter) nilai.getWord().get(1)).addUyir((TamilSimpleCharacter) varum.getWord().get(0)));
            joined.addAll(varum.getWord().subList(1, varum.getWord().size()));
            return new TamilWordPartContainer(joined);

        }
        return null;
    }
}
