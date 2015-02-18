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
public class V32Handler extends CommonHandler {

    public static final TamilWord udan = new TamilWord(TamilSimpleCharacter.U,TamilSimpleCharacter.DA, TamilCompoundCharacter.IN);
    @Override
    public TamilWord getUrubu() {
        return udan;
    }

    @Override
    public int getNumber() {
        return 3;
    }

    @Override
    public String getName() {
        return "மூன்றாம்வேற்றுமை";
    }
}
