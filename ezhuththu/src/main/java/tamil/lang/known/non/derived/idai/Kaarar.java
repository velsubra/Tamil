package tamil.lang.known.non.derived.idai;

import my.interest.lang.tamil.generated.types.PaalViguthi;
import tamil.lang.TamilWord;
import tamil.lang.known.derived.HavingPaal;
import tamil.lang.known.non.derived.NonStartingIdaichchol;

/**
 * Created by velsubra on 1/18/16.
 * உறவுக்காரர்.
 */
public final class Kaarar  extends NonStartingIdaichchol implements HavingPaal {
    public Kaarar() {
        super(TamilWord.from("காரர்"), false);
    }

    public PaalViguthi getPaalViguthi() {
        return PaalViguthi.AAR;
    }

    public boolean isUyarThinhai() {
        return true;
    }
}
