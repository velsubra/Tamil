package my.interest.lang.tamil.parser.impl.sax;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.known.IKnownWord;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class SpecificTokenRecognizer extends AbstractTokenRecognizer {
    protected SpecificTokenRecognizer(TamilWord token) {
        super(token);
    }

    @Override
    public TokenMatcherResult match(TamilWordPartContainer nilaimozhi, TamilWordPartContainer varumozhi, List<IKnownWord> tail, TamilDictionary dictionary, FeatureSet set) {
        if (token.equals(varumozhi.getWord())) {

            List<TamilWordPartContainer> list =  getNilaiMozhi(tokenContainer, nilaimozhi);


//                    new ArrayList<TamilWordPartContainer>();
//            list.add(nilaimozhi);
//            if (tokenContainer.isStartingWithUyir()) {
//                if (nilaimozhi.isEndingWithMei()) {
//                    if (!nilaimozhi.isThanikKurilOtru()) {
//                        TamilWord uyirvarin = nilaimozhi.getWord().duplicate();
//                        TamilCharacter last = uyirvarin.removeLast().asTamilCharacter();
//                        uyirvarin.addLast(last.addUyir(TamilSimpleCharacter.U));
//                        list.add(new TamilWordPartContainer(uyirvarin));
//                    }
//                }
//            }

            return TokenMatcherResult.Matching(list, getKnowns());
        }
        if (token.endsWith(varumozhi.getWord(), false)) {
            return TokenMatcherResult.Continue();
        }
        return TokenMatcherResult.DisContinue();
    }
}
