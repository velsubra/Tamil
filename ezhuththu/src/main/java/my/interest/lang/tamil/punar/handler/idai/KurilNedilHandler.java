package my.interest.lang.tamil.punar.handler.idai;

import common.lang.impl.AbstractCharacter;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunharchiHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class KurilNedilHandler extends AbstractPunharchiHandler {

    public static final KurilNedilHandler HANDLER = new KurilNedilHandler();

    @Override
    public String getName() {
        return "நெடிலுயிர்வரின் குறிலுயிர்  மெய்விட்டோடும் (போன + ஆல் = போனால்)";
    }

    @Override
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilaic, TamilWordPartContainer varumc) {
        TamilWord nilai = nilaic.getWord();
        TamilWord varum = varumc.getWord();
        List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();
        if (nilai.size() < 2) return null;
        if (nilai.size() == 1) {

            if (!nilai.get(0).getSoundSizeDigest().equals(TamilSimpleCharacter.DIGEST_SOUND_SIZE._T_.toString())) {
                return null;
            }
        }

        AbstractCharacter tc = nilai.removeLast();
        if (!tc.isPureTamilLetter()) return null;
        if (!tc.asTamilCharacter().isUyirMeyyezhuththu()) {
            return null;
        }
        if (!tc.asTamilCharacter().getUyirPart().isaa()) {
            return null;
        }


        nilai.add(tc.asTamilCharacter().getMeiPart().addUyir(TamilSimpleCharacter.a));
        varum.add(0, tc.asTamilCharacter().getUyirPart());
        TamilWordSplitResult split = new TamilWordSplitResult();
        split.add(new TamilWordPartContainer(nilai));
        split.add(new TamilWordPartContainer(varum));
        list.add(split);


        return list;
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {

        return null;
    }
}