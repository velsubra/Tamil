package my.interest.tamil.rest.resources;


import tamil.lang.TamilWord;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import tamil.lang.api.persist.matcher.RootVerbDescriptionMatcher;
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

@Path("root-verbs")

public class RootVerbResource extends BaseResource{

    static final Logger logger = Logger.getLogger(RootVerbResource.class.getName());


    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<RootVerbsDescription> listAll() {
          return list(null,true, false);
    }

    @GET
    @Path("/verbs")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<RootVerbsDescription> list(@QueryParam("pattern") String pattern, @QueryParam("all") boolean all, @QueryParam("noprops") boolean noprops) {
        try {
            RootVerbsDescription desc = all? PersistenceInterface.get().getAllRootWords().getVinai().getVerbs() :  PersistenceInterface.get().findAllVerbsWithPattern(pattern,  20,null, RootVerbDescriptionMatcher.STARTS_MATCHER);

            if (desc == null) {
                throw new ResourceNotFoundException();
            }
            if (noprops) {
                RootVerbsDescription ret = new RootVerbsDescription();
                ret.setList(new RootVerbsList());
                for (RootVerbDescription r : desc.getList().getVerb()) {
                    RootVerbDescription temp = new RootVerbDescription();
                    temp.setRoot(r.getRoot());
                    ret.getList().getVerb().add(temp);
                }
                desc = ret;
            }

            RootVerbsDescription temp= new RootVerbsDescription();
            temp.setList(new RootVerbsList());
            temp.getList().getVerb().addAll(desc.getList().getVerb());

            //  logger.info("size verbs#" + desc.getVerb().size() + ":" + pattern);
            return new ObjectFactory().createVerbs(temp);
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
    @Path("/verbs/name/{name}/adjacent/next")
    @Produces({MediaType.TEXT_PLAIN})
    public String findNext(@PathParam("name") String name, @QueryParam("search") String search) {
        try {
            RootVerbDescription root =  PersistenceInterface.get().findNextRootVerbDescription(name, search ==null ? RootVerbDescriptionMatcher.TRAVERSE : PersistenceInterface.matchermap.get(search));
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
    @Path("/verbs/name/{name}/adjacent/previous")
    @Produces({MediaType.TEXT_PLAIN})
    public String findPrevious(@PathParam("name") String name, @QueryParam("search") String search) {
        try {
            RootVerbDescription root = PersistenceInterface.get().findPreviousRootVerbDescription(name, search ==null ? RootVerbDescriptionMatcher.TRAVERSE: PersistenceInterface.matchermap.get(search));
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
    @Path("/verbs/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<RootVerbDescription> describe(@PathParam("name") String name) {
        try {
            RootVerbDescription root = PersistenceInterface.get().findRootVerbDescription(name);
            if (root == null) {
                throw new ResourceNotFoundException();
            }
            return new ObjectFactory().createRootVerb(root);
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
    @Path("/verbs/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteVerb(@PathParam("name") String name) {
        try {
            PersistenceInterface.get().deleteRootVerb(name);

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
    @Path("/verbs/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addVerb(@PathParam("name") String name) {
        try {
            if (!TamilWord.from(name).isPure()) {
                throw new ResourceException(name + " தமிழ்ச்சொல்லில்லை");
            }

            if (PersistenceInterface.get().addRootVerb(name)) {
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
    @Path("/verbs/name/{name}/property/{property}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addVerbProperty(@PathParam("name") String name, @PathParam("property") String property, String value) {
        try {
            if (PersistenceInterface.get().addRootVerbProperty(name, property, value)) {
                return Response.status(201).build();
            } else {
                return Response.status(202).build();
            }

//        Map<String, RootVerbDescription> map = DefinitionFactory.getAllRootVerbsMap();
//        logger.info(name + ":" + property + ":" + value);
//        RootVerbDescription root = map.get(name);
//        if (root == null) {
//            throw new ResourceNotFoundException();
//        } else {
//            synchronized (root) {
//                if (root.getDescription() == null) {
//                    root.setDescription(new Properties());
//                }
//                boolean found = false;
//                for (Property p : root.getDescription().getProperty()) {
//                    if (property.equals(p.getName())) {
//                        found = true;
//                        p.setValue(value);
//                        break;
//                    }
//                }
//                if (!found) {
//                    synchronized (root.getDescription().getProperty()) {
//                        Property p = new Property();
//                        p.setName(property);
//                        p.setValue(value);
//                        root.getDescription().getProperty().appendNodesToAllPaths(p);
//                    }
//                }
//
//                return Response.status(202).build();
//            }
//        }
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
    @Path("/verbs/name/{name}/property/{property}")
    public Response deleteVerbProperty(@PathParam("name") String name, @PathParam("property") String property) {
        try {
            // logger.info(name + ":" + property + ":");
            PersistenceInterface.get().deleteRootVerbProperty(name, property);
            return Response.status(202).build();
//        Map<String, RootVerbDescription> map = DefinitionFactory.getAllRootVerbsMap();
//        RootVerbDescription root = map.get(name);
//        if (root == null) {
//            throw new ResourceNotFoundException();
//        } else {
//            synchronized (root) {
//                if (root.getDescription() == null) {
//                    root.setDescription(new Properties());
//                }
//                synchronized (root.getDescription().getProperty()) {
//                    for (int i = 0; i < root.getDescription().getProperty().size(); i++) {
//                        Property p = root.getDescription().getProperty().get(i);
//                        if (property.equals(p.getName())) {
//                            root.getDescription().getProperty().remove(i);
//                            break;
//                        }
//
//                    }
//                }
//
//
//                return Response.status(202).build();
//            }
//        }

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
        return words.getVinai().getGlobalTypes();
    }

    @Override
    public int getCount(TamilRootWords words) {

        return words.getVinai().getVerbs().getList().getVerb().size();
    }
}
