package my.interest.lang.tamil.punar.handler.verrrrumai;

import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
 class V5Handler extends CommonHandler {

    public static final TamilWord IL = new TamilWord(TamilSimpleCharacter.E, TamilCompoundCharacter.IL);
    @Override
    public TamilWord getUrubu() {
        return IL;
    }

    @Override
    public String getName() {
        return "ஐந்தாம்வேற்றுமை ";
    }
}
