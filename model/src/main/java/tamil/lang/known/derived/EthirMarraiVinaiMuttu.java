package tamil.lang.known.derived;

import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.generated.types.PaalViguthi;
import my.interest.lang.tamil.generated.types.SimpleTense;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.IEthirmarrai;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class EthirMarraiVinaiMuttu extends VinaiMuttuBase implements IEthirmarrai {
    public EthirMarraiVinaiMuttu(TamilWord word, Vinaiyadi vinaiyadi, SimpleTense tense, PaalViguthi viguthi) {
        this(word,vinaiyadi,tense,viguthi,false);
    }

    public EthirMarraiVinaiMuttu(TamilWord word, Vinaiyadi vinaiyadi, SimpleTense tense, PaalViguthi viguthi, boolean implicit) {
        super(word, vinaiyadi, tense, viguthi, implicit);


        if (!implicit && TamilUtils.isVinaiMuttuAsNoun(tense, viguthi)) {
            EthirmarraiVinaiyaalanhaiyumPeyar vp = new EthirmarraiVinaiyaalanhaiyumPeyar(word,vinaiyadi, viguthi);
            PersistenceInterface.addKnown(vp);
        }


    }
}
