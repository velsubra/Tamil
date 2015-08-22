package my.interest.lang.tamil.impl;

import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.impl.dictionary.DefaultPlatformDictionaryBase;
import my.interest.lang.tamil.impl.rx.AnyOneInTamilLetterSetRx;


import my.interest.lang.tamil.parser.impl.sax.SaxParser;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilFactory;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.api.ezhuththu.EzhuththuDescription;
import tamil.lang.api.ezhuththu.TamilCharacterSetCalculator;
import tamil.lang.api.parser.EagerlyParsingFeature;
import tamil.lang.api.parser.ParseWithDictionary;
import tamil.lang.api.parser.ParserResult;
import tamil.lang.api.parser.ParserResultCollection;
import tamil.lang.exception.TamilPlatformException;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.derived.Peyarechcham;
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

    static final Map<String, EzhuththuDescription> map = new HashMap<String, EzhuththuDescription>();

    static {
        map.put("குறில்", new AnyOneInTamilLetterSetRx("குறில்", "குறிலெழுத்தைக்குறிக்கிறது. எ.கா: க", EzhuththuUtils.filterKuRil()));
        map.put("!குறில்", new AnyOneInTamilLetterSetRx("!குறில்", "குறிலல்லாவெழுத்தைக்குறிக்கிறது. எ.கா) ஆ, க்" , EzhuththuUtils.filterOut(EzhuththuUtils.filterKuRil())));

        map.put("நெடில்", new AnyOneInTamilLetterSetRx("நெடில்", EzhuththuUtils.filterNedil()));
        map.put("!நெடில்", new AnyOneInTamilLetterSetRx("!நெடில்", EzhuththuUtils.filterOut(EzhuththuUtils.filterNedil())));

        map.put("உயிர்", new AnyOneInTamilLetterSetRx("உயிர்", EzhuththuUtils.filterUyir()));
        map.put("!உயிர்", new AnyOneInTamilLetterSetRx("!உயிர்", EzhuththuUtils.filterOut(EzhuththuUtils.filterUyir())));

        map.put("மெய்", new AnyOneInTamilLetterSetRx("மெய்", EzhuththuUtils.filterMei()));
        map.put("!மெய்", new AnyOneInTamilLetterSetRx("!மெய்", EzhuththuUtils.filterOut(EzhuththuUtils.filterMei())));


        map.put("!உயிர்மெய்", new AnyOneInTamilLetterSetRx("!உயிர்மெய்", EzhuththuUtils.filterOut(EzhuththuUtils.filterUyirMei())));
        map.put("உயிர்மெய்", new AnyOneInTamilLetterSetRx("உயிர்மெய்", EzhuththuUtils.filterUyirMei()));

        map.put("ஆய்தம்", new AnyOneInTamilLetterSetRx("ஆய்தம்", EzhuththuUtils.filterAaytham()));
        map.put("!ஆய்தம்", new AnyOneInTamilLetterSetRx("!ஆய்தம்", EzhuththuUtils.filterOut(EzhuththuUtils.filterAaytham())));
        map.put("முதல்", new AnyOneInTamilLetterSetRx("முதல்", EzhuththuUtils.filterUyir(), EzhuththuUtils.filterMei()));
        map.put("!முதல்", new AnyOneInTamilLetterSetRx("!முதல்", EzhuththuUtils.filterAaytham(), EzhuththuUtils.filterUyirMei()));


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



        map.put("!ஓரெழுத்துமொழி", new AnyOneInTamilLetterSetRx("!ஓரெழுத்துமொழி", "ஓரெழுத்தாயிருந்து ஒருமொழியாயிருக்கும் வாய்ப்பில்லாத எழுத்து",EzhuththuUtils.filterOut(EzhuththuUtils.filterOarezhutthuMozhi())));
        map.put("ஓரெழுத்துமொழி", new AnyOneInTamilLetterSetRx("ஓரெழுத்துமொழி","ஓரெழுத்தாயிருந்து ஒருமொழியாயிருக்கும் வாய்ப்புகொண்ட எழுத்து", EzhuththuUtils.filterOarezhutthuMozhi()));



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
        String[] ors = query.split(" அல்லது ");
        Set<TamilCharacter> ret = new LinkedHashSet<TamilCharacter>();
        for (String or : ors) {
            ret.addAll(  resolve(or));
        }
        return ret;

    }


    public Set<EzhuththuDescription> getEzhuththuDescriptions() {
        return Collections.unmodifiableSet(new LinkedHashSet<EzhuththuDescription>( map.values()));
    }


    private Set<TamilCharacter> resolve(String compound) {

        EzhuththuDescription desc = map.get(compound);
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
            ParserResultCollection resultCollection = new SaxParser().parse(TamilWord.from(compound), 10, new ParseWithDictionary() {
                        @Override
                        public TamilDictionary getDictionary() {
                            return LetterDictionary;
                        }
                    }
            );

            if (resultCollection.isEmpty()) {
                throw new TamilPlatformException("Unable to parse:" + compound);
            }
            ParserResult result = resultCollection.getList().get(0);
            if (!result.isParsed()) {
                throw new TamilPlatformException("Unable to parse:" + compound);
            }
            return result.getSplitWords();


        }
    }


    private Set<TamilCharacter> resolve(List<IKnownWord> parsed) {
        EzhuththuDescription gen = map.get(parsed.get(0).getWord().toString());
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
