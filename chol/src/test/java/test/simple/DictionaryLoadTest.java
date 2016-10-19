package test.simple;

import my.interest.lang.tamil.generated.types.TamilRootWords;
import my.interest.lang.tamil.impl.job.ExecuteManager;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.parser.impl.TamilPageListener;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.TamilPage;
import tamil.lang.TamilWord;
import tamil.lang.api.parser.ParserResult;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.IBasePeyar;
import tamil.lang.known.non.derived.IBaseVinai;
import tamil.lang.known.thodar.AbstractThodarMozhi;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by velsubra on 10/19/16.
 */
public class DictionaryLoadTest {
    static {
        TamilFactory.init();
    }

    @Test
    public void testLoad() throws  Exception{

        PersistenceInterface inter = PersistenceInterface.get();
        inter.setAutoLoad(true);

        //Causes to read the persisted root verbs and sample applications
        //Since the auto load is true , words are generated and and added into the dictionary in the backgrond.
        TamilRootWords rootWords = inter.getAllRootWords();

        //Lets wait for some time for the dictionary to completely load
        int seconds = 0;
        while(!ExecuteManager.isQueueEmpty()) {
         Thread.currentThread().sleep(1000);
          System.out.println((++seconds) + " Seconds. Dictionary loading in the background with size now: " + TamilFactory.getSystemDictionary().size());
        }
        //The dictionary is hopefully loaded.


        // Now find the root words
        testPage();



    }


    private void testPage() {
        String s = "எதிர்பாராத்திருப்பங்கள்\n" + "=======================\n"
                + "எந்த எதிர்வினை மகாபாரதத்தை பெரிதும்  ஆவலுக்குள்ளாக்குகிறது.\n" + "\n"
                + "1. எந்தக்குலத்தைக்காக்க தன் வாழ்வை அற்பணித்தானோ அதேக்குலத்திற்பிறந்த அர்ச்சுனனால் அம்புக்கட்டிலில் உடல்முழுவதும் துழைக்கப்பட்டு   போர்க்களத்தில்    படுக்கவைக்கப்படுகிறான். - பீசுமன்\n"
                + "======\n" + "\n"
                + "2. எந்தநாட்டைவீழ்த்த வில்லாற்றலை அர்ச்சுனனுக்கு ஊட்டியூட்டி போதித்தானோ, அதே நாட்டின் மருமகனாகிவிட்ட   அர்ச்சுனனின்  அதே வில்லாற்றலால் போர்க்களத்தில்   எதிர்க்கப்படுகிறான் - துரோணன்.\n"
                + "=======\n" + "\n"
                + "3. எந்தவொருவனை போரில்வெல்லவேண்டி     தீராப்பகையும் போட்டியுங்கொண்டானோ அதேவொருவனாகிய அர்ச்சுனனின்   பாசத்திற்குரிய  மூத்தவண்ணனாகிப்போய் போர்க்களத்தில் கடேசித்தம்பியான    அர்ச்சுனனுக்கெதிராக    நின்றான் - கர்ணன்  (இதுவரை சகோதரப்பாசத்தை கண்டிறாத ஆனால் அதுபோன்றவோன்றிற்காக  ஏங்கியவன் கர்ணன்)\n"
                + "\n"
                + "இம்மூவர்தான் கௌரவப்படையின் முக்கியத்தளபதிகள்.   இம்மூவரின் ஆற்றலையும் (ஏதோவொரு வழியில்) உடைத்தவன் அர்ச்சுனன்.";
        // "=======================\n";

        // String s ="";
        TamilPage page = TamilPage.from(s);

        List<TamilWord> words = page.getAllTamilWords();
        System.out.println("# of words in the page:" + words.size());
        for (TamilWord tamilWord : words) {
            if (tamilWord.isPure() && !tamilWord.isEmpty()) {
                //TamilWord tword = TamilFactory.getTransliterator(null).transliterate(tamilWord.translitToEnglish());
                IKnownWord alreadyKnown = TamilFactory.getSystemDictionary().peek(tamilWord);
                if (alreadyKnown == null) {
                    ParserResult result = TamilFactory.getCompoundWordParser().quickParse(tamilWord);
                    if (result == null || result.getSplitWords().isEmpty()) {
                        System.out.println("The base word for:" + tamilWord.toString() +" could not be determined");
                        continue;
                    } else {
                        alreadyKnown = result.getSplitWords().get(0);
                    }
                }

                System.out.println(alreadyKnown.getWord().toString() +" -> " + recursive_FindRoot(alreadyKnown).getWord());

            }
        }
        // page.getAllTamilWords().stream().filter(word -> ((TamilWord)
        // word).isPure()).filter(word -> !((TamilWord)
        // word).isEmpty()).forEach(word ->
        // System.out.println(((TamilWord)word).translitToEnglish()));
    }

    IKnownWord recursive_FindRoot(IKnownWord known) {

        if (AbstractThodarMozhi.class.isAssignableFrom(known.getClass())) {
            List list = ((AbstractThodarMozhi) known).getWords();
            if (((IKnownWord) list.get(0)).getWord().equals(known.getWord())) {
                return (IKnownWord) list.get(0);
            } else {
                return recursive_FindRoot((IKnownWord) list.get(0));
            }
        } else if (IBasePeyar.class.isAssignableFrom(known.getClass())) {
            return  ((IBasePeyar)known).getPeyar();
        } else if (IBaseVinai.class.isAssignableFrom(known.getClass())) {
            return ((IBaseVinai)known).getVinaiyadi();
        } else {
            return known;
        }
    }
}
