package my.interest.tamil.rest.resources.filters;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
@Provider
public class TamilContainerFilter implements ContainerRequestFilter {
    @Context
    private HttpServletRequest servletRequest;

    @Override
    public ContainerRequest filter(ContainerRequest request) {
        servletRequest.getSession(true);
        //System.out.println("ContainerRequest=" + request.getClass().getName());
        return request;
    }
}
