package test;

import my.interest.lang.tamil.TamilUtils;
import org.junit.Assert;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.api.regex.*;
import tamil.util.IPropertyFinder;
import tamil.util.regex.FeaturedMatchersList;
import tamil.util.regex.FeaturedPatternsList;
import tamil.util.regex.TamilPattern;

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
    static {
        TamilFactory.init();
    }


    @Test
    public void testWithAlternatives() {
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
        while(matcher.find()) {
            count ++;
            System.out.println(allKurralh.substring(matcher.start(), matcher.end()));
        }
        System.out.println(count);
        Assert.assertEquals(count, 4);

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
        TamilPattern pattern = TamilPattern.compile("((\\*\\*.*)|${[ஔ]}${எழுத்து}+)",null, RXIncludeCanonicalEquivalenceFeature.FEATURE);
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
                System.out.print( "\t" + count + "\t");
                System.out.println(found);
            }  else {
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
