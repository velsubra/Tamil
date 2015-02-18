package my.interest.lang.tamil.punar.handler.uyirvarin;

import common.lang.impl.AbstractCharacter;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilWord;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilCharacter;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunarchiHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * நூற்பா:
 * <p/>
 * உயிர்வரின் உக்குறள் மெய்விட் டோடும்;
 * யவ்வரின் இய்யாம் முற்றும்அற் றொரோவழி. (நன்னூல் - 164)
 * <p/>
 * <p/>
 * </p>
 *
 * @author velsubra
 */
public class UyirvarinUkkuralMeiVittodumHandler extends AbstractPunarchiHandler {

    public  static  final  UyirvarinUkkuralMeiVittodumHandler  HANDLER = new UyirvarinUkkuralMeiVittodumHandler();
    @Override
    public String getName() {
        return "உயிர்வரின் உக்குறள் மெய்விட்டோடும்  ";
    }

    @Override
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilaic, TamilWordPartContainer varumc) {
        TamilWord nilai = nilaic.getWord();
        TamilWord varum = varumc.getWord();
        List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();

        if (nilai.size() == 1) {

            if (!nilai.get(0).getSoundSizeDigest().equals(TamilSimpleCharacter.DIGEST_SOUND_SIZE._T_.toString())) {
                return null;
            }
        }
        if (varumc.size() == 1) {
            if (varumc.isStartingWithUgaaram()) {
                return null;
            }
        }
        AbstractCharacter tc = varum.removeFirst();
        if (!tc.isPureTamilLetter()) return null;
        if (!tc.asTamilCharacter().isUyirMeyyezhuththu()) {
            return null;
        }


        nilai.add(tc.asTamilCharacter().getMeiPart().addUyir(TamilSimpleCharacter.U));
        varum.add(0, tc.asTamilCharacter().getUyirPart());

        TamilWordSplitResult split = new TamilWordSplitResult();
        split.add(new TamilWordPartContainer(nilai));
        split.add(new TamilWordPartContainer(varum));
        list.add(split);


        return list;
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        if (!varum.isStartingWithUyir()) return null;
        if (nilai.getWord().size() < 2) return null;

        if (nilai.isEndingWithUgaaram()) {
            if (!((TamilCharacter) nilai.getWord().getLast()).isUyirMeyyezhuththu()) {
                return null;
            }
            if (nilai.getWord().size() == 2) {
                if (!nilai.getWord().get(0).getSoundSizeDigest().equals(TamilSimpleCharacter.DIGEST_SOUND_SIZE._T_.toString())) {
                    return null;
                }
            }


            //
            TamilWord ret = nilai.getWord().duplicate();
            TamilCompoundCharacter ch = ((TamilCompoundCharacter) ret.removeLast()).getMeiPart();
            ret.add(ch.addUyir((TamilSimpleCharacter) varum.getWord().get(0)));
            ret.addAll(varum.getWord().subList(1, varum.getWord().size()));
            return new TamilWordPartContainer(ret);
        }

        return null;
    }
}
