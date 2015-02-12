package test.simple;

import common.lang.impl.AbstractWord;
import tamil.lang.TamilWord;
import tamil.lang.TamilPage;
import my.interest.lang.tamil.bean.DigestBean;
import my.interest.lang.tamil.parser.impl.TamilPageListener;
import my.interest.lang.tamil.parser.impl.TamilWordListener;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.iyalbu.IyalbuPunarchiHandler;
import my.interest.lang.tamil.punar.handler.nannool.*;
import my.interest.lang.tamil.punar.handler.thannotru.ThannotruIrattiththalHandler;
import my.interest.lang.tamil.punar.handler.udambadu.UadambaduMeiHandler;
import my.interest.lang.tamil.punar.handler.uyirvarin.UyirvarinUkkuralMeiVittodumHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.regex.Pattern;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */

public class ScratchTest {


    @Test
    public void testDigests() throws Exception {
        TamilWord word = TamilWord.from("தமிழ் ");
        TamilWordPartContainer container = new TamilWordPartContainer(word);
        System.out.println("getDIGEST_CHARACTER_TYPE\t\t"+ container.getDIGEST_CHARACTER_TYPE().getNumberArray());
        System.out.println("getDIGEST_CONSONANT\t\t\t\t"+ container.getDIGEST_CONSONANT().getNumberArray());
        System.out.println("getDIGEST_SOUND_SIZE\t\t\t"+ container.getDIGEST_SOUND_SIZE().getNumberArray());
        System.out.println("getDIGEST_SOUND_STRENGTH\t\t"+ container.getDIGEST_SOUND_STRENGTH().getNumberArray());
        System.out.println("getDIGEST_VOWEL\t\t\t\t\t"+ container.getDIGEST_VOWEL().getNumberArray());

        List<DigestBean> lists  = DigestBean.getDigestBean("தமிழ், வேல், ஜனதா,முருகன்")      ;
        JSONObject obj=new JSONObject();
        JSONArray array = new JSONArray();
        int count = 0;
        for (DigestBean b :lists) {
            array.put(count, new JSONObject( b));
            count ++;
        }
        obj.put("list", array);
        System.out.println( obj.toString(1));
    }

//    @Test
//    public void testVinaiMutru() throws Exception {
//
//        Map<String, RootVerbDescription> map = DefinitionFactory.getAllRootVerbsMap();
//        RootVerbDescription root = map.get("கொடு");
//        VinaiMutruTable table = VinaiMutruTable.generate(root, DefinitionFactory.getAllRootVerbList().getVerbs().getGlobalDescription());
//
//
//    }
//
//    @Test
//    public void testAllWords() throws Exception {
//        InputStream is = AbstractWord.class.getResourceAsStream("/etc/tamilverbs.txt");
//        TamilPage page = TamilPageListener.readUTF8(is);
//
//        List<TamilWord> list = page.getAllTamilWords();
//
//        Map<String, RootVerbDescription> map = DefinitionFactory.getAllRootVerbsMap();
//        for (TamilWord w : list) {
//            if (map.get(w.toString()) == null) {
//                RootVerbDescription v = new RootVerbDescription();
//                v.setRoot(w.toString());
//                map.put(w.toString(), v);
//            }
//        }
//
//        List<RootVerbDescription> toSort = new ArrayList<RootVerbDescription>(map.values());
//        Collections.sort(toSort, new Comparator<RootVerbDescription>() {
//            @Override
//            public int compare(RootVerbDescription o1, RootVerbDescription o2) {
//                return o1.getRoot().compareTo(o2.getRoot());
//            }
//        });
//
//
//        System.out.println(list.size());
//        TamilRootVerbs verbs = new TamilRootVerbs();
//        verbs.setVerbs(new RootVerbsDescription());
//        verbs.getVerbs().getVerb().addAll(toSort);
//
//        JAXBContext jc = JAXBContext.newInstance(TamilRootVerbs.class);
//        Marshaller marshaller = jc.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        marshaller.marshal(verbs, System.out);
//
//
//    }

