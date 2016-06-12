package tamil.lang.known.non.derived.idai;

import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.generated.types.PaalViguthi;
import tamil.lang.TamilWord;
import tamil.lang.known.derived.HavingPaal;
import tamil.lang.known.non.derived.NonStartingIdaichchol;

/**
 * Created by velsubra on 1/18/16.
 * E,g)
 * உள்ளது
 மாட்டேன்
 */
class PaalPinnoddu extends NonStartingIdaichchol implements HavingPaal{

    PaalViguthi viguthi = null;

     PaalPinnoddu(TamilWord word) {
        super(word, false);
    }

    public PaalViguthi getPaalViguthi() {
        return viguthi;
    }

    public boolean isUyarThinhai() {
        return EzhuththuUtils.isUyarThinhai(viguthi);
    }
}
