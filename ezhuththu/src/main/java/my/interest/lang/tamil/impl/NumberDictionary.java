package my.interest.lang.tamil.impl;

import my.interest.lang.tamil.impl.dictionary.DefaultPlatformDictionaryBase;
import my.interest.lang.tamil.impl.number.known.KnownNumberComponent;
import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;

import java.math.BigInteger;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class NumberDictionary extends DefaultPlatformDictionaryBase {
    public static final KnownNumberComponent ORU = new KnownNumberComponent(0, 1, TamilWord.from("ஒரு"));




    public static final NumberDictionary INSTANCE = new NumberDictionary();

    public void addKnown(IKnownWord word) {
       throw new RuntimeException("Nothing should be added."); 
    }

    private void addKnownPrivate(IKnownWord word) {
          super.addKnown(word);
    }


    private NumberDictionary() {
          super(KnownNumberComponent.class);

        addKnownPrivate(new KnownNumberComponent(0, 0, TamilWord.from("புள்ளி")));
        addKnownPrivate(new KnownNumberComponent(0, 0, TamilWord.from("சுழி")));
        addKnownPrivate(new KnownNumberComponent(0, 0, TamilWord.from("பூச்சியம்")));
        addKnownPrivate(new KnownNumberComponent(0, 1, TamilWord.from("ஓர்")));
        addKnownPrivate(ORU);
        addKnownPrivate(new KnownNumberComponent(0, 1, TamilWord.from("ஒன்று")));
        addKnownPrivate(new KnownNumberComponent(0, 2, TamilWord.from("ஈர்")));
        addKnownPrivate(new KnownNumberComponent(0, 2, TamilWord.from("இரண்டு")));
        addKnownPrivate(new KnownNumberComponent(0, 3, TamilWord.from("மூ")));
        addKnownPrivate(new KnownNumberComponent(0, 5, TamilWord.from("ஐ")));
        addKnownPrivate(new KnownNumberComponent(0, 3, TamilWord.from("மூன்று")));
        addKnownPrivate(new KnownNumberComponent(0, 4, TamilWord.from("நான்கு")));
        addKnownPrivate(new KnownNumberComponent(0, 5, TamilWord.from("ஐந்து")));
        addKnownPrivate(new KnownNumberComponent(0, 6, TamilWord.from("ஆறு")));
        addKnownPrivate(new KnownNumberComponent(0, 7, TamilWord.from("ஏழு")));
        addKnownPrivate(new KnownNumberComponent(0, 8, TamilWord.from("எட்டு")));
        addKnownPrivate(new KnownNumberComponent(0, 9, TamilWord.from("ஒன்பது")));
        addKnownPrivate(new KnownNumberComponent(1, 1, TamilWord.from("பத்து")));
        addKnownPrivate(new KnownNumberComponent(1, 1, TamilWord.from("பதின்")));
        addKnownPrivate(new KnownNumberComponent(1, 1, TamilWord.from("பன்")));
        addKnownPrivate(new KnownNumberComponent(1, 1, TamilWord.from("பதி")));
        addKnownPrivate(new KnownNumberComponent(1, 2, TamilWord.from("இருபது")));
        addKnownPrivate(new KnownNumberComponent(1, 2, TamilWord.from("இருபத்து")));
        addKnownPrivate(new KnownNumberComponent(1, 3, TamilWord.from("முப்பது")));
        addKnownPrivate(new KnownNumberComponent(1, 3, TamilWord.from("முப்பத்து")));
        addKnownPrivate(new KnownNumberComponent(1, 4, TamilWord.from("நாற்பது")));
        addKnownPrivate(new KnownNumberComponent(1, 4, TamilWord.from("நாற்பத்து")));
        addKnownPrivate(new KnownNumberComponent(1, 5, TamilWord.from("ஐம்பது")));
        addKnownPrivate(new KnownNumberComponent(1, 5, TamilWord.from("ஐம்பத்து")));
        addKnownPrivate(new KnownNumberComponent(1, 6, TamilWord.from("அறுபது")));
        addKnownPrivate(new KnownNumberComponent(1, 6, TamilWord.from("அறுபத்து")));
        addKnownPrivate(new KnownNumberComponent(1, 7, TamilWord.from("எழுபது")));
        addKnownPrivate(new KnownNumberComponent(1, 7, TamilWord.from("எழுபத்து")));
        addKnownPrivate(new KnownNumberComponent(1, 8, TamilWord.from("எண்பது")));
        addKnownPrivate(new KnownNumberComponent(1, 8, TamilWord.from("எண்பத்து")));
        addKnownPrivate(new KnownNumberComponent(1, 9, TamilWord.from("தொண்ணூறு")));
        addKnownPrivate(new KnownNumberComponent(1, 9, TamilWord.from("தொண்ணூற்று")));
        addKnownPrivate(new KnownNumberComponent(2, 1, TamilWord.from("நூற்று")));
        addKnownPrivate(new KnownNumberComponent(2, 1, TamilWord.from("நூறு")));
        addKnownPrivate(new KnownNumberComponent(2, 2, TamilWord.from("இருநூற்று")));
        addKnownPrivate(new KnownNumberComponent(2, 2, TamilWord.from("இருநூறு")));
        addKnownPrivate(new KnownNumberComponent(2, 3, TamilWord.from("முந்நூற்று")));
        addKnownPrivate(new KnownNumberComponent(2, 3, TamilWord.from("முந்நூறு")));
        addKnownPrivate(new KnownNumberComponent(2, 3, TamilWord.from("முன்னூறு")));
        addKnownPrivate(new KnownNumberComponent(2, 3, TamilWord.from("முன்னூற்று")));

        addKnownPrivate(new KnownNumberComponent(2, 4, TamilWord.from("நானூற்று")));
        addKnownPrivate(new KnownNumberComponent(2, 4, TamilWord.from("நானூறு")));
        addKnownPrivate(new KnownNumberComponent(2, 4, TamilWord.from("நாநூற்று")));
        addKnownPrivate(new KnownNumberComponent(2, 4, TamilWord.from("நாநூறு")));

        addKnownPrivate(new KnownNumberComponent(2, 5, TamilWord.from("ஐந்நூற்று")));
        addKnownPrivate(new KnownNumberComponent(2, 5, TamilWord.from("ஐநூறு")));
        addKnownPrivate(new KnownNumberComponent(2, 5, TamilWord.from("ஐநூற்று")));
        addKnownPrivate(new KnownNumberComponent(2, 5, TamilWord.from("ஐந்நூறு")));
        addKnownPrivate(new KnownNumberComponent(2, 6, TamilWord.from("அறுநூற்று")));
        addKnownPrivate(new KnownNumberComponent(2, 6, TamilWord.from("அறுநூறு")));
        addKnownPrivate(new KnownNumberComponent(2, 7, TamilWord.from("எழுநூற்று")));
        addKnownPrivate(new KnownNumberComponent(2, 7, TamilWord.from("எழுநூறு")));
        addKnownPrivate(new KnownNumberComponent(2, 8, TamilWord.from("எண்ணூற்று")));
        addKnownPrivate(new KnownNumberComponent(2, 8, TamilWord.from("எண்ணூறு")));
        addKnownPrivate(new KnownNumberComponent(2, 9, TamilWord.from("தொள்ளாயிரத்து")));
        addKnownPrivate(new KnownNumberComponent(2, 9, TamilWord.from("தொள்ளாயிரம்")));
        addKnownPrivate(new KnownNumberComponent(3, 1, TamilWord.from("ஆயிரம்")));
        addKnownPrivate(new KnownNumberComponent(3, 1, TamilWord.from("ஆயிரத்து")));
        addKnownPrivate(new KnownNumberComponent(5, 1, TamilWord.from("இலட்சம்")));
        addKnownPrivate(new KnownNumberComponent(5, 1, TamilWord.from("இலட்சத்து")));
        addKnownPrivate(new KnownNumberComponent(5, 1, TamilWord.from("லட்சம்")));
        addKnownPrivate(new KnownNumberComponent(5, 1, TamilWord.from("லட்சத்து")));
        addKnownPrivate(new KnownNumberComponent(7, 1, TamilWord.from("கோடியே")));
        addKnownPrivate(new KnownNumberComponent(7, 1, TamilWord.from("கோடி")));


        addKnownPrivate(new KnownNumberComponent(6, 1, TamilWord.from("மில்லியன்")));
        addKnownPrivate(new KnownNumberComponent(9, 1, TamilWord.from("பில்லியன்")));
        addKnownPrivate(new KnownNumberComponent(6, 1, TamilWord.from("மில்லியனே")));
        addKnownPrivate(new KnownNumberComponent(9, 1, TamilWord.from("பில்லியனே")));

        //Ilango Pichchandi
        addKnownPrivate(new KnownNumberComponent(6, 1, TamilWord.from("எண்ணம்")));
        addKnownPrivate(new KnownNumberComponent(9, 1, TamilWord.from("இரட்டம்")));
        addKnownPrivate(new KnownNumberComponent(12, 1, TamilWord.from("மூவகம்")));
        addKnownPrivate(new KnownNumberComponent(15, 1, TamilWord.from("நாவகம்")));
        addKnownPrivate(new KnownNumberComponent(18, 1, TamilWord.from("ஐவகம்")));
        addKnownPrivate(new KnownNumberComponent(21, 1, TamilWord.from("அறுவகம்")));
        addKnownPrivate(new KnownNumberComponent(24, 1, TamilWord.from("எழுவகம்")));
        addKnownPrivate(new KnownNumberComponent(27, 1, TamilWord.from("எண்மகம்")));
        addKnownPrivate(new KnownNumberComponent(30, 1, TamilWord.from("தொட்டகம்")));



        addKnownPrivate(new KnownNumberComponent(6, 1, TamilWord.from("எண்ணத்து")));
        addKnownPrivate(new KnownNumberComponent(9, 1, TamilWord.from("இரட்டத்து")));
        addKnownPrivate(new KnownNumberComponent(12, 1, TamilWord.from("மூவகத்து")));
        addKnownPrivate(new KnownNumberComponent(15, 1, TamilWord.from("நாவகத்து")));
        addKnownPrivate(new KnownNumberComponent(18, 1, TamilWord.from("ஐவகத்து")));
        addKnownPrivate(new KnownNumberComponent(21, 1, TamilWord.from("அறுவகத்து")));
        addKnownPrivate(new KnownNumberComponent(24, 1, TamilWord.from("எழுவகத்து")));
        addKnownPrivate(new KnownNumberComponent(27, 1, TamilWord.from("எண்மகத்து")));
        addKnownPrivate(new KnownNumberComponent(30, 1, TamilWord.from("தொட்டகத்து")));

        //Jeyapandian

        addKnownPrivate(new KnownNumberComponent(6, 1, TamilWord.from("இருமடியாயிரம்")));
        addKnownPrivate(new KnownNumberComponent(6, 1, TamilWord.from("இரண்டுமடியாயிரம்")));
        addKnownPrivate(new KnownNumberComponent(9, 1, TamilWord.from("மும்மடியாயிரம்")));
        addKnownPrivate(new KnownNumberComponent(9, 1, TamilWord.from("மூன்றுமடியாயிரம்")));
        addKnownPrivate(new KnownNumberComponent(12, 1, TamilWord.from("நான்மடியாயிரம்")));
        addKnownPrivate(new KnownNumberComponent(12, 1, TamilWord.from("நான்குமடியாயிரம்")));
        addKnownPrivate(new KnownNumberComponent(15, 1, TamilWord.from("ஐமடியாயிரம்")));
        addKnownPrivate(new KnownNumberComponent(15, 1, TamilWord.from("ஐந்துமடியாயிரம்")));
        addKnownPrivate(new KnownNumberComponent(18, 1, TamilWord.from("அறுமடியாயிரம்")));
        addKnownPrivate(new KnownNumberComponent(18, 1, TamilWord.from("ஆறுமடியாயிரம்")));
        addKnownPrivate(new KnownNumberComponent(21, 1, TamilWord.from("எழுமடியாயிரம்")));
        addKnownPrivate(new KnownNumberComponent(21, 1, TamilWord.from("ஏழுமடியாயிரம்")));
        addKnownPrivate(new KnownNumberComponent(24, 1, TamilWord.from("எண்மடியாயிரம்")));
        addKnownPrivate(new KnownNumberComponent(24, 1, TamilWord.from("எட்டுமடியாயிரம்")));
        addKnownPrivate(new KnownNumberComponent(27, 1, TamilWord.from("ஒன்பதுமடியாயிரம்")));
        addKnownPrivate(new KnownNumberComponent(30, 1, TamilWord.from("பன்மடியாயிரம்")));
        addKnownPrivate(new KnownNumberComponent(30, 1, TamilWord.from("பத்துமடியாயிரம்")));

        addKnownPrivate(new KnownNumberComponent(6, 1, TamilWord.from("இருமடியாயிரத்து")));
        addKnownPrivate(new KnownNumberComponent(6, 1, TamilWord.from("இரண்டுமடியாயிரத்து")));
        addKnownPrivate(new KnownNumberComponent(9, 1, TamilWord.from("மும்மடியாயிரத்து")));
        addKnownPrivate(new KnownNumberComponent(9, 1, TamilWord.from("மூன்றுமடியாயிரத்து")));
        addKnownPrivate(new KnownNumberComponent(12, 1, TamilWord.from("நான்மடியாயிரத்து")));
        addKnownPrivate(new KnownNumberComponent(12, 1, TamilWord.from("நான்குமடியாயிரத்து")));
        addKnownPrivate(new KnownNumberComponent(15, 1, TamilWord.from("ஐமடியாயிரத்து")));
        addKnownPrivate(new KnownNumberComponent(15, 1, TamilWord.from("ஐந்துமடியாயிரத்து")));
        addKnownPrivate(new KnownNumberComponent(18, 1, TamilWord.from("அறுமடியாயிரத்து ")));
        addKnownPrivate(new KnownNumberComponent(18, 1, TamilWord.from("ஆறுமடியாயிரத்து ")));
        addKnownPrivate(new KnownNumberComponent(21, 1, TamilWord.from("எழுமடியாயிரத்து")));
        addKnownPrivate(new KnownNumberComponent(21, 1, TamilWord.from("ஏழுமடியாயிரத்து")));
        addKnownPrivate(new KnownNumberComponent(24, 1, TamilWord.from("எண்மடியாயிரத்து")));
        addKnownPrivate(new KnownNumberComponent(24, 1, TamilWord.from("எட்டுமடியாயிரத்து")));
        addKnownPrivate(new KnownNumberComponent(27, 1, TamilWord.from("ஒன்பதுமடியாயிரத்து")));
        addKnownPrivate(new KnownNumberComponent(30, 1, TamilWord.from("பன்மடியாயிரத்து")));
        addKnownPrivate(new KnownNumberComponent(30, 1, TamilWord.from("பத்துமடியாயிரத்து")));

        //wiki https://en.wikipedia.org/wiki/Tamil_numerals#Multiples_of_ten_.28.E0.AE.AA.E0.AE.A4.E0.AE.BF.E0.AE.A9.E0.AF.8D.E0.AE.AA.E0.AF.86.E0.AE.B0.E0.AF.81.E0.AE.95.E0.AF.8D.E0.AE.95.E0.AE.AE.E0.AF.8D.29
        addKnownPrivate(new KnownNumberComponent(5, 1, TamilWord.from("நூறாயிரம்")));
        addKnownPrivate(new KnownNumberComponent(5, 1, TamilWord.from("நூறாயிரத்து")));

        addKnownPrivate(new KnownNumberComponent(6, 1, TamilWord.from("மெய்யிரம்")));
        addKnownPrivate(new KnownNumberComponent(6, 1, TamilWord.from("மெய்யிரத்து")));
        addKnownPrivate(new KnownNumberComponent(9, 1, TamilWord.from("தொள்ளுண்")));
        addKnownPrivate(new KnownNumberComponent(9, 1, TamilWord.from("தொள்ளுண்ணே")));
        addKnownPrivate(new KnownNumberComponent(12, 1, TamilWord.from("ஈகியம்")));
        addKnownPrivate(new KnownNumberComponent(12, 1, TamilWord.from("ஈகியத்து")));
        addKnownPrivate(new KnownNumberComponent(15, 1, TamilWord.from("நெளை")));
        addKnownPrivate(new KnownNumberComponent(15, 1, TamilWord.from("நெளையே")));
        addKnownPrivate(new KnownNumberComponent(18, 1, TamilWord.from("இளஞ்சி")));
        addKnownPrivate(new KnownNumberComponent(18, 1, TamilWord.from("இளஞ்சியே")));
        addKnownPrivate(new KnownNumberComponent(20, 1, TamilWord.from("வெள்ளம்")));
        addKnownPrivate(new KnownNumberComponent(20, 1, TamilWord.from("வெள்ளத்து")));
        addKnownPrivate(new KnownNumberComponent(21, 1, TamilWord.from("ஆம்பல்")));
        addKnownPrivate(new KnownNumberComponent(21, 1, TamilWord.from("ஆம்பலே")));




    }
}