    @Test
    public void test1NannoolHandler165() throws Exception {
        NannoolHandler165 handler = new NannoolHandler165();
//        String w = "எதிர்பாராத்திருப்பங்கள்";
//        TamilWord res = TamilWord.from(w);
//        List<TamilWordSplitResult> results = handler.split(new TamilWordPartContainer(res)) ;
//        System.out.println(results.size());
//        System.out.println(results);

        TamilWordPartContainer than = new TamilWordPartContainer(TamilWord.from("மா"));
        TamilWordPartContainer uyri = new TamilWordPartContainer(TamilWord.from("காய்"));
        TamilWordPartContainer container = handler.join(than, uyri);
        System.out.println(container);
        if (container != null) {
            System.out.println(container.getWord().getSoundSizeDigest());
            System.out.println(container.getWord().getCharacterTypeDigest());
            System.out.println(container.getWord().getConsonantDigest());
        }

        if (container != null) {
            List<TamilWordSplitResult> list = handler.splitAll(container);
            System.out.println(list);
        }


    }

    @Test
    public void test1NannoolHandle164_2() throws Exception {
        NannoolHandler164_2 handler = new NannoolHandler164_2();
//        String w = "எதிர்பாராத்திருப்பங்கள்";
//        TamilWord res = TamilWord.from(w);
//        List<TamilWordSplitResult> results = handler.split(new TamilWordPartContainer(res)) ;
//        System.out.println(results.size());
//        System.out.println(results);

        TamilWordPartContainer than = new TamilWordPartContainer(TamilWord.from("சான்றுகளில்உறவு"));
        TamilWordPartContainer uyri = new TamilWordPartContainer(TamilWord.from("யாது"));
        TamilWordPartContainer container = handler.join(than, uyri);
        System.out.println(container);
        if (container != null) {
            System.out.println(container.getWord().getSoundSizeDigest());
            System.out.println(container.getWord().getCharacterTypeDigest());
            System.out.println(container.getWord().getConsonantDigest());
        }

        if (container != null) {
            List<TamilWordSplitResult> list = handler.splitAll(container);
            System.out.println(list);
        }


    }

    @Test
    public void test1NannoolHandler163_2() throws Exception {
        NannoolHandler163_2 handler = new NannoolHandler163_2();
//        String w = "எதிர்பாராத்திருப்பங்கள்";
//        TamilWord res = TamilWord.from(w);
//        List<TamilWordSplitResult> results = handler.split(new TamilWordPartContainer(res)) ;
//        System.out.println(results.size());
//        System.out.println(results);

        TamilWordPartContainer than = new TamilWordPartContainer(TamilWord.from("விள"));
        TamilWordPartContainer uyri = new TamilWordPartContainer(TamilWord.from("நாள்"));
        TamilWordPartContainer container = handler.join(than, uyri);
        System.out.println(container);
        if (container != null) {
            System.out.println(container.getWord().getSoundSizeDigest());
            System.out.println(container.getWord().getCharacterTypeDigest());
            System.out.println(container.getWord().getConsonantDigest());
        }

        if (container != null) {
            List<TamilWordSplitResult> list = handler.splitAll(container);
            System.out.println(list);
        }


    }

    @Test
    public void test1NannoolHandler163_1() throws Exception {
        NannoolHandler163_1 handler = new NannoolHandler163_1();
//        String w = "எதிர்பாராத்திருப்பங்கள்";
//        TamilWord res = TamilWord.from(w);
//        List<TamilWordSplitResult> results = handler.split(new TamilWordPartContainer(res)) ;
//        System.out.println(results.size());
//        System.out.println(results);

        TamilWordPartContainer than = new TamilWordPartContainer(TamilWord.from("எ"));
        TamilWordPartContainer uyri = new TamilWordPartContainer(TamilWord.from("அளவு"));
        TamilWordPartContainer container = handler.join(than, uyri);
        System.out.println(container);
        if (container != null) {
            System.out.println(container.getWord().getSoundSizeDigest());
            System.out.println(container.getWord().getCharacterTypeDigest());
            System.out.println(container.getWord().getConsonantDigest());
        }

        if (container != null) {
            List<TamilWordSplitResult> list = handler.splitAll(container);
            System.out.println(list);
        }


    }

