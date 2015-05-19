package my.interest.lang.tamil.parser.impl.sax.filter.both;

import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import my.interest.lang.tamil.parser.impl.sax.filter.known.KnowWordFilter;
import my.interest.lang.tamil.parser.impl.sax.filter.unknown.UnknownWordFilter;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilCompoundCharacter;
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
public class UdambaduMeiFilter implements UnknownWordFilter, KnowWordFilter {
    public List<IKnownWord> filterUnknown(ParsingContext context) {
        TamilWordPartContainer varumozhiCandidate = context.varumozhi;
        if (context.tail.isEmpty()) {
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
            IKnownWord next = context.tail.get(0);
            if (!next.getWord().getFirst().asTamilCharacter().isUyirezhuththu()) {
                return Collections.emptyList();
            }
            TamilWord lastbutone = varumozhiCandidate.getWord().duplicate();
            lastbutone.removeLast();
            if (!lastbutone.getLast().asTamilCharacter().isUyirMeyyezhuththu() && !lastbutone.getLast().asTamilCharacter().isUyirezhuththu()) {
                return Collections.emptyList();
            }
            TamilWordPartContainer   container = new TamilWordPartContainer(lastbutone);

            if (iv) {
                 if (!container.isEndingWithVagaraUdambadumeiYuir()) {
                     return Collections.emptyList();
                 }
                return context.dictionary.lookup(lastbutone);
            } else {
                if (!container.isEndingWithYagaraUdambadumeiYuir()) {
                    return Collections.emptyList();
                }
                return context.dictionary.lookup(lastbutone);

            }
        } else {
            return Collections.emptyList();
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
        if (context.tail.isEmpty()) {
            return returnThis(recognized);
        }
        IKnownWord next = context.tail.get(0);
        if (!next.getWord().get(0).asTamilCharacter().isUyirezhuththu()) {
            return returnThis(recognized);
        }

        if (!recognized.getWord().getLast().asTamilCharacter().isUyirMeyyezhuththu()) {
            return returnThis(recognized);
        }
        if (context.varumozhi.isEndingWithOneConsonantOfType(TamilCompoundCharacter.IY) || context.varumozhi.isEndingWithOneConsonantOfType(TamilCompoundCharacter.IV)) {
            return returnThis(recognized);
        }
        if (context.varumozhi.isUkkurralh()) {
            return returnThis(recognized);
        }
         return Collections.emptyList();
    }
}
