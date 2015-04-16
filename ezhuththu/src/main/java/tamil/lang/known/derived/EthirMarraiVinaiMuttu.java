package tamil.lang.known.derived;

import my.interest.lang.tamil.EzhuththuUtils;

import my.interest.lang.tamil.generated.types.PaalViguthi;
import my.interest.lang.tamil.generated.types.SimpleTense;

import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.IEthirmarrai;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 *     Represents எதிர்மறைவினைமுற்று எ.கா) வரவில்லை.
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


        if (!implicit && EzhuththuUtils.isVinaiMuttuAsNoun(tense, viguthi)) {
            EthirmarraiVinaiyaalanhaiyumPeyar vp = new EthirmarraiVinaiyaalanhaiyumPeyar(word,vinaiyadi, viguthi);
            TamilFactory.getSystemDictionary().add(vp);
        }


    }
}
