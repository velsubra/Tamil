package tamil.lang.known.non.derived.idai;

import my.interest.lang.tamil.generated.types.PaalViguthi;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.join.WordsJoiner;
import tamil.lang.known.derived.HavingPaal;
import tamil.lang.known.non.derived.IEthirmarrai;
import tamil.lang.known.non.derived.NonStartingIdaichchol;

/**
 * Created by velsubra on 1/18/16.
 */
public final class Maaddu  extends NonStartingIdaichchol implements HavingPaal, IEthirmarrai {

    public static final TamilWord MAADDU = TamilWord.from("மாட்டு");
    PaalViguthi viguthi = null;

    public Maaddu( PaalViguthi viguthi) {
        super(append(viguthi),false);
        this.viguthi = viguthi;
    }

    public PaalViguthi getPaalViguthi() {
        return viguthi;
    }

    public boolean isUyarThinhai() {
        return true;
    }

    private static TamilWord append(PaalViguthi viguthi) {
        TamilWord vig = TamilWord.from(viguthi.value());
        WordsJoiner joiner = TamilFactory.createWordJoiner(MAADDU);
        joiner.addVaruMozhi(vig);
        return joiner.getSum();
    }
}