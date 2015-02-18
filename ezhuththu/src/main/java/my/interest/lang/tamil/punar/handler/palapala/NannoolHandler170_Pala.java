package my.interest.lang.tamil.punar.handler.palapala;

import tamil.lang.TamilCharacter;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunarchiHandler;
import my.interest.lang.tamil.punar.handler.nannool.NannoolHandler158_3_2;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class NannoolHandler170_Pala extends AbstractPunarchiHandler {
    @Override
    public String getName() {
        return "நன்னூல்விதி-170_1(பல,பல)";
    }

    public static final TamilWord PALA = TamilWord.from("பல");
    public static final TamilWord SILA = TamilWord.from("சில");

    public List<TamilWordSplitResult> splitAll(TamilWordPartContainer result) {
        List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();
        if (result.size() == 4) {
            if (result.getWord().endsWith((PALA))) {
                if (result.getWord().startsWith(PALA.subWord(0, 1))) {
                    if (TamilSimpleCharacter.LA.equals(result.getWord().get(1)) || TamilCompoundCharacter.IRR.equals(result.getWord().get(1))) {
                        list.add(new TamilWordSplitResult(PALA, PALA));
                        return list;
                    }
                }
            } else if (result.getWord().endsWith((SILA))) {
                if (result.getWord().startsWith(SILA.subWord(0, 1))) {
                    if (TamilSimpleCharacter.LA.equals(result.getWord().get(1)) || TamilCompoundCharacter.IRR.equals(result.getWord().get(1))) {
                        list.add(new TamilWordSplitResult(SILA, SILA));
                        return list;
                    }
                }
            }
        } else if (result.size() == 5) {
            if (result.getWord().endsWith((PALA))) {
                if (result.getWord().startsWith(PALA)) {
                    if (TamilCompoundCharacter.IP.equals(result.getWord().get(2))) {
                        list.add(new TamilWordSplitResult(PALA, PALA));
                        return list;
                    }
                }
            } else if (result.getWord().endsWith((SILA))) {
                if (result.getWord().startsWith(SILA)) {
                    if (TamilCompoundCharacter.ICH.equals(result.getWord().get(2))) {
                        list.add(new TamilWordSplitResult(SILA, SILA));
                        return list;
                    }
                }
            }
        }
        return null;


    }


    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        if (nilai.getWord().equals(PALA) || nilai.getWord().equals(SILA)) {
            if (!nilai.getWord().equals(varum.getWord())) {
                TamilWord n = new TamilWord();
                n.add(nilai.getWord().get(0));
                n.add(((TamilCharacter) nilai.getWord().get(1)).getMeiPart());
                NannoolHandler158_3_2 h = new NannoolHandler158_3_2();
                TamilWordPartContainer r = h.join(new TamilWordPartContainer(n), varum);
                //More has be handled.
                return r;
            } else {
                TamilWord n = new TamilWord();

                n.add(nilai.getWord().get(0));
                n.add(TamilCompoundCharacter.IRR);
                n.addAll(varum.getWord());
                return new TamilWordPartContainer(n);
            }
        }
        return null;

    }
}