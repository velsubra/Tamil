package my.interest.lang.tamil.punar.handler.verrrrumai;

import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
 class V2Handler extends CommonHandler {

    public static final TamilWord AI = new TamilWord(TamilSimpleCharacter.I);
    @Override
    public TamilWord getUrubu() {
        return AI;
    }

    @Override
    public int getNumber() {
        return 2;
    }

    @Override
    public String getName() {
        return "இரண்டாம்வேற்றுமை";
    }
}
