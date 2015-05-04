package my.interest.lang.tamil.impl;

import my.interest.lang.tamil.impl.dictionary.DefaultPlatformDictionaryBase;
import my.interest.lang.tamil.impl.number.known.KnownNumberComponent;
import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class NumberDictionary extends DefaultPlatformDictionaryBase {
    public static final KnownNumberComponent ORU = new KnownNumberComponent(1, 1, TamilWord.from("ஒரு"));




    public static final NumberDictionary INSTANCE = new NumberDictionary();

    public void addKnown(IKnownWord word) {
       throw new RuntimeException("Nothing should be added."); 
    }

    private void addKnownPrivate(IKnownWord word) {
          super.addKnown(word);
    }


    private NumberDictionary() {


        addKnownPrivate(new KnownNumberComponent(0, 0, TamilWord.from("புள்ளி")));
        addKnownPrivate(new KnownNumberComponent(1, 0, TamilWord.from("சுழி")));
        addKnownPrivate(new KnownNumberComponent(1, 0, TamilWord.from("பூச்சியம்")));
        addKnownPrivate(new KnownNumberComponent(1, 1, TamilWord.from("ஓர்")));
        addKnownPrivate(ORU);
        addKnownPrivate(new KnownNumberComponent(1, 1, TamilWord.from("ஒன்று")));
        addKnownPrivate(new KnownNumberComponent(1, 2, TamilWord.from("ஈர்")));
        addKnownPrivate(new KnownNumberComponent(1, 2, TamilWord.from("இரண்டு")));
        addKnownPrivate(new KnownNumberComponent(1, 3, TamilWord.from("மூ")));
        addKnownPrivate(new KnownNumberComponent(1, 5, TamilWord.from("ஐ")));
        addKnownPrivate(new KnownNumberComponent(1, 3, TamilWord.from("மூன்று")));
        addKnownPrivate(new KnownNumberComponent(1, 4, TamilWord.from("நான்கு")));
        addKnownPrivate(new KnownNumberComponent(1, 5, TamilWord.from("ஐந்து")));
        addKnownPrivate(new KnownNumberComponent(1, 6, TamilWord.from("ஆறு")));
        addKnownPrivate(new KnownNumberComponent(1, 7, TamilWord.from("ஏழு")));
        addKnownPrivate(new KnownNumberComponent(1, 8, TamilWord.from("எட்டு")));
        addKnownPrivate(new KnownNumberComponent(1, 9, TamilWord.from("ஒன்பது")));
        addKnownPrivate(new KnownNumberComponent(10, 1, TamilWord.from("பத்து")));
        addKnownPrivate(new KnownNumberComponent(10, 1, TamilWord.from("பதின்")));
        addKnownPrivate(new KnownNumberComponent(10, 1, TamilWord.from("பன்")));
        addKnownPrivate(new KnownNumberComponent(10, 1, TamilWord.from("பதி")));
        addKnownPrivate(new KnownNumberComponent(10, 2, TamilWord.from("இருபது")));
        addKnownPrivate(new KnownNumberComponent(10, 2, TamilWord.from("இருபத்து")));
        addKnownPrivate(new KnownNumberComponent(10, 3, TamilWord.from("முப்பது")));
        addKnownPrivate(new KnownNumberComponent(10, 3, TamilWord.from("முப்பத்து")));
        addKnownPrivate(new KnownNumberComponent(10, 4, TamilWord.from("நாற்பது")));
        addKnownPrivate(new KnownNumberComponent(10, 4, TamilWord.from("நாற்பத்து")));
        addKnownPrivate(new KnownNumberComponent(10, 5, TamilWord.from("ஐம்பது")));
        addKnownPrivate(new KnownNumberComponent(10, 5, TamilWord.from("ஐம்பத்து")));
        addKnownPrivate(new KnownNumberComponent(10, 6, TamilWord.from("அறுபது")));
        addKnownPrivate(new KnownNumberComponent(10, 6, TamilWord.from("அறுபத்து")));
        addKnownPrivate(new KnownNumberComponent(10, 7, TamilWord.from("எழுபது")));
        addKnownPrivate(new KnownNumberComponent(10, 7, TamilWord.from("எழுபத்து")));
        addKnownPrivate(new KnownNumberComponent(10, 8, TamilWord.from("எண்பது")));
        addKnownPrivate(new KnownNumberComponent(10, 8, TamilWord.from("எண்பத்து")));
        addKnownPrivate(new KnownNumberComponent(10, 9, TamilWord.from("தொண்ணூறு")));
        addKnownPrivate(new KnownNumberComponent(10, 9, TamilWord.from("தொண்ணூற்று")));
        addKnownPrivate(new KnownNumberComponent(100, 1, TamilWord.from("நூற்று")));
        addKnownPrivate(new KnownNumberComponent(100, 1, TamilWord.from("நூறு")));
        addKnownPrivate(new KnownNumberComponent(100, 2, TamilWord.from("இருநூற்று")));
        addKnownPrivate(new KnownNumberComponent(100, 2, TamilWord.from("இருநூறு")));
        addKnownPrivate(new KnownNumberComponent(100, 3, TamilWord.from("முந்நூற்று")));
        addKnownPrivate(new KnownNumberComponent(100, 3, TamilWord.from("முந்நூறு")));
        addKnownPrivate(new KnownNumberComponent(100, 3, TamilWord.from("முன்னூறு")));
        addKnownPrivate(new KnownNumberComponent(100, 3, TamilWord.from("முன்னூற்று")));

        addKnownPrivate(new KnownNumberComponent(100, 4, TamilWord.from("நானூற்று")));
        addKnownPrivate(new KnownNumberComponent(100, 4, TamilWord.from("நானூறு")));
        addKnownPrivate(new KnownNumberComponent(100, 4, TamilWord.from("நாநூற்று")));
        addKnownPrivate(new KnownNumberComponent(100, 4, TamilWord.from("நாநூறு")));

        addKnownPrivate(new KnownNumberComponent(100, 5, TamilWord.from("ஐந்நூற்று")));
        addKnownPrivate(new KnownNumberComponent(100, 5, TamilWord.from("ஐநூறு")));
        addKnownPrivate(new KnownNumberComponent(100, 5, TamilWord.from("ஐநூற்று")));
        addKnownPrivate(new KnownNumberComponent(100, 5, TamilWord.from("ஐந்நூறு")));
        addKnownPrivate(new KnownNumberComponent(100, 6, TamilWord.from("அறுநூற்று")));
        addKnownPrivate(new KnownNumberComponent(100, 6, TamilWord.from("அறுநூறு")));
        addKnownPrivate(new KnownNumberComponent(100, 7, TamilWord.from("எழுநூற்று")));
        addKnownPrivate(new KnownNumberComponent(100, 7, TamilWord.from("எழுநூறு")));
        addKnownPrivate(new KnownNumberComponent(100, 8, TamilWord.from("எண்ணூற்று")));
        addKnownPrivate(new KnownNumberComponent(100, 8, TamilWord.from("எண்ணூறு")));
        addKnownPrivate(new KnownNumberComponent(100, 9, TamilWord.from("தொள்ளாயிரத்து")));
        addKnownPrivate(new KnownNumberComponent(100, 9, TamilWord.from("தொள்ளாயிரம்")));
        addKnownPrivate(new KnownNumberComponent(1000, 1, TamilWord.from("ஆயிரம்")));
        addKnownPrivate(new KnownNumberComponent(1000, 1, TamilWord.from("ஆயிரத்து")));
        addKnownPrivate(new KnownNumberComponent(100000, 1, TamilWord.from("இலட்சம்")));
        addKnownPrivate(new KnownNumberComponent(100000, 1, TamilWord.from("இலட்சத்து")));
        addKnownPrivate(new KnownNumberComponent(100000, 1, TamilWord.from("லட்சம்")));
        addKnownPrivate(new KnownNumberComponent(100000, 1, TamilWord.from("லட்சத்து")));
        addKnownPrivate(new KnownNumberComponent(10000000, 1, TamilWord.from("கோடியே")));
        addKnownPrivate(new KnownNumberComponent(10000000, 1, TamilWord.from("கோடி")));

    }
}
