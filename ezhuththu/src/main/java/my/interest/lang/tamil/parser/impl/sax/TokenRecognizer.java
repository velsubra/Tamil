package my.interest.lang.tamil.parser.impl.sax;

import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilCharacter;
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
public abstract  class TokenRecognizer {


    public  TokenMatcherResult matchRoot(TamilWordPartContainer nilaimozhi, TamilWordPartContainer varumozhi , List<IKnownWord> tail) {
        return match(nilaimozhi, varumozhi, tail);
    }


    public abstract TokenMatcherResult match(TamilWordPartContainer nilaimozhi, TamilWordPartContainer varumozhi , List<IKnownWord> tail);




    protected static boolean isAllJustOfKind( Class<? extends IKnownWord> clz, IKnownWord ... all) {
        if (all == null) return false;
        for (IKnownWord w : all) {
            if (!clz.isAssignableFrom(w.getClass())) {
                return false;
            }
        }
        return true;
    }

    protected static boolean isAtleastKind(Class<? extends IKnownWord> clz, IKnownWord ... all) {
        if (all == null) return false;
        for (IKnownWord w : all) {
            if (clz.isAssignableFrom(w.getClass())) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param tokenContainer   the current recognized word (varumozhi)
     * @param nilaimozhi   the remaining text
     * @return the possible nilimozhis
     */
    protected static List<TamilWordPartContainer>  getNilaiMozhi(TamilWordPartContainer tokenContainer, TamilWordPartContainer nilaimozhi) {
        List<TamilWordPartContainer> list = new ArrayList<TamilWordPartContainer>();
          //nilaimozhi.size() == 3 is to help thanikkurril ottu + ottu
        if (nilaimozhi.size() >= 3 || !nilaimozhi.isEndingWithTwoConsonantsOfAnyType() ) {

                list.add(nilaimozhi);

        }
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
        return list;
    }


}
