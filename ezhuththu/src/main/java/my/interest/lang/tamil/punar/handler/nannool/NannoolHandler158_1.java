package my.interest.lang.tamil.punar.handler.nannool;

import tamil.lang.TamilWord;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunarchiHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * எண்மூ எழுத்து ஈற்று எவ்வகை மொழிக்கும்
 * முன்வரு ஞநமய வக்கள் இயல்பும்;
 * குறில்வழி யத்தனி ஐந்நொது முன்மெலி
 * மிகலுமாம்; ணளனல வழிநத் திரியும். - (நன்னூல்,158)
 * <p/>
 * <p/>
 * <p>
 * இள + ஞாயிறு = இளஞாயிறு
 * (அ முன் ஞகரமெய் இயல்பாக வந்தது)
 * <p/>
 * தென்னை + நீண்டது = தென்னை நீண்டது
 * (ஐ முன் நகரமெய் இயல்பாக வந்தது)
 * <p/>
 * பூ + மலர்ந்தது = பூ மலர்ந்தது
 * (ஊ முன் மகரமெய் இயல்பாக வந்தது)
 * <p/>
 * மரம் + வளர்ந்தது = மரம் வளர்ந்தது
 * (மகரமெய் முன் வகரமெய் இயல்பாக வந்தது)
 * <p/>
 * எஃகு + யாது = எஃகு யாது
 * </p>
 *
 * @author velsubra
 */
public class NannoolHandler158_1 extends AbstractPunarchiHandler {
    @Override
    public String getName() {
        return "நன்னூல்விதி-158_1";
    }

    @Override
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilaipart, TamilWordPartContainer varumpart) {
        List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();


        if (!nilaipart.isEndingFine()) {
            return null;
        }
        if (!varumpart.isStartingWithNannool158_1()) {
            return null;
        }
        if (!varumpart.isStartingFine()) {
            return null;
        }

        TamilWordSplitResult split = new TamilWordSplitResult();
        split.add(nilaipart);
        split.add(varumpart);

        list.add(split);


        return list;
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        if (!nilai.isEndingFine()) return null;
        if (!varum.isStartingWithNannool158_1()) {
            return null;
        }
        TamilWord ret = nilai.getWord().duplicate();
        ret.addAll(varum.getWord());
        return new TamilWordPartContainer(ret);
    }
}
