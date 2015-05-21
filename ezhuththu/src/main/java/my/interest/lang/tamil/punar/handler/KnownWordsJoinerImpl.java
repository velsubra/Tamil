package my.interest.lang.tamil.punar.handler;

import my.interest.lang.tamil.punar.handler.nannool.NannoolHandler165;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.api.join.KnownWordsJoiner;
import tamil.lang.exception.TamilPlatformException;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.IPeyarchchol;
import tamil.lang.known.non.derived.idai.Kalh;
import tamil.lang.known.non.derived.Peyarchchol;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class KnownWordsJoinerImpl implements KnownWordsJoiner {

    IKnownWord sum = null;

    public KnownWordsJoinerImpl(IKnownWord base) {
        this.sum = base;
    }

    /**
     * adds word to the current sum of the joiner by means of doing புணர்ச்சி
     *
     * @param word the word to be added
     */
    public void addVaruMozhi(IKnownWord word, TYPE type) {
        if (Kalh.KALH == word && IPeyarchchol.class.isAssignableFrom(sum.getClass())) {
            IPeyarchchol peyar = (IPeyarchchol) sum;
            WordsJoinHandler handler = new WordsJoinHandler();
            boolean vali = false;
            if (sum.getWord().size() == 1) {
                vali = true;

            }
            TamilCharacter last = sum.getWord().getLast().asTamilCharacter();
            if (last.isUyirMeyyezhuththu()) {
                TamilSimpleCharacter uyir = last.getUyirPart();
                if (uyir.isaa() || uyir.isOO() || uyir.isAA() || uyir.isEE()) {
                    vali = true;
                } else if (uyir.isI()) {
                    vali = false;
                }
            }


            if (vali) {
                handler.prependInstanceHandler(NannoolHandler165.HANDLER);
            }
            handler.addNilaiMozhi(sum.getWord().duplicate());
            handler.addVaruMozhi(word.getWord().duplicate());
            sum = new Peyarchchol(handler.getSum(), 0, peyar.isUyarThinhai(), peyar.isProNoun());
        } else {
            throw new TamilPlatformException("Unhandled punharchi yet:");
        }
    }

    /**
     * adds the current sum of the joiner into the given word by doing புணர்ச்சி
     *
     * @param word the word to be inserted
     */
    public void addNilaiMozhi(IKnownWord word, TYPE type) {
        KnownWordsJoinerImpl impl = new KnownWordsJoinerImpl(word);
        impl.addVaruMozhi(sum, type);
        sum = impl.getSum();
    }


    public IKnownWord getSum() {
        return sum;
    }


}
