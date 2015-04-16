package my.interest.tamil.rest.resources;


import tamil.lang.TamilWord;
import tamil.lang.api.persist.matcher.IdaichcholDescriptionMatcher;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.generated.types.*;
import my.interest.tamil.rest.resources.exception.ResourceException;
import my.interest.tamil.rest.resources.exception.ResourceNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import java.util.logging.Logger;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */

@Path("root-prepositions")

public class IdaichcholResource extends BaseResource {
    static final Logger logger = Logger.getLogger(IdaichcholResource.class.getName());


    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<Idai> list() {
        try {
            Idai desc =PersistenceInterface.get().getAllRootWords().getIdai();
            return new ObjectFactory().createIdai(desc);
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
    @Path("/prepositions")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<IdaichchorrkalhDescription> list(@QueryParam("pattern") String pattern, @QueryParam("all") boolean all, @QueryParam("noprops") boolean noprops) {
        try {
            IdaichchorrkalhDescription desc = all ? PersistenceInterface.get().getAllRootWords().getIdai().getWords() : PersistenceInterface.get().findAllPrepositionsWithPattern(pattern, 20, null, IdaichcholDescriptionMatcher.STARTS_MATCHER);

            if (desc == null) {
                throw new ResourceNotFoundException();
            }
            if (noprops) {
                IdaichchorrkalhDescription ret = new IdaichchorrkalhDescription();
                ret.setList(new IdaichchorrkalhList());
                for (IdaichcholDescription r : desc.getList().getWord()) {
                    IdaichcholDescription temp = new IdaichcholDescription();
                    temp.setRoot(r.getRoot());
                    ret.getList().getWord().add(temp);
                }
                desc = ret;
            }

            IdaichchorrkalhDescription temp = new IdaichchorrkalhDescription();
            temp.setList(new IdaichchorrkalhList());
            temp.getList().getWord().addAll(desc.getList().getWord());


            return new ObjectFactory().createPrepositions(temp);
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
    @Path("/prepositions/name/{name}/adjacent/next")
    @Produces({MediaType.TEXT_PLAIN})
    public String findNext(@PathParam("name") String name, @QueryParam("search") String search) {
        try {
            IdaichcholDescription root = PersistenceInterface.get().findNextIdaichcholDescription(name, search == null ? IdaichcholDescriptionMatcher.TRAVERSE : PersistenceInterface.idaimatchermap.get(search));
            if (root == null) {
                throw new ResourceNotFoundException();
            }
            return root.getRoot();
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
    @Path("/prepositions/name/{name}/adjacent/previous")
    @Produces({MediaType.TEXT_PLAIN})
    public String findPrevious(@PathParam("name") String name, @QueryParam("search") String search) {
        try {
            IdaichcholDescription root = PersistenceInterface.get().findPreviousIdaichcholDescription(name, search == null ? IdaichcholDescriptionMatcher.TRAVERSE : PersistenceInterface.idaimatchermap.get(search));
            if (root == null) {
                throw new ResourceNotFoundException();
            }
            return root.getRoot();
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
    @Path("/prepositions/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<IdaichcholDescription> describe(@PathParam("name") String name) {
        try {
            IdaichcholDescription root = PersistenceInterface.get().findIdaichcholDescription(name);
            if (root == null) {
                throw new ResourceNotFoundException();
            }
            return new ObjectFactory().createRootIdai(root);
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
    @Path("/prepositions/name/{name}/properties")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<Properties> getProperties(@PathParam("name") String name, @QueryParam("select") String select) {
        try {
            IdaichcholDescription root = PersistenceInterface.get().findIdaichcholDescription(name);
            if (root == null) {
                throw new ResourceNotFoundException();
            }
            if (select == null) {
                select = "";
            }

            Properties properties = new Properties();
            if (root.getDescription() != null) {
                for (Property p : root.getDescription().getProperty()) {
                    if (p.getName().startsWith(select)) {
                        properties.getProperty().add(p);
                    }
                }
            }
            return new ObjectFactory().createProperties(properties);
        } catch (WebApplicationException wa) {
            throw wa;
        } catch (ResourceException re) {
            throw re;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceException(e.getMessage());
        }
    }

    @DELETE
    @Path("/prepositions/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deletePrep(@PathParam("name") String name) {
        try {
            PersistenceInterface.get().deletePrep(name);

            return Response.status(202).build();
        } catch (WebApplicationException wa) {
            throw wa;
        } catch (ResourceException re) {
            throw re;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceException(e.getMessage());
        }
    }


    @POST
    @Path("/prepositions/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addPrep(@PathParam("name") String name) {
        try {
            if (!TamilWord.from(name).isPure()) {
                throw new ResourceException(name + " தமிழ்ச்சொல்லில்லை");
            }

            if (PersistenceInterface.get().addRootPreposition(name)) {
                return Response.status(201).build();
            } else {
                return Response.status(202).build();
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


    @PUT
    @Path("/prepositions/name/{name}/property/{property}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addProperty(@PathParam("name") String name, @PathParam("property") String property, String value) {
        try {
            if (PersistenceInterface.get().addIdaiProperty(name, property, value)) {
                return Response.status(201).build();
            } else {
                return Response.status(202).build();
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

    @DELETE
    @Path("/prepositions/name/{name}/property/{property}")
    public Response deleteProperty(@PathParam("name") String name, @PathParam("property") String property) {
        try {
            // logger.info(name + ":" + property + ":");
            PersistenceInterface.get().deleteIdaiProperty(name, property);
            return Response.status(202).build();


        } catch (WebApplicationException wa) {
            throw wa;
        } catch (ResourceException re) {
            throw re;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceException(e.getMessage());
        }
    }

    @Path("/prepositions/name/{name}/property/{property}")
    @Produces("text/plain; charset=UTF-8")
    @GET
    public Response getProperty(@PathParam("name") String name, @PathParam("property") String property) {
        try {
            // logger.info(name + ":" + property + ":");
            String val = PersistenceInterface.get().get().getIdaiProperty(name, property);
            if (val == null) {
                return Response.status(404).build();
            }
            return Response.status(200).entity(val).build();


        } catch (WebApplicationException wa) {
            throw wa;
        } catch (ResourceException re) {
            throw re;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceException(e.getMessage());
        }
    }

    @Override
    public GlobalTypes getGlobalTypes(TamilRootWords words) {
        return words.getIdai().getGlobalTypes();
    }

    @Override
    public int getCount(TamilRootWords words) {

        return words.getIdai().getWords().getList().getWord().size();
    }
}