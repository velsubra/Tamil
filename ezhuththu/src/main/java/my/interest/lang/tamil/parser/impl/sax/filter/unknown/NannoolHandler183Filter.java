package my.interest.lang.tamil.parser.impl.sax.filter.unknown;

import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.IBasePeyar;
import tamil.lang.known.non.derived.idai.VUrubu;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * நன்னூல்விதி183(நெடிலோ டுயிர்த்தொடர்க் குற்றுக ரங்களுட்டறவொற்றிரட்டும்
 * <p/>
 * </p>
 *
 * @author velsubra
 */
public class NannoolHandler183Filter implements UnknownWordFilter {

    public List<IKnownWord> filterUnknown(ParsingContext context) {
        TamilWordPartContainer varumozhiCandidate = context.varumozhi;
        if (!context.tail.isEmpty() && varumozhiCandidate.size() >= 3 && varumozhiCandidate.isEndingWithUyirMei()) {
            if (varumozhiCandidate.getWord().get(varumozhiCandidate.size() - 2).asTamilCharacter().isMeyyezhuththu()) {
                IKnownWord last = context.tail.get(0);
                if (VUrubu.class.isAssignableFrom(last.getClass()) || IBasePeyar.class.isAssignableFrom(last.getClass())) {
                    //ஈற்றி ற்கு = ஈறு + இற்கு
                    if (varumozhiCandidate.isEndingWithTwoConsonantsOfType(TamilCompoundCharacter.IRR)) {
                        TamilWord candidate = varumozhiCandidate.getWord().subWord(0, varumozhiCandidate.size() - 2);
                        candidate.add(TamilCompoundCharacter.IRR_U);
                        return context.dictionary.lookup(candidate);
                    } else if (varumozhiCandidate.isEndingWithTwoConsonantsOfType(TamilCompoundCharacter.IDD)) {
                        TamilWord candidate = varumozhiCandidate.getWord().subWord(0, varumozhiCandidate.size() - 2);
                        candidate.add(TamilCompoundCharacter.IDD_U);
                        return context.dictionary.lookup(candidate);
                    }
                }
            }

        }

        return Collections.emptyList();
    }
}
