package my.interest.lang.tamil.parser.impl.sax.filter;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.known.IKnownWord;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class UdambaduMeiFilter implements UnknownWordFilter {
    public List<IKnownWord> filterUnknown(TamilWordPartContainer nilaimozhi, TamilWordPartContainer varumozhiCandidate, List<IKnownWord> tail, TamilDictionary dictionary, FeatureSet set) {
        if (tail.isEmpty()) {
            return Collections.emptyList();
        }
        if (varumozhiCandidate.size() < 2) {
            return Collections.emptyList();
        }
        //சாலைய் ==     varumozhiCandidate ;  ஓரம் == tail.get(0)
        TamilCharacter last = varumozhiCandidate.getWord().getLast().asTamilCharacter();
        if (!last.isMeyyezhuththu()) return Collections.emptyList();
        boolean iv = last == TamilCompoundCharacter.IV;
        boolean iy = last == TamilCompoundCharacter.IY;
        if (iv || iy) {
            IKnownWord next = tail.get(0);
            if (!next.getWord().getFirst().asTamilCharacter().isUyirezhuththu()) {
                return Collections.emptyList();
            }
            TamilWord lastbutone = varumozhiCandidate.getWord().duplicate();
            lastbutone.removeLast();
            TamilWordPartContainer   container = new TamilWordPartContainer(lastbutone);

            if (iv) {
                 if (!container.isEndingWithVagaraUdambadumeiYuir()) {
                     return Collections.emptyList();
                 }
                return dictionary.lookup(lastbutone);
            } else {
                if (!container.isEndingWithYagaraUdambadumeiYuir()) {
                    return Collections.emptyList();
                }
                return dictionary.lookup(lastbutone);

            }
        } else {
            return Collections.emptyList();
        }


    }
}
