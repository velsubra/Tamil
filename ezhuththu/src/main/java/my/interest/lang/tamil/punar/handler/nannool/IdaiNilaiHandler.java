package my.interest.lang.tamil.punar.handler.nannool;

import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunharchiHandler;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class IdaiNilaiHandler extends AbstractPunharchiHandler {
    @Override
    public String getName() {
        return "காலவிடைநிலையைப்பொறுத்து வினையடியை விகாரப்படுதும் விதி.   ";
    }

    public static final IdaiNilaiHandler HANDLER = new IdaiNilaiHandler();

    @Override
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilaipart, TamilWordPartContainer varumpart) {
        return null;
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {

        if (nilai.isEndingWithUyirMei()) {
            if (nilai.isEndingWithOneConsonantOfType(TamilCompoundCharacter.IDD)) {
                //சுடு+ ட்
                //போடு
                if (varum.getWord().startsWith(TamilCompoundCharacter.IDD)) {
                    //அண்ட+ட்டும்
                    if (varum.size() > 1) return null;
                    TamilWord b = nilai.getWord().subWord(0, nilai.size() - 1);
                     b.add(nilai.getWord().getLast().asTamilCharacter().getMeiPart());
                    b.addAll(varum.getWord());
                    return new TamilWordPartContainer(b);
                }
            }
            if (nilai.isEndingWithOneConsonantOfType(TamilCompoundCharacter.IRR)) {
                //உறு+ற்+ஈர்

                if (varum.getWord().startsWith(TamilCompoundCharacter.IRR)) {
                    TamilWord b = nilai.getWord().subWord(0, nilai.size() - 1);
                    b.add(nilai.getWord().getLast().asTamilCharacter().getMeiPart());
                    b.addAll(varum.getWord());
                    return new TamilWordPartContainer(b);
                }
            }
        } else {
            if (nilai.getWord().endsWith(TamilCompoundCharacter.IL)) {
                //வில் + ப்
               // but not இயல்+ப்

//                if (varum.getWord().startsWith(TamilCompoundCharacter.IP)) {
//                    TamilWord b = nilai.getWord().subWord(0, nilai.size() - 1);
//                    b.appendNodesToAllPaths(TamilCompoundCharacter.IRR);
//                    b.addAll(varum.getWord());
//                    return new TamilWordPartContainer(b);
//                }

                //ஏல் +   ற்
                //but not செல்  + ற் = சென்ற்
                //சொல் + ற் = சொன்
//                if (varum.isStartingWithOneConsonantOfType(TamilCompoundCharacter.IRR)) {
//                    if (!varum.isThanikKurilOtru()) {
//                        TamilWord b = nilai.getWord().subWord(0, nilai.size() - 1);
//                        b.appendNodesToAllPaths(TamilCompoundCharacter.IRR);
//                        b.addAll(varum.getWord());
//                        return new TamilWordPartContainer(b);
//                    }
//                }
            } else if (nilai.getWord().endsWith(TamilCompoundCharacter.ILL)) {
                //  ஆள் /மீள்  + ட்
                // but not for கேள்
//            if (varum.getWord().startsWith(TamilCompoundCharacter.IDD)) {
//                TamilWord b = nilai.getWord().subWord(0, nilai.size() - 1);
//                b.appendNodesToAllPaths(TamilCompoundCharacter.INNN);
//                b.addAll(varum.getWord());
//                return new TamilWordPartContainer(b);
////            }
// else
                //கேள் + ப்
                //not aalh + p
//                if (varum.getWord().startsWith(TamilCompoundCharacter.IP)) {
//                    TamilWord b = nilai.getWord().subWord(0, nilai.size() - 1);
//                    b.appendNodesToAllPaths(TamilCompoundCharacter.IDD);
//                    b.addAll(varum.getWord());
//                    return new TamilWordPartContainer(b);
//                }
            }
        }

        return null;
    }
}
