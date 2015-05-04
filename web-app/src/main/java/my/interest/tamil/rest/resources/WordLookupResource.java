package my.interest.tamil.rest.resources;

import common.lang.impl.AbstractCharacter;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.generated.types.*;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.derived.HavingPaal;
import tamil.lang.known.derived.HavingTense;
import tamil.lang.known.derived.PeyarchcholDerivative;
import tamil.lang.known.derived.VinaiyadiDerivative;
import tamil.lang.known.non.derived.*;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */

@Path("lookup")
public class WordLookupResource {

    @GET
    @Path("/words/count")
    @Produces({MediaType.TEXT_PLAIN})
    public String getSize() {
        return String.valueOf(PersistenceInterface.get().totalWordsSize());
    }

    @GET
    @Path("/words")
    @Produces("application/json; charset=UTF-8")

    public String list(@QueryParam("pattern") String pattern, @QueryParam("maxcount") @DefaultValue("30") int maxcount, @QueryParam("rootverbs") boolean includeRootVerbs, @QueryParam("suggest") boolean suggest) throws Exception {
        List<String> ret = new ArrayList<String>();
        Set<String> set = new HashSet<String>();
        List<Class<? extends IKnownWord>> list = null;
        if (includeRootVerbs) {
            if (list == null) {
                list = new ArrayList<Class<? extends IKnownWord>>();
            }
            list.add(Vinaiyadi.class);
        }


        List<IKnownWord> words = null;
        if (suggest) {
            words = TamilFactory.getSystemDictionary().suggest(TamilFactory.getTransliterator(null).transliterate( pattern), maxcount, list);
        } else {
            words = TamilFactory.getSystemDictionary().search(TamilFactory.getTransliterator(null).transliterate(pattern), maxcount, list);
        }

        for (IKnownWord d : words) {
            String s = d.toString();
            if (!set.contains(s)) {
                ret.add(s);
                set.add(s);
            }
        }
        JSONObject obj = new JSONObject();
        obj.put("results", ret);
        return obj.toString();
    }


    @PUT
    @Path("/words")
    @Produces("application/json; charset=UTF-8")
    public String listFromBody(String pattern, @QueryParam("maxcount") @DefaultValue("30") int maxcount, @QueryParam("rootverbs") boolean includeRootVerbs, @QueryParam("suggest") boolean suggest) throws Exception {
        return list(pattern, maxcount, includeRootVerbs, suggest);
    }


    @PUT
    @Path("/words/describe")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<KnownWords> find(String word1) throws Exception {
        TamilWord actual = TamilWord.from(word1);
        TamilWord pure = new TamilWord();
        for (AbstractCharacter ch : actual) {
            if (ch.isPureTamilLetter()) {
                pure.add(ch);
            }
        }

        List<IKnownWord> list = TamilFactory.getSystemDictionary().search(pure,true,10,null);
        KnownWords words = new KnownWords();
        for (IKnownWord w : list) {
            KnownWord k = new KnownWord();
            k.setType(w.getType().toString());
            k.setValue(w.getWord().toString());
            words.getWords().add(k);

            k.setProperties(new Properties());
            if (VinaiyadiDerivative.class.isAssignableFrom(w.getClass())) {
                Property pr = new Property();
                pr.setName("root");
                pr.setValue(((VinaiyadiDerivative) w).getVinaiyadi().getWord().toString());
                k.getProperties().getProperty().add(pr);
            }


            if (PeyarchcholDerivative.class.isAssignableFrom(w.getClass())) {
                Property pr = new Property();
                pr.setName("root");
                pr.setValue(((PeyarchcholDerivative) w).getPeyar().getWord().toString());
                k.getProperties().getProperty().add(pr);
            }

            for (String p : w.getPropertyNames()) {
                Property pr = new Property();
                pr.setName(p);
                pr.setValue(w.getProperty(p));
                if (pr.getName() != null && pr.getValue() != null) {
                    k.getProperties().getProperty().add(pr);
                }
            }
            if (HavingPaal.class.isAssignableFrom(w.getClass())) {
                Property pr = new Property();
                pr.setName("paal");
                pr.setValue(((HavingPaal) w).getPaalViguthi().toString());
                k.getProperties().getProperty().add(pr);
            }

            if (HavingTense.class.isAssignableFrom(w.getClass())) {
                Property pr = new Property();
                pr.setName("tense");
                pr.setValue(((HavingTense) w).getTense().toString());
                k.getProperties().getProperty().add(pr);
            }

            if (IPeyarchchol.class.isAssignableFrom(w.getClass())) {
                Property pr = new Property();
                pr.setName("peyarchchol");
                pr.setValue("true");
                k.getProperties().getProperty().add(pr);

                pr = new Property();
                pr.setName("uyarthinhai");
                pr.setValue(String.valueOf(((IPeyarchchol) w).isUyarThinhai()));
                k.getProperties().getProperty().add(pr);
            }

            if (IPeyarechcham.class.isAssignableFrom(w.getClass())) {
                Property pr = new Property();
                pr.setName("peyarechcham");
                pr.setValue("true");


                k.getProperties().getProperty().add(pr);
            }

            if (IVinaiyechcham.class.isAssignableFrom(w.getClass())) {
                Property pr = new Property();
                pr.setName("vinaiyechcham");
                pr.setValue("true");
                k.getProperties().getProperty().add(pr);
            }


            if (IKaddalhai.class.isAssignableFrom(w.getClass())) {
                Property pr = new Property();
                pr.setName("kaddalhai");
                pr.setValue("true");
                k.getProperties().getProperty().add(pr);
            }

            if (IEthirmarrai.class.isAssignableFrom(w.getClass())) {
                Property pr = new Property();
                pr.setName("ethirmarrai");
                pr.setValue("true");
                k.getProperties().getProperty().add(pr);
            }

            if (Vinaiyadi.class.isAssignableFrom(w.getClass())) {
                Property pr = new Property();
                pr.setName("transitive");
                pr.setValue(String.valueOf(((Vinaiyadi) w).isTransitive()));
                k.getProperties().getProperty().add(pr);
            }
//
//            if (IIdaichchol.class.isAssignableFrom(w.getClass())) {
//                Property pr = new Property();
//                pr.setName("idaichchol");
//                pr.setValue("true");
//                k.getProperties().getProperty().appendNodesToAllPaths(pr);
//            }


        }
        return new ObjectFactory().createKnownWords(words);
    }
}
