package my.interest.lang.tamil.parser.impl.sax;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.parser.impl.sax.filter.*;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilFactory;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.*;

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

    public AnyKnownWordMatcher(boolean noNonWordLookup) {
        super();
        this.noNonWordLookup = noNonWordLookup;
    }

    private boolean noNonWordLookup = false;

    private static List<KnowWordFilter> filters = new ArrayList<KnowWordFilter>() {
        {
            add(new MagaramFilter());
            add(new ChuttuppeyarFilter());
            add(new PanhpuththogaiThiribuFilter());
            add(new VUrubuFilter());


        }
    };


    private static List<UnknownWordFilter> filtersUnknown = new ArrayList<UnknownWordFilter>() {
        {
            add(new MagaramFilter());
            add(new UdambaduMeiFilter());
            add(new ThanikkurrilOttuFilter());
            add(new NannoolHandler183Filter());
        }
    };

    @Override
    public TokenMatcherResult match(TamilWordPartContainer nilaimozhi, TamilWordPartContainer varumozhi, List<IKnownWord> tail, TamilDictionary dictionary, FeatureSet set) {
        List<IKnownWord> list = dictionary.lookup(varumozhi.getWord());


        if (list.isEmpty()) {
            Set<IKnownWord> filteredList = new java.util.HashSet<IKnownWord>();
            if (!noNonWordLookup) {
                for (UnknownWordFilter unKnownWordFilter : filtersUnknown) {
                    List<IKnownWord> knownWords = unKnownWordFilter.filterUnknown(nilaimozhi, varumozhi, tail,dictionary, set);
                    if (!knownWords.isEmpty()) {
                        filteredList.addAll(knownWords);
                    }
                }
            }

            if (filteredList.isEmpty()) {
                return TokenMatcherResult.Continue();
            } else {
                List<TamilWordPartContainer> listnilaimozhi = getNilaiMozhi(varumozhi, nilaimozhi);
                return TokenMatcherResult.Matching(listnilaimozhi, new ArrayList<IKnownWord>(filteredList));
            }
        } else {
            Set<IKnownWord> filteredList = new java.util.HashSet<IKnownWord>();
            for (IKnownWord known : list) {
                if (!tail.isEmpty()) {
                    IKnownWord next = tail.get(0);
                    if (isAllJustOfKind(IKaddalhai.class, next, known)) {
                        continue;
                    }
                    if (tail.size() > 1) {
                        IKnownWord nextnext = tail.get(1);
                        if (isAllJustOfKind(IIdaichchol.class, nextnext, next, known)) {
                            continue;
                        }
                    }

                }
                if (Aththu.class.isAssignableFrom(known.getClass())) {
                    continue;
                } else if (Ottu.class.isAssignableFrom(known.getClass())) {
                    continue;
                } else if (INonStartingIdaichchol.class.isAssignableFrom(known.getClass())) {
                    if (nilaimozhi.getWord().isEmpty()) {
                        continue;
                    }
                }


                Set<IKnownWord> filteredListForThisKnow = new java.util.HashSet<IKnownWord>();
                for (KnowWordFilter KnownWordFilter : filters) {
                    List<IKnownWord> knownWords = KnownWordFilter.filter(known, nilaimozhi, varumozhi, tail,dictionary,set);
                    if (knownWords.isEmpty()) {
                        filteredListForThisKnow.clear();
                        break;
                    } else {
                        filteredListForThisKnow.addAll(knownWords);
                    }


                }
                filteredList.addAll(filteredListForThisKnow);


            }


            if (filteredList.isEmpty()) {
                return TokenMatcherResult.Continue();
            } else {
                List<TamilWordPartContainer> listnilaimozhi = getNilaiMozhi(varumozhi, nilaimozhi);
                return TokenMatcherResult.Matching(listnilaimozhi, new ArrayList<IKnownWord>(filteredList));
            }
        }
    }


}
