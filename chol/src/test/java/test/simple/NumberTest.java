package test.simple;

import junit.framework.Assert;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.feature.Feature;
import tamil.lang.api.feature.FeatureConstants;
import tamil.lang.api.number.IgnoreNonDigitFeature;
import tamil.lang.api.number.NotANumberException;
import tamil.lang.api.number.NumberReader;
import tamil.lang.api.number.PunharchiFeature;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class NumberTest {
    static {
        TamilFactory.init();
    }

    @Test
    public void testRead0() throws Exception {

        NumberReader reader = TamilFactory.getNumberReader();
        TamilWord word = reader.readNumber(null);
        System.out.println(word);
        Assert.assertEquals(0, word.size());

        try {
            word = reader.readNumber(" 9q");
        } catch (NotANumberException e) {
            Assert.assertEquals("Index:0 Invalid character: ", e.getMessage());
        }

        word = reader.readNumber("100000", IgnoreNonDigitFeature.INSTANCE_IGNORE_NON_DIGIT, FeatureConstants.READ_NUMBER_PUNHARCHCHI_KEEP_ONLY_POSITION_VAL_135);
        System.out.println(word);
        Assert.assertEquals("ஓரிலட்சம்", word.toString());

        word = reader.readNumber("000.0000");
        System.out.println(word);
        Assert.assertEquals("சுழி", word.toString());


        word = reader.readNumber("000.00100");
        System.out.println(word);
        Assert.assertEquals("சுழி புள்ளி சுழி சுழி ஒன்று", word.toString());




        word = reader.readNumber(" 9q", IgnoreNonDigitFeature.INSTANCE_IGNORE_NON_DIGIT);
        System.out.println(word);
        Assert.assertEquals("ஒன்பது", word.toString());


        word = reader.readNumber(" 1 9q 0", IgnoreNonDigitFeature.INSTANCE_TREAT_NON_DIGIT_AS_0, FeatureConstants.READ_NUMBER_PUNHARCHCHI_KEEP_ONLY_POSITION_VAL_135);
        System.out.println(word);
        Assert.assertEquals("ஓரிலட்சத்து ஒன்பதாயிரம்", word.toString());


        word = reader.readNumber(" 1 9q 0", IgnoreNonDigitFeature.INSTANCE_TREAT_NON_DIGIT_AS_0);
        System.out.println(word);
        Assert.assertEquals("ஓர் இலட்சத்து ஒன்பதாயிரம்", word.toString());


        word = reader.readNumber(190);
        System.out.println(word);
        Assert.assertEquals("நூற்று தொண்ணூறு", word.toString());


        word = reader.readNumber(0);
        System.out.println(word);
        Assert.assertEquals("சுழி", word.toString());


        word = reader.readNumber(0.0);
        System.out.println(word);
        Assert.assertEquals("சுழி", word.toString());


        word = reader.readNumber(0.01);
        System.out.println(word);
        Assert.assertEquals("புள்ளி சுழி ஒன்று", word.toString());

        word = reader.readNumber("0.01");
        System.out.println(word);
        Assert.assertEquals("சுழி புள்ளி சுழி ஒன்று", word.toString());


        word = reader.readNumber("190", PunharchiFeature.INSTANCE_FULL);
        System.out.println(word);
        Assert.assertEquals("நூற்றுத்தொண்ணூறு", word.toString());

        word = reader.readNumber("10010", PunharchiFeature.INSTANCE_FULL);
        System.out.println(word);
        Assert.assertEquals("பத்தாயிரத்துப்பத்து", word.toString());


        word = reader.readNumber(".000000000101");
        System.out.println(word);
        Assert.assertEquals("சுழி புள்ளி சுழி சுழி சுழி சுழி சுழி சுழி சுழி சுழி சுழி ஒன்று சுழி ஒன்று", word.toString());

        word = reader.readNumber("000000000101");
        System.out.println(word);
        Assert.assertEquals("நூற்று ஒன்று", word.toString());

        word = reader.readNumber("100000000000101", FeatureConstants.READ_NUMBER_PUNHARCHCHI_KEEP_ONLY_POSITION_VAL_135);
        System.out.println(word);
        Assert.assertEquals("ஒருகோடியே கோடியே நூற்று ஒன்று", word.toString());

        word = reader.readNumber("100000000000101");
        System.out.println(word);
        Assert.assertEquals("ஒரு கோடியே கோடியே நூற்று ஒன்று", word.toString());

        word = reader.readNumber("1000000100", PunharchiFeature.INSTANCE_KEEP_ONLY_POSITION);
        System.out.println(word);
        Assert.assertEquals("நூறுகோடியே நூறு", word.toString());

        word = reader.readNumber("100000010", PunharchiFeature.INSTANCE_KEEP_ONLY_POSITION);
        System.out.println(word);
        Assert.assertEquals("பத்துகோடியே பத்து", word.toString());

        word = reader.readNumber("100000000");
        System.out.println(word);
        Assert.assertEquals("பத்துகோடி", word.toString());

        word = reader.readNumber("10000000", FeatureConstants.READ_NUMBER_PUNHARCHCHI_KEEP_ONLY_POSITION_VAL_135);
        System.out.println(word);
        Assert.assertEquals("ஒருகோடி", word.toString());

        word = reader.readNumber("10000001", FeatureConstants.READ_NUMBER_PUNHARCHCHI_KEEP_ONLY_POSITION_VAL_135);
        System.out.println(word);
        Assert.assertEquals("ஒருகோடியே ஒன்று", word.toString());

        word = reader.readNumber("200091", FeatureConstants.READ_NUMBER_PUNHARCHCHI_KEEP_ONLY_POSITION_VAL_135);
        System.out.println(word);
        Assert.assertEquals("இரண்டிலட்சத்து தொண்ணூற்றொன்று", word.toString());


        word = reader.readNumber("100011", PunharchiFeature.INSTANCE_FULL);
        System.out.println(word);
        Assert.assertEquals("ஓரிலட்சத்துப்பதினொன்று", word.toString());


        word = reader.readNumber("100001", FeatureConstants.READ_NUMBER_PUNHARCHCHI_KEEP_ONLY_POSITION_VAL_135);
        System.out.println(word);
        Assert.assertEquals("ஓரிலட்சத்து ஒன்று", word.toString());


        word = reader.readNumber("10000");
        System.out.println(word);
        Assert.assertEquals("பத்தாயிரம்", word.toString());

        word = reader.readNumber("10001");
        System.out.println(word);
        Assert.assertEquals("பத்தாயிரத்து ஒன்று", word.toString());


        word = reader.readNumber("1000");
        System.out.println(word);
        Assert.assertEquals("ஓர் ஆயிரம்", word.toString());

        word = reader.readNumber("3000", PunharchiFeature.INSTANCE_KEEP_ONLY_POSITION);
        System.out.println(word);
        Assert.assertEquals("மூவாயிரம்", word.toString());


        word = reader.readNumber("3000", PunharchiFeature.INSTANCE_FULL);
        System.out.println(word);
        Assert.assertEquals("மூவாயிரம்", word.toString());


        word = reader.readNumber("3000");
        System.out.println(word);
        Assert.assertEquals("மூன்றாயிரம்", word.toString());

        word = reader.readNumber("30000000000");
        System.out.println(word);
        Assert.assertEquals("மூன்றாயிரங்கோடி", word.toString());


        word = reader.readNumber("43400434.0", PunharchiFeature.INSTANCE_FULL);
        System.out.println(word);
        Assert.assertEquals("நான்குகோடியேமுப்பத்துநான்கிலட்சத்துநானூற்றுமுப்பத்துநான்கு", word.toString());


        word = reader.readNumber("43400434.");
        System.out.println(word);
        Assert.assertEquals("நான்குகோடியே முப்பத்து நான்கிலட்சத்து நானூற்று முப்பத்து நான்கு", word.toString());


        word = reader.readNumber("43400434", PunharchiFeature.INSTANCE_KEEP_ONLY_POSITION);
        System.out.println(word);
        Assert.assertEquals("நான்குகோடியே முப்பத்துநான்கிலட்சத்து நானூற்று முப்பத்துநான்கு", word.toString());



        word = reader.readNumber("21.12",PunharchiFeature.INSTANCE_KEEP_ONLY_POSITION);
        System.out.println(word);
        Assert.assertEquals("இருபத்தொன்று புள்ளி ஒன்று இரண்டு", word.toString());


        word = reader.readNumber("21.12");
        System.out.println(word);
        Assert.assertEquals("இருபத்து ஒன்று புள்ளி ஒன்று இரண்டு", word.toString());

        word = reader.readNumber("444");
        System.out.println(word);
        Assert.assertEquals("நானூற்று நாற்பத்து நான்கு", word.toString());

        word = reader.readNumber("167", FeatureConstants.READ_NUMBER_PUNHARCHCHI_KEEP_ONLY_POSITION_VAL_135);
        System.out.println(word);
        Assert.assertEquals("நூற்று அறுபத்தேழு", word.toString());


        word = reader.readNumber("100");
        System.out.println(word);
        Assert.assertEquals("நூறு", word.toString());

        word = reader.readNumber("99", PunharchiFeature.INSTANCE_KEEP_ONLY_POSITION);
        System.out.println(word);
        Assert.assertEquals("தொண்ணூற்றொன்பது", word.toString());


        word = reader.readNumber("56");
        System.out.println(word);
        Assert.assertEquals("ஐம்பத்து ஆறு", word.toString());


        word = reader.readNumber("50");
        System.out.println(word);
        Assert.assertEquals("ஐம்பது", word.toString());


        word = reader.readNumber("19");
        System.out.println(word);
        Assert.assertEquals("பத்தொன்பது", word.toString());

        word = reader.readNumber("12");
        System.out.println(word);
        Assert.assertEquals("பன்னிரண்டு", word.toString());


        word = reader.readNumber("11");
        System.out.println(word);
        Assert.assertEquals("பதினொன்று", word.toString());


        word = reader.readNumber("10");
        System.out.println(word);
        Assert.assertEquals("பத்து", word.toString());

        word = reader.readNumber("");
        System.out.println(word);
        Assert.assertEquals("சுழி", word.toString());

        word = reader.readNumber("0");
        System.out.println(word);
        Assert.assertEquals("சுழி", word.toString());


        word = reader.readNumber("1");
        System.out.println(word);
        Assert.assertEquals("ஒன்று", word.toString());

        word = reader.readNumber("3");
        System.out.println(word);


        word = reader.readNumber("3", PunharchiFeature.INSTANCE_KEEP_ONLY_POSITION);
        System.out.println(word);
        Assert.assertEquals("மூன்று", word.toString());

        word = reader.readNumber("9");
        System.out.println(word);
        Assert.assertEquals("ஒன்பது", word.toString());


        word = reader.readNumber(34543.21);
        System.out.println(word);
        Assert.assertEquals("முப்பத்து நான்காயிரத்து ஐந்நூற்று நாற்பத்து மூன்று புள்ளி இரண்டு ஒன்று", word.toString());


        word = reader.readNumber("34543.21", PunharchiFeature.INSTANCE_FULL);
        System.out.println(word);
        Assert.assertEquals("முப்பத்துநான்காயிரத்தைந்நூற்றுநாற்பத்துமூன்று புள்ளி இரண்டு ஒன்று", word.toString());

        word = reader.readNumber("34543.21", PunharchiFeature.INSTANCE_KEEP_ONLY_POSITION);
        System.out.println(word);
        Assert.assertEquals("முப்பத்துநான்காயிரத்து ஐந்நூற்று நாற்பத்துமூன்று புள்ளி இரண்டு ஒன்று", word.toString());




        word = reader.readNumber("345q43.21", PunharchiFeature.INSTANCE_KEEP_ONLY_POSITION, IgnoreNonDigitFeature.INSTANCE_IGNORE_NON_DIGIT );
        System.out.println(word);
        Assert.assertEquals("முப்பத்துநான்காயிரத்து ஐந்நூற்று நாற்பத்துமூன்று புள்ளி இரண்டு ஒன்று", word.toString());



        word = reader.readNumber("345q43.21", PunharchiFeature.INSTANCE_KEEP_ONLY_POSITION, IgnoreNonDigitFeature.INSTANCE_TREAT_NON_DIGIT_AS_0 );
        System.out.println(word);
        Assert.assertEquals("மூன்றிலட்சத்து நாற்பத்தையாயிரத்து நாற்பத்துமூன்று புள்ளி இரண்டு ஒன்று", word.toString());



        word = reader.readNumber("01.101000000", PunharchiFeature.INSTANCE_KEEP_ONLY_POSITION, IgnoreNonDigitFeature.INSTANCE_IGNORE_NON_DIGIT );
        System.out.println(word);
        Assert.assertEquals("ஒன்று புள்ளி ஒன்று சுழி ஒன்று", word.toString());


    }
}
