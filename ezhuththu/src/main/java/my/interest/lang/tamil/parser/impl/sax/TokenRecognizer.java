package my.interest.lang.tamil.parser.impl.sax;

import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public abstract class TokenRecognizer {


    public TokenMatcherResult matchRoot(ParsingContext context) {
        return match(context);
    }


    public abstract TokenMatcherResult match(ParsingContext context);


    public static boolean isAllJustOfKind(Class<? extends IKnownWord> clz, IKnownWord... all) {
        if (all == null) return false;
        for (IKnownWord w : all) {
            if (!clz.isAssignableFrom(w.getClass())) {
                return false;
            }
        }
        return true;
    }


    public static boolean isAllJustOfExactKind(Class<? extends IKnownWord> clz, IKnownWord... all) {
        if (all == null) return false;
        for (IKnownWord w : all) {
            if (clz != w.getClass()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if there is at least one known word of the given type
     * @param clz
     * @param all
     * @return
     */
    public static boolean isAtleastKind(Class<? extends IKnownWord> clz, IKnownWord... all) {
        if (all == null) return false;
        for (IKnownWord w : all) {
            if (clz.isAssignableFrom(w.getClass())) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns true if the word is one of the types
     * @param known
     * @param types
     * @return
     */
    public static boolean isWordOfOneOfTypes(IKnownWord known, Class<? extends IKnownWord>... types) {
        if (types == null) return false;

        for (Class t : types) {
            if (t.isAssignableFrom(known.getClass())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the possible nilimozhis
     */
    public static List<TamilWordPartContainer> getNilaiMozhi(ParsingContext context) {
        boolean nilaimozhiFine = true;
        //This contains the text that is resloved.
        TamilWordPartContainer tokenContainer = context.varumozhi;
        TamilWordPartContainer nilaimozhi = context.nilaimozhi;
        List<TamilWordPartContainer> list = new ArrayList<TamilWordPartContainer>();

//        //nilaimozhi.size() == 3 is to help thanikkurril ottu + ottu
//        if (nilaimozhi.size() == 3 && nilaimozhi.isEndingWithTwoConsonantsOfAnyType() && nilaimozhi.getWord().getLast().asTamilCharacter().isMeyyezhuththu()) {
//
//            nilaimozhiFine = false;
//
//        }

        if (tokenContainer.isStartingWithUyir()) {
            if (nilaimozhi.isEndingWithMei()) {
                if (!nilaimozhi.isThanikKurilOtru()) {
                    TamilWord uyirvarin = nilaimozhi.getWord().duplicate();
                    TamilCharacter last = uyirvarin.removeLast().asTamilCharacter();
                    uyirvarin.addLast(last.addUyir(TamilSimpleCharacter.U));
                    list.add(new TamilWordPartContainer(uyirvarin));
                }
            }

        }
//
//        if (nilaimozhi.size() > 1) {
//
//            if (tokenContainer.isStartingWithUyir()) {
//                // Remove udambadu mei
//                if (nilaimozhi.getWord().getLast() == TamilCompoundCharacter.IY) {
//                    TamilWordPartContainer lastbutone = new TamilWordPartContainer(new TamilWord(nilaimozhi.getWord().get(nilaimozhi.size() - 2)));
//                    if (lastbutone.isStartingWithYagaraUdambadumeiYuir()) {
//                        TamilWord rmMei = nilaimozhi.getWord().duplicate();
//                        rmMei.removeLast();
//                        list.add(new TamilWordPartContainer(rmMei));
//                    }
//                } else if (nilaimozhi.getWord().getLast() == TamilCompoundCharacter.IV) {
//                    TamilWordPartContainer lastbutone = new TamilWordPartContainer(new TamilWord(nilaimozhi.getWord().get(nilaimozhi.size() - 2)));
//                    if (lastbutone.isStartingWithVagaraUdambadumeiYuir()) {
//                        TamilWord rmMei = nilaimozhi.getWord().duplicate();
//                        rmMei.removeLast();
//                        list.add(new TamilWordPartContainer(rmMei));
//                    }
//                }
//            }
//        }

        if (nilaimozhiFine) {
            list.add(nilaimozhi);
        }
        return list;
    }


}
