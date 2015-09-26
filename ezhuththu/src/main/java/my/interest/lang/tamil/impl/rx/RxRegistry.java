package my.interest.lang.tamil.impl.rx;

import common.lang.impl.AbstractCharacter;
import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.rx.asai1.*;
import my.interest.lang.tamil.impl.rx.asai2.Karuvilham;
import my.interest.lang.tamil.impl.rx.asai2.KoovilhamRx;
import my.interest.lang.tamil.impl.rx.asai2.PulhimaRx;
import my.interest.lang.tamil.impl.rx.asai2.TheamaRx;
import my.interest.lang.tamil.impl.rx.asai3.*;
import my.interest.lang.tamil.impl.rx.cir.*;
import my.interest.lang.tamil.impl.rx.maaththirai.HalfRx;
import my.interest.lang.tamil.impl.rx.maaththirai.OneRx;
import my.interest.lang.tamil.impl.rx.maaththirai.TwoRx;
import my.interest.lang.tamil.impl.rx.paa.KurralRx;
import my.interest.lang.tamil.impl.rx.paa.KurralhCirRx;
import my.interest.lang.tamil.impl.rx.paa.KurralhThalaiRx;
import my.interest.lang.tamil.impl.rx.thalhai.*;
import my.interest.lang.tamil.impl.rx.util.IdaivelhiRx;
import my.interest.lang.tamil.impl.yaappu.AsaiIterator;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.ezhuththu.EzhuththuSetDescription;
import tamil.lang.api.regex.RXOverrideSysDefnFeature;
import tamil.lang.exception.TamilPlatformException;
import tamil.util.IPropertyFinder;
import tamil.util.regex.TamilPatternSyntaxException;
import tamil.yaappu.asai.AbstractAsai;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class RxRegistry implements IPropertyFinder {

    public static final Map<String, PatternGenerator> map = new HashMap<String, PatternGenerator>();

    IPropertyFinder parent = null;
    FeatureSet featureSet = null;

    public RxRegistry(IPropertyFinder parent, FeatureSet featureSet) {
        this.parent = parent;
        this.featureSet = featureSet;
    }


    static {


        map.put("எழுத்துவடிவம்", new TamilSymbolRx());
        map.put("!எழுத்துவடிவம்", new NonTamilSymbolRx("!எழுத்துவடிவம்"));
        map.put("எழுத்து", new AnyOneInTamilLetterSetRx("எழுத்து", "தமிழெழுத்தைக்குறிக்கிறது. எ.கா: ப ", EzhuththuUtils.filterAaytham(), EzhuththuUtils.filterUyir(), EzhuththuUtils.filterMei(), EzhuththuUtils.filterUyirMei()));
        map.put("!எழுத்து", new NonTamilSymbolRx("!எழுத்து"));

        Set<EzhuththuSetDescription> sets = TamilFactory.getTamilCharacterSetCalculator().getEzhuththuSetDescriptions();
        for (EzhuththuSetDescription set : sets) {
            if (PatternGenerator.class.isAssignableFrom(set.getClass())) {
                map.put(set.getName(), (PatternGenerator) set);
            } else {
                map.put(set.getName(), new AnyOneInTamilLetterSetRx(set.getName(), set.getDescription(), set.getCharacterSet()));
            }
        }
        map.put("கொக்கி", new TamilGlyphRx());

        map.put("குற்றுக்குறில்", new Kuttukkurril());
        map.put("குற்றுநெடில்", new KuttuNtedil());


        map.put("மொழி", new MozhiRx());

        map.put("நேர்", new NtearRx());
        map.put("நிரை", new NtiraiRx());
        map.put("நாள்", new NtaalhRx());
        map.put("மலர்", new MalarRx());

        map.put("நேர்பு", new NtearbuRx());
        map.put("நிரைபு", new NtiraibuRx());

        map.put("காசு", new KaasuRx());
        map.put("பிறப்பு", new PirrappuRx());

        map.put("தேமா", new TheamaRx());
        map.put("புளிமா", new PulhimaRx());
        map.put("கூவிளம்", new KoovilhamRx());
        map.put("கருவிளம்", new Karuvilham());

        map.put("தேமாங்காய்", new TheamaangaayRx());
        map.put("புளிமாங்காய்", new PulhimaangaayRx());
        map.put("கூவிளங்காய்", new KoovilhangaayRx());
        map.put("கருவிளங்காய்", new KaruvilhangaayRx());
        map.put("தேமாங்கனி", new TheamaanganiRx());
        map.put("புளிமாங்கனி", new PulhimaanganiRx());
        map.put("கூவிளங்கனி", new KoovilhanganiRx());
        map.put("கருவிளங்கனி", new KaruvilhanganiRx());

        map.put("அசை", new Asai());
        map.put("காய்ச்சீர்", new Kaaychcheer());
        map.put("கனிச்சீர்", new Kanichcheer());
        map.put("மாச்சீர்", new Maachcheer());
        map.put("விளச்சீர்", new Vilhachcheer());

        map.put("ஈரசைச்சீர்", new Eerasaichcheer());
        map.put("மூவசைச்சீர்", new Moovasaichcheer());

        map.put("மாமுன் நிரை", new MaaMunNtiraiRx());
        map.put("விளம்முன் நேர்", new VilhamMunNtear());
        map.put("இயற்சீர்வெண்டளை", new IyarrCirVendalhaiRx());

        map.put("வெண்டளை", new VendalhaiRx());
        map.put("வெண்சீர்வெண்டளை", new VenhCirVendalhaiRx());
        map.put("இடைவெளி", new IdaivelhiRx());
        map.put("வெண்பாவின் இறுதிச்சீர்", new VenhbaLastCirRx());
        map.put("குறளின் சீரமைப்பு", new KurralhCirRx());
        map.put("குறளின் தளையமைப்பு", new KurralhThalaiRx());
        map.put("குறள்", new KurralRx());

        map.put("அரைமாத்திரை", new HalfRx());
        map.put("ஒருமாத்திரை", new OneRx());
        map.put("இருமாத்திரை", new TwoRx());


    }

    public String findProperty(String p1) {
        String translit = null;
        PatternGenerator gen = null;
        if (parent != null && featureSet.isFeatureEnabled(RXOverrideSysDefnFeature.class)) {
            String alias = parent.findProperty(p1);
            if (alias != null) {
                return alias;
            }
            translit =  TamilFactory.getTransliterator(null).transliterate(p1).toString();
            alias = parent.findProperty(translit);
            if (alias != null) {
                return alias;
            }

        }

        gen = map.get(p1);

        if (gen == null) {
            if (translit == null) {
                translit =  TamilFactory.getTransliterator(null).transliterate(p1).toString();
            }
            p1 = translit;
            gen = map.get(p1);
        }
        if (gen == null) {

            if (p1.startsWith("(")) {
                String inner = p1.substring(1, p1.length());
                if (p1.endsWith(")")) {
                    inner = p1.substring(0, p1.length() - 1);
                    return "\\b${" + inner + "}\\b";

                } else {
                    return "\\b${" + inner + "}";
                }
            } else if (p1.endsWith(")")) {
                String inner = p1.substring(0, p1.length() - 1);
                return "${" + inner + "}\\b";

            }
            if (p1.startsWith("[") && p1.endsWith("]")) {
                String inner = p1.substring(1, p1.length() - 1);
                TamilWord literal = TamilWord.from(inner, true);
                return literal.toUnicodeStringRepresentation();
            }

            if (p1.startsWith("தளை[") && p1.endsWith("]")) {
                String inner = p1.substring(4, p1.length() - 1).trim();
                int munIndex = inner.indexOf(" முன் ");
                if (munIndex < 1) {
                    throw new TamilPatternSyntaxException("Invalid definition for தளை. It should be of the form ${தளை[(மாச்சீர்) முன் நேர்]} ", p1, 0);
                }
                String first = inner.substring(0, munIndex).trim();
                String second = inner.substring(munIndex + 6).trim();
                if (second.length() == 0) {
                    throw new TamilPatternSyntaxException("Invalid definition for தளை. It should be of the form ${தளை[(மாச்சீர்) முன் நேர்]} ", p1, 0);
                }
                //            String contextFirst = StringUtils.replaceWithContext("${", "}", "${" + first +"}", this, true, true, true).finalString;
//                String  contextSecond = StringUtils.replaceWithContext("${", "}", "${" + second +"}", this, true, true, true).finalString;
                //     contextFirst = contextFirst.replaceAll("\\*", "{0,10}");
                //   contextFirst = contextFirst.replaceAll("\\+", "{1,10}");

//                contextSecond = contextSecond.replaceAll("\\*", "{0,10}");
//                contextSecond = contextSecond.replaceAll("\\+", "{1,10}");
                //  first ="ezhuththu";
                StringBuffer buffer = new StringBuffer();
                // buffer.append("(?<=(${" + first +"}))\\s+");
                // buffer.append("(?<=(" + contextFirst +"))\\s+");
                buffer.append("${" + first + "}${idaivelhi}");
                buffer.append("(?=(${" + second + "}))");
                String ret = buffer.toString();

                return ret;

            }
            if (p1.startsWith("அசை[") && p1.endsWith("]")) {
                String inner = p1.substring(4, p1.length() - 1);
                Iterator<AbstractAsai> it = new AsaiIterator(TamilWord.from(inner, true));
                StringBuffer buffer = new StringBuffer();
                while (it.hasNext()) {
                    AbstractAsai s = it.next();
                    if (s.isNtear()) {
                        buffer.append("${ntear}");
                    } else {
                        buffer.append("${ntirai}");
                    }
                }
                return buffer.toString();

            }
            if (p1.startsWith("மாத்திரை[") && p1.endsWith("]")) {
                String inner = p1.substring(9, p1.length() - 1);
                StringBuffer buffer = new StringBuffer();
                TamilWord w = TamilWord.from(inner, true);
                for (int i = 0; i < w.length(); i++) {
                    AbstractCharacter ch = w.charAt(i);
                    if (ch.isPureTamilLetter()) {
                        TamilCharacter p = ch.asTamilCharacter();
                        if (p.isKurilezhuththu()) {
                            buffer.append("${kurril}");
                        } else if (p.isNtedilezhuththu()) {
                            buffer.append("${ntedil}");
                        } else if (p.isMeyyezhuththu()) {
                            buffer.append("(${mey}|${aaytham})");
                        } else {
                            buffer.append("(${mey}|${aaytham})");
                        }

                    } else {
                        for (int j = 0; j < ch.getMinCodePointsCount(); j++) {
                            buffer.append(".");
                        }
                    }

                }
                return buffer.toString();

            }
            if (p1.startsWith("வகை[") && p1.endsWith("]")) {
                String inner = p1.substring(4, p1.length() - 1);
                StringBuffer buffer = new StringBuffer();
                TamilWord w = TamilWord.from(inner, true);
                for (int i = 0; i < w.length(); i++) {
                    AbstractCharacter ch = w.charAt(i);
                    if (ch.isPureTamilLetter()) {
                        TamilCharacter p = ch.asTamilCharacter();
                        if (p.isUyirezhuththu()) {
                            buffer.append("${uyir}");
                        } else if (p.isMeyyezhuththu()) {
                            buffer.append("${mey}");
                        } else if (p.isUyirMeyyezhuththu()) {
                            buffer.append("${uyirmey}");
                        } else {
                            buffer.append("${aaytham}");
                        }

                    } else {
                        for (int j = 0; j < ch.getMinCodePointsCount(); j++) {
                            buffer.append(".");
                        }
                    }

                }
                return buffer.toString();

            }

            if (p1.startsWith("மெய்வகை[") && p1.endsWith("]")) {
                String inner = p1.substring(8, p1.length() - 1);
                StringBuffer buffer = new StringBuffer();
                TamilWord w = TamilWord.from(inner, true);
                for (int i = 0; i < w.length(); i++) {
                    AbstractCharacter ch = w.charAt(i);
                    if (ch.isPureTamilLetter()) {
                        TamilCharacter p = ch.asTamilCharacter();
                        if (p.isUyirezhuththu()) {
                            buffer.append("${uyir}");
                        } else if (p.isAaythavezhuththu()) {
                            buffer.append("${aaytham}");
                        } else if (p.isVallinam()) {
                            buffer.append("${vali}");
                        } else if (p.isMellinam()) {
                            buffer.append("${meli}");
                        } else if (p.isIdaiyinam()) {
                            buffer.append("${idai}");
                        } else {
                            throw new TamilPlatformException("Unexpected char:" + p.toString());
                        }

                    } else {
                        for (int j = 0; j < ch.getMinCodePointsCount(); j++) {
                            buffer.append(".");
                        }
                    }

                }
                return buffer.toString();

            }

            if (parent != null) {
                String alias = parent.findProperty(p1);
                if (alias != null) {
                    return alias;
                }
            }
            //Do split and figure out
            Set<TamilCharacter> chars = TamilFactory.getTamilCharacterSetCalculator().find(p1);
            if (chars == null) {

                return null;
            }
            gen = new AnyOneInTamilLetterSetRx(p1, chars);
        }
        return gen.generate(featureSet);
//        String groupName = TamilUtils.toRxGroupName(TamilWord.from(gen.getName(), true).translitToEnglish());
//        if (generatedPatterns.containsKey(gen.getName())) {
//            return "(\\k<" + groupName + ">)";
//        } else {
//
//            generatedPatterns.put(gen.getName(), gen);
//            String ex = "(?<" + groupName + ">(" + gen.generate() + "))";
//            return ex;
//        }

    }

    private Map<String, PatternGenerator> generatedPatterns = new HashMap<String, PatternGenerator>();


}
