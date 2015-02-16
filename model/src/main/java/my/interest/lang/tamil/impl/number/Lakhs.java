package my.interest.lang.tamil.impl.number;

import common.lang.impl.AbstractCharacter;
import common.lang.impl.UnknownCharacter;
import my.interest.lang.tamil.impl.DefaultNumberReader;
import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilWord;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class Lakhs extends AbstractPlace {
    public Lakhs(int size) {
        super(size);
    }


    @Override
    public TamilWordPartContainer read(AbstractPlace prev, AbstractPlace nextPlace, TamilWordPartContainer next, AbstractPlace valueExistingPlace, FeatureSet set) {

        switch (size) {
            case 0:
                if (next.size() == 0) {
                    return new TamilWordPartContainer(TamilWord.from("சுழி"));
                } else {
                    return new TamilWordPartContainer(new TamilWord());
                }


            default:

                TamilWord val = null;
                if (valueExistingPlace == null) {
                    val = TamilWord.from("இலட்சம்");
                } else {
                    val = TamilWord.from("இலட்சத்து");

                    AbstractCharacter ch = null;
                    if (set.isNumberPurchchiFeatureFull()) {
                        // if (next != null && next.size() > 0) {
                        if (next.isStartingWithOneConsonantsOfKASATHABA()) {
                            ch = next.getWord().get(0).asTamilCharacter().getMeiPart();
                        }
                        // }
                    } else {
                        ch = UnknownCharacter.SPACE;
                    }
                    if (ch != null) {
                        val.add(ch);
                    }
                }

                return new TamilWordPartContainer(DefaultNumberReader.reader.readNumber(size, val, set));
        }
    }
}
