package tamil.lang.known.derived;

import my.interest.lang.tamil.generated.types.PaalViguthi;
import my.interest.lang.tamil.generated.types.SimpleTense;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.punar.handler.VinaiMutruCreationHandler;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.IVinaiyechcham;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class Vinaiyechcham extends DerivativeWithTense implements IVinaiyechcham {


    public Vinaiyechcham(TamilWord word, Vinaiyadi vinaiyadi, SimpleTense tense) {
        super(word, vinaiyadi, tense);

        //வந்திற்று
        TamilWord vm = null;
        TamilCharacter last = word.getLast().asTamilCharacter();
        if (last.isUyirMeyyezhuththu() && last.getUyirPart().equals(TamilSimpleCharacter.E)) {
            vm = word.duplicate();
            vm.add(TamilCompoundCharacter.IRR);
            vm.add(TamilCompoundCharacter.IRR_U);
        } else {
            VinaiMutruCreationHandler handler = new VinaiMutruCreationHandler();
            TamilWord ITTU = new TamilWord(TamilSimpleCharacter.E, TamilCompoundCharacter.IRR,TamilCompoundCharacter.IRR_U);
            handler.add(word);
            handler.add(ITTU);
            vm = handler.getVinaiMutru();
        }
        PersistenceInterface.addKnown(new VinaiMuttu(vm,vinaiyadi,SimpleTense.PAST, PaalViguthi.THU));
        PersistenceInterface.addKnown(new VinaiMuttu(vm,vinaiyadi,SimpleTense.PAST, PaalViguthi.A));
    }

}