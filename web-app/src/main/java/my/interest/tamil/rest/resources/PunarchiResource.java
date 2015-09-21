package my.interest.tamil.rest.resources;

import common.lang.impl.AbstractCharacter;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.internal.api.HandlerFactory;
import my.interest.lang.tamil.bean.*;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.handler.AbstractPunarchiHandler;
import my.interest.lang.tamil.translit.EnglishToTamilCharacterLookUpContext;
import tamil.lang.api.feature.FeatureConstants;
import tamil.lang.api.trans.TranslitFeature;

import javax.ws.rs.*;
import java.lang.ref.SoftReference;
import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
@Path("punarchi")
public class PunarchiResource {
    public PunarchiResource() {

    }

    @PUT
    @Path("/join/{nilai}/{varum}")
    @Produces("application/json; charset=UTF-8")
    public FullJoinResult join(@PathParam("nilai") String nilai, @PathParam("varum") String varum, String handler) throws Exception {

        AbstractPunarchiHandler hand = null;
        if (handler != null) {
            hand = HandlerFactory.findHandlerByName(handler);
        }
        FullJoinResult result = null;
        if (hand != null) {
            result = new FullJoinResult();
            HandlerJoinResult single = HandlerFactory.join(nilai, varum, hand);
            result.add(single);
        } else {
            result = HandlerFactory.join(nilai, varum);
        }
        return result;
    }


    @PUT
    @Path("/split/{joined}")
    @Produces("application/json; charset=UTF-8")
    public FullSplitResult split(@PathParam("joined") String joined, String handler) throws Exception {


        AbstractPunarchiHandler hand = null;
        if (handler != null) {
            hand = HandlerFactory.findHandlerByName(handler);
        }
        FullSplitResult result = null;
        if (hand != null) {
            result = new FullSplitResult();
            HandlerSplitResult split = HandlerFactory.split(joined, hand);
            result.add(split);
        } else {
            result = HandlerFactory.split(joined);
        }
        return result;
    }


    @PUT
    @Path("/parse")
    @Produces("application/json; charset=UTF-8")
    public FullSplitResult parse(String word1 , @DefaultValue("3") @QueryParam("maxpathscount") int maxpathscount) throws Exception {

        TamilWord actual = TamilWord.from(word1);
        TamilWord pure = new TamilWord();
        for (AbstractCharacter ch : actual) {
            if (ch.isTamilLetter()) {
                pure.add(ch);
            }
        }
        String purestr = pure.toString();

//        SaxParser parser = new SaxParser();
//        List<ParserResult> list = parser.parse(TamilWord.from(purestr), 10, null);
//        System.out.println(list.size());




        FullSplitResult ret = new FullSplitResult();
        List<SimpleSplitResult> res = null;

        res = HandlerFactory.parse(purestr, maxpathscount).getSplit();

        if (res != null) {
            for (int i = 0; i < res.size(); i++) {
                HandlerSplitResult r = new HandlerSplitResult();
                r.setHandlerDescription(purestr);
                r.setHandlerName(String.valueOf(i + 1));
                r.getSplitLists().add(res.get(i));
                ret.add(r);
            }
        }
        return ret;
    }

    @PUT
    @Path("/translitbulk/")
    @Produces("application/json; charset=UTF-8")
    public List<TranslitResult> translitBulk(String english, @QueryParam("join") boolean join) throws Exception {
        if (english == null || english.trim().equals("")) {
            return Collections.emptyList();
        }
        english = english.replaceAll("\\n", " ");
        english = english.replaceAll("\\.", " ");
        List<TranslitResult> list = new ArrayList<TranslitResult>();
        Set<String> set = TamilUtils.parseAndRemoveDuplicatesAsSet(english, " ");
        for (String s : set) {
            TranslitResult r = translit(s, join, false, true, 1);
            r.setActual(s);
            list.add(r);
        }

        return list;

    }

    private static boolean wordExists(String w) {
        return !TamilFactory.getSystemDictionary().lookup(TamilWord.from(w)).isEmpty();
    }


