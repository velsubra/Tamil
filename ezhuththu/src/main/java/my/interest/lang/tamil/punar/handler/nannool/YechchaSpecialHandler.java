package my.interest.lang.tamil.punar.handler.nannool;

import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunarchiHandler;
import my.interest.lang.tamil.punar.handler.iyalbu.IyalbuPunarchiHandler;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class YechchaSpecialHandler extends AbstractPunarchiHandler {
    @Override
    public String getName() {
        return "Special விதி.";
    }

    public static final YechchaSpecialHandler HANDLER = new YechchaSpecialHandler();

    @Override
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilaipart, TamilWordPartContainer varumpart) {
        return null;
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {

        if (nilai.size() == 2 && nilai.isStartingWithKuril() && nilai.isEndingWithUyirMei() && nilai.isEndingWithUgaaram()) {
                //நடு + அ = நட
                if (varum.isStartingWithUyir() && varum.size() ==1) {
                    TamilWord b = nilai.getWord().subWord(0, nilai.size() - 1);
                    b.add(nilai.getWord().getLast().asTamilCharacter().getMeiPart().addUyir((TamilSimpleCharacter)varum.getWord().get(0).asTamilCharacter()));

                    return new TamilWordPartContainer(b);
                }

        }

        if (nilai.getWord().toString().equals("என்") && varum.size() == 1 && (varum.getWord().get(0) == TamilSimpleCharacter.aa || varum.getWord().get(0) == TamilSimpleCharacter.a)) {
            //என் + ஆ + த = எனாத
           // எய்ய (எய்+அ)
            return IyalbuPunarchiHandler.HANDLER.join(nilai, varum);

        }
        return null;
    }
}