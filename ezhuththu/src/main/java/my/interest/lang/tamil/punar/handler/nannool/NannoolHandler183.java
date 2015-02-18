package my.interest.lang.tamil.punar.handler.nannool;

import tamil.lang.TamilCharacter;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunarchiHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class NannoolHandler183 extends AbstractPunarchiHandler {
    @Override
    public String getName() {
        return "நன்னூல்விதி183(நெடிலோ டுயிர்த்தொடர்க் குற்றுக ரங்களுட்\n" +
                "டறவொற் றிரட்டும்)";
    }
    public  static final NannoolHandler183 HANDLER = new NannoolHandler183();

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
        return null;
    }
}
