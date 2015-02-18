package my.interest.lang.tamil.impl.number;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.handler.VinaiMutruCreationHandler;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.join.WordsJoiner;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public abstract class AbstractPlace {

    public AbstractPlace(int size) {
        this.size = size;

    }

    protected int size;


    public abstract TamilWordPartContainer read(AbstractPlace prevPlace, AbstractPlace nextPlace, TamilWordPartContainer next, AbstractPlace valueExistingPlace, FeatureSet set);


    public static TamilWordPartContainer sumUp(List<AbstractPlace> list, TamilWordPartContainer tail , FeatureSet set) {
        TamilWordPartContainer sum = tail == null? new TamilWordPartContainer(new TamilWord()): tail;
        if (list == null) {
            return sum;
        }

        AbstractPlace next = null;
        AbstractPlace prev = null;
        AbstractPlace valueExistingPlace = null;
        for (int i = list.size() - 1; i >= 0; i--) {
            if (i > 0) {
                prev = list.get(i - 1);
            }
            WordsJoiner h = TamilFactory.createWordJoiner(list.get(i).read(prev, next, sum, valueExistingPlace, set).getWord());
            h.addVaruMozhi(sum.getWord());
            sum = new TamilWordPartContainer(h.getSum());
            next = list.get(i);
            if (next.size > 0) {
                valueExistingPlace = next;
            }

        }
        return sum;
    }


}
