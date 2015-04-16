package test.simple;

import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.internal.api.HandlerFactory;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.bean.MultipleWordSplitResult;
import my.interest.lang.tamil.bean.SimpleSplitResult;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.Aththu;
import tamil.lang.known.non.derived.Ottu;
import tamil.lang.known.non.derived.Peyarchchol;
import my.interest.lang.tamil.multi.ExecuteManager;
import my.interest.lang.tamil.multi.WordGeneratorFromVinaiyadi;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.ThodarMozhiBuilder;
import my.interest.lang.tamil.punar.ThodarMozhiBuilders;
import my.interest.lang.tamil.translit.EnglishToTamilCharacterLookUpContext;
import my.interest.lang.util.TimeUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class ParseTest {

    @Test
    public void testParse0() throws Exception {
        PersistenceInterface inter = PersistenceInterface.get();
        inter.setAutoLoad(false);
       // ExecuteManager.fire(new WordGeneratorFromVinaiyadi(inter.findRootVerbDescription("போ")));
       // ExecuteManager.fire(new WordGeneratorFromVinaiyadi(inter.findRootVerbDescription("முடி")));

        TamilFactory.getSystemDictionary().add(new Peyarchchol(EnglishToTamilCharacterLookUpContext.getBestMatch("thamizh"), 0));
        TamilFactory.getSystemDictionary().add(new Peyarchchol(EnglishToTamilCharacterLookUpContext.getBestMatch("vellam"),0));
        TamilFactory.getSystemDictionary().add(new Peyarchchol(EnglishToTamilCharacterLookUpContext.getBestMatch("thaazhi"),0));
        TamilFactory.getSystemDictionary().add(new Aththu());
        TamilFactory.getSystemDictionary().add(new Ottu("க்"));
        TamilFactory.getSystemDictionary().add(new Ottu("ச்"));
        TamilFactory.getSystemDictionary().add(new Ottu("த்"));
        TamilFactory.getSystemDictionary().add(new Ottu("ப்"));



        System.out.println("Starting ....");

        Date date = new Date();
        ThodarMozhiBuilders builder =  ThodarMozhiBuilders.createNewBuilder();
    ThodarMozhiBuilders builders =  builder.build(new TamilWordPartContainer(TamilWord.from("தமிழ்வெல்லத்தாழி")));
      //  ThodarMozhiBuilders builders =  builder.build(new TamilWordPartContainer(TamilWord.from("தாழித்தமிழ்")));
    //   ThodarMozhiBuilders builders =  builder.build(new TamilWordPartContainer(TamilWord.from("தமிழ்வெல்லத்தாழிவெல்லத்தாழித்தமிழ்த்தமிழ்வெல்லத்தாழிவெல்லத்தாழித்தமிழ்")));
    //  ThodarMozhiBuilders builders =  builder.build(new TamilWordPartContainer(TamilWord.from("தமிழ்வெல்லத்தாழிவெல்லத்தாழித்தமிழ்")));
       // ThodarMozhiBuilders builders =  builder.build(new TamilWordPartContainer(TamilWord.from("வெல்லத்தாழி")));
       // ThodarMozhiBuilders builders =  builder.build(new TamilWordPartContainer(TamilWord.from("தமிழ்")));
      // ThodarMozhiBuilders builders =  builder.build(new TamilWordPartContainer(TamilWord.from("தமிழ்வெல்லம்")));

        System.out.println("------------------");
        for (ThodarMozhiBuilder b:  builders.getList()) {
            System.out.println("out:" + b.getClass().getName());
            for (IKnownWord known : b.getThodarMozhi()) {
                System.out.println("\tpart:" +  known.getWord());
            }
        }
       System.out.println(new Date().getTime() - date.getTime());


    }

    @Test
    public void testParse1() throws Exception {
        PersistenceInterface inter = PersistenceInterface.get();
         inter.setAutoLoad(false);
        System.out.print("Firing po: ...");
        Date date = new Date();
        ExecuteManager.fire(new WordGeneratorFromVinaiyadi(inter.findRootVerbDescription("போ")));
        System.out.println(TimeUtils.millisToLongDHMS(new Date().getTime() - date.getTime()));
        System.out.print("Firing mudi: ...");
        date = new Date();
        ExecuteManager.fire(new WordGeneratorFromVinaiyadi(inter.findRootVerbDescription("முடி")));

        System.out.println(  TimeUtils.millisToLongDHMS(new Date().getTime() - date.getTime()) );
         Thread.currentThread().sleep(2000);
        System.out.print("Firing parsing: ...");
        date = new Date();
      //  MultipleWordSplitResult res = HandlerFactory.parse("போகமுடியாதவர்களுக்காக", 5);
        MultipleWordSplitResult res = HandlerFactory.parse("போகாதவர்கள்", 5);
        System.out.println(  TimeUtils.millisToLongDHMS(new Date().getTime() - date.getTime()) );
        if (res.getSplit() != null) {
            int count = 0;
            for (SimpleSplitResult s: res.getSplit())   {
                System.out.println(count++ + ":" + s);
            }
        }


    }

    @Test
    public void testArrayValue() throws Exception {
        TamilWord ret = EnglishToTamilCharacterLookUpContext.getArrayValue("aa = a, b=b");
        Assert.assertEquals("aa=அ, b=ப்",ret.toString());
    }
}
