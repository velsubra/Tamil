package my.interest.lang.tamil.translit;

import common.lang.impl.UnknownCharacter;

import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.parser.impl.TamilWordListener;
import my.interest.lang.tamil.punar.TamilWordPartContainer;

import my.interest.lang.tamil.punar.handler.iyalbu.IyalbuPunarchiHandler;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilFactory;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;
import tamil.lang.api.feature.FeatureConstants;
import tamil.lang.api.join.WordsJoiner;
import tamil.lang.api.trans.JoinFeature;
import tamil.lang.api.trans.NounLookupFeature;
import tamil.lang.api.trans.TranslitFeature;
import tamil.lang.api.trans.Transliterator;
import tamil.lang.known.IKnownWord;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.*;

/**
 * <p>
 *     WARNING: Applications should not directly use any class under my.interest.** packages.
 *     These are subject to change. Applications should only use tamil.** packages. The entry point for the application is
 *     {@link tamil.lang.TamilFactory}
 * </p>
 *
 * @author velsubra
 */
public final class EnglishToTamilCharacterLookUpContext implements Transliterator {
    static final Map<String, SoftReference<TamilWord>> TRANSLIT_CACHE = Collections.synchronizedMap(new HashMap<String, SoftReference<TamilWord>>());
    static final Map<String, SoftReference<TamilWord>> TRANSLIT_CACHE_JOIN = Collections.synchronizedMap(new HashMap<String, SoftReference<TamilWord>>());


    public static final EnglishToTamilCharacterLookUpContext TRANSLIST = new EnglishToTamilCharacterLookUpContext();

    private static Map<String, TamilWord> map = new HashMap<String, TamilWord>();

    private static Map<Character, String> preprocess = new HashMap<Character, String>();

    private static TamilWord findWord(String eng, boolean start, boolean end) {
        TamilWord w = null;
        if (start) {
            if (end) {
                w = map.get(" " + eng + " ");
            } else {

                w = map.get(" " + eng);
            }
        } else {
            if (end) {
                w = map.get(eng + " ");
            }
        }
        if (w == null) {
            w = map.get(eng);
        }
        return w;
    }

