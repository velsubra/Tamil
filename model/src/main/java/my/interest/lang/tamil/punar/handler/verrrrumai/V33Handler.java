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
public class V33Handler extends CommonHandler {

    public static final TamilWord odu = new TamilWord(TamilSimpleCharacter.O, TamilCompoundCharacter.IDD_U);

    @Override
    public TamilWord getUrubu() {
        return odu;
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
