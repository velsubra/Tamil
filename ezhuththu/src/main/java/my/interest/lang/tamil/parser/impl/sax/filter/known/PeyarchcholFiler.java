package my.interest.lang.tamil.parser.impl.sax.filter.known;

import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.derived.VinaiyadiDerivative;
import tamil.lang.known.non.derived.IPeyarchchol;
import tamil.lang.known.non.derived.idai.Ottu;
import tamil.lang.known.non.derived.Peyarchchol;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class PeyarchcholFiler extends AbstractKnownWordFilter {
    public PeyarchcholFiler() {
        super(IPeyarchchol.class);
    }

    @Override
    public List<IKnownWord> filterMatched(IKnownWord recognized, ParsingContext context) {
        if (!context.tail.isEmpty()) {
            if (context.tail.size() > 1) {
                IKnownWord next = context.tail.get(0);
                IKnownWord nextnext = context.tail.get(1);
                if (Ottu.class.isAssignableFrom(next.getClass())) {
                   if ( !Peyarchchol.class.isAssignableFrom(nextnext.getClass())) {
                       //அறிமுகப்படுத்து
                       if (recognized.getWord().getLast() != TamilCompoundCharacter.IM && !VinaiyadiDerivative.class.isAssignableFrom(nextnext.getClass())) {
                           return ignore();
                       }
                   }
                }
            }
        }
        return returnSingle(recognized);

    }
}