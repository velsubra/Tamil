package my.interest.lang.tamil.punar.handler.nannool;

import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunarchiHandler;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.join.WordsJoiner;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.IBaseVinai;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class NannoolHandler183 extends AbstractPunarchiHandler {

    static final TamilWord ntoorru = TamilWord.from("நூறு");
    static final TamilWord koadi = TamilWord.from("கோடி");

    static final TamilWord aayira = TamilWord.from("ஆயிர");

    private static List<Class<? extends IKnownWord>> verbbased = new ArrayList<Class<? extends IKnownWord>>() {
        {
            add(IBaseVinai.class);
        }

    };

    @Override
    public String getName() {
        return "நன்னூல்விதி183(நெடிலோ டுயிர்த்தொடர்க் குற்றுக ரங்களுட்\n" +
                "டறவொற் றிரட்டும்)";
    }

    public static final NannoolHandler183 HANDLER = new NannoolHandler183();

    protected boolean isEmptyVaruMozhiOk() {
        return true;
    }

    @Override
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilaipart, TamilWordPartContainer varumpart) {

        if (!nilaipart.isStartingWithUyir() && !nilaipart.isStartingWithNedil()) return null;
        if (nilaipart.size() < 3) return null;
        if (!nilaipart.isEndingWithUyirMei()) return null;
        if (!nilaipart.getWord().get(nilaipart.size() - 2).asTamilCharacter().isMeyyezhuththu()) return null;
        List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();
        //ஈற்றி ற்கு = ஈறு + இற்கு
        if (nilaipart.isEndingWithTwoConsonantsOfType(TamilCompoundCharacter.IDD)) {
            TamilCharacter last = (TamilCharacter) nilaipart.getWord().getLast();
            TamilWord nilai = nilaipart.getWord().subWord(0, nilaipart.size() - 2);
            nilai.add(TamilCompoundCharacter.IDD_U);

            TamilWord varm = varumpart.getWord().duplicate();
            varm.addFirst(last.getUyirPart());
            list.add(new TamilWordSplitResult(nilai, varm));
        } else if (nilaipart.isEndingWithTwoConsonantsOfType(TamilCompoundCharacter.IRR)) {
            TamilCharacter last = (TamilCharacter) nilaipart.getWord().getLast();
            TamilWord nilai = nilaipart.getWord().subWord(0, nilaipart.size() - 2);
            nilai.add(TamilCompoundCharacter.IRR_U);

            TamilWord varm = varumpart.getWord().duplicate();
            varm.addFirst(last.getUyirPart());
            list.add(new TamilWordSplitResult(nilai, varm));
        }


        return list;
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        if (nilai.isKutriyaLugaram()) {
            if (nilai.getWord().equals(ntoorru) && (varum.getWord().startsWith(koadi) || varum.getWord().startsWith(aayira))) {
                return null;
            }
            TamilCharacter lastbutone = nilai.getWord().get(nilai.size() - 2).asTamilCharacter();
            if (lastbutone.isUyirMeyyezhuththu() || lastbutone.isUyirezhuththu()) {
                TamilCompoundCharacter last = (TamilCompoundCharacter) nilai.getWord().getLast();
                if (last == TamilCompoundCharacter.IDD_U || last == TamilCompoundCharacter.IRR_U) {
                    if (isVerbDriven(varum.getWord())) return null;
                    TamilWord dup = nilai.getWord().duplicate();
                    dup.add(dup.size() - 1, last.getMeiPart());
                    WordsJoiner joiner = TamilFactory.createWordJoiner(dup);
                    joiner.addVaruMozhi(varum.getWord());
                    return new TamilWordPartContainer(joiner.getSum());
                }
            }
        }
        return null;
    }

    private boolean isVerbDriven(TamilWord w) {
        try {
            return !TamilFactory.getSystemDictionary().search(w, true, 1, verbbased).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}
