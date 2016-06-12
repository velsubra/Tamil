package tamil.lang.known.non.derived.idai;

import my.interest.lang.tamil.generated.types.PaalViguthi;
import tamil.lang.TamilWord;
import tamil.lang.known.derived.HavingPaal;
import tamil.lang.known.non.derived.NonStartingIdaichchol;

/**
 * Created by velsubra on 1/18/16.
 *
 * E.gஉறவுக்காரன்
 */
public final class Kaaran extends NonStartingIdaichchol implements HavingPaal {
    public Kaaran() {
        super(TamilWord.from("காரன்"), false);
    }

    public PaalViguthi getPaalViguthi() {
        return PaalViguthi.AAN;
    }

    public boolean isUyarThinhai() {
        return true;
    }
}
