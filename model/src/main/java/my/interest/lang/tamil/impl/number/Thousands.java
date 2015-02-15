package my.interest.lang.tamil.impl.number;

import common.lang.impl.UnknownCharacter;
import my.interest.lang.tamil.impl.DefaultNumberReader;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilWord;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class Thousands extends AbstractPlace {
    public Thousands(int size) {
        super(size);
    }


    @Override
    public TamilWordPartContainer read(AbstractPlace tens, AbstractPlace nextPlace, TamilWordPartContainer next, AbstractPlace valueExistingPlace) {

        switch (size) {
            case 0:
                return new TamilWordPartContainer(new TamilWord());

            default:
                TamilWord val = null;
                if (valueExistingPlace == null) {
                    val = TamilWord.from("ஆயிரம்");
                } else {
                    val = TamilWord.from("ஆயிரத்து");
                    TamilCharacter ch = null;
                    if (next != null && next.size() > 0) {
                        if (next.isStartingWithOneConsonantsOfKASATHABA()) {
                            ch = next.getWord().get(0).asTamilCharacter().getMeiPart();
                        }
                    }
                    if (ch != null) {
                        val.add(ch);
                    } else {
                        val.add(new UnknownCharacter(' '));
                    }
                }


                return new TamilWordPartContainer(DefaultNumberReader.reader.readNumber(size, val));
        }
    }
}
