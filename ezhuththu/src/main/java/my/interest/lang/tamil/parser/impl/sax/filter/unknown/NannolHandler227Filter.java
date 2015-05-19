package my.interest.lang.tamil.parser.impl.sax.filter.unknown;

import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * "நன்னூல்விதி-227(லகர, ளகரவிதி)"
 * </p>
 *
 * @author velsubra
 */
public class NannolHandler227Filter implements UnknownWordFilter {

    public List<IKnownWord> filterUnknown(ParsingContext context) {
        TamilWordPartContainer varumozhiCandidate = context.varumozhi;
        if (context.tail.isEmpty()) {
            return Collections.emptyList();
        } else {
            if (varumozhiCandidate.size() < 2) {
                return Collections.emptyList();
            }
            IKnownWord next = context.tail.get(0);
            TamilCharacter lastfirstChar = next.getWord().getFirst().asTamilCharacter();
            if (!lastfirstChar.isVallinam() || !lastfirstChar.isUyirMeyyezhuththu()) {
                return Collections.emptyList();
            }
            if (varumozhiCandidate.isEndingWithOneConsonantOfType(TamilCompoundCharacter.IDD)) {
                TamilWord wordEndingWithIDD = varumozhiCandidate.getWord().duplicate();
                wordEndingWithIDD.removeLast();
                wordEndingWithIDD.add(TamilCompoundCharacter.ILL);
                return context.dictionary.lookup(wordEndingWithIDD);
            } else if (varumozhiCandidate.isEndingWithOneConsonantOfType(TamilCompoundCharacter.IRR)) {
                TamilWord wordEndingWithIDD = varumozhiCandidate.getWord().duplicate();
                wordEndingWithIDD.removeLast();
                wordEndingWithIDD.add(TamilCompoundCharacter.IL);
                return context.dictionary.lookup(wordEndingWithIDD);
            } else {
                return Collections.emptyList();
            }


        }
    }
}
