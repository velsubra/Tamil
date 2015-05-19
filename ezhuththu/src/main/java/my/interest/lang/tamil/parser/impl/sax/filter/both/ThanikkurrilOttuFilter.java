package my.interest.lang.tamil.parser.impl.sax.filter.both;

import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import my.interest.lang.tamil.parser.impl.sax.filter.known.KnowWordFilter;
import my.interest.lang.tamil.parser.impl.sax.filter.unknown.UnknownWordFilter;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class ThanikkurrilOttuFilter  implements UnknownWordFilter, KnowWordFilter {

    public List<IKnownWord> filterUnknown(ParsingContext context) {
        TamilWordPartContainer varumozhiCandidate = context.varumozhi;
       if (context.tail.isEmpty()) {
           return Collections.emptyList();
       }  else {
           if (varumozhiCandidate.size() != 3) {
               return Collections.emptyList();
           }
           IKnownWord next = context.tail.get(0);
           if (!next.getWord().getFirst().asTamilCharacter().isUyirezhuththu()) {
               return   Collections.emptyList();
           }
           if (varumozhiCandidate.isEndingWithTwoConsonantsOfAnyType() &&  varumozhiCandidate.getWord().get(0).asTamilCharacter().isKurilezhuththu() &&varumozhiCandidate.getWord().getLast() == varumozhiCandidate.getWord().get( varumozhiCandidate.getWord().size() -2)) {

               TamilWord thanikkurrilOttu = varumozhiCandidate.getWord().duplicate();
               thanikkurrilOttu.removeLast();
               return context.dictionary.lookup(thanikkurrilOttu);

           } else  {
               return Collections.emptyList();
           }

       }
    }

    private static List<IKnownWord> returnThis(IKnownWord recog) {
        List<IKnownWord> list = new ArrayList<IKnownWord>();
        list.add(recog);
        return list;
    }

    /**
     * @param recognized should be returned as part of the list if this is a valid sequence
     * @param context    the context of parsing
     * @return the filtered list, New words can be added into it.
     */
    public List<IKnownWord> filter(IKnownWord recognized, ParsingContext context) {
        if (recognized.getWord().size() != 2 ) {
           return returnThis(recognized);
        }
        if (context.tail.isEmpty()) {
           return returnThis(recognized);
        }
        IKnownWord next = context.tail.get(0);
        if (!next.getWord().getFirst().asTamilCharacter().isUyirezhuththu()) {
            return returnThis(recognized);
        }
        if (!context.varumozhi.isThanikKurilOtru()) {
            return returnThis(recognized);
        }
        return  Collections.emptyList();
    }
}
