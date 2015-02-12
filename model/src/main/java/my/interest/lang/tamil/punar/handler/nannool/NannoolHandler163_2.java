package my.interest.lang.tamil.punar.handler.nannool;

import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunarchiHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * http://www.tamilvu.org/courses/degree/c021/c0213/html/c0213332.htm
 * எகர வினா, முச்சுட்டுகளின் முன்னர் உயிரும் யகரமும் நீங்கிய பிற வல்லின, மெல்லின, இடையின மெய் எழுத்துகள் வரும்போது அவற்றிற்கு இடையில், வருகின்ற அந்தந்த மெய் எழுத்துகளே மிகும்.
 * <p/>
 * சான்று:
 * <p/>
 * வல்லினம்
 * எ + குதிரை	= எக்குதிரை
 * அ + குதிரை	= அக்குதிரை
 * இ + குதிரை	= இக்குதிரை
 * உ + குதிரை	= உக்குதிரை
 * மெல்லினம்
 * எ + நாள்	= எந்நாள்
 * அ + நாள்	= அந்நாள்
 * இ + நாள்	= இந்நாள்
 * உ + நாள்	= உந்நாள்
 * இடையினம்
 * எ + விதம்	= எவ்விதம்
 * அ + விதம்	= அவ்விதம்
 * இ + விதம்	= இவ்விதம்
 * உ + விதம்	= உவ்விதம்
 * </p>
 *
 * @author velsubra
 */
public class NannoolHandler163_2 extends AbstractPunarchiHandler {
    @Override
    public String getName() {
        return "நன்னூல்விதி-163_2";
    }


    @Override
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilaiPart, TamilWordPartContainer varumPart) {
        List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();

        if (!nilaiPart.isOneLetterChuttuUyir()) return null;



        if (!varumPart.isStartingWithOtruAndFowlloedBySameInam()) {
            return null;
        }
        if (!varumPart.isStartingWithMei()) return null;

        if (!varumPart.getWord().get(1).asTamilCharacter().isUyirMeyyezhuththu()) return null;
        if (varumPart.getWord().get(1).asTamilCharacter().getMeiPart().isYa()) return null;
        TamilWord varumWord = new TamilWord();

        varumWord.addAll(varumPart.getWord().subList(1, varumPart.size()));
        TamilWordSplitResult split1 = new TamilWordSplitResult();
        split1.add(nilaiPart);
//        TamilWord mei = new TamilWord();
//        mei.appendNodesToAllPaths(varumPart.getWord().getFirst());
//        split1.appendNodesToAllPaths(new TamilWordPartContainer(mei));
        split1.add(new TamilWordPartContainer(varumWord));
        list.add(split1);


        return list;
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        if (!nilai.isOneLetterChuttuUyir()) return null;
        if (!varum.getWord().get(0).isPureTamilLetter()) return null;
        if (varum.getWord().get(0).asTamilCharacter().isUyirezhuththu() || varum.isStartingWithOneConsonantOfType(TamilCompoundCharacter.IY))
            return null;
        if (varum.getWord().get(0).asTamilCharacter().isAaythavezhuththu()) return null;

        TamilWord ret = nilai.getWord().duplicate();
        ret.add(varum.getWord().get(0).asTamilCharacter().getMeiPart());
        ret.addAll(varum.getWord());
        return new TamilWordPartContainer(ret);
    }
}