    @Test
    public void test1YagaraUdambaduMei() throws Exception {
        UadambaduMeiHandler handler = new UadambaduMeiHandler();
//        String w = "எதிர்பாராத்திருப்பங்கள்";
//        TamilWord res = TamilWord.from(w);
//        List<TamilWordSplitResult> results = handler.split(new TamilWordPartContainer(res)) ;
//        System.out.println(results.size());
//        System.out.println(results);

        TamilWordPartContainer than = new TamilWordPartContainer(TamilWord.from("பலி"));
        TamilWordPartContainer uyri = new TamilWordPartContainer(TamilWord.from("இடங்களிலுமுண்டாவென"));
        TamilWordPartContainer container = handler.join(than, uyri);
        System.out.println(container);
        if (container != null) {
            System.out.println(container.getWord().getSoundSizeDigest());
            System.out.println(container.getWord().getCharacterTypeDigest());
            System.out.println(container.getWord().getConsonantDigest());
        }

        if (container != null) {
            List<TamilWordSplitResult> list = handler.splitAll(container);
            System.out.println(list);
        }


    }

    @Test
    public void test1VagaraUdambaduMei() throws Exception {
        UadambaduMeiHandler handler = new UadambaduMeiHandler();
//        String w = "எதிர்பாராத்திருப்பங்கள்";
//        TamilWord res = TamilWord.from(w);
//        List<TamilWordSplitResult> results = handler.split(new TamilWordPartContainer(res)) ;
//        System.out.println(results.size());
//        System.out.println(results);

        TamilWordPartContainer than = new TamilWordPartContainer(TamilWord.from("பல"));
        TamilWordPartContainer uyri = new TamilWordPartContainer(TamilWord.from("இடங்களிலுமுண்டாவென"));
        TamilWordPartContainer container = handler.join(than, uyri);
        System.out.println(container);
        if (container != null) {
            System.out.println(container.getWord().getSoundSizeDigest());
            System.out.println(container.getWord().getCharacterTypeDigest());
            System.out.println(container.getWord().getConsonantDigest());
        }

        if (container != null) {
            List<TamilWordSplitResult> list = handler.splitAll(container);
            System.out.println(list);
        }


    }

    @Test
    public void test1Nannool158_3_2() throws Exception {
        NannoolHandler158_3_2 handler = new NannoolHandler158_3_2();
//        String w = "எதிர்பாராத்திருப்பங்கள்";
//        TamilWord res = TamilWord.from(w);
//        List<TamilWordSplitResult> results = handler.split(new TamilWordPartContainer(res)) ;
//        System.out.println(results.size());
//        System.out.println(results);

        TamilWordPartContainer than = new TamilWordPartContainer(TamilWord.from("பொன்"));
        TamilWordPartContainer uyri = new TamilWordPartContainer(TamilWord.from("நீண்டது"));
        TamilWordPartContainer container = handler.join(than, uyri);
        System.out.println(container);
        if (container != null) {
            System.out.println(container.getWord().getSoundSizeDigest());
            System.out.println(container.getWord().getCharacterTypeDigest());
            System.out.println(container.getWord().getConsonantDigest());
        }

        if (container != null) {
            List<TamilWordSplitResult> list = handler.splitAll(container);
            System.out.println(list);
        }


    }

    @Test
    public void test1Nannool158_3_1() throws Exception {
        NannoolHandler158_3_1 handler = new NannoolHandler158_3_1();
//        String w = "எதிர்பாராத்திருப்பங்கள்";
//        TamilWord res = TamilWord.from(w);
//        List<TamilWordSplitResult> results = handler.split(new TamilWordPartContainer(res)) ;
//        System.out.println(results.size());
//        System.out.println(results);

        TamilWordPartContainer than = new TamilWordPartContainer(TamilWord.from("முள்"));
        TamilWordPartContainer uyri = new TamilWordPartContainer(TamilWord.from("நீண்டது"));
        TamilWordPartContainer container = handler.join(than, uyri);
        System.out.println(container);
        if (container != null) {
            System.out.println(container.getWord().getSoundSizeDigest());
            System.out.println(container.getWord().getCharacterTypeDigest());
            System.out.println(container.getWord().getConsonantDigest());
        }

        if (container != null) {
            List<TamilWordSplitResult> list = handler.splitAll(container);
            System.out.println(list);
        }


    }

