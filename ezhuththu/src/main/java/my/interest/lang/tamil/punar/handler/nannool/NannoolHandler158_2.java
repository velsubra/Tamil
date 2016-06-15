package my.interest.lang.tamil.punar.handler.nannool;

import tamil.lang.TamilCharacter;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunharchiHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * எண்மூ எழுத்து ஈற்று எவ்வகை மொழிக்கும்
 * முன்வரு ஞநமய வக்கள் இயல்பும்;
 * குறில்வழி யத்தனி ஐந்நொது முன்மெலி
 * மிகலுமாம்; ணளனல வழிநத் திரியும். - (நன்னூல்,158)
 * மெய் + ஞான்றது = மெய்ஞ்ஞான்றது
 * மெய் + நீண்டது = மெய்ந்நீண்டது
 * மெய் + மாண்டது = மெய்ம்மாண்டது
 * <p/>
 * கை + ஞான்றது = கைஞ்ஞான்றது
 * கை + நீண்டது = கைந்நீண்டது
 * கை + மாண்டது = கைம்மாண்டது
 * http://www.tamilvu.org/courses/degree/c021/c0213/html/c0213222.htm
 * <p/>
 * <p/>
 * </p>
 *
 * @author velsubra
 */
public class NannoolHandler158_2 extends AbstractPunharchiHandler {
    @Override
    public String getName() {
        return "நன்னூல்விதி-158_2";
    }

    @Override
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilaiPart, TamilWordPartContainer varum) {
        List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();

        if (!nilaiPart.isNilaiWithNannool158_2()) {
            return null;
        }
        if (varum.size() < 2) {
            return null;
        }

        if (!varum.isStartingWithTwoConsonantsOfTypeNannool158_2()) {
             return null;
        }

        TamilCompoundCharacter ch = ((TamilCharacter) varum.getWord().get(0)).getMeiPart();

        TamilCompoundCharacter chnext = ((TamilCharacter) varum.getWord().get(1)).getMeiPart();
        if (!ch.toString().equals(chnext.toString())) return null;
        TamilWord varumWithoutMei = varum.getWord().duplicate();
        varumWithoutMei.removeFirst();


        TamilWordSplitResult split = new TamilWordSplitResult();
        split.add(nilaiPart);
        split.add(new TamilWordPartContainer(varumWithoutMei));

        list.add(split);


        return list;
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        if (!nilai.isNilaiWithNannool158_2()) return null;
        if (!varum.isStartingWithNannool158_2()) {
            return null;
        }
        TamilWord ret = nilai.getWord().duplicate();
        TamilCompoundCharacter ch = (TamilCompoundCharacter) varum.getWord().get(0);
        ret.add(ch.getMeiPart());
        ret.addAll(varum.getWord());
        return new TamilWordPartContainer(ret);
    }
}