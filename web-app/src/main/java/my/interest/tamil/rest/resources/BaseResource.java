package my.interest.tamil.rest.resources;

import my.interest.lang.tamil.TamilUtils;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.internal.api.DefinitionFactory;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.generated.types.*;
import my.interest.lang.tamil.generated.types.Properties;
import my.interest.lang.tamil.translit.EnglishToTamilCharacterLookUpContext;
import my.interest.tamil.rest.resources.exception.ResourceException;
import my.interest.tamil.rest.resources.exception.ResourceNotFoundException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public abstract class BaseResource {


    public abstract GlobalTypes getGlobalTypes(TamilRootWords words);

    public abstract int getCount(TamilRootWords words);


    @GET
    @Path("/words/count")
    @Produces({MediaType.TEXT_PLAIN})
    public String getCount() {

        TamilRootWords list = PersistenceInterface.get().getAllRootWords();
        return String.valueOf(getCount(list));
    }

    @GET
    @Path("/types")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<GlobalTypes> listTypes(@QueryParam("select") String types) {
        try {
            TamilRootWords list = PersistenceInterface.get().getAllRootWords();
            if (list == null) {
                throw new ResourceNotFoundException();
            }

            GlobalTypes glob = new GlobalTypes();
            GlobalTypes existing = getGlobalTypes(list);
            if (existing != null) {
                if (types == null) {
                    types = "";
                }
                List<String> queried = TamilUtils.parseString(types, ",", false);
                for (Typed t : existing.getDeclare()) {
                    boolean include = false;
                    for (String q : queried) {
                        if (t.getName().trim().startsWith(q)) {
                            include = true;
                            break;
                        }
                    }
                    if (include) {
                        glob.getDeclare().add(t);
                    }
                }
            }


            return new ObjectFactory().createGlobalTypes(glob);
        } catch (WebApplicationException wa) {
            throw wa;
        } catch (ResourceException re) {
            throw re;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceException(e.getMessage());
        }
    }


    @GET
    @Path("/types-values")
    @Produces("application/json; charset=UTF-8")
    public String listTypeValues() {
        try {
            TamilRootWords list = PersistenceInterface.get().getAllRootWords();
            if (list == null) {
                throw new ResourceNotFoundException();
            }
            List<String> ret = new ArrayList<String>();

            GlobalTypes existing = getGlobalTypes(list);
            if (existing != null) {

                for (Typed t : existing.getDeclare()) {
                    if (DefinitionFactory.NS_TAMIL.equals(t.getType().getNamespaceURI())) {
                        if (!ret.contains(t.getType().getLocalPart())) {
                            ret.add(t.getType().getLocalPart());
                        }
                    }
                }
            }
            JSONObject obj = new JSONObject();
            obj.put("values", ret);
            return obj.toString();


        } catch (WebApplicationException wa) {
            throw wa;
        } catch (ResourceException re) {
            throw re;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceException(e.getMessage());
        }
    }


    @GET
    @Path("/types/name/{name}/description")
    @Produces({MediaType.TEXT_PLAIN})
    public String getDescription(@PathParam("name") String name) {
        try {
            TamilRootWords list = PersistenceInterface.get().getAllRootWords();
            if (list == null) {
                throw new ResourceNotFoundException();
            }
            GlobalTypes existing = getGlobalTypes(list);
            if (existing == null) {
                throw new ResourceNotFoundException();
            }
            Typed matched = null;
            for (Typed t : existing.getDeclare()) {
                if (name.equals(t.getName().trim())) {
                    matched = t;
                    break;
                }
            }
            if (matched == null) {
                throw new ResourceNotFoundException();
            }
            return matched.getDescription();
        } catch (WebApplicationException wa) {
            throw wa;
        } catch (ResourceException re) {
            throw re;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceException(e.getMessage());
        }
    }

  //Add a question

    @PUT
    @Path("/types/name/{name}")
    @Produces({MediaType.TEXT_PLAIN})
    public Response addNewType(@PathParam("name") String name, @QueryParam("desc") String description, @QueryParam("tamil") boolean nsTamil, String value) {
        if (value == null || name == null) {
            throw new RuntimeException("value or name is not found.");
        }
        value = value.replaceAll("\\n", "");
        name = name.replaceAll("\\n", "");
        name = name.trim();
        value = value.trim();
        try {

            int status = 201;
            PersistenceInterface.get().lock();
            TamilRootWords list = PersistenceInterface.get().getAllRootWords();
            if (list == null) {
                throw new ResourceNotFoundException();
            }
            GlobalTypes existing = getGlobalTypes(list);
            if (existing == null) {
                throw new ResourceNotFoundException();
            }
            Typed matched = null;
            for (Typed t : existing.getDeclare()) {
                if (name.equals(t.getName().trim())) {
                    matched = t;
                    matched.setDescription(description);
                    matched.setType(new QName(nsTamil ? DefinitionFactory.NS_TAMIL : DefinitionFactory.NS_XML, value, nsTamil ? "tamil" : "xs"));
                    status = 202;
                    break;
                }
            }
            if (matched == null) {
                matched = new Typed();
                matched.setName(name);
                matched.setDescription(description);
                matched.setType(new QName(nsTamil ? DefinitionFactory.NS_TAMIL : DefinitionFactory.NS_XML, value, nsTamil ? "tamil" : "xs"));
                existing.getDeclare().add(matched);
            }
            PersistenceInterface.get().persist(list);
            return Response.status(status).build();
        } catch (WebApplicationException wa) {
            throw wa;
        } catch (ResourceException re) {
            throw re;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceException(e.getMessage());
        } finally {
            PersistenceInterface.get().unlock();
        }
    }

    @GET
    @Path("/types/name/{name}/values")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<Properties> listValues(@PathParam("name") String name) {
        try {
            TamilRootWords list = PersistenceInterface.get().getAllRootWords();
            if (list == null) {
                throw new ResourceNotFoundException();
            }
            GlobalTypes existing = getGlobalTypes(list);
            if (existing == null) {
                throw new ResourceNotFoundException();
            }
            Typed matched = null;
            for (Typed t : existing.getDeclare()) {
                if (name.equals(t.getName().trim())) {
                    matched = t;
                    break;
                }
            }
            if (matched == null) {
                throw new ResourceNotFoundException();
            }
            Map<String, String> map = null;
            if (DefinitionFactory.NS_TAMIL.equals(matched.getType().getNamespaceURI())) {
                String localpart = matched.getType().getLocalPart();
                localpart = localpart.trim();
                localpart = localpart.replaceAll("\\n", "");
                if (localpart.startsWith("[") && localpart.endsWith("]")) {
                    List<String> items = TamilUtils.parseString(localpart.substring(1, localpart.length() - 1));
                    map = new LinkedHashMap<String, String>();
                    for (String it : items) {
                        it = it.trim();
                        if (it.equals("")) continue;
                        List<String> pairs = TamilUtils.parseString(it, "=");
                        String key = pairs.get(0);
                        String value = null;
                        if (pairs.size() > 1) {
                            value = EnglishToTamilCharacterLookUpContext.getBestMatch(pairs.get(1)).toString();
                        } else {
                            value = key;
                        }
                        map.put(key.trim(), value.trim());
                    }
                } else {
                    try {
                        map = TamilUtils.getEnumValuesAsList(Class.forName(TamilUtils.getPackageName(RootVerbDescription.class.getName()) + "." + matched.getType().getLocalPart()));
                    } catch (ClassNotFoundException cnf) {
                        cnf.printStackTrace();

                    }
                }
            } else {
                if ("boolean".equals(matched.getType().getLocalPart())) {
                    map = new HashMap<String, String>();
                    map.put("true", "ஆம்");
                    map.put("false", "இல்லை");

                }
            }
            if (map == null) {
                return new ObjectFactory().createProperties(new Properties());
            } else {

                Properties p = new Properties();
                for (Map.Entry<String, String> e : map.entrySet()) {
                    Property pr = new Property();

                    TamilWord words = null;
                    if (!TamilWord.from(e.getValue()).containsTamilCharacter()) {
                        words = EnglishToTamilCharacterLookUpContext.getBestMatch(e.getKey());
                    }
                    if (words == null) {
                        pr.setValue(e.getValue());
                        pr.setName(e.getKey());
                    } else {
                        pr.setName(e.getKey());
                        pr.setValue(words.toString());
                    }
                    p.getProperty().add(pr);
                }
                return new ObjectFactory().createProperties(p);


            }


        } catch (WebApplicationException wa) {
            throw wa;
        } catch (ResourceException re) {
            throw re;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceException(e.getMessage());
        }
    }
}
