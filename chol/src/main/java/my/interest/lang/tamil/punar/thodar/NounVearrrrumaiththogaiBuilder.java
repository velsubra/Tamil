package my.interest.lang.tamil.punar.thodar;

import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.Aththu;
import tamil.lang.known.non.derived.IPeyarchchol;
import tamil.lang.known.non.derived.Ottu;
import my.interest.lang.tamil.punar.AbstractThodarmozhiBuilder;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.ThodarMozhiBuilder;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class NounVearrrrumaiththogaiBuilder extends AbstractThodarmozhiBuilder {
    public NounVearrrrumaiththogaiBuilder() {

    }


    protected NounVearrrrumaiththogaiBuilder(NounVearrrrumaiththogaiBuilder from) {
        super(from);

    }

    /**
     * @param known
     * @return
     */
    @Override
    public ThodarMozhiBuilder accept(IKnownWord known , boolean clone) {
       // System.out.println(this +":" + known.getWord());
        if (!IPeyarchchol.class.isAssignableFrom(known.getClass()) && (!Ottu.class.isAssignableFrom(known.getClass())) && (!Aththu.class.isAssignableFrom(known.getClass()))) {
            return null;
        }

        if (list.isEmpty()) {
            if (!IPeyarchchol.class.isAssignableFrom(known.getClass())) {
                return null;
            } else {
                NounVearrrrumaiththogaiBuilder ret =clone? (NounVearrrrumaiththogaiBuilder) cloneContext() :this;
                ret.list.add(known);
                ret.atLogicalConclusion = false;
                return ret;
            }
        } else {

            IKnownWord last = list.get(list.size() - 1);
            if (Ottu.class.isAssignableFrom(last.getClass())) {  // last is ottu.
                if (Ottu.class.isAssignableFrom(known.getClass())) {
                    return null;
                } else {

                    if (new TamilWordPartContainer(known.getWord()).isStartingWithOneConsonantOfType(last.getWord().getLast().asTamilCharacter().getMeiPart())) {
                        NounVearrrrumaiththogaiBuilder ret = clone? (NounVearrrrumaiththogaiBuilder) cloneContext() : this;
                        ret.list.add(known);
                        ret.atLogicalConclusion = true;
                        return ret;
                    } else {
                        return null;
                    }
                }

            } else {    // last is not ottu
                if (Ottu.class.isAssignableFrom(known.getClass())) {
                    NounVearrrrumaiththogaiBuilder ret = clone? (NounVearrrrumaiththogaiBuilder) cloneContext() : this;
                    ret.list.add(known);
                    ret.atLogicalConclusion = false;
                    return ret;
                } else { //new noun
                    if (!new TamilWordPartContainer(last.getWord()).isEndingWithSomethingWhichCanBeFollowedByOttu() && new TamilWordPartContainer(known.getWord()).isStartingWithOneConsonantsOfKASATHABA()) {
                        // no ottu
                        return null;
                    } else {
                        NounVearrrrumaiththogaiBuilder ret = clone? (NounVearrrrumaiththogaiBuilder) cloneContext() : this;
                        ret.list.add(known);
                        ret.atLogicalConclusion = true;
                        return ret;
                    }
                }
            }

        }

    }


    @Override
    public ThodarMozhiBuilder cloneContext() {
        return new NounVearrrrumaiththogaiBuilder(this);
    }
}