    static {
        preprocess.put('A', "aa");
        preprocess.put('E', "ea");
        preprocess.put('I', "ee");
        preprocess.put('O', "oa");
        preprocess.put('U', "oo");
        preprocess.put('L', "lh");
        preprocess.put('N', "nh");
        preprocess.put('R', "rr");
        preprocess.put('T', "th");


        map.put("a", new TamilWord(TamilSimpleCharacter.a));
        map.put("ae", new TamilWord(TamilSimpleCharacter.AA));
        map.put("ai", new TamilWord(TamilSimpleCharacter.I));
        map.put("ao", new TamilWord(TamilSimpleCharacter.OO));

        map.put("aia", new TamilWord(TamilSimpleCharacter.I, TamilSimpleCharacter.YA));
        map.put("aiai", new TamilWord(TamilSimpleCharacter.I, TamilCompoundCharacter.IY_I));
        map.put("aiaa", new TamilWord(TamilSimpleCharacter.I, TamilCompoundCharacter.IY_aa));
        map.put("aie", new TamilWord(TamilSimpleCharacter.I, TamilCompoundCharacter.IY_AA));
        map.put("aiea", new TamilWord(TamilSimpleCharacter.I, TamilCompoundCharacter.IY_AA));
        map.put("aiee", new TamilWord(TamilSimpleCharacter.I, TamilCompoundCharacter.IY_EE));
        map.put("aa", new TamilWord(TamilSimpleCharacter.aa));
        map.put("aai", new TamilWord(TamilSimpleCharacter.aa, TamilCompoundCharacter.IY));
        map.put("au", new TamilWord(TamilSimpleCharacter.OU));
        map.put("b", new TamilWord(TamilCompoundCharacter.IP));
        map.put("by ", new TamilWord(TamilCompoundCharacter.IP_I));
        map.put("c", new TamilWord(TamilCompoundCharacter.ICH));
        map.put("cy ", new TamilWord(TamilCompoundCharacter.ICH_I));
        map.put("ch", new TamilWord(TamilCompoundCharacter.ICH));
        map.put("d", new TamilWord(TamilCompoundCharacter.IDD));
        map.put("dy ", new TamilWord(TamilCompoundCharacter.IDD_I));
        map.put(" d", new TamilWord(TamilSimpleCharacter.E, TamilCompoundCharacter.IDD));
        map.put("dh", new TamilWord(TamilCompoundCharacter.ITH));
        map.put("e", new TamilWord(TamilSimpleCharacter.A));
        map.put("ea", new TamilWord(TamilSimpleCharacter.AA));
        map.put("ee", new TamilWord(TamilSimpleCharacter.EE));
        map.put("ei", new TamilWord(TamilSimpleCharacter.A, TamilCompoundCharacter.IY));
        map.put("eu", new TamilWord(TamilCompoundCharacter.IY));
        map.put("f", new TamilWord(TamilSimpleCharacter.AKTHU));
        map.put("g", new TamilWord(TamilCompoundCharacter.IK));
        map.put("gy ", new TamilWord(TamilCompoundCharacter.IK_I));
        map.put("gh", new TamilWord(TamilCompoundCharacter.IK));
        map.put("gn", new TamilWord(TamilCompoundCharacter.INJ));
        map.put(" h", new TamilWord(TamilCompoundCharacter.IH_));
        map.put("h", new TamilWord(TamilCompoundCharacter.IK));
        map.put("i", new TamilWord(TamilSimpleCharacter.E));
        map.put("ia", new TamilWord(TamilSimpleCharacter.E, TamilSimpleCharacter.YA));
        map.put("iu", new TamilWord(TamilSimpleCharacter.E, TamilCompoundCharacter.IY_U));
        map.put("ie", new TamilWord(TamilSimpleCharacter.E, TamilCompoundCharacter.IY_A));
        map.put("iea", new TamilWord(TamilSimpleCharacter.E, TamilCompoundCharacter.IY_AA));
        map.put("iaa", new TamilWord(TamilSimpleCharacter.E, TamilCompoundCharacter.IY_aa));
        map.put("ii", new TamilWord(TamilSimpleCharacter.EE));
        map.put("j", new TamilWord(TamilCompoundCharacter.IJ_));
        map.put("k", new TamilWord(TamilCompoundCharacter.IK));
        map.put("ky ", new TamilWord(TamilCompoundCharacter.IK_I));
        map.put("l", new TamilWord(TamilCompoundCharacter.IL));
        map.put("ly ", new TamilWord(TamilCompoundCharacter.IL_I));
        map.put("ll", new TamilWord(TamilCompoundCharacter.IL, TamilCompoundCharacter.IL));
        map.put("lll", new TamilWord(TamilCompoundCharacter.ILL));
        map.put("llll", new TamilWord(TamilCompoundCharacter.ILL, TamilCompoundCharacter.ILL));
        map.put("lllll", new TamilWord(TamilCompoundCharacter.ILL, TamilCompoundCharacter.ILL));
        map.put("lh", new TamilWord(TamilCompoundCharacter.ILL));
        map.put("lhh", new TamilWord(TamilCompoundCharacter.ILLL));
        map.put("llh", new TamilWord(TamilCompoundCharacter.ILL, TamilCompoundCharacter.ILL));
        map.put("lhl", new TamilWord(TamilCompoundCharacter.ILL, TamilCompoundCharacter.ILL));
        map.put("lhlh", new TamilWord(TamilCompoundCharacter.ILL, TamilCompoundCharacter.ILL));
        map.put("ln", new TamilWord(TamilCompoundCharacter.ILLL));
        map.put("lnt", new TamilWord(TamilCompoundCharacter.IL, TamilCompoundCharacter.INTH));
        map.put("m", new TamilWord(TamilCompoundCharacter.IM));
        map.put("mz", new TamilWord(TamilCompoundCharacter.IM));
        map.put(" n", new TamilWord(TamilCompoundCharacter.INTH));
        map.put(" nt", new TamilWord(TamilCompoundCharacter.INTH));
        map.put("n", new TamilWord(TamilCompoundCharacter.IN));
        map.put("nz", new TamilWord(TamilCompoundCharacter.IN));
        map.put("ny ", new TamilWord(TamilCompoundCharacter.IN_I));
        map.put("nn", new TamilWord(TamilCompoundCharacter.IN, TamilCompoundCharacter.IN));
        map.put("nnn", new TamilWord(TamilCompoundCharacter.INNN));
        map.put("nnnn", new TamilWord(TamilCompoundCharacter.INNN, TamilCompoundCharacter.INNN));
        map.put("nnnnn", new TamilWord(TamilCompoundCharacter.INNN, TamilCompoundCharacter.INNN));
        map.put("nd", new TamilWord(TamilCompoundCharacter.INNN, TamilCompoundCharacter.IDD));
        map.put("ndr", new TamilWord(TamilCompoundCharacter.IN, TamilCompoundCharacter.IRR));
        map.put("ndrr", new TamilWord(TamilCompoundCharacter.IN, TamilCompoundCharacter.IRR));
        map.put("ndh", new TamilWord(TamilCompoundCharacter.INTH, TamilCompoundCharacter.ITH));
        map.put("nj", new TamilWord(TamilCompoundCharacter.INJ));
        map.put("njc", new TamilWord(TamilCompoundCharacter.INJ, TamilCompoundCharacter.ICH));
        map.put("njch", new TamilWord(TamilCompoundCharacter.INJ, TamilCompoundCharacter.ICH));
        map.put("ng", new TamilWord(TamilCompoundCharacter.ING, TamilCompoundCharacter.IK));
        map.put("ng ", new TamilWord(TamilCompoundCharacter.ING));
        map.put("ngk", new TamilWord(TamilCompoundCharacter.ING, TamilCompoundCharacter.IK));
        map.put("ngh", new TamilWord(TamilCompoundCharacter.ING, TamilCompoundCharacter.IK));
        map.put("ngg", new TamilWord(TamilCompoundCharacter.ING, TamilCompoundCharacter.IK));
        map.put("nt", new TamilWord(TamilCompoundCharacter.INTH));

//        map.put("nti ", new TamilWord(TamilCompoundCharacter.IN, TamilCompoundCharacter.IRR_E));
//        map.put("nta ", new TamilWord(TamilCompoundCharacter.IN, TamilSimpleCharacter.RRA));
//        map.put("ntaa ", new TamilWord(TamilCompoundCharacter.IN, TamilCompoundCharacter.IRR_aa));
//        map.put("ntu ", new TamilWord(TamilCompoundCharacter.IN, TamilCompoundCharacter.IRR_U));
//        map.put("ntea ", new TamilWord(TamilCompoundCharacter.IN, TamilCompoundCharacter.IRR_AA));

        map.put("nr", new TamilWord(TamilCompoundCharacter.IN, TamilCompoundCharacter.IRR));
        map.put("ntr", new TamilWord(TamilCompoundCharacter.IN, TamilCompoundCharacter.IRR));
        map.put("nrr", new TamilWord(TamilCompoundCharacter.IN, TamilCompoundCharacter.IRR));
        map.put("nth", new TamilWord(TamilCompoundCharacter.INTH, TamilCompoundCharacter.ITH));
        map.put("ntt", new TamilWord(TamilCompoundCharacter.INTH, TamilCompoundCharacter.ITH));
        map.put("ntth", new TamilWord(TamilCompoundCharacter.INTH, TamilCompoundCharacter.ITH));
        map.put("nh", new TamilWord(TamilCompoundCharacter.INNN));
        map.put("nhh", new TamilWord(TamilCompoundCharacter.INTH));
        map.put("nht", new TamilWord(TamilCompoundCharacter.IN, TamilCompoundCharacter.IRR));
        map.put("nhth", new TamilWord(TamilCompoundCharacter.INNN, TamilCompoundCharacter.ITH));
        map.put("nhr", new TamilWord(TamilCompoundCharacter.IN, TamilCompoundCharacter.IRR));
        map.put("nhrr", new TamilWord(TamilCompoundCharacter.IN, TamilCompoundCharacter.IRR));

        map.put("nhn", new TamilWord(TamilCompoundCharacter.INNN, TamilCompoundCharacter.INNN));
        map.put("nhnh", new TamilWord(TamilCompoundCharacter.INNN, TamilCompoundCharacter.INNN));
        map.put("nnh", new TamilWord(TamilCompoundCharacter.INNN, TamilCompoundCharacter.INNN));
        map.put("o", new TamilWord(TamilSimpleCharacter.O));
        map.put("oi", new TamilWord(TamilSimpleCharacter.O, TamilCompoundCharacter.IY));
        map.put("oo", new TamilWord(TamilSimpleCharacter.UU));
        map.put("ou", new TamilWord(TamilSimpleCharacter.OU));
        map.put(" om ", new TamilWord(TamilSimpleCharacter.OM));
       // map.put("oam", new TamilWord(TamilSimpleCharacter.OM));
        map.put("oa", new TamilWord(TamilSimpleCharacter.OO));
        map.put("p", new TamilWord(TamilCompoundCharacter.IP));
        map.put("py ", new TamilWord(TamilCompoundCharacter.IP_I));
        map.put("q", new TamilWord(TamilCompoundCharacter.IK));
        map.put("qy ", new TamilWord(TamilCompoundCharacter.IK_I));
        map.put("r", new TamilWord(TamilCompoundCharacter.IR));
        map.put("ry ", new TamilWord(TamilCompoundCharacter.IR_I));
        map.put(" r", new TamilWord(TamilSimpleCharacter.E, TamilCompoundCharacter.IR));
        map.put("rr", new TamilWord(TamilCompoundCharacter.IRR));
        map.put("rs", new TamilWord(TamilSimpleCharacter.RS));
        map.put("rh", new TamilWord(TamilCompoundCharacter.IRR));
        map.put("rrr", new TamilWord(TamilCompoundCharacter.IRR, TamilCompoundCharacter.IRR));
        map.put("rrrr", new TamilWord(TamilCompoundCharacter.IRR, TamilCompoundCharacter.IRR));
        map.put("s", new TamilWord(TamilCompoundCharacter.ICH));
        map.put("ss", new TamilWord(TamilCompoundCharacter.ISS_));
        map.put("sy ", new TamilWord(TamilCompoundCharacter.ICH_I));
        map.put("sh", new TamilWord(TamilCompoundCharacter.ISH_));
        map.put("t", new TamilWord(TamilCompoundCharacter.IRR));
        map.put("ty ", new TamilWord(TamilCompoundCharacter.IRR_I));
        map.put(" t", new TamilWord(TamilCompoundCharacter.ITH));
        map.put(" th", new TamilWord(TamilCompoundCharacter.ITH));
        map.put("tt", new TamilWord(TamilCompoundCharacter.IRR, TamilCompoundCharacter.IRR));
        map.put("tth", new TamilWord(TamilCompoundCharacter.ITH, TamilCompoundCharacter.ITH));
        map.put("ta", new TamilWord(TamilSimpleCharacter.THA));
        map.put("tai", new TamilWord(TamilCompoundCharacter.ITH_I));
        map.put("taa", new TamilWord(TamilCompoundCharacter.ITH_aa));
        map.put("tr", new TamilWord(TamilCompoundCharacter.IRR, TamilCompoundCharacter.IRR));
        map.put("trr", new TamilWord(TamilCompoundCharacter.IRR, TamilCompoundCharacter.IRR));
        map.put("th", new TamilWord(TamilCompoundCharacter.ITH));
        map.put("thh", new TamilWord(TamilCompoundCharacter.ITH));
        map.put("tht", new TamilWord(TamilCompoundCharacter.ITH, TamilCompoundCharacter.ITH));
        map.put("thth", new TamilWord(TamilCompoundCharacter.ITH, TamilCompoundCharacter.ITH));
        map.put("u", new TamilWord(TamilSimpleCharacter.U));
        map.put("ua", new TamilWord(TamilSimpleCharacter.AA));

        map.put("uu", new TamilWord(TamilSimpleCharacter.UU));
        map.put("v", new TamilWord(TamilCompoundCharacter.IV));
        map.put("w", new TamilWord(TamilCompoundCharacter.IV));
        map.put("x", new TamilWord(TamilCompoundCharacter.IK));
        map.put("y", new TamilWord(TamilCompoundCharacter.IY));

        map.put("z", new TamilWord(TamilCompoundCharacter.ILLL));
        map.put("zz", new TamilWord(TamilCompoundCharacter.ILLL, TamilCompoundCharacter.ICH));
        map.put("zh", new TamilWord(TamilCompoundCharacter.ILLL));
        map.put("zhz", new TamilWord(TamilCompoundCharacter.ILLL, TamilCompoundCharacter.ICH));





    }

