package my.interest.lang.tamil.parser.impl.sax.filter.unknown;

import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;

import java.util.Collections;
import java.util.List;

/**
 * Created by velsubra on 6/13/16.
 * // in the first part we do
 * சொறொடர் => சொற் +  தொடர்
 */
public class NannoolHandler227FilterFirstPart implements UnknownWordFilter {
    public List<IKnownWord> filterUnknown(ParsingContext context) {
        if (context.nilaimozhi.isEndingWithMei() && context.varumozhi.isStartingWithUyirMei()) {
            if (context.nilaimozhi.isEndingWithOneConsonantOfType(TamilCompoundCharacter.IDD)) {
                if (context.varumozhi.isStartingWithOneConsonantOfType(TamilCompoundCharacter.IDD)) {
                    TamilWord starting_With_Tha = context.varumozhi.getWord().duplicate();
                    TamilCharacter first = (TamilCharacter) starting_With_Tha.removeFirst();
                    starting_With_Tha.addFirst(TamilCompoundCharacter.ITH.addUyir(first.getUyirPart()));
                    return context.dictionary.lookup(starting_With_Tha);

                }
            } else if (context.nilaimozhi.isEndingWithOneConsonantOfType(TamilCompoundCharacter.IRR)) {
                if (context.varumozhi.isStartingWithOneConsonantOfType(TamilCompoundCharacter.IRR)) {
                    TamilWord starting_With_Tha = context.varumozhi.getWord().duplicate();
                    TamilCharacter first = (TamilCharacter) starting_With_Tha.removeFirst();
                    starting_With_Tha.addFirst(TamilCompoundCharacter.ITH.addUyir(first.getUyirPart()));
                    return context.dictionary.lookup(starting_With_Tha);

                }
            }
        }
        return Collections.emptyList();

    }
}
