package my.interest.tamil.webapp;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

import javax.ws.rs.ext.Provider;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
@Provider
public class NoCacheFilter implements ContainerResponseFilter {

    /**
     * Filter the response.
     * <p/>
     * An implementation may modify the state of the response or
     * return a new instance.
     *
     * @param request  the request.
     * @param response the response.
     * @return the response.
     */
    @Override
    public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
      //  System.out.println("Setting headers!");
       // response.getHttpHeaders().add("Cache-Control", "no-cache, no-store, must-revalidate");
      //  response.getHttpHeaders().add("Pragma", "no-cache");
        //response.getHttpHeaders().add("Expires", "0");
        return response;
    }
}
