package tamil.lang.known.derived.inhaippu;

import my.interest.lang.tamil.generated.types.SimpleTense;
import tamil.lang.TamilWord;
import tamil.lang.known.AbstractVinaiyadiPeyarechcham;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.derived.Peyarechcham;
import tamil.lang.known.derived.VinaiyadiDerivative;
import tamil.lang.known.non.derived.IInhaippu;
import tamil.lang.known.non.derived.Vinaiyadi;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class VinaiIf extends VinaiyadiDerivative implements IInhaippu {
    public static final TamilWord TYPE = TamilWord.from("வினை-ஆல்");
    List<Class<? extends IKnownWord>>  peyarcham = new ArrayList<Class<? extends IKnownWord>>() {
        {
            add(Peyarechcham.class);
        }
    };
    public VinaiIf(TamilWord word, Vinaiyadi vinaiyadi) {

        super(word, vinaiyadi);
        this.type = TYPE;
        List<IKnownWord> peyarechchams = vinaiyadi.getRelatedDictionary().search(new TamilWord(),false,10, peyarcham);
        for (IKnownWord know : peyarechchams) {
            Peyarechcham  ech = (Peyarechcham)know;
            if (ech.getTense() == SimpleTense.PAST) {
                TamilWord twoletters =  ech.getWord().subWord(0,2);
                if (!word.startsWith(twoletters)) {
                    System.out.println("------------------->" + word + " :" + ech.toString());
                }
            }
        }


    }
}