    @Test
    public void test1Nannool158_2() throws Exception {
        NannoolHandler158_2 handler = new NannoolHandler158_2();
        String w = "வைக்க";
        TamilWord res = TamilWord.from(w);
        List<TamilWordSplitResult> results = handler.splitAll(new TamilWordPartContainer(res));
        System.out.println(results.size());
        System.out.println(results);

        TamilWordPartContainer than = new TamilWordPartContainer(TamilWord.from("நொ"));
        TamilWordPartContainer uyri = new TamilWordPartContainer(TamilWord.from("நாகா"));
        TamilWordPartContainer container = handler.join(than, uyri);
        System.out.println(container);
        if (container != null) {
            System.out.println(container.getWord().getSoundSizeDigest());
            System.out.println(container.getWord().getCharacterTypeDigest());
            System.out.println(container.getWord().getConsonantDigest());
        }

        if (container != null) {
            List<TamilWordSplitResult> list = handler.splitAll(container);
            System.out.println(list);
        }


    }


    @Test
    public void test1Nannool158_1() throws Exception {
        NannoolHandler158_1 handler = new NannoolHandler158_1();
//        String w = "எதிர்பாராத்திருப்பங்கள்";
//        TamilWord res = TamilWord.from(w);
//        List<TamilWordSplitResult> results = handler.split(new TamilWordPartContainer(res)) ;
//        System.out.println(results.size());
//        System.out.println(results);

        TamilWordPartContainer than = new TamilWordPartContainer(TamilWord.from("கல்    "));
        TamilWordPartContainer uyri = new TamilWordPartContainer(TamilWord.from("யானை"));
        TamilWordPartContainer container = handler.join(than, uyri);
        System.out.println(container);
        if (container != null) {
            System.out.println(container.getWord().getSoundSizeDigest());
            System.out.println(container.getWord().getCharacterTypeDigest());
            System.out.println(container.getWord().getConsonantDigest());
        }

        if (container != null) {
            List<TamilWordSplitResult> list = handler.splitAll(container);
            System.out.println(list);
        }


    }


    @Test
    public void test1UyirVarin() throws Exception {
        UyirvarinUkkuralMeiVittodumHandler handler = new UyirvarinUkkuralMeiVittodumHandler();
//        String w = "எதிர்பாராத்திருப்பங்கள்";
//        TamilWord res = TamilWord.from(w);
//        List<TamilWordSplitResult> results = handler.split(new TamilWordPartContainer(res)) ;
//        System.out.println(results.size());
//        System.out.println(results);

        TamilWordPartContainer than = new TamilWordPartContainer(TamilWord.from("நாடு    "));
        TamilWordPartContainer uyri = new TamilWordPartContainer(TamilWord.from("ஒற்று"));
        TamilWordPartContainer container = handler.join(than, uyri);
        System.out.println(container);
        if (container != null) {
            System.out.println(container.getWord().getSoundSizeDigest());
            System.out.println(container.getWord().getCharacterTypeDigest());
            System.out.println(container.getWord().getConsonantDigest());
            List<TamilWordSplitResult> list = handler.splitAll(container);
            System.out.println(list);
        }


    }

    @Test
    public void test1Thannotru() throws Exception {
        ThannotruIrattiththalHandler handler = new ThannotruIrattiththalHandler();
//        String w = "எதிர்பாராத்திருப்பங்கள்";
//        TamilWord res = TamilWord.from(w);
//        List<TamilWordSplitResult> results = handler.split(new TamilWordPartContainer(res)) ;
//        System.out.println(results.size());
//        System.out.println(results);

        TamilWordPartContainer than = new TamilWordPartContainer(TamilWord.from("தன்"));
        TamilWordPartContainer uyri = new TamilWordPartContainer(TamilWord.from("ஒற்று"));
        TamilWordPartContainer container = handler.join(than, uyri);
        System.out.println(container);
        System.out.println(container.getWord().getSoundSizeDigest());
        System.out.println(container.getWord().getCharacterTypeDigest());
        System.out.println(container.getWord().getConsonantDigest());

        List<TamilWordSplitResult> list = handler.splitAll(container);
        System.out.println(list);


    }

