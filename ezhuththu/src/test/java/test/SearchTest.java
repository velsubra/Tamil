package test;

import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.impl.job.ExecuteManager;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.job.JobManager;
import tamil.lang.api.job.JobResultChunk;
import tamil.lang.api.job.JobResultSnapShot;
import tamil.lang.api.regex.*;
import tamil.util.IPropertyFinder;
import tamil.util.regex.FeaturedMatchersList;
import tamil.util.regex.FeaturedPatternsList;
import tamil.util.regex.TamilPattern;
import tamil.util.regex.impl.AbstractSimpleMatcherBasedJob;
import tamil.util.regex.impl.CharacterCounterJob;
import tamil.util.regex.impl.YaappuPatternFinderJob;
import tamil.util.regex.impl.YaappuPatternsCounterJob;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class SearchTest {
    static {
        TamilFactory.init();
    }


    @Test
    public void test_Hari_Corrected() throws Exception {
       String kurralh =  new String (TamilUtils.readAllFrom(SearchTest.class.getResourceAsStream("/test/data/Hari_corrected_kuRaL.txt"), false), TamilUtils.ENCODING);

        String pattern = "${(குறள்)}";
        YaappuPatternFinderJob nottranposed = new YaappuPatternFinderJob(kurralh, pattern);
        YaappuPatternFinderJob transposed = new YaappuPatternFinderJob(kurralh, pattern, null, true);

        List<YaappuPatternFinderJob> list = new ArrayList<YaappuPatternFinderJob>();
        list.add(nottranposed);
        list.add(transposed);
        try {
            ExecuteManager.start();
            for (YaappuPatternFinderJob job : list) {


                JobManager manager = TamilFactory.getJobManager("jobs/search/simple/category");
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


                StringBuffer buffer = new StringBuffer();
                for (JSONObject json : resultSnapShot.getNewResults(0).getChunk()) {
                    if (json.has(AbstractSimpleMatcherBasedJob.PROP_PRE_SKIPPED_COUNT)) {
                        buffer.append("\n.... " + json.getInt(AbstractSimpleMatcherBasedJob.PROP_PRE_SKIPPED_COUNT) + " code points skipped...\n");
                    }
                    if (json.has(AbstractSimpleMatcherBasedJob.PROP_PRE_MATCH_TEXT)) {
                        buffer.append(json.getString(AbstractSimpleMatcherBasedJob.PROP_PRE_MATCH_TEXT));
                    }
                    buffer.append("{");
                    buffer.append(json.getString(AbstractSimpleMatcherBasedJob.PROP_MATCH_TEXT));
                    buffer.append("}\n\n");
                    if (json.has(AbstractSimpleMatcherBasedJob.PROP_POST_MATCH_TEXT)) {
                        buffer.append(json.getString(AbstractSimpleMatcherBasedJob.PROP_POST_MATCH_TEXT));
                    }
                    if (json.has(AbstractSimpleMatcherBasedJob.PROP_POST_SKIPPED_COUNT)) {
                        buffer.append("\n.... " + json.getInt(AbstractSimpleMatcherBasedJob.PROP_POST_SKIPPED_COUNT) + " code points skipped...\n");
                    }
                }

                System.out.println(buffer.toString());
                Assert.assertEquals(job ==nottranposed ? 122 : 123, resultSnapShot.getNewResults(0).getChunk().size());

                JobResultChunk<JSONObject> lastResults = resultSnapShot.getLastResults(2);
                Assert.assertEquals(2, lastResults.getChunk().size());

                System.out.print("===>Job id:" + id);
                Assert.assertEquals("FINISHED", resultSnapShot.getStatus().getStatus().toString());


            }

        } finally {
            ExecuteManager.stop();
        }


    }



    @Test
    public void testContinuous() throws Exception {
        String kurralh ="1.அகர முதல எழுத்தெல்லாம் ஆதி\n" +
                "பகவன் முதற்றே உலகு.\n" +
                "\n" +
                "2.கற்றதனால் ஆய பயனென்கொல் வாலறிவன்\n" +
                "நற்றாள் தொழாஅர் எனின்.\n" +
                "\n" +
                "3.மலர்மிசை ஏகினான் மாணடி சேர்ந்தார்\n" +
                "நிலமிசை நீடுவாழ் வார்.\n" +
                "\n" +

                "\n" +
                "5.இருள்சேர் இருவினையும் சேரா இறைவன்\n" +
                "பொருள்சேர் புகழ்புரிந்தார் மாட்டு.\n" +
                "\n" +
                "6.பொறிவாயில் ஐந்தவித்தான் பொய்தீர் ஒழுக்க\n" +
                "நெறிநின்றார் நீடுவாழ் வார்.\n" +
                "\n" +
                "7.தனக்குவமை இல்லாதான் தாள்சேர்ந்தார்க் கல்லால்\n" +
                "மனக்கவலை மாற்றல் அரிது.\n" +
                "\n" +
                "8.அறவாழி அந்தணன் தாள்சேர்ந்தார்க் கல்லால்\n" +
                "பிறவாழி நீந்தல் அரிது.\n" +
                "\n" +
                "9.கோளில் பொறியின் குணமிலவே எண்குணத்தான்\n" +
                "தாளை வணங்காத் தலை.\n" +
                "\n" +
                "10.பிறவிப் பெருங்கடல் நீந்துவர் நீந்தார்\n" +
                "இறைவன் அடிசேரா தார்.\n" +
                "\n" +
                "11.வான்நின்று உலகம் வழங்கி வருதலால்\n" +
                "தான்அமிழ்தம் என்றுணரற் பாற்று.\n" +
                "\n" +
                "12.துப்பார்க்குத் துப்பாய துப்பாக்கித் துப்பார்க்குத்\n" +
                "துப்பாய தூஉம் மழை.\n" +
                "\n" +
                "13.விண்இன்று பொய்ப்பின் விரிநீர் வியனுலகத்து\n" +
                "உள்நின்று உடற்றும் பசி.\n" +
                "\n" +
                "14.ஏரின் உழாஅர் உழவர் புயல்என்னும்\n" +
                "வாரி வளங்குன்றிக் கால்.\n" +
                "\n" +
                "15.கெடுப்பதூஉம் கெட்டார்க்குச் சார்வாய்மற் றாங்கே\n" +
                "எடுப்பதூஉம் எல்லாம் மழை.\n" +
                "\n" +
                "16.விசும்பின் துளிவீழின் அல்லால்மற் றாங்கே\n" +
                "பசும்புல் தலைகாண்பு அரிது.\n" +
                "\n" +
                "17.நெடுங்கடலும் தன்நீர்மை குன்றும் தடிந்தெழிலி\n" +
                "தான்நல்கா தாகி விடின்.\n" +
                "\n" +
                "18.சிறப்பொடு பூசனை செல்லாது வானம்\n" +
                "வறக்குமேல் வானோர்க்கும் ஈண்டு.\n" +
                "\n" +
                "19.தானம் தவம்இரண்டும் தங்கா வியன்உலகம்\n" +
                "வானம் வழங்கா தெனின்.\n" +
                "\n" +
                "20.நீர்இன்று அமையாது உலகெனின் யார்யார்க்கும்\n" +
                "வான்இன்று அமையாது ஒழுக்கு.\n" +
                "\n" +
                "21.ஒழுக்கத்து நீத்தார் பெருமை விழுப்பத்து\n" +
                "வேண்டும் பனுவல் துணிவு.\n" +
                "\n" +
                "22.துறந்தார் பெருமை துணைக்கூறின் வையத்து\n" +
                "இறந்தாரை எண்ணிக்கொண் டற்று.\n" +
                "\n" +
                "23.இருமை வகைதெரிந்து ஈண்டுஅறம் பூண்டார்\n" +
                "பெருமை பிறங்கிற்று உலகு.\n" +
                "\n" +
                "24.உரனென்னும் தோட்டியான் ஓரைந்தும் காப்பான்\n" +
                "வரனென்னும் வைப்பிற்கோர் வித்து.\n" +
                "\n" +
                "25.ஐந்தவித்தான் ஆற்றல் அகல்விசும்பு ளார்கோமான்\n" +
                "இந்திரனே சாலுங் கரி.\n" +
                "\n" +
                "26.செயற்கரிய செய்வார் பெரியர் சிறியர்\n" +
                "செயற்கரிய செய்கலா தார்.\n" +
                "\n" +
                "27.சுவைஒளி ஊறுஓசை நாற்றமென ஐந்தின்\n" +
                "வகைதெரிவான் கட்டே உலகு.\n" +
                "\n" +
                "28.நிறைமொழி மாந்தர் பெருமை நிலத்து\n" +
                "மறைமொழி காட்டி விடும்.\n" +
                "\n" +
                "29.குணமென்னும் குன்றேறி நின்றார் வெகுளி\n" +
                "கணமேயும் காத்தல் அரிது.\n" +
                "\n" +

                "\n" +

                "\n" +
                "32.அறத்தினூஉங்கு ஆக்கமும் இல்லை அதனை\n" +
                "மறத்தலின் ஊங்கில்லை கேடு.\n" +
                "\n" +
                "33.ஒல்லும் வகையான் அறவினை ஓவாதே\n" +
                "செல்லும்வாய் எல்லாஞ் செயல்.\n" +
                "\n" +

                "\n" +
                "35.அழுக்காறு அவாவெகுளி இன்னாச்சொல் நான்கும்\n" +
                "இழுக்கா இயன்றது அறம்.";

        String pattern = "${(குறள்)}";
        YaappuPatternFinderJob nottranposed = new YaappuPatternFinderJob(kurralh, pattern);
        YaappuPatternFinderJob transposed = new YaappuPatternFinderJob(kurralh, pattern, null, true);

        List<YaappuPatternFinderJob> list = new ArrayList<YaappuPatternFinderJob>();
        list.add(nottranposed);
        list.add(transposed);
        try {
            ExecuteManager.start();
            for (YaappuPatternFinderJob job : list) {


                JobManager manager = TamilFactory.getJobManager("jobs/search/simple/category");
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


                int count = 0;
                StringBuffer buffer = new StringBuffer();
                for (JSONObject json : resultSnapShot.getNewResults(0).getChunk()) {
                    count ++;
                    if (json.has(AbstractSimpleMatcherBasedJob.PROP_PRE_SKIPPED_COUNT)) {
                        buffer.append("\n.... " + json.getInt(AbstractSimpleMatcherBasedJob.PROP_PRE_SKIPPED_COUNT) + " code points skipped...\n");
                    }
                    if (json.has(AbstractSimpleMatcherBasedJob.PROP_PRE_MATCH_TEXT)) {
                        buffer.append(json.getString(AbstractSimpleMatcherBasedJob.PROP_PRE_MATCH_TEXT));
                    }
                    buffer.append("{");
                    buffer.append(json.getString(AbstractSimpleMatcherBasedJob.PROP_MATCH_TEXT));
                    buffer.append("}"+ count +"\n\n");
                    if (json.has(AbstractSimpleMatcherBasedJob.PROP_POST_MATCH_TEXT)) {
                        buffer.append(json.getString(AbstractSimpleMatcherBasedJob.PROP_POST_MATCH_TEXT));
                    }
                    if (json.has(AbstractSimpleMatcherBasedJob.PROP_POST_SKIPPED_COUNT)) {
                        buffer.append("\n.... " + json.getInt(AbstractSimpleMatcherBasedJob.PROP_POST_SKIPPED_COUNT) + " code points skipped...\n");
                    }
                }

                System.out.println(buffer.toString());
                Assert.assertEquals(job ==nottranposed ? 31 : 32, resultSnapShot.getNewResults(0).getChunk().size());

                JobResultChunk<JSONObject> lastResults = resultSnapShot.getLastResults(2);
                Assert.assertEquals(2, lastResults.getChunk().size());

                System.out.print("===>Job id:" + id);
                Assert.assertEquals("FINISHED", resultSnapShot.getStatus().getStatus().toString());


            }

        } finally {
            ExecuteManager.stop();
        }


    }


    @Test
    public void testFindKurralh() throws Exception {
        String kurralh =
                "Copied from http://www.thirukkural.com/search/label/%E0%AE%85%E0%AE%9F%E0%AE%95%E0%AF%8D%E0%AE%95%E0%AE%AE%E0%AF%81%E0%AE%9F%E0%AF%88%E0%AE%AE%E0%AF%88" +
                        "\nகுறள் 121: \n" +
                        "அடக்கம் அமரருள் உய்க்கும் அடங்காமை \n" +
                        "ஆரிருள் உய்த்து விடும்.\n" +
                        "கலைஞர் உரை: \n" +
                        "அடக்கம் அழியாத புகழைக் கொடுக்கும். அடங்காமை வாழ்வையே இருளாக்கி விடும்.\n" +
                        "மு.வ உரை:\n" +
                        "அடக்கம் ஒருவனை உயர்த்தித் தேவருள் சேர்க்கும்; அடக்கம் இல்லாதிருத்தல், பொல்லாத இருள் போன்ற தீய வாழ்க்கையில் செலுத்தி விடும்.\n" +
                        "சாலமன் பாப்பையா உரை:\n" +
                        "அடக்கம் ஒருவனைப் பிற்காலத்தில் தேவர் உலகிற்குக் கொண்டு சேர்க்கும்; அடங்காமல் வாழ்வதோ அவனை நிறைந்த இருளுக்குக் கொண்டு போகும்.\n" +
                        "பரிமேலழகர் உரை:\n" +
                        "[அஃதாவது, மெய், மொழி, மனங்கள் தீநெறிக்கண் செல்லாது அடங்குதல் உடையன் ஆதல். அஃது ஏதிலார் குற்றம்போல் தம் குற்றமும் காணும்(குறள்.190) நடுவுநிலைமை உடையார்க்கு ஆகலின், இது நடுவு நிலைமையின்பின் வைக்கப்பட்டது.)\n" +
                        "\n" +
                        "அடக்கம் அமரருள் உய்க்கும் - ஒருவனை அடக்கம் ஆகிய அறம் பின் தேவருலகத்து உய்க்கும் ; அடங்காமை ஆர்இருள் உய்த்துவிடும் - அடங்காமையாகிய பாவம் தங்குதற்கு அரிய இருளின்கண் செலுத்தும். ( 'இருள்' என்பது ஓர் நரக விசேடம். \"எல்லாம் பொருளில் பிறந்துவிடும்\" (நான்மணி.7) என்றாற்போல, 'உய்த்துவிடும்' என்பது ஒரு சொல்லாய் நின்றது.).\n" +
                        "மணக்குடவர் உரை:\n" +
                        "மன மொழி மெய்களை யடக்கி யொழுக அவ்வடக்கம் தேவரிடத்தே கொண்டு செலுத்தும்: அவற்றை யடக்காதொழிய அவ்வடங்காமை தானே நரகத்திடைக் கொண்டு செலுத்திவிடும். மேல் பலவாகப் பயன் கூறினாராயினும், ஈண்டு அடக்கத்திற்கும் அடங்காமைக்கு மிதுவே பயனென்று தொகுத்துக் கூறினார்.\n" +
                        "Translation: \n" +
                        "Control of self does man conduct to bliss th' immortals share; \n" +
                        "Indulgence leads to deepest night, and leaves him there.\n" +
                        "Explanation: \n" +
                        "Self-control will place (a man) among the Gods; the want of it will drive (him) into the thickest darkness (of hell).\n" +
                        "மேலே செல்ல\n" +
                        "குறள் 122: \n" +
                        "காக்க பொருளா அடக்கத்தை ஆக்கம் \n" +
                        "அதனினூஉங் கில்லை உயிர்க்கு.\n" +
                        "கலைஞர் உரை: \n" +
                        "மிக்க உறுதியுடன் காக்கப்படவேண்டியது அடக்கமாகும். அடக்கத்தைவிட ஆக்கம் தரக் கூடியது வேறொன்றும் இல்லை.\n" +
                        "மு.வ உரை:\n" +
                        "அடக்கத்தை உறுதிப் பொருளாகக் கொண்டு போற்றிக் \u200Cகாக்க வேண்டும். அந்த அடக்கத்தைவிட மேம்பட்ட ஆக்கம் உயிர்க்கு இல்லை.\n" +
                        "சாலமன் பாப்பையா உரை:\n" +
                        "அடக்கத்தைச் செல்வமாக எண்ணிக் காக்க; அதைக் காட்டிலும் பெரிய செல்வம் வேறு இல்லை.\n" +
                        "பரிமேலழகர் உரை:\n" +
                        "உயிர்க்கு அதனின் ஊங்கு ஆக்கம் இல்லை - உயிர்கட்கு அடக்கத்தின் மிக்க செல்வம் இல்லை; அடக்கத்தைப் பொருளாகக் காக்க - ஆதலான் அவ்வடக்கத்தை உறுதிப் பொருளாகக் கொண்டு அழியாமல் காக்க. (உயிர் என்பது சாதியொருமை. அஃது ஈண்டு மக்கள் உயிர்மேல் நின்றது, அறிந்து அடங்கிப் பயன் கொள்வது அதுவே ஆகலின்.).\n" +
                        "மணக்குடவர் உரை:\n" +
                        "ஒருவன் தனக்குப் பொருளாக அடக்கத்தை யுண்டாக்குக. அவனுயிர்க்கு ஆக்கம் அதனின் மேற்பட்டது பிறிதில்லை.\n" +
                        "Translation: \n" +
                        "Guard thou as wealth the power of self-control; \n" +
                        "Than this no greater gain to living soul!.\n" +
                        "Explanation: \n" +
                        "Let self-control be guarded as a treasure; there is no greater source of good for man than that.\n" +
                        "மேலே செல்ல\n" +
                        "குறள் 123: \n" +
                        "செறிவறிந்து சீர்மை பயக்கும் அறிவறிந்து \n" +
                        "ஆற்றின் அடங்கப் பெறின்.\n" +
                        "கலைஞர் உரை: \n" +
                        "அறிந்து கொள்ள வேண்டியவற்றை அறிந்து அதற்கேற்ப அடக்கத்துடன் நடந்து கொள்பவரின் பண்பை உணர்ந்து பாராட்டுகள் குவியும்.\n" +
                        "மு.வ உரை:\n" +
                        "அறிய வேண்டியவற்றை அறிந்து, நல்வழியில் அடங்கி ஒழுகப்பெற்றால், அந்த அடக்கம் நல்லோரால் அறியப்பட்டு மேன்மை பயக்கும்.\n" +
                        "சாலமன் பாப்பையா உரை:\n" +
                        "அடக்கத்துடன் வாழ்வதே அறிவுடைமை என்று அறிந்து, ஒருவன் அடக்கமாக வாழ்ந்தால் அவனது அடக்கம் நல்லவர்களால் அறியப்பட்டு அது அவனுக்குப் பெருமையைக் கொடுக்கும்.\n" +
                        "பரிமேலழகர் உரை:\n" +
                        "அறிவு அறிந்து ஆற்றின் அடங்கப் பெறின் - அடங்குதலே நமக்கு அறிவாவது என்று அறிந்து நெறியானே ஒருவன் அடங்கப் பெறின்; செறிவு அறிந்து சீர்மை பயக்கும் - அவ்வடக்கம் நல்லோரான் அறியப்பட்டு அவனுக்கு விழுப்பத்தைக் கொடுக்கும். (இல்வாழ்வானுக்கு அடங்கும் நெறியாவது, மெய்ம்முதல் மூன்றும் தன்வயத்த ஆதல்.).\n" +
                        "மணக்குடவர் உரை:\n" +
                        "அறியப்படுவனவும் அறிந்து அடக்கப்படுவனவும் அறிந்து நெறியினானே யடங்கப்பெறின் அவ்வடக்கம் நன்மை பயக்கும். அறியப்படுவன- சுவை ஒளி ஊறு ஓசை நாற்றம்: அடக்கப் படுவன- மெய் வாய் கண் மூக்கு செவி.\n" +
                        "Translation: \n" +
                        "If versed in wisdom's lore by virtue's law you self restrain. \n" +
                        "Your self-repression known will yield you glory's gain.\n" +
                        "Explanation: \n" +
                        "Knowing that self-control is knowledge, if a man should control himself, in the prescribed course, such self-control will bring him distinction among the wise.\n" +
                        "மேலே செல்ல\n" +
                        "குறள் 124: \n" +
                        "நிலையின் திரியாது அடங்கியான் தோற்றம் \n" +
                        "மலையினும் மாணப் பெரிது.\n" +
                        "கலைஞர் உரை: \n" +
                        "உறுதியான உள்ளமும், அத்துடன் ஆர்ப்பாட்டமற்ற அடக்க உணர்வும் கொண்டவரின் உயர்வு, மலையைவிடச் சிறந்தது எனப் போற்றப்படும்.\n" +
                        "மு.வ உரை:\n" +
                        "தன் நிலையிலிருந்து மாறுபடாமல் அடங்கி ஒழுகுவோனுடைய உயர்வு, மலையின் உயர்வை விட மிகவும் பெரிதாகும்.\n" +
                        "சாலமன் பாப்பையா உரை:\n" +
                        "தன் நேர்மையான வழியை விட்டு விலகாது, அடக்கத்துடன் வாழ்பவனைப் பற்றிய பிறர் மனத் தோற்றம் மலையைக் காட்டிலும் மிக உயரமானது.\n" +
                        "பரிமேலழகர் உரை:\n" +
                        "நிலையின் திரியாது அடங்கியான் தோற்றம் - இல்வாழ்க்கையாகிய தன் நெறியின் வேறுபடாது நின்று அடங்கியவனது உயர்ச்சி, மலையினும் மாணப்பெரிது - மலையின் உயர்ச்சியினும் மிகப் பெரிது. (திரியாது அடங்குதல் - பொறிகளால் புலன்களை நுகராநின்றே அடங்குதல். 'மலை' ஆகுபெயர்.).\n" +
                        "மணக்குடவர் உரை:\n" +
                        "தனது நிலையிற் கெடாதே யடங்கினவனது உயர்ச்சி மலையினும் மிகப் பெரிது. நிலை- வன்னாச்சிரம தன்மம்.\n" +
                        "Translation: \n" +
                        "In his station, all unswerving, if man self subdue, \n" +
                        "Greater he than mountain proudly rising to the view.\n" +
                        "Explanation: \n" +
                        "More lofty than a mountain will be the greatness of that man who without swerving from his domestic state, controls himself.\n" +
                        "மேலே செல்ல\n" +
                        "குறள் 125: \n" +
                        "எல்லார்க்கும் நன்றாம் பணிதல் அவருள்ளும் \n" +
                        "செல்வர்க்கே செல்வம் தகைத்து.\n" +
                        "கலைஞர் உரை: \n" +
                        "பணிவு என்னும் பண்பு, எல்லார்க்கும் நலம் பயக்கும். ஏற்கனவே செல்வர்களாக இருப்பவர்களுக்கு அந்தப் பண்பு, மேலும் ஒரு செல்வமாகும்.\n" +
                        "மு.வ உரை:\n" +
                        "பணிவுடையவராக ஒழுகுதல்பொதுவாக எல்லோர்க்கும் நல்லதாகும்; அவர்களுள் சிறப்பாகச் செல்வர்க்கே மற்றொரு செல்வம் போன்றதாகும்.\n" +
                        "சாலமன் பாப்பையா உரை:\n" +
                        "செருக்கு இல்லாமல் அடக்கமாக வாழ்வது எல்லார்க்குமே நல்லதுதான்; அவ் எல்லாருள்ளும் செல்வர்களுக்கு அது மேலும் ஒரு செல்வமாக விளங்கும்.\n" +
                        "பரிமேலழகர் உரை:\n" +
                        "பணிதல் எல்லோர்க்கும் நன்றாம் - பெருமிதம் இன்றி அடங்குதல் எல்லார்க்கும் ஒப்ப நன்றே எனினும்; அவருள்ளும் செல்வர்க்கே செல்வம் தகைத்து - அவ்வெல்லாருள்ளும் செல்வம் உடையார்க்கே வேறொரு செல்வம் ஆம் சிறப்பினை உடைத்து. (பெருமிதத்தினைச் செய்யுங் கல்வியும் குடிப்பிறப்பும் உடையார் அஃது இன்றி அவை தம்மானே அடங்கியவழி அவ்வடக்கஞ் சிறந்து காட்டாது ஆகலின், 'செல்வர்க்கே செல்வம் தகைத்து' என்றார். 'செல்வத்தகைத்து' என்பது மெலிந்து நின்றது. பொது என்பாரையும் உடம்பட்டுச் சிறப்பாதல் கூறியவாறு. இவை ஐந்து பாட்டானும் பொதுவகையான் அடக்கத்தது சிறப்புக் கூறப்பட்டது.).\n" +
                        "மணக்குடவர் உரை:\n" +
                        "அடங்கியொழுகல் எல்லார்க்கும் நன்மையாம்: அவரெல்லாரினுஞ் செல்வமுடையார்க்கே மிகவும் நன்மை யுடைத்தாம். செல்வம் - மிகுதி.\n" +
                        "Translation: \n" +
                        "To all humility is goodly grace; but chief to them \n" +
                        "With fortune blessed, -'tis fortune's diadem.\n" +
                        "Explanation: \n" +
                        "Humility is good in all; but especially in the rich it is (the excellence of) higher riches.\n" +
                        "குறள் 126: \n" +
                        "ஒருமையுள் ஆமைபோல் ஐந்தடக்கல் ஆற்றின் \n" +
                        "எழுநம்யும் ஏமாப் புடைத்து.\n" +
                        "கலைஞர் உரை: \n" +
                        "உறுப்புகளை ஓர் ஓட்டுக்குள் அடக்கிக் கொள்ளும் ஆமையைப் போல் ஐம்பொறிகளையும் அடக்கியாளும் உறுதி, காலமெல்லாம் வாழ்க்கைக்குக் காவல் அரணாக அமையும்.\n" +
                        "மு.வ உரை:\n" +
                        "ஒரு பிறப்பில், ஆமைபோல் ஐம்பொறிகளையும் அடக்கியாள வல்லவனானால், அஃது அவனுக்குப் பல பிறப்பிலும் காப்பாகும் சிறப்பு உடையது.\n" +
                        "சாலமன் பாப்பையா உரை:\n" +
                        "ஆமை தன் நான்கு கால், ஒரு தலை ஆகிய ஐந்து உறுப்புகளையும் ஆபத்து வரும்போது ஓட்டுக்குள் மறைத்துக் கொள்வது போல, ஒருவன் தன் ஒரு பிறப்பில் மெய், வாய், கண், மூக்கு, செவி ஆகிய ஐந்து பொறிகளையும் அறத்திற்கு மாறான தீமை வரும்போது அடக்கும் ஆற்றல் பெறுவான் என்றால், அது அவனுக்குப் பிறவி தோறும் ஏழு பிறப்பிலும் - அரணாக இருந்து உதவும்.\n" +
                        "பரிமேலழகர் உரை:\n" +
                        "ஆமை போல் ஒருமையுள் ஐந்து அடக்கல் ஆற்றின் - ஆமைபோல, ஒருவன் ஒரு பிறப்பின்கண் ஐம்பொறிகளையும் அடக்கவல்லன் ஆயின்; எழுமையும் ஏமாப்பு உடைத்து - அவ் வன்மை அவனுக்கு எழுபிறப்பின் கண்ணும் அரண் ஆதலை உடைத்து. (ஆமை ஐந்து உறுப்பினையும் இடர் புகுதாமல் அடக்குமாறு போல இவனும் ஐம்பொறிகளையும் பாவம் புகுதாமல் அடக்க வேண்டும் என்பார் 'ஆமை போல்' என்றார். ஒருமைக்கண் செய்த வினையின் பயன் எழுமையும் தொடரும் என்பது இதனான் அறிக. இதனான் மெய்யடக்கம் கூறப்பட்டது.).\n" +
                        "மணக்குடவர் உரை:\n" +
                        "ஒருபிறப்பிலே பொறிகளைந்தினையும் ஆமைபோல அடக்க வல்லவனாயின், அவனுக்கு அதுதானே எழுபிறப்பினுங் காவலாதலை யுடைத்து.\n" +
                        "Translation: \n" +
                        "Like tortoise, who the five restrains \n" +
                        "In one, through seven world bliss obtains.\n" +
                        "Explanation: \n" +
                        "Should one throughout a single birth, like a tortoise keep in his five senses, the fruit of it will prove a safe-guard to him throughout the seven-fold births.\n" +
                        "மேலே செல்ல\n" +
                        "குறள் 127: \n" +
                        "யாகாவா ராயினும் நாகாக்க காவாக்கால் \n" +
                        "சோகாப்பர் சொல்லிழுக்குப் பட்டு.\n" +
                        "கலைஞர் உரை: \n" +
                        "ஒருவர் எதைக் காத்திட முடியாவிட்டாலும் நாவையாவது அடக்கிக் காத்திட வேண்டும். இல்லையேல் அவர் சொன்ன சொல்லே அவர் துன்பத்துக்குக் காரணமாகி விடும்.\n" +
                        "மு.வ உரை:\n" +
                        "காக்க வேண்டியவற்றுள் எவற்றைக் காக்கா விட்டாலும் நாவையாவது காக்க வேண்டு்ம்; காக்கத் தவறினால் சொற்குற்றத்தில் அகப்பட்டுத் துன்புறுவர்.\n" +
                        "சாலமன் பாப்பையா உரை:\n" +
                        "எதைக் காக்க முடியாதவரானாலும் நா ஒன்றையாவது காத்துக் கொள்ள வேண்டும். முடியாது போனால் சொல்குற்றத்தில் சிக்கித் துன்பப்படுவர்.\n" +
                        "பரிமேலழகர் உரை:\n" +
                        "யாகாவாராயினும் நாகாக்க - தம்மால் காக்கப்படுவன எல்லாவற்றையும் காக்க மாட்டாராயினும் நாவொன்றனையும் காக்க, காவாக்கால் சொல் இழுக்குப்பட்டுச் சோகாப்பர் - அதனைக் காவாராயின் சொல் இழுக்குப்பட்டுச் சோகாப்பர் - அதனைக் காவாராயின் சொற்குற்றத்தின்கண் பட்டுத் தாமே துன்புறுவர். ('யா' என்பது அஃறிணைப் பன்மை வினாப்பெயர். அஃது ஈண்டு எஞ்சாமை உணர நின்றது. முற்று உம்மை விகாரத்தால் தொக்கது. சொற்குற்றம் - சொல்லின்கண் தோன்றும் குற்றம். 'அல்லாப்பர்செம்மாப்பர்' என்பன போலச் 'சோகாப்பர்' என்பது ஒரு சொல்.).\n" +
                        "மணக்குடவர் உரை:\n" +
                        "எல்லாவற்றையும் அடக்கிலராயினும் நாவொன்றினையும் அடக்குக: அதனை அடக்காக்காற் சொற்சோர்வுபட்டுத் தாமே சோகிப்பா ராதலான். இது சோகத்தின்மாட்டே பிணிக்கப் படுவரென்பது.\n" +
                        "Translation: \n" +
                        "Whate'er they fail to guard, o'er lips men guard should keep; \n" +
                        "If not, through fault of tongue, they bitter tears shall weep.\n" +
                        "Explanation: \n" +
                        "Whatever besides you leave unguarded, guard your tongue; otherwise errors of speech and the consequent misery will ensue.\n" +
                        "மேலே செல்ல\n" +
                        "குறள் 128: \n" +
                        "ஒன்றானுந் தீச்சொல் பொருட்பயன் உண்டாயின் \n" +
                        "நன்றாகா தாகி விடும்.\n" +
                        "கலைஞர் உரை: \n" +
                        "ஒரு குடம் பாலில் துளி நஞ்சுபோல், பேசும் சொற்களில் ஒரு சொல் தீய சொல்லாக இருந்து துன்பம் விளைவிக்குமானாலும், அந்தப் பேச்சில் உள்ள நல்ல சொற்கள் அனைத்தும் தீயவாகிவிடும்.\n" +
                        "மு.வ உரை:\n" +
                        "தீய \u200Cசொற்களின் பொருளால் விளையும் தீமை ஒன்றாயினும் ஒருவனிடம் உண்டானால், அதனால் மற்ற அறங்களாலும் நன்மை விளையாமல் போகும்.\n" +
                        "சாலமன் பாப்பையா உரை:\n" +
                        "தீய சொற்களின் பொருளால் பிறர்க்கு வரும் துன்பம் சிறிதே என்றாலும் அந்தக் குறை ஒருவனிடம் இருந்தால் அவனுக்குப் பிற அறங்களால் வரும் நன்மையம் தீமையாகப் போய்விடும்.\n" +
                        "பரிமேலழகர் உரை:\n" +
                        "தீச்சொல் பொருள் பயன் ஒன்றானும் உண்டாயின்- தீயவாகிய சொற்களின் பொருள்களால் பிறர்க்கு வரும் துன்பம் ஒன்றாயினும் ஒருவன் பக்கல் உண்டாவதாயின்; நன்று ஆகாது ஆகிவிடும் - அவனுக்குப் பிற அறங்களான் உண்டான நன்மை தீதாய்விடும். (தீயசொல்லாவன - தீங்கு பயக்கும் பொய், குறளை, கடுஞ்சொல் என்பன. ஒருவன் நல்லவாகச் சொல்லும் சொற்களின் கண்ணே ஒன்றாயினும் 'தீச்சொற்படும் பொருளினது பயன் பிறர்க்கு உண்டாவதாயின்' என்று உரைப்பாரும் உளர்.).\n" +
                        "மணக்குடவர் உரை:\n" +
                        "ஒரு சொல்லேயாயினும் கேட்டார்க்கு இனிதாயிருந்து தீயசொல்லின் பொருளைப் பயக்குமாயின், நன்மையாகாதாகியே விடும். இது சால மொழிகூறினாலுந் தீதாமென்றது.\n" +
                        "Translation: \n" +
                        "Though some small gain of good it seem to bring, \n" +
                        "The evil word is parent still of evil thing.\n" +
                        "Explanation: \n" +
                        "If a man's speech be productive of a single evil, all the good by him will be turned into evil.\n" +
                        "மேலே செல்ல\n" +
                        "குறள் 129: \n" +
                        "தீயினாற் சுட்டபுண் உள்ளாறும் ஆறாதே \n" +
                        "நாவினாற் சுட்ட வடு.\n" +
                        "கலைஞர் உரை: \n" +
                        "நெருப்பு சுட்ட புண்கூட ஆறி விடும்; ஆனால் வெறுப்புக் கொண்டு திட்டிய சொற்கள் விளைத்த துன்பம் ஆறவே ஆறாது.\n" +
                        "மு.வ உரை:\n" +
                        "தீயினால் சுட்ட புண் புறத்தே வடு இருந்தாலும் உள்ளே ஆறிவிடும்; ஆனால் நாவினால் தீய சொல் கூறிச் சுடும் வடு என்றும் ஆறாது.\n" +
                        "சாலமன் பாப்பையா உரை:\n" +
                        "ஒருவனை மற்றொருவன் தீயால் சுட்ட புண் உடம்பின்மேல் வடுவாக இருந்தாலும் உள்ளத்துக் காயம் காலத்தில் ஆறிப்போய்விடும். ஆனால் கொடிய வார்த்தைகளால் நெஞ்சைச் சுட்ட வடு அதில் புண்ணாகவே கிடந்து ஒரு நாளும் ஆறாது.\n" +
                        "பரிமேலழகர் உரை:\n" +
                        "தீயினால் சுட்டபுண் உள் ஆறும் - ஒருவனை ஒருவன் தீயினால் சுட்ட புண் மெய்க்கண் கிடப்பினும், மனத்தின்கண், அப்பொழுதே ஆறும்; நாவினால் சுட்ட வடு ஆறாது - அவ்வாறன்றி வெவ்வுரை உடைய நாவினால் சுட்ட வடு அதன் கண்ணும் எஞ்ஞான்றும் ஆறாது. (ஆறிப்போதலால் தீயினால் சுட்டதனைப் 'புண்' என்றும், ஆறாது கிடத்தலால் நாவினால் சுட்டதனை 'வடு' என்றும் கூறினார். தீயும் வெவ்வுரையும் சுடுதல் தொழிலான் ஒக்கும் ஆயினும், ஆறாமையால் தீயினும் வெவ்வுரை கொடிது என்பது போதரலின், இது குறிப்பான் வந்த வேற்றுமை அலங்காரம். இவை மூன்று பாட்டானும் மொழி அடக்கம் கூறப்பட்டது.\n" +
                        "மணக்குடவர் உரை:\n" +
                        "தீயினாற் சுட்டபுண் உள்ளாறித் தீரும்: நாவினாற் சுட்ட புண் ஒருகாலத்தினுந் தீராது.\n" +
                        "Translation: \n" +
                        "In flesh by fire inflamed, nature may thoroughly heal the sore; \n" +
                        "In soul by tongue inflamed, the ulcer healeth never more.\n" +
                        "Explanation: \n" +
                        "The wound which has been burnt in by fire may heal, but a wound burnt in by the tongue will never heal.\n" +
                        "மேலே செல்ல\n" +
                        "குறள் 130: \n" +
                        "கதங்காத்துக் கற்றடங்கல் ஆற்றுவான் செவ்வி \n" +
                        "அறம்பார்க்கும் ஆற்றின் நுழைந்து.\n" +
                        "கலைஞர் உரை: \n" +
                        "கற்பவை கற்றுச், சினம் காத்து, அடக்கமெனும் பண்பு கொண்டவரை அடைந்திட அறமானது வழிபார்த்துக் காத்திருக்கும்.\n" +
                        "மு.வ உரை:\n" +
                        "சினம் தோன்றாமல் காத்து, கல்வி கற்று, அடக்கமுடையவனாக இருக்க வல்லவனுடைய செவ்வியை, அவனுடைய வழியில் சென்று அறம் பார்த்திருக்கும்.\n" +
                        "சாலமன் பாப்பையா உரை:\n" +
                        "கல்வி கற்று மனத்துள் கோபம் பிறக்காமல் காத்து, அடக்கமாக வாழும் ஆற்றல் படைத்தவனை அடைவதற்கான நேரத்தை எதிர்பார்த்து அறம் அவன் வழியில் நுழைந்து காத்து இருக்கும்.\n" +
                        "பரிமேலழகர் உரை:\n" +
                        "கதம் காத்துக் கற்று அடங்கல் ஆற்றுவான் செவ்வி - மனத்தின்கண் வெகுளி தோன்றாமல் காத்துக் கல்வியுடையவனாய் அடங்குதலை வல்லவனது செவ்வியை, அறம் பார்க்கும் ஆற்றின் நுழைந்து - அறக் கடவுள் பாராநிற்கும் அவனை அடையும் நெற்றியின்கண் சென்று. (அடங்குதல் - மனம் புறத்துப் பரவாது அறத்தின் கண்ணே நிற்றல். செவ்வி - தன் குறை கூறுதற்கு ஏற்ற மனம், மொழி முகங்கள் இனியனாம் ஆம் காலம். இப் பெற்றியானை அறம் தானே சென்று அடையும் என்பதாம். இதனான் மனவடக்கம் கூறப்பட்டது.).\n" +
                        "மணக்குடவர் உரை:\n" +
                        "வெகுளியும் அடக்கிக் கல்வியுமுடையனாய் அதனால் வரும் பெருமிதமும் அடக்கவல்லவன்மாட்டு, அறமானது நெறியானே வருந்தித் தானே வருதற்குக் காலம் பார்க்கும். இஃது அடக்கமுடையார்க்கு அறமுண்டாமென்றது.\n" +
                        "Translation: \n" +
                        "Who learns restraint, and guards his soul from wrath, \n" +
                        "Virtue, a timely aid, attends his path.\n" +
                        "Explanation: \n" +
                        "Virtue, seeking for an opportunity, will come into the path of that man who, possessed of learning and self-control, guards himself against anger." +
                        "" +
                        "" +
                        "" +
                        "" +
                        "" +
                        "" +
                        "" +
                        "" +
                        "" +
                        "" +
                        "" +
                        "" +
                        "" +
                        "" +
                        "" +
                        "" +
                        "" +
                        "";
        String pattern = "${(குறள்)}";
        YaappuPatternFinderJob nottranposed = new YaappuPatternFinderJob(kurralh, pattern);
        YaappuPatternFinderJob transposed = new YaappuPatternFinderJob(kurralh, pattern, null, true);

        List<YaappuPatternFinderJob> list = new ArrayList<YaappuPatternFinderJob>();
        list.add(nottranposed);
        list.add(transposed);
        try {
            ExecuteManager.start();
            for (YaappuPatternFinderJob job : list) {


                JobManager manager = TamilFactory.getJobManager("jobs/search/simple/category");
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


                StringBuffer buffer = new StringBuffer();
                for (JSONObject json : resultSnapShot.getNewResults(0).getChunk()) {
                    if (json.has(AbstractSimpleMatcherBasedJob.PROP_PRE_SKIPPED_COUNT)) {
                        buffer.append("\n.... " + json.getInt(AbstractSimpleMatcherBasedJob.PROP_PRE_SKIPPED_COUNT) + " code points skipped...\n");
                    }
                    if (json.has(AbstractSimpleMatcherBasedJob.PROP_PRE_MATCH_TEXT)) {
                        buffer.append(json.getString(AbstractSimpleMatcherBasedJob.PROP_PRE_MATCH_TEXT));
                    }
                    buffer.append("\n\n");
                    buffer.append(json.getString(AbstractSimpleMatcherBasedJob.PROP_MATCH_TEXT));
                    buffer.append("\n\n");
                    if (json.has(AbstractSimpleMatcherBasedJob.PROP_POST_MATCH_TEXT)) {
                        buffer.append(json.getString(AbstractSimpleMatcherBasedJob.PROP_POST_MATCH_TEXT));
                    }
                    if (json.has(AbstractSimpleMatcherBasedJob.PROP_POST_SKIPPED_COUNT)) {
                        buffer.append("\n.... " + json.getInt(AbstractSimpleMatcherBasedJob.PROP_POST_SKIPPED_COUNT) + " code points skipped...\n");
                    }
                }

                System.out.println(buffer.toString());
                Assert.assertEquals(job ==nottranposed ? 10 : 11, resultSnapShot.getNewResults(0).getChunk().size());

                JobResultChunk<JSONObject> lastResults = resultSnapShot.getLastResults(2);
                Assert.assertEquals(2, lastResults.getChunk().size());

                System.out.print("===>Job id:" + id);
                Assert.assertEquals("FINISHED", resultSnapShot.getStatus().getStatus().toString());


            }

        } finally {
            ExecuteManager.stop();
        }


    }


    @Test
    public void testPatternSearch() throws Exception {
        try {
            ExecuteManager.start();
            String pattern = "${தேமா}";
            YaappuPatternFinderJob job = new YaappuPatternFinderJob("இருள்சேர் இருவினையும் சேரா இறைவன்", pattern);
            JobManager manager = TamilFactory.getJobManager("jobs/count/simple/category");
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
            Assert.assertEquals("[{\"match\":\"ருள்சேர்\",\"preMatch\":\"இ\"}, {\"match\":\"னையும்\",\"preMatch\":\" இருவி\"}, {\"match\":\"சேரா\",\"preMatch\":\" \"}, {\"match\":\"றைவன்\",\"preMatch\":\" இ\"}]", resultSnapShot.getNewResults(0).getChunk().toString());
            Assert.assertEquals(4, resultSnapShot.getNewResults(0).getChunk().size());
        } finally {
            ExecuteManager.stop();
        }
    }

    @Test
    public void testCharacterPatternCount() throws Exception {
        try {
            ExecuteManager.start();
            List<String> setNames = new ArrayList<String>();
            setNames.add("${எழுத்து}");
            setNames.add("${(மொழி)}");
            setNames.add("${(தேமா)}");
            setNames.add("${இடைவெளி}${எழுத்து}");
            setNames.add("${வலியுகரவரிசை}");
            setNames.add("${அகரவரிசை}");
            YaappuPatternsCounterJob job = new YaappuPatternsCounterJob("aஇருள்சேர் இருவினையும் சேரா இறைவன்", "searching patterns", setNames, null);
            JobManager manager = TamilFactory.getJobManager("jobs/count/simple/category");
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
            Assert.assertEquals("[{\"labels\":[\"${எழுத்து}\",\"${(மொழி)}\",\"${(தேமா)}\",\"${இடைவெளி}${எழுத்து}\",\"${வலியுகரவரிசை}\",\"${அகரவரிசை}\"],\"counts\":[17,3,1,3,0,1]}]", resultSnapShot.getNewResults(0).getChunk().toString());
            Assert.assertEquals(1, resultSnapShot.getNewResults(0).getChunk().size());
        } finally {
            ExecuteManager.stop();
        }
    }


    @Test
    public void testCharacterCont() throws Exception {
        try {
            ExecuteManager.start();
            List<String> setNames = new ArrayList<String>();
            setNames.add("எழுத்து");
            setNames.add("வலியுகரவரிசை");
            setNames.add("அகரவரிசை");
            CharacterCounterJob job = new CharacterCounterJob(TamilWord.from("aஇருள்சேர் இருவினையும் சேரா இறைவன்"
                    , true), setNames);
            JobManager manager = TamilFactory.getJobManager("jobs/count/simple/category");
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
            Assert.assertEquals("[{\"labels\":[\"எழுத்து\",\"வலியுகரவரிசை\",\"அகரவரிசை\"],\"counts\":[17,0,1]}]", resultSnapShot.getNewResults(0).getChunk().toString());
            Assert.assertEquals(1, resultSnapShot.getNewResults(0).getChunk().size());
        } finally {
            ExecuteManager.stop();
        }
    }

    @Test
    public void testWithAlternatives() throws Exception {
        TamilRXCompiler compiler = TamilFactory.getRegEXCompiler();
        FeaturedPatternsList list = compiler.compileToPatternsList("${(kurralh)}", null, null, RXKuttuAcrossCirFeature.FEATURE, RXAythamAsKurrilFeature.FEATURE, RXKuttuFeature.FEATURE, RXIncludeCanonicalEquivalenceFeature.FEATURE);
        //FeaturedPatternsList list = compiler.compileToPatternsList("${(kurralh)}", null, null,  RXAythamAsKurrilFeature.FEATURE,RXKuttuAcrossCirFeature.FEATURE);
        System.out.println(list.getPatternsListSize());
        Assert.assertEquals(list.getPatternsListSize(), 16);
        String allKurralh = "\n" +
                "இருள்சேர் இருவினையும் சேரா இறைவன்\n" +
                "பொருள்சேர் புகழ்புரிந்தார் மாட்டு \n" +
                "\n" +
                "அணியன்றோ நாணுடைமை சான்றோர்க்கஃ தின்றேல்\n" +
                "பிணிஅன்றோ பீடு நடை\n" +
                "//அன்றோ needs  the option ற + ே + ா = றோ  be checked to be correctly recognized \n" +
                "\n" +
                "இன்பங் கடல்மற்றுக் காம மஃதடுங்கால்\n" +
                "துன்ப மதனிற் பெரிது\n" +
                "\n" +
                "ஓர்ந்துகண் ணோடாது இறைபுரிந்து யார்மாட்டும்\n" +
                "தேர்ந்துசெய் வஃதே முறை";
        FeaturedMatchersList matcher = list.matchersList(allKurralh);
        int count = 0;
        while (matcher.find()) {
            count++;
            System.out.println(allKurralh.substring(matcher.start(), matcher.end()));
        }
        System.out.println(count);
        Assert.assertEquals(count, 4);


        try {
            ExecuteManager.start();
            YaappuPatternFinderJob job = new YaappuPatternFinderJob(allKurralh, "${(kurralh)}");
            JobManager manager = TamilFactory.getJobManager("jobs/kurralh/category");
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
            Assert.assertEquals(4, resultSnapShot.getNewResults(0).getChunk().size());
        } finally {
            ExecuteManager.stop();
        }

    }


    @Test
    public void testBasicTests() {

        TamilPattern pattern = TamilPattern.compile("${asai}");
        Matcher matcher = pattern.matcher("று");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ezhuththu}");
        matcher = pattern.matcher("று");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${valiyugaravarisai}");
        matcher = pattern.matcher("று");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntedil}");
        matcher = pattern.matcher("கௌ");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${kurril}");
        matcher = pattern.matcher("க");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${uyirmey}");
        matcher = pattern.matcher("க");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${mei}", null, RXIncludeCanonicalEquivalenceFeature.FEATURE);
        matcher = pattern.matcher("க்");
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${uyir}");
        matcher = pattern.matcher("அ");
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${uyir}", null, RXIncludeCanonicalEquivalenceFeature.FEATURE);
        matcher = pattern.matcher("ஔ");
        Assert.assertTrue(matcher.matches());


    }


    @Test
    public void testLocalKamba() throws Exception {
        if (!new File("/Users/velsubra/Downloads/kambar-ramayanam.txt").exists()) {
            return;
        }
        String data = new String(TamilUtils.readAllFromFile("/Users/velsubra/Downloads/kambar-ramayanam.txt"), TamilUtils.ENCODING);

        //      TamilPattern pattern = TamilPattern.compile("\\b(${எழுத்து})*?${வலியுகரவரிசை}${உயிர்}(${எழுத்து})*\\b" );

//        TamilPattern pattern = TamilPattern.compile("${அரிதுஅரோ ஐப்போன்ற சொல்}", new IPropertyFinder() {
//            public String findProperty(String p1) {
//                if ("அரிதுஅரோ ஐப்போன்ற சொல்".equals(p1))  {
//                    return "${(அரிதுஅரோ ஐப்போன்ற பாங்கு)}";
//                }
//                if ("அரிதுஅரோ ஐப்போன்ற பாங்கு".equals(p1)) {
//                 return "${0 அல்லது அதற்குமேற்பட்ட எழுத்துகள்}${குற்றுயிரைத்தொடர்ந்த உயிர்}${0 அல்லது அதற்குமேற்பட்ட எழுத்துகள்}";
//                }
//                if ("0 அல்லது அதற்குமேற்பட்ட எழுத்துகள்".equals(p1)) {
//                    return  "${எழுத்து}*?";
//                }
//                if ("குற்றுயிரைத்தொடர்ந்த உயிர்".equals(p1)) {
//                     return "${வலியுகரவரிசை}${உயிர்}";
//                }
//                return  null;
//            }
//        });


        //  TamilPattern pattern = TamilPattern.compile("${(அசையெண்ணிக்கை[5-])}");
        //TamilPattern pattern = TamilPattern.compile("(\\*\\*.*)");
        //TamilPattern pattern = TamilPattern.compile("((\\*\\*.*))|(${எழுத்து}*(${[ழ்]}|${[ள்]})${!எழுத்து}+${தகரவரிசையுயிர்மெய்}${எழுத்து})");
        //  TamilPattern pattern = TamilPattern.compile("((\\*\\*.*))|(${எழுத்து}*${[ழ்]}${இடைவெளி}${தகரவரிசையுயிர்மெய்}${எழுத்து}*)");
        TamilPattern pattern = TamilPattern.compile("((\\*\\*.*)|${[ஔ]}${எழுத்து}+)", null, RXIncludeCanonicalEquivalenceFeature.FEATURE);
        //TamilPattern pattern = TamilPattern.compile("((\\*\\*.*))|(${எழுத்து}*${[ழ்]}${இடைவெளி}${தகரவரிசையுயிர்மெய்}${எழுத்து}*)",null, RXIncludeCanonicalEquivalenceFeature.FEATURE);
//          TamilPattern pattern = TamilPattern.compile("((\\*\\*.*))|(${எழுத்து}*(${[ள்]})${!எழுத்து}+${தகரவரிசையுயிர்மெய்}${எழுத்து})");


//        TamilPattern pattern = TamilPattern.compile("${எண்சீர்களைக்கொண்ட வரி}", new PropertyFinder(
//                "இடைவெளி = [ ]+\n" +
//                        "சீர் = ${எழுத்து}+${இடைவெளி} \n" +
//                        "எண்சீர் = (${சீர்}){8,}${எழுத்து}+\n" +
//                        "எண்சீர்களைக்கொண்ட வரி = ${(எண்சீர்)} " +
//                        ""));


        Matcher matcher = pattern.matcher(data);
        int count = 0;
        Date start = new Date();
        String heading = null;
        while (matcher.find()) {
            String found = data.substring(matcher.start(), matcher.end());
            if (!found.startsWith("*")) {
                count++;
                if (heading != null) {
                    System.out.println(heading);
                    heading = null;
                }
                System.out.print("\t" + count + "\t");
                System.out.println(found);
            } else {
                heading = found;
            }

        }

        System.out.println("Time taken:" + TamilUtils.millisToLongDHMS(new Date().getTime() - start.getTime()));

    }


    public static class PropertyFinder implements IPropertyFinder {
        private Map<String, String> map = new HashMap<String, String>();

        public PropertyFinder(String alias) throws Exception {
            String delimiter = "=";
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(alias.getBytes("UTF-8"))));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) continue;
                if (line.charAt(0) == '#') continue;

                int delimPosition = line.indexOf(delimiter);
                String key = line.substring(0, delimPosition - 1).trim();
                String value = line.substring(delimPosition + 1).trim();
                map.put(key, value);
            }
            reader.close();
        }

        public String findProperty(String p1) {
            return map.get(p1);
        }
    }
}