    private static List<TamilWord> duplicate(List<TamilWord> list) {
        List<TamilWord> ret = new ArrayList<TamilWord>();
        for (TamilWord l : list) {
            ret.add(l.duplicate());
        }
        return ret;
    }


    @Override
    public TamilWord transliterate(String word) {
        return transliterate(word, false);
    }


    @Override
    public TamilWord transliterate(String word, boolean join) {

        return transliterate(word, join ? new TranslitFeature[]{FeatureConstants.TRANSLIT_JOIN_FEATURE_VAL_110} : null);
    }


    @Override
    public TamilWord transliterate(String englishoriginal, TranslitFeature... features) {

        boolean cacheable = true;
        boolean join = false;
        boolean atleastOneTranslit = false;
        NounLookupFeature nounlookup = null;

        if (features != null) {
            for (TranslitFeature f : features) {
                if (f == JoinFeature.INSTANCE) {
                    join = true;
                } else if (NounLookupFeature.class.isAssignableFrom(f.getClass())) {
                    nounlookup = (NounLookupFeature) f;
                    cacheable = false;
                }

            }
        }

        TamilWord word = null;
        Map<String, SoftReference<TamilWord>> cache = null;
        if (cacheable) {
            cache = join ? TRANSLIT_CACHE_JOIN : TRANSLIT_CACHE;
            SoftReference<TamilWord> ref = cache.get(englishoriginal);
            if (ref != null) {
                word = ref.get();
                if (word != null) {
                    return word.duplicate();
                }
            }
        }

        StringBuffer buffer = new StringBuffer();
        StringBuffer nonresolved = new StringBuffer();

        WordsJoiner handler = null;
        if (join) {
            handler = TamilFactory.createWordJoiner(new TamilWord());
        } else {
            word = new TamilWord();
        }
        if (englishoriginal == null) return word;
        StringBuffer english = new StringBuffer();
        boolean allenglish = true;
        //Pre-processing    starts
        for (int i = 0; i < englishoriginal.length(); i++) {

            char ch = englishoriginal.charAt(i);
            if (nounlookup != null && allenglish && ch == nounlookup.getLookupChar()) {
                if (english.length() > 0) {
                    IKnownWord lookedKnown = TamilFactory.getSystemDictionary().peekEnglish(english.toString());
                    TamilWord looked = null;
                    if (lookedKnown != null) {
                        looked = lookedKnown.getWord();
                    }
                    if (looked == null) {
                        looked = new TamilWord();
                        //looked.add ( UnknownCharacter.getFor('\\'));
                        for (int j = 0; j < english.length(); j++) {
                            looked.add( UnknownCharacter.getFor(english.charAt(j)));
                        }
                    }

                    if (join) {
                        handler.addVaruMozhi(looked);
                    } else {
                        word.addAll(looked);
                    }

                    english = new StringBuffer();
                    allenglish = true;


                    continue;
                }
            }
            if (ch >= 'A' && ch <= 'Z') {
                String pref = preprocess.get(ch);
                if (pref != null) {
                    english.append(pref);
                } else {
                    //To lower case.
                    english.append((char) (ch + 32));
                }

            } else {
                allenglish = allenglish && ((ch >= 'a' && ch <= 'z') || ch == '-');
                english.append(ch);
            }

        }

         //Transliteration starts here.
        boolean starting = true;
        for (int i = 0; i < english.length(); i++) {
            TamilWord single = map.get(String.valueOf(english.charAt(i)));
            if (single == null) {
                if (buffer.length() > 0) {
                    //All English
                    TamilWord allEnglish = getBestMatchNoNonChar(buffer.toString(), starting, true);
                    atleastOneTranslit = true;
                    if (join) {
                        handler.addVaruMozhi(allEnglish);
                    } else {
                        word.addAll(allEnglish);
                    }
                    buffer.setLength(0);

                }
                starting = false;
                nonresolved.append(english.charAt(i));
            } else {
                if (nonresolved.length() > 0) {
                    TamilWord non = TamilWordListener.readUTF8(nonresolved.toString(), true);
                    if (join) {
                        handler.addVaruMozhi(non);
                    } else {
                        word.addAll(non);
                    }
                    nonresolved.setLength(0);
                }

                buffer.append(english.charAt(i));
            }
        }

        if (buffer.length() > 0) {
            TamilWord allEnglish = getBestMatchNoNonChar(buffer.toString(), starting, true);
            atleastOneTranslit = true;
            if (join) {
                handler.addVaruMozhi(allEnglish);
            } else {
                word.addAll(allEnglish);
            }
            buffer.setLength(0);
        }
        if (nonresolved.length() > 0) {
            TamilWord non = TamilWordListener.readUTF8(nonresolved.toString(), true);
            if (join) {
                handler.addVaruMozhi(non);
            } else {
                word.addAll(non);
            }

            nonresolved.setLength(0);
        }
        if (join) {
            word = handler.getSum();
        }
        if (cacheable && atleastOneTranslit) {
           cache.put(englishoriginal, new SoftReference<TamilWord>(word.duplicate()));
        }
        return word;
    }