    @Test
    public void test1Iyalbu() throws Exception {
        IyalbuPunarchiHandler handler = new IyalbuPunarchiHandler();
        String w = "தன்னொற்று";
        TamilWord res = TamilWord.from(w);
        List<TamilWordSplitResult> results = handler.splitAll(new TamilWordPartContainer(res));
        System.out.println(results.size());
        System.out.println(results);
    }

//    @Test
//    public void testScratch01() throws Exception {
//
//        Grid grid = new Grid();
//        grid.addColumn("S.NO1");
//
//        grid.addColumn("Sound Size Digest\n\n_H_ = half,\n _O_ = one,\n _T_= two");
//        grid.addColumn("Sound Strength Digest\n\n_V_ = Vallinum,\n _M_ = Mellinam,\n _I_= Idaiyinam");
//        grid.addColumn("Vowel Digest");
//        grid.addColumn("Consonant Digest");
//        grid.addColumn("Character type Digest\n\n_U_ = Uyir,\n _UM_ = UyirMei,\n _M_= Mei");
//        grid.addColumn("Tamil Character");
//        int count = -1;
//        for (TamilCharacter ch : TamilCharacterLookUpContext.listAllTamilCharacters()) {
//            count++;
//            Row r = grid.createNewRow();
//            int col = 0;
//            r.setObjectAt(String.valueOf(count), col++);
//
//            r.setObjectAt(ch.getSoundSizeDigest(), col++);
//            r.setObjectAt(ch.getSoundStrengthDigest(), col++);
//            r.setObjectAt(ch.getVowelDigest(), col++);
//            r.setObjectAt(ch.getConsonantDigest(), col++);
//            r.setObjectAt(ch.getCharacterTypeDigest(), col++);
//            r.setObjectAt(ch, col++);
//
//        }
//        grid.pack(70);
//        grid.printTo(Logger.getDEFAULT(), "Character  Reference Table. _X_ indicates absence of a Trait.");
//
//
//        InputStream is = AbstractWord.class.getResourceAsStream("/etc/tamilverbs.txt");
//        TamilPage page = TamilPageListener.readUTF8(is);
//
//        List<TamilWord> list = page.getAllTamilWords();
//
//        grid = new Grid();
//        grid.addColumn("S.NO");
//
//        grid.addColumn("Sound Size Digest\n (_H_ = half, _O_ = one, _T_= two)");
//        grid.addColumn("Sound Strength Digest\n\n_V_ = Vallinum,\n _M_ = Mellinam,\n _I_= Idaiyinam");
//        grid.addColumn("Vowel Digest");
//        grid.addColumn("Consonant Digest");
//        grid.addColumn("Character type Digest\n(_U_ = Uyir, _UM_ = UyirMei, _M_= Mei)");
//        grid.addColumn("Tamil Word");
//        count = -1;
//        for (TamilWord ch : list) {
//            count++;
//            Row r = grid.createNewRow();
//            int col = 0;
//            r.setObjectAt(String.valueOf(count), col++);
//
//            r.setObjectAt(ch.getSoundSizeDigest(), col++);
//            r.setObjectAt(ch.getSoundStrengthDigest(), col++);
//            r.setObjectAt(ch.getVowelDigest(), col++);
//            r.setObjectAt(ch.getConsonantDigest(), col++);
//            r.setObjectAt(ch.getCharacterTypeDigest(), col++);
//            r.setObjectAt(ch.toString(), col++);
//
//        }
//        grid.pack(70);
//        grid.printTo(Logger.getDEFAULT(), "Word Digests.");
//
//    }

