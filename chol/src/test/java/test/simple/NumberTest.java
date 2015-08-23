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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public void testRead00() throws Exception {
        NumberReader reader = TamilFactory.getNumberReader();


        TamilWord word = reader.readNumber(String.valueOf(12), FeatureConstants.READ_NUMBER_PUNHARCHCHI_KEEP_ONLY_POSITION_VAL_135);
        String ret = reader.readAsNumber(word.toString());
        System.out.println("Number Back\t:" + ret);
        Assert.assertEquals(Long.parseLong(ret), 12);


        for (int i = 0; i < 100; i++) {
            word = reader.readNumber(String.valueOf(i), FeatureConstants.READ_NUMBER_PUNHARCHCHI_KEEP_ONLY_POSITION_VAL_135);

            System.out.println("Number Given\t:" + i);
            System.out.println("Text:" + word.toString());
            ret = reader.readAsNumber(word.toString());
            System.out.println("Number Back\t:" + ret);
            Assert.assertEquals(Long.parseLong(ret), i);


            word = reader.readNumber(String.valueOf(i), PunharchiFeature.INSTANCE_FULL);
            System.out.println("Text:" + word.toString());
            ret = reader.readAsNumber(word.toString());
            System.out.println("Number Back\t:" + ret);
            Assert.assertEquals(Long.parseLong(ret), i);

            word = reader.readNumber(String.valueOf(i));
            System.out.println("Text:" + word.toString());
            ret = reader.readAsNumber(word.toString());
            System.out.println("Number Back\t:" + ret);
            Assert.assertEquals(Long.parseLong(ret), i);

        }
    }
    @Test
    public void testRead01() throws Exception {
        NumberReader reader = TamilFactory.getNumberReader();
        testReadWrite(reader, "0");
        testReadWrite(reader, "0.0","0");
        testReadWrite(reader, "0.1","0.1");
        testReadWrite(reader, "0.01");
        testReadWrite(reader, "0.00000001");
        testReadWrite(reader, "0.00000001234567890","0.0000000123456789");
        testReadWrite(reader, "0.000450002330100000000000000000000002000000000000001");
        testReadWrite(reader, "10000000");
        testReadWrite(reader, "100000000000000");
        testReadWrite(reader, "1000000000000000000000");
        testReadWrite(reader, "1000000000000000000000101");
        testReadWrite(reader, "10000001000000100001000010101");
        testReadWrite(reader, "0000001000000100001000010101","1000000100001000010101");
        testReadWrite(reader, "6000000000000002");
        testReadWrite(reader, "7527203843535636364646000000000000002343242");
        testReadWrite(reader, "35000");
        testReadWrite(reader, "3002427350850188288");

    }

    @Test
    public void testRead02() throws Exception {
        NumberReader reader = TamilFactory.getNumberReader();

        testReadWrite(reader,384);
        testReadWrite(reader,20384);

        testReadWrite(reader,752720384);
        testReadWrite(reader,752720384);
    }

    @Test
    public void testRead03() throws Exception {
        NumberReader reader = TamilFactory.getNumberReader();
        Exception failed = null;
        List<Long> faileds = new ArrayList<Long>();
        for (int j = 0 ; j < 1000; j++) {
            Random random = new Random();
            long randomValue =
                    (long)(random.nextDouble()*(Long.MAX_VALUE - 0));
            if (randomValue <0 ){
                randomValue = -randomValue;
            }
            try {
                testReadWrite(reader, randomValue);
            } catch (Exception e) {
                System.out.println("Failed:" + randomValue);
                failed = e;
                faileds.add(randomValue);
            }



        }
        if (failed != null) {
            System.out.println("Failed List------->:" + faileds);
              throw failed;
        }

    }

    @Test
    public void testRead04() throws Exception {
        NumberReader reader = TamilFactory.getNumberReader();

        testReadWrite(reader, "5796739736615636992");
        testReadWrite(reader, "53001");
    }

    private  static void    testReadWrite( NumberReader reader, long randomValue) {
        if (randomValue < 0) {
            randomValue = -randomValue;
        }

        testReadWrite(reader, String.valueOf(randomValue));
    }

    private  static void testReadWrite( NumberReader reader, String randomValue) {
           testReadWrite(reader, randomValue, randomValue);
    }


    private  static void testReadWrite( NumberReader reader, String randomValue, String expected) {

        TamilWord word = reader.readNumber(randomValue,FeatureConstants.READ_NUMBER_PUNHARCHCHI_KEEP_ONLY_POSITION_VAL_135);

        System.out.println("Number Given\t:" + randomValue);
        System.out.println("Text:" + word.toString());
        String ret =  reader.readAsNumber(word.toString());
        System.out.println("Number  Back\t:" + ret);
        Assert.assertEquals(ret, expected);


        word = reader.readNumber(String.valueOf(randomValue),PunharchiFeature.INSTANCE_FULL);
        System.out.println("Text:" + word.toString());
        ret =  reader.readAsNumber(word.toString());
        System.out.println("Number  Back\t:" + ret);
        Assert.assertEquals(ret, expected);

        word = reader.readNumber(String.valueOf(randomValue));
        System.out.println("Text:" + word.toString());
        ret =  reader.readAsNumber(word.toString());
        System.out.println("Number  Back\t:" + ret);
        Assert.assertEquals(ret, expected);
        System.out.println("------------------------");
    }

    @Test
    public void testRead1() throws Exception {

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


        word = reader.readNumber("102003000400005000006000000700000008000000009000000000");
        System.out.println(word);
        Assert.assertEquals("பத்தாயிரத்து இருநூறுகோடியே முப்பதிலட்சத்து நானூறுகோடியே ஐம்பதாயிரங்கோடியே ஆறிலட்சங்கோடியே ஏழிலட்சங்கோடியே எண்பதாயிரங்கோடியே தொள்ளாயிரங்கோடி", word.toString());



        word = reader.readNumber("106",PunharchiFeature.INSTANCE_FULL);
        System.out.println(word);
        Assert.assertEquals("நூற்றாறு", word.toString());


        word = reader.readNumber("606",PunharchiFeature.INSTANCE_FULL);
        System.out.println(word);
        Assert.assertEquals("அறுநூற்றாறு", word.toString());



    }
}
