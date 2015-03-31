package tamil.lang.known.derived;

import my.interest.lang.tamil.generated.types.PaalViguthi;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.punar.handler.VinaiMutruCreationHandler;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.IPeyarechcham;
import tamil.lang.known.non.derived.Peyarchchol;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * குறிப்புப்பெயரெச்சம் எ.கா) நல்ல  [பையன்]
 * </p>
 *
 * @author velsubra
 */
public final class KurrippupPeyarechcham extends PeyarchcholDerivative implements IPeyarechcham {
    private static final Map<PaalViguthi, TamilWord[]> viguthis = new HashMap<PaalViguthi, TamilWord[]>();

    private static void fillMap() {
        for (PaalViguthi v : PaalViguthi.values()) {

            if (v == PaalViguthi.A) {
                viguthis.put(v, new TamilWord[]{new TamilWord(TamilSimpleCharacter.NA), new TamilWord(TamilCompoundCharacter.IV_I)});
            } else if (v == PaalViguthi.AALH) {
                viguthis.put(v, new TamilWord[]{TamilWord.from("அள்")});
            } else if (v == PaalViguthi.AAN) {
                viguthis.put(v, new TamilWord[]{TamilWord.from("அன்")});
            } else if (v == PaalViguthi.AR) {
                viguthis.put(v, new TamilWord[]{TamilWord.from("அர்")});
            } else if (v == PaalViguthi.THU) {
                viguthis.put(v, new TamilWord[]{TamilWord.from("து")});
            }

        }
    }

    public KurrippupPeyarechcham(TamilWord word, Peyarchchol peyar) {
        super(word, peyar);
        if (word.endsWith(TamilSimpleCharacter.a, false)) {
            if (viguthis.isEmpty()) {
                fillMap();
            }
            for (PaalViguthi v : PaalViguthi.values()) {
                TamilWord[] vigs = viguthis.get(v);
                if (vigs == null) {
                    continue;
                }
                for (TamilWord vi : vigs) {
                    TamilWord kpeyar1 = word.duplicate();
                    VinaiMutruCreationHandler handler = new VinaiMutruCreationHandler();
                    handler.add(kpeyar1);
                    handler.add(vi);
                    PersistenceInterface.addKnown(new KurrippupPeyarechchappeyar(handler.getVinaiMutru(), peyar, v));
                }
            }
        }


    }
}
