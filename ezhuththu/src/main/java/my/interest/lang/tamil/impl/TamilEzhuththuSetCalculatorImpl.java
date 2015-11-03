package my.interest.lang.tamil.impl;

import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.impl.dictionary.DefaultPlatformDictionaryBase;
import my.interest.lang.tamil.impl.rx.AnyOneInTamilLetterSetRx;


import my.interest.lang.tamil.parser.impl.sax.SaxParser;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.api.ezhuththu.EzhuththuSetDescription;
import tamil.lang.api.ezhuththu.TamilCharacterSetCalculator;
import tamil.lang.api.parser.*;
import tamil.lang.exception.TamilPlatformException;
import tamil.lang.known.IKnownWord;

import tamil.lang.known.non.derived.AbstractKnownWord;
import tamil.lang.known.non.derived.Peyarchchol;
import tamil.lang.known.non.derived.idai.Ottu;

import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilEzhuththuSetCalculatorImpl implements TamilCharacterSetCalculator {

    public  static  final TamilEzhuththuSetCalculatorImpl DEFAULT = new TamilEzhuththuSetCalculatorImpl();
    static final TamilDictionary LetterDictionary = new DefaultPlatformDictionaryBase() {

    };

    static final class letter_known extends AbstractKnownWord {
        public boolean isEthirMarrai() {
            return ethirMarrai;
        }

        private boolean ethirMarrai = false;

        public letter_known(TamilWord word) {
            super(word);
        }

        public letter_known(TamilWord word, boolean ethirMarrai) {
            super(word);
            this.ethirMarrai = ethirMarrai;
        }
    }

    static final Map<String, EzhuththuSetDescription> map = new HashMap<String, EzhuththuSetDescription>();

    static {
        map.put("எழுத்து", new AnyOneInTamilLetterSetRx("எழுத்து", "தமிழெழுத்தைக்குறிக்கிறது. எ.கா: ப ", EzhuththuUtils.filterAaytham(), EzhuththuUtils.filterUyir(), EzhuththuUtils.filterMei(), EzhuththuUtils.filterUyirMei()));
        map.put("குறில்", new AnyOneInTamilLetterSetRx("குறில்", "குறிலெழுத்தைக்குறிக்கிறது. எ.கா: க", EzhuththuUtils.filterKuRil()));
        map.put("!குறில்", new AnyOneInTamilLetterSetRx("!குறில்", "குறிலல்லாவெழுத்தைக்குறிக்கிறது. எ.கா) ஆ, க்" , EzhuththuUtils.filterOut(EzhuththuUtils.filterKuRil())));

        map.put("நெடில்", new AnyOneInTamilLetterSetRx("நெடில்","நெடிலெழுத்தைக்குறிக்கிறது எ.கா) ஆ, கீ", EzhuththuUtils.filterNedil()));
        map.put("!நெடில்", new AnyOneInTamilLetterSetRx("!நெடில்","நெடிலல்லாவெழுத்தைக்குறிக்கிறது எ.கா) க், அ, ஃ", EzhuththuUtils.filterOut(EzhuththuUtils.filterNedil())));

        map.put("உயிர்", new AnyOneInTamilLetterSetRx("உயிர்","உயிரெழுத்தைக்குறிக்கிறது எ.கா) அ, ஔ   ", EzhuththuUtils.filterUyir()));
        map.put("!உயிர்", new AnyOneInTamilLetterSetRx("!உயிர்","உயிரல்லாவெழுத்தைக்குறிக்கிறது எ.கா) க், நா, ஃ ", EzhuththuUtils.filterOut(EzhuththuUtils.filterUyir())));

        map.put("மெய்", new AnyOneInTamilLetterSetRx("மெய்","மெய்யெழுத்தைக்குறிக்கிறது எ.கா) ங், க் ", EzhuththuUtils.filterMei()));
        map.put("!மெய்", new AnyOneInTamilLetterSetRx("!மெய்","மெய்யல்லாவெழுத்தைக்குறிக்கிறது எ.கா) அ, கா, ஃ", EzhuththuUtils.filterOut(EzhuththuUtils.filterMei())));


        map.put("!உயிர்மெய்", new AnyOneInTamilLetterSetRx("!உயிர்மெய்","உயிர்மெய்யல்லாவெழுத்தைக்குறிக்கிறது. எ.கா) க், ஃ, ஆ", EzhuththuUtils.filterOut(EzhuththuUtils.filterUyirMei())));
        map.put("உயிர்மெய்", new AnyOneInTamilLetterSetRx("உயிர்மெய்","உயிர்மெய்யெழுத்தைக்குறிக்கிறது எ.கா) கா, சூ, ப", EzhuththuUtils.filterUyirMei()));

        map.put("ஆய்தம்", new AnyOneInTamilLetterSetRx("ஆய்தம்","ஆய்தவெழுத்தைக்குறிக்கிறது (ஃ)", EzhuththuUtils.filterAaytham()));
        map.put("!ஆய்தம்", new AnyOneInTamilLetterSetRx("!ஆய்தம்","ஆய்தமல்லாவெழுத்தைக்குறிக்கிறது. எ.கா) ஆ, க ,ந்", EzhuththuUtils.filterOut(EzhuththuUtils.filterAaytham())));

        map.put("முதல்", new AnyOneInTamilLetterSetRx("முதல்","முதலெழுத்தைக்குறிக்கிறது எ.கா) க், அ, ஐ", EzhuththuUtils.filterUyir(), EzhuththuUtils.filterMei()));
        map.put("!முதல்", new AnyOneInTamilLetterSetRx("!முதல்","முதலெழுத்தல்லாவெழுத்தைக்க்குறிக்கிறது எ.கா) ஃ, கா , மீ", EzhuththuUtils.filterAaytham(), EzhuththuUtils.filterUyirMei()));


        map.put("!வலி", new AnyOneInTamilLetterSetRx("!வலி","வலியல்லாவெழுத்தைக்குறிக்கிறது எ.கா) ங், ம, ஆ", EzhuththuUtils.filterOut(EzhuththuUtils.filterVali())));
        map.put("வலி", new AnyOneInTamilLetterSetRx("வலி","வலியெழுத்தைக்குறிக்கிறது எ.கா) க, சா, ப்", EzhuththuUtils.filterVali()));

        map.put("!மெலி", new AnyOneInTamilLetterSetRx("!மெலி","மெலியல்லாவெழுத்தைக்குறிக்கிறது எ.கா) ங், நா , மௌ             ", EzhuththuUtils.filterOut(EzhuththuUtils.filterMeli())));
        map.put("மெலி", new AnyOneInTamilLetterSetRx("மெலி","மெலியெழுத்தைக்குறிக்கிறது எ.கா) மா, ந், ணௌ ", EzhuththuUtils.filterMeli()));


        map.put("!இடை", new AnyOneInTamilLetterSetRx("!இடை","இடையல்லாவெழுத்தைக்குறிக்கிறது எ.கா) க், நீ, ம", EzhuththuUtils.filterOut(EzhuththuUtils.filterIdai())));
        map.put("இடை", new AnyOneInTamilLetterSetRx("இடை","இடையெழுத்தைக்குறிக்கிறது எ.கா) ய, ர், வீ", EzhuththuUtils.filterIdai()));


        map.put("!மொழிமுதல்", new AnyOneInTamilLetterSetRx("!மொழிமுதல்","மொழிமுதலல்லாவெழுத்தைக்குறிக்கிறது எ.கா) ன, ர, க்    ", EzhuththuUtils.filterOut(EzhuththuUtils.filterMozhiMuthal())));
        map.put("மொழிமுதல்", new AnyOneInTamilLetterSetRx("மொழிமுதல்","மொழிமுதலெழுத்தைக்குறிக்கிறது. எ.கா) ஆ, க , நீ", EzhuththuUtils.filterMozhiMuthal()));

        map.put("!மொழிக்கடை", new AnyOneInTamilLetterSetRx("!மொழிக்கடை","மொழிக்கடையல்லாவெழுத்தைக்குறிக்கிறது எ.கா) ந் , க் , ஆ ", EzhuththuUtils.filterOut(EzhuththuUtils.filterMozhiLast())));
        map.put("மொழிக்கடை", new AnyOneInTamilLetterSetRx("மொழிக்கடை","மொழிக்கடையெழுத்தைக்குறிக்கிறது எ.கா) ன், ம் ,கு ", EzhuththuUtils.filterMozhiLast()));


        map.put("!மொழியிடை", new AnyOneInTamilLetterSetRx("!மொழியிடை","மொழியிடையல்லாவெழுத்தைக்குறிக்கிறது எ.கா) ஆ, எ, ஔ", EzhuththuUtils.filterOut(EzhuththuUtils.filterMozhiYidai())));
        map.put("மொழியிடை", new AnyOneInTamilLetterSetRx("மொழியிடை","மொழியிடையெழுத்தைக்குறிக்கிறது எ.கா) க், ச, மா", EzhuththuUtils.filterMozhiYidai()));


        map.put("!வடமொழியெழுத்து", new AnyOneInTamilLetterSetRx("!வடமொழியெழுத்து","வடமொழியெழுத்தல்லாவெழுத்தைக்குறிக்கிறது எ.கா) அ, ஃ, க், தூ", EzhuththuUtils.filterOut(EzhuththuUtils.filterVadaMozhi())));
        map.put("வடமொழியெழுத்து", new AnyOneInTamilLetterSetRx("வடமொழியெழுத்து","வடமொழியெழுத்தைக்குறிக்கிறது எ.கா) ஸ, ஷ், ஜ", EzhuththuUtils.filterVadaMozhi()));



        map.put("!ஓரெழுத்துமொழி", new AnyOneInTamilLetterSetRx("!ஓரெழுத்துமொழி", "தனிச்சொல்லாயிருக்கவியலாவெழுத்தைக்குறிக்கிறது எகா) க், ம, அ",EzhuththuUtils.filterOut(EzhuththuUtils.filterOarezhutthuMozhi())));
        map.put("ஓரெழுத்துமொழி", new AnyOneInTamilLetterSetRx("ஓரெழுத்துமொழி","தனிச்சொல்லாயிருக்கவல்லவெழுத்தைக்குறிக்கிறது எ.கா) மா, ஆ, கோ ", EzhuththuUtils.filterOarezhutthuMozhi()));




        map.put("!ஓரெண்", new AnyOneInTamilLetterSetRx("!ஓரெண்", "ஒருங்குறியில் (single code point) ஓரெண்கொண்டுவரையறுக்கப்படாத  எழுத்துகள்",EzhuththuUtils.filterOut(EzhuththuUtils.filterWithSingleCodePoint())));
        map.put("ஓரெண்", new AnyOneInTamilLetterSetRx("ஓரெண்","ஒருங்குறியில்  (single code point) ஓரெண்கொண்டுவரையறுக்கப்பட்டுள்ள எழுத்துகள் ", EzhuththuUtils.filterWithSingleCodePoint()));


//        map.put("!ஈரெண்", new AnyOneInTamilLetterSetRx("!ஈரெண்", "யூனிகோட்டில் (two code points) ஈரெண்கொண்டுவரையறுக்கப்படாத  எழுத்துகள்",EzhuththuUtils.filterOut(EzhuththuUtils.filterWithCodePointSeries(1))));
//        map.put("ஈரெண்", new AnyOneInTamilLetterSetRx("ஈரெண்","யூனிகோட்டில் ஓரெண்கொண்டு (two code points) ஈரெண்கொண்டுவரையறுக்கப்பட்டுள்ள எழுத்துகள் ", EzhuththuUtils.filterWithCodePointSeries(1)));
//



        map.put("!ஓரெண்கொண்டவரிசை", new AnyOneInTamilLetterSetRx("!ஓரெண்கொண்டவரிசை", "ஒருங்குறியில் ஓரெண்ணைக்கொண்டிருக்கும்வரிசையால் குறிக்கப்படவியலாத எழுத்துகள்.",EzhuththuUtils.filterOut(EzhuththuUtils.filterWithCodePointSeries(1))));
        map.put("ஓரெண்கொண்டவரிசை", new AnyOneInTamilLetterSetRx("ஓரெண்கொண்டவரிசை","ஒருங்குறியில் ஓரெண்ணைக்கொண்டிருக்கும்வரிசையால் குறிக்கப்படவல்ல எழுத்துகள்.", EzhuththuUtils.filterWithCodePointSeries(1)));


        map.put("!ஈரெண்கள்கொண்டவரிசை", new AnyOneInTamilLetterSetRx("!ஈரெண்கள்கொண்டவரிசை", "ஒருங்குறியில்  ஈரெண்களைக்கொண்டிருக்கும்வரிசையால் குறிக்கப்படவியலாத  எழுத்துகள். ",EzhuththuUtils.filterOut(EzhuththuUtils.filterWithCodePointSeries(2))));
        map.put("ஈரெண்கள்கொண்டவரிசை", new AnyOneInTamilLetterSetRx("ஈரெண்கள்கொண்டவரிசை","ஒருங்குறியில்  ஈரெண்களைக்கொண்டிருக்கும்வரிசையால் குறிக்கப்படவல்ல எழுத்துகள். ", EzhuththuUtils.filterWithCodePointSeries(2)));


        map.put("!மூவெண்கள்கொண்டவரிசை", new AnyOneInTamilLetterSetRx("!மூவெண்கள்கொண்டவரிசை", "ஒருங்குறியில்  மூவெண்களைக்கொண்டிருக்கும்வரிசையால் குறிக்கப்படவியலாத  எழுத்துகள்.",EzhuththuUtils.filterOut(EzhuththuUtils.filterWithCodePointSeries(3))));
        map.put("மூவெண்கள்கொண்டவரிசை", new AnyOneInTamilLetterSetRx("மூவெண்கள்கொண்டவரிசை","ஒருங்குறியில்  மூவெண்களைக்கொண்டிருக்கும்வரிசையால் குறிக்கப்படவல்ல   எழுத்துகள்.", EzhuththuUtils.filterWithCodePointSeries(3)));

        map.put("!தனியெண்வரிசை", new AnyOneInTamilLetterSetRx("!தனியெண்வரிசை", "ஒருங்குறியில் தனது எண்வரிசையை வேறு எழுத்துகளுடன் பகிரும் எழுத்துகள். ",EzhuththuUtils.filterOut(EzhuththuUtils.filterWithIndependentCodePoints())));
        map.put("தனியெண்வரிசை", new AnyOneInTamilLetterSetRx("தனியெண்வரிசை","ஒருங்குறியில் தனது எண்வரிசையை வேறெந்த எழுத்துகளுடனும் பகிராத எழுத்துகள்.", EzhuththuUtils.filterWithIndependentCodePoints()));

        map.put("!இதழ்குவிவரிசை", new AnyOneInTamilLetterSetRx("!இதழ்குவிவரிசை", "இதழ்களின் குவிப்பு தேவையில்லாமல் ஒலிக்கப்படும் எழுத்துகள். ",EzhuththuUtils.filterOut(EzhuththuUtils.filterWithLipsSpread())));
        map.put("இதழ்குவிவரிசை", new AnyOneInTamilLetterSetRx("இதழ்குவிவரிசை","இதழ்களை குவித்து ஒலிக்கப்படும் எழுத்துகள்.", EzhuththuUtils.filterWithLipsSpread()));

        map.put("!இதழ்மூடுவரிசை", new AnyOneInTamilLetterSetRx("!இதழ்மூடுவரிசை", "இதழ்களை மூடாமல் ஒலிக்கப்படும் எழுத்துகள். ",EzhuththuUtils.filterOut(EzhuththuUtils.filterWithLipsClosed())));
        map.put("இதழ்மூடுவரிசை", new AnyOneInTamilLetterSetRx("இதழ்மூடுவரிசை","இதழ்களை மூடிக்கொண்டு ஒலிக்கப்படும் எழுத்துகள்.", EzhuththuUtils.filterWithLipsClosed()));



        Set<TamilCharacter> meis = EzhuththuUtils.filterMei();
        for (TamilCharacter mei : meis) {
            TamilWord karam = new TamilWord();
            TamilCharacter first = mei.addUyir(TamilSimpleCharacter.a);
            if (!first.isWordToStartWith()) {
                karam.add(TamilSimpleCharacter.E);
            }
            karam.add(first);
            karam.addAll(TamilWord.from("கரவரிசை"));
            Set<TamilCharacter> karams = EzhuththuUtils.filterUyirMeiWithMei(mei);
            karams.add(mei);
            map.put(karam.toString(), new AnyOneInTamilLetterSetRx(karam.toString(), karams));
            map.put("!" + karam.toString(), new AnyOneInTamilLetterSetRx("!" + karam.toString(), EzhuththuUtils.filterOut(karams)));

        }

        Set<TamilCharacter> uyirs = EzhuththuUtils.filterUyir();
        for (TamilCharacter u : uyirs) {
            TamilWord karam = new TamilWord();
            karam.add(u);
            if (u.isKurilezhuththu()) {
                karam.addAll(TamilWord.from("கரவரிசை"));
            } else {
                karam.addAll(TamilWord.from("காரவரிசை"));
            }

            Set<TamilCharacter> karams = EzhuththuUtils.filterUyirMeiWithUyir(u);
            karams.add(u);
            map.put(karam.toString(), new AnyOneInTamilLetterSetRx(karam.toString(), karams));
            map.put("!" + karam.toString(), new AnyOneInTamilLetterSetRx("!" + karam.toString(), EzhuththuUtils.filterOut(karams)));

        }


        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            LetterDictionary.add(new letter_known(TamilWord.from(key)));
        }

        LetterDictionary.add(new letter_known(TamilWord.from("அல்லாத"), true));
        LetterDictionary.add(new letter_known(TamilWord.from("அல்லா"), true));
        LetterDictionary.add(new letter_known(TamilWord.from("அல்லாதது"), true));
        LetterDictionary.add(new letter_known(TamilWord.from("அல்லாதவை"), true));

        LetterDictionary.add(Ottu.ICH);
        LetterDictionary.add(Ottu.IK);
        LetterDictionary.add(Ottu.IP);
        LetterDictionary.add(Ottu.ITH);


        //These will be ignored.
        LetterDictionary.add(new Peyarchchol(TamilWord.from("எழுத்து"), 0));
        LetterDictionary.add(new Peyarchchol(TamilWord.from("இனம்"), 0));
        LetterDictionary.add(new Peyarchchol(TamilWord.from("கள்"), 0));



    }



    public Set<TamilCharacter> find(String query) throws TamilPlatformException {
        if (query==null) {
            return Collections.emptySet();
        }
        query = query.replaceAll("\\s+", " ");
        boolean negate = false;
        if (query.startsWith("!")) {
            query = query.substring(1, query.length());
            negate = true;
        }
        String[] ors = query.split(" அல்லது ");
        Set<TamilCharacter> ret = new LinkedHashSet<TamilCharacter>();
        for (String or : ors) {
            ret.addAll(  resolve(or));
        }
        if (negate) {
            return  EzhuththuUtils.filterOut(ret);
        }  else {
            return ret;
        }

    }


    public Set<EzhuththuSetDescription> getEzhuththuSetDescriptions() {
        return Collections.unmodifiableSet(new LinkedHashSet<EzhuththuSetDescription>( map.values()));
    }


    private Set<TamilCharacter> resolve(String compound) {

        EzhuththuSetDescription desc = map.get(compound);
        if (desc == null) {
            return resolve(resolveSimpleToParsedList(compound));
        }  else {
            return desc.getCharacterSet();
        }

    }

    List<IKnownWord> resolveSimpleToParsedList(String compound) {

        if (compound.startsWith("!")) {
            compound = compound.substring(1, compound.length());
            List<IKnownWord> parsed = resolveSimpleToParsedList(compound);
            parsed.add(new letter_known(TamilWord.from("!"), true));
            return parsed;
        } else {
            String[] splits = compound.split(" ");
            List<IKnownWord> ret = new ArrayList<IKnownWord>();
            for (String sp: splits) {
                ParserResultCollection resultCollection = new SaxParser().parse(TamilWord.from(sp), 10, new ParseWithCustomDictionary(LetterDictionary));

                if (resultCollection.isEmpty()) {
                    throw new TamilPlatformException("Unable to parse:" + sp);
                }
                ParserResult result = resultCollection.getList().get(0);
                if (!result.isParsed()) {
                    throw new TamilPlatformException("Unable to parse:" + sp);
                }
                ret.addAll(result.getSplitWords());
            }
            return ret;


        }
    }


    private Set<TamilCharacter> resolve(List<IKnownWord> parsed) {
        EzhuththuSetDescription gen = map.get(parsed.get(0).getWord().toString());
        if (gen == null) {
            throw new TamilPlatformException("Unknown letter set:" + parsed.get(0).getWord().toString());
        }



        Set<TamilCharacter> set =  gen.getCharacterSet();
        for (int i = 1; i < parsed.size(); i++) {
            IKnownWord known = parsed.get(i);
            if (letter_known.class.isAssignableFrom(known.getClass())) {
                letter_known lknown = (letter_known) known;
                if (lknown.isEthirMarrai()) {
                    set = EzhuththuUtils.filterOut(set);
                } else {
                    gen = map.get(known.getWord().toString());
                    if (gen == null) {
                        throw new TamilPlatformException("Unknown letter set:" + known.getWord().toString());
                    }

                    Set<TamilCharacter> subset =  gen.getCharacterSet();
                    set = EzhuththuUtils.filterIntersection(set, subset);
                }


            } else {
                continue;

            }
        }
        return set;
    }
}
