package my.interest.lang.tamil.impl.number;

import common.lang.impl.UnknownCharacter;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilWord;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class Hundreds extends AbstractPlace {
    public Hundreds(int size) {
        super(size);
    }

    @Override
    public TamilWordPartContainer read(AbstractPlace thousands, AbstractPlace tens, TamilWordPartContainer next, AbstractPlace valueExistingPlace) {

        TamilWordPartContainer word = null;
        switch (size) {
            case 0:
                word = new TamilWordPartContainer(new TamilWord());
                break;


            case 1:

                if (valueExistingPlace != null) {
                    word = new TamilWordPartContainer(TamilWord.from("நூற்று"));

                } else {
                    word = new TamilWordPartContainer(TamilWord.from("நூறு"));
                }
                break;


            case 2:

                if (valueExistingPlace != null) {
                    word = new TamilWordPartContainer(TamilWord.from("இருநூற்று"));

                } else {
                    word = new TamilWordPartContainer(TamilWord.from("இருநூறு"));
                }
                break;


            case 3:

                if (valueExistingPlace != null) {
                    word = new TamilWordPartContainer(TamilWord.from("முந்நூற்று"));

                } else {
                    word = new TamilWordPartContainer(TamilWord.from("முந்நூறு"));
                }
                break;

            case 4:

                if (valueExistingPlace != null) {
                    word = new TamilWordPartContainer(TamilWord.from("நானூற்று"));

                } else {
                    word = new TamilWordPartContainer(TamilWord.from("நானூறு"));
                }
                break;

            case 5:

                if (valueExistingPlace != null) {
                    word = new TamilWordPartContainer(TamilWord.from("ஐந்நூற்று"));

                } else {
                    word = new TamilWordPartContainer(TamilWord.from("ஐநூறு"));
                }
                break;

            case 6:

                if (valueExistingPlace != null) {
                    word = new TamilWordPartContainer(TamilWord.from("அறுநூற்று"));

                } else {
                    word = new TamilWordPartContainer(TamilWord.from("அறுநூறு"));
                }
                break;

            case 7:

                if (valueExistingPlace != null) {
                    word = new TamilWordPartContainer(TamilWord.from("எழுநூற்று"));

                } else {
                    word = new TamilWordPartContainer(TamilWord.from("எழுநூறு"));
                }
                break;

            case 8:

                if (valueExistingPlace != null) {
                    word = new TamilWordPartContainer(TamilWord.from("எண்ணூற்று"));

                } else {
                    word = new TamilWordPartContainer(TamilWord.from("எண்ணூறு"));
                }
                break;
            case 9:

                if (valueExistingPlace != null) {
                    word = new TamilWordPartContainer(TamilWord.from("தொள்ளாயிரத்து"));

                } else {
                    word = new TamilWordPartContainer(TamilWord.from("தொள்ளாயிரம்"));
                }
                break;


            default:
                throw new RuntimeException("Invalid number model built");
        }

        if (word.isEndingWithTwoConsonantsOfTHARRA()) {
            if (valueExistingPlace != null) {
                TamilCharacter ch = null;
                if (next != null && next.size() > 0) {
                    if (next.isStartingWithOneConsonantsOfKASATHABA()) {
                        ch = next.getWord().get(0).asTamilCharacter().getMeiPart();
                    }
                }
                TamilWord val = word.getWord();
                if (ch != null) {

                    val.add(ch);
                } else {
                    val.add(new UnknownCharacter(' '));
                }
                word = new TamilWordPartContainer(val);

            }
        }

        if (word == null) {
            throw new RuntimeException("Certain case is not handled!");
        }
        return word;
    }
}
