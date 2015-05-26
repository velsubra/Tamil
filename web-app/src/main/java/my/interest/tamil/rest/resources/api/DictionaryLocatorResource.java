package my.interest.tamil.rest.resources.api;

import my.interest.lang.tamil.generated.types.RootVerbDescription;
import my.interest.lang.tamil.impl.dictionary.DictionaryCollection;
import my.interest.lang.tamil.punar.PropertyDescriptionContainer;
import my.interest.tamil.rest.resources.exception.ResourceException;
import my.interest.tamil.rest.resources.exception.ResourceNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.Vinaiyadi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
@Path("api/dictionary")
public class DictionaryLocatorResource extends BaseResource {


    TamilDictionary dictionary = null;

    public DictionaryLocatorResource() {
        dictionary = TamilFactory.getSystemDictionary();
    }

    ;

    DictionaryLocatorResource(TamilDictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Path("/")
    public DictionaryResource getGlobal() {

        return new DictionaryResource(dictionary);

    }

    @GET
    @Path("base-types/{base}")
    @Produces("application/json; charset=UTF-8")
    public String getBaseTypes(@PathParam("base") String type) {
        try {
            Class cls = null;

            try {
                cls = Class.forName(type);
            } catch (Exception e) {
                throw new ResourceNotFoundException();
            }
            if (!IKnownWord.class.isAssignableFrom(cls)) {
                throw new ResourceNotFoundException();
            }

            JSONObject obj = new JSONObject();
            Collection<Class> list = new ArrayList<Class>();
            Class base = cls.getSuperclass();
            if (base != null && IKnownWord.class.isAssignableFrom(base)) {
                list.add(base);
            }
            for (Class c : cls.getInterfaces()) {
                if (IKnownWord.class.isAssignableFrom(c)) {
                    list.add(c);
                }
            }

            JSONArray array = new JSONArray();
            obj.put("list", array);
            for (Class c : list) {
                JSONObject typejson = new JSONObject();
                array.put(typejson);

                typejson.put("clazz", c.getName());
                typejson.put("tamil", c.getSimpleName());
            }
            return obj.toString();
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());


        }
    }


    @Path("/root-verbs/{verb}/dictionary")
    public DictionaryResource getVerbDictionary(@PathParam("verb") String root) {
        try {
            RootVerbDescription description = TamilFactory.getPersistenceManager().getRootVerbManager().findRootVerbDescription(root);
            if (description == null) {
                throw new ResourceNotFoundException();
            }
            PropertyDescriptionContainer container = new PropertyDescriptionContainer(description, null);
            List<TamilDictionary> list = new ArrayList<TamilDictionary>();
            if (container.isTransitive()) {
                Vinaiyadi vin = Vinaiyadi.get(TamilWord.from(root), container, true);
                if (vin.getRelatedDictionary().size() > 1) {
                    list.add(vin.getRelatedDictionary());
                }
            }

            if (container.isInTransitive()) {
                Vinaiyadi vin = Vinaiyadi.get(TamilWord.from(root), container, false);
                if (vin.getRelatedDictionary().size() > 1) {
                    list.add(vin.getRelatedDictionary());
                }
            }
            DictionaryCollection collection = new DictionaryCollection(list);
            return new DictionaryResource(collection);
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());


        }
    }


    @Path("/word-types/{type}/dictionary")
    public DictionaryResource getTypeDictionary(@PathParam("type") String type) {
        try {
            Class cls = null;

            try {
                cls = Class.forName(type);
            } catch (Exception e) {
                throw new ResourceNotFoundException();
            }
            if (!IKnownWord.class.isAssignableFrom(cls)) {
                throw new ResourceNotFoundException();
            }

            TamilDictionary dictionary = this.dictionary.getMiniDictionaryForWordType(cls);
            if (dictionary == null) {
                throw new ResourceNotFoundException();
            }
            return new DictionaryResource(dictionary);
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());


        }
    }


}
