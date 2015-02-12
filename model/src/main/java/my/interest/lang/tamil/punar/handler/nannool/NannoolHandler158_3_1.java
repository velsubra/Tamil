package my.interest.lang.tamil.punar.handler.nannool;

import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilWord;
import tamil.lang.TamilSimpleCharacter;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunarchiHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * <p/>
 * மண் + நீண்டது
 * = மண்ணீண்டது
 * முள் + நீண்டது
 * = முண்ணீண்டது
 * </p>
 *
 * @author velsubra
 */
public class NannoolHandler158_3_1 extends AbstractPunarchiHandler {
    @Override
    public String getName() {
        return "நன்னூல்விதி-158_3_1";
    }

    //
    //  மண் + நீண்டது= மண்ணீண்டது
    //  முள் + நீண்டது= முண்ணீண்டது
    @Override
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilai, TamilWordPartContainer varumPart) {
        List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();



        if (!varumPart.isStartingWithTwoConsonantsOfType(TamilCompoundCharacter.INNN)) return null;
        if (!varumPart.isStartingWithMeiFollowedByUyirMei()) return null;


        TamilWord nilaiLL = nilai.getWord().duplicate();
        nilaiLL.add(TamilCompoundCharacter.ILL);
        TamilWord varumLL = varumPart.getWord().duplicate();
        varumLL.remove();
        varumLL.add(0, TamilCompoundCharacter.INTH.addUyir(varumLL.remove().asTamilCharacter().getUyirPart()));

        TamilWord nilaiNNN = nilai.getWord().duplicate();
        nilaiNNN.add(TamilCompoundCharacter.INNN);
        TamilWord varumNNN = varumPart.getWord().duplicate();
        varumNNN.remove();
        varumNNN.add(0, TamilCompoundCharacter.INTH.addUyir(varumNNN.remove().asTamilCharacter().getUyirPart()));


        TamilWordSplitResult split1 = new TamilWordSplitResult();
        split1.add(new TamilWordPartContainer(nilaiNNN));
        split1.add(new TamilWordPartContainer(varumNNN));
        list.add(split1);

        TamilWordSplitResult split2 = new TamilWordSplitResult();
        split2.add(new TamilWordPartContainer(nilaiLL));
        split2.add(new TamilWordPartContainer(varumLL));
        list.add(split2);


        return list;
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        if (!varum.getWord().getFirst().isPureTamilLetter() || !varum.getWord().getFirst().asTamilCharacter().isUyirMeyyezhuththu())
            return null;
        if (!nilai.getWord().getLast().toString().equals(TamilCompoundCharacter.INNN.toString()) && !nilai.getWord().getLast().toString().equals(TamilCompoundCharacter.ILL.toString()))
            return null;
        if (!varum.getWord().getFirst().getConsonantDigest().equals(TamilSimpleCharacter.DIGEST_CONSONANT_TYPE._NTHA_.toString())) {
            return null;
        }
        TamilWord ret = nilai.getWord().duplicate();
        ret.removeLast();
        ret.add(TamilCompoundCharacter.INNN); //);new TamilCompoundCharacter(TamilSimpleCharacter.NNNA.getValue(), TamilCompoundCharacter.PULLI));
        ret.add(TamilCompoundCharacter.INNN.addUyir(varum.getWord().getFirst().asTamilCharacter().getUyirPart()));
        ret.addAll(varum.getWord().subList(1, varum.getWord().size()));
        return new TamilWordPartContainer(ret);
    }
}