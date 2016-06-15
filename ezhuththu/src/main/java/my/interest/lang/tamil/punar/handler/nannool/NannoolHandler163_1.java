package my.interest.lang.tamil.punar.handler.nannool;

import tamil.lang.TamilWord;
import tamil.lang.TamilCompoundCharacter;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunharchiHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 1. எ என்னும் வினா எழுத்தின் முன்னரும், அ,இ,உ என்னும் மூன்று சுட்டு எழுத்துகளின் முன்னரும் உயிர் எழுத்துகளும், யகர மெய்யும் வந்தால் அவற்றிற்கு இடையில் வகரமெய் தோன்றும்.
 * <p/>
 * சான்று:
 * <p/>
 * எ + அளவு > எ + வ் + அளவு = எவ்வளவு
 * அ + இடம் > அ + வ் + இடம் = அவ்விடம்
 * இ + உலகம் > இ + வ் + உலகம் = இவ்வுலகம்
 * உ + இடம் > உ + வ் + இடம் = உவ்விடம்
 * <p/>
 * எ + யானை > எ + வ் + யானை = எவ்யானை
 * அ + யாழ் > அ + வ் + யாழ் = அவ்யாழ்
 * இ + யாழ் > இ + வ் + யாழ் = இவ்யாழ்
 * உ + யானை > உ + வ் + யானை = உவ்யானை
 * <p/>
 * இச்சான்றுகளில் எகர வினா முச்சுட்டுகளின் முன்னர் உயிரும், யகரமும் வர, அவற்றிற்கு இடையில் வகர மெய் தோன்றியதைக் காணலாம்.
 * </p>
 *
 * @author velsubra
 */
public class NannoolHandler163_1 extends AbstractPunharchiHandler {
    @Override
    public String getName() {
        return "நன்னூல்விதி-163_1";
    }


    @Override
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilaiPart, TamilWordPartContainer varumPart) {
        List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();



        if (!varumPart.isStartingWithTwoConsonantsOfType(TamilCompoundCharacter.IV) && !varumPart.isStartingWithTwoConsonantsOfType(TamilCompoundCharacter.IV, TamilCompoundCharacter.IY)) {
            return null;
        }
        if (!varumPart.getWord().get(1).asTamilCharacter().isUyirMeyyezhuththu()) return null;


        if (!nilaiPart.isOneLetterChuttuUyir()) return null;


        TamilWord varumWord = new TamilWord();

        if (!varumPart.isStartingWithTwoConsonantsOfType(TamilCompoundCharacter.IV)) {
            //Yagram as it is
            varumWord.add(varumPart.getWord().get(1));
        } else {
            varumWord.add(varumPart.getWord().get(1).asTamilCharacter().getUyirPart());
        }


        varumWord.addAll(varumPart.getWord().subList(2, varumPart.size()));

        TamilWordSplitResult split1 = new TamilWordSplitResult();
        split1.add(new TamilWordPartContainer(nilaiPart.getWord()));
        split1.add(new TamilWordPartContainer(varumWord));
        list.add(split1);


        return list;
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        if (!nilai.isOneLetterChuttuUyir()) return null;
        if (!varum.getWord().get(0).isPureTamilLetter()) return null;
        if (!varum.getWord().get(0).asTamilCharacter().isUyirezhuththu() && !varum.isStartingWithOneConsonantOfType(TamilCompoundCharacter.IY))
            return null;

        TamilWord ret = nilai.getWord().duplicate();
        ret.add(TamilCompoundCharacter.IV);
        if (varum.isStartingWithOneConsonantOfType(TamilCompoundCharacter.IY)) {
            ret.add(varum.getWord().get(0));
        } else {
            ret.add(TamilCompoundCharacter.IV.addUyir(varum.getWord().get(0).asTamilCharacter().getUyirPart()));
        }
        ret.addAll(varum.getWord().subList(1, varum.getWord().size()));
        return new TamilWordPartContainer(ret);
    }
}