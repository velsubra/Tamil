package my.interest.lang.tamil.parser.impl.sax.filter;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
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
public class ThanikkurrilOttuFilter  implements UnknownWordFilter {

    public List<IKnownWord> filterUnknown(TamilWordPartContainer nilaimozhi, TamilWordPartContainer varumozhiCandidate, List<IKnownWord> tail, TamilDictionary dictionary, FeatureSet set) {
       if (tail.isEmpty()) {
           return Collections.emptyList();
       }  else {
           if (varumozhiCandidate.size() != 3) {
               return Collections.emptyList();
           }
           if (varumozhiCandidate.isEndingWithTwoConsonantsOfAnyType() && varumozhiCandidate.getWord().get(0).asTamilCharacter().isKurilezhuththu()) {
               IKnownWord next = tail.get(0);
               TamilWord thanikkurrilOttu = varumozhiCandidate.getWord().duplicate();
               thanikkurrilOttu.removeLast();
               return dictionary.lookup(thanikkurrilOttu);

           } else  {
               return Collections.emptyList();
           }

       }
    }
}
