package my.interest.tamil.rest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


import my.interest.lang.tamil.internal.api.HandlerFactory;
import my.interest.lang.tamil.punar.handler.AbstractPunharchiHandler;

import org.json.JSONObject;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
@Path("/handlers")

public class HandlerResource {

    @GET
    @Path("/list")
    @Produces("application/json; charset=UTF-8")
    public String getList() throws  Exception{

        List<? extends AbstractPunharchiHandler> list =  HandlerFactory.getAllHandlers();
        List<String> ret = new ArrayList<String>();
        for (AbstractPunharchiHandler h : list) {
            ret.add(h.getName());
        }
        JSONObject obj=new JSONObject();
        obj.put("handlers", ret);
        return obj.toString();
    }

}
