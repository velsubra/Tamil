package tamil.lang.known.derived;


import my.interest.lang.tamil.generated.types.PaalViguthi;
import my.interest.lang.tamil.generated.types.RootVerbDescription;
import my.interest.lang.tamil.generated.types.SimpleTense;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.punar.PropertyDescriptionContainer;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.handler.VinaiMutruCreationHandler;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.IVinaiyechcham;
import tamil.lang.known.non.derived.Vinaiyadi;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class Vinaiyechcham extends DerivativeWithTense implements IVinaiyechcham {

    static TamilWord ulh = TamilWord.from("ulh");
    static TamilWord illai = TamilWord.from("illai");

    private static final Map<PaalViguthi, TamilWord> ulhlhana = new HashMap<PaalViguthi, TamilWord>();

    private static void fillMap() {
        for (PaalViguthi v : PaalViguthi.values()) {
            VinaiMutruCreationHandler handler = new VinaiMutruCreationHandler();
            handler.add(ulh);

            if (v == PaalViguthi.THU) {
                handler.add(new TamilWord(TamilSimpleCharacter.a));
            }

            if (v == PaalViguthi.AR) {
                handler.add(new TamilWord(TamilSimpleCharacter.a, TamilCompoundCharacter.IN));
            }
            handler.add(TamilWord.from(v.value().toLowerCase()));

            if (v == PaalViguthi.A) {
                handler.add(new TamilWord(TamilSimpleCharacter.NA));
            }
            ulhlhana.put(v, handler.getVinaiMutru());
            // System.out.println("__________:" + handler.getVinaiMutru());
        }
    }

    public Vinaiyechcham(TamilWord word, Vinaiyadi vinaiyadi, SimpleTense tense) {
        this(word, vinaiyadi, tense, false);
    }


    public Vinaiyechcham(TamilWord word, Vinaiyadi vinaiyadi, SimpleTense tense, boolean derived) {
        super(word, vinaiyadi, tense);

        if (!derived) {

            if (tense == SimpleTense.PRESENT) {
                //viyangolh

                if (word.endsWith(TamilSimpleCharacter.KA) && !new TamilWordPartContainer(vinaiyadi.getWord()).isUkkurralh()) {
                    Viyangoalh v = new Viyangoalh(word, vinaiyadi);
                    PersistenceInterface.addKnown(v);
                } else {
                    TamilWord viyangoalh = vinaiyadi.getWord().duplicate();
                    viyangoalh.add(TamilSimpleCharacter.KA);
                    Viyangoalh v = new Viyangoalh(viyangoalh, vinaiyadi);
                    PersistenceInterface.addKnown(v);
                }

            }

            if (ulhlhana.isEmpty()) {
                fillMap();
                RootVerbDescription iru = PersistenceInterface.get().findRootVerbDescription("இரு");
                if (iru == null) {
                    throw new RuntimeException("Verb இரு not found");
                }
                PropertyDescriptionContainer container = new PropertyDescriptionContainer(iru);
                for (PaalViguthi v : PaalViguthi.values()) {
                    VinaiMuttu vm = new VinaiMuttu(ulhlhana.get(v), new Vinaiyadi(TamilWord.from("இரு"), container, false), SimpleTense.PRESENT, v, true);
                    PersistenceInterface.addKnown(vm);
                }

            }
            if (tense == SimpleTense.FUTURE) {
                throw new RuntimeException("Cannot be in future");
            }


            for (PaalViguthi v : PaalViguthi.values()) {


                VinaiMutruCreationHandler handler = new VinaiMutruCreationHandler();
                handler.add(word);
                handler.add(ulhlhana.get(v));
                //வரவுள்ளது
                VinaiMuttu vm = new VinaiMuttu(handler.getVinaiMutru(), vinaiyadi, tense == SimpleTense.PRESENT ? SimpleTense.FUTURE : SimpleTense.PAST, v, true);
                if (tense == SimpleTense.PAST) {
                    vm.addProperty("muttu", "true");
                }
                PersistenceInterface.addKnown(vm);


                if (tense == SimpleTense.PRESENT) {
                    VinaiMutruCreationHandler neghandler = new VinaiMutruCreationHandler();
                    neghandler.add(word);
                    neghandler.add(illai);
                    EthirMarraiVinaiMuttu nvm = new EthirMarraiVinaiMuttu(neghandler.getVinaiMutru(), vinaiyadi, SimpleTense.PAST, v, true);
                    PersistenceInterface.addKnown(nvm);
                }


            }

            if (tense == SimpleTense.PAST) {
                //வந்திற்று
                TamilWord vm = null;
                TamilCharacter last = word.getLast().asTamilCharacter();
                if (last.isUyirMeyyezhuththu() && last.getUyirPart().equals(TamilSimpleCharacter.E)) {
                    vm = word.duplicate();
                    vm.add(TamilCompoundCharacter.IRR);
                    vm.add(TamilCompoundCharacter.IRR_U);
                } else {
                    VinaiMutruCreationHandler handler = new VinaiMutruCreationHandler();
                    TamilWord ITTU = new TamilWord(TamilSimpleCharacter.E, TamilCompoundCharacter.IRR, TamilCompoundCharacter.IRR_U);
                    handler.add(word);
                    handler.add(ITTU);
                    vm = handler.getVinaiMutru();
                }
                PersistenceInterface.addKnown(new VinaiMuttu(vm, vinaiyadi, SimpleTense.PAST, PaalViguthi.THU, true));
                PersistenceInterface.addKnown(new VinaiMuttu(vm, vinaiyadi, SimpleTense.PAST, PaalViguthi.A, true));
            }
        }
    }

}