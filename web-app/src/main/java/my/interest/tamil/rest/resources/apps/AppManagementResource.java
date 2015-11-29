package my.interest.tamil.rest.resources.apps;

import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.generated.types.*;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.xml.AppCache;
import my.interest.tamil.rest.resources.exception.ResourceException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */

@Path("app-management")
public class AppManagementResource {

    @GET
    @Path("/apps")
    @Produces("application/json; charset=UTF-8")
    public String list() throws Exception {
        List<String> ret = PersistenceInterface.get().getAppNames();
        JSONObject obj = new JSONObject();
        obj.put("results", ret);
        return obj.toString();
    }


    @GET
    @Path("/apps/name/{name}/resources")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<AppDescriptionBase> listResources(@PathParam("name") String name) throws Exception {
        AppDescription app = PersistenceInterface.get().findApp(name);
        if (app == null) {
            return new ObjectFactory().createAppbase(null);
        }

        AppDescriptionBase ret = new AppDescriptionBase();
        ret.setRoot(app.getRoot());
        ret.setDescription(app.getDescription());
        ret.setResourceInheritance(app.getResourceInheritance());
        ret.setProperties(app.getProperties());
        ret.setName(app.getName());
        ret.setCode("****");
        ret.getExternalResources().addAll(app.getExternalResources());
        ret.setLibraryDependencies(app.getLibraryDependencies());
        ret.setResources(new AppResources());
        if (app.getResources() != null) {
            ret.getResources().setWelcome(app.getResources().getWelcome());
            for (AppResource r : app.getResources().getResources()) {
                AppResource res = new AppResource();
                res.setName(r.getName());
                res.setType(r.getType());
                ret.getResources().getResources().add(res);
            }
        }

        return new ObjectFactory().createAppbase(ret);
    }

    @POST
    @Path("/apps/name/{name}")
    public Response addApp(@PathParam("name") String name, @QueryParam("as") String as, @HeaderParam("X-TAMIL-APP-ACCESS-CODE") String code) {
        try {
            if (as == null) {
                PersistenceInterface.get().createApp(code, name);
            } else {
                PersistenceInterface.get().createAppAs(code, name, as);
            }
            return Response.status(201).build();
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
    @Path("/apps/name/{name}/resources/resource/{resourcename:.*}")

    public Response addResource(@PathParam("name") String name, @PathParam("resourcename") String resourcename, @HeaderParam("X-TAMIL-APP-ACCESS-CODE") String code) {
        try {
            PersistenceInterface.get().createResourceToApp(code, name, resourcename);
            return Response.status(201).build();
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
    @Path("/apps/name/{name}/libs/text")

    public Response createClassloader(@PathParam("name") String name,  @HeaderParam("X-TAMIL-APP-ACCESS-CODE") String code, String paths) {
        try {
          List<String> urls =  PersistenceInterface.get().updateClassloader(code, name, paths);
            return Response.status(200).type(MediaType.TEXT_PLAIN).entity(TamilUtils.getSeparatedListOfString(urls, "","",false,"\n")).build();
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
    @Path("/apps/name/{name}/resources/resource/{resourcename:.*}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<AppResource> getResource(@PathParam("name") String name, @PathParam("resourcename") String resourcename) {

        try {
            AppResource resource = PersistenceInterface.get().findAppResource(name, resourcename,true);

            return new ObjectFactory().createAppresource(resource);

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
    @Path("/apps/name/{name}/resources/resource/{resourcename:.*}")
    @Consumes("application/x-www-form-urlencoded;charset=UTF-8")
    public Response updateResource(@PathParam("name") String name, @PathParam("resourcename") String resourcename, @QueryParam("type") String type, byte[] data, @HeaderParam("X-TAMIL-APP-ACCESS-CODE") String code) {
        try {
            String message = PersistenceInterface.get().addOrUpdateResourceToApp(code, name, resourcename, type, data);
           // System.out.println("Content:"+ new String(data));
            if (message == null) {
                return Response.status(202).build();
            } else {
                return Response.status(200).type(MediaType.TEXT_PLAIN).entity(message).build();
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
    @Path("/apps/name/{name}/")
    @Consumes("text/plain; charset=UTF-8")
    public Response updateApp(@PathParam("name") String name, @QueryParam("welcome") String welcome,  @HeaderParam("X-TAMIL-APP-ACCESS-CODE") String code, @QueryParam("parents") String parents, @QueryParam("inheritanceorder") String inheritanceorder, @QueryParam("externalroots") String externalUrls,String desc) {
        try {
            PersistenceInterface.get().updateApp(code, name, welcome, parents,inheritanceorder, desc, externalUrls);
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

    @DELETE
    @Path("/apps/name/{name}/resources/resource/{resourcename:.*}")
    public Response deleteResource(@PathParam("name") String name, @PathParam("resourcename") String resourcename, @HeaderParam("X-TAMIL-APP-ACCESS-CODE") String code) {
        try {
            PersistenceInterface.get().deleteResourceFromApp(code, name, resourcename);
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

    @DELETE
    @Path("/apps/name/{name}")
    public Response deleteApp(@PathParam("name") String name, @HeaderParam("X-TAMIL-APP-ACCESS-CODE") String code) {
        try {
            PersistenceInterface.get().deleteApp(code, name);
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
}
