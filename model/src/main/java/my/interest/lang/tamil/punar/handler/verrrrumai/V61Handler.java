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
class V61Handler  extends CommonHandler {

    public static final TamilWord IN = new TamilWord(TamilSimpleCharacter.E, TamilCompoundCharacter.IN);

    @Override
    public TamilWord getUrubu() {
        return IN;
    }

    @Override
    public String getName() {
        return "ஆறாம்வேற்றுமை ";
    }

}