package my.interest.tamil.rest.resources.api;

import my.interest.lang.tamil.generated.types.ObjectFactory;
import my.interest.lang.tamil.generated.types.PeyarchchchorrkalhDescription;
import my.interest.lang.tamil.generated.types.PeyarchchchorrkalhList;
import my.interest.lang.tamil.generated.types.PeyarchcholDescription;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.tamil.rest.resources.exception.ResourceException;
import my.interest.tamil.rest.resources.exception.ResourceNotFoundException;
import tamil.lang.api.persist.matcher.PeyarchcholDescriptionMatcher;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
@Path("api/persist")
public class PersistenceResource extends BaseResource {


    @GET
    @Path("/applet/nouns/")
    @Produces("application/json; charset=UTF-8")
  //  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<PeyarchchchorrkalhDescription> list() {
        try {
            PeyarchchchorrkalhDescription desc =  PersistenceInterface.get().findAllNounsWithPattern("", 2000, null, PeyarchcholDescriptionMatcher.FOR_APPLET_MATCHER);

            if (desc == null) {
                throw new ResourceNotFoundException();
            }
//            if (noprops) {
//                PeyarchchchorrkalhDescription ret = new PeyarchchchorrkalhDescription();
//                ret.setList(new PeyarchchchorrkalhList());
//                for (PeyarchcholDescription r : desc.getList().getWord()) {
//                    PeyarchcholDescription temp = new PeyarchcholDescription();
//                    temp.setRoot(r.getRoot());
//                    ret.getList().getWord().add(temp);
//                }
//                desc = ret;
//            }

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
}
