package test.simple;

import tamil.lang.TamilWord;
import my.interest.lang.tamil.internal.api.HandlerFactory;
import my.interest.lang.tamil.bean.HandlerJoinResult;
import my.interest.lang.tamil.bean.HandlerSplitResult;
import my.interest.lang.tamil.bean.SimpleSplitResult;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.handler.AbstractPunharchiHandler;
import my.interest.lang.tamil.punar.handler.palapala.NannoolHandler170_Pala;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class FactoryTest {




    @Test
    public void testJoin1() throws Exception {

        AbstractPunharchiHandler hand = new NannoolHandler170_Pala();


        HandlerJoinResult jresult = HandlerFactory.join("பல", "பல", hand);
        System.out.println("--------");
        System.out.println("--------");

        System.out.println(jresult);

        if (jresult != null) {

            HandlerSplitResult hr = HandlerFactory.split(jresult.getResult(), hand);
             if (hr == null) {
                return;
             }


            for (SimpleSplitResult sr : hr.getSplitLists()) {
                TamilWordPartContainer res = hand.join(new TamilWordPartContainer(TamilWord.from(sr.getSplitList().get(0))), new TamilWordPartContainer(TamilWord.from(sr.getSplitList().get(1))));
                System.out.println(sr + "\n");
                Assert.assertEquals(jresult.getResult(), res.toString());


            }


        }

    }

  //  @Test
//    @Ignore
//    public void testSplit1() throws Exception {
//
//        // System.out.println( new TamilWordPartContainer( TamilWord.from("அங்")).isEndingFine());
//
//
//        String word = "உயிரளபெடையில்";
//        FullSplitResult result = HandlerFactory.split(word);
//        System.out.println(result);
//
//        for (HandlerSplitResult hr : result) {
//            AbstractPunarchiHandler handler = HandlerFactory.findHandlerByName(hr.getHandlerName());
//            System.out.println(hr.getHandlerName() + ":");
//            for (SimpleSplitResult sr : hr.getSplitLists()) {
//                TamilWordPartContainer res = handler.join(new TamilWordPartContainer(TamilWord.from(sr.getSplitList().get(0))), new TamilWordPartContainer(TamilWord.from(sr.getSplitList().get(1))));
//                System.out.println(res + "\n");
//                Assert.assertEquals(word, res.toString());
//
//
//            }
//
//        }
//
//    }


//    @Test
//    @Ignore
//    public void testSplit2() throws Exception {
//
//        // System.out.println( new TamilWordPartContainer( TamilWord.from("அங்")).isEndingFine());
//
//
//        String p = "உலகிலேயே மிகவும் பாதுகாப்பானதும், விபத்துகள் மிகவும் குறைந்ததும், விபத்துகளால் ஏற்படும் இறப்புகள் குறைந்ததுமான போக்குவரத்து சாதனம் விமானம்தான். பலர் விமானப் பிரயாணம் பயங்கரமானது, ஆபத்தானது என்ற தேவையற்ற கணிப்பினிலேயே வாழ்கின்றனர். அதற்குக் காரணம், 'ஒரு விமானம் விபத்தில் அகப்பட்டால், உயிர் தப்பும் சாத்த்தியங்கள் மிகவும் குறைவு என்பதே!'\n" +
//                ". விமான விபத்துகளைவிட, அதனால் ஏற்படும் இறப்புகளை விட, மிக அதிக அளவில் தரைவழிப் பாதைகளில் ஏற்படும் விபத்துகளில் மனிதர்கள் இறப்பு ஏற்படுகிறது. இவற்றுடன் ஒப்பிடும் போது விமானம் ஒன்றுமே இல்லையென்னும் அளவுக்குத்தான் இருக்கிறது.\n" +
//                ". சுட்டு விழுத்துதல், மாயமாக மறைதல் போன்ற விசமத்தனங்கள் எதுவும் இல்லாமல் போனால், விமானப் பயணம் என்பது மேலும் பாதுகாப்பானது என்ற நிலைதான் உண்டு.";
//        TamilPage page = TamilPage.from(p);
//        List<TamilWord> words = page.getAllTamilWords();
//        System.out.println("Word count:" + words.size());
//        for (TamilWord word : words) {
//
//            FullSplitResult result = HandlerFactory.sp(word.toString());
//            System.out.println(result);
//
//            for (HandlerSplitResult hr : result) {
//                AbstractPunarchiHandler handler = HandlerFactory.findHandlerByName(hr.getHandlerName());
//                System.out.println(hr.getHandlerName() + ":");
//                for (SimpleSplitResult sr : hr.getSplitLists()) {
//                    System.out.println( "SPLIT:" + sr.getSplitList().get(0) +":" +sr.getSplitList().get(1));
//                    TamilWordPartContainer res = handler.join(new TamilWordPartContainer(TamilWord.from(sr.getSplitList().get(0))), new TamilWordPartContainer(TamilWord.from(sr.getSplitList().get(1))));
//                    System.out.println(res + "\n");
//                    Assert.assertEquals(word.toString(), res.toString());
//
//
//                }
//
//            }
//        }
//
//    }
}
