package test;

import my.interest.lang.tamil.TamilUtils;
import org.junit.Test;
import tamil.util.IPropertyFinder;
import tamil.util.regx.TamilPattern;

import java.util.Date;
import java.util.regex.Matcher;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class SearchTest {

    //@Test
    public void testLocalKamba() throws Exception {
        String data = new String(TamilUtils.readAllFromFile("/Users/velsubra/Downloads/Kamba Ramayanam Unicode-A.txt"), TamilUtils.ENCODING);

      //  TamilPattern pattern = TamilPattern.compile("\\b(${எழுத்து})*?${வலியுகரவரிசை}${உயிர்}(${எழுத்து})*\\b" );

        TamilPattern pattern = TamilPattern.compile("${அரிதுஅரோ ஐப்போன்ற சொல்}", new IPropertyFinder() {
            public String findProperty(String p1) {
                if ("அரிதுஅரோ ஐப்போன்ற சொல்".equals(p1))  {
                    return "${(அரிதுஅரோ ஐப்போன்ற பாங்கு)}";
                }
                if ("அரிதுஅரோ ஐப்போன்ற பாங்கு".equals(p1)) {
                 return "${0 அல்லது அதற்குமேற்பட்ட எழுத்துகள்}${குற்றுயிரைத்தொடர்ந்த உயிர்}${0 அல்லது அதற்குமேற்பட்ட எழுத்துகள்}";
                }
                if ("0 அல்லது அதற்குமேற்பட்ட எழுத்துகள்".equals(p1)) {
                    return  "${எழுத்து}*?";
                }
                if ("குற்றுயிரைத்தொடர்ந்த உயிர்".equals(p1)) {
                     return "${வலியுகரவரிசை}${உயிர்}";
                }
                return  null;
            }
        });

        Matcher matcher = pattern.matcher(data);
        int count = 0;
        Date start = new Date();
        while (matcher.find()) {
            count ++;
            System.out.println(count +"\t:" +data.substring(matcher.start(), matcher.end()));
        }

        System.out.println("Time taken:" + TamilUtils.millisToLongDHMS(new Date().getTime() - start.getTime()));

    }
}
