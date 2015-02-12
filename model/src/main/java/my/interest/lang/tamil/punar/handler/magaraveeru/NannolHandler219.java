package my.interest.lang.tamil.punar.handler.magaraveeru;

import common.lang.impl.AbstractCharacter;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunarchiHandler;
import my.interest.lang.tamil.punar.handler.iyalbu.IyalbuPunarchiHandler;
import my.interest.lang.tamil.punar.handler.udambadu.UadambaduMeiHandler;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilWord;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class NannolHandler219 extends AbstractPunarchiHandler {
    @Override
    public String getName() {
        return "நன்னூல்விதி-219(மவ்வீறு)";
    }

    public static final NannolHandler219 HANDLER = new NannolHandler219();
    static final TamilWord UM = TamilWord.from("உம்");

    @Override
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilaipart, TamilWordPartContainer varumpart) {
        List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();
        //குளங்கரை
        if (varumpart.isInavezhuththuSeries()) {
            TamilWord n = nilaipart.getWord().duplicate();
            n.add(TamilCompoundCharacter.IM);

            TamilWord v = varumpart.getWord().duplicate();
            v.remove(0);
            list.add(new TamilWordSplitResult(n, v));
            return list;
        }


        if (varumpart.isStartingWithTwoConsonantsOfType(TamilCompoundCharacter.ITH) && varumpart.isStartingWithMeiFollowedByUyirMei()) {
            TamilWord n = nilaipart.getWord().duplicate();
            n.add(TamilCompoundCharacter.IM);

            TamilWord v = varumpart.getWord().duplicate();
            v.remove(0);

            //மரத்தில் = மரம் +  அத்து +  இல்.
            list.add(new TamilWordSplitResult(n, v.duplicate()));
            v.add(0, v.remove(0).asTamilCharacter().getUyirPart());
            list.add(new TamilWordSplitResult(n, TamilWord.from("அத்து"), v));
            return list;
        }


        //குள + கரை
        if (!nilaipart.isEndingWithMei() && varumpart.isStartingWithTwoConsonantsOfKASATHABA()) {
            if (varumpart.getWord().get(1).asTamilCharacter().isUyirMeyyezhuththu()) {
                TamilWord n = nilaipart.getWord().duplicate();
                n.add(TamilCompoundCharacter.IM);

                TamilWord v = varumpart.getWord().duplicate();
                v.remove(0);
                list.add(new TamilWordSplitResult(n, v));
            }
        } else {
            //குள + நூல்

            if (!nilaipart.isEndingWithMei() && varumpart.isStartingWithUyirMei() && !varumpart.getWord().get(0).asTamilCharacter().isVallinam()) {
                TamilWord n = nilaipart.getWord().duplicate();
                n.add(TamilCompoundCharacter.IM);

                if (varumpart.isStartingFine()) {
                    list.add(new TamilWordSplitResult(n, varumpart.getWord()));
                }
                //வீர + வுணர்வு
                if (nilaipart.isEndingWithVagaraUdambadumeiYuir() && varumpart.isStartingWithOneConsonantOfType(TamilCompoundCharacter.IV)) {
                    TamilWord v = varumpart.getWord().duplicate();
                    v.add(0, v.removeFirst().asTamilCharacter().getUyirPart());
                    list.add(new TamilWordSplitResult(n, v));
                }

            } else {
                //குளம் + ஆடு
                if (varumpart.isStartingWithOneConsonantOfType(TamilCompoundCharacter.IM) && varumpart.isStartingWithUyirMei()) {
                    TamilWord n = nilaipart.getWord().duplicate();
                    n.add(TamilCompoundCharacter.IM);
                    TamilWord v = varumpart.getWord().duplicate();
                    AbstractCharacter ma = v.remove(0);
                    v.add(0, ma.asTamilCharacter().getUyirPart());
                    list.add(new TamilWordSplitResult(n, v));
                } else {
                    List<TamilWordSplitResult> udam = UadambaduMeiHandler.HANDLER.split(nilaipart, varumpart);
                    if (udam != null && !udam.isEmpty()) {
                        for (TamilWordSplitResult r : udam) {
                            TamilWord n = r.get(0).getWord().duplicate();
                            n.add(TamilCompoundCharacter.IM);

                            list.add(new TamilWordSplitResult(n, r.get(1).getWord()));
                        }
                    }
                }
            }
        }


        return list;
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        if (nilai.getWord().endsWith(TamilCompoundCharacter.IM)) {
            TamilWord mgone = nilai.getWord().subWord(0, nilai.getWord().size() - 1);
            if (varum.isStartingWithUyir()) {
                if (nilai.getWord().endsWith(UM, false)) {
                    return IyalbuPunarchiHandler.HANDLER.join(nilai, varum);
                } else {
                    return UadambaduMeiHandler.HANDLER.join(new TamilWordPartContainer(mgone), varum);
                }
            } else if (varum.isStartingWithUyirMei()) {
                if (varum.getWord().get(0).asTamilCharacter().isVallinam()) {
                    mgone.add(varum.getWord().get(0).asTamilCharacter().getMeiPart());
                    mgone.addAll(varum.getWord());
                    return new TamilWordPartContainer(mgone);
                } else {
                    mgone.addAll(varum.getWord());
                    return new TamilWordPartContainer(mgone);
                }
            }
        }
        return null;
    }
}
