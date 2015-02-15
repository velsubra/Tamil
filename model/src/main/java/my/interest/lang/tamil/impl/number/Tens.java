package my.interest.lang.tamil.impl.number;

import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class Tens extends AbstractPlace {
    public Tens(int size) {
        super(size);
    }

    @Override
    public TamilWordPartContainer read(AbstractPlace hs, AbstractPlace ones, TamilWordPartContainer next, AbstractPlace valueExistingPlace) {

        TamilWordPartContainer word = null;
        switch (size) {
            case 0:
                word = new TamilWordPartContainer(new TamilWord());
                break;
            case 1:

                switch (ones.size) {
                    case 9:
                    case 0:
                        word = new TamilWordPartContainer(TamilWord.from("பத்து"));
                        break;

                    case 1:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        word = new TamilWordPartContainer(TamilWord.from("பதின்"));
                        break;

                    case 2:
                        word = new TamilWordPartContainer(TamilWord.from("பன்"));
                        break;
                    default:     //3,4
                        word = new TamilWordPartContainer(TamilWord.from("பதி"));
                        break;
                }


                break;

            case 2:
                if (ones.size == 0) {
                    word = new TamilWordPartContainer(TamilWord.from("இருபது"));
                } else {
                    word = new TamilWordPartContainer(TamilWord.from("இருபத்து"));
                }
                break;

            case 3:
                if (ones.size == 0) {
                    word = new TamilWordPartContainer(TamilWord.from("முப்பது"));
                } else {
                    word = new TamilWordPartContainer(TamilWord.from("முப்பத்து"));
                }
                break;


            case 4:
                if (ones.size == 0) {
                    word = new TamilWordPartContainer(TamilWord.from("நாற்பது"));
                } else {
                    word = new TamilWordPartContainer(TamilWord.from("நாற்பத்து"));
                }
                break;



            case 5:
                if (ones.size == 0) {
                    word = new TamilWordPartContainer(TamilWord.from("ஐம்பது"));
                } else {
                    word = new TamilWordPartContainer(TamilWord.from("ஐம்பத்து"));
                }
                break;



            case 6:
                if (ones.size == 0) {
                    word = new TamilWordPartContainer(TamilWord.from("அறுபது"));
                } else {
                    word = new TamilWordPartContainer(TamilWord.from("அறுபத்து"));
                }
                break;

            case 7:
                if (ones.size == 0) {
                    word = new TamilWordPartContainer(TamilWord.from("எழுபது"));
                } else {
                    word = new TamilWordPartContainer(TamilWord.from("எழுபத்து"));
                }
                break;


            case 8:
                if (ones.size == 0) {
                    word = new TamilWordPartContainer(TamilWord.from("எண்பது"));
                } else {
                    word = new TamilWordPartContainer(TamilWord.from("எண்பத்து"));
                }
                break;


            case 9:
                if (ones.size == 0) {
                    word = new TamilWordPartContainer(TamilWord.from("தொண்ணூறு"));
                } else {
                    word = new TamilWordPartContainer(TamilWord.from("தொண்ணூற்று"));
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
