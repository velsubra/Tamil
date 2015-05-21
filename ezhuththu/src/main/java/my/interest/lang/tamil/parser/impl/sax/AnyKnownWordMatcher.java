package my.interest.lang.tamil.parser.impl.sax;

import my.interest.lang.tamil.impl.NumberDictionary;
import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
import my.interest.lang.tamil.parser.impl.sax.filter.both.MagaramFilter;
import my.interest.lang.tamil.parser.impl.sax.filter.both.ThanikkurrilOttuFilter;
import my.interest.lang.tamil.parser.impl.sax.filter.both.UdambaduMeiFilter;
import my.interest.lang.tamil.parser.impl.sax.filter.known.*;
import my.interest.lang.tamil.parser.impl.sax.filter.unknown.NannolHandler227Filter;
import my.interest.lang.tamil.parser.impl.sax.filter.unknown.NannoolHandler183Filter;
import my.interest.lang.tamil.parser.impl.sax.filter.unknown.UnknownWordFilter;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.api.dictionary.ReverseSearchFeature;
import tamil.lang.api.dictionary.StartsWithHigherLengthSearch;
import tamil.lang.api.parser.EagerlyParsingFeature;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.*;
import tamil.lang.known.non.derived.idai.Aththu;
import tamil.lang.known.non.derived.idai.Ottu;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class AnyKnownWordMatcher extends TokenRecognizer {


    public AnyKnownWordMatcher() {
        super();
    }

    /**
     * Creates a new Parser
     *
     * @param noNonWordLookup, flag indicating if the nilaimozhi is not recognized, if that should be passed through the filter.
     */
    public AnyKnownWordMatcher(boolean noNonWordLookup) {
        super();
        this.noNonWordLookup = noNonWordLookup;
    }

    private boolean noNonWordLookup = false;

    private static List<KnowWordFilter> filters = new ArrayList<KnowWordFilter>() {
        {
            add(new PeyarchcholFiler());
            add(new MagaramFilter());
            add(new ThanikkurrilOttuFilter());
            add(new UdambaduMeiFilter());
            add(new ChuttuppeyarFilter());
            add(new PanhpuththogaiThiribuFilter());
            add(new VUrubuFilter());
            add(new VinaiyadiFilter());
            add(new IdaichcholFilter());


        }
    };


    private static List<UnknownWordFilter> filtersUnknown = new ArrayList<UnknownWordFilter>() {
        {
            add(new MagaramFilter());
            add(new UdambaduMeiFilter());
            add(new ThanikkurrilOttuFilter());
            add(new NannoolHandler183Filter());
            add(new NannolHandler227Filter());


        }
    };

    @Override
    public TokenMatcherResult match(ParsingContext context) {


        List<IKnownWord> list = null;
        boolean onEagerParsing = false;
        if (context.nilaimozhi.size() > 0) {

            EagerlyParsingFeature eager = context.set.getFeature(EagerlyParsingFeature.class);
            if (eager != null) {
                list = context.dictionary.search(context.varumozhi.getWord(), 1, null, StartsWithHigherLengthSearch.FEATURE, ReverseSearchFeature.FEATURE);
                if (list.isEmpty()) {
                    onEagerParsing = false;

                } else {
                    // causes to continue without returning something.
                    list.clear();
                    onEagerParsing = true;
                }
            }
        } else {
            EagerlyParsingFeature eager = context.set.getFeature(EagerlyParsingFeature.class);
            if (eager != null) {
                context.set.removeFeature(EagerlyParsingFeature.class);
            }
        }
        if (!onEagerParsing) {
            list = context.dictionary.lookup(context.varumozhi.getWord());
        }

      //  if (list.isEmpty()) {

            if (!noNonWordLookup) {
                for (UnknownWordFilter unKnownWordFilter : filtersUnknown) {
                    List<IKnownWord> knownWords = unKnownWordFilter.filterUnknown(context);
                    if (!knownWords.isEmpty()) {
                        list.addAll(knownWords);
                    }
                }
            }

            if (list.isEmpty()) {
                return TokenMatcherResult.Continue();
            }
      //  }

        Set<IKnownWord> filteredList = new java.util.HashSet<IKnownWord>();
        for (IKnownWord known : list) {
            if (Aththu.class.isAssignableFrom(known.getClass())) {
                continue;
            } else if (INonStartingIdaichchol.class.isAssignableFrom(known.getClass())) {
                if (context.nilaimozhi.getWord().isEmpty()) {
                    continue;
                }
                if (Ottu.class.isAssignableFrom(known.getClass())) {
                    continue;
                }

            }


//            else if (PeyarchCholThiribu.class.isAssignableFrom(known.getClass())) {
//                if (nilaimozhi.size() !=0  || !tail.isEmpty()) {
//                    continue;
//                }
//            }


            if (!context.tail.isEmpty()) {
                IKnownWord next = context.tail.get(0);
//                //Todo: remove அத்து
//                if ("அத்து".equals(next.getWord().toString())) {
//                    if (known.getWord().getLast() != TamilCompoundCharacter.IM) {
//                        continue;
//                    }
//                }
                if (isAllJustOfKind(IKaddalhai.class, next, known)) {
                    continue;
                }
                if (context.tail.size() > 1) {
                    IKnownWord nextnext = context.tail.get(1);
                    if (isAllJustOfExactKind(NonStartingIdaichchol.class, nextnext, next, known)) {
                        continue;
                    }
                }

            }


            Set<IKnownWord> filteredListForThisKnow = new java.util.HashSet<IKnownWord>();
            if (context.dictionary == NumberDictionary.INSTANCE) {
                filteredListForThisKnow.add(known);
            } else {

                for (KnowWordFilter KnownWordFilter : filters) {
                    List<IKnownWord> knownWords = KnownWordFilter.filter(known, context);
                    if (knownWords.isEmpty()) {
                        filteredListForThisKnow.clear();
                        break;
                    } else {
                        filteredListForThisKnow.addAll(knownWords);
                    }


                }
            }
            filteredList.addAll(filteredListForThisKnow);


        }


        if (filteredList.isEmpty()) {
            return TokenMatcherResult.Continue();
        } else {
            List<TamilWordPartContainer> listnilaimozhi = getNilaiMozhi(context);
            return TokenMatcherResult.Matching(listnilaimozhi, new ArrayList<IKnownWord>(filteredList));
        }

    }


}
