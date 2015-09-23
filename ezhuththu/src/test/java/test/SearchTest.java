package test;

import my.interest.lang.tamil.TamilUtils;
import org.junit.Test;
import tamil.util.IPropertyFinder;
import tamil.util.regx.TamilPattern;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class SearchTest {

    @Test
    public void testLocalKamba() throws Exception {
        if (!new File("/Users/velsubra/Downloads/Kamba Ramayanam Unicode-A.txt").exists()) {
            return;
        }
        String data = new String(TamilUtils.readAllFromFile("/Users/velsubra/Downloads/Kamba Ramayanam Unicode-A.txt"), TamilUtils.ENCODING);

        //  TamilPattern pattern = TamilPattern.compile("\\b(${எழுத்து})*?${வலியுகரவரிசை}${உயிர்}(${எழுத்து})*\\b" );

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


        TamilPattern pattern = TamilPattern.compile("${எண்சீர்களைக்கொண்ட வரி}", new PropertyFinder(
                "இடைவெளி = [ ]+\n" +
                        "சீர் = ${எழுத்து}+${இடைவெளி} \n" +
                        "எண்சீர் = (${சீர்}){12,}${எழுத்து}+\n" +
                        "எண்சீர்களைக்கொண்ட வரி = ${(எண்சீர்)} " +
                        ""));


        Matcher matcher = pattern.matcher(data);
        int count = 0;
        Date start = new Date();
        while (matcher.find()) {
            count++;
            System.out.println(count + "\t:" + data.substring(matcher.start(), matcher.end()));
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
