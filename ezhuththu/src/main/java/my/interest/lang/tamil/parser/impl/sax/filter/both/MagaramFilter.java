package my.interest.lang.tamil.parser.impl.sax.filter.both;

import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import my.interest.lang.tamil.parser.impl.sax.filter.known.KnowWordFilter;
import my.interest.lang.tamil.parser.impl.sax.filter.known.PeyarchcholFiler;
import my.interest.lang.tamil.parser.impl.sax.filter.unknown.UnknownWordFilter;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.IPeyarchchol;
import tamil.lang.known.non.derived.idai.Ottu;

import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class MagaramFilter implements KnowWordFilter, UnknownWordFilter {
   // private static Map<TamilDictionary, Map<TamilWord, List<IKnownWord>>> magaramCache = new HashMap<TamilDictionary, Map<TamilWord, List<IKnownWord>>>();
    List<Class<? extends IKnownWord>> list = new ArrayList<Class<? extends IKnownWord>>() {
        {
            add(IPeyarchchol.class);
        }
    };

    public List<IKnownWord> filter(IKnownWord recognized, ParsingContext context) {
        if (context.tail.isEmpty() || recognized.getWord().size() < 2 || !recognized.getWord().getLast().asTamilCharacter().isUyirMeyyezhuththu()) {
            return returnThis(recognized);
        } else {
            IKnownWord next = context.tail.get(0);
            if (!Ottu.class.isAssignableFrom(next.getClass())) {
                TamilCharacter first = next.getWord().getFirst().asTamilCharacter();
                if (!first.isUyirMeyyezhuththu() || first.isVallinam()) {
                    return returnThis(recognized);
                }
            }

            TamilWord magaram = recognized.getWord().duplicate();
            magaram.add(TamilCompoundCharacter.IM);

            List<IKnownWord> knowns = null; //magaramCache.get(context.dictionary).get(magaram);
            if (knowns == null) {
                List<IKnownWord> knownsbeforeFilter = context.dictionary.search(magaram, true, 2, list);
                knowns = new ArrayList<IKnownWord>();

                //Check if these nouns are really valid at these locations.
                PeyarchcholFiler filter = new PeyarchcholFiler();
                for (IKnownWord peyar : knownsbeforeFilter) {
                    if (!filter.filterMatched(peyar, context).isEmpty()) {
                        knowns.add(peyar);
                    }
                }

                if (!knowns.isEmpty()) {
                    //  knowns.add(recognized);
                   // magaramCache.put(magaram, knowns);
                    return knowns;
                }
            } else {
                if (!knowns.contains(recognized)) {
                    // knowns.add(recognized);
                }
                return knowns;
            }
        }
        return returnThis(recognized);
    }

    private static List<IKnownWord> returnThis(IKnownWord recog) {
        List<IKnownWord> list = new ArrayList<IKnownWord>();
        list.add(recog);
        return list;
    }

    public List<IKnownWord> filterUnknown(ParsingContext context) {
        TamilWordPartContainer varumozhiCandidate = context.varumozhi;
        if (context.tail.isEmpty() || varumozhiCandidate.size() < 2) { //|| !varumozhiCandidate.getWord().getLast().asTamilCharacter().isUyirMeyyezhuththu()) {
            return Collections.emptyList();
        } else {
            IKnownWord next = context.tail.get(0);
            //வெல்ல த் தாழி
            if (!Ottu.class.isAssignableFrom(next.getClass())) {
                boolean vallinam_Without_Ottu = false;
                boolean panhpuththogai_candidate = false;
                //vallinam without ottu
                TamilCharacter first = next.getWord().getFirst().asTamilCharacter();
                if (!first.isUyirMeyyezhuththu() || first.isVallinam()) {
                    vallinam_Without_Ottu = true;
                }

                //செந்தமிழ்
                TamilCharacter first_In_Next = next.getWord().getFirst().asTamilCharacter();
                if (first_In_Next.isVallinam()) {
                    TamilCharacter varumLast = varumozhiCandidate.getWord().getLast().asTamilCharacter();
                    if (varumLast.isMeyyezhuththu()) {
                        if (first_In_Next.getInaMellinam() == varumLast.getMeiPart()) {
                            TamilWord removeLast = varumozhiCandidate.getWord().duplicate();
                            removeLast.removeLast();
                            varumozhiCandidate = new TamilWordPartContainer(removeLast);
                            panhpuththogai_candidate = true;
                        }
                    }
                }


                if (vallinam_Without_Ottu && !panhpuththogai_candidate) {
                    return Collections.emptyList();
                }
            }

            TamilWord magaram = varumozhiCandidate.getWord().duplicate();
            magaram.add(TamilCompoundCharacter.IM);
            //நானுங்கூறினேன் even உம் has tobe found
            List<IKnownWord> knowns = null; //magaramCache.get(magaram);
            if (knowns == null) {
                knowns = context.dictionary.lookup(magaram);
                if (!knowns.isEmpty()) {
                  //  magaramCache.put(magaram, knowns);
                    return knowns;
                } else {
                    return Collections.emptyList();
                }
            } else {
                return knowns;
            }
        }
    }
}
