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
 class V3Handler extends CommonHandler {

    public static final TamilWord AAL = new TamilWord(TamilSimpleCharacter.aa, TamilCompoundCharacter.IL);
    @Override
    public TamilWord getUrubu() {
        return AAL;
    }

    @Override
    public String getName() {
        return "மூன்றாம்வேற்றுமை";
    }
}