    static class TamilWordComparator implements Comparator<TamilWord>, Serializable {


        @Override
        public int compare(TamilWord o1, TamilWord o2) {
            if (o1.size() < o2.size()) {
                return -1;
            } else if (o1.size() > o2.size()) {
                return 1;
            } else {
                return 0;
            }
        }
    }


//    private static TamilWord getBestMatchNoNonChar(String english) {
//        return getBestMatchNoNonChar(english, true, true);
//    }

    //Eats max of four five characters
    private static TamilWord getBestMatchNoNonChar(String english, boolean starting, boolean ending) {
        TamilWord current = new TamilWord();
        IyalbuPunarchiHandler handler = new IyalbuPunarchiHandler();
        for (int i = 0; i < english.length(); i++) {

            TamilWord single = null;

            //todo:This can be looped later

            if (i < english.length() - 4) {
                String five = english.substring(i, i + 5);
                single = findWord(five, starting && i == 0, ending && (english.length() - i - 5) == 0);


            }
            if (single != null) {
                i += 4;
            } else {

                if (i < english.length() - 3) {
                    String four = english.substring(i, i + 4);
                    single = findWord(four, starting && i == 0, ending && (english.length() - i - 4) == 0);


                }
                if (single != null) {
                    i += 3;
                } else {

                    if (i < english.length() - 2) {
                        String three = english.substring(i, i + 3);
                        single = findWord(three, starting && i == 0, ending && (english.length() - i - 3) == 0);


                    }

                    if (single != null) {
                        i += 2;
                    } else {

                        if (i < english.length() - 1) {
                            String two = english.substring(i, i + 2);
                            single = findWord(two, starting && i == 0, ending && (english.length() - i - 2) == 0);
                        }
                        if (single != null) {
                            i++;
                        } else {
                            single = findWord(String.valueOf(english.charAt(i)), starting && i == 0, ending && (english.length() - i - 1) == 0);

                        }

                    }
                }
            }
            current = iyalbuadd(current, single, handler);


        }
        return current;
    }

