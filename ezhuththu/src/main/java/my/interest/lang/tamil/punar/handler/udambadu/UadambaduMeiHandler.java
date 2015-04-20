package my.interest.lang.tamil.punar.handler.udambadu;

import tamil.lang.TamilCompoundCharacter;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunarchiHandler;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class UadambaduMeiHandler extends AbstractPunarchiHandler {

    public static final UadambaduMeiHandler HANDLER = new UadambaduMeiHandler();

    @Override
    public String getName() {
        return "உடம்படுமெய்த்தோன்றல்";
    }

    static final AbstractPunarchiHandler VAGARA = new VagaraUadambaduMeiHandler();
    static final AbstractPunarchiHandler YAGARAM = new YagaraUadambaduMeiHandler();


    @Override
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilai, TamilWordPartContainer varumPart) {
        if (nilai.isKutriyaLugaram() && varumPart.isStartingWithUyirMei() && ( varumPart.isStartingWithOneConsonantOfType(TamilCompoundCharacter.IV) ||  varumPart.isStartingWithOneConsonantOfType(TamilCompoundCharacter.IY))) {
            return null;
        }
        List<TamilWordSplitResult> list = VAGARA.split(nilai, varumPart);
        if (list == null || list.isEmpty()) {
            return YAGARAM.split(nilai, varumPart);
        } else {
            return list;
        }

    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
//        if (nilai.isEndingWithUyir()) {
//            return  null;
//        }
        if (nilai.isKutriyaLugaram() && varum.isStartingWithUyir()) {
            return null;
        }
        TamilWordPartContainer ret = YAGARAM.join(nilai, varum);
        if (ret == null) {
            return VAGARA.join(nilai, varum);
        }  else {
            return  ret;
        }
    }
}