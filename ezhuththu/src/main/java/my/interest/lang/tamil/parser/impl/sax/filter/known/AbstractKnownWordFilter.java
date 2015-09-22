package my.interest.lang.tamil.parser.impl.sax.filter.known;

import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import my.interest.lang.tamil.parser.impl.sax.filter.known.KnowWordFilter;
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
public abstract class AbstractKnownWordFilter implements KnowWordFilter {

    protected Class<? extends IKnownWord> type = null;

    protected AbstractKnownWordFilter(Class<? extends IKnownWord> type) {
        this.type = type;
    }


    public abstract List<IKnownWord> filterMatched(IKnownWord recognized, ParsingContext context);

    public List<IKnownWord> filter(IKnownWord recognized, ParsingContext context) {
        if (type.isAssignableFrom(recognized.getClass())) {
            return filterMatched(recognized,  context);

        } else {
            return returnSingle(recognized);
        }
    }

    protected List<IKnownWord> ignore() {
        return Collections.emptyList();
    }

    protected List<IKnownWord> returnSingle(IKnownWord word) {
        List<IKnownWord> list = new ArrayList<IKnownWord>();
        list.add(word);
        return list;
    }
}