    public static TamilWord getArrayValue(String english) {
        if (english == null) {
            return new TamilWord();
        }
        int eq = english.indexOf("=");
        if (eq >= 0) {
            String left = english.substring(0, eq);
            List<String> list = EzhuththuUtils.parseString(left, " ");
            TamilWord ret = new TamilWord();
            if (list.size() > 0) {
                for (int i = 0; i < list.size() - 1; i++) {
                    ret.addAll(getBestMatch(list.get(i)));
                    ret.add( UnknownCharacter.SPACE);
                }
                for (int ch : list.get(list.size() - 1).toCharArray()) {
                    ret.add( UnknownCharacter.getFor(ch));
                }
            }
            ret.add( UnknownCharacter.getFor('='));
            ret.addAll(getArrayValue(english.substring(eq + 1, english.length())));
            return ret;

        } else {
            return getBestMatch(english);
        }
    }

    public static TamilWord getBestMatch(String englishoriginal) {
        return TRANSLIST.transliterate(englishoriginal, false);

    }


    private static TamilWord iyalbuadd(TamilWord w, TamilWord ch, IyalbuPunarchiHandler handler) {
        TamilWord one = new TamilWord();
        one.addAll(ch);
        TamilWordPartContainer res = handler.join(new TamilWordPartContainer(w), new TamilWordPartContainer(one));
        if (res != null) {

            return res.getWord();
        } else {
            w.addAll(ch);
            return w;
        }
    }


}
