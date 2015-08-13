package my.interest.lang.tamil.impl.rx;

import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.impl.rx.asai1.NtearRx;
import my.interest.lang.tamil.impl.rx.asai1.NtearbuRx;
import my.interest.lang.tamil.impl.rx.asai1.NtiraiRx;
import my.interest.lang.tamil.impl.rx.asai1.NtiraibuRx;
import my.interest.lang.tamil.impl.rx.asai2.Karuvilham;
import my.interest.lang.tamil.impl.rx.asai2.KoovilhamRx;
import my.interest.lang.tamil.impl.rx.asai2.PulhimaRx;
import my.interest.lang.tamil.impl.rx.asai2.TheamaRx;
import my.interest.lang.tamil.impl.rx.asai3.Theamaangaay;
import my.interest.lang.tamil.internal.api.IPropertyFinder;
import my.interest.lang.tamil.internal.api.UnicodePatternGenerator;
import tamil.lang.TamilFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class RxRegistry implements IPropertyFinder {

    static final Map<String, UnicodePatternGenerator> map = new HashMap<String, UnicodePatternGenerator>();

    static {
        map.put("எழுத்துவடிவம்", new TamilSymbolRx());
        map.put("!எழுத்துவடிவம்", new NonTamilSymbolRx());
        map.put("எழுத்து", new AnyOneInTamilLetterSetRx("எழுத்து", EzhuththuUtils.filterAaytham(), EzhuththuUtils.filterUyir(), EzhuththuUtils.filterMei(), EzhuththuUtils.filterUyirMei()));
        map.put("!எழுத்து", new NonTamilSymbolRx());

        map.put("குறில்", new AnyOneInTamilLetterSetRx("குறில்", EzhuththuUtils.filterKuRil()));
        map.put("!குறில்", new AnyOneInTamilLetterSetRx("!குறில்", EzhuththuUtils.filterOut(EzhuththuUtils.filterKuRil())));

        map.put("நெடில்", new AnyOneInTamilLetterSetRx("நெடில்", EzhuththuUtils.filterNedil()));
        map.put("!நெடில்", new AnyOneInTamilLetterSetRx("!நெடில்", EzhuththuUtils.filterOut(EzhuththuUtils.filterNedil())));

        map.put("உயிர்", new AnyOneInTamilLetterSetRx("உயிர்", EzhuththuUtils.filterUyir()));
        map.put("!உயிர்", new AnyOneInTamilLetterSetRx("!உயிர்", EzhuththuUtils.filterOut(EzhuththuUtils.filterUyir())));

        map.put("மெய்", new AnyOneInTamilLetterSetRx("மெய்", EzhuththuUtils.filterMei()));
        map.put("!மெய்", new AnyOneInTamilLetterSetRx("!மெய்", EzhuththuUtils.filterOut(EzhuththuUtils.filterMei())));

        map.put("ஒற்று", new AnyOneInTamilLetterSetRx("ஒற்று", EzhuththuUtils.filterMei()));
        map.put("!ஒற்று", new AnyOneInTamilLetterSetRx("!ஒற்று", EzhuththuUtils.filterOut(EzhuththuUtils.filterMei())));

        map.put("!உயிர்மெய்", new AnyOneInTamilLetterSetRx("!உயிர்மெய்", EzhuththuUtils.filterOut(EzhuththuUtils.filterUyirMei())));
        map.put("உயிர்மெய்", new AnyOneInTamilLetterSetRx("உயிர்மெய்", EzhuththuUtils.filterUyirMei()));

        map.put("ஆய்தம்", new AnyOneInTamilLetterSetRx("ஆய்தம்", EzhuththuUtils.filterAaytham()));
        map.put("!ஆய்தம்", new AnyOneInTamilLetterSetRx("!ஆய்தம்", EzhuththuUtils.filterOut(EzhuththuUtils.filterAaytham())));
        map.put("முதல்", new AnyOneInTamilLetterSetRx("முதல்", EzhuththuUtils.filterUyir(), EzhuththuUtils.filterMei()));
        map.put("!முதல்", new AnyOneInTamilLetterSetRx("!முதல்", EzhuththuUtils.filterAaytham(), EzhuththuUtils.filterUyirMei()));

        map.put("!வலி", new AnyOneInTamilLetterSetRx("!வலி", EzhuththuUtils.filterOut(EzhuththuUtils.filterVali())));
        map.put("வலி", new AnyOneInTamilLetterSetRx("வலி", EzhuththuUtils.filterVali()));
        map.put("!வலி", new AnyOneInTamilLetterSetRx("!வலி", EzhuththuUtils.filterOut(EzhuththuUtils.filterVali())));
        map.put("வலி", new AnyOneInTamilLetterSetRx("வலி", EzhuththuUtils.filterVali()));
        map.put("!மெலி", new AnyOneInTamilLetterSetRx("!மெலி", EzhuththuUtils.filterOut(EzhuththuUtils.filterMeli())));
        map.put("மெலி", new AnyOneInTamilLetterSetRx("மெலி", EzhuththuUtils.filterMeli()));
        map.put("!இடை", new AnyOneInTamilLetterSetRx("!இடை", EzhuththuUtils.filterOut(EzhuththuUtils.filterIdai())));
        map.put("இடை", new AnyOneInTamilLetterSetRx("இடை", EzhuththuUtils.filterIdai()));


        map.put("!மொழிமுதல்", new AnyOneInTamilLetterSetRx("!மொழிமுதல்", EzhuththuUtils.filterOut(EzhuththuUtils.filterMozhiMuthal())));
        map.put("மொழிமுதல்", new AnyOneInTamilLetterSetRx("மொழிமுதல்", EzhuththuUtils.filterMozhiMuthal()));

        map.put("!மொழிக்கடை", new AnyOneInTamilLetterSetRx("!மொழிக்கடை", EzhuththuUtils.filterOut(EzhuththuUtils.filterMozhiLast())));
        map.put("மொழிக்கடை", new AnyOneInTamilLetterSetRx("மொழிக்கடை", EzhuththuUtils.filterMozhiLast()));


        map.put("!மொழியிடை", new AnyOneInTamilLetterSetRx("!மொழியிடை", EzhuththuUtils.filterOut(EzhuththuUtils.filterMozhiYidai())));
        map.put("மொழியிடை", new AnyOneInTamilLetterSetRx("மொழியிடை", EzhuththuUtils.filterMozhiYidai()));


        map.put("!வடமொழியெழுத்து", new AnyOneInTamilLetterSetRx("!வடமொழியெழுத்து", EzhuththuUtils.filterOut(EzhuththuUtils.filterVadaMozhi())));
        map.put("வடமொழியெழுத்து", new AnyOneInTamilLetterSetRx("வடமொழியெழுத்து", EzhuththuUtils.filterVadaMozhi()));


        map.put("!குற்றியலுகரம்", new AnyOneInTamilLetterSetRx("!குற்றியலுகரம்", EzhuththuUtils.filterOut(EzhuththuUtils.filterKuttiyalugaram())));
        map.put("குற்றியலுகரம்", new AnyOneInTamilLetterSetRx("குற்றியலுகரம்", EzhuththuUtils.filterKuttiyalugaram()));

        map.put("!ஓரெழுத்துமொழி", new AnyOneInTamilLetterSetRx("!ஓரெழுத்துமொழி", EzhuththuUtils.filterOut(EzhuththuUtils.filterOarezhutthuMozhi())));
        map.put("ஓரெழுத்துமொழி", new AnyOneInTamilLetterSetRx("ஓரெழுத்துமொழி", EzhuththuUtils.filterOarezhutthuMozhi()));

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

        map.put("தேமாங்காய்", new Theamaangaay());



    }

    public String findProperty(String p1) {
        UnicodePatternGenerator gen = map.get(p1);
        if (gen == null) {
            p1 = TamilFactory.getTransliterator(null).transliterate(p1).toString();
            gen = map.get(p1);
        }
        if (gen == null) {

            if (p1.startsWith("(") && p1.endsWith(")")) {
                String inner = p1.substring(1, p1.length() - 1);
                gen = map.get(inner);
                if (gen != null) {
                    return "\\b${" + inner + "}\\b";
                }  else {
                    return null;
                }
            }
            return null;
        } else {
            return gen.generate();
        }
    }
}
