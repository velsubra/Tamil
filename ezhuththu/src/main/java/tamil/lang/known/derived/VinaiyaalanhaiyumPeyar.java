package tamil.lang.known.derived;

import my.interest.lang.tamil.generated.types.PaalViguthi;
import my.interest.lang.tamil.generated.types.SimpleTense;

import my.interest.lang.tamil.punar.handler.VinaiMutruCreationHandler;
import tamil.lang.TamilFactory;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.IPeyarchchol;
import tamil.lang.known.non.derived.idai.Kalh;
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
        this(word,vinaiyadi,tense,viguthi,false);
    }
    public VinaiyaalanhaiyumPeyar(TamilWord word, Vinaiyadi vinaiyadi, SimpleTense tense, PaalViguthi viguthi, boolean derived) {
        super(word, vinaiyadi, tense, viguthi);
        if (derived) return;
        if (viguthi == PaalViguthi.AAR || viguthi == PaalViguthi.AR) {
            TamilWord withkalh = word.duplicate();
            withkalh.addAll(Kalh.KALH.getWord());
            VinaiyaalanhaiyumPeyar kalh = new VinaiyaalanhaiyumPeyar(withkalh, vinaiyadi, tense, viguthi, true);
            TamilFactory.getSystemDictionary().add(kalh);
        }

        if (PaalViguthi.THU == viguthi) {


            if (SimpleTense.FUTURE == tense) {
                //நடப்பன
                TamilWord ntadappana = word.duplicate();
                ntadappana.removeLast();
                ntadappana.add(TamilSimpleCharacter.NA);
                TamilFactory.getSystemDictionary().add(new Vinaippeyar(ntadappana, vinaiyadi));


                //படுவது   it comes without tense and person.
                ThozhirrPeyar thozhi = new ThozhirrPeyar(word, vinaiyadi);
                TamilFactory.getSystemDictionary().add(thozhi);

                //வருவதுண்டு
                for (PaalViguthi v : PaalViguthi.values()) {
                    VinaiMutruCreationHandler handler = new VinaiMutruCreationHandler();
                    handler.add(word);
                    handler.add(unhdu);
                    VinaiMuttu vm = new VinaiMuttu(handler.getVinaiMutru(), vinaiyadi, SimpleTense.PRESENT, v,true);
                    TamilFactory.getSystemDictionary().add(vm);

                    VinaiMutruCreationHandler neghandler = new VinaiMutruCreationHandler();
                    neghandler.add(word);
                    neghandler.add(illai);
                    EthirMarraiVinaiMuttu nvm = new EthirMarraiVinaiMuttu(neghandler.getVinaiMutru(), vinaiyadi, SimpleTense.FUTURE, v,true);
                    TamilFactory.getSystemDictionary().add(nvm);
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
                    TamilFactory.getSystemDictionary().add(vm);

                    VinaiMutruCreationHandler neghandler = new VinaiMutruCreationHandler();
                    neghandler.add(word);
                    neghandler.add(illai);
                    EthirMarraiVinaiMuttu nvm = new EthirMarraiVinaiMuttu(neghandler.getVinaiMutru(), vinaiyadi, SimpleTense.PRESENT, v,true);
                    nvm.addProperty("muttu", "true");
                    TamilFactory.getSystemDictionary().add(nvm);
                }
            }
        }

    }

    @Override
    public boolean isProNoun() {
        return false;
    }
}
