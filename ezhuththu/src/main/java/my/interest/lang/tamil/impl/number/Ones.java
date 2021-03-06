package my.interest.lang.tamil.impl.number;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class Ones extends AbstractPlace {
    public Ones(int size) {
        super(size);
    }


    @Override
    public TamilWordPartContainer read(AbstractPlace tens, AbstractPlace nextPlace, TamilWordPartContainer next, AbstractPlace valueExistingPlace, FeatureSet set) {

        TamilWordPartContainer word = null;
        switch (size) {
            case 0:
                if (tens == null) {
                    word = new TamilWordPartContainer(TamilWord.from("சுழி"));
                } else {
                    word = new TamilWordPartContainer(new TamilWord());
                }

                break;
            case 1:
                if (next.size() > 0) {
                    if (next.isStartingWithUyir()) {
                        word = new TamilWordPartContainer(TamilWord.from("ஓர் ", !set.isNumberPurchchiFeaturePosition()));
                    } else {

                        word = new TamilWordPartContainer(TamilWord.from("ஒரு ", !set.isNumberPurchchiFeaturePosition()));
                    }

                } else {
                    word = new TamilWordPartContainer(TamilWord.from("ஒன்று"));
                }
                break;

            case 2:
                if (next.size() > 0) {
                    if (next.isStartingWithUyir() && tens == null) {
                        word = new TamilWordPartContainer(TamilWord.from("ஈர் ", !set.isNumberPurchchiFeaturePosition()));
                    } else {
                        word = new TamilWordPartContainer(TamilWord.from("இரண்டு", !set.isNumberPurchchiFeaturePosition()));
                    }

                } else {
                    word = new TamilWordPartContainer(TamilWord.from("இரண்டு", true));
                }
                break;

            case 3:
                if (next.size() > 0) {
                    if (set.isNumberPurchchiFeaturePosition() && next.isStartingWithUyir() && next.getWord().get(0).equals(TamilSimpleCharacter.aa)) {
                        word = new TamilWordPartContainer(TamilWord.from("மூ"));
                    } else {
                        word = new TamilWordPartContainer(TamilWord.from("மூன்று", !set.isNumberPurchchiFeaturePosition()));
                    }

                } else {
                    word = new TamilWordPartContainer(TamilWord.from("மூன்று"));
                }
                break;

            case 4:
                if (next.size() > 0) {

                    word = new TamilWordPartContainer(TamilWord.from("நான்கு", !set.isNumberPurchchiFeaturePosition()));

                } else {
                    word = new TamilWordPartContainer(TamilWord.from("நான்கு"));
                }
                break;
            case 5:

                if (next.size() > 0) {
                    if (set.isNumberPurchchiFeaturePosition() && next.isStartingWithUyir() && next.getWord().get(0).equals(TamilSimpleCharacter.aa)) {
                        word = new TamilWordPartContainer(TamilWord.from("ஐ"));
                    } else {
                        word = new TamilWordPartContainer(TamilWord.from("ஐந்து", !set.isNumberPurchchiFeaturePosition()));
                    }

                } else {
                    word = new TamilWordPartContainer(TamilWord.from("ஐந்து", true));
                }
                break;

            case 6:

                if (next.size() > 0) {
                    word = new TamilWordPartContainer(TamilWord.from("ஆறு", !set.isNumberPurchchiFeaturePosition()));
                } else {
                    word = new TamilWordPartContainer(TamilWord.from("ஆறு", false));
                }


                break;

            case 7:

                if (next.size() > 0) {
                    word = new TamilWordPartContainer(TamilWord.from("ஏழு", !set.isNumberPurchchiFeaturePosition()));
                } else {
                    word = new TamilWordPartContainer(TamilWord.from("ஏழு", false));
                }


                break;

            case 8:

                if (next.size() > 0) {

                    word = new TamilWordPartContainer(TamilWord.from("எட்டு", !set.isNumberPurchchiFeaturePosition()));


                } else {
                    word = new TamilWordPartContainer(TamilWord.from("எட்டு", false));
                }
                break;

            case 9:
                if (next.size() > 0) {
                    word = new TamilWordPartContainer(TamilWord.from("ஒன்பது", !set.isNumberPurchchiFeaturePosition()));
                } else {
                    word = new TamilWordPartContainer(TamilWord.from("ஒன்பது", false));
                }

                break;


            default:
                throw new RuntimeException("Invalid number model built");
        }
        if (word == null) {
            throw new RuntimeException("Certain case is not handled!");
        }
        return word;
    }
}
