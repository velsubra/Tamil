package my.interest.lang.tamil.punar.handler.verrrrumai;

import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.handler.iyalbu.IyalbuPunarchiHandler;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
 class V6Handler extends CommonHandler {

    public static final TamilWord ATHU = new TamilWord(TamilSimpleCharacter.a, TamilCompoundCharacter.ITH_U);

    @Override
    public TamilWord getUrubu() {
        return ATHU;
    }

    @Override
    public String getName() {
        return "ஆறாம்வேற்றுமை ";
    }

    @Override
    public TamilWordPartContainer translateForProNoun(TamilWordPartContainer nilai) {
        return IyalbuPunarchiHandler.HANDLER.join(nilai, new TamilWordPartContainer(new TamilWord(TamilSimpleCharacter.a)));
    }

}
