package my.interest.lang.tamil.parser.impl.sax.filter.known;

import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilCharacter;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.Chuttuppeyar;
import tamil.lang.known.non.derived.IPeyarchchol;
import tamil.lang.known.non.derived.idai.Ottu;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class ChuttuppeyarFilter extends AbstractKnownWordFilter {
    public ChuttuppeyarFilter() {
        super(Chuttuppeyar.class);
    }

    public List<IKnownWord> filterMatched(IKnownWord recognized, ParsingContext context) {
        if (context.nilaimozhi.size() > 0) {
            return ignore();
        }
        if (context.tail.size() < 1) {

            return ignore();
        }
        IKnownWord next = context.tail.get(0);
       // IKnownWord nextnext = context.tail.get(1);

        TamilCharacter lastChar = (TamilCharacter) recognized.getWord().getLast();
        if (!lastChar.isMeyyezhuththu()) {
            return  ignore();
        }

//        if (!lastChar.isMeyyezhuththu()){
//            if (!Ottu.class.isAssignableFrom(next.getClass())) {
//                return ignore();
//            }
//        } else {
//
//        }

        TamilCharacter firstChar = next.getWord().getFirst().asTamilCharacter();
        if (!firstChar.isUyirMeyyezhuththu()) {
            return ignore();
        }
        if (lastChar != firstChar.getMeiPart()) {
            return  ignore();
        }

        if (!IPeyarchchol.class.isAssignableFrom(next.getClass())) {
            return ignore();
        }
        return returnSingle(recognized);
    }
}