    @Test
    public void testScratch0() throws Exception {
        String s = "1வே2ல்3.முருகன்5";
        AbstractWord word = TamilWordListener.readUTF8(new ByteArrayInputStream(s.getBytes()));
        String s1 = word.toString();
        System.out.println(word.toString());
        Assert.assertEquals(s1, s);

        s = "1வே2ல்3.முருகன்5";
        word = TamilWordListener.readUTF8(new ByteArrayInputStream(s.getBytes()));
        s1 = word.toString();
        System.out.println(word.toString());
        Assert.assertEquals(s1, s);

        s = "1வே2ல்3. முருகன்5";
        word = TamilWordListener.readUTF8(new ByteArrayInputStream(s.getBytes()));
        s1 = word.toString();
        System.out.println(word.toString());
        Assert.assertEquals(s1, "1வே2ல்3.");


    }

    @Test
    public void testScratch00() throws Exception {
        String s = "எதிர்பாராத்திருப்பங்கள்";
        TamilWord word = TamilWordListener.readUTF8(new ByteArrayInputStream(s.getBytes()));
        System.out.println(word.isPure());
        // word.addLetter(TamilSimpleCharacter.a);
        String s1 = word.toString();
        System.out.println(s1);
        System.out.println("Vowel:" + word.getVowelDigest());
        System.out.println(Pattern.compile("(_A_).*").matcher(word.getVowelDigest().toString()).matches());
        System.out.println("Conso:" + word.getConsonantDigest());
        System.out.println("Sound:" + word.getSoundSizeDigest());
        System.out.println("Type:" + word.getCharacterTypeDigest());
    }

    @Test
    public void testScratch1() throws Exception {
        // String s = new String ("1வே2ல்3முருகன்5".getBytes(), "UTF-8");
        String s = "எதிர்பாராத்திருப்பங்கள்\n" +
                "=======================\n" +
                "எந்த எதிர்வினை மகாபாரதத்தை பெரிதும்  ஆவலுக்குள்ளாக்குகிறது.\n" +
                "\n" +
                "1. எந்தக்குலத்தைக்காக்க தன் வாழ்வை அற்பணித்தானோ அதேக்குலத்திற்பிறந்த அர்ச்சுனனால் அம்புக்கட்டிலில் உடல்முழுவதும் துழைக்கப்பட்டு   போர்க்களத்தில்    படுக்கவைக்கப்படுகிறான். - பீசுமன்\n" +
                "======\n" +
                "\n" +
                "2. எந்தநாட்டைவீழ்த்த வில்லாற்றலை அர்ச்சுனனுக்கு ஊட்டியூட்டி போதித்தானோ, அதே நாட்டின் மருமகனாகிவிட்ட   அர்ச்சுனனின்  அதே வில்லாற்றலால் போர்க்களத்தில்   எதிர்க்கப்படுகிறான் - துரோணன்.\n" +
                "=======\n" +
                "\n" +
                "3. எந்தவொருவனை போரில்வெல்லவேண்டி     தீராப்பகையும் போட்டியுங்கொண்டானோ அதேவொருவனாகிய அர்ச்சுனனின்   பாசத்திற்குரிய  மூத்தவண்ணனாகிப்போய் போர்க்களத்தில் கடேசித்தம்பியான    அர்ச்சுனனுக்கெதிராக    நின்றான் - கர்ணன்  (இதுவரை சகோதரப்பாசத்தை கண்டிறாத ஆனால் அதுபோன்றவோன்றிற்காக  ஏங்கியவன் கர்ணன்)\n" +
                "\n" +
                "இம்மூவர்தான் கௌரவப்படையின் முக்கியத்தளபதிகள்.   இம்மூவரின் ஆற்றலையும் (ஏதோவொரு வழியில்) உடைத்தவன் அர்ச்சுனன்.";
        //     "=======================\n";

        //String s ="";
        TamilPage page = TamilPageListener.readUTF8(new ByteArrayInputStream(s.getBytes()));
        System.out.println(page.size());

        System.out.println(page);

    }

    @Test
    public void testScratch2() throws Exception {
        String s = ".";
        AbstractWord word = TamilWordListener.readUTF8(new ByteArrayInputStream(s.getBytes()));
        String s1 = word.toString();
        System.out.println(word.toString());
        Assert.assertEquals(s1, s);

//        TamilSentence sentence = TamilSentenceListener.readUTF8(new ByteArrayInputStream( s.getBytes()));
//        s1 = sentence.toString();
//        System.out.println( s1);
//        Assert.assertEquals(s1, s);


    }
}
