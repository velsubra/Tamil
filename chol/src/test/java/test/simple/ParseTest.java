package test.simple;

import my.interest.lang.tamil.bean.MultipleWordSplitResult;
import my.interest.lang.tamil.bean.SimpleSplitResult;
import my.interest.lang.tamil.impl.job.ExecuteManager;
import my.interest.lang.tamil.internal.api.HandlerFactory;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.multi.WordGeneratorFromVinaiyadi;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.ThodarMozhiBuilder;
import my.interest.lang.tamil.punar.ThodarMozhiBuilders;
import my.interest.lang.tamil.translit.EnglishToTamilCharacterLookUpContext;
import my.interest.lang.util.TimeUtils;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.job.JobManager;
import tamil.lang.api.job.JobResultSnapShot;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.Peyarchchol;
import tamil.lang.known.non.derived.Vinaiyadi;
import tamil.lang.known.non.derived.idai.Aththu;
import tamil.lang.known.non.derived.idai.Ottu;
import tamil.util.regex.impl.DictionaryRegExFinderJob;
import tamil.util.regex.impl.YaappuPatternsCounterJob;

import java.util.Date;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class ParseTest {
    static {
        TamilFactory.init();
    }

    @Test
    public void testParse0() throws Exception {
        PersistenceInterface inter = PersistenceInterface.get();
        inter.setAutoLoad(false);
        // ExecuteManager.fire(new WordGeneratorFromVinaiyadi(inter.findRootVerbDescription("போ")));
        // ExecuteManager.fire(new WordGeneratorFromVinaiyadi(inter.findRootVerbDescription("முடி")));

        TamilFactory.getSystemDictionary().add(new Peyarchchol(EnglishToTamilCharacterLookUpContext.getBestMatch("thamizh"), 0));
        TamilFactory.getSystemDictionary().add(new Peyarchchol(EnglishToTamilCharacterLookUpContext.getBestMatch("vellam"), 0));
        TamilFactory.getSystemDictionary().add(new Peyarchchol(EnglishToTamilCharacterLookUpContext.getBestMatch("thaazhi"), 0));
        TamilFactory.getSystemDictionary().add(Aththu.ATHTHU);
        TamilFactory.getSystemDictionary().add(Ottu.IK);
        TamilFactory.getSystemDictionary().add(Ottu.ICH);
        TamilFactory.getSystemDictionary().add(Ottu.ITH);
        TamilFactory.getSystemDictionary().add(Ottu.IP);


        System.out.println("Starting ....");

        Date date = new Date();
        ThodarMozhiBuilders builder = ThodarMozhiBuilders.createNewBuilder();
        ThodarMozhiBuilders builders = builder.build(new TamilWordPartContainer(TamilWord.from("தமிழ்வெல்லத்தாழி")));
        //  ThodarMozhiBuilders builders =  builder.build(new TamilWordPartContainer(TamilWord.from("தாழித்தமிழ்")));
        //   ThodarMozhiBuilders builders =  builder.build(new TamilWordPartContainer(TamilWord.from("தமிழ்வெல்லத்தாழிவெல்லத்தாழித்தமிழ்த்தமிழ்வெல்லத்தாழிவெல்லத்தாழித்தமிழ்")));
        //  ThodarMozhiBuilders builders =  builder.build(new TamilWordPartContainer(TamilWord.from("தமிழ்வெல்லத்தாழிவெல்லத்தாழித்தமிழ்")));
        // ThodarMozhiBuilders builders =  builder.build(new TamilWordPartContainer(TamilWord.from("வெல்லத்தாழி")));
        // ThodarMozhiBuilders builders =  builder.build(new TamilWordPartContainer(TamilWord.from("தமிழ்")));
        // ThodarMozhiBuilders builders =  builder.build(new TamilWordPartContainer(TamilWord.from("தமிழ்வெல்லம்")));

        System.out.println("------------------");
        for (ThodarMozhiBuilder b : builders.getList()) {
            System.out.println("out:" + b.getClass().getName());
            for (IKnownWord known : b.getThodarMozhi()) {
                System.out.println("\tpart:" + known.getWord());
            }
        }
        System.out.println(new Date().getTime() - date.getTime());


    }

    @Test
    public void testParse1() throws Exception {
        try {
            ExecuteManager.start();

            PersistenceInterface inter = PersistenceInterface.get();
            inter.setAutoLoad(false);
            System.out.print("Firing po: ...");
            Date date = new Date();
            ExecuteManager.fire(new WordGeneratorFromVinaiyadi(inter.findRootVerbDescription("போ")));
            System.out.println(TimeUtils.millisToLongDHMS(new Date().getTime() - date.getTime()));
            System.out.print("Firing mudi: ...");
            date = new Date();
            ExecuteManager.fire(new WordGeneratorFromVinaiyadi(inter.findRootVerbDescription("முடி")));

            System.out.println(TimeUtils.millisToLongDHMS(new Date().getTime() - date.getTime()));
            Thread.currentThread().sleep(2000);
            System.out.print("Firing parsing: ...");
            date = new Date();
            //  MultipleWordSplitResult res = HandlerFactory.parse("போகமுடியாதவர்களுக்காக", 5);
            MultipleWordSplitResult res = HandlerFactory.parse("போகாதவர்கள்", 5);
            System.out.println(TimeUtils.millisToLongDHMS(new Date().getTime() - date.getTime()));
            if (res.getSplit() != null) {
                int count = 0;
                for (SimpleSplitResult s : res.getSplit()) {
                    System.out.println(count++ + ":" + s);
                }
            }

        } finally {
            ExecuteManager.stop();
        }
    }

    @Test
    public void testDictionaryTest() throws Exception {
        try {
            ExecuteManager.start();
            PersistenceInterface inter = PersistenceInterface.get();
            inter.setAutoLoad(false);
            System.out.print("Firing po: ...");

            ExecuteManager.fire(new WordGeneratorFromVinaiyadi(inter.findRootVerbDescription("போ")));
            Thread.currentThread().sleep(2000);
            DictionaryRegExFinderJob job = new DictionaryRegExFinderJob("\\b${vali}${ezhuththu}*", Vinaiyadi.class);
            JobManager manager = TamilFactory.getJobManager("jobs/search/dictionary/category");
            long id = manager.submit(job, JSONObject.class);
            JobResultSnapShot<JSONObject> resultSnapShot = manager.findJobResultSnapShot(id, JSONObject.class);

            while (true) {
                if (resultSnapShot == null) {
                    throw new Exception("Not found");
                }
                System.out.println(resultSnapShot.getStatus().getCompletionPercent() + " %");

                if (resultSnapShot.isDone()) break;
                Thread.currentThread().sleep(100);
                resultSnapShot = manager.findJobResultSnapShot(id, JSONObject.class);

            }
            System.out.println(resultSnapShot.getNewResults(0).getChunk());
            Assert.assertEquals(1, resultSnapShot.getNewResults(0).getChunk().size());

        } finally {
            ExecuteManager.stop();
        }
    }

    @Test
    public void testArrayValue() throws Exception {
        TamilWord ret = EnglishToTamilCharacterLookUpContext.getArrayValue("aa = a, b=b");
        Assert.assertEquals("aa=அ, b=ப்", ret.toString());
    }
}
