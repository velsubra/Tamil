package my.interest.tamil.rest.resources;


import tamil.lang.TamilWord;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.internal.api.PeyarchcholDescriptionMatcher;
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
@Path("root-nouns")

public class PeyarchcholResource  extends BaseResource {

    static final Logger logger = Logger.getLogger(PeyarchcholResource.class.getName());



    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<Peyar> list() {
        try {
            Peyar desc =PersistenceInterface.get().getAllRootWords().getPeyar();
            return new ObjectFactory().createPeyar(desc);
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
    @Path("/nouns")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<PeyarchchchorrkalhDescription> list(@QueryParam("pattern") String pattern, @QueryParam("all") boolean all, @QueryParam("noprops") boolean noprops) {
        try {
            PeyarchchchorrkalhDescription desc = all ? PersistenceInterface.get().getAllRootWords().getPeyar().getWords() : PersistenceInterface.get().findAllNounsWithPattern(pattern, 20, null, PeyarchcholDescriptionMatcher.STARTS_MATCHER);

            if (desc == null) {
                throw new ResourceNotFoundException();
            }
            if (noprops) {
                PeyarchchchorrkalhDescription ret = new PeyarchchchorrkalhDescription();
                ret.setList(new PeyarchchchorrkalhList());
                for (PeyarchcholDescription r : desc.getList().getWord()) {
                    PeyarchcholDescription temp = new PeyarchcholDescription();
                    temp.setRoot(r.getRoot());
                    ret.getList().getWord().add(temp);
                }
                desc = ret;
            }

            PeyarchchchorrkalhDescription temp = new PeyarchchchorrkalhDescription();
            temp.setList(new PeyarchchchorrkalhList());
            temp.getList().getWord().addAll(desc.getList().getWord());


            return new ObjectFactory().createNouns(temp);
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
    @Path("/nouns/name/{name}/adjacent/next")
    @Produces({MediaType.TEXT_PLAIN})
    public String findNext(@PathParam("name") String name, @QueryParam("search") String search) {
        try {
            PeyarchcholDescription root = PersistenceInterface.get().findNextPeyarchcholDescription(name, search == null ? PeyarchcholDescriptionMatcher.TRAVERSE : PersistenceInterface.peyarmatchermap.get(search));
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
    @Path("/nouns/name/{name}/adjacent/previous")
    @Produces({MediaType.TEXT_PLAIN})
    public String findPrevious(@PathParam("name") String name, @QueryParam("search") String search) {
        try {
            PeyarchcholDescription root = PersistenceInterface.get().findPreviousPeyarchcholDescription(name, search == null ? PeyarchcholDescriptionMatcher.TRAVERSE : PersistenceInterface.peyarmatchermap.get(search));
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
    @Path("/nouns/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<PeyarchcholDescription> describe(@PathParam("name") String name) {
        try {
            PeyarchcholDescription root = PersistenceInterface.get().findPeyarchcholDescription(name);
            if (root == null) {
                throw new ResourceNotFoundException();
            }
            return new ObjectFactory().createRootPeyar(root);
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
    @Path("/nouns/name/{name}/properties")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<Properties> getProperties(@PathParam("name") String name, @QueryParam("select") String select) {
        try {
            PeyarchcholDescription root = PersistenceInterface.get().findPeyarchcholDescription(name);
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
    @Path("/nouns/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteNoun(@PathParam("name") String name) {
        try {
            PersistenceInterface.get().deleteNoun(name);

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
    @Path("/nouns/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addNoun(@PathParam("name") String name) {
        try {
            if (!TamilWord.from(name).isPure()) {
                throw new ResourceException(name + " தமிழ்ச்சொல்லில்லை");
            }

            if (PersistenceInterface.get().addRootNoun(name)) {
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
    @Path("/nouns/name/{name}/property/{property}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addProperty(@PathParam("name") String name, @PathParam("property") String property, String value) {
        try {
            if (PersistenceInterface.get().addPeyarProperty(name, property, value)) {
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
    @Path("/nouns/name/{name}/property/{property}")
    public Response deleteProperty(@PathParam("name") String name, @PathParam("property") String property) {
        try {
            // logger.info(name + ":" + property + ":");
            PersistenceInterface.get().deletePeyarProperty(name, property);
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

    @Path("/nouns/name/{name}/property/{property}")
    @Produces("text/plain; charset=UTF-8")
    @GET
    public Response getProperty(@PathParam("name") String name, @PathParam("property") String property) {
        try {
            // logger.info(name + ":" + property + ":");
            String val = PersistenceInterface.get().get().getPeyarProperty(name, property);
            if (val == null) {
              return  Response.status(404).build();
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
        return words.getPeyar().getGlobalTypes();
    }

    @Override
    public int getCount(TamilRootWords words) {

        return words.getPeyar().getWords().getList().getWord().size();
    }
}
