package my.interest.lang.tamil.multi;

import tamil.lang.TamilWord;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.generated.types.IdaichcholDescription;
import tamil.lang.known.non.derived.NonEndingIdaichChol;
import tamil.lang.known.non.derived.NonStartingIdaichchol;
import my.interest.lang.tamil.punar.PropertyDescriptionContainer;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class WordGeneratorFromIdai extends WordsGenerator {

    IdaichcholDescription idai = null;
    PersistenceInterface per = null;

    public WordGeneratorFromIdai(IdaichcholDescription idai, PersistenceInterface per) {
        this.idai = idai;
        this.per = per;
    }

    @Override
    public void run() {

        try {

            TamilWord id = TamilWord.from(idai.getRoot());
            PropertyDescriptionContainer cont = per.getConsolidatedPropertyContainerFor(idai);
            if (cont.isChuttuIdaichchol()) {
                NonEndingIdaichChol chol = new NonEndingIdaichChol(id, cont.isAtomicIdaichchol());
                PersistenceInterface.addOrUpdateKnown(chol);
            } else {
                NonStartingIdaichchol chol = new NonStartingIdaichchol(id, cont.isAtomicIdaichchol());
                PersistenceInterface.addOrUpdateKnown(chol);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
