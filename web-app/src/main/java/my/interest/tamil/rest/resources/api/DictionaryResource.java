package my.interest.tamil.rest.resources.api;

import my.interest.lang.tamil.impl.FeatureSet;
import org.json.JSONArray;
import org.json.JSONObject;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.dictionary.DictionaryFeature;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.known.IKnownWord;

import javax.ws.rs.*;
import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */

public class DictionaryResource extends BaseResource {
    //FB App id 1493373077613181
    //f4ff1a747a36f626c3408c9b3257994a

    private   DictionaryResource() {
        //dont allow it
    }
    TamilDictionary dictionary = null;


    DictionaryResource(TamilDictionary dictionary) {
        this.dictionary = dictionary;
    }


    @Path("/")
    public DictionaryLocatorResource getGlobal() {
        return new DictionaryLocatorResource(dictionary);
    }


    @GET
    @Path("size")
    @Produces("application/json; charset=UTF-8")
    public String size()  throws  Exception {
        JSONObject obj = new JSONObject();
        try {

            obj.put("size", String.valueOf(dictionary.size()));
            return obj.toString();
        } catch (Exception e) {
            handle(obj, e);


        }
        return obj.toString();
    }


    @GET
    @Path("word-types")
    @Produces("application/json; charset=UTF-8")
    public String getWordTypes() throws  Exception{
        JSONObject obj = new JSONObject();
        try {

            List<Class<? extends IKnownWord>> list = new ArrayList<Class<? extends IKnownWord>>( dictionary.getWordTypes());

            Collections.sort(list, new Comparator<Class<? extends IKnownWord>>() {
                public int compare(Class<? extends IKnownWord> o1, Class<? extends IKnownWord> o2) {
                    return o1.getSimpleName().compareTo(o2.getSimpleName());
                }
            });
            JSONArray array = new JSONArray();

            obj.put("list", array);
            for (Class cls : list) {

                List<Class<? extends IKnownWord>> searchClass = new ArrayList<Class<? extends IKnownWord>>();
                searchClass.add(cls);
                TamilDictionary subDictionary = dictionary.getMiniDictionaryForWordType(cls);
               List<IKnownWord> result =   subDictionary.search(new TamilWord(), false, 1, searchClass) ;
                if (result.isEmpty()) continue;

                JSONObject type = new JSONObject();


                type.put("clazz", cls.getName());
                type.put("tamil", result.get(0).getType().toString());
                type.put("size", subDictionary.size());
                array.put(type);
            }
        } catch (Exception e) {
            handle(obj, e);


        }
        return obj.toString();
    }

    @GET
    @Path("search/")
    @Produces("application/json; charset=UTF-8")
    public String search(@QueryParam("word") String word, @DefaultValue("20") @QueryParam("max") int max, @DefaultValue("") @QueryParam("features") String features) throws Exception {
        JSONObject obj = new JSONObject();
        try {
            obj.put("given", word);

            List<IKnownWord> list = dictionary.search(TamilWord.from(word), max, null, FeatureSet.findFeatures(DictionaryFeature.class, features).toArray(new DictionaryFeature[]{}));
            obj.put("list", from(list));
            return obj.toString();

        } catch (Exception e) {
            handle(obj, e);


        }
        return obj.toString();
    }

    @GET
    @Path("peekenglish/")
    @Produces("application/json; charset=UTF-8")
    public String peekenglish(@QueryParam("word") String word) throws Exception {
        JSONObject obj = new JSONObject();
        try {
            obj.put("given", word);

            IKnownWord tamil = dictionary.peekEnglish(word);
            if (tamil != null) {
                obj.put("found", from(tamil));
            } else {
                //   obj.put("tamil", word);
            }
            return obj.toString();

        } catch (Exception e) {
            handle(obj, e);


        }
        return obj.toString();

    }
}

