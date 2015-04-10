package tamil.lang.known.derived;

import my.interest.lang.tamil.generated.types.PaalViguthi;
import my.interest.lang.tamil.generated.types.SimpleTense;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.punar.handler.VinaiMutruCreationHandler;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.IPeyarchchol;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 * வினையாலணையும்பெயர் எ.கா) வந்தவன் , வந்தது
 * </p>
 *
 * @author velsubra
 */
public final class VinaiyaalanhaiyumPeyar extends DerivativeWithTenseAndPaal implements IPeyarchchol {

    static TamilWord illai = TamilWord.from("illai");
    static TamilWord unhdu = TamilWord.from("unhdu");
    public VinaiyaalanhaiyumPeyar(TamilWord word, Vinaiyadi vinaiyadi, SimpleTense tense, PaalViguthi viguthi) {
        super(word, vinaiyadi, tense, viguthi);

        if (PaalViguthi.THU == viguthi) {


            if (SimpleTense.FUTURE == tense) {
                //நடப்பன
                TamilWord ntadappana = word.duplicate();
                ntadappana.removeLast();
                ntadappana.add(TamilSimpleCharacter.NA);
                PersistenceInterface.addKnown(new Vinaippeyar(ntadappana,vinaiyadi));


                //படுவது   it comes without tense and person.
                ThozhirrPeyar thozhi = new ThozhirrPeyar(word, vinaiyadi);
                PersistenceInterface.addKnown(thozhi);

                //வருவதுண்டு
                for (PaalViguthi v : PaalViguthi.values()) {
                    VinaiMutruCreationHandler handler = new VinaiMutruCreationHandler();
                    handler.add(word);
                    handler.add(unhdu);
                    VinaiMuttu vm = new VinaiMuttu(handler.getVinaiMutru(), vinaiyadi, SimpleTense.PRESENT, v,true);
                    PersistenceInterface.addKnown(vm);

                    VinaiMutruCreationHandler neghandler = new VinaiMutruCreationHandler();
                    neghandler.add(word);
                    neghandler.add(illai);
                    EthirMarraiVinaiMuttu nvm = new EthirMarraiVinaiMuttu(neghandler.getVinaiMutru(), vinaiyadi, SimpleTense.FUTURE, v,true);
                    PersistenceInterface.addKnown(nvm);
                }

            }
             //வந்ததில்லை
            if (SimpleTense.PAST == tense) {
                for (PaalViguthi v : PaalViguthi.values()) {
                    VinaiMutruCreationHandler handler = new VinaiMutruCreationHandler();
                    handler.add(word);
                    handler.add(unhdu);
                    //வந்ததுண்டு
                    VinaiMuttu vm = new VinaiMuttu(handler.getVinaiMutru(), vinaiyadi, SimpleTense.PRESENT, v,true);
                    vm.addProperty("muttu", "true");
                    PersistenceInterface.addKnown(vm);

                    VinaiMutruCreationHandler neghandler = new VinaiMutruCreationHandler();
                    neghandler.add(word);
                    neghandler.add(illai);
                    EthirMarraiVinaiMuttu nvm = new EthirMarraiVinaiMuttu(neghandler.getVinaiMutru(), vinaiyadi, SimpleTense.PRESENT, v,true);
                    nvm.addProperty("muttu", "true");
                    PersistenceInterface.addKnown(nvm);
                }
            }
        }

    }

    @Override
    public boolean isProNoun() {
        return false;
    }
}
