package my.interest.lang.tamil.punar.handler.nannool;

import common.lang.impl.AbstractCharacter;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunharchiHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>  இயல்பினும் விதியினும் நின்ற உயிர்முன்
 * க ச த ப மிகும் விதவாதன மன்னே.           (நன்னூல் - 165)
 * <p/>
 * <p/>
 * </p>
 *
 * @author velsubra
 */
public class NannoolHandler165 extends AbstractPunharchiHandler {


    public static NannoolHandler165 HANDLER = new NannoolHandler165();

    @Override
    public String getName() {
        return "நன்னூல்விதி-165 - க ச த ப மிகும்;(பொது)";
    }

    @Override
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilaiPart, TamilWordPartContainer varum) {
        List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();
        if (!varum.isStartingWithTwoConsonantsOfKASATHABA()) return null;
        if (!varum.isStartingWithMei()) {
            return null;
        }

        //if (!nilaiPart.isEndingWithUyirMei()) return null;
        if (nilaiPart.isKutriyaLugaram()) {
            AbstractCharacter lastbutone = nilaiPart.getWord().get(nilaiPart.size() - 1);
            if (!lastbutone.isPureTamilLetter()) {
                return null;
            }

            if (!lastbutone.asTamilCharacter().isVallinam()) return null;

        }

        TamilWordSplitResult split = new TamilWordSplitResult();
        split.add(nilaiPart);



        TamilWord varumPart = new TamilWord();
        varumPart.addAll(varum.getWord().subList(1, varum.getWord().size()));
        TamilWord kasathapa = new TamilWord();
        kasathapa.add(varum.getWord().getFirst());
        split.add(new TamilWordPartContainer(kasathapa));
        split.add(new TamilWordPartContainer(varumPart));

        list.add(split);


        return list;
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        //if (!nilai.isEndingWithUyirMei()) return null;
        if (!varum.isStartingWithOneConsonantsOfKASATHABA()) return null;
        if (nilai.isKutriyaLugaram()) {
            AbstractCharacter lastbutone = nilai.getWord().get(nilai.size() - 2);
            if (!lastbutone.isPureTamilLetter()) {
                return null;
            }

            if (!lastbutone.asTamilCharacter().isVallinam()) return null;

        }

        TamilWord word = nilai.getWord().duplicate();
        word.add(varum.getWord().get(0).asTamilCharacter().getMeiPart());
        word.addAll(varum.getWord());
        return new TamilWordPartContainer(word);

    }
}
