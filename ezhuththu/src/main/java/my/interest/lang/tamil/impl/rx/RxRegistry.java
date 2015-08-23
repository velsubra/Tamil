package my.interest.lang.tamil.impl.rx;

import common.lang.impl.AbstractCharacter;
import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.impl.rx.asai1.NtearRx;
import my.interest.lang.tamil.impl.rx.asai1.NtearbuRx;
import my.interest.lang.tamil.impl.rx.asai1.NtiraiRx;
import my.interest.lang.tamil.impl.rx.asai1.NtiraibuRx;
import my.interest.lang.tamil.impl.rx.asai2.Karuvilham;
import my.interest.lang.tamil.impl.rx.asai2.KoovilhamRx;
import my.interest.lang.tamil.impl.rx.asai2.PulhimaRx;
import my.interest.lang.tamil.impl.rx.asai2.TheamaRx;
import my.interest.lang.tamil.impl.rx.asai3.*;
import my.interest.lang.tamil.impl.yaappu.SeerIterator;
import my.interest.lang.tamil.internal.api.IPropertyFinder;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.ezhuththu.EzhuththuDescription;
import tamil.lang.exception.TamilPlatformException;
import tamil.yaappu.seer.AbstractSeer;

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

    public RxRegistry(IPropertyFinder parent) {
        this.parent = parent;
    }


    static {


        map.put("எழுத்துவடிவம்", new TamilSymbolRx());
        map.put("!எழுத்துவடிவம்", new NonTamilSymbolRx("!எழுத்துவடிவம்"));
        map.put("எழுத்து", new AnyOneInTamilLetterSetRx("எழுத்து", "தமிழெழுத்தைக்குறிக்கிறது. எ.கா: ப ", EzhuththuUtils.filterAaytham(), EzhuththuUtils.filterUyir(), EzhuththuUtils.filterMei(), EzhuththuUtils.filterUyirMei()));
        map.put("!எழுத்து", new NonTamilSymbolRx("!எழுத்து"));

        Set<EzhuththuDescription> sets = TamilFactory.getTamilCharacterSetCalculator().getEzhuththuDescriptions();
        for (EzhuththuDescription set : sets) {
            if (PatternGenerator.class.isAssignableFrom(set.getClass())) {
                map.put(set.getName(), (PatternGenerator) set);
            } else {
                map.put(set.getName(), new AnyOneInTamilLetterSetRx(set.getName(), set.getDescription(), set.getCharacterSet()));
            }
        }


        map.put("மொழி", new MozhiRx());

        map.put("நேர்", new NtearRx());
        map.put("நிரை", new NtiraiRx());
        map.put("நாள்", new NtearRx());
        map.put("மலர்", new NtiraiRx());

        map.put("நேர்பு", new NtearbuRx());
        map.put("நிரைபு", new NtiraibuRx());

        map.put("காசு", new NtearbuRx());
        map.put("பிறப்பு", new NtiraibuRx());

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


    }

    public String findProperty(String p1) {
        PatternGenerator gen = map.get(p1);
        if (gen == null) {
            p1 = TamilFactory.getTransliterator(null).transliterate(p1).toString();
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
            if (p1.startsWith("அசை[") && p1.endsWith("]")) {
                String inner = p1.substring(4, p1.length() - 1);
                Iterator<AbstractSeer> it = new SeerIterator(TamilWord.from(inner, true));
                StringBuffer buffer = new StringBuffer();
                while (it.hasNext()) {
                    AbstractSeer s = it.next();
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
                        for (int j = 0; j < ch.getCodePointsCount(); j++) {
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
                        for (int j = 0; j < ch.getCodePointsCount(); j++) {
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
                        for (int j = 0; j < ch.getCodePointsCount(); j++) {
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
            return new AnyOneInTamilLetterSetRx(p1, chars).generate();
        } else {
            return gen.generate();
        }
    }


}
