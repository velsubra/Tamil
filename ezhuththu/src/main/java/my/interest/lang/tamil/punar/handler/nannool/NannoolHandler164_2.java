package my.interest.lang.tamil.punar.handler.nannool;

import common.lang.impl.AbstractCharacter;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;
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
 * சான்று:
 * <p/>
 * நாடு + யாது = நாடியாது
 * குரங்கு + யாது = குரங்கியாது
 * களிற்று + யானை = களிற்றியானை
 * <p/>
 * இங்கே காட்டிய சான்றுகளில், நிலைமொழியின் ஈற்றில் உள்ள குற்றியலுகரம், வருமொழி முதலில் யகரமெய் வந்து சேரும்போது இகரமாகத் திரிந்துள்ளதைக் காணலாம். இவ்வாறு திரிந்து வரும் இகரம் குற்றியலிகரம் எனப்படும்.
 * <p/>
 * குற்றியலுகரத்திற்குக் கூறப்பட்ட இவ்விரண்டு விதிகளை முற்றியலுகரமும் சிலவிடங்களில் பெற்றுவரும்.
 * <p/>
 * <p/>
 * </p>
 *
 * @author velsubra
 */
public class NannoolHandler164_2 extends AbstractPunarchiHandler {
    @Override
    public String getName() {
        return "நன்னூல்விதி-164_2";
    }

    @Override
    public String getDescription() {
        return "நாடு + யாது = நாடியாது\n" +
                "குரங்கு + யாது = குரங்கியாது\n" +
                "களிற்று + யானை = களிற்றியானை";
    }


    @Override
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilai, TamilWordPartContainer varumpart) {

        if (!varumpart.isStartingWithOneConsonantOfType(TamilCompoundCharacter.IY)) return null;

        List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();
        if (nilai.size() < 2) return null;
        if (nilai.size() == 2) {

            if (!nilai.isStartingWithNedil()){
                return null;
            }
        }
        TamilWord nilayWord = nilai.getWord().duplicate();
        AbstractCharacter tc = nilayWord.removeLast();
        if (!tc.isPureTamilLetter()) return null;

        if (!tc.asTamilCharacter().isUyirMeyyezhuththu()) {
            return null;
        }
        if (!tc.getVowelDigest().equals(TamilSimpleCharacter.DIGEST_VOWEL._E_.toString())) {
            return null;
        }

        nilayWord.add(tc.asTamilCharacter().getMeiPart().addUyir(TamilSimpleCharacter.U));
        TamilWordSplitResult split = new TamilWordSplitResult();
        split.add(new TamilWordPartContainer(nilayWord));
        split.add(varumpart);
        list.add(split);


        return list;
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        if (!varum.isStartingWithOneConsonantOfType(TamilCompoundCharacter.IY)) return null;
        if (!varum.isStartingWithUyirMei()) return null;
        if (nilai.getWord().size() < 2) return null;

        if (nilai.isEndingWithUgaaram()) {
            if (!((TamilCharacter) nilai.getWord().getLast()).isUyirMeyyezhuththu()) {
                return null;
            }
            if (nilai.getWord().size() == 2) {
                if (!nilai.isStartingWithNedil()) {
                    return null;
                }
            }

            //
            TamilWord ret = nilai.getWord().duplicate();
            TamilCompoundCharacter ch = ((TamilCompoundCharacter) ret.removeLast()).getMeiPart();
            ret.add(ch.addUyir(TamilSimpleCharacter.E));
            ret.addAll(varum.getWord());
            return new TamilWordPartContainer(ret);
        }

        return null;
    }
}
