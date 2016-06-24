package test.sound.download;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import common.lang.impl.AbstractCharacter;
import my.interest.lang.tamil.TamilUtils;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.sound.AtomicSound;
import tamil.lang.sound.TamilSoundLookUpContext;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by velsubra on 12/30/15.
 */
public class DownloaderTest {
    static {
        TamilFactory.init();
       // System.setProperty("http.proxyHost", "www-proxy.us.oracle.com");
      //  System.setProperty("http.proxyPort", "80");
    }

  //  @Test
    public void testDownload_iitm() {
        Client cl = Client.create();
//        cl.addFilter(new LoggingFilter());
//       byte[] data =  toByte("ப்பி", cl);
//        System.out.println("Data size:" + data.length);

        List<AtomicSound> list = TamilSoundLookUpContext.getAllTamilSounds();
        Collections.sort(list);
        int count = 0;
        File dir = new File("src/main/resources/sound/download_iltp");
        System.out.println("Total size:" + list.size());
        for (AtomicSound s : list) {
            TamilWord word = new TamilWord();
            for (AbstractCharacter c: s.getWord()) {
                word.add(c);
                try {
                    String eng = word.translitToEnglish();
                    File file = new File(dir, eng + ".wav");
                    count++;
                    if (!file.exists()) {
                        byte[] data = toByte_iitm(word.toString(), cl);
                        System.out.println(count + ": Writing :" + file.getAbsolutePath() + " for:" + word.toString());
                        TamilUtils.writeToFile(file, data);

                    } else {
                        System.out.println(count + "  Exists:" + word.toString());
                    }
                } catch (Exception e) {
                    System.out.println(word.toString() + ":" + e.getMessage());
                }
            }
        }

        System.out.println("Count:" + count);

    }

    public static byte[] toByte_iitm(String text, Client client) {
        String data = "Languages=tamil1&ex=execute&op="+text;
        ClientResponse res = client.resource("http://www.iitm.ac.in/donlab/hts/festival_cs.php").type("application/x-www-form-urlencoded").post(ClientResponse.class, data);
        String str = res.getEntity(String.class);
        System.out.println(res);
        String find = "href=\"wav_output/";
        int index = str.indexOf(find);
        if (index < 0) {
            throw new RuntimeException("Could not TTS:" + text);
        }
        int indexwav = str.indexOf(".wav", index);
        if (indexwav < 0) {
            throw new RuntimeException("Could not find wav file:" + text);
        }
        String fileName = str.substring(index + find.length(), indexwav + 4);
        // System.out.println("extracted file:" + fileName);

        ClientResponse res1 = client.resource("http://www.iitm.ac.in/donlab/hts/wav_output/" + fileName).get(ClientResponse.class);
        return res1.getEntity(byte[].class);

//http://mile.ee.iisc.ernet.in:8080/tts_demo/ttsWorkDir/f808wa0s0v1r.wav

    }



  //  @Test
    public void testDownload_IISC() {
        Client cl = Client.create();
//        cl.addFilter(new LoggingFilter());
//       byte[] data =  toByte("ப்பி", cl);
//        System.out.println("Data size:" + data.length);

        List<AtomicSound> list = TamilSoundLookUpContext.getAllTamilSounds();
        Collections.sort(list);
        int count = 0;
        File dir = new File("src/main/resources/sound/download");
        System.out.println("Total size:" + list.size());
        for (AtomicSound s : list) {
            try {
                String eng = s.getWord().translitToEnglish();
                File file = new File(dir, eng + ".wav");
                if (!file.exists()) {
                    byte[] data = toByteIISC(s.getWord().toString(), cl);
                    System.out.println("Writing :" + file.getAbsolutePath() + " for:" + s.getWord().toString());
                    TamilUtils.writeToFile(file, data);
                    count++;
                }
            } catch (Exception e) {
                System.out.println(s.getWord().toString() + ":" + e.getMessage());
            }
        }

        System.out.println("Count:" + count);

    }

    public static byte[] toByteIISC(String text, Client client) {

        ClientResponse res = client.resource("http://mile.ee.iisc.ernet.in:8080/tts_demo/rest/").type("application/x-www-form-urlencoded").post(ClientResponse.class, "inputText=" + text);
        String str = res.getEntity(String.class);
        System.out.println(res);
        int index = str.indexOf("ttsWorkDir/");
        if (index < 0) {
            throw new RuntimeException("Could not TTS:" + text);
        }
        int indexwav = str.indexOf(".wav", index);
        if (indexwav < 0) {
            throw new RuntimeException("Could not find wav file:" + text);
        }
        String fileName = str.substring(index + 11, indexwav + 4);
       // System.out.println("extracted file:" + fileName);

        ClientResponse res1 = client.resource("http://mile.ee.iisc.ernet.in:8080/tts_demo/ttsWorkDir/" + fileName).get(ClientResponse.class);
        return res1.getEntity(byte[].class);

//http://mile.ee.iisc.ernet.in:8080/tts_demo/ttsWorkDir/f808wa0s0v1r.wav

    }
}