    @PUT
    @Path("/filterUnknown/nouns/")
    @Produces("application/json; charset=UTF-8")
    public List<SingleWordSplit> findUnknownFirstWord(String english) throws Exception {
        if (english == null || english.trim().equals("")) {
            return Collections.emptyList();
        }
        english = english.replaceAll("\\n", " ");
        Map<String, SingleWordSplit> map = new HashMap<String, SingleWordSplit>();
        Set<String> set = TamilUtils.parseAndRemoveDuplicatesAsSet(english, " ");
        //   Set<String> innerset = new HashSet<String>();
        Set<String> unique = new HashSet<String>();
        for (String s1 : set) {
            TranslitResult r = translit(s1, false, false, true, 1);
            if (!r.isKnown()) {
                String thodar = r.getActual();
                MultipleWordSplitResult multi = HandlerFactory.parseForChance(thodar, false, 20);
                if (multi.getSplit() == null) continue;

                for (SimpleSplitResult ss : multi.getSplit()) {
                    String val = multi.getFirstFoundUnknown(ss);
                    if (val == null || val.trim().equals("")) {
                        continue;
                    }
                    if (unique.contains(val)) {
                        continue;
                    }
                    TamilWordPartContainer cont = new TamilWordPartContainer(TamilWord.from(val));
                    if (!cont.isEndingFine()) continue;
                    if (!cont.isStartingFine()) continue;
                    //h.getHandlerDescription() contains the thodarmozhi
                    SingleWordSplit sp = map.get(thodar);
                    if (sp == null) {
                        sp = new SingleWordSplit();
                        sp.setSplit(new SimpleSplitResult());
                        sp.setWord(thodar);
                        map.put(thodar, sp);
                    }

                    sp.getSplit().insertIntoList(val);
                    if (cont.isEndingWithUyirMei()) {
                        TamilWord magaram = cont.getWord().duplicate();
                        magaram.add(TamilCompoundCharacter.IM);
                        if (!unique.contains(magaram.toString()) && !wordExists(magaram.toString())) {
                            sp.getSplit().insertIntoList(magaram.toString());
                            unique.add(magaram.toString());
                        }
                        if (cont.size() <= 2 && cont.isStartingWithKuril()) {
                            magaram.add(TamilCompoundCharacter.IM_I);
                            if (!unique.contains(magaram.toString())  && !wordExists(magaram.toString())) {
                                sp.getSplit().insertIntoList(magaram.toString());
                                unique.add(magaram.toString());
                            }
                        }
                    } else if (cont.isThanikKurilOtru()) {
                        TamilWord mai = cont.getWord().duplicate();
                        mai.add(TamilCompoundCharacter.IM_I);
                        if (!unique.contains(mai.toString())  && !wordExists(mai.toString())) {
                            sp.getSplit().insertIntoList(mai.toString());
                            unique.add(mai.toString());
                        }
                    }
                    TamilWordPartContainer thodarw = new TamilWordPartContainer(TamilWord.from(thodar));

                    if (thodarw.isEndingWithUyirMei()) {
                        TamilWord magaram = thodarw.getWord().duplicate();
                        magaram.add(TamilCompoundCharacter.IM);
                        if (!unique.contains(magaram.toString())  && !wordExists(magaram.toString())) {
                            sp.getSplit().insertIntoList(magaram.toString());
                            unique.add(magaram.toString());
                        }

                    }

                    unique.add(val);


                }


            }


        }

        return new ArrayList<SingleWordSplit>(map.values());

    }



    static final Map<String, SoftReference<TranslitResult>>  TRANSLIT_CACHE =   new HashMap<String, SoftReference<TranslitResult>>();
    //array means list of values with key=pair where pair is translated.
    @PUT
    @Path("/translit/")
    @Produces("application/json; charset=UTF-8")
    public TranslitResult translit(String english, @QueryParam("join") boolean join,  @QueryParam("array") boolean array,  @QueryParam("parse") boolean parse, @DefaultValue("1") @QueryParam("maxpathscount") int maxpathscount) throws Exception {

        if (maxpathscount == 1 && parse) {
            SoftReference<TranslitResult> ref =  TRANSLIT_CACHE.get(english);
            TranslitResult r = null;
            if (ref != null) {
                r = ref.get();
                if (r == null) {
                    TRANSLIT_CACHE.remove(english);
                } else {
                    return  r;
                }
            }
        }
        TranslitResult result = new TranslitResult();
        TamilWord word = array ?  EnglishToTamilCharacterLookUpContext.getArrayValue(english)  : EnglishToTamilCharacterLookUpContext.TRANSLIST.transliterate(english, join ? new TranslitFeature[]{FeatureConstants.TRANSLIT_JOIN_FEATURE_VAL_110, FeatureConstants.TRANSLIT_NOUN_LOOKUP_FEATURE_VAL_115} : null);
        result.setTamilWord(word.toString());

        if (!array && parse) {
            result.setParsed(true);
            String pure = word.filterToPure().toString();
            result.setActual(pure);
            List<SimpleSplitResult> res = HandlerFactory.parse(pure, true, false, maxpathscount).getSplit();
            if (res != null && !res.isEmpty()) {
                result.setKnown(true);


            }
        }

        if (maxpathscount == 1 && parse && result.isKnown()) {
            SoftReference<TranslitResult> ref = new SoftReference<TranslitResult>(result);
            TRANSLIT_CACHE.put(english,ref);
        }

        return result;

    }


}
