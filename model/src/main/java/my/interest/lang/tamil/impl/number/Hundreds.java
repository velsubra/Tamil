package my.interest.lang.tamil.impl.number;

import common.lang.impl.AbstractCharacter;
import common.lang.impl.UnknownCharacter;
import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
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
    public TamilWordPartContainer read(AbstractPlace thousands, AbstractPlace tens, TamilWordPartContainer next, AbstractPlace valueExistingPlace, FeatureSet set) {

        TamilWordPartContainer word = null;
        switch (size) {
            case 0:
                word = new TamilWordPartContainer(new TamilWord());
                break;


            case 1:

                if (valueExistingPlace != null) {
                    word = new TamilWordPartContainer(TamilWord.from("நூற்று"));

                } else {
//                    if (next.size() > 0) {
//                        word = new TamilWordPartContainer(TamilWord.from("நூறு ",  !set.isNumberPurchchiFeaturePosition()));
//                    }  else {
                        word = new TamilWordPartContainer(TamilWord.from("நூறு"));
                   // }

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
        AbstractCharacter chToAdd = null;
        //If there is more to continue
        if (word.isEndingWithTwoConsonantsOfTHARRA()) {

            if (!set.isNumberPurchchiFeatureFull()) {
                chToAdd = UnknownCharacter.SPACE;
            } else {
               // if (next != null && next.size() > 0) {     should have something! at this point
                    if (next.isStartingWithOneConsonantsOfKASATHABA()) {
                        chToAdd = next.getWord().get(0).asTamilCharacter().getMeiPart();
                    }
               // }
            }
        }

        if (chToAdd != null) {
            TamilWord val = word.getWord();
            val.add(chToAdd);
            word = new TamilWordPartContainer(val);
        }



        if (word == null) {
            throw new RuntimeException("Certain case is not handled!");
        }
        return word;
    }
}